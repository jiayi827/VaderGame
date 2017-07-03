// CSE 143 Spring 2017, Homework 7, Jiayi Wang

// QuestionNode objects is a single node of a binary tree of strings
public class QuestionNode {
	public String data;    // data stored at this node
	public QuestionNode left;    // reference to the left subtree
	public QuestionNode right;    // refrence to the right subtree
	
	// Constructs a leaf node with the given string
	public QuestionNode(String s) {
		this(s, null, null);
	}
	
	// Constructs a leaf or branch node with the given string and links
	public QuestionNode(String s, QuestionNode left, QuestionNode right) {
		this.data = s;
		this.left = left;
		this.right = right;
	}
}
