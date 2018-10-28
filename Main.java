import java.util.Random;

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
		puzzle = new int[10][10];
		sections = new boolean[10][10];
		columns = new boolean[10][10];
		rows = new boolean[10][10];
		
		
		int givenNumbers = 54;  //The amount of numbers to fill up the puzzle. Can be used to set difficulty of the puzzle. 
		createPuzzle(givenNumbers);
		printPuzzle();
		

	}
	
	
	public static void createPuzzle(int numberGiven) {
		Random rand = new Random();
		int numbers[] = new int[10]; //Each cell of this array will represent a number 1-9 
		for(  int x = 0; x < 9; x++) {
			numbers[x] = numberGiven/9; //Distibutes the numbers to be added across the array.
		}
		int spot = 0; //Used to track what number we are in for the numbers array.
		int count = 0; 
		int step = 0;
		int number = 0;
		for(int x = numberGiven; x > 0; x--) {  //Adds the amount of numbers the user asks for. Decrements the number left to add after each it
			int spotX = rand.nextInt(9) ;
			int spotY =rand.nextInt(9);
			number = spot + 1;
			
			if(canBePlaced(spotX, spotY, number) ) {
				System.out.println("SpotX = " +spotX+"\nSpotY = "+spotY);
				sections[getSection(spotX,spotY)][number] = true;
				puzzle[spotX][spotY] = number;
				columns[spotX][number] = true;
				rows[spotY][number] = true;
				System.out.println(number + " added to puzzle["+spotX+"]["+spotY+"]"); //Logging spot number is added an where so you can test if it matches up.
				count++;
				numbers[spot]--;
				if(numbers[spot] == 0) spot++;
				System.out.println("Count = "+count);
			} else {
				x++;
				step++;
			}
			
			
		}
		System.out.println("Count = "+count+"\nStep = "+step);
	}
	public static Boolean canBePlaced(int spotX, int spotY, int number) {
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

	public static void printPuzzle() {
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
		System.out.println(rows[1][1]+"\n"+columns[1][1]+"\n"+sections[0][1]); //A way to check if the logic is working.
	}

}
