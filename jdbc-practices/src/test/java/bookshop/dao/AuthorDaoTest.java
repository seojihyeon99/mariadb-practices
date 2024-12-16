package bookshop.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import bookshop.vo.AuthorVo;

public class AuthorDaoTest {
	
	private static AuthorVo vo = new AuthorVo();
	private static AuthorDao dao = new AuthorDao();
	
	@Test
	@Order(1)
	public void insertTest() {
		vo.setName("칼세이건");
		
		dao.insert(vo);
		
		assertNotNull(vo.getId());
	}
	
	// static : 클래스 전체에 대해 한 번만 실행
	// JUnit은 테스트 메서드마다 새로 인스턴스를 생성해 실행하는 방식이 기본이다. 따라서 테스트 메서드의 인스턴스는 테스트가 끝나면 사라질 수 있다.
	@AfterAll
	public static void cleanUp() {
		dao.deleteById(vo.getId());
	}
	
}
