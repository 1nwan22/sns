package com.sns.timeline.domain;

import java.util.List;

import com.sns.comment.domain.CommentView;
import com.sns.like.domain.Like;
import com.sns.post.entity.PostEntity;
import com.sns.user.entity.UserEntity;

import lombok.Data;

// View 용 객체
// 글 1개와 매핑됨
@Data
public class CardView {
	// 글 1개
	private PostEntity post;
	
	// 글쓴이 정보 / 비밀번호등 개인정보가 들어가므로 별로 좋은 구조는 아니고 SimpleUser 처럼 개인정보를 뺀 추가 객체를 만들어서 다른 걸로 하는 것이 바람직함
	private UserEntity user;
	
	// 댓글들
	private List<CommentView> commentList;
	
	// 좋아요 개수
	private int likeCount;
	
	// 내가 좋아요를 눌렀는지 여부
	private boolean filledLike;	// ture:filled false:empty
}
