package com.ict.camping.map.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.ict.camping.map.service.CampingService;
import com.ict.camping.map.vo.MapVO;

@RestController
@RequestMapping("/api/camping")
public class CampingController {

    @Autowired
    private CampingService campingService;

    // 특정 지역의 캠핑장 목록 조회
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/sites")
    public List<MapVO> getCampingSites() {
        List<MapVO> campingSites = campingService.getAllCampingSites();
        System.out.println("아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ");
        return campingSites;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/sites/{contentId}")
    public MapVO getCampingSite(@PathVariable("contentId") String contentId) {
        System.out.println("컨텐트 아이디가 제대로 오는지 보기 위한 sysout : "+contentId);
        return campingService.findCampingSiteByContentId(contentId);
    }
}
