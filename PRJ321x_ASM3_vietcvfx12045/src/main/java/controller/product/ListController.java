package controller.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AccountUser;
import dao.ListProductDAO;
import model.Product;
import model.ProductOrders;

/**
 * Servlet implementation class ListController
 */
@WebServlet("/ListController")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListController() {
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
		//1.Gọi session, lấy tài khoản từ session lưu tài khoản "member"
		HttpSession session = request.getSession(true);
		AccountUser userLoginAccount = (AccountUser) session.getAttribute("member");
		
		//2.1 Lấy tên tài khoản đăng nhập, trả quay lại cart.jsp và thông báo nếu chưa đăng nhập
		String userLogin = "";
		if(userLoginAccount == null) {
			session.setAttribute("messageCart", "Vui lòng đăng nhập để xem lại hàng đã mua!");
			response.sendRedirect("cart.jsp");
			return;
		//2.2 Nếu đã đăng nhập thì lấy để gọi list sản phẩm Product đã mua từ CSDL (theo cách truy vấn riêng)
		} else {
			userLogin = userLoginAccount.getUser();
		}
				
		try {
			List<ProductOrders> listFromSQL = new ListProductDAO().search(userLogin);
			session.setAttribute("ListHangDaMua", listFromSQL);
			
			RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
			rd.forward(request, response);
			
		} catch (Exception ex) {
			//Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, ex);
			response.getWriter().println(ex);
			System.out.println("Có vấn đề xảy ra khi dùng tên tài khoản đăng nhập để lấy danh sách sản phẩm đã mua ! Trang ListController, hàm ListProductDAO");
		}
	}
	

}
