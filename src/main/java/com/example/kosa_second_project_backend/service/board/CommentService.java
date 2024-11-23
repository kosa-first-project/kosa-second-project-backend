package com.example.kosa_second_project_backend.service.board;

import com.example.kosa_second_project_backend.entity.board.Board;
import com.example.kosa_second_project_backend.entity.board.Comment;
import com.example.kosa_second_project_backend.dto.board.CommentDto;
import com.example.kosa_second_project_backend.dto.board.CommentEditDto;
import com.example.kosa_second_project_backend.exception.ResourceNotFoundException;
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

    @Transactional // 댓글 저장
    public Long saveComment(Long boardId, CommentDto commentDto) { // 댓글 저장
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ResourceNotFoundException("페이지가 존재하지 않습니다.")); //

        Comment comment = Comment.builder()
                .nickname(commentDto.getNickname())
                .password(commentDto.getPassword())
                .content(commentDto.getContent())
                .hearts(0)
                .board(board)
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    @Transactional // 댓글 기능 수정
    public void updateComment(Long boardId, Long commentId, CommentEditDto commentEditDto) {
        boardRepository.findById(boardId).orElseThrow(() -> new ResourceNotFoundException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) {
            throw new ResourceNotFoundException("잘못된 경로입니다.");
        }
        if (!comment.getPassword().equals(commentEditDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
            commentRepository.updateComment(commentId, commentEditDto.getContent());

    }

    @Transactional // 댓글 삭제
    public void deleteComment(Long boardId, Long commentId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) { // 삭제하려고 하는 댓글이 현재 게시판에 속해있는지 확인하는 명령어
            throw new ResourceNotFoundException("잘못된 경로입니다.");
        }

        commentRepository.deleteById(commentId);
    }

    @Transactional // 댓글 추천 수 증가
    public void recommendComment(Long boardId, Long commentId) {
        boardRepository.findById(boardId).orElseThrow(() -> new ResourceNotFoundException("페이지가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("댓글이 존재하지 않습니다."));

        if (comment.getBoard().getBoardId() != boardId) {
            throw new ResourceNotFoundException("잘못된 경로입니다.");
        }
        commentRepository.updateHeartCount(commentId);
    }

}
