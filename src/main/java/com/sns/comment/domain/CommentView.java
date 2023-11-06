package com.sns.comment.domain;

import com.sns.user.entity.UserEntity;

import lombok.Data;

@Data
public class CommentView {

	private Comment comment;
	
	private UserEntity user;
}
