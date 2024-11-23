package com.example.kosa_second_project_backend.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

// 댓글 수정 요청을 처리할 때 사용되며, 사용자가 입력한 비밀번호와 새로운 댓글 내용을 검증하는 데 사용
// 비밀번호는 댓글을 수정할 권한을 확인하는데 사용되고, 내용은 실제로 변경되는 데이터이다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class CommentEditDto {
    @NotBlank(message = "비밀번호를 입력하세요.") // 빈 문자열이나 Null 허용 X
    private String password;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 3, max = 100, message = "내용은 3자 이상 100자 이하만 가능합니다.") // 길이를 3자 이상 100자 이하로 제한
    private String content;

    @Builder
    public CommentEditDto(String password, String content) {
        this.password = password;
        this.content = content;
    }
}
