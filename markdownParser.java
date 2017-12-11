// CSE 114 Homework 03
// markdownParser.java
// Fall 2017, Stony Brook University
// Name: Alwin Joseph
// SBU ID #: 110866970

import java.util.regex.*;

public class markdownParser {
	private boolean list_bool;
	//public String input;

	public markdownParser() {
		this.list_bool = false;
	}
	
	public String parseString(String input){
	   //Use of Regular Expressions to parse and breakup input strings
	   Pattern strong = Pattern.compile("(.*?)\\*\\*(.*?)\\*\\*(.*)");
	   Pattern emph = Pattern.compile("(.*?)\\*(.*?)\\*(.*)");
	   Pattern image = Pattern.compile("(.*?)!\\[(.*?)\\]\\((.*?)\"(.*?)\"\\)(.*)"); //might need \\ for ! 
	   Pattern hl = Pattern.compile("(.*?)\\[(.*?)\\]\\((.*?)\\)(.*)");
	   Pattern code = Pattern.compile("(.*?)`(.*?)`(.*)");
	   Pattern list = Pattern.compile("\\+ (.*)");
	   
	   Matcher m = list.matcher(input);
	   
	   //strong detection
	   m = strong.matcher(input);
	   if (m.matches()) {
		   String ret = "";
		   for (int i = 0; i < m.groupCount(); i++) {
		   		ret += m.group(i+1);
		   }
		   return parseString(m.group(1)) + translateStrong(m.group(2)) + parseString(m.group(3));
	   }
	   
	   //emphasis detection
	   m = emph.matcher(input);
	   if (m.matches()) {
		   String ret = "";
		   for (int i = 0; i < m.groupCount(); i++) {
			   ret += m.group(i+1);
		   }
		   return parseString(m.group(1)) + translateEmphasis(m.group(2)) + parseString(m.group(3));
	   }
	  
	   //image detection
	   m = image.matcher(input);
	   if (m.matches()) {
		   String ret = "";
		   for (int i = 0; i < m.groupCount(); i++) {
			   ret += m.group(i+1);   
		   }
		   return parseString(m.group(1)) + translateImage(m.group(2), m.group(3), m.group(4)) + parseString(m.group(5));
	   }
	   
	   //hyperlink detection
	   m = hl.matcher(input);
	   if (m.matches()) {
		   String ret = "";
		   for (int i = 0; i < m.groupCount(); i++) {
			   ret += m.group(i+1);
		   }
		  return parseString(m.group(1)) + translateHyperlink(m.group(2), m.group(3)) + parseString(m.group(4));
	   }
	   
	   //code detection
	   m = code.matcher(input);
	   if (m.matches()) {
		   String ret = "";
		   for (int i = 0; i < m.groupCount(); i++) {
			   ret += m.group(i+1);
		   }
		  return parseString(m.group(1)) + translateCode(m.group(2)) + parseString(m.group(3));
	   }
	   
	   //list detection
	   m = list.matcher(input);
	   if(m.matches()) {
		   String ret = "";
		   for (int i = 0; i < m.groupCount(); i++) {
			   ret += m.group(i+1);
		   }
		   if (list_bool) {
			   //process normally
			   if (m.matches()) {
				  return translateListItem(m.group(1));
			   }
		   } 
		   else {
			   list_bool=true;
			   //write the <ul> to start of input
			   return "<ul>\n" + translateListItem(m.group(1)); //need for first list item
		   }
	   } else if (list_bool) {
		   list_bool=false;
		   //close the </ul> tag
		   input = input + "</ul>\n";
	   }
	   
	   return input;

   }
	
   //method to emphasis text
   public String translateEmphasis(String inputText) {	
       return "<em>" + inputText + "</em>";
   }
   //method to make 'strong' text
   public String translateStrong(String inputText) {  
       return "<strong>" + inputText + "</strong>";
   }
   //method for hyperlinks
   public String translateHyperlink(String linkText, String URL) { 
       return "<a href=\"" + URL + "\">" + linkText +"</a>";
   }
   //method for image tags
   public String translateImage(String alternateText, String imagePath, String imageTitle) { 
       return "<img src=\"" + imagePath + "\" alt=\"" + alternateText + "\" title=\"" + imageTitle + "\">";
   }
   //method for code formated text
   public String translateCode(String inputCode) { 
       return "<code>" + inputCode + "</code>";
   }
   //method for HTML lists
   public String translateListItem(String listItem) {  
       return "<li>" + listItem + "</li>";
   }
   //setter method for list detection boolean variable
   public void setListBool(boolean b) {
	   this.list_bool = b;
   }

}