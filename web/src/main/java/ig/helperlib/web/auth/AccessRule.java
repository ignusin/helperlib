package ig.helperlib.web.auth;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

import lombok.Getter;

@Getter
public class AccessRule {
	private String urlPattern;
	private AuthorizationType authType;
	private HttpMethodMap httpMethodMap;
	private List<String> roles;
	
	public AccessRule(String urlPattern, AuthorizationType authType, HttpMethodMap httpMethodMap, String... roles) {
		this.urlPattern = urlPattern;
		this.authType = authType;
		this.httpMethodMap = httpMethodMap;
		this.roles = ListUtils.unmodifiableList(Arrays.asList(roles));
	}
}
