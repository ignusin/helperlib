package ig.helperlib.web.auth.jwt;

import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import ig.helperlib.web.auth.AuthInfo;

public class JwtGeneratorImpl implements JwtGenerator {
	private final String secret;
	
	public JwtGeneratorImpl(String secret) {
		this.secret = secret;
	}
	
	@Override
	public String generateToken(AuthInfo authInfo) {
		HashMap<String, Object> claims = new HashMap<>();
		claims.put(Claim.USERID.getTokenField(), authInfo.getUserId());
		claims.put(Claim.SUBJECT.getTokenField(), authInfo.getUserName());
		
		Object[] roleNames = authInfo.getRoles().stream().toArray();
		claims.put(Claim.ROLES.getTokenField(), roleNames);
		
		// TODO: set expiration and validate token in JwtParser.
		String result = Jwts
			.builder()
			.setClaims(claims)
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact();
		
		return result;
	}
}
