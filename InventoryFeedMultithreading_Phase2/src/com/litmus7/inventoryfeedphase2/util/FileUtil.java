package com.litmus7.inventoryfeedphase2.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeedphase2.exception.InventoryFileOperationException;

public class FileUtil {
	 private static final Logger logger = LogManager.getLogger(FileUtil.class);

	    
	    public static void moveFileSafe(Path source, Path target) {
	        try {
	            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
	            logger.info("Moved file from {} to {}", source, target);
	        } catch (IOException e) {
	            logger.error("Failed to move file from {} to {}", source, target, e);
	            throw new InventoryFileOperationException("Failed to move file from " + source + " to " + target, e);
	        }
	    }
}
