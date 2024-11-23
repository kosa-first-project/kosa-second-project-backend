package com.example.kosa_second_project_backend.entity.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// JPA 엔티티 클래스의 슈퍼클래스로 사용된다. (상속받는 Entity 클래스들은 이 클래스의 필드를 자신의 테이블에 포함시켜야 함)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 엔티티 리스너를 지정하여 감사 기능 활성화(엔티티 생성 및 수정 시점을 자동으로 기록)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 접근 제한자를 protected로 설정
@Getter
public class Time { // 다른 엔티티 클래스들이 상속받아 공통으로 사용하는 타임스탬프 필드를 정의

    @CreatedDate // 엔티티가 처음 생성될 때 createDate 필드에 자동으로 생성일자를 기록 Auditing 기능이 활성화되어 있을 때만 가능
    @Column(updatable = false) // createDate 필드가 DB에 삽입된 후에는 업데이트되지 않도록 설정
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // JSON 직렬화 시 날짜와 시간의 형식을 지정
    private LocalDateTime createDate; // 엔티티의 생성일자를 저장하는 필드

    @LastModifiedDate // 엔티티가 마지막으로 수정될 때 자동으로 수정일자를 기록
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate; // 엔티티의 마지막 수정일자를 저장하는 필드
}


/*
1. 공통 타임스탬프 필드 제공:
createDate와 modifiedDate 필드를 정의하여 엔티티의 생성 및 수정 시점을 자동으로 관리합니다.

2. 코드 중복 제거:
여러 엔티티 클래스에서 동일한 타임스탬프 필드를 반복적으로 정의하지 않고, Time 클래스를 상속받아 재사용할 수 있습니다.

3. 감사 기능 통합:
JPA의 감사 기능을 통해 데이터베이스의 생성 및 수정 정보를 자동으로 기록합니다.

4. JSON 직렬화 형식 지정:
API 응답 시 날짜와 시간의 형식을 일관되게 유지하여 클라이언트에서 처리하기 쉽게 합니다.*/
