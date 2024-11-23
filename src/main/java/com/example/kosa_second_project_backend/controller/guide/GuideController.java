package com.example.kosa_second_project_backend.controller.guide;

import com.example.kosa_second_project_backend.entity.guide.GuideInfo;
import com.example.kosa_second_project_backend.repository.guide.GuideRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*")  // CORS 설정 추가
@RestController  // JSON 반환
@RequestMapping("/api/guides")  // API URL 패턴 지정
public class GuideController {

    private final GuideRepository repository;

    public GuideController(GuideRepository repository) {
        this.repository = repository;
    }

    // 전체 가이드 리스트 - 페이지네이션
    @GetMapping("/GuideMain")
    public ResponseEntity<Map<String, Object>> getGuideInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<GuideInfo> list = repository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("list", list.getContent());
        response.put("totalElements", list.getTotalElements());
        response.put("totalPages", list.getTotalPages());

        System.out.println("GuideMain Response: " + response);
        return ResponseEntity.ok(response);
    }

    // 가이드 검색 - 상태와 키워드(제목/내용)
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchGuides(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // 상태 및 키워드 조건에 따른 필터링
        List<GuideInfo> results = repository.findAll();

        if (state != null && !state.equals("all")) {
            results = results.stream()
                    .filter(guide -> guide.getGuideInfoState().equals(state))
                    .collect(Collectors.toList());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            results = results.stream()
                    .filter(guide -> guide.getTitle().contains(keyword) || guide.getText().contains(keyword))
                    .collect(Collectors.toList());
        }

        // 페이징 처리
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int) pageable.getOffset(), results.size());
        int end = Math.min((start + pageable.getPageSize()), results.size());
        List<GuideInfo> paginatedResults = results.subList(start, end);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("list", paginatedResults);
        response.put("totalElements", results.size());
        response.put("totalPages", (int) Math.ceil((double) results.size() / size));

        return ResponseEntity.ok(response);
    }

    // 가이드 글 작성
    @PostMapping("/insert")
    @Transactional
    public ResponseEntity<Map<String, String>> insert(@RequestBody GuideInfo vo) {
        Map<String, String> response = new HashMap<>();
        try {
            vo.setHits(0);
            vo.setLikeCount(0);
            vo.setBoardRating(0);
            vo.setGuideInfoState("activate");
            repository.save(vo);
            response.put("message", "글 작성 성공");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "글 작성에 실패했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    // 가이드 상세 조회 및 조회수 증가
    @GetMapping("/one")
    public ResponseEntity<GuideInfo> one(@RequestParam("id") int id) {
        Optional<GuideInfo> result = repository.findById(id);
        if (result.isPresent()) {
            GuideInfo guideInfo = result.get();
            guideInfo.setHits(guideInfo.getHits() + 1);
            repository.save(guideInfo);
            return ResponseEntity.ok(guideInfo);
        } else {
            System.out.println("Guide not found with ID: " + id);
            return ResponseEntity.status(404).body(null);
        }
    }

    // 가이드 수정
    @PostMapping("/update")
    @Transactional
    public ResponseEntity<Map<String, String>> update(@RequestBody GuideInfo vo) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<GuideInfo> optionalEntity = repository.findById(vo.getGuideInfoId());
            if (optionalEntity.isPresent()) {
                GuideInfo entity = optionalEntity.get();
                entity.setTitle(vo.getTitle());
                entity.setText(vo.getText());
                entity.setCity(vo.getCity());
                entity.setCareer(vo.getCareer());
                entity.setCapacity(vo.getCapacity());
                entity.setWeekdayPrice(vo.getWeekdayPrice());
                entity.setBoardRating(vo.getBoardRating());
                entity.setGuideInfoState(vo.getGuideInfoState());
                repository.save(entity);

                response.put("message", "글 수정 성공");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "수정할 게시글이 없습니다.");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "글 수정에 실패했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    // 상태 변경
    @PutMapping("/{id}/state")
    @Transactional
    public ResponseEntity<Map<String, String>> updateGuideState(@PathVariable int id, @RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String newState = request.get("guideInfoState");

        try {
            Optional<GuideInfo> optionalGuide = repository.findById(id);
            if (optionalGuide.isPresent()) {
                GuideInfo guideInfo = optionalGuide.get();
                guideInfo.setGuideInfoState(newState);
                repository.save(guideInfo);

                response.put("message", "상태가 " + newState + "로 변경되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "해당 ID의 가이드 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "상태 변경 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    // 가이드 삭제
    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<Map<String, String>> delete(@RequestParam("id") int id) {
        Map<String, String> response = new HashMap<>();
        try {
            repository.deleteById(id);
            response.put("message", "글 삭제 성공");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "글 삭제에 실패했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }
}
