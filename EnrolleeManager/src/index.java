import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class index {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		readFile();
	}

	public static void readFile() {

		// Scanner scanner = new Scanner("enrollees.csv");
		Scanner scan = new Scanner(System.in);
		Scanner file = null;

		while (true) {
			System.out.print("Enter the file name: ");
			String fileName = scan.nextLine();

			try {
				file = new Scanner(new File(fileName));
				System.out.println("File Found!");
				break;

			} catch (FileNotFoundException e) {

				System.out.println("Could not open file! \n");
				continue;
			}

		}
		
		
		ArrayList<String> fileLines = new ArrayList<String>();
		while(file.hasNextLine()) {
			fileLines.add(file.nextLine());
		}
		
		
		ArrayList<String> companies = new ArrayList<String>();
		String line;
		int x = 1;
		// get insurance companies
		while(!fileLines.isEmpty()) {
			line = fileLines.get(x);
			String[] data = line.split(",");
			String insCompany = data[3]; 
			
			if(companies.isEmpty()) { 
				companies.add(insCompany);
			}
			else {
				int found = -1;
				for(int i = 0; i < companies.size(); i++) {
					String lineCompany = companies.get(i);
					
					
					if(lineCompany.compareToIgnoreCase(insCompany) == 0) {
						found = 0;
						continue;
					}
					
				}
				if(found == -1) {
					companies.add(insCompany);
				}
			}
			x++;
		}
//		
		// for each insurance company found, create a csv file and populate with corresponding enrollees
		for(int j = 0; j < companies.size(); j++) {
			
			ArrayList<String> companyArrayList = new ArrayList<String>();
			for(String fLine : fileLines) {
				String[] fLineData = fLine.split(",");
				
				if(fLineData[3] == companies.get(j)) {
					
					companyArrayList.add(fLine);
					
				}
			}
			
			
			//sort array by first and last name using bubble sort
			for (int outer = 0; outer < companyArrayList.size() - 1; outer++) {
				for (int inner = companyArrayList.size() -1; inner > outer; inner--) {
					
					String[] innerData = companyArrayList.get(inner).split(",");
					String innerName = innerData[1];
					
					String[] innerDataNext = companyArrayList.get(inner - 1).split(",");
					String innerNameNext = innerDataNext[1];
					
					if(innerName.compareToIgnoreCase(innerNameNext) > 0) {
						String temp = companyArrayList.get(inner);
						
						companyArrayList.set(inner, companyArrayList.get(inner -1));
						companyArrayList.set((inner - 1), temp);
					}
					
				}
			}
			
			
			//name for csv file
			String name = companies.get(j);
			name = name.concat(".csv");
			
			FileWriter fW;
			//create file new file writer and file
			try {
				 fW = new FileWriter(name);
				 fW.append("User ID,First and Last Name,Version,Insurance Company");
				 
				 for(int i = 0; i < companyArrayList.size(); i++) {
					 fW.append(companyArrayList.get(i));
				 }
			} catch (IOException e) {
				System.out.println("could not create file: " + name);
			}
			
		
			
		}

		
	}
}
