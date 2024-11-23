package com.example.kosa_second_project_backend.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 특정 게시글의 상세 정보를 클라이언트에게 제공할 때 사용된다. 주로 게시글 조회 API의 응답으로 사용되며,
// 게시글의 모든 세부 정보(작성자 닉네임, 조회 수,생성일, 제목, 내용, 첨부된 이미지 목록, 댓글 목록)를 포함한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardDetailsDto {
    private String nickname;
    private Integer views;

    @JsonFormat(pattern = "yyyy.MM.dd. HH:mm")
    private LocalDateTime createDate;

    private String title;
    private String content;
    private List<ImageDetailsDto> images; // ImageDetailsDto 객체의 리스트로, 각 이미지의 세부 정보를 포함한다.
    private List<CommentDetailsDto> comments; // CommentDetailsDto 객체들의 리스트로, 각 댓글의 세부 정보를 포함한다.

    // 페이징 정보 추가
    private int currentPage; // 현재 페이지 번호 (0부터 시작)
    private int totalPages; // 총 댓글 페이지 수
    private long totalComments; // 총 댓글 수


    @Builder
    public BoardDetailsDto(String nickname, Integer views, LocalDateTime createDate, String title,
                           String content, List<ImageDetailsDto> images, List<CommentDetailsDto> comments,
                            int currentPage, int totalPages, long totalComments ) {
        this.nickname = nickname;
        this.views = views;
        this.createDate = createDate;
        this.title = title;
        this.content = content;
        this.images = images;
        this.comments = comments;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalComments = totalComments;
    }
}