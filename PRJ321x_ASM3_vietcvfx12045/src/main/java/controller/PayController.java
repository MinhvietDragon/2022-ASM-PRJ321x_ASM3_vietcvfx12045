package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AccountUser;
import dao.OrdersDAO;
import model.Cart;
import model.Orders;
import model.Product;

/**
 * Servlet implementation class PayController
 */
@WebServlet("/PayController")
public class PayController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
		try {
			/*
			 * 1.Lấy session để lấy thông tin cart (giỏ hàng)
			 * Xử lý 3 trường hợp giỏ hàng trống, số lượng hàng = 0. Xử lý quay lại nếu không có số lượng nào, tránh trường hợp gửi dữ liệu vào Database khi đơn trống
			 */
			
			HttpSession session = request.getSession(true);
			
			//1.1 Nếu Cart là null thì quay lại để mời chọn hàng
			if(session.getAttribute("cart") == null) {
				session.setAttribute("messageCart", "Giỏ hàng trống! Vui lòng chọn hàng để mua! (1)");
				response.sendRedirect("cart.jsp");
				return;
			}
			
			//1.2 Lấy Cart (ở servlet) từ session (sau khi gửi từ form ở trang cart.jsp lên).
			Cart cartDetail = (Cart) session.getAttribute("cart");
			
			if (cartDetail == null) {
				session.setAttribute("messageCart", "Giỏ hàng trống! Vui lòng chọn hàng để mua! (2)");
				response.sendRedirect("cart.jsp");
				return;
				
			} else {
				//Nếu điều kiện đầy đủ thì set lại thông báo là trống
				session.setAttribute("messageCart", "");
			}
			
			
			//1.3 Đếm tổng số lượng hàng có trong list. Xử lý điều hướng theo kết quả của số lượng
			//lấy list Product từ cart
			List <Product> cartList = cartDetail.getItems();
			
			int productTotal = 0;	
			for (int i=0; i<cartList.size(); i++) {
					productTotal += 1;
			}
			
			if (productTotal == 0) {
				session.setAttribute("messageCart", "Vui lòng chọn hàng để mua! (3)");
				response.sendRedirect("cart.jsp");
			
			//Có thể không cần thêm else if, nhưng ở đây là thêm để tránh cả trường hợp số âm hi hữu xảy ra ở dự án khác
			} else if (productTotal >0) {
				
				session.setAttribute("messageCart", "");
				
				/*2.1 Lấy tên tài khoản đăng nhập (email login) để lưu vào CSDL. Mục đích khớp tk đăng nhập và khi mua hàng
				 *Cách 1 lấy userLogin -- Lấy thông tin tài khoản thành viên đã login ở session (đã set ở LoginController)
				 *Cách này có thể nói là đảm bảo userLogin tồn tại cùng gốc phiên session của session "member" (bớt cầu trung gian là đưa ra form rồi lại lấy lại từ form - cách 2)
				 */
				AccountUser userLoginAccount = (AccountUser) session.getAttribute("member");
				
				/*Xử lý trường hợp không dùng tk đăng nhập thì gán cho userLogin = "" hoặc một tên cụ thể. Nên đặt tên cụ thể để có thể select ở trong CSDL
				 *Nếu có dùng tk thì dùng hàm getUser() của AccountUser
				 *Hiện tại ở đây chỉ xử lý lấy mình userLogin từ session "member" mà không xử lý các tham số khác, vì muốn để chung phần tham số khác tại input của form. 
				 *(Trong thực tế khách hàng có thể cần sửa đổi địa chỉ nhận hàng, như mua hộ mua giúp ... hoặc thấy thông tin cần sửa đổi. Điều quan trọng là ở đây ta đảm bảo được đúng tên tài khoản)
				 *(Tạo một chút động ở đúng lúc đúng chỗ để cảm nhận từ phía người dùng thay vì fix cứng như trong lúc đăng ký. Cũng có thể làm cập nhật sửa đổi cho thông tin tài khoản) 
				 *chỉ đặc biệt xử lý userLogin vì liên quan đến tài khoản đã đăng nhập (trong bảng Account CSDL) sẽ lưu vào bảng Orders của CSDL. 
				 *(Mục đích xác định chính xác tài khoản đã nhập để lưu lại)
				 */
				String userLogin = "";
					if(userLoginAccount == null) {
						userLogin = "khach";
						//Có thể xử lý phần 2.2 TH1 không login tại đây
					} else {
						userLogin = userLoginAccount.getUser();
						//Có thể xử lý phần 2.2 TH2 có login tại đây (lấy từ AccountUser của session "member")
					}
				//Cách 2 lấy userLogin -- String userLogin = request.getParameter("userLogin"); -- Với điều kiện đã từng lấy và đặt user là 1 input của form ở form trong cart.jsp
				
				//2.2 Lấy thông tin khách hàng (đã nhập tại form của trang cart.jsp) và lưu vào đối tượng Order (cùng tên tài khoản đã login nếu có)
				String customerName = request.getParameter("customerName");
				String address = request.getParameter("address"); 
				String phoneNumber = request.getParameter("phoneNumber"); 
				String discount = request.getParameter("discount");
				
				//Tải lại trang và thông báo nếu chưa nhập username, address
				if(customerName == "" || address == "" || phoneNumber == "") {
					session.setAttribute("messageCart", "Vui lòng điền thông tin: Name, Address, Phone number");
					response.sendRedirect("cart.jsp");
					return;	//Thiếu return tại đây thì sẽ run cả phần sau (ghi vào CSDL) khi yêu cầu điền lại thông tin
				}
				
				//3.1 Lưu thông tin của khách hàng vào một đối tượng Orders (Phiên bản giản tiện)
				Orders yourOrders = new Orders(userLogin, customerName, 2, discount, address, phoneNumber, null); //tham số date null sẽ set cụ thể trong OrdersDAO sau
				
				//3.2 Dùng lớp OrdersDAO để truyền dữ liệu vào và thực thi lệnh lưu vào CSDL (Cả thông tin cart và thông tin khách hàng đã lấy được tại mục 2)
				OrdersDAO dao = new OrdersDAO();
				dao.insertOrder (yourOrders, cartDetail);
				
				//4.1 set lại thông tin khách hàng trong session để chuyển sang trang. (Có thể lưu Orders vào 1 tên trong session và lấy ra từ trang sau, ở đây là lưu từng tên một)
				//Lưu: session.setAttribute("userLogin", yourOrders.getUserLogin());
				session.setAttribute("nameFn", yourOrders.getName());
				session.setAttribute("addressFn", yourOrders.getAddress());
				session.setAttribute("phoneNumberFn", yourOrders.getPhoneNumber());
				session.setAttribute("discountFn", yourOrders.getDiscount());
				
				//4.2 Chuyển đến trang xác nhận đã đặt hàng thành công
				response.sendRedirect("index.jsp");
				
			}
			
		} catch (Exception e) {
			response.getWriter().println(e);
			e.printStackTrace();
		}
		
	}

}
