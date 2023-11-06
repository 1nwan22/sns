package com.sns.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.Comment;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;

@RequestMapping("/timeline")
@Controller
public class TimelineController {
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private CommentBO commentBO;
	
	// http://localhost:8080/timeline/list-view
	@GetMapping("/list-view")
	public String timelineListView(Model model) {
		model.addAttribute("viewName", "timeline/timeline");
		
		List<PostEntity> postList = postBO.getPostList();
		model.addAttribute("postList", postList);
		
		List<Comment> commentList = commentBO.getCommentList();
		model.addAttribute("commentList", commentList);
		
		
		
		
		return "template/layout";
	}
}
