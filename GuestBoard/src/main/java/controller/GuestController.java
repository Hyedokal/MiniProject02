package controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import classes.GuestBook;
import classes.GuestBookDAO;

/**
 * Servlet implementation class GuestController
 */
@WebServlet("/guestBook")
public class GuestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GuestBookDAO dao;
	private ServletContext ctx;
	private final String START_PAGE = "GuestBookList.jsp";
    public GuestController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new GuestBookDAO();
		ctx = getServletContext();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		dao = new GuestBookDAO();
		Method m;
		String view = null;
		
		//action파라미터 없이 접근한 경우 
		if(action == null) {
			action="listGuestBook";
		}
		try {
			m = this.getClass().getMethod(action, HttpServletRequest.class);
			view = (String) m.invoke(this, request);
			System.out.println(view);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			ctx.log("요청 action 없음!");
			request.setAttribute("error", "action 파라미터 오류");
			view = START_PAGE;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(view.startsWith("redirect:/")) {
			String rview = view.substring("redirect:/".length());
			response.sendRedirect(rview);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
	}

	public String addGuestBook(HttpServletRequest request) {
		try{
			GuestBook gb = new GuestBook();
			
			BeanUtils.populate(gb, request.getParameterMap());
			dao.addContents(gb);
		} catch(Exception e){
			e.printStackTrace();
			ctx.log("방명록 추가 과정에서 문제 발생!");
			request.setAttribute("error", "방명록이 정상적으로 등록되지 않음");
			return listGuestBook(request);
		}
		return "redirect:/guestBook?action=listGuestBook";
	}
	
	public String listGuestBook(HttpServletRequest request) {
		List<GuestBook> list;
		try {
			list = dao.getAll();
			request.setAttribute("guestList", list);
		} catch(Exception e) {
			e.printStackTrace();
			ctx.log("방명록 목록 생성 과정에서 문제 발생!");
			request.setAttribute("error", "방명록 목록이 정상적으로 처리되지 않았습니다.");
		}
		return "GuestBookList.jsp";
	}
	
	public String getGuestBook(HttpServletRequest request) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		try {
			GuestBook gb = dao.getGuest(cid);
			request.setAttribute("gb", gb);
		} catch (SQLException e) {
			e.printStackTrace();
			ctx.log("방명록을 가져오는 과정에서 문제 발생!");
			request.setAttribute("error", "방명록을 정상적으로 가져오지 못했습니다.");
		}
		return "GuestBookView.jsp";
	}
	
	public String deleteGuestBook(HttpServletRequest request) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		try {
			dao.delGuest(cid);
		} catch(SQLException e) {
			e.printStackTrace();
			ctx.log("방명록 삭제 과정에서 문제 발생!");
			request.setAttribute("error", "방명록을 정상적으로 삭제하지 못했습니다!");
			return listGuestBook(request);
		}
		return "redirect:/guestBook?action=listGuestBook";
	}
}
















