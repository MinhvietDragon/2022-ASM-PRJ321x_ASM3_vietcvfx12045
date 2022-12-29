/*
 * Lưu thông tin sản phẩm trong giỏ hàng vào data source
 */
package dao;

import java.sql.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import context.DBContext;
import model.Cart;
import model.Orders;
import model.Product;

public class OrdersDAO {
	
	public OrdersDAO () {}
	
	public void insertOrder(Orders orders, Cart cart) throws Exception {
		Connection connection = new DBContext().getConnection();
		try {
			/* 1.Truy vấn lấy order_id lớn nhất (Tại bảng Orders, trước khi chèn dữ liệu vào Orders tại phần 2).
			 * Mục đích để điền đúng thứ tự tiếp theo cho order_id (trong bảng Orders_details). Vì tại Orders là tự động tăng lên 1
			 * id cần lấy ra để gán trong phần 3 (vòng lặp) vào bảng Orders_detail, vì một order_id có thể có nhiều sản phẩm trong đơn hàng
			 * Chú ý: a. Cần có một order_id đầu tiên tại bảng Orders (để get) nếu để order_id tại Orders là tự động tăng (identity)
			 * 		  b. Có thể không cần set số tự động trong bảng Orders, mà sẽ chèn order_id tạo tại đây vào Orders. 
			 * 			 Hoặc mở chức năng tự gán bằng set identity_insert orders on (kết thúc lệnh là off) nếu đã cài đặt tự động order_id tại Orders
			 */
			String sql1 = "Select top 1 order_id from Orders order by order_id DESC"; //Lấy ra hàng dữ liệu có số order_id lớn nhất
			PreparedStatement stmt1 = connection.prepareStatement(sql1); //Chuẩn bị câu lệnh 1
			ResultSet rs1 = stmt1.executeQuery(); //thực thi, trả về bảng tạm
			
			int id = 0; 	//Tạo tên id mới mục đích dùng để gán order_id lớn nhất hiện tại (Tại bảng Orders)
			if (rs1.next()) { 	//Kiểm tra có dữ liệu với câu truy vấn phía trên hay không
				id = rs1.getInt("order_id"); 	//nếu có: set id bằng order_id lớn nhất hiện tại (theo sql1)
			}
			id = id +1; //Tăng id lên 1, như vậy khi không có rs1.next() thì vẫn có id = 1, đây cũng là order_id của Orders sau khi thực hiện xong phần 2 (nếu tự động tăng)
			
			/* 
			 * 2.Thực hiện chèn dữ liệu vào bảng Orders (8 / 8 cột dữ liệu), có thể bỏ qua order_id nếu để tự động tăng
			 */
			String sql2 = "set identity_insert orders on " //Phải thêm dấu cách cho đúng cấu trúc lệnh
						+ "insert into Orders (user_mail, order_id, order_status, order_date, order_discount_code, order_address, order_phone, user_name) VALUES (?,?,?,?,?,?,?,?) "
						+ "set identity_insert orders off";
				
			PreparedStatement stmt2 = connection.prepareStatement(sql2); //Chuẩn bị câu lệnh 2
			
			//Bắt đầu set các tham số trong câu lệnh chuẩn bị (kiểu dữ liệu phù hợp với dữ liệu trong bảng SQL)
			stmt2.setString(1, orders.getUserLogin());
			stmt2.setInt(2, id);
			stmt2.setInt(3, orders.getStatus());
			
			//Tạo hàm lấy ngày date hiện tại. Chỉ cần tạo được date thì dữ liệu trong SQL sẽ tự get đúng kiểu của nó
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			
			stmt2.setDate(4, date);
			stmt2.setString(5, orders.getDiscount());		
			stmt2.setString(6, orders.getAddress());
			stmt2.setString(7, orders.getPhoneNumber());
			stmt2.setString(8, orders.getName());
			
			//Thực thi câu lệnh và đóng luồng stmt2
			stmt2.executeUpdate(); 
			stmt2.close();
			
			/*
			 * 3. Truyền dữ liệu giỏ hàng cart vào bảng Orders_detail (Có cả cột order_id)
			 * 3.1 Lấy danh sách Product (sản phẩm) từ cart (đã truyền vào)		
			 */
			List<Product> productsInCart = cart.getItems();
			
			//3.2 Chèn vào bảng Orders_detail (Có 4 cột dữ liệu + 1 Tổng tiền theo sản phẩm (tự thêm). Show lên để nhìn cho dễ hiểu)
			String sql = "Insert into Orders_detail (order_id, product_id, amount_product, price_product, price_product_sum) Values (?, ?, ?, ?, ?)";
			PreparedStatement stmt3 = connection.prepareStatement(sql); //Chuẩn bị câu lệnh 3
				
				//Chạy vòng lặp lưu lần lượt hết danh sách productsInCart có trong cart vào CSDL
				for (Product productList : productsInCart) {
					stmt3.setInt(1, id); // id đã get ở phần 1
					stmt3.setInt(2, productList.getId()); //Là id của Product (sản phẩm)
					stmt3.setInt(3, productList.getNumber());
					stmt3.setFloat(4, productList.getPrice());
					stmt3.setFloat(5, productList.getNumber()*productList.getPrice());
					
					stmt3.executeUpdate(); //Thực thi câu lệnh
				}
				
				stmt3.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Có vấn đề xảy ra khi ghi vào CSDL tại OrdersDAO");
		}
	}
}


//String sql2 = "set identity_insert orders on" //Mở chức năng điền số tự động tiếp theo (với những cột dữ liệu đã cài đặt tính năng đó)
//+ "insert into orders (user_mail, order_id, order_status, order_date, order_discount_code, order_address, order_phone) VALUES (?,?,?,?,?,?,?)"
//+ "set identity_insert orders off";