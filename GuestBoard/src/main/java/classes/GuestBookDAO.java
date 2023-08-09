package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestBookDAO {
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";
	
	//DB 연결을 가져오는 메서드. DBCP를 사용하는 것이 좋음
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "jwbook", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//게시글을 추가하는 메서드.
	public void addContents(GuestBook gb) throws Exception{
		Connection conn = open();
		String sql = "INSERT INTO guestbook (nickname, date, contents) VALUES (?, CURRENT_TIMESTAMP(0), ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn; pstmt){
			pstmt.setString(1, gb.getNickname());
			pstmt.setString(2, gb.getContents());
			pstmt.executeUpdate();
		}	
	}
	
	//getAll() 메서드 구현
	public List<GuestBook> getAll() throws Exception{
		Connection conn = open();
		List<GuestBook> guestList = new ArrayList<>();
		
		String sql = "SELECT cid, nickname, date, contents FROM guestbook";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try(conn; pstmt; rs){
			while(rs.next()) {
				GuestBook gb = new GuestBook();
				gb.setCid(rs.getInt("cid"));
				gb.setNickname(rs.getString("nickname"));
				gb.setDate(rs.getString("date"));
				gb.setContents(rs.getString("contents"));
				guestList.add(gb);
			}
		}
		return guestList;
	}
	
	//getGuest() 메서드 구현
	public GuestBook getGuest(int cid) throws SQLException{
		Connection conn = open();
		GuestBook gb = new GuestBook();
		String sql = "SELECT cid, nickname, date, contents FROM guestbook WHERE cid=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, cid);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		try(conn; pstmt){
			gb.setCid(rs.getInt("cid"));
			gb.setNickname(rs.getString("nickname"));
			gb.setDate(rs.getString("date"));
			gb.setContents(rs.getString("contents"));
		}
		return gb;
	}
	
	//delGuest() 메서드 구현
	//aid값에 해당하는 password값과 입력한 password값이 같아야만 삭제할 수 있도록 한다.
	public void delGuest(int aid) throws SQLException{
		Connection conn = open();
		String sql = "DELETE FROM guestbook WHERE cid=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn; pstmt;){
			pstmt.setInt(1, aid);
			if(pstmt.executeUpdate() == 0) {
				throw new SQLException("DB오류입니다.");
			}
		}
	}
}




