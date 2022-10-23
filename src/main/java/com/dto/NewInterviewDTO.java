package com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewInterviewDTO {
  private int candidateId;
  private int employerId;
  private int jobId;
}
