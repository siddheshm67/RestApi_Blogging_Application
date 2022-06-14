package com.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponce {
		
	private String message;
	private boolean success;
}
