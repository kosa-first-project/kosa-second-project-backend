package com.example.kosa_second_project_backend.controller.board;

import com.example.kosa_second_project_backend.dto.board.BoardDetailsDto;
import com.example.kosa_second_project_backend.dto.board.BoardDto;
import com.example.kosa_second_project_backend.dto.board.BoardEditDto;
import com.example.kosa_second_project_backend.dto.board.BoardListsDto;
import com.example.kosa_second_project_backend.service.board.BoardService;
import com.example.kosa_second_project_backend.service.board.ImageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*")  // CORS 설정 추가
@RestController
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final ImageService imageService;
    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<BoardDto> writePost(@Valid @RequestPart("board") BoardDto boardDto,
                                              BindingResult bindingResult,
                                              @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                logger.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest()
                    .body(BoardDto.builder().build());
        }
        Long boardId = boardService.savePost(boardDto);

        // 이미지가 있는 경우에만 저장
        if (files != null && !files.isEmpty() && !files.get(0).isEmpty()) {
            imageService.saveImage(boardId, files);
        }

        return ResponseEntity.ok()
                .body(boardDto);
    }

    //게시글 검색
    @GetMapping()
    public ResponseEntity<Page<BoardListsDto>> findLists(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @PageableDefault(size = 5, sort = "boardId", direction = Sort.Direction.DESC) Pageable pageable) {
        if (keyword.isEmpty()) {
            return ResponseEntity.ok()
                    .body(boardService.findPost(pageable));
        } else {
            return ResponseEntity.ok()
                    .body(boardService.searchPosts(keyword, pageable));
        }
    }
    // 게시글 상세 조회
    @GetMapping("{boardId}")
    public ResponseEntity<BoardDetailsDto> findPostDetails(@PathVariable(value = "boardId") Long boardId,
                                                           @PageableDefault(size = 5, sort = "commentId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok()
                .body(boardService.findPostDetails(boardId, pageable));
    }

    @DeleteMapping("/{boardId}/delete")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) {
        try {
            boardService.deletePost(boardId);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제 실패");
        }
    }
    // 게시글 수정
    @PutMapping("{boardId}/edit")
    public ResponseEntity<BoardEditDto> editPost(
            @PathVariable(value = "boardId") Long boardId,
            @Valid @RequestPart(value = "board") BoardEditDto boardEditDto,
            BindingResult bindingResult,
            @RequestPart(value = "image", required = false) List<MultipartFile> images) throws IOException {

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                logger.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(BoardEditDto.builder().build());
        }
        boardService.updatePost(boardId, boardEditDto);

        if (images != null && !images.isEmpty() && !images.get(0).isEmpty()) {
            imageService.saveImage(boardId, images);
        } else {
            imageService.updateImage(boardId);
        }
        return ResponseEntity.ok()
                .body(boardEditDto);
    }

}