package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;

	/**
	 * 로그인 아이디 중복 확인 API
	 * 
	 * @param loginId
	 * @return
	 */
	// http://localhost:8080/user/is-duplicated-id
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(@RequestParam("loginId") String loginId) {

		UserEntity user = userBO.getUserEntityByLoginId(loginId);

		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if (user == null) {
			result.put("isDuplicated", false);
		} else {
			result.put("isDuplicated", true);
		}

		return result;
	}
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(@RequestParam("loginId") String loginId,
			@RequestParam("password") String password, @RequestParam("name") String name,
			@RequestParam("email") String email) {

		String hashedPassword = EncryptUtils.md5(password);

		Integer id = userBO.addUser(loginId, hashedPassword, name, email);

		Map<String, Object> result = new HashMap<>();
		if (id == null) {
			result.put("code", 500);
			result.put("errorMessage", "회원가입 실패");
		} else {
			result.put("code", 200);
			result.put("result", "success");

		}
		return result;
	}
	
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session) {
		
		String hashedPassword = EncryptUtils.md5(password);
		
		UserEntity user = userBO.getUserEntityByLoginIdAndPassword(loginId, hashedPassword);
	
		
		
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			session.setAttribute("userLoginId", user.getLoginId());
			result.put("code", 200);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "로그인 실패");
		}
		
		return result;
	}
}
