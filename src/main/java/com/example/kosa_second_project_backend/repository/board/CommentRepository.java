package com.example.kosa_second_project_backend.repository.board;

import com.example.kosa_second_project_backend.entity.board.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> { // <클래스 명, 기본 키 타입>
    
   
    @Modifying  // 수정 작업
    @Query("update Comment c set c.content = :content where c.commentId = :commentId") // 쿼리문 작성
    void updateComment(Long commentId, String content); // 특정 댓글 ID의 새로운 내용을 수정하는 작업

    @Modifying
    @Query("update Comment c set c.hearts = c.hearts + 1 where c.commentId = :commentId")
    int updateHeartCount(Long commentId); // 특정 댓글 ID의 좋아요 수를 1 증가 시키는 쿼리

    Long countByBoard_BoardId(Long boardId); // 특정 게시글의 댓글 전체 개수를 반환

    List<Comment> findAllByBoard_BoardId(Long boardId); // 특정 게시글의 모든 댓글 조회

    // 특정 게시글의 모든 댓글을 페이징 처리하여 조회한다. (페이징 지원)
    Page<Comment> findAllByBoard_BoardId(Long boardId, Pageable pageable);
//    List<Comment> findAllByBoard_BoardId(Long boardId, Pageable pageable);
}