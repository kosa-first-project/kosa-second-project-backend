package com.example.kosa_second_project_backend.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardDto {
    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 1, max = 20, message = "닉네임은 1자 이상, 20자 이하만 가능합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 1, max = 30, message = "제목은 1자 이상, 30자 이하만 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 5, message = "내용은 5자 이상 입력해야 합니다.")
    private String content;

    @Builder
    public BoardDto(String nickname, String password, String title, String content) {
        this.nickname = nickname;
        this.password = password;
        this.title = title;
        this.content = content;
    }
}
