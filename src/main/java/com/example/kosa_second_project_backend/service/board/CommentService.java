package com.example.kosa_second_project_backend.service.board;

import com.example.kosa_second_project_backend.entity.board.Board;
import com.example.kosa_second_project_backend.entity.board.Comment;
import com.example.kosa_second_project_backend.dto.board.CommentDto;
import com.example.kosa_second_project_backend.dto.board.CommentEditDto;
import com.example.kosa_second_project_backend.repository.board.BoardRepository;
import com.example.kosa_second_project_backend.repository.board.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long saveComment(Long boardId, CommentDto commentDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .nickname(commentDto.getNickname())
                .content(commentDto.getContent())
                .hearts(0)
                .board(board)
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    @Transactional
    public void updateComment(Long boardId, Long commentId, CommentEditDto commentEditDto) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) {
            throw new IllegalStateException("잘못된 경로입니다.");
        }

            commentRepository.updateComment(commentId, commentEditDto.getContent());

    }

    @Transactional
    public void deleteComment(Long boardId, Long commentId, String password) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) {
            throw new IllegalStateException("잘못된 경로입니다.");
        }
        commentRepository.deleteById(commentId);

    }

    @Transactional
    public void recommendComment(Long boardId, Long commentId) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) {
            throw new IllegalStateException("잘못된 경로입니다.");
        }
        commentRepository.updateHeartCount(commentId);
    }
}
