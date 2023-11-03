package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/comment")
@RestController
public class CommentRestController {

	@PostMapping("/create")
	public Map<String, Object> commentCreate(
			@RequestParam("comment") String comment,
			@RequestParam("postId") int postId) {
		
		Map<String, Object> result = new HashMap<>();
		
		return result;
	}
}
