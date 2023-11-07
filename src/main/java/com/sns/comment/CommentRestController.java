package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	/**
	 * 댓글 쓰기 API
	 * 
	 * @param content
	 * @param postId
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> commentCreate(@RequestParam("content") String content,
			@RequestParam("postId") int postId, HttpSession session) {
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

	@DeleteMapping("/delete")
	public Map<String, Object> commentDelete(
			@RequestParam("commentId") int commentId, 
			HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		// 로그인 여부 확인
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 500);
			result.put("errorMessage", "로그인 되지 않은 사용자 입니다.");
			return result;
		}
		// 삭제
		commentBO.deleteCommentById(commentId);

		// 응답값
		result.put("code", 200);
		result.put("result", "success");

		return result;
	}
}
