package emp_mgt_sys;

public class Main {

	public static void main(String[] args)  {
		String filePath = "C:\\Users\\sunee\\OneDrive\\Documents\\LITMUS LEARNING\\Java\\employee_data.csv";

		CSVReader reader = new CSVReader();
		try {
			reader.fetchData(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
