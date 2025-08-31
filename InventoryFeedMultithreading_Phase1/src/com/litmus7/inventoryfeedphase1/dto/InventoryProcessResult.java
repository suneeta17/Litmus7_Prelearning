package com.litmus7.inventoryfeedphase1.dto;

public class InventoryProcessResult {
	
	private int totalFiles;
	private int processedFiles;
	private int errorFiles;
	
	public InventoryProcessResult(int totalFiles , int processedFiles, int errorFiles) {
		this.totalFiles = totalFiles;
		this.processedFiles = processedFiles;
		this.errorFiles = errorFiles;
	}

	public int getTotalFiles() {
		return totalFiles;
	}

	public int getProcessedFiles() {
		return processedFiles;
	}

	public int getErrorFiles() {
		return errorFiles;
	}
	
	

}
