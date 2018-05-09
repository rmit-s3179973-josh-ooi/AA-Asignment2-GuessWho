import java.util.HashMap;

public class Person {
	
	private HashMap<String,String> attributes;
	private String name;
	
	public Person(String newName)
	{
		this.name = newName;
		attributes = new HashMap<String, String>();
	}
	
	public void addAttribute(String attr, String val)
	{
		attributes.put(attr, val);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getAttributeValue(String attr)
	{
		String output = "";
		if(!attributes.containsKey(attr)) {
			System.err.println("key not found");
		}else {
			output = attributes.get(attr);
		}
		
		return output;
	}
}
