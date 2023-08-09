package classes;

public class GuestBook {
	private int cid;			//게시글 번호
	private String nickname;	//닉네임(사용자 직접 설정)
	
	private String date;		//게시일자
	private String contents;	//글 내용
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
