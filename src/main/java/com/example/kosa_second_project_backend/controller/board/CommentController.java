package com.example.kosa_second_project_backend.controller.board;

import com.example.kosa_second_project_backend.dto.board.ApiResponse;
import com.example.kosa_second_project_backend.dto.board.CommentDto;
import com.example.kosa_second_project_backend.dto.board.CommentEditDto;
import com.example.kosa_second_project_backend.service.board.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*")  // CORS 설정 추가
@RestController
@RequestMapping("/board/{boardId}/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("write") // 새로운 댓글 저장
    public ResponseEntity<ApiResponse<CommentDto>> saveComment(@PathVariable Long boardId, // 댓글이 속한 게시판의 ID
                                                   @RequestBody @Valid CommentDto commentDto, // 댓글
                                                   BindingResult bindingResult) { // 유효성 검사 결과를 저장
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            logger.error("Validation Error: {}", errorMessage);

            ApiResponse<CommentDto> response = new ApiResponse<>(false, errorMessage, null);
            return ResponseEntity.badRequest().body(response);
        }

        commentService.saveComment(boardId, commentDto);
        ApiResponse<CommentDto> response = new ApiResponse<>(true, "댓글이 성공적으로 저장되었습니다.", commentDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{commentId}/edit") // 댓글 수정
    public ResponseEntity<ApiResponse<CommentEditDto>> editComment(@PathVariable Long boardId, // 게시판의 ID
                                                      @PathVariable Long commentId, // 댓글의 ID
                                                      @RequestBody @Valid CommentEditDto commentEditDto, // 수정할 댓글 데이터
                                                      BindingResult bindingResult) { // 유효성 검사 결과
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            logger.error("Validation Error: {}", errorMessage);

            ApiResponse<CommentEditDto> response = new ApiResponse<>(false, errorMessage, null);
            return ResponseEntity.badRequest().body(response);
        }

        commentService.updateComment(boardId, commentId, commentEditDto);
        ApiResponse<CommentEditDto> response = new ApiResponse<>(true, "댓글이 성공적으로 수정되었습니다.", commentEditDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{commentId}") // 댓글 삭제
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long boardId,
                                                @PathVariable Long commentId) {
        commentService.deleteComment(boardId, commentId);
        ApiResponse<Void> response = new ApiResponse<>(true, "댓글이 성공적으로 삭제되었습니다.", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{commentId}/heart") // 댓글의 좋아요 기능
    public ResponseEntity<ApiResponse<Void>> recommendComment(@PathVariable Long boardId,
                                                   @PathVariable Long commentId) {
        commentService.recommendComment(boardId, commentId);
        ApiResponse<Void> response = new ApiResponse<>(true, "댓글을 추천하였습니다.", null);
        return ResponseEntity.ok(response);
    }
}
