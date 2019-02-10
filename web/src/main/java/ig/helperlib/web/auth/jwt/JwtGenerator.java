package ig.helperlib.web.auth.jwt;

import ig.helperlib.web.auth.AuthInfo;

public interface JwtGenerator {
	String generateToken(AuthInfo authInfo);
}
