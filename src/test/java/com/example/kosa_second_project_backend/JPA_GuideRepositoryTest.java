package com.example.kosa_second_project_backend;

import com.example.kosa_second_project_backend.entity.guide.GuideInfo;
import com.example.kosa_second_project_backend.repository.guide.GuideRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class JPA_GuideRepositoryTest {

    @Autowired
    private GuideRepository guideRepository;

    // 테스트 전 데이터 삽입
    @BeforeEach
    void setupData() {
         guideRepository.deleteAll(); // 모든 데이터를 삭제

        // 테스트용 데이터 삽입
        GuideInfo guide1 = new GuideInfo();
        guide1.setUserId("bot1");
        guide1.setText("안녕하세요. 이 가이드는 Spring Boot로 개발되었습니다.");
        guide1.setTitle("봇1 가이드");
        guide1.setCity("서울");
        guide1.setCareer("5년");
        guide1.setCapacity(5);
        guide1.setWeekdayPrice(20000);
        guide1.setBoardRating(4);
        guide1.setLikeCount(10);
        guide1.setHits(100);
        guide1.setGuideInfoState("activate");

        guideRepository.save(guide1);

        GuideInfo guide2 = new GuideInfo();
        guide2.setUserId("bot2");
        guide2.setText("봇2의 가이드. 여기에 '봇'이라는 단어가 포함되어 있습니다.");
        guide2.setTitle("봇2 가이드");
        guide2.setCity("부산");
        guide2.setCareer("3년");
        guide2.setCapacity(4);
        guide2.setWeekdayPrice(25000);
        guide2.setBoardRating(5);
        guide2.setLikeCount(15);
        guide2.setHits(150);
        guide2.setGuideInfoState("activate");

        guideRepository.save(guide2);
    }


    @Test
    @Order(1)
    @Transactional
    void list() {
        List<GuideInfo> list = guideRepository.findAll();
        list.forEach(System.out::println);
        Assertions.assertEquals(2, list.size()); // 저장된 데이터 개수 검증
    }

    @Test
    @Order(2)
    @Transactional
    void findByTextContains() {
        List<GuideInfo> list = guideRepository.findByTextContains("안녕");
        list.forEach(System.out::println);
        Assertions.assertTrue(list.size() > 0); // '안녕'이 포함된 가이드가 있어야 함
    }

    @Test
    @Order(3)
    @Transactional
    void findByUserIdContains() {

        guideRepository.findAll().forEach(guide -> System.out.println("Stored Guide: " + guide));

        List<GuideInfo> list = guideRepository.findByUserIdContains("bot");
        list.forEach(System.out::println);
        Assertions.assertTrue(list.size() > 0); // '봇'을 포함한 userId가 있어야 함
    }

    @Test
    @Order(4) // 네 번째 테스트: 데이터 삭제
    @Transactional
    void deleteByIdTest() {
        // 첫 번째 데이터 (guide1)를 ID로 삭제
        List<GuideInfo> listBeforeDelete = guideRepository.findAll();
        Assertions.assertEquals(2, listBeforeDelete.size(), "삭제 전 데이터 개수");

        int guideIdToDelete = listBeforeDelete.get(0).getGuideInfoId();
        guideRepository.deleteById(guideIdToDelete);

        Optional<GuideInfo> deletedGuide = guideRepository.findById(guideIdToDelete);
        Assertions.assertFalse(deletedGuide.isPresent(), "삭제된 가이드 정보가 존재해서는 안 된다");

        List<GuideInfo> listAfterDelete = guideRepository.findAll();
        Assertions.assertEquals(1, listAfterDelete.size(), "삭제 후 데이터 개수");

        listAfterDelete.forEach(guide -> System.out.println("Remaining Guide: " + guide));

    }

    @Test
    @Order(5)
    @Transactional
    void deleteByUserIdTest() {
        List<GuideInfo> listBeforeDelete = guideRepository.findAll();
        Assertions.assertEquals(2, listBeforeDelete.size(), "삭제 전 데이터 개수");

        guideRepository.deleteByUserId("bot2");

        List<GuideInfo> listAfterDelete = guideRepository.findAll();
        Assertions.assertEquals(1, listAfterDelete.size(), "삭제 후 데이터 개수");

        listAfterDelete.forEach(guide -> System.out.println("Remaining Guide: " + guide));
    }
}
