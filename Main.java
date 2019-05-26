import java.util.Random;
import java.util.*;

public class Main {
	/** Stores all of the actual numbers in the Sudoku Puzzle*/
	public static int puzzle[][];
	/**
	 * An array showing the 9 individual 3x3 sections of the puzzle. 
	 * For each of the nine it has 9 booleans representing the numbers 1-9 that need to fill up the section.
	 * If a number is present in the section then it will be true, if not it will be false 
	 */
	public static boolean sections[][];
	/**
	 * An array showing the 9 individual Columns of the puzzle. 
	 * For each of the nine it has 9 booleans representing the numbers 1-9 that need to fill up the column.
	 * If a number is present in the section then it will be true, if not it will be false 
	 */
	public static boolean columns[][];
	/**
	 * An array showing the 9 individual rows of the puzzle. 
	 * For each of the nine it has 9 booleans representing the numbers 1-9 that need to fill up the row.
	 * If a number is present in the section then it will be true, if not it will be false 
	 */
	public static boolean rows [][];

	public static void main(String[] args) {
		puzzle = new int[9][9];
		sections = new boolean[9][9];
		columns = new boolean[9][9];
		rows = new boolean[9][9];
		
		
		int givenNumbers =81;  //The amount of numbers to fill up the puzzle. Can be used to set difficulty of the puzzle. 
		createPuzzle(givenNumbers);
		printPuzzle();
		

	}
	
	
	public static void createPuzzle(int numberGiven) {
		Random rand = new Random();
		int numbers[] = new int[9]; //Each cell of this array will represent a number 1-9 
		for(int x = 0; x < 9; x++) {
			numbers[x] = numberGiven/9; //Distributes the numbers to be added across the array.
		}
		int spot = 0; //Used to track what number we are in for the numbers array.
		int count = 0; 
		int step = 0;
		int number = 0;
		int skipped = 0;
		
		int spotX, spotY;
		int  circle = 0;
		int round = 0;
		boolean catcher = false;
		boolean changed = true; 
		spotX = 0;
		spotY = 0;
		boolean reset = false;
		int x = numberGiven;
		Vector<Integer>  xs = new Vector<>();
		Vector<Integer>  ys = new Vector<>();
		while(changed == true) {
			changed = false;
			spotX = 0;
			spotY = 0;
			number = 0;
			x =  numberGiven - printPuzzle();
			for(; x > 0; x--) {  //Adds the amount of numbers the user asks for. Decrements the number left to add after each it
			    
				//if(x - ((x/9)*9) == 0 && x != 0) spotX++;
				//spotX = circle;
			    Vector<Integer> needsNumber = new Vector<>();
			    for(int adder = 0; adder< 9; adder++) {
			    	//System.out.println(adder);
			    	//System.out.println(number);
			    	//System.out.println(count);
		    		if(canBePlaced(spotX, adder, number)) {
		    			needsNumber.add(adder);
		    		} else if(sections[getSection(spotX,adder) ][number] == true ){
		    			adder += 2;
		    		} else {
		    			catcher = false;
		    		}
		    		
		    	}
			    if(needsNumber.size() == 0 ) {
			    	
			    	int sectionTop = 0;
			    	for(int cellCheck = 0; cellCheck < 9 ; cellCheck += 3) {
			    		if(sections[getSection(spotX, cellCheck)][number] == false) {
			    			sectionTop = cellCheck;
			    			break;
			    		}
			    	}
			    	//int startY = 0 ;
			    	System.out.println("Starting Test NOW");
			    	printPuzzle();
			    	Vector<Integer> emptyCells = new Vector<>();
			    	Vector<Integer> needsThatNumber = new Vector<>();
			    	for(int sectionCheck = sectionTop; sectionCheck < sectionTop +3; sectionCheck++ ) {
			    		if(puzzle[spotX][sectionCheck] == 0) {
			    			emptyCells.add(sectionCheck);
			    		} else {
			    			needsThatNumber.add(sectionCheck);
			    		}
			    		
			    	}
			    	int needsMoving = 0;
			    	if(emptyCells.size() != 0) {
				    	for( int goThrough = 0; goThrough < 9; goThrough++) {
				    		if(puzzle[goThrough][emptyCells.elementAt(0)]  == number+1) {
				    			needsMoving = goThrough;
				    			puzzle[goThrough][emptyCells.elementAt(0)] = 0;
				    			puzzle[spotX][emptyCells.elementAt(0)] = number+1;
				    			columns[goThrough][number] = false;
				    			//rows[emptyCells.elementAt(0)][number] = false;
				    			sections[getSection(goThrough,emptyCells.elementAt(0))][number] = false;
				    			break;
				    		}
				    		
				    	}
			    	} else {
			    		int erasedNum;
			    		for(int erase = sectionTop; erase < sectionTop + 3; erase++) {
			    			erasedNum = puzzle[spotX][erase];
			    			puzzle[spotX][erase] = 0;
			    			columns[spotX][erasedNum] = false;
			    			rows[emptyCells.elementAt(0)][number] = false;
			    			sections[getSection(goThrough, emptyCells.elementAt(0))][erasedNum] = false;
			    			
			    			
			    		}
			    	}
			    	printPuzzle();
			    	x++;
			    	
			    	
			    	
			    	
			    	
			    	
			    	/*
			    	
			    	
			    	int xFix= 0;
			    	int yFix = 0;
			    	for(int fix = 0; fix < xs.size(); fix++) {
			    		xFix =  xs.elementAt(fix);
			    		yFix = ys.elementAt(fix);
			    		puzzle[xFix][yFix] = 0;
			    		columns[xFix][number] = false;
		    			rows[yFix][number] = false;
		    			sections[getSection(xFix,yFix)][number] = false;
		    			count--;
			    	}
			    	//count -= (xs.size()- 1);
			    	spotX = 0;
			    	spotY = 0;
			    	x += xs.size();
			    	*/
			    	
			    	//System.out.println("Wait what");
			    	//x++;
			    	//spotX= 0;
			    } else {
			    	int most = 0;
			    	int tmp = 0;
			    	int numCheck = 0;
			    	Vector<Integer> spotss = new Vector<>();
			    	//Vector<Integer> spotss = new Vector<>();
			    	
			    	
			    	// Checks what numbers are need in the spots that are needing a number. 
			    	// Picks spot that can take the most numbers. 
			    	for(int detect = 0; detect < needsNumber.size(); detect++  ) {  
			    		numCheck = needsNumber.elementAt(detect);
			    		tmp = 0;
			    		for(int detect2 = 0; detect2 < 9; detect2++) {
				    		if(canBePlaced(spotX, numCheck, detect2)) {
				    			tmp++;
				    		}
			    		}
			    		if(tmp > most) {
			    			most = tmp;
			    			spotss.clear();
			    			spotss.add(numCheck);
			    		} else if(tmp == most) {
			    			spotss.add(numCheck);
			    		}
			    	}
			    	//spotY = needsNumber.elementAt(rand.nextInt(needsNumber.size()));
			    	spotY = spotss.elementAt(rand.nextInt(spotss.size()));
			    
			    
			    
			    
			    	reset = false;
				    //System.out.println("\nplaceable.size() = " + placeable.size());
				    
				    	
				    
				    
				    //System.out.println("number = "+(number+1)+"\n");
					
					if(canBePlaced(spotX, spotY, number) ) {
					//	System.out.println("SpotX = " +spotX+"\nSpotY = "+spotY);
						sections[getSection(spotX,spotY)][number] = true;
						puzzle[spotX][spotY] = number+1;
						
						columns[spotX][number] = true;
						rows[spotY][number] = true;
						
						xs.add(spotX );
						ys.add(spotY);
						System.out.println(number + " added to puzzle["+spotX+"]["+spotY+"]"); //Logging spot number is added an where so you can test if it matches up.
						count++;
						printPuzzle();
				    	System.out.println("X = "+spotX);
				    	System.out.println("Y = "+spotY);
						if(count - ((count/9)*9) == 0  && count != 0) {
							number++;
							if(number == 9 ) number = 0;
							xs.clear();
							ys.clear();
							spotX=0;
						}else {
							spotX++;
						}
						changed = true;
						
						//numbers[spot]--;
						if(numbers[spot] == 0) spot++;
					//	System.out.println("Count = "+count);
					} else {
						x++;
					}
					
			    }
			}
		}
		System.out.println("Count = "+count+"\nSkipped = "+skipped);
	}
	public static Boolean canBePlaced(int spotX, int spotY, int number) {
	   // System.out.println("\n\n Section = "+ getSection(spotX, spotY));
	    //System.out.println("\n\n Number = "+ number);
		if(!sections[getSection(spotX,spotY)][number] && !rows[spotY][number] && !columns[spotX][number] && puzzle[spotX][spotY] == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/** Returns the number of the section that the point is in.
	 * Imagine a Sudoku puzzle, but then number each section of 3x3 as if it
	 * were a number pad starting at 0 instead of 1. 
	 * @param spotX
	 * @param spotY
	 * @return
	 */
	private static int getSection(int spotX, int spotY) {
		int section = 3;
		if(spotX < 3) {
			if(spotY < 3) {
				section = 0;
			} else if(spotY < 6) {
				section = 3;
			}else {
				section = 6;
			}
		} else if(spotX < 6) {
			if(spotY < 3) {
				section = 1;
			} else if(spotY < 6) {
				section = 4;
			}else {
				section = 7;
			}
		} else {
			if(spotY < 3) {
				section = 2;
			} else if(spotY < 6) {
				section = 5;
			}else {
				section = 8;
			}
		}
		return section;
		
	}

	public static int printPuzzle() {
		int numbersActuallyAdded = 0; //Tracks the amount of numbers that were actually added to the puzzle. 
		for(int y = 0; y < 9; y++) {
			
			if(y%3 == 0 ) {
				System.out.println("- - - - - - - - - - - - - - - - - - - -");
			}
			
			for(int x = 0; x < 9; x++) {
				if(x%3==0 && x != 0) {
					System.out.print("|");
				}
				if(puzzle[x][y] != 0) {
					System.out.print("| "+puzzle[x][y]+" " );
					numbersActuallyAdded++;
				} else {
					System.out.print("|   " );
				}
			}
			System.out.println("|");
		}
		System.out.println(numbersActuallyAdded);
		return numbersActuallyAdded;
		//System.out.println(rows[1][1]+"\n"+columns[1][1]+"\n"+sections[0][1]); //A way to check if the logic is working.
	}

}
