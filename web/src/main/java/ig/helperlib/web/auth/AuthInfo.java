package ig.helperlib.web.auth;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

public class AuthInfo {
	private final long userId;
	private final String userName;
	private final List<String> roles;
	
	public AuthInfo(long userId, String userName, String ...roles) {
		this(userId, userName, Arrays.asList(roles));
	}
	
	public AuthInfo(long userId, String userName, List<String> roles) {
		this.userId = userId;
		this.userName = userName;
		this.roles = ListUtils.unmodifiableList(roles);		
	}
	
	public long getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public List<String> getRoles() {
		return roles;
	}
}
