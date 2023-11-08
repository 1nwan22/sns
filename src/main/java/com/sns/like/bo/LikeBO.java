package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.domain.Like;
import com.sns.like.mapper.LikeMapper;

@Service
public class LikeBO {

	@Autowired
	private LikeMapper likeMapper;

	public boolean getLike(int postId, Integer userId) {
		if (userId == null) {
			return false;
		}
		// 로그인
		// 0보다 큰 경우 있음(채운다 true) 그렇지 않으면 false(빈 하트)
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0;
	}

	public void likeToggle(int postId, int userId) {

		if (likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0) {
			likeMapper.deleteLike(postId, userId);
		} else {
			likeMapper.insertLike(postId, userId);
		}
	}

	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
}
