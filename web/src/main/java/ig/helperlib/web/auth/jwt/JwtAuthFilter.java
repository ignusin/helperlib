package ig.helperlib.web.auth.jwt;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import ig.helperlib.web.auth.*;

public class JwtAuthFilter extends OncePerRequestFilter {
	private static class CompiledAccessRule {
		final Pattern urlPattern;
		final AccessRule accessRule;
		
		CompiledAccessRule(Pattern urlPattern, AccessRule accessRule) {
			this.urlPattern = urlPattern;
			this.accessRule = accessRule;
		}
	}
	
	
	private final static String HTTP_AUTH_HEADER 				= "Authorization";
	private final static String BEARER_KEYWORD 					= "Bearer ";
		
	private final List<CompiledAccessRule> accessRules;
	private final AuthInfoHolder authInfoHolder;
	private final JwtParser jwtParser;

	public JwtAuthFilter(List<AccessRule> accessRules, AuthInfoHolder authInfoHolder, JwtParser jwtParser) {
		this.accessRules = compileAccessRules(accessRules);
		this.authInfoHolder = authInfoHolder;
		this.jwtParser = jwtParser;
	}
	
	private static List<CompiledAccessRule> compileAccessRules(List<AccessRule> accessRules) {
		return accessRules.stream()
			.map(x -> new CompiledAccessRule(compileUrlPattern(x.getUrlPattern()), x))
			.collect(Collectors.toList());
	}
	
	private static Pattern compileUrlPattern(String urlPattern) {
		String regExpPattern = "^" + urlPattern.replace("/", "\\/");
		
		if (regExpPattern.indexOf('*') >= 0) {
			regExpPattern = regExpPattern.replace("*", ".*");
		}
		else {
			regExpPattern += "$";
		}
		
		return Pattern.compile(regExpPattern);
	}
	
	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		if (HttpMethods.OPTIONS.equals(request.getMethod())) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// Check if path is excluded.
		if (isAnybodyAllowed(request)) {		
			filterChain.doFilter(request, response);
			return;
		}
		
		// Extract token.
		String token = extractTokenFromHeader(request);

		if (token == null) {
			token = extractTokenFromParameter(request);
		}
		
		// If token wasn't extracted - send Unauthorized back to client.
		if (token == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return;
		}
		
		// Extract token data.
		AuthInfo authInfo;
		
		try {
			authInfo = jwtParser.parseToken(token);
		}
		catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return;			
		}
		
		if (!isAuthInfoAllowed(authInfo, request)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return;			
		}
		
		authInfoHolder.setAuthInfo(authInfo);
		
		filterChain.doFilter(request, response);
	}

	private String getAppRequestURI(HttpServletRequest request) {
		return request.getRequestURI().substring(request.getContextPath().length());
	}

	private boolean isAnybodyAllowed(HttpServletRequest request) {
		for (CompiledAccessRule rule: accessRules) {
			if (rule.urlPattern.matcher(getAppRequestURI(request)).matches()) {
				return rule.accessRule.getAuthType() == AuthorizationType.ANYONE;
			}
		}
		
		return false;
	}

	private boolean isRequestPreMatched(CompiledAccessRule rule, HttpServletRequest request) {
		return rule.urlPattern.matcher(getAppRequestURI(request)).matches()
			&& rule.accessRule.getHttpMethodMap().isHttpMethodSupported(request.getMethod());
	}
	
	private boolean isAuthInfoAllowed(AuthInfo authInfo, HttpServletRequest request) {
		for (CompiledAccessRule rule: accessRules) {
			if (isRequestPreMatched(rule, request)) {
				if (rule.accessRule.getRoles().size() == 0) {
					return true;
				}

				for (String role: authInfo.getRoles()) {
					if (rule.accessRule.getRoles().contains(role)) {
						return true;
					}
				}
				
				return false;
			}
		}
		
		return false;
	}
	
	private String extractTokenFromHeader(HttpServletRequest request) {
		String authHeader = request.getHeader(HTTP_AUTH_HEADER);
		
		if (authHeader == null) {
			return null;
		}
		
		int bearerIndex = authHeader.indexOf(BEARER_KEYWORD);
		if (bearerIndex < 0) {
			return null;
		}
		
		if (authHeader.length() <= bearerIndex + BEARER_KEYWORD.length() + 1) {
			return null;
		}
		
		String result = authHeader.substring(bearerIndex + BEARER_KEYWORD.length());
		
		return result;
	}

	private String extractTokenFromParameter(HttpServletRequest request) {
		return request.getParameter("auth_token");
	}

	private String extractTokenFromCookie(HttpServletRequest request) {
		return Stream.of(request.getCookies())
			.filter(x -> "auth".equals(x.getName()))
			.findFirst()
			.map(x -> x.getValue())
			.orElse(null);
	}
}
