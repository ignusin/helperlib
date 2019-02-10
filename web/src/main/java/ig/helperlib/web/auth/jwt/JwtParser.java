package ig.helperlib.web.auth.jwt;

import ig.helperlib.web.auth.AuthInfo;

public interface JwtParser {
	AuthInfo parseToken(String token);
}
