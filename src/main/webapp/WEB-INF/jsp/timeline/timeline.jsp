<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="d-flex justify-content-center">
	<div class="contents-box">
		<%-- 글쓰기 영역 --%>
		<c:if test="${not empty userName}">
		<div class="write-box border rounded m-3">
			<textarea id="writeTextArea" placeholder="내용을 입력해주세요" class="w-100 border-0"></textarea>

			<%-- 이미지 업로드를 위한 아이콘과 업로드 버튼을 한 행에 멀리 떨어뜨리기 위한 div --%>
			<div class="d-flex justify-content-between">
				<div class="file-upload d-flex">
					<!-- file 태그를 숨겨두고 이미지를 클릭하면 file 태그를 클릭한 효과 -->
					<input type="file" id="file" class="d-none" accept=".jpg, .png, .gif, .jpeg">
					
					
					<a href="#"  id="fileUploadBtn">
						<img width="35" src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png">
					</a>
					
					<!-- 업로드 된 임시 파일명 노출 -->
					<div id="fileName" class="ml-2"></div>
				</div>
				<button id="writeBtn" class="btn btn-info">게시</button>
			</div>
		</div> <%--// 글쓰기 영역 끝 --%>
		</c:if>

		<%-- 타임라인 영역 --%>
		<c:forEach items="${postList}" var="post">
		<div class="timeline-box my-5">
			<%-- 카드1 --%>
			<div class="card border rounded mt-3">
				<%-- 글쓴이, 더보기(삭제) --%>
				<div class="p-2 d-flex justify-content-between">
					<span class="font-weight-bold">${post.userId}</span>

					<a href="#" class="more-btn">
						<img src="https://www.iconninja.com/files/860/824/939/more-icon.png" width="30">
					</a>
				</div>	

				<%-- 카드 이미지 --%>
				<div class="card-img">
					<img src="${post.imagePath}" class="w-100" alt="본문 이미지">
				</div>

				<%-- 좋아요 --%>
				<div class="card-like m-3">
					<a href="#" class="like-btn">
						<img src="https://www.iconninja.com/files/214/518/441/heart-icon.png" width="18" height="18" alt="filled heart">
					</a>
					좋아요 11개
				</div>

				<%-- 글 --%>
				<div class="card-post m-3">
					<span class="font-weight-bold">${post.userId}</span>
					<span>${post.content}</span>
				</div>

				<%-- 댓글 제목 --%>
				<div class="card-comment-desc border-bottom">
					<div class="ml-3 mb-1 font-weight-bold">댓글</div>
				</div>

				<%-- 댓글 목록 --%>
				<div class="card-comment-list m-2">
					<%-- 댓글 내용들 --%>
					<div class="card-comment m-1">
						<span class="font-weight-bold">댓글쓴이</span>
						<span>댓글 내용</span>

						<%-- 댓글 삭제 버튼 --%>
						<a href="#" class="comment-del-btn">
							<img src="https://www.iconninja.com/files/603/22/506/x-icon.png" width="10" height="10">
						</a>
					</div>

					<%-- 댓글 쓰기 --%>
					<div class="comment-write d-flex border-top mt-2">
						<input type="text" class="form-control border-0 mr-2 comment-input" placeholder="댓글 달기"/> 
						<button type="button" class="comment-btn btn btn-light" data-post-id="${post.id}">게시</button>
					</div>
				</div> <%--// 댓글 목록 끝 --%>
			</div> <%--// 카드1 끝 --%>
		</div> <%--// 타임라인 영역 끝  --%>
		</c:forEach>
	</div> <%--// contents-box 끝  --%>
</div>

<script>
	$(document).ready(function() {
		
		// 파일이미지 클릭 => 숨겨져 있던 type="file"을 동작시킨다.
		$("#fileUploadBtn").on("click", function(e) {
			e.preventDefault();  // a 태그의 올라가는 현상 방지
			$("#file").click();
		});
		
		// 이미지를 선택하는 순간 -> 유효성 확인 및 업로드 된 파일명 노출
		$("#file").on("change", function(e) {
			let fileName = e.target.files[0].name; // screenshot4.png
			console.log(fileName); 
			
			// 확장자 유효성 확인
			let ext = fileName.split(".").pop().toLowerCase();
			
			if (ext != 'jpg' && ext != 'gif' && ext != 'png' && ext != 'jpeg') {
				alert("이미지 파일만 업로드 할 수 있습니다.");
				$("#file").val(""); // 파일 태그에 파일 제거(보이지 않지만 업로드 될 수 있으므로 주의)
				$("#fileName").text(""); // 파일명 비우기
				return;
			}
			
			// 유효성 통과한 이미지는 업로드 된 파일명 노출
			$("#fileName").text(fileName);
			
		});
		
		
		
		$("#writeBtn").on("click", function() {
			let content = $("#writeTextArea").val().trim();
			let fileName = $("#file").val();
			
			if (!fileName) {
				alert("이미지를 업로드하세요");
				return;
			}
			
			let ext = fileName.split(".").pop().toLowerCase();
			
			if ($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1) { // 존재하면 인덱스가, 없으면 -1이 나옴
				alert("이미지 파일만 업로드 할 수 있습니다.");
				$("#file").val(""); // 파일을 비운다.
				return;
			}
			
			let formData = new FormData();
			formData.append("content", content)
			formData.append("file", $("#file")[0].files[0]);
			
			
			$.ajax({
				type:"POST"
				, url:"/post/create"
				, data:formData
				, enctype:"multipart/form-data"
				, processData:false
				, contentType:false
			
				, success:function(data) {
					if (data.result == "success") {
						alert("저장되었습니다.")
						location.href = "/timeline/list-view"
					}
				}
				, error:function(request, status, error) {
					alert("글 등록 에러")
				}
			})
			
		});
		
		$(".comment-btn").on("click", function() {
			let content = $(this).prev().val().trim();
			let postId = $(this).data("postId");
			
			$.ajax({
				type:"post"
				, url:"/comment/create"
				, data:{"content"=content, "postId"=postId}
			
				, success:function(data) {
					if (data.result == "success") {
						alert("댓글 등록 성공");
					} else {
						alert(data.errorMessage);
					}
				}
				, error:function(request, status, error) {
					alert("댓글 등록 실패");
				}
			});
			
		});
		
		
	});
</script>
