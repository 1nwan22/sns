package com.sns.like.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sns.like.domain.Like;

@Repository
public interface LikeMapper {

//	public Like selectLike(
//			@Param("postId") int postId, 
//			@Param("userId") int userId);
//	public int selectLikeCountByPostId(int postId);
	
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId);
	
	public void insertLike(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	
	public void deleteLike(
			@Param("postId") int postId, 
			@Param("userId") Integer userId);
	
}
