import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer implements Player
{
	
	private HashMap<String, Person> persons;
	private HashMap<String, String[]> availableAttributes;
	private LinkedList<String> attrName;
	private Person person;
	private DecisionTree dt;
	
    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName Name of the chosen person for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *    Note you can handle IOException within the constructor and remove
     *    the "throws IOException" method specification, but make sure your
     *    implementation exits gracefully if an IOException is thrown.
     */
    public BinaryGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
    	persons = FileLoader.getPersons(gameFilename);
    	person = persons.get(chosenName);
    	availableAttributes = FileLoader.getAttributes(gameFilename);
    	attrName = FileLoader.getAttributeNames(gameFilename);
    	dt = new DecisionTree();
    } // end of BinaryGuessPlayer()


    public Guess guess() {
    	// pick an attribute
    	String attr = "";
    	String value = "";
    	
    	LinkedList<Person> personGrp = getCorrectPerson();
    	
    	
    	if(personGrp.size() == 1)
    	{
    		Person p = personGrp.peek();
    		return new Guess(Guess.GuessType.Person, "", p.getName());
    	}
    	
    	if(attrName.size() > 0)
    	{
    		attr = attrName.getFirst();
    		value = getMajorityValue(attr, personGrp);
    	}
    	
    	return new Guess(Guess.GuessType.Attribute, attr, value);
    	
    	
    } // end of guess()


	public boolean answer(Guess currGuess) {

		if(currGuess.getType() == Guess.GuessType.Person && person.getName().equals(currGuess.getValue()))
			return true;
		
		if(person.getAttributeValue(currGuess.getAttribute()).equals(currGuess.getValue()))
			return true;
		
        return false;
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {

        if(currGuess.getType() == Guess.GuessType.Person && answer)
        	return true;
        
        if(!answer)
        {
        	String[] newValues = availableAttributes.get(currGuess.getAttribute());
        	newValues = removeFromArray(newValues,currGuess.getValue());
        	availableAttributes.put(currGuess.getAttribute(), newValues);
        }else {
        	attrName.remove(currGuess.getAttribute());
        }
        
        dt.insert(currGuess, answer);
        return false;
    } // end of receiveAnswer()
	
	private String[] removeFromArray(String[] arr, String val)
	{
//		String[] res = new String[arr.length - 1];
		LinkedList<String> res = new LinkedList<String>();
		
		int k = -1;
		for(int i = 0; i < arr.length; i++ )
		{
			if(arr[i] != val)
			{
				res.add(arr[i]);
			}
			
		}
		
		return res.toArray(arr);
	}
	private String getMajorityValue(String attr, LinkedList<Person> persons)
	{
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		String[] vals = availableAttributes.get(attr);
		String majority = "";
		int max = 0;
		
		for(int i = 0; i< vals.length; i++)
		{
			counter.put(vals[i], 0);
		}
		// for each person 
		for(Person currentP : persons)
		{
			String val =currentP.getAttributeValue(attr);
			
			if(counter.containsKey(val)) {
				int i = counter.get(val);
				counter.put(val, i+1);
			}
			
			
		}
		
		for(Map.Entry<String, Integer> entry : counter.entrySet())
		{			
			int val = entry.getValue();
			if(val > max)
			{
				max = val;
				majority = entry.getKey();
			}
		}
		
		return majority;
	}
	
	private LinkedList<Person> getCorrectPerson()
	{
		LinkedList<Person> res = new LinkedList<Person>();
		LinkedList<Guess> guesses = this.dt.getCorrectGuesses();
		Person currentP = null;
		boolean skip = false;
		
		for(Map.Entry<String, Person> entry : this.persons.entrySet()) {
			currentP = entry.getValue();
			skip = false;
			
			for(Guess g : guesses)
			{
				String gAttr = g.getAttribute();
	    		String gVal = g.getValue();
	    		
	    		if(!currentP.getAttributeValue(gAttr).equals(gVal))
	    		{
	    			skip = true;
	    			break;
	    		}
			}
			
			if(!skip)
			{
				res.add(currentP);
			}
		}
		return res;
	}

} // end of class BinaryGuessPlayer
