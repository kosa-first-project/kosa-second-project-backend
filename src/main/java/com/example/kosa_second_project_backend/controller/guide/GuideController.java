package com.example.kosa_second_project_backend.controller.guide;

import com.example.kosa_second_project_backend.model.entity.guide.GuideInfo;
import com.example.kosa_second_project_backend.repository.guide.GuideRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class GuideController {

    private final GuideRepository repository;

    public GuideController(GuideRepository repository) {
        this.repository = repository;
    }

    // 전체 가이드 리스트
    @RequestMapping("/GuideMain")
    public ModelAndView getGuideInfo(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {

        ModelAndView mav = new ModelAndView();
        Pageable pageable = PageRequest.of(page, size);
        Page<GuideInfo> list = repository.findAll(pageable);

        if (list.hasContent()) {
            mav.addObject("list", list);
        } else {
            mav.addObject("msg", "추출된 결과가 없어요");
        }

        mav.setViewName("GuideMain");
        return mav;
    }

    // 가이드 글 작성
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Transactional
    public String insert(GuideInfo vo, Model model) {
        try {
            // GuideInfo 엔티티 저장
            vo.setHits(0);  // 처음에는 조회수를 0으로 설정
            vo.setLikeCount(0); // 좋아요 수 초기화
            vo.setBoardRating(0); // 후기 평점 초기화
            vo.setGuideInfoState("activate"); // 상태 초기화
            repository.save(vo);
            return "redirect:/GuideMain";
        } catch (Exception e) {
            model.addAttribute("msg", "글 작성에 실패했습니다.");
        }
        return "GuideMain";
    }

    // 텍스트 검색
    @RequestMapping("/search")
    public ModelAndView search(@Param("keyword") String keyword) {

        //????일단 추가 에러피하기
        if (keyword == null || keyword.trim().isEmpty()) {
            // 검색어가 없으면 처리하지 않거나, 전체 목록을 반환
            return getGuideInfo(0, 10); // 예시로 기본적으로 전체 목록을 반환
        }

        List<GuideInfo> list = repository.findByTextContains(keyword);

        ModelAndView mav = new ModelAndView();
        if (!list.isEmpty()) {
            mav.addObject("list", list);
        } else {
            mav.addObject("msg", "추출된 결과가 없어요");
        }
        mav.setViewName("GuideMain");
        return mav;
    }

    // 작성자 검색
    @RequestMapping("/writer")
    public ModelAndView writer(@Param("keyword") String keyword) {
        List<GuideInfo> list = repository.findByUserIdContains(keyword);

        ModelAndView mav = new ModelAndView();
        if (!list.isEmpty()) {
            mav.addObject("list", list);
        } else {
            mav.addObject("msg", "추출된 결과가 없어요");
        }
        mav.setViewName("GuideMain");
        return mav;
    }

    // 가이드 상세 조회 및 조회수 증가
    @RequestMapping(value = "/one", produces = "application/json; charset=utf-8")
    @ResponseBody
    public GuideInfo one(@Param("id") int id) {
        Optional<GuideInfo> result = repository.findById(id);

        if (result.isPresent()) {
            GuideInfo guideInfo = result.get();
            guideInfo.setHits(guideInfo.getHits() + 1); // 조회수 증가
            repository.save(guideInfo); // 수정된 객체 저장
            return guideInfo;
        } else {
            throw new RuntimeException("GuideInfo not found with id: " + id);
        }
    }

    // 가이드 수정
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public String update(@RequestBody GuideInfo vo, Model model) {
        try {
            Optional<GuideInfo> optionalEntity = repository.findById(vo.getGuideInfoId());

            if (optionalEntity.isPresent()) {
                GuideInfo entity = optionalEntity.get();
                // 수정할 필드들 업데이트
                entity.setTitle(vo.getTitle());
                entity.setText(vo.getText());  // 텍스트도 수정 가능
                entity.setCity(vo.getCity());
                entity.setCareer(vo.getCareer());
                entity.setCapacity(vo.getCapacity());
                entity.setWeekdayPrice(vo.getWeekdayPrice());
                entity.setBoardRating(vo.getBoardRating());
                entity.setGuideInfoState(vo.getGuideInfoState()); // 상태 업데이트
                repository.save(entity);  // 수정된 객체 저장

                return "redirect:/GuideMain";
            } else {
                model.addAttribute("msg", "수정할 게시글이 없습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "글 수정에 실패했습니다.");
        }
        return "GuideMain";
    }

    // 가이드 삭제
    @RequestMapping(value = "/delete")
    @Transactional
    public ModelAndView delete(@Param("id") int id) {
        ModelAndView mav = new ModelAndView();
        try {
            repository.deleteById(id);
            mav.addObject("list", repository.findAll()); // 삭제 후 전체 리스트를 다시 로드
        } catch (Exception e) {
            mav.addObject("msg", "글 삭제에 실패했습니다.");
        }
        mav.setViewName("GuideMain");
        return mav;
    }
}
