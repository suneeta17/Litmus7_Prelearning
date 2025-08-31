package com.litmus7.inventoryfeedphase1.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class CSVReader {
	
	public static List<String[]> loadData(Path filePath) throws IOException{
		 List<String[]> data = new ArrayList<>();
	        try (BufferedReader br = Files.newBufferedReader(filePath)) {
	            br.readLine(); 
	            String line;
	            while ((line = br.readLine()) != null) {
	                if (!line.trim().isEmpty()) {
	                    data.add(line.split(","));
	                }
	            }
	        }
	        return data;
				
			}
	}
