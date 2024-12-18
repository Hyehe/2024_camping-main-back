package com.ict.camping.domain.reqularMeeting.service;

import java.util.List;
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
    private ChatRoomMapper chatRoomMapper;

    @Override
    public void createMeeting(ReqularMeetingVO meeting) {
        // 정규 모임 저장
        reqularMeetingMapper.insertMeeting(meeting);

        // 새로운 채팅방 생성
        ChatRoomVO chatRoom = new ChatRoomVO(null);
        chatRoom.setRoomId("meeting_" + meeting.getMeetingIdx());
        chatRoom.setName("Chat for " + meeting.getName());
        chatRoomMapper.createChatRoom(chatRoom);
    }

    @Override
    public List<ReqularMeetingVO> selectAllMeetings() {
      return reqularMeetingMapper.selectAllMeetings();
    }

    @Override
    public ReqularMeetingVO selectMeetingById(int meetingId) {
      return reqularMeetingMapper.selectMeetingById(meetingId);
    }
}
