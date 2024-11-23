package com.example.kosa_second_project_backend.dto.board;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class ImageDetailsDto { // 데이터 전송 객체(DTO) 서비스 계층과 프레젠테이션 계층 간에 사용된다.
    private String originName; // 원본 이미지 파일의 이름을 저장
    private String saveName; // 서버에 저장된 이미지 파일의 이름 저장(중복 피하기 위함)
    private String imagePath; // 이미지 파일이 저장된 경로 저장
    private Long imageSize; // 이미지 파일의 크기를 바이트 단위로 저장

    @Builder // 필드가 많거나 선택적으로 지정해야할 경우 유용하게 사용 가능
    public ImageDetailsDto(String originName, String saveName, String imagePath, Long imageSize) {
        this.originName = originName;
        this.saveName = saveName;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
    }
}
// DTO가 REST API의 응답으로 사용될 때, 필요한 경우 Jackson 애노테이션을 사용하여 JSON 직렬화 방식을 제어할 수 있다.