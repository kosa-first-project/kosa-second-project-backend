package com.example.kosa_second_project_backend.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class CommentDto {
    private Long commentId; // commentId 필드 추가
    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 1, max = 20, message = "닉네임은 1자 이상, 20자 이하만 가능합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 1, max = 100, message = "내용은 1자 이상 100자 이하만 가능합니다.")
    private String content;

    @Builder
    public CommentDto(Long commentId, String nickname, String password, String content) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.password = password;
        this.content = content;
    }
}