package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.catalina.tribes.membership.cloud.CloudMembershipProvider;

import bean.Account;
import bean.AccountUser;
import bean.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Tự tạo thêm DataSource, dùng để cấu hình JNDI để gọi connection
		private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// Cấu hình cho hàm Init cho kết nối của JNDI
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			try {
				InitialContext initContext = new InitialContext();
				Context env = (Context) initContext.lookup("java:comp/env");

				// ds la thuoc tinh DataSource khai bao phia tren
				ds = (DataSource) env.lookup("jdbc/ShoppingDB");
			} catch (NamingException e) {
				throw new ServletException();
			}
		}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Cài đặt ngôn ngữ hiển thị
		response.setContentType("text/html; charset = UTF-8");
		request.setCharacterEncoding("utf-8");
				
		// Xử lý khi mới truy cập vào
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		
		// Gán giá trị ban đầu cho các thông số
		request.setAttribute("email", "");
		request.setAttribute("password", "");
		request.setAttribute("message", "Giá trị ban đầu của message");

		// Xử lý điều hướng trang với các trường hợp của action
		if (action == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		} else if (action.equals("gotologin")) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		} else if (action.equals("createAccount")) {
			request.setAttribute("repeatpassword", "");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			
		} else {
			out.println("Không có action để thực hiện!");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Sử dụng hàm processRequest		
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset = UTF-8");
		request.setCharacterEncoding("utf-8");
		
		//I. Lay hanh dong action cua form tu login.jsp hoac register.jsp de dieu huong va su dung
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();

		if (action == null) {
			out.println("Action cua Form null!");
			return;
		}

		Connection connection = null;
		
		//Gọi connection với ds (DataSource) khi đã cấu hình JNDI
		try {
			connection = ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Tạo account mới với connection vừa get được
		Account account = new Account(connection);
		
		//II. Lay email va password tu form de so sanh voi truy van den SQL, nick admin, khi đăng nhập
		if (action.equals("dologin")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			//Tạo session, dùng session cũ nếu có
			HttpSession session = request.getSession(true);
			
			User user = new User(email, password); //Tạo user để lưu và kiểm tra, đồng thời cũng tự bảo mật, cũng có thể dùng đối tượng khác tuỳ người lập trình
			
			// Lưu email, password vao request de lay su dung tai trang khac bat ky (điền sẵn nhập lại form khi nhập sai)
			request.setAttribute("email", email);
			request.setAttribute("password", password);
			
			/*
			 * 1.Đăng nhập với nick admin lưu ở web.xml, chuyển đến trang của admin
			 * Lay tham so tai  web.xml cua nick admin
			 */
			String adminUser = getServletContext().getInitParameter("username"); //Lưu ý tạo super.init(config) ở hàm init
			String adminPassword = getServletContext().getInitParameter("password");
			
			//Kiem tra so sanh mat khau cua account (tu form) voi web.xml
			if (email != null && user.getEmail().equalsIgnoreCase(adminUser) && user.getPassword().equals(adminPassword)) {
				//Tao session luu lai user để sử dụng trong trang index của admin			
				session.setAttribute("userAdmin", user.getEmail());
				//Chuyen den trang admin nếu khớp
				response.sendRedirect("admin/index.jsp");
				return;
			} 

			/*
			 * 2.Phần đăng nhập với nick ở CSDL (Nếu không phải admin), chuyển đến trang mua hàng
			 * Su dung ham truy van Database la login() cua Account den SQL va kiem tra so voi form
			 * Mục đích xem email và password truyền vào từ form có tồn tại trong csdl không
			 */
			try {
				// a.Kiểm tra tồn tại tài khoản trong SQL (với email va password nhập tại form) lưu để sử dụng và chuyển tiếp trang
				if (account.login(email, password)) {
					
					//b.Tạo AccountUser để LẤY THÔNG TIN TÀI KHOẢN TỪ CSDL (với email và password nhập tại form)
					AccountUser accUser = new AccountUser (email, password);
					//lưu AccountUser vào session để dùng, trong phiên làm việc của tài khoản
					session.setAttribute("member", accUser);
					
					//c.Tạo thông điệp chào mừng thành viên và lưu vào session (Tạo nút logout lưu cùng với userWelcome)
					String logoutString = "<form action='LogoutController' method='post' style='float: right;'>" + "<input type='submit' value='Logout'>"  +  "</form>";
					session.setAttribute("userWelcome", "Welcome: " + email + "!  " + logoutString);
					
					request.setAttribute("message", "email và password nằm trong Database!!!");
					
					//d.Sau khi đã có thông tin tài khoản đăng nhập, ta chuyển tiếp đến trang xem và chọn mua hàng
					request.getRequestDispatcher("/phone.jsp").forward(request, response);
				
				//e.Thông báo và quay lại login nếu email không có trong CSDL
				} else {
					request.setAttribute("message", "email hoặc mật khẩu không có trong Database!");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
				
			} catch (SQLException | ServletException | IOException e) {
				System.out.println("Đăng nhập tài khoản xảy ra vấn đề");
				e.printStackTrace();
			} 
			
			//f.Đóng connection
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		/*
		 * III.Phần tạo tài khoản mới, kiểm tra tồn tại và lưu vào CSDL
		 */
		} else if (action.equals("createAccount")) {
			//1.lấy các parameter được gửi lên từ form của register.jsp. Cũng nên đặt tên khác biệt tránh nhầm với các request, session khác
			String email = request.getParameter("email");
			String nameRegister = request.getParameter("nameForm"); //Tạo mẫu đặt tên tham số điển hình để hiểu bản chất cách truyền dữ liệu từ form đến điểm cuối là tạo tk mới
			String password = request.getParameter("password");
			String repeatpPassword = request.getParameter("repeatpassword");
			String addressRegister = request.getParameter("addressRegister");
			String phoneRegister = request.getParameter("phoneRegister");
			
			//2.Gán các giá trị vừa lấy được vào request để gọi ra sử dụng ở chính register.jsp (khi được chuyển tiếp)
			request.setAttribute("email", email);
			request.setAttribute("nameFromRequest", nameRegister);
			request.setAttribute("password", password);
			request.setAttribute("repeatpassword", repeatpPassword);
			request.setAttribute("message", "");
			request.setAttribute("addressRegister", addressRegister);
			request.setAttribute("phoneRegister", phoneRegister);
			
			//3.Nếu mật khẩu nhập 2 lần khác nhau thì quay lại trang register.jsp
			if(!password.equals(repeatpPassword)) {
				request.setAttribute("message", "Mật khẩu phải giống nhau");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			
			} else {
				
				User user = new User(email, password);
				
				//4.Kiểm tra cú pháp tài khoản. Nếu không vượt qua kiểm tra của user thì trả lại trang register.jsp, thông báo theo lỗi đã setup trong User.java
				if(!user.kiemtra()) {
					request.setAttribute("message", user.getMessage());
					request.getRequestDispatcher("/register.jsp").forward(request, response);
					return;
					
				} else {
					//5.Khi đã vượt qua kiểm tra của user, tiếp tục dùng kiểm tra của Account, nếu vượt qua thì tạo tài khoản lưu vào CSDL
					try {
						//Trường hợp tài khoản (email) đã tồn tại trong database, đăng ký lại
						if(account.exists(email)) {
							request.setAttribute("message", "Account với Email này đã tồn tại trong CSDL! Vui lòng chọn tên email khác !");
							request.getRequestDispatcher("/register.jsp").forward(request, response);
							return;
							
						} else {
							//Dùng hàm tạo account mới trong Account.java với email và password người dùng nhập (đã được get ở phần đầu) -> lưu vào CSDL
							//Video nói trong thực tế có thể nên lưu thêm vào session (phiên làm việc) để sử dụng lâu dài
							account.create(email, password, nameRegister, addressRegister, phoneRegister);
							request.setAttribute("message", "Tài khoản mới với email và password vừa nhập đã được thêm vào CSDL");
							request.getRequestDispatcher("/createsuccess.jsp").forward(request, response);
							return;
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		
		//Thông báo nếu không có aciton nào khớp với phía trên
		} else {
			out.println("Action cua Form khong hoat dong!");
			return;
		}
	}

}
