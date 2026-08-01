package com.CrmService.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Message<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	private String responseMessage;

	private transient T data; // Marked as transient
//	
//	private transient List<?> list; // Marked as transient

	
	
	
}
