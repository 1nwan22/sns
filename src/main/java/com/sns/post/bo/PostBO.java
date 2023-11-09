package com.sns.post.bo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

@Service
public class PostBO {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FileManagerService fileManager;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	public List<PostEntity> getPostList() {
		return postRepository.findAll();
	}
	
	public Integer addPost(int userId, String userLoginId, String content, MultipartFile file) {
		String imagePath = null;
		
		if (file != null) {
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		PostEntity postEntity = postRepository.save(
				PostEntity.builder()
				.userId(userId)
				.content(content)
				.imagePath(imagePath)
				.build());
		
		return postEntity == null ? null : postEntity.getId();
		
	}
	
	// 글 삭제
	@Transactional
	public void deletePostById(int postId) {
		// 기존 글 => 이미지 삭제
		PostEntity post = postRepository.findById(postId).orElse(null);
		
		if (post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}
		// db 글 삭제
		postRepository.delete(post);
		
		// db 댓글 삭제
		commentBO.deleteCommentListByPostId(postId);
		
		// db 좋아요 삭제
		likeBO.deleteLikesByPostId(postId);
		
	}
}
