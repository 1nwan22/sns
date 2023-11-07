package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.domain.Like;
import com.sns.like.mapper.LikeMapper;

@Service
public class LikeBO {

	@Autowired
	private LikeMapper likeMapper;
	
	public boolean getLike(int postId, int userId) {
		Like like = likeMapper.selectLike(postId, userId);
		
		if (like == null) {
			return false;
		}
		return true;
	}
	
	public void likeToggle(int postId, int userId) {
		Like like = likeMapper.selectLike(postId, userId);
		
		if (like == null) {
			likeMapper.insertLike(postId, userId);
		} else {
			likeMapper.deleteLike(postId, userId);
		}
	}
	
	public int countLike(int postId) {
		return likeMapper.countLike(postId);
	}
}
