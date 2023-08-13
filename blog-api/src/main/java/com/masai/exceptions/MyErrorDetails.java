package com.masai.exceptions;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyErrorDetails {
	private LocalDate timeStamp;
	private String message;
	private String discription;

	@Override
	public String toString() {
		return "MyErrorDetails [timeStamp=" + timeStamp + ", message=" + message + ", discription=" + discription + "]";
	}

}
