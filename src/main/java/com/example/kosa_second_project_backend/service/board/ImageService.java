package com.example.kosa_second_project_backend.service.board;

import com.example.kosa_second_project_backend.entity.board.Board;
import com.example.kosa_second_project_backend.entity.board.Image;
import com.example.kosa_second_project_backend.dto.board.ImageDetailsDto;
import com.example.kosa_second_project_backend.repository.board.BoardRepository;
import com.example.kosa_second_project_backend.repository.board.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final String absolutePath = Paths.get("C:", "image").toString();
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public void saveImage(Long boardId, List<MultipartFile> images) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("페이지가 존재하지 않습니다."));
        List<Image> imageList = imageRepository.findAllByBoard_BoardId(boardId);
        List<String> originNameList = new ArrayList<>();

        for (Image image : imageList) {
            originNameList.add(image.getOriginName());
        }

        for (MultipartFile file : images) {
            if (!(originNameList.contains(file.getOriginalFilename()))) {
                ImageDetailsDto imageDetailsDto = uploadImage(file);
                Image image = Image.builder()
                        .originName(imageDetailsDto.getOriginName())
                        .saveName(imageDetailsDto.getSaveName())
                        .imagePath(imageDetailsDto.getImagePath())
                        .imageSize(imageDetailsDto.getImageSize())
                        .board(board)
                        .build();
                imageRepository.save(image);
            }
        }
    }

    private ImageDetailsDto uploadImage(MultipartFile file) {
        String saveFileName = createSaveFileName(file.getOriginalFilename());
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uploadPath = Paths.get(getUploadPath(now), saveFileName).toString();
        File uploadFile = new File(uploadPath);

        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ImageDetailsDto.builder()
                .originName(file.getOriginalFilename())
                .saveName(saveFileName)
                .imagePath(uploadPath)
                .imageSize(file.getSize())
                .build();
    }

    @Transactional
    public void updateImage(Long boardId) {
        List<Image> images = imageRepository.findAllByBoard_BoardId(boardId);

        if (!images.isEmpty()) {
            for (Image image : images) {
                deleteImage(image);
            }
            imageRepository.deleteAll(images);
        }
    }

    public void deleteImage(Image image) {
        File deleteFile = new File(image.getImagePath());
        deleteFile.delete();
    }

    private String createSaveFileName(String filename) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    private String getUploadPath(String addPath) {
        return makeDirectories(Paths.get(absolutePath, addPath).toString());
    }

    private String makeDirectories(String absolutePath) {
        File dir = new File(absolutePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath();
    }
}