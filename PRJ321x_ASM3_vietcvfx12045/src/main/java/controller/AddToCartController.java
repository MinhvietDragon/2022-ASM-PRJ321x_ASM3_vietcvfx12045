package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.compiler.NewlineReductionServletWriter;

import dao.ListProductDAO;
import model.Cart;
import model.Product;

/**
 * Servlet implementation class AddToCartController
 */
@WebServlet("/AddToCartController")
public class AddToCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartController() {
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
		try {
			//request.getSession(true).invalidate(); //-> Làm mất hiệu lực session cũ
			//Sử dụng session có sẵn (nếu có) request.getSession(true); | Tạo session mới (nếu chưa có)
			HttpSession session = request.getSession(true);
			
			String action = request.getParameter("action");
			String imageId = request.getParameter("imageId"); //lay duoc Id cua san pham
			
			if (action != null && action.equalsIgnoreCase("addToCart")) {
				int id = Integer.parseInt(imageId); //Vì imageId đang ở dạng String nên cần parseInt để lấy id gốc thật
				
				//Tạo mới session cart nếu chưa có (Nếu có thì đã lấy ở phía trên đầu)
				if (session.getAttribute("cart") == null) { 
					session.setAttribute("cart", new Cart());
				}
				
				//Lấy Product từ CSDL bằng cách truy vấn theo id (trả về 1 sản phẩm Product) ("" + id: là cách để chuyển id thành String)
				Product p = new ListProductDAO().getProduct("" + id);
				
				//Tạo Product bằng Product vừa lấy được từ CSDL (nếu có imageId ở Database). 
				//Đặt number là 1 (Nếu trong list cart chưa có Product đó thì sẽ được add vào cart với number ban đầu là 1)	
				Product product = new Product(p.getId(),p.getName(), p.getDescription(), p.getPrice(), p.getSrc(), p.getType(), p.getBrand(), 1);
				
				//Lấy Cart ở session và thêm 1 sản phẩm (vừa lấy được) vào Cart items (giỏ hàng)
				Cart cart = (Cart) session.getAttribute("cart");
				cart.add(product);
				
				session.setAttribute("TongTien", cart.getAmount());
				session.setAttribute("messageCart", "");
				
			} else if (action != null && action.equalsIgnoreCase("updateNumber")) {
				int id = Integer.parseInt(imageId); //Vì imageId đang ở dạng String nên cần parseInt để lấy id gốc thật
				
				//Lấy số lượng đã điền (ở form)
				String soLuongSanPham = request.getParameter("soLuongSanPham");
				int soLuong = Integer.parseInt(soLuongSanPham);
			
				//Tạo giỏ hàng nếu chưa có. (Nếu có thì đã lấy được ở phía trên đầu hàm)
				if (session.getAttribute("cart") == null) { 
					session.setAttribute("cart", new Cart());
				}
				
				//Lấy sản phẩm từ CSDL (với id truyền vào)
				Product p = new ListProductDAO().getProduct("" + id);
				Product product = new Product(p.getId(),p.getName(), p.getDescription(), p.getPrice(), p.getSrc(), p.getType(), p.getBrand());
				
				//Lấy cart từ session để thực hiện cập nhật số lượng, cho sản phẩm với id đang thực thi, và số lượng truyền vào
				Cart cartUpdate = (Cart) session.getAttribute("cart");
				cartUpdate.update(product, soLuong);
				
				session.setAttribute("TongTien", cartUpdate.getAmount());
				session.setAttribute("messageCart", "");
				
			} else if (action != null && action.equalsIgnoreCase("delete")) {
				int id = Integer.parseInt(imageId); //Vì imageId đang ở dạng String nên cần parseInt để lấy id gốc thật
				
				//Lấy cart ở session và xoá Product khỏi list items (với id được gửi lên)
				Cart cart = (Cart) session.getAttribute("cart");
				cart.remove(id);
				
				//Cập nhật tổng số tiền, thông báo
				session.setAttribute("TongTien", cart.getAmount());
				session.setAttribute("messageCart", "");
			
			//Hàm trừ số lượng trong giỏ hàng (dùng tạm)	
			} else if (action != null && action.equalsIgnoreCase("minusProduct")) {
				int id = Integer.parseInt(imageId); //Vì imageId đang ở dạng String nên cần parseInt để lấy id gốc thật
				
				//(Lấy session cart nếu đã có sẵn và) tạo mới nếu chưa có
				if (session.getAttribute("cart") == null) { 
					session.setAttribute("cart", new Cart());
				}
				
				//Hàm ListProductDAO().getProduct("" + id): Truy vấn trong SQL 1 sản phẩm theo id và trả về 1 sản phẩm Product để sử dụng. ("" + id: là cách để chuyển id thành String)
				Product p = new ListProductDAO().getProduct("" + id);
				//Tạo Product bằng chính nó ở ListProductDAO.java (nếu có imageId ở Database). 
				//Đặt number là 1 (Nếu trong list cart chưa có Product đó thì sẽ được add vào cart với number ban đầu là 1)	
				Product product = new Product(p.getId(),p.getName(), p.getDescription(), p.getPrice(), p.getSrc(), p.getType(), p.getBrand(), 1);
				
				//Lấy Cart ở session và thêm 1 sản phẩm (vừa lấy được) vào Cart items (giỏ hàng)
				Cart cart = (Cart) session.getAttribute("cart");
				cart.minus(product);
				
				session.setAttribute("TongTien", cart.getAmount());
				session.setAttribute("messageCart", "");
			
			//Làm mới lại giỏ hàng	
			} else if (action != null && action.equalsIgnoreCase("resetCart")) {
				
				//Đặt giỏ hàng mới vào session (thay cho giỏ hàng trước đó)
				Cart newCart = new Cart();
				session.setAttribute("cart", newCart);
				session.setAttribute("TongTien", newCart.getAmount());
				session.setAttribute("messageCart", "");
				
				/*
				 * Cách khác, hủy một session: 
				 * (Để cho biết chứ trong trường hợp này nếu dùng có thể sẽ tạo null và không sử dụng được trang khác)
				 * ( HttpSession session = request.getSession(); )
				 * session.removeAttribute(“keyName”);
				 */
			}
			
			//Cuối cùng đều chuyển chạy lại đến cart.jsp
			response.sendRedirect("cart.jsp");
			
		} catch (Exception ex) {
			response.getWriter().println(ex);
		}
		
	}

}
