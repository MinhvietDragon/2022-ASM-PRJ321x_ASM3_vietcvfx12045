package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

public class Account {
	private Connection connection;
	
	//Khởi tạo Account với connection truyền vào, để sử dụng các hàm
	public Account(Connection connection) {
		this.connection = connection;
	}
	/*
	 * Kiểm tra email và password truyền vào xem có ở CSDL không?
	 * Hàm truy vấn dữ liệu từ database, kiểm tra so sánh với nhập từ form (truyền vào) và database
	 */
	public boolean login(String email, String password) throws SQLException {
		String sql = "select count(*) as count from Account where user_mail=? and password=?"; // Trả về duy nhất 1 giá trị là số hàng có email và password như truy vấn
		//Dùng connection được truyền vào Account, (hoặc có thể tự tạo DBcontext) để kết nối CSDL
		PreparedStatement stmt = connection.prepareStatement(sql); //Chuẩn bị câu lệnh
		
		stmt.setString(1, email);
		stmt.setString(2, password);
		
		ResultSet rs = stmt.executeQuery(); //Thực thi câu lệnh, nhận bảng tạm trả về
		
		int count = 0;		
		if(rs.next()) {
			count = rs.getInt("count"); 	//Lấy cột count vừa select ở phía trên, nếu tồn tại tài khoản thì count (duy nhất 1 giá trị) sẽ >=1 (Thường là 1)				
		}	
		
		rs.close();
		
		if(count == 0) {
			//Tài khoản không tồn tại trong CSDL
			return false;
		} else {
			//Tài khoản có tồn tại trong CSDL
			return true;
		}		
	}
	
	/* Kiểm tra sự tồn tại (exists) của email trong hệ thống Database
	 * Mã nguồn phương thức này giống như phương thức login
	 * Chỉ khác là không có tham số password, vì chỉ cần tìm tên email của account.
	 */
	public boolean exists (String email) throws SQLException {
		String sql = "select count(*) as count from Account where user_mail=?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		
		int count = 0;
		
		if(rs.next()) {
			count = rs.getInt("count"); //Lấy cột count vừa select ở phía trên
		}
		
		//Không có tài khoản trùng tên
		if (count == 0) {
			return false;
		//Có tk trùng tên
		} else {
			return true;
		}
		
	}
	
	public void create(String email, String password, String nameRegister, String addressRegister, String phoneRegister) throws SQLException {
		//Câu lệnh SQL chèn thêm dữ liệu vào bảng Account trong Dadabase
		//account_role: Số tự nghĩ ra để đánh dấu kiểu tài khoản. VD: số 1 là tài khoản quản trị, số 2 là khách đạt cấp độ vip khi mua nhiều ... khách 5 là khách thông thường
		String sql = "Insert into Account (user_mail, password, account_role, user_name, user_address, user_phone) values (?,?,?,?,?,?)";
		
		//Chuẩn bị câu lệnh
		PreparedStatement stmt = connection.prepareStatement(sql); 
		
		stmt.setString(1, email);
		stmt.setString(2, password);
		stmt.setInt(3, 5);
		stmt.setString(4, nameRegister);
		stmt.setString(5, addressRegister);
		stmt.setString(6, phoneRegister);
		
		//Thực hiện câu lệnh
		stmt.executeUpdate();
		
		//Đóng luồng
		stmt.close();
	}
	
}
