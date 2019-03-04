package xiong.shop.mapper;

import xiong.shop.entity.User;

/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {

	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer addnew(User user);

	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUsername(String username);
	/**
	 * 根据电话查找对应用户uid
	 * @param phone 电话号码
	 * @return	返回对应的用户uid，无则返回null
	 */
	Integer findUidByPhone(String phone);
}
