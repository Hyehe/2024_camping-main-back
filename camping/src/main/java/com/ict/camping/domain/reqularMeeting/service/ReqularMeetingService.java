package com.ict.camping.domain.reqularMeeting.service;

import java.util.List;

import com.ict.camping.domain.reqularMeeting.vo.ReqularMeetingVO;

public interface ReqularMeetingService {
  void createMeeting(ReqularMeetingVO meeting);

  List<ReqularMeetingVO> selectAllMeetings();

  ReqularMeetingVO selectMeetingById(int meetingId);

}
