package com.ict.camping.domain.reqularMeeting.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ict.camping.domain.reqularMeeting.service.ReqularMeetingService;
import com.ict.camping.domain.reqularMeeting.vo.ReqularMeetingVO;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/regular-meetings")
public class ReqularMeetingController {

  @Autowired
  private ReqularMeetingService reqularMeetingService;

  // 1) 정규 모임 생성
  @PostMapping
  public Map<String, Object> createMeeting(@RequestBody ReqularMeetingVO meeting) {
    // @RequestBody로 받은 JSON 데이터를 VO에 매핑
    int meetingId = reqularMeetingService.createMeeting(meeting);

    // 만약 해시태그들을 같이 넘긴다면, 그 해시태그들에 대해 insertMeetingHashtags를 진행
    // (이 로직은 필요한 형태로 구현)
    for (String tag : ((ReqularMeetingVO) meeting).getHashtagsList()) {
      int hashId = reqularMeetingService.findOrCreateHashtag(tag);
      reqularMeetingService.insertMeetingHashtags(meetingId, hashId);
    }

    return Map.of("success", true, "meeting_idx", meetingId);
  }

  // 2) 전체 목록 조회
  @GetMapping
  public List<Map<String, Object>> getAllMeetings(@RequestParam(required = false, defaultValue = "0") int user_idx) {
    // user_idx가 0이라면 비로그인 유저라 간주할 수도 있고, 아니면 로직에 따라 다르게 처리
    return reqularMeetingService.selectAllMeetings(user_idx);
  }

  // 3) 특정 모임 상세조회
  @GetMapping("/{meetingId}")
  public ReqularMeetingVO getMeetingById(@PathVariable("meetingId") int meetingId) {
    return reqularMeetingService.selectMeetingById(meetingId);
  }

  // 좋아요
  @PostMapping("/{meetingId}/favorite")
  public Map<String, Object> toggleFavorite(
      @PathVariable int meetingId,
      @RequestParam("user_idx") int userIdx, Object trueOrFalse) {
          // 1) favorite 테이블(regular_meetings_favorites)에
          // user_idx + meeting_idx 조합으로 검색
          // 있으면 -> remove
          // 없으면 -> insert
          // 2) return json
          return Map.of("success", true, "favorite", trueOrFalse);
  }

  // 이미지 업로드
  @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> createMeeting(
      @RequestParam("name") String name,
      @RequestParam("description") String description,
      @RequestParam("region") String region,
      @RequestParam("subregion") String subregion,
      @RequestParam("personnel") int personnel,
      @RequestParam("hashtags") String hashtags,
      @RequestParam(value = "file", required = false) MultipartFile file) {
    try {
      // 파일 처리
      String fileName = file.getOriginalFilename();
      Path uploadDir = Paths.get("uploads/");
      if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
      }
      Path filePath = uploadDir.resolve(fileName);
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      // 모임 데이터 저장
      ReqularMeetingVO meeting = new ReqularMeetingVO();
      meeting.setName(name);
      meeting.setDescription(description);
      meeting.setProfile_image(fileName);
      meeting.setRegion(region);
      meeting.setSubregion(subregion);
      meeting.setPersonnel(personnel);

      // 리더 ID 가져오기 (예: 로그인 사용자)
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();
      meeting.setLeader_idx(1); // userService.findUserIdxByUsername(username)로 수정 가능

      int meetingIdx = reqularMeetingService.createMeeting(meeting);

      // 해시태그 연결
      for (String hashtag : hashtags.split(",")) {
        int hashtagIdx = reqularMeetingService.findOrCreateHashtag(hashtag.trim());
        reqularMeetingService.insertMeetingHashtags(meetingIdx, hashtagIdx);
      }

      return ResponseEntity.ok("Regular meeting created successfully!");
    } catch (IOException e) {
      log.error("File upload failed", e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file upload");
    } catch (Exception e) {
      log.error("Error creating meeting", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating meeting");
    }
  }
}
