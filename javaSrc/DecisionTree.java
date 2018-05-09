import java.util.LinkedList;

public class DecisionTree {
	
	Node root;
	
	public DecisionTree()
	{
		this.root = null;
	}
	
	public void insert(Guess key, boolean answer)
	{
		this.root = insert(this.root,key, answer);
	}
	
	private Node insert(Node rt, Guess key, boolean answer)
	{
		if(rt == null)
		{
			rt = new Node(key, answer);
		}else if(answer)
		{
			rt.setRightChild(insert(rt.getRightChild(), key, answer));
		}else if(!answer)
		{
			rt.setLeftChild(insert(rt.getLeftChild(),key, answer));
		}
		
		return rt;
	}
	
	public boolean isEmpty()
	{
		if(root == null)
			return true;
		
		return false;
	}
	
	public LinkedList<Guess> getCorrectGuesses()
	{
		Node temp = this.root;
		LinkedList<Guess> list = new LinkedList<Guess>();
		while(temp != null)
		{
			if(temp.getAnswer())
			{
				list.add(temp.getKey());
			}
			temp = temp.getRightChild();
		}
		
		return list;
	}
	
	
		
}

class Node {
	private Guess key;
	private Node left;
	private Node right;
	private boolean answer;
	
	public Node(Guess key, boolean answer)
	{
		this.key = key;
		this.answer = answer;
	}
	
	public Guess getKey()
	{
		return key;
	}
	
	public Node getLeftChild()
	{
		return this.left;
	}
	
	public Node getRightChild()
	{
		return this.right;
	}
	
	public boolean getAnswer()
	{
		return this.answer;
	}
	
	public void setLeftChild(Node n)
	{
		this.left = n;
	}
	
	public void setRightChild(Node n)
	{
		this.right = n;
	}
}
