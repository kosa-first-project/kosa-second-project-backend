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
    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 3, max = 10, message = "닉네임은 3자 이상, 10자 이하만 가능합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문, 숫자, 특수기호가 적어도 1개 이상씩 포함된 8~20자만 가능합니다.")
    private String password;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 3, max = 100, message = "내용은 3자 이상 100자 이하만 가능합니다.")
    private String content;

    @Builder
    public CommentDto(String nickname, String password, String content) {
        this.nickname = nickname;
        this.password = password;
        this.content = content;
    }
}