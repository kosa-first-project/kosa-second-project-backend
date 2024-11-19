package com.example.kosa_second_project_backend.entity.board;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Image extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String originName;
    private String saveName;
    private String imagePath;
    private Long imageSize;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}