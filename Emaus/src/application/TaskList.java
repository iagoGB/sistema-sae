package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TaskList {
	public static String lista() throws Exception {
		String line;
		String pidInfo ="";
	
		Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
	
		BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = input.readLine()) != null) {
			pidInfo+=line; 
			System.out.println(line);
		}
		input.close();
		
		return pidInfo;
	}
}
