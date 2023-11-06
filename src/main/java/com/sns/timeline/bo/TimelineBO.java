package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class TimelineBO {

	@Autowired
	private PostBO postBO;

	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;

	// input:X output:List<CardView>
	public List<CardView> generateCardViewList() {
		List<CardView> cardViewList = new ArrayList<>(); // []

		// 글 목록을 가져온다. List<PostEntity>
		List<PostEntity> postList = postBO.getPostList();

		// 글 목록 반복문 순회
		// postEntity => CardView => cardViewList에 담는다.
//		CardView card = null;
//		
//		for (int i = 0; i < postList.size(); i++) {
//			PostEntity post = postList.get(i);
//			cardViewList.add(i, card);
//			card.setPost(post);
//		}
//		
//		for (int i = 0; i < cardViewList.size(); i++) {
//			UserEntity user = userBO.getUserEntityById(cardViewList.get(i).getPost().getUserId());
//			cardViewList.get(i).setUser(user);
//		}

		for (PostEntity post : postList) {
			CardView cardView = new CardView();
			// 글 1개
			cardView.setPost(post);
			
			// 글쓴이 정보 세팅
			UserEntity user = userBO.getUserEntityById(post.getUserId());
			cardView.setUser(user);
			
			// 댓글들
			List<CommentView> commentList = commentBO.generateCommentViewListByPostId(post.getId());
			cardView.setCommentList(commentList);
			
			// 좋아요 카운트
			
			// 내가 좋아요 눌렀는지 여부
			
			//★★★★★ 마지막에 CardViewList에 card를 넣는다
			cardViewList.add(cardView);
		}

		return cardViewList;
	}
}
