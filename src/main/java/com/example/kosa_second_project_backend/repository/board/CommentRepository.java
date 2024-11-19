package com.example.kosa_second_project_backend.repository.board;

import com.example.kosa_second_project_backend.entity.board.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("update Comment c set c.content = :content where c.commentId = :commentId")
    void updateComment(Long commentId, String content);

    @Modifying
    @Query("update Comment c set c.hearts = c.hearts + 1 where c.commentId = :commentId")
    Integer updateHeartCount(Long commentId);

    Long countByBoard_BoardId(Long boardId);

    List<Comment> findAllByBoard_BoardId(Long boardId);

    List<Comment> findAllByBoard_BoardId(Long boardId, Pageable pageable);
}