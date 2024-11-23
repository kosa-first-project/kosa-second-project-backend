package com.example.kosa_second_project_backend.service.board;

import com.example.kosa_second_project_backend.dto.board.*;
import com.example.kosa_second_project_backend.entity.board.Board;
import com.example.kosa_second_project_backend.entity.board.Comment;
import com.example.kosa_second_project_backend.entity.board.Image;
import com.example.kosa_second_project_backend.exception.ResourceNotFoundException;
import com.example.kosa_second_project_backend.repository.board.BoardRepository;
import com.example.kosa_second_project_backend.repository.board.CommentRepository;
import com.example.kosa_second_project_backend.repository.board.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {
    private final ImageService imageService;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    // 저장
    @Transactional
    public Long savePost(BoardDto boardDto) { // 게시글 저장
        Board board = Board.builder()
                .nickname(boardDto.getNickname())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .views(0)
                .build();

        return boardRepository.save(board).getBoardId();
    }

    // 게시글 목록 조회(페이징 처리, BoardListsDto로 매핑하여 반환)
    @Transactional(readOnly = true)
    public Page<BoardListsDto> findPost(Pageable pageable) { // BoardListsDto로 매핑하여 반환
        Page<Board> boards = boardRepository.findAll(pageable);

        Page<BoardListsDto> boardListsDtos = boards.map(board -> BoardListsDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .commentCount(commentRepository.countByBoard_BoardId(board.getBoardId()))
                .nickname(board.getNickname())
                .views(board.getViews())
                .createDate(board.getCreateDate())
                .build());

        return boardListsDtos;
    }

    // 특정 게시글의 상세 정보를 조회하고, 관련 이미지와 댓글을 포함한 BoardDetailsDto를 반환, 조회 수를 증가시킨다.
    @Transactional
    public BoardDetailsDto findPostDetails(Long boardId, Pageable pageable) {
        // 기존 엔터티 조회
        Board board = boardRepository.findById(boardId) // 특정 게시글 조회
                .orElseThrow(() -> new ResourceNotFoundException("페이지가 존재하지 않습니다.")); // 존재하지 않을 경우 예외

        // 조회수 증가
        board.incrementViews();

        // 이미지 및 댓글 처리
        List<Image> images = imageRepository.findAllByBoard_BoardId(boardId); // 게시글에 첨부된 이미지 조회
        List<ImageDetailsDto> imageDetailsDtos = new ArrayList<>(); // ImageDetailsDto로 매핑
        for (Image image : images) {
            imageDetailsDtos.add(ImageDetailsDto.builder()
                    .originName(image.getOriginName())
                    .saveName(image.getSaveName())
                    .imagePath(image.getImagePath())
                    .imageSize(image.getImageSize())
                    .build());
        }

        Page<Comment> commentsPage = commentRepository.findAllByBoard_BoardId(boardId, pageable); // 게시글에 작성된 댓글 조회
        List<Comment> comments = commentsPage.getContent(); // 게시글에 작성된 댓글 조회
        List<CommentDetailsDto> commentDetailsDtos = new ArrayList<>(); // CommentDetailsDto로 매핑
        for (Comment comment : comments) {
            System.out.println(comment);
            commentDetailsDtos.add(CommentDetailsDto.builder()
                    .commentId(comment.getCommentId()) // commentId 추가
                    .password(comment.getPassword()) // password 추가
                    .nickname(comment.getNickname())
                    .content(comment.getContent())
                    .hearts(comment.getHearts())
                    .createDate(comment.getCreateDate())
                    .build());
        }

        // 최종적으로 모든 정보가 포함된 BoardDetailsDto를 반환
        return BoardDetailsDto.builder()
                .nickname(board.getNickname())
                .views(board.getViews())
                .createDate(board.getCreateDate())
                .title(board.getTitle())
                .content(board.getContent())
                .images(imageDetailsDtos)
                .comments(commentDetailsDtos)
                .currentPage(commentsPage.getNumber())
                .totalPages(commentsPage.getTotalPages())
                .totalComments(commentsPage.getTotalElements())
                .build();
    }

    // 특정 게시글을 삭제하고 해당 게시글에 첨부된 이미지, 댓글들도 함께 삭제한다.
    @Transactional
    public void deletePost(Long boardId) {
        Board board = boardRepository.findById(boardId) // 게시글 조회
                .orElseThrow(() -> new ResourceNotFoundException("페이지가 존재하지 않습니다.")); // 존재하지 않을 경우 예외 처리

        List<Image> images = imageRepository.findAllByBoard_BoardId(boardId); // 게시글에 첨부된 이미지 조회
        List<Comment> comments = commentRepository.findAllByBoard_BoardId(boardId); // 게시글에 작성된 댓글 조회

//            for (Image image : images) {
//                imageService.deleteImage(image);
//            }
        imageRepository.deleteAll(images);
        commentRepository.deleteAll(comments);
        boardRepository.delete(board);
    }

    @Transactional
    public void updatePost(Long boardId, BoardEditDto boardEditDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("페이지가 존재하지 않습니다."));

        // 비밀번호 검증 로직 추가 (예: boardEditDto에 비밀번호 추가)
        if (!board.getPassword().equals(boardEditDto.getPassword())) {
            throw new ResourceNotFoundException("비밀번호가 일치하지 않습니다.");
        }

        // 엔티티 직접 수정 ( JPA의 변경 감지 기능 활용)
        // JPA는 트랜잭션 종료 시 자동으로 변경 사항을 감지하고 업데이트
        board.setTitle(boardEditDto.getTitle());
        board.setContent(boardEditDto.getContent());
    }

    // 키워드가 포함된 게시글을 검색하고 페이징 처리된 결과를 BoardListsDto로 매핑하여 반환
    @Transactional(readOnly = true)
    public Page<BoardListsDto> searchPosts(String keyword, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContaining(keyword, pageable);

        Page<BoardListsDto> boardListsDtos = boards.map(board -> BoardListsDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .commentCount(commentRepository.countByBoard_BoardId(board.getBoardId())) // 댓글 개수 계산
                .nickname(board.getNickname())
                .views(board.getViews())
                .createDate(board.getCreateDate())
                .build());

        return boardListsDtos;
    }
}