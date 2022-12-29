package model;

import java.util.ArrayList;
import java.util.List;

//Đối tượng lớp Cart là một danh sách các Product (sản phẩm), mỗi Product này đã có đủ thông tin của sản phẩm
public class Cart {
	private List<Product> items;
	
	public Cart () {
		items = new ArrayList<>();
	}
	
	public void add (Product productAdd) {
		//1.Nếu Product được add (truyền vào) đã có tồn tại trong list items của Cart (trùng id với Product trong items) thì tăng số lượng của Product đó trong List lên 1
		//Vòng lặp for cải tiến với cấu trúc: for ((Kiểu dữ liệu) (tên gán trả về mỗi vòng) : (tên mảng, list, danh sách ... được sử dụng)){}
		
		//Chạy vòng hết list items để tìm, kiểm tra xem có id truyền vào trùng với id trong cart
		for(Product productList : items) {
			if (productAdd.getId() == productList.getId()) {
				productList.setNumber(productList.getNumber() + 1);
				return;
			}
		}
		
		//2.Nếu Product truyền vào chưa có trong list items, thì dùng hàm add của ArrayList thêm Product vào một cách bình thường
		items.add(productAdd);
	}
	
	public void update (Product productAdd, int number) {
		/*
		 * 1.Nếu Product được update (truyền vào) đã có tồn tại trong list items của Cart (trùng id với Product trong items) 
		 * thì cập nhật số lượng của sản phẩm đó ở trong List bằng số lượng truyền vào (chính là số lượng đã điền ở form)
		 * Thực ra ở hàm này thì sản phẩm đã chắc chắn có trong cart, vì ở trong cart mới có ô điền số lượng và cập nhật cho mỗi hàng sản phẩm
		 * Vòng lặp for cải tiến (chuyên dành cho mảng, List, ArrayList): for ([Kiểu dữ liệu] [tên dữ liệu của mỗi vòng] : [tên mảng, list, ArrayList ... được sử dụng]){}
		 */
		
		//Chạy vòng hết list items để tìm, kiểm tra xem có id truyền vào trùng với id trong cart
		for(Product productList : items) {
			if (productAdd.getId() == productList.getId()) {
				productList.setNumber(number);
				return;
			}
		}
		
		//2.Nếu Product truyền vào chưa có trong list items, thì dùng hàm add của ArrayList thêm Product vào một cách bình thường
		items.add(productAdd);
	}
	
	public void remove (int idRemove) {
		for (Product productList: items) {
			if (productList.getId() == idRemove) {
				//Sử dụng hàm remove của List, xoá Product trong list
				items.remove(productList);
				return;
			}
		}
	}
	
	public double getAmount() {
		double s = 0;
		for (Product x : items) {
			s += x.getPrice() * x.getNumber();
		}
		return Math.round(s*100.0) / 100.0;
	}
	/*
	 * Trả về danh sách của Cart (List Product items)
	 */
	public List<Product> getItems (){
		return items;
	}
	
	/*
	 * Hàm trừ từng số 1 (số lượng trong cart)
	 */
	public void minus (Product productMinus) {
		//1.Nếu Product được add (truyền vào) đã có tồn tại trong list items của Cart (trùng id với Product trong items) thì giảm số lượng của Product đó trong List đi 1
		for(Product productList : items) {
			//Nếu số lượng của sản phẩm có id đó <= 1 thì dừng hành động
			if (productMinus.getId() == productList.getId() && productList.getNumber() <= 1) {
				return;
			//Nếu số lượng > 1 thì trừ từng lần 1
			} else if (productMinus.getId() == productList.getId() && productList.getNumber() > 1) {
				productList.setNumber(productList.getNumber() - 1);
				return;
			}
		}		
	}
}
