package com.litmus7.inventoryfeedphase3.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.litmus7.inventoryfeedphase3.dao.InventoryFeedDao;
import com.litmus7.inventoryfeedphase3.dto.InventoryProcessResult;
import com.litmus7.inventoryfeedphase3.dto.Product;
import com.litmus7.inventoryfeedphase3.exception.InventoryDaoException;
import com.litmus7.inventoryfeedphase3.exception.InventoryServiceException;
import com.litmus7.inventoryfeedphase3.util.CSVReader;
import com.litmus7.inventoryfeedphase3.util.ValidationUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InventoryFeedService {
	
    private static final Logger logger = LogManager.getLogger(InventoryFeedService.class);

	private InventoryFeedDao inventoryFeedDao = new InventoryFeedDao();
	
	public InventoryProcessResult processInventoryFeeds() throws InventoryServiceException, IOException {
		logger.debug("Entering processInventoryFeeds method");

		// Step 1: Load config
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("config.properties"));
			logger.info("Loaded configuration from config.properties");
		} catch (FileNotFoundException e) {
			logger.error("config.properties file not found", e);
			throw new InventoryServiceException("File not found exception", e);
		} catch (IOException e) {
			logger.error("Error reading config.properties", e);
			throw new InventoryServiceException("Input output exception", e);
		}
		
		Path inputFolder = Paths.get(props.getProperty("input.folder"));
		Path processedFolder = Paths.get(props.getProperty("processed.folder"));
		Path errorFolder = Paths.get(props.getProperty("error.folder"));
		logger.debug("Input folder: {}, Processed folder: {}, Error folder: {}", inputFolder, processedFolder, errorFolder);
		
		// Step 2: Load files
		List<Path> files = Files.list(inputFolder)
								.filter(Files::isRegularFile)
								.collect(Collectors.toList());
		
		int totalFiles = files.size();
		AtomicInteger processedFileCount = new AtomicInteger(0);
		AtomicInteger errorFileCount = new AtomicInteger(0);

		logger.info("Found {} files in input folder", totalFiles);
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(Path file  : files) {
			executor.submit (()-> {
				try {
					processingSingleFile(file,processedFolder,
							errorFolder, processedFileCount, errorFileCount);
				} catch (IOException e) {
					 logger.error("Error processing file: {}", file.getFileName(), e);
				}
			});
			
		}
		
		executor.shutdown();
		
		try {
			if(!executor.awaitTermination(1,TimeUnit.HOURS)) {
				executor.shutdown();
			}
		}catch(InterruptedException e) {
			logger.error("Executor interrupted while for tasks to finish",e);
			executor.shutdown();
			Thread.currentThread().interrupt();
		}
		
		logger.info("Inventory feed processing completed. Total: {}, Processed: {}, Errors: {}", totalFiles, processedFileCount, errorFileCount);
		logger.debug("Exiting processInventoryFeeds method");
		
		return new InventoryProcessResult(totalFiles, processedFileCount, errorFileCount);
	}
			
		//Method to process files	
		private void processingSingleFile(Path file,Path processedFolder,Path errorFolder, AtomicInteger processedFileCount, AtomicInteger errorFileCount) throws IOException {
		logger.info("Processing file: {}", file.getFileName());
		List<String[]> data = CSVReader.loadData(file);
		boolean allValid = true;
		
		for(String[] record : data) {
			if (!ValidationUtility.isInventoryRecordValid(record)) {
				logger.warn("Invalid record found in file {}. Moving to error folder.", file.getFileName());
				Files.move(file, errorFolder.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
				errorFileCount.incrementAndGet();
				allValid = false;
				return;
			}
		}
		
		if(allValid) {
			List<Product> products = data.stream()
					.map(tokens -> new Product(
			                Integer.parseInt(tokens[0].trim()),
			                tokens[1].trim(),
			                Integer.parseInt(tokens[2].trim()),
			                Double.parseDouble(tokens[3].trim())))
			            .collect(Collectors.toList());
			
			try {
				boolean inserted = inventoryFeedDao.addInventoryFeeds(products);
				
				if(inserted) {
					Files.move(file, processedFolder.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
					processedFileCount.incrementAndGet();
					logger.info("File {} processed successfully and moved to processed folder.", file.getFileName());
				} else {
					Files.move(file, errorFolder.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
					errorFileCount.incrementAndGet();
					logger.error("File {} could not be inserted into DB. Moved to error folder.", file.getFileName());
				}
			} catch (InventoryDaoException e) {
				Files.move(file, errorFolder.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
				errorFileCount.incrementAndGet();
				logger.error("Error inserting data from file {} into DB. Moved to error folder.", file.getFileName(), e);
				
			}
		}
	}
	
	
}

