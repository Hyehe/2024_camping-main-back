package com.ict.camping.domain.reqularMeeting.vo;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqularMeetingVO {
  private int meetingIdx;
  private String name;
  private String description;
  private int leader_idx;
  private Timestamp createdAt;
  public void setRegion(String region) {
    
    throw new UnsupportedOperationException("Unimplemented method 'setRegion'");
  }
  public void setCapacity(int capacity) {
    
    throw new UnsupportedOperationException("Unimplemented method 'setCapacity'");
  }

}
