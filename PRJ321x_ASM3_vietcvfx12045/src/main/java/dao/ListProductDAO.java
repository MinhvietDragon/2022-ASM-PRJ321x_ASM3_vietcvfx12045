package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import context.DBContext;
import model.Product;
import model.ProductOrders;

public class ListProductDAO {
	
	/*
	 * Hàm lấy về một danh sách dữ liệu (nhiều cột tuỳ chọn) của sản phẩm đã mua (của một tài khoản được truyền vào (userLogin))
	 */
	public List<ProductOrders> search (String userLogin) throws Exception {
		DBContext context = new DBContext();
		Connection connection = context.getConnection();
		List<ProductOrders> danhSachProduct = new ArrayList<ProductOrders>();
		
		//1. Đếm số (hàng) sản phẩm có trong data. Khẳng định có dữ liệu hay không. Đồng thời cũng có thể lấy số cho vòng lặp nếu sử dụng
		String sql1 = "Select count (*) as dataCount " 
					+ "from Products P join Orders_detail Od on P.product_id = Od.product_id "
					+ "join Orders O on Od.order_id = O.order_id join Account A on O.user_mail = A.user_mail "
					+ "Where A.user_mail=?";
		
		//1.1 Truy vấn và nhận bảng tạm trả về
		PreparedStatement stmt1 = connection.prepareStatement(sql1);
		stmt1.setString(1, userLogin);
		ResultSet kq1 = stmt1.executeQuery(); //Trả về kết quả bảng tạm
		
		//1.2 Kiểm tra kết quả trả về có dữ liệu (số dataCount) nào không
		int count = 0;
		if(kq1.next()) {
			count = kq1.getInt("dataCount"); 	//Lấy cột dataCount vừa select ở phía trên, nếu tồn tại dữ liệu thì dataCount (duy nhất 1 giá trị) sẽ trả về đúng số hàng dữ liệu	
		}
		
		kq1.close();
		
		//2.1 Xử lý nếu không có dữ liệu (chưa từng mua)
		if (count == 0) {
			return danhSachProduct = null;
		}
		
		//2.2 Nếu có dữ liệu: Truy vấn gọi lấy danh sách sản phẩm từ data với tài khoản userLogin truyền vào
		String sql2 = "Select Od.order_id, Od.product_id, Od.amount_product, Od.price_product, Od.price_product_sum, P.product_name, P.product_img_source, O.order_date "
					+ "from Products P join Orders_detail Od on P.product_id = Od.product_id "
					+ "join Orders O on Od.order_id = O.order_id join Account A on O.user_mail = A.user_mail "
					+ "Where A.user_mail=? "
					+ "order by O.order_date DESC, O.order_id DESC, Od.amount_product ASC";
		
		PreparedStatement stmt2 = connection.prepareStatement(sql2);//Chuẩn bị lệnh gọi
		stmt2.setString(1, userLogin);
		ResultSet kq2 = stmt2.executeQuery(); //Trả về kết quả bảng tạm
		
		//2.3 Tạo lưu từng sản phẩm trong bảng tạm kq2 vào đối tượng ProductOrders và add vào List bằng vòng lặp while, với điều kiện mỗi lần kq2.next() true
		while (kq2.next()) { 
			int id = kq2.getInt("order_id");
			int productId = kq2.getInt("product_id");
			int amountProduct = kq2.getInt("amount_product");
			float priceProduct = kq2.getFloat("price_product");
			float priceProductSum = kq2.getFloat("price_product_sum");
			String productName = kq2.getString("product_name");
			String producImgSource = kq2.getString("product_img_source");
			Date orderDate = kq2.getDate("order_date");			
				
			ProductOrders tungSanPhamTuCSDL = new ProductOrders(id, productId, amountProduct, priceProduct, priceProductSum, productName, producImgSource, orderDate);
			danhSachProduct.add(tungSanPhamTuCSDL);
		}
		
		return danhSachProduct;
	}
	
	/*
	 * Hàm truy vấn lấy về thông tin một Product trong CSDL từ một id truyền vào
	 */
	public Product getProduct (String imageId) throws Exception {
		DBContext context = new DBContext();
		Connection connection = context.getConnection();
		
		String sql = "select * from Products where product_id=?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setString(1, imageId);
		
		ResultSet rs = stmt.executeQuery();	
		
		Product product = null;
		
		//Nếu tồn tại Product với imageId, truy vấn có ít nhất 1 kết quả trả về (rs.next() == true), thì gán product với product truy vấn được (có imageId)
		if(rs.next()) {
			int id = rs.getInt("product_id");
			String name = rs.getString("product_name");
			String des = rs.getString("product_des");
			float price = rs.getFloat("product_price");
			String source = rs.getString("product_img_source");
			String type = rs.getString("product_type");
			String brand = rs.getString("product_brand");
			product = new Product(id, name, des, price, source, type, brand); //Chưa đặt số lượng
		}
		
		rs.close();
		stmt.close();
		connection.close();
		
		//Trả về product với đúng thông tin đã truy vấn được (với imageId)
		return product;
		//Nếu không tồn tại Product với imageId được add (truyền vào) thì product trả về là null
	}
}
