import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FileLoader {
	
	public static HashMap<String, Person> getPersons(String filename) throws IOException
	{
		boolean skip = false;
		String temp = "";
		StringTokenizer data;
		Person tempP;
		HashMap<String, Person> persons = new HashMap<String, Person>();
		Scanner isr = new Scanner(new FileReader(filename));
			
		while(isr.hasNextLine())
		{
			temp = isr.nextLine();
			skip = false;
			
			if(Pattern.matches("^P[0-9]+$", temp))
			{				
				tempP = new Person(temp);
				
				while(isr.hasNextLine() && !skip)
				{
					temp = isr.nextLine();
					if(temp.equals(""))
					{
						skip = true;
					}else {
						
						data = new StringTokenizer(temp," ");
						
						if(data.countTokens() > 2)
							throw new IOException("format error");
						else {							
							tempP.addAttribute(data.nextToken(), data.nextToken());
						}
					}
				}
				
				persons.put(tempP.getName(),tempP);
			}
		}
		isr.close();
		
		return persons;
	}
	
	public static HashMap<String,String[]> getAttributes(String filename) throws IOException
	{
		HashMap<String, String[]> attributes = new HashMap<String, String[]>();
		Scanner sc = new Scanner(new FileReader(filename));
		String line = "";
		String[] token;
		String[] values;
		
		while(sc.hasNextLine())
		{
			line = sc.nextLine();
			if(line.equals("") || Pattern.matches("^P[0-9]+$", line))
				break;
			
			token = line.split(" ");
			
			if(token.length <= 0)
				{throw new IOException("format error");}
			else {
				values = Arrays.copyOfRange(token,1,token.length);
				attributes.put(token[0], values);
			}
		}
		
		return attributes;
	}
	
	public static LinkedList<String> getAttributeNames(String filename) throws IOException
	{
		LinkedList<String> attr = new LinkedList<String>();
		Scanner sc = new Scanner(new FileReader(filename));
		String line = "";
		String[] token;
		
		while(sc.hasNextLine())
		{
			line = sc.nextLine();
			if(line.equals("") || Pattern.matches("^P[0-9]+$", line))
				break;
			
			token = line.split(" ");
			
			if(token.length <= 0)
				{throw new IOException("format error");}
			else {
				System.out.println(token[0]);
				attr.add(token[0]);
			}
		}
		return attr;
	}
	
}
