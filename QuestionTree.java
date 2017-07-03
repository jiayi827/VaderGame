// CSE 143 Spring 2017, Homework 7, Jiayi Wang
import java.util.*;
import java.io.*;

// A QuestionTree object represents the entire binary tree of strings in the game
// Check whether the computer guessed right, otherwise grow the binary tree
// Assuming all public methods params not null, otherwise throw an IllegalArgumentException
public class QuestionTree {
	private QuestionNode overallRoot;
	private UserInterface ui;
	private int gamePlayed;
	private int gameWon;
	
	// Initialize a game with a root node of "Computer"
	// gamePlayed and gameWon initialed to be 0, ui set to be the given UserInterface
	public QuestionTree(UserInterface ui) {
		checkParam(ui);
		gamePlayed = 0;
		gameWon = 0;
		this.ui = ui;
		overallRoot = new QuestionNode("computer");
	}
	
	// Play one complete guessing game
	public void play() {
		overallRoot = play(overallRoot);
	}
	
	// When reaching a leaf node, check whether it is the right guess
	// When reaching a branch node, print out the question to get user input
	// Takes a QuestionNode root as param
	// Returns the root node
	private QuestionNode play(QuestionNode root) {
		if (root.left == null && root.right == null) {
			gamePlayed++;
			ui.print("Would your object happen to be " + root.data + "?");
			if (ui.nextBoolean()) {      // if it is the right guess
				ui.println("I win!");
				gameWon++;
			} else {        // if it is a wrong guess
				return buildTree(root);
			}
		} else {       // if it is a node of question
			ui.print(root.data);
			if (ui.nextBoolean()) {      // if user answers yes, go left
  				root.left = play(root.left);
			} else {        // if user answers no, go right
				root.right = play(root.right);
			}
		}
		return root;
	}
	
	// Develop the binary tree with user given object and question
	// Takes a QuestionNode root as param
	// Returns the new question node
	private QuestionNode buildTree(QuestionNode root) {
		ui.print("I lose. What is your object?");
		QuestionNode other = new QuestionNode(ui.nextLine());
		ui.print("Type a yes/no question to distinguish your item from " + root.data + ":");
		QuestionNode newQ = new QuestionNode(ui.nextLine());
		ui.print("And what is the answer for your object?");
		if (ui.nextBoolean()) {
			newQ.left = other;
			newQ.right = root;
		} else {
			newQ.left = root;
			newQ.right = other;
		}
		return newQ;
	}
	
	// Save the current tree to an output file
	// Takes a PrintStream object output as param
	public void save(PrintStream output) {
		checkParam(output);
		save(output, overallRoot);
	}
	
	// Save the current tree to an output file by a preorder traversal
	// Takes a PrintStream object output and a QuestionNode root as params
	private void save(PrintStream output, QuestionNode root) {
		if (root != null) {
			if (root.left == null && root.right == null) {
				output.println("A:" + root.data);
			} else {
				output.println("Q:" + root.data);
			}
			save(output, root.left);
			save(output, root.right);
		}
	}
	
	// Replace the current tree with lines read from a file
	// Takes a Scanner object input as param
	public void load(Scanner input) {
		checkParam(input);
		overallRoot = load(input, overallRoot);
	}
	
	// Read the lines from a file and build a binary tree with them
	// Takes a Scanner object and a QuestionNode as param
	// Returns the root node
	private QuestionNode load(Scanner input, QuestionNode root) {
		if (!input.hasNextLine()) {
			return root;
		}
		String str = input.nextLine();
		root = new QuestionNode(str.substring(2));
		// if this line is a question, add a branch with left and right subtree
		// otherwise just add a leaf
		if (str.charAt(0) == 'Q') {
			root.left = load(input, root.left);
			root.right = load(input, root.right);
		}
		return root;
	}

	// Returns total number of games played
	public int totalGames() {
		return gamePlayed;
	}
	
	// Returns the number of wins
	public int gamesWon() {
		return gameWon;
	}
 	
	// Throw an IllegalArgumentException if the param is null
	private void checkParam(Object object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
	}
}
