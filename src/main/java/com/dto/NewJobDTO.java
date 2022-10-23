package com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewJobDTO {
  private String jobDescription;
  private String industry;
  private String jobStatus;
}
