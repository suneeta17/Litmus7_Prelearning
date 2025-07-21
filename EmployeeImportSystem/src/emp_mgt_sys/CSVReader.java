package emp_mgt_sys;


import java.io.BufferedReader;
import java.io.FileReader;


public class CSVReader {
	
	//Fetches the Data from CSV file
	public void fetchData(String filepath) throws Exception {
		String line;
		String delimeter = ",";
		
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		
		br.readLine();
		
		while ((line = br.readLine()) != null) {
			String[] values = line.split(delimeter);
			
			
			EmployeeDBService dbService = new EmployeeDBService();
		
			if (Validator.recordValidator(values, dbService)) {
				dbService.insertEmployee(values);
			}	
		}
		br.close();
	}
	
}
