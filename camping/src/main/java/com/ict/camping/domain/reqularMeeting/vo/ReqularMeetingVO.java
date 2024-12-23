package com.ict.camping.domain.reqularMeeting.vo;

import java.security.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqularMeetingVO {
  private int meeting_idx;
  private String name, description, profile_image, region, subregion;
  private int leader_idx, personnel;
  private Timestamp created_at;
  // private MultipartFile file;
  public String[] getHashtagsList() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getHashtagsList'");
  }



}
