package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller") //Đặt tên gọi servlet
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");	
			
		//Tạo bản đồ hành động thông qua tham số action, tương đương với else if của action nhưng dạng Map này show ra dễ nhìn hơn, gọn hơn nếu nhiều action
		Map <String, String> actionMap = new HashMap<>();
		actionMap.put("gotophone", "/phone.jsp");
		actionMap.put("imageController", "/image.jsp");
		actionMap.put("home", "/home.jsp");
		
		if(action == null || !actionMap.containsKey(action)) {
			action = "home";
		}
		
		//Vì chuyển tiếp nên tham số URL đã click tại home vẫn được giữ nguyên và sử dụng tại trang được chuyển tiếp
		request.getRequestDispatcher(actionMap.get(action)).forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
