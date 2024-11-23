package com.example.kosa_second_project_backend.controller.board;

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

//@CrossOrigin(origins = "*")  // CORS 설정 추가
@RestController
@RequestMapping("/board/{boardId}/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("write")
    public ResponseEntity<CommentDto> saveComment(@PathVariable Long boardId,
                                                  @RequestBody @Valid CommentDto commentDto,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                logger.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(CommentDto.builder().build());
        }

        commentService.saveComment(boardId, commentDto);
        return ResponseEntity.ok(commentDto);
    }

    @PutMapping("{commentId}/edit")
    public ResponseEntity<CommentEditDto> editComment(@PathVariable Long boardId,
                                                      @PathVariable Long commentId,
                                                      @RequestBody @Valid CommentEditDto commentEditDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                logger.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(CommentEditDto.builder().build());
        }
        commentService.updateComment(boardId, commentId, commentEditDto);
        return ResponseEntity.ok(commentEditDto);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long boardId,
                                                @PathVariable Long commentId) {
        commentService.deleteComment(boardId, commentId);
        return ResponseEntity.ok("commentId: " + commentId + " 이(가) 삭제되었습니다.");
    }

    @PostMapping("{commentId}/heart")
    public ResponseEntity<String> recommendComment(@PathVariable Long boardId,
                                                   @PathVariable Long commentId) {
        commentService.recommendComment(boardId, commentId);
        return ResponseEntity.ok("commentId: " + commentId + " 을(를) 추천했습니다.");
    }
}
