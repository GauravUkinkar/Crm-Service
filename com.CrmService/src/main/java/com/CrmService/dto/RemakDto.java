package com.CrmService.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class RemakDto {
	
	private int id;
	private String remark;

}
