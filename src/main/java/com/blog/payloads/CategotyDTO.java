package com.blog.payloads;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategotyDTO {
	
	
	private int id;
	
	@NotBlank(message = "field should be not blank")
	private String title;
	
	@NotBlank(message = "field should be not blank")
	private String description;
	
}
