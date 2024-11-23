package com.example.kosa_second_project_backend.entity.board;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 접근 제한자를 protected로 설정한 파라미터가 없는 기본 생성자를 생성한다.
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Comment extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 자동으로 생성
    private Long commentId; // 댓글 ID
    private String nickname; // 작성자 닉네임
    private String password; // 댓글 비밀번호
    private String content; // 댓글 내용
    private int hearts; // 좋아요(추천) 수

    @ManyToOne(fetch = FetchType.LAZY) // Comment 엔티티는 Board 엔티티와 다대일(N:1)관계를 가짐
    @JoinColumn(name = "board_id") // Comment 테이블에 board_id라는 외래 키 컬럼 생성 후 Board 엔티티와 연결
    private Board board;

//    // 조회수 증가 메서드와 유사하게 좋아요 수를 증가시키는 메서드를 추가할 수 있습니다.
//    public void incrementHearts() {
//        this.hearts++;
//    }
//
//    // 비밀번호 설정 시 해시 처리 메서드 예시
//    public void setPassword(String rawPassword) {
//        this.password = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
//    }
}
