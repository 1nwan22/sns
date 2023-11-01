package com.sns.timeline;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/timeline")
@Controller
public class TimelineController {
	
	// http://localhost:8080/timeline/list-view
	@GetMapping("/list-view")
	public String timelineListView(Model model, HttpSession session) {
		model.addAttribute("viewName", "timeline/timeline");
		return "template/layout";
	}
}
