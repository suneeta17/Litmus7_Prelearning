package com.litmus7.inventoryfeedphase2.dto;

import java.util.concurrent.atomic.AtomicInteger;

public class InventoryProcessResult {
	
	private int totalFiles;
	private AtomicInteger processedFiles;
	private AtomicInteger errorFiles;
	
	public InventoryProcessResult(int totalFiles , AtomicInteger processedFileCount, AtomicInteger errorFileCount) {
		this.totalFiles = totalFiles;
		this.processedFiles = processedFileCount;
		this.errorFiles = errorFileCount;
	}

	public int getTotalFiles() {
		return totalFiles;
	}

	public AtomicInteger getProcessedFiles() {
		return processedFiles;
	}

	public AtomicInteger getErrorFiles() {
		return errorFiles;
	}
	
	

}
