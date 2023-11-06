package com.sns.user.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;

@Service
public class UserBO {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity getUserEntityById(int userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByloginId(loginId);
	}
	
	public UserEntity getUserEntityByLoginIdAndPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}
	
	public List<UserEntity> getUserList() {
		return userRepository.findAll();
	}
	
	public Integer addUser(String loginId, String password, String name, String email) {
		UserEntity userEntity =  userRepository.save(
				UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
		
		return userEntity == null ? null : userEntity.getId();
	}
}
