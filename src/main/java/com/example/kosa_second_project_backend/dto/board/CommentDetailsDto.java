package com.example.kosa_second_project_backend.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat; // JSON 직렬화 시 날짜 형식을 지정하기 위해 사용
import lombok.*;

import java.time.LocalDateTime;

// 댓글의 상세 정보를 클라이언트에게 제공할 때 사용된다.
// 주로 댓글 조회 API 응답에 사용되고 댓글의 모든 세부 정보를 포함한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class CommentDetailsDto {
    private Long commentId; // commentId 필드 추가
    private String nickname;
    private String password;
    private String content;
    private Integer hearts;

    @JsonFormat(pattern = "yyyy.MM.dd. HH:mm") // @JsonFormat을 통해 JSON 직렬화 시 지정된 형식으로 출력
    private LocalDateTime createDate;

    @Builder
    public CommentDetailsDto(Long commentId, String password, String nickname, String content, Integer hearts, LocalDateTime createDate) {
        this.commentId = commentId;
        this.password = password;
        this.nickname = nickname;
        this.content = content;
        this.hearts = hearts;
        this.createDate = createDate;
    }
}
