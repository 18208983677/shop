package xiong.shop.controller;
import xiong.shop.service.ex.InsertException;
import xiong.shop.service.ex.ServiceException;
import xiong.shop.service.ex.UserConflictException;
import xiong.shop.util.ResponseResult;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制器类的基类
 */
public class BaseController {
	/**
	 * 处理请求时，用于表示操作正确的代码
	 */
	public static final int SUCCESS = 200;
	
	/**
	 * 从Session中获取当前登录的用户的id
	 * @param session 
	 * @return 当前登录的用户的id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}
	
	@ExceptionHandler({ServiceException.class})
	public ResponseResult<Void>
		handleException(Exception e) {
		// 声明返回对象
		ResponseResult<Void> rr = new ResponseResult<Void>();
		// 在返回对象封装错误提示的文字
		rr.setMessage(e.getMessage());
		
		// 根据异常的不同，返回的错误代码也不同
		if (e instanceof UserConflictException) {
			// 400-用户名冲突
			rr.setState(400);
		}else if (e instanceof InsertException) {
			// 500-插入数据异常
			rr.setState(500);
		}
		
		// 返回
		return rr;
	}

}
