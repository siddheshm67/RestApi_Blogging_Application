package com.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
	
	
	private int id;
	
	@NotEmpty(message = "field must be not blank")
	@Size(min = 3,message = "name must be greater then 3 letters")
	private String name;
	
	@Email(message = "given email address not match criteria")
	private String email;
	
	@NotEmpty(message = "field must be not blank")
	@Size(min = 3,max = 12,message = "password must be 3 to 13 characters")
	private String password;
	
	@NotEmpty(message = "field must be not blank")
	private String aboutString;
}
