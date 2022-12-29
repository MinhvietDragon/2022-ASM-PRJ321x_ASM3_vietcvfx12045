package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class URLController
 */
public class URLController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public URLController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lấy sẵn dữ liệu từ database để sử dụng, chuyển tiếp đến các trang. (Hiện tại chỉ lấy dữ liệu của phone)
		
		
		
		//Lấy action và điều hướng trang
		String action = request.getParameter("action");
		
		String page = null;
		if(action == null) {
			page = "/home.jsp";
		} else {
			switch (action) {
			case "about" -> page = "/about.jsp";
			case "login" -> page = "/login.jsp";
			case "gotophone" -> page = "/phone.jsp";
			case "home" -> page = "/home.jsp";
			case "products" -> page = "/products.jsp";
			case "forgotpassword" -> page = "/forgotpassword.jsp";
			}
		}
		
		//Trang được chuyển hướng đến theo page theo từng trường hợp phía trên
		getServletContext().getRequestDispatcher(page).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
