package xiong.shop.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xiong.shop.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTestCase {
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void addnew() {
		User user = new User();
		user.setUsername("root");
		user.setPassword("1234");
		Integer rows = mapper.addnew(user);
		System.err.println("rows=" + rows);
	}
	@Test
	public void findByUsername() {
		String username = "root";
		User user = mapper.findByUsername(username);
		System.err.println(user);
	}
	@Test
	public void findUidByPhone() {
		String phone="18208983677";
		Integer result=mapper.findUidByPhone(phone);
		System.out.println(result);
	}
}
