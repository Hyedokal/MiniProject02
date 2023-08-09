
# MiniProject02
================

JSP, Java Servlet을 이용하여 만든 방명록 앱입니다.
MVC 패턴을 적용하여, 컨트롤러(GuestController.java), 뷰(GuestList.jsp, GuestView.jsp), 모델(GuestBookDAO.java)을 각각 만들었습니다.
사용자가 이름과 내용을 적어 submit하면,
H2 Database에 만들어 놓은 guestbook 테이블에 번호(cid int primary key auto increment), 이름, 내용, 게시 일자(current_timestamp(0))가 저장되어 올라가는 방식으로
제작하였습니다.

보완할 점: 사용자가 글을 게시할 때 비밀번호도 입력받고, 삭제 버튼을 누르면 비밀번호 입력창이 나와서
            비밀번호가 DB에 저장된 것과 일치하면 삭제되고, 아니라면 redirect하는 기능 만들기
