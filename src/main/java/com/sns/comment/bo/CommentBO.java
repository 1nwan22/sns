package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentView;
import com.sns.comment.mapper.CommentMapper;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class CommentBO {
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;
	
	public List<Comment> getCommentList() {
		return commentMapper.selectCommentList();
	}
	
	public List<Comment> getCommentListByPostId(int postId) {
		return commentMapper.selectCommentListByPostId(postId);
	}
	
	public List<CommentView> generateCommentViewListByPostId(int postId) {
		List<CommentView> commentViewList = new ArrayList<>();
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		
		for (Comment comment : commentList) {
			CommentView commentView = new CommentView();
			
			// 댓글 내용 담기
			commentView.setComment(comment);
			
			// 댓글쓴이 내용 담기
			UserEntity user = userBO.getUserEntityById(comment.getUserId());
			commentView.setUser(user);
			
			// 리스트에 담는다.
			commentViewList.add(commentView);
		}
		
		return commentViewList;
	}
	
	public void addComment(int postId, int userId, String content) {
		commentMapper.insertComment(postId, userId, content);
	}
	
	public void deleteCommentById(int id) {
		commentMapper.deleteCommentById(id);
	}

}
