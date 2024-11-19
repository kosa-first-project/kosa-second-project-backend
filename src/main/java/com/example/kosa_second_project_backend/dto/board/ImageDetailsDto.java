package com.example.kosa_second_project_backend.dto.board;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class ImageDetailsDto {
    private String originName;
    private String saveName;
    private String imagePath;
    private Long imageSize;

    @Builder
    public ImageDetailsDto(String originName, String saveName, String imagePath, Long imageSize) {
        this.originName = originName;
        this.saveName = saveName;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
    }
}