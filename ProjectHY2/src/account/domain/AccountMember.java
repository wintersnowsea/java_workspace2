package account.domain;

//회원관리 DTO
public class AccountMember {
	private int accountmember_idx;
	private String id;
	private String pass;
	private String email;
	private String regdate;
	
	public int getAccountmember_idx() {
		return accountmember_idx;
	}
	public void setAccountmember_idx(int accountmember_idx) {
		this.accountmember_idx = accountmember_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
}
