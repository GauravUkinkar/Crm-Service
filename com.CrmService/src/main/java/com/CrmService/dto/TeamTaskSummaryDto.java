package com.CrmService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class TeamTaskSummaryDto {
	 private String teamName;
	    private long todayEntries;
	    private long entriesCount;
	    private long completedEntries;
	    private long inProgressEntries;
	    private long delayedEntries;
	    private long pausedEntries;
	    private long pendingEntries;
}
