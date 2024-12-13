package bookmall.vo;

public class BookVo {
	private int no;
	private String title;
	private int price;
	private int categoryNo;
	
	public BookVo(String title, int price) {
		this.title = title;
		this.price = price;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public int getPrice() {
		return price;
	}

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	
}
