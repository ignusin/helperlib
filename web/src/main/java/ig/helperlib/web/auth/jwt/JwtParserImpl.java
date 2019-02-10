package ig.helperlib.web.auth.jwt;

import java.util.List;

import io.jsonwebtoken.Jwts;

import ig.helperlib.web.auth.AuthInfo;

public class JwtParserImpl implements JwtParser {
	private final String secret;
	
	public JwtParserImpl(String secret) {
		this.secret = secret;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AuthInfo parseToken(String token) {
		io.jsonwebtoken.Claims claims = Jwts
			.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token)
			.getBody();
		
		Integer userId = claims.get(Claim.USERID.getTokenField(), Integer.class);
		String userName = claims.get(Claim.SUBJECT.getTokenField(), String.class);
		List<String> roleNames = (List<String>)claims.get(Claim.ROLES.getTokenField(), List.class);
		
		return new AuthInfo(userId, userName, roleNames.toArray(new String[0]));
	}
}
