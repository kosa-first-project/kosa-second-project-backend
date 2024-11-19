package com.example.kosa_second_project_backend.service.board;

import com.example.kosa_second_project_backend.entity.board.Board;
import com.example.kosa_second_project_backend.entity.board.Comment;
import com.example.kosa_second_project_backend.entity.board.Image;
import com.example.kosa_second_project_backend.dto.board.*;
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
    public Long savePost(BoardDto boardDto) {
        Board board = Board.builder()
                .nickname(boardDto.getNickname())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .views(0)
                .build();

        return boardRepository.save(board).getBoardId();
    }

    // 게시글 찾기
    public List<BoardListsDto> findPost(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        List<BoardListsDto> boardListsDtos = new ArrayList<>();

        for (Board board : boards) {
            boardListsDtos.add(BoardListsDto.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .commentCount(commentRepository.countByBoard_BoardId(board.getBoardId()))
                    .nickname(board.getNickname())
                    .views(board.getViews())
                    .createDate(board.getCreateDate())
                    .build());
        }
        return boardListsDtos;
    }

    @Transactional
    public BoardDetailsDto findPostDetails(Long boardId, Pageable pageable) {
        // 기존 엔터티 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

        // 조회수 증가
        board.incrementViews();

        // 이미지 및 댓글 처리
        List<Image> images = imageRepository.findAllByBoard_BoardId(boardId);
        List<ImageDetailsDto> imageDetailsDtos = new ArrayList<>();
        for (Image image : images) {
            imageDetailsDtos.add(ImageDetailsDto.builder()
                    .originName(image.getOriginName())
                    .saveName(image.getSaveName())
                    .imagePath(image.getImagePath())
                    .imageSize(image.getImageSize())
                    .build());
        }

        List<Comment> comments = commentRepository.findAllByBoard_BoardId(boardId, pageable);
        List<CommentDetailsDto> commentDetailsDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDetailsDtos.add(CommentDetailsDto.builder()
                    .nickname(comment.getNickname())
                    .content(comment.getContent())
                    .hearts(comment.getHearts())
                    .createDate(comment.getCreateDate())
                    .build());
        }

        // DTO 반환
        return BoardDetailsDto.builder()
                .nickname(board.getNickname())
                .views(board.getViews())
                .createDate(board.getCreateDate())
                .title(board.getTitle())
                .content(board.getContent())
                .images(imageDetailsDtos)
                .comments(commentDetailsDtos)
                .build();
    }


//    // 개별 게시글 찾기
//    @Transactional
//    public BoardDetailsDto findPostDetails(Long boardId, Pageable pageable) {
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
//
//        boardRepository.save(Board.builder()
//                .boardId(boardId)
//                .nickname(board.getNickname())
//                .password(board.getPassword())
//                .title(board.getTitle())
//                .content(board.getContent())
//                .views(board.getViews() + 1)
//                .build());
//
//        List<Image> images = imageRepository.findAllByBoard_BoardId(boardId);
//        List<ImageDetailsDto> imageDetailsDtos = new ArrayList<>();
//
//        List<Comment> comments = commentRepository.findAllByBoard_BoardId(boardId, pageable);
//        List<CommentDetailsDto> commentDetailsDtos = new ArrayList<>();
//
//        for (Image image : images) {
//            imageDetailsDtos.add(ImageDetailsDto.builder()
//                    .originName(image.getOriginName())
//                    .saveName(image.getSaveName())
//                    .imagePath(image.getImagePath())
//                    .imageSize(image.getImageSize())
//                    .build());
//        }
//
//        for (Comment comment : comments) {
//            commentDetailsDtos.add(CommentDetailsDto.builder()
//                    .nickname(comment.getNickname())
//                    .content(comment.getContent())
//                    .hearts(comment.getHearts())
//                    .createDate(comment.getCreateDate())
//                    .build());
//        }
//
//        return BoardDetailsDto.builder()
//                .nickname(board.getNickname())
//                .views(board.getViews() + 1)
//                .createDate(board.getCreateDate())
//                .title(board.getTitle())
//                .content(board.getContent())
//                .images(imageDetailsDtos)
//                .comments(commentDetailsDtos)
//                .build();
//    }

    // 삭제
    @Transactional
    public void deletePost(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

            List<Image> images = imageRepository.findAllByBoard_BoardId(boardId);
            List<Comment> comments = commentRepository.findAllByBoard_BoardId(boardId);

//            for (Image image : images) {
//                imageService.deleteImage(image);
//            }
            imageRepository.deleteAll(images);
            commentRepository.deleteAll(comments);
            boardRepository.delete(board);
    }

    // 수정
    @Transactional
    public void updatePost(Long boardId, BoardEditDto boardEditDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));

            boardRepository.save(Board.builder()
                    .boardId(boardId)
                    .nickname(board.getNickname())
                    .password(board.getPassword())
                    .title(boardEditDto.getTitle())
                    .content(boardEditDto.getContent())
                    .views(board.getViews())
                    .build());
    }

    // 검색
    public List<BoardListsDto> searchPosts(String keyword, Pageable pageable) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword, pageable);
        List<BoardListsDto> boardListsDtos = new ArrayList<>();

        for (Board board : boards) {
            BoardListsDto boardListsDto = BoardListsDto.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .commentCount(commentRepository.countByBoard_BoardId(board.getBoardId()))
                    .nickname(board.getNickname())
                    .views(board.getViews())
                    .createDate(board.getCreateDate())
                    .build();
            boardListsDtos.add(boardListsDto);
        }
        return boardListsDtos;
    }
}