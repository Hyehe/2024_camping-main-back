package com.ict.camping.domain.reqularMeeting.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.camping.domain.reqularMeeting.vo.HashtagVO;
import com.ict.camping.domain.reqularMeeting.vo.ReqularMeetingVO;

@Mapper
public interface ReqularMeetingMapper {

    // 1) 정규 모임 생성
    int insertMeeting(ReqularMeetingVO meeting);

    // 2) 전체 모임 목록
    List<Map<String, Object>> selectAllMeetings(@Param("user_idx") int user_idx);

    // 3) 특정 모임 조회
    ReqularMeetingVO selectMeetingById(int meetingId);

    // 4) 해시태그 존재 여부
    Integer findHashtagByName(String hashtag);

    // 5) 해시태그 신규 삽입
    void insertHashtag(String hashtag);

    // 6) 모임 - 해시태그 연결
    void insertMeetingHashtags(@Param("meeting_idx") int meeting_idx,
                               @Param("hashtag_idx") int hashtag_idx);

    // +) 해시태그 전체 목록 (필요시)
     List<HashtagVO> selectAllHashtags();

    // 좋아요 상태 확인
    boolean checkFavorite(@Param("user_idx") int userIdx, @Param("meeting_idx") int meetingIdx);

    // 좋아요 추가
    void insertFavorite(@Param("user_idx") int userIdx, @Param("meeting_idx") int meetingIdx);

    // 좋아요 삭제
    void deleteFavorite(@Param("user_idx") int userIdx, @Param("meeting_idx") int meetingIdx);
}
