package com.CrmService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class TaskStatusCountDto {
	  private int userId;
	    private long completed;
	    private long inProgress;
	    private long paused;
	    private long delayed;
	    private long pending;
	    
	    public TaskStatusCountDto(long completed, long inProgress, long paused, long delayed, long pending) {
	        this.completed = completed;
	        this.inProgress = inProgress;
	        this.paused = paused;
	        this.delayed = delayed;
	        this.pending = pending;
	    }
}
