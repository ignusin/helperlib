package ig.helperlib.web.auth;

public class AuthInfoHolderImpl implements AuthInfoHolder {
	private AuthInfo authInfo;
	
	@Override
	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	@Override
	public AuthInfo getAuthInfo() {
		return authInfo;
	}
}
