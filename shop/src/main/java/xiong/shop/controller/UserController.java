package xiong.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xiong.shop.entity.User;
import xiong.shop.service.IUserService;
import xiong.shop.util.ResponseResult;

/**
 * 处理用户数据请求的控制器类
 */
@RestController
@RequestMapping("user")
public class UserController
	extends BaseController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("reg")
	public ResponseResult<Void> handleReg(User user) {
		userService.reg(user);
		return new ResponseResult<Void>(SUCCESS);
	}


}
