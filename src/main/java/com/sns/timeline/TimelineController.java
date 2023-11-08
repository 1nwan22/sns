package com.sns.timeline;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sns.like.bo.LikeBO;
import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardView;

@RequestMapping("/timeline")
@Controller
public class TimelineController {
	
	@Autowired
	private TimelineBO timelineBO;
	
	@Autowired
	private LikeBO likeBO;
	
	// http://localhost:8080/timeline/list-view
	@GetMapping("/list-view")
	public String timelineListView(Model model, HttpSession session) {
		model.addAttribute("viewName", "timeline/timeline");
		Integer userId = (Integer) session.getAttribute("userId");
		List<CardView> cardViewList = timelineBO.generateCardViewList(userId);
		model.addAttribute("cardList", cardViewList);
		
		return "template/layout";
	}
}
