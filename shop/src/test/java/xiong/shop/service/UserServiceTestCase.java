package xiong.shop.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xiong.shop.entity.User;
import xiong.shop.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTestCase {
	@Autowired
	private IUserService service;
	
	@Test
	public void reg() {
		User user=new User();
		String phone="18208983676";
		String username="18208983677";
		String password="123456";
		user.setUsername(username);
		user.setPhone(phone);
		user.setPassword(password);
		try {
			service.reg(user);
		}catch(ServiceException e){
			System.out.println(e.getMessage());
		}
		}
}
