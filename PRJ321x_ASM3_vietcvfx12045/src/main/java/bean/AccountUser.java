package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;

import context.DBContext;

public class AccountUser {
	private String user, password;
	private int role;
	private String name, address, phone;
	private int check;

	public AccountUser() {
	}

	public AccountUser(String user, String password, int role, String name, String address, String phone, int check) {
		this.user = user;
		this.password = password;
		this.role = role;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.check = check;
	}
	
	/*
	 * Tạo AccountUser đầy đủ thông tin từ CSDL với email và password được truyền vào
	 * Ở đây sự tồn tại của email và password là chắc chắn nhờ có các bài kiểm tra trước khi truyền vào đây
	 * như ở LoginController: if (account.login(email, password))
	 */
	public AccountUser (String email, String password) throws Exception {
		DBContext context = new DBContext();
		Connection connection = context.getConnection();
		
		String sql = "select * from Account where user_mail=? and password=?";
		//Dùng connection được truyền vào Account (Hàm khởi tạo), (hoặc có thể tự tạo DBcontext) để kết nối CSDL
		PreparedStatement stmt = connection.prepareStatement(sql); //Chuẩn bị câu lệnh
		
		stmt.setString(1, email);
		stmt.setString(2, password);
		
		ResultSet kq = stmt.executeQuery(); //Thực thi câu lệnh, nhận bảng tạm trả về
		
		/* Nếu tồn tại truy vấn (mà thực ra đã được kiểm tra bằng hàm login() của Account là chắc chắn tồn tại)
		 * Gán giá trị cho tài khoản AccountUser bằng dữ liệu trong CSDL
		 */
		if(kq.next()) {
			this.user = kq.getString("user_mail");
			this.password = kq.getString("password");
			this.role = kq.getInt("account_role");
			this.name = kq.getString("user_name");
			this.address = kq.getString("user_address");
			this.phone = kq.getString("user_phone");
			this.check = 1;
		}
		
		kq.close();
	}
	

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

}
