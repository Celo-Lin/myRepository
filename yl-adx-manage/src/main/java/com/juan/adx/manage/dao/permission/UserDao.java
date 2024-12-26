package com.juan.adx.manage.dao.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.permission.UserMapper;
import com.juan.adx.model.entity.permission.User;
import com.juan.adx.model.form.manage.UserForm;


@Repository
public class UserDao{

	@Autowired
	private UserMapper userMapper;

	public User getUserByName(String name) {
		return this.userMapper.getUserByName(name);
	}

	public List<User> listUser(UserForm paramForm) {
		return this.userMapper.listUser(paramForm);
	}

	public User getUser(String id) {
		return this.userMapper.getUser(id);
	}

	public int updateUser(User user) {
		return this.userMapper.updateUser(user);
	}

	public int saveUser(User user) {
		return this.userMapper.saveUser(user);
	}

	public int deleteUser(String id) {
		return this.userMapper.deleteUser(id);
	}

	public int updatePassword(User user) {
		return this.userMapper.updatePassword(user);
	}

	public User getUserWithPassword(String id) {
		return this.userMapper.getUserWithPassword(id);
	}
	
}
