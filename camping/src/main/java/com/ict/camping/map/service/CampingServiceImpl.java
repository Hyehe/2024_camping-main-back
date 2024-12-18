package com.ict.camping.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.camping.map.mapper.CampingMapper;
import com.ict.camping.map.vo.MapVO;

@Service
public class CampingServiceImpl implements CampingService{

    @Autowired
    private CampingMapper campingMapper;

    // 모든 캠핑장 데이터를 가져오는 메서드
    @Override
    public List<MapVO> getAllCampingSites() {
        return campingMapper.findAllCampingSites();
    }

    @Override
    public MapVO findCampingSiteByContentId(String contentId) {
        return campingMapper.findCampingSiteByContentId(contentId);
    }
}