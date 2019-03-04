package xiong.shop.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import xiong.shop.entity.User;
import xiong.shop.mapper.UserMapper;
import xiong.shop.service.IUserService;
import xiong.shop.service.ex.FormaErrorException;
import xiong.shop.service.ex.InsertException;
import xiong.shop.service.ex.PhoneUsedException;
import xiong.shop.service.ex.ServiceException;
import xiong.shop.service.ex.UserConflictException;
import xiong.shop.service.ex.YZMErrorException;

@Service
public class UserServiceImpl
implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public void reg(User user) throws ServiceException {
		//获得注册电话和密码
				String phone=user.getPhone();
				String password=user.getPassword();
		if (!passwordFormat(password)){
			throw new FormaErrorException("密码格式错误");
		}
		/*
		 * if (!yzmVerify(yzm, session)){ throw new YZMErrorException("验证码错误"); }
		 */
		//对电话号码是否重复使用进行判断
		phoneUsed(phone);
		// 根据user.getUsername()获取用户名匹配的数据
		String username = user.getUsername();
		User data = findByUsername(username);
		// 检查数据是否为null
		if (data == null) {
			// 是：为null，用户名未被占用，则应该补全参数中的属性值
			// - 1. 密码加密，并封装
			String salt = UUID.randomUUID().toString().toUpperCase();
			String md5Password
			= getMd5Password(user.getPassword(), salt);
			user.setPassword(md5Password);
			// - 2. 封装salt
			user.setSalt(salt);
			// - 3. 封装isDelete，固定为0
			user.setIsDelete(0);
			// - 4. 封装4项日志数据
			Date now = new Date();
			user.setCreatedTime(now);
			user.setModifiedTime(now);
			user.setCreatedUser(username);
			user.setModifiedUser(username);
			// - 执行注册：addnew(user)
			addnew(user);
		} else {
			// 否：非null，用户名被占用，则抛出UserConflictException
			throw new UserConflictException(
					"注册失败！您尝试注册的用户名(" + username + ")已经被占用！");
		}
	}

	private void phoneUsed(String phone) throws ServiceException {
		if (!phoneFormat(phone)){
			throw new FormaErrorException("手机号格式不正确");
		}
		Integer row = findUidByPhone(phone);
		if (row != null){
			throw new PhoneUsedException("手机号已被注册");
		}
		
	}
	
	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	private void addnew(User user) {
		Integer rows = userMapper.addnew(user);
		if (rows != 1) {
			throw new InsertException("增加用户数据时出现未知错误！请联系系统管理员！");
		}
	}

	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	/**
	 * 使用MD5算法执行密码加密
	 * @param password 密码原文
	 * @param salt 盐值
	 * @return 加密后的密文
	 */
	private String getMd5Password(
			String password, String salt) {
		// 加密规则
		// 1. 将盐值添加到原文的左侧
		// 2. 执行加密10次
		String str = salt + password;
		for (int i = 0; i < 10; i++) {
			str = DigestUtils
					.md5DigestAsHex(str.getBytes())
					.toUpperCase();
		}
		return str;
	}
	/**
	 * 密码格式验证
	 * @param password
	 * @return
	 */
	private boolean passwordFormat(String password){
		String regex = "\\w{6,20}";
		boolean b = password.matches(regex);
		return b;
		
	}
	/**
	 * 手机号格式验证
	 * @param phone
	 * @return
	 */
	private boolean phoneFormat(String phone){
		String regex = "\\d{11}";
		boolean b = phone.matches(regex);
		return b;
	}
	/**
	 * 判断验证码是否正确
	 * @param yzm
	 * @param session
	 * @return
	 */
	private boolean yzmVerify(Integer yzm, HttpSession session){
		Integer yzmT = (Integer)session.getAttribute("yzm");
		System.out.println(yzm+"   "+yzmT);
		return yzm.equals(yzmT);
		
	}
	/**
	 * 根据号码查询用户
	 * @param phone
	 * @return
	 */
	private Integer findUidByPhone(String phone) {
		return userMapper.findUidByPhone(phone);
	}
}
