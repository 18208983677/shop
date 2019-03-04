package xiong.shop.service;


import xiong.shop.entity.User;
import xiong.shop.service.ex.InsertException;
import xiong.shop.service.ex.ServiceException;
import xiong.shop.service.ex.UserConflictException;

public interface IUserService {
	/**
	 * 账号注册
	 * @param user 用户信息
	 * @param yzm	验证码
	 * @param session  
	 * @throws UserConflictException
	 * @throws InsertException
	 */
	void reg(User user) throws ServiceException;
}
