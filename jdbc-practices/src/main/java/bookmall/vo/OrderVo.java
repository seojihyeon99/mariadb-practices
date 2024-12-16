package bookmall.vo;

public class OrderVo {
	private int no;
	private String number;
//	private String orderer; // 주문자(이름/이메일) => '비회원주문'을 고려하여 따로 저장
	private String status;
	private int payment;
	private String shipping;
	private int userNo;
	
	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
//	public String getOrderer() {
//		return orderer;
//	}
//
//	public void setOrderer(String orderer) {
//		this.orderer = orderer;
//	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getPayment() {
		return payment;
	}
	
	public void setPayment(int payment) {
		this.payment = payment;
	}
	
	public String getShipping() {
		return shipping;
	}
	
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	
	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
}
