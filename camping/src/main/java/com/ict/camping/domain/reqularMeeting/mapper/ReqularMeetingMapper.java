package com.ict.camping.domain.reqularMeeting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ict.camping.domain.reqularMeeting.vo.ReqularMeetingVO;

@Mapper
public interface ReqularMeetingMapper {

  void createMeeting(ReqularMeetingVO meeting);

  List<ReqularMeetingVO> selectAllMeetings();

  ReqularMeetingVO selectMeetingById(int meetingId);

  void insertMeeting(ReqularMeetingVO meeting);
}
