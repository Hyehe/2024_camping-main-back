package com.ict.camping.map.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ict.camping.map.vo.MapVO;
@Mapper
public interface CampingMapper {
    // 모든 캠핑장 데이터를 조회하는 메서드
    @Select("SELECT * FROM campingsites")
    List<MapVO> findAllCampingSites();

     // contentId를 사용하여 특정 캠핑장 정보를 조회하는 메서드
     @Select("SELECT * FROM campingsites WHERE contentId = #{contentId}")
     MapVO findCampingSiteByContentId(String contentId);
}