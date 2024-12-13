package bookshop.example;

public class Book {
	private int no; // 책번호
	private String title; // 책제목
	private String author; // 작가
	private int stateCode; // 상태코드(대여유무 : 0은 대여중, 1은 재고있음)
	
	
	public int getNo() {
		return no;
	}

	Book(int no, String title, String author) {
		this.no = no;
		this.title = title;
		this.author = author;
		stateCode = 1;
	}
	
	void rent() {
		if(stateCode == 0) {
			System.out.println(title + "이 이미 대여중입니다.");
			return;
		}
		
		stateCode = 0;
		System.out.println(title + "이(가) 대여 됐습니다.\n" );
	}
	
	void print() {
		System.out.println("책제목:" + title + ", 작가:" + author + ", 대여유무:" + (stateCode == 0 ? "대여중" : "재고있음"));
	}
}
