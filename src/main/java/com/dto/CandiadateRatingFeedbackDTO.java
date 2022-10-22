package com.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandiadateRatingFeedbackDTO {

	private int rating;
	private String feedback;
}
