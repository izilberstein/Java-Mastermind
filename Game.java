
import java.util.*;
import java.io.*;

public class Game {

    private static Scanner keyboard = new Scanner (System.in);
    public static void main (String[] args) {

	char answerChar = 'a';
	while (answerChar != 'q') {                                                      // program ends with q
	    printMenu();
	    System.out.println("Enter a command: ");
	    String response = keyboard.nextLine();
	    answerChar = response.charAt(0);
	        
	    switch (answerChar) {

	    case 'a':	   
		playGameGuesser();
		break;
	    
	    case 'b':
		playGameMaster();
		break;

	    case 'c':          
		printRules();		
		break;

	    case 'd':
		printLeaderboard();
		break;
		
	    case 'q':
		break;	   
	    }	    
	}
    }

    private static void printMenu() {

	System.out.println ("Commands: ");
	System.out.println ("  a --- play game as guesser");
	System.out.println ("  b --- play game as master");
	System.out.println ("  c --- print rules");
	System.out.println ("  d --- print leaderboard");
	System.out.println ("  q --- quit");
    }

    private static void printRules() {
	
	System.out.println("This is the game of Mastermind. In this game, you can choose to play as the guesser or the master.");
	System.out.println("To play as the guesser, you will attempt to guess the code that the computer makes. A code is a sequence of 4 numbers ranging from 0 to " + MaxVal.MAX_VAL + ", in a specific order.");
	System.out.println("After each turn, you will be told how many taps and hits you got.");
	System.out.println("A tap means that you guessed a number right, but it is in the wrong location.");
	System.out.println("A hit means that a number is both the correct value and in right location.");
	System.out.println("If you guess the code you win, but you only get 10 turns to do so.");
	System.out.println("To play as the master, you will create a code that the computer guesses. You will need to tell the computer how many taps and hits it got after each turn. Good luck!");
    }
    
    public static Code makeCodeUser() {                                                  //gets a code from the user
	    
	int[] d = new int[4];
	for (int i = 0; i<4; i++) {
	    System.out.println("Enter a number (0-" + MaxVal.MAX_VAL + ")");
	    int a = keyboard.nextInt();	 
	    while (a<0 || a>MaxVal.MAX_VAL) {
		System.out.println("Enter a valid number (0-" + MaxVal.MAX_VAL + ")");
		a = keyboard.nextInt();
	    }  
	    d[i] = a;		    
	}
	String skippy = keyboard.nextLine();
	return new Code(d);	
    }
    
    public static Code makeCodeComputer() {                                              //randomly generates a code from the computer
	
	int[] mcc = new int[4];
	for (int i=0; i<4; i++)
	    mcc[i] = (int) (Math.random()*(MaxVal.MAX_VAL+1));
	return new Code (mcc);
    }
    
    public static ArrayList <Code> fillArray(int n) {                                    //all possible codes
	
	ArrayList <Code> a =  new ArrayList <Code> (1296);
	for (int i = 0; i<=n; i++)
	    for (int j = 0; j<=n; j++)
		for (int k = 0; k<=n; k++) 
		    for (int h = 0; h<=n; h++) {
			int[] p = {i, j, k, h};
			a.add(new Code (p));
		    }
	return a;
    }
    
    public static ArrayList<Code> findPossible(int t, int h, ArrayList<Code> all, Code test){//codes that could possibly still be the code

	ArrayList <Code> some = new ArrayList<Code> ();
	for (Code c : all) {
	    Key k = test.check(c);
	    if (k.taps == t && k.hits == h) some.add(c);
	}
	return some;
    }

    public static void playGameGuesser() {

	Code masterComp = makeCodeComputer();                                            //master code
	boolean again = true;
	int turns = 0;
	while (again) {
	    turns++;
	    Code guessUser = makeCodeUser();                                             // user guess
	    Key temp = masterComp.check(guessUser);                                      // compare codes
	    System.out.println("You guessed " + guessUser);
	    System.out.println(temp);
	    if (temp.hits == 4) {
		again = false;                                                           // end game
		System.out.println("Congrats! You guessed the code in " + turns + " turns!");		
		if (checkBoard(turns)) addLeader(turns);
	    }
	    else if (turns == 10) {
		again = false;                                                           // end game
		System.out.println("You lose! Couldn't guess my code.");
	    }
	}
    }

    public static void playGameMaster() {

	ArrayList <Code> possible = fillArray(MaxVal.MAX_VAL);                          // all possible codes 
	System.out.println("Choose your master code of 4 values (0-" + MaxVal.MAX_VAL + "). Press return when ready.");// user master code (suggest writing it down)
	String skipLine = keyboard.nextLine();
	Code guessComp = makeCodeComputer();                                            // initial random guess
	boolean again = true;
	int turns = 0;
	while (again) {
	    turns++;		    
	    System.out.println("Computer guess: " + guessComp);
	    System.out.println("Enter how many taps the computer got:");
	    int t = keyboard.nextInt();
	    System.out.println("Enter how many hits the computer got:");
	    int h = keyboard.nextInt();		
	    possible = findPossible(t, h, possible, guessComp);                          // revises possible codes
	    try {
		guessComp = possible.get( (int) (Math.random()*possible.size()));        //random guess from possible codes
	    }
	    catch (IndexOutOfBoundsException e) {
		System.out.println("You cheated! Game over.");
		again = false;                                                           // end game
	    }
	    if (h == 4){
		again = false;                                                           // end game
		System.out.println("Nice try! The computer guessed your code in " + turns + " turns!");
	    }
	    else if (turns == 10) {                                                      // computer never loses but still include this
		again = false;                                                           //end game
		System.out.println("You win! The computer couldn't guess your code.");   		      
	    }
	    skipLine = keyboard.nextLine();
	}
    }

    static boolean checkBoard (int score) {                                              //checks if a score qualifies for leaderboard

	for (Leader person: getLeaderboard())
	    if (score <= person.score) return true;
	return false;
       
    }

    static void addLeader (int score) {                                                  // adds a leader to leaderboard

	try {	    
	    System.out.println ("Congratulaions, you made the leaderboards! What is your name?");
	    String victor = keyboard.nextLine();	
	    ArrayList <Leader> list = getLeaderboard();
	    insert(list, score, victor);
	    PrintWriter p = new PrintWriter (new FileOutputStream("leaderboard.txt"));
	    int count = 0;
	    for (Leader l: list) {                    	                                 // prints leaderboard in file, only top 5 
		if (count<5) 
		    p.println(l);
		count++;
	    }
	    p.close();
	}
	catch (FileNotFoundException e){
	}
    }
    
    static ArrayList<Leader> insert (ArrayList <Leader> list, int score, String victor) {// adds the user to the leadboard array list
	
	for (int i=0; i<list.size(); i++) {                                              //inserts based on score
	    if (score <= list.get(i).score) {
		list.add(i, new Leader(0, score, victor));
		break;
	    }
	}
	int holder = -1;                                                                 // to check for ties, since in order of score, first check won't be tie
	for (int i=0; i<list.size(); i++) {                                              //assigns correct position values 	    
	    if (holder == list.get(i).score) list.get(i).position = list.get(i-1).position; // accounts for ties
	    else 
		list.get(i).position = i+1;
	    holder = list.get(i).score;  
	}
	return list;
    }

    public static ArrayList<Leader> getLeaderboard() {                                    // takes leaderboard in as an array list of type leader

	ArrayList <Leader> list = new ArrayList<Leader> ();
	try {	    
	    Scanner sc = new Scanner(new FileReader("leaderboard.txt"));
	    while (sc.hasNextLine()){
		String s = sc.nextLine();
		String[] a = s.split(": ");
		int p = Integer.parseInt(a[0]);
		String n = a[1];
		int d = Integer.parseInt(a[2]);
		list.add(new Leader (p, d, n));
	    }
	    for (int i = list.size()-1; i > 4; i--)                                      //trims leaderboard down to 5 people
		list.remove(i);
	}
	catch (FileNotFoundException e){
	}
	return list;	
    }
    
    public static void printLeaderboard () {                                              // prints leaderboard in terminal

	for (Leader person : getLeaderboard())
	    System.out.println(person);
    }	    
	
}
    
