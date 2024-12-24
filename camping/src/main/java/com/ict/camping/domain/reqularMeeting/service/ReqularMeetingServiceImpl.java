package com.ict.camping.domain.reqularMeeting.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.camping.domain.chat.mapper.ChatRoomMapper;
import com.ict.camping.domain.chat.vo.ChatRoomVO;
import com.ict.camping.domain.reqularMeeting.mapper.ReqularMeetingMapper;
import com.ict.camping.domain.reqularMeeting.vo.ReqularMeetingVO;

@Service
public class ReqularMeetingServiceImpl implements ReqularMeetingService {

  @Autowired
  private ReqularMeetingMapper reqularMeetingMapper;

  @Autowired
  private ChatRoomMapper chatRoomMapper; // 채팅방 생성용

  // 1) 정규 모임 생성
  @Override
  public int createMeeting(ReqularMeetingVO meeting) {
    // 1) 모임 DB 삽입
    reqularMeetingMapper.insertMeeting(meeting); 
    // insert 후에는 meeting 객체 내부에 생성된 PK(meeting_idx)가 세팅됨

    // 2) 채팅방 생성(원하실 경우)
    ChatRoomVO chatRoom = new ChatRoomVO(null);
    chatRoom.setRoomId("meeting_" + meeting.getMeeting_idx());
    chatRoom.setName("Chat for " + meeting.getName());
    chatRoomMapper.createChatRoom(chatRoom);

    return meeting.getMeeting_idx(); 
  }

  // 2) 특정 모임 조회
  @Override
  public ReqularMeetingVO selectMeetingById(int meetingId) {
    return reqularMeetingMapper.selectMeetingById(meetingId);
  }

  // 3) 전체 모임 목록
  @Override
  public List<Map<String, Object>> selectAllMeetings(int user_idx) {
    return reqularMeetingMapper.selectAllMeetings(user_idx);
  }

  // 4) 해시태그 찾거나 생성
  @Override
  public int findOrCreateHashtag(String hashtag) {
    Integer hashtagIdx = reqularMeetingMapper.findHashtagByName(hashtag);
    if (hashtagIdx == null) {
      reqularMeetingMapper.insertHashtag(hashtag);
      hashtagIdx = reqularMeetingMapper.findHashtagByName(hashtag);
    }
    return hashtagIdx;
  }

  // 5) 모임-해시태그 연결
  @Override
  public void insertMeetingHashtags(int meetingIdx, int hashtagIdx) {
    reqularMeetingMapper.insertMeetingHashtags(meetingIdx, hashtagIdx);
  }

  @Override
    public boolean toggleFavorite(int userIdx, int meetingIdx) {
        // 좋아요 상태 확인
        boolean isLiked = reqularMeetingMapper.checkFavorite(userIdx, meetingIdx);

        if (isLiked) {
            // 좋아요 상태라면 삭제
            reqularMeetingMapper.deleteFavorite(userIdx, meetingIdx);
            return false; // 좋아요 취소 상태
        } else {
            // 좋아요 상태가 아니라면 추가
            reqularMeetingMapper.insertFavorite(userIdx, meetingIdx);
            return true; // 좋아요 상태
        }
    }

}
