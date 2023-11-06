package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.comment.bo.CommentBO;

@RequestMapping("/comment")
@RestController
public class CommentRestController {

	@Autowired
	private CommentBO commentBO;
	
	@PostMapping("/create")
	public Map<String, Object> commentCreate(
			@RequestParam("content") String content,
			@RequestParam("postId") int postId,
			HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		
		commentBO.addComment(postId, userId, content);
		
		Map<String, Object> result = new HashMap<>();
		if (userId == null) {
			result.put("code", 500);
			result.put("errorMessage", "댓글 등록 실패, 로그인하세요");
			return result;
		}
		result.put("code", 200);
		result.put("result", "success");
		
		return result;
	}
}
