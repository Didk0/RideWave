package com.ride.wave.driver.payload;

import lombok.Data;

@Data
public class CreateDriverRequest {

  private String name;
  private String vehicle;
}
