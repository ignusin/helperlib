package ig.helperlib.web.auth.jwt;

public enum Claim {
	USERID("userid"), SUBJECT("sub"), ROLES("roles");
	
	private final String tokenField;
	
	Claim(String tokenField) {
		this.tokenField = tokenField;
	}
	
	public String getTokenField() {
		return tokenField;
	}
	
	public static Claim fromTokenField(String tokenField) {
		switch (tokenField) {
			case "userid": return USERID;
			case "sub": return SUBJECT;
			case "roles": return ROLES;
			default: throw new IllegalArgumentException("Invalid tokenField");
		}
	}
}
