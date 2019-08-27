package marcWeiss.sTracker.entity.webEntity;

import java.util.List;

public class WebUser {

	String	username;
	boolean enabled;
	List<String> authorities;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "WebUser [username=" + username + ", enabled=" + enabled + ", Authorities=" + authorities + "]";
	}
	
	
	
	
}
