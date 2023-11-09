package com.sns.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	private PostBO postBO;

	@PostMapping("/create")
	public Map<String, Object> postCreate(@RequestParam(value = "content", required = false) String content,
			@RequestParam("file") MultipartFile file, HttpSession session) {

		int userId = (int) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");

		Integer id = postBO.addPost(userId, userLoginId, content, file);

		Map<String, Object> result = new HashMap<>();

		if (id == null) {
			result.put("code", 500);
			result.put("errorMessage", "글 등록에 실패");
		} else {
			result.put("code", 200);
			result.put("result", "success");

		}

		return result;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> postDelete(
			@RequestParam("postId") int postId) {
		
		Map<String, Object> result = new HashMap<>();
		
		postBO.deletePostById(postId);
		result.put("code", 200);
		result.put("result", "success");
		return result;
	}
}
