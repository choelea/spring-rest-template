package tech.icoding.springrest.data;

import java.util.List;

public class UserInfo {

	private Integer id;

	private String username;

	private String name;

	private String role;

	private List<String> permissions;

	
	public UserInfo(Integer id, String username, String name, String role, List<String> permissions) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.role = role;
		this.permissions = permissions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
