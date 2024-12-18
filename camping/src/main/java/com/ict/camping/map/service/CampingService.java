package com.ict.camping.map.service;

import java.util.List;

import com.ict.camping.map.vo.MapVO;

public interface CampingService {
    // 모든 캠핑장 데이터를 조회하는 메서드
    List<MapVO> getAllCampingSites();
    // 디테일
    MapVO findCampingSiteByContentId(String contentId);
}