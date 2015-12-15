package treenote.domain;

public class User {
	private int userNo;
	private int editTreeNo;
	private String email;
	private String password;
	private String name;
	private String phone;
	private String photo;
	private String snsUser;
	private int requestStatus;
	private int activity;
	private String role;

	

	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", editTreeNo=" + editTreeNo + ", email=" + email + ", password=" + password
				+ ", name=" + name + ", phone=" + phone + ", photo=" + photo + ", snsUser=" + snsUser
				+ ", requestStatus=" + requestStatus + ", activity=" + activity + ", role=" + role + "]";
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getEditTreeNo() {
		return editTreeNo;
	}

	public void setEditTreeNo(int editTreeNo) {
		this.editTreeNo = editTreeNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSnsUser() {
		return snsUser;
	}

	public void setSnsUser(String snsUser) {
		this.snsUser = snsUser;
	}

	

	public int getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}