import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonReader 
{
    public static void main(String[] args)
    {
    	File f = new File(args[0]);
    	
    	readPersonSet(f);
    }
    
	public static void readPersonSet(File f)
	{
		//Hashmap that holds and matches all traits and trait variants in (Trait Name, Trait Variant List)  Key/Value pairs		
		Map<String, List<String>> traits = new HashMap<String, List<String>>();
		
		//Hashmap that hold data for each person in (Person Name, Person (Trait name, Trait quality) Hashmap) Key/Value pairs.
		Map<String, Map<String,String>> people = new HashMap<String, Map<String,String>>();
		
		try 
		{
			BufferedReader  reader = new BufferedReader(new FileReader(f));
			
			String currLine;
			
			//Loop to extract list of traits for a game and put into 'traits' hashmap
			
			//Go through top text block of file
			while(!(currLine = reader.readLine()).equals(""))
			{
				//Break up each line into individual words
				String[] splitLine = currLine.split(" ");
				List<String> variants = new ArrayList<String>();
				
				//splitline[0] is the trait name, which can be used as a key
				
				//Go through possible trait variants and add to a list
				for(int i = 1; i<splitLine.length;i++)
				{
					variants.add(splitLine[i]);
				}
				
				//The hashmap links the trait name to its possible variants
				traits.put(splitLine[0], variants);
			}
			
			System.out.println(Arrays.asList(traits));
			
			
			
			//Loop to extract list of people and their traits for a game and put into 'people' hashmap
			
			//go through remainder of file
			while((currLine != null))
			{
				Map<String, String> playerAtts = new HashMap<String, String>();
				
				//Find block of person info
				if(currLine.equals(""))
				{
					currLine = reader.readLine();
					
					//Isolate name
					String playerName = currLine;
					
					
					//Rest of block is (Trait, Variant) pair
					while((currLine = reader.readLine()) != null && !(currLine).equals(""))
					{
						String[] splitAttri = currLine.split(" ");
						
						playerAtts.put(splitAttri[0], splitAttri[1]);
					}
					
					//Hashmap links player name and trait data
					people.put(playerName, playerAtts);
				}
			}

			System.out.println(Arrays.asList(people));
			
			reader.close();
		}		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}
}
