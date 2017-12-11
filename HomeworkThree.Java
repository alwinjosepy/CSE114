// CSE 114 Homework 03
// HomeworkThree.java
// Fall 2017, Stony Brook University
// Name: Alwin Joseph
// SBU ID #: 110866970

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class HomeworkThree{
   public static void main(String[] args) {	//main method to read/write the file contents and convert to HTML4/5
	   markdownParser mp = new markdownParser();
       Scanner input = new Scanner(System.in);
       System.out.println("Enter name of Input file: ");
       String inputFile = input.next();
       System.out.println("Enter name of Output file: ");
       String outputFile = input.next();
       PrintWriter pw;
       
       File inFile = new File(inputFile);
       
       Scanner fs = null;
	try {
		fs = new Scanner(inFile);
	} catch (FileNotFoundException e1) {  
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} // call Scanner methods to read input
	
	try{
 	   File fo = new File(outputFile);
 	   pw = new PrintWriter(fo);
    }  catch (FileNotFoundException e){
 	   e.printStackTrace();
 	   return;
    }
	//File start boilerplate
	pw.write("<!DOCTYPE html>\n<html>\n<head>\n" + "<title>Results of Markdown Translation</title>\n</head>\n<body>\n\n"); 
	
    while (fs.hasNextLine()){
    	//Paragraph tag handling
    	String line = fs.nextLine(); // read the next line of the file  
    		if(line.equals("\n") || line.equals(" ") || line.equals("\t") || line.equals("\r")){  
    			pw.write("<p>");
          }
        String something = mp.parseString(line);
        System.out.println(something);
     	pw.write(something + "\n");
    }
    
	//File end boilerplate
	pw.write("\n</body>\n</html>");
	pw.flush();
	pw.close();
   }
}