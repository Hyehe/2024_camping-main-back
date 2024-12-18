package com.ict.camping.domain.reqularMeeting.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.ict.camping.domain.chat.vo.ChatRoomVO;
import com.ict.camping.domain.reqularMeeting.service.ReqularMeetingService;
import com.ict.camping.domain.reqularMeeting.vo.ReqularMeetingVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/regular-meetings")
public class ReqularMeetingController {
  @Autowired
  private ReqularMeetingService reqularMeetingService;

  // @Autowired
  // private PasswordEncoder passwordEncoder;

  @PostMapping
  public ResponseEntity<String> createMeeting(@RequestBody ReqularMeetingVO meeting) {
    reqularMeetingService.createMeeting(meeting);
    return ResponseEntity.ok("Regular meeting created successfully!");
  }

  @GetMapping
  public ResponseEntity<List<ReqularMeetingVO>> getAllMeetings() {
    List<ReqularMeetingVO> meetings = reqularMeetingService.selectAllMeetings();
    return ResponseEntity.ok(meetings);
  }

  @GetMapping("/{meetingId}")
  public ResponseEntity<?> getMeetingById(@PathVariable("meetingId") int meetingId) {
    ReqularMeetingVO meeting = reqularMeetingService.selectMeetingById(meetingId);
    if (meeting != null) {
      return ResponseEntity.ok(meeting);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meeting not found");
    }
  }

  @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<String> createMeeting(
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestParam("region") String region,
        @RequestParam(value = "subRegion", required = false) String subRegion,
        @RequestParam("hashtags") String hashtags,
        @RequestParam("capacity") int capacity,
        @RequestParam("file") MultipartFile file) {
    try {
        // 파일 처리 로직
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get("uploads/", fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 정규 모임 데이터 저장
        ReqularMeetingVO meeting = new ReqularMeetingVO();
        meeting.setName(name);
        meeting.setDescription(description);
        meeting.setLeader_idx(1); // 임시 leader_idx 값
        meeting.setRegion(region);
        meeting.setCapacity(capacity);

        reqularMeetingService.createMeeting(meeting);

        return ResponseEntity.ok("Regular meeting created successfully!");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating meeting: " + e.getMessage());
    }
}


}
