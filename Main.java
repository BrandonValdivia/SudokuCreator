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
		Vector<Integer>  numbersToBePlaced = new Vector<>();
		int randomSpot;
		
		// Fills 3 sections going diagonally across the board. Theses three do not interfere with eachother, so
		// they can all be filled without using any checks. This saves a lot of time.
		for(int sectionToFill = 0; sectionToFill < 3; sectionToFill++){
		    for(int fillNumbers = 0; fillNumbers < 9; fillNumbers++){
	            numbersToBePlaced.add(fillNumbers);    
		    }		
		    for(int xFill = sectionToFill * 3; xFill < sectionToFill*3 + 3; xFill++){
		        for(int yFill = sectionToFill * 3; yFill < sectionToFill*3 + 3; yFill++) {
		            randomSpot = rand.nextInt(numbersToBePlaced.size());
		            number = numbersToBePlaced.elementAt(randomSpot);
		            numbersToBePlaced.removeElement(number);
		            placeNumber(xFill,yFill, number);
		        }
		    }
		    numbersToBePlaced.clear();
		}
		int xOfSection = 3;
		int yOfSection = 0;
		numbersToBePlaced.clear();
		//Every number will have a total of 4 spots it can be placed at this time. 
		Vector<Integer>  possibleSpotsForNumbers = new Vector<>();
		Vector<Integer>  placeableNumbers = new Vector<>();
		Boolean placed;
		for(int fillNumbers = 0; fillNumbers < 9; fillNumbers++){
	            possibleSpotsForNumbers.add(4);
	            numbersToBePlaced.add(fillNumbers);
		}
		int possibleSpots;
		for(int xCheck = xOfSection; xCheck < xOfSection + 3; xCheck++){
	        for(int yCheck = yOfSection; yCheck < yOfSection + 3; yCheck++) {
	            placed = false;
	            placeableNumbers.clear();
	            for(int numberToCheck = 0; numberToCheck < numbersToBePlaced.size(); numberToCheck++ ){
	                number = numbersToBePlaced.elementAt(numberToCheck);
	                if(canBePlaced(xCheck, yCheck, number) ){
	                    placeableNumbers.add(number);
	                }
	            }
	            
                for(int placeableCheck = 0; placeableCheck < placeableNumbers.size(); placeableCheck++){
                    number = placeableNumbers.elementAt(placeableCheck);    
                    possibleSpots = possibleSpotsForNumbers.elementAt(number);
                    if( possibleSpots == 1){
                        placeNumber(xCheck, yCheck, number);
                        placed = true;
                    }
                    possibleSpotsForNumbers.setElementAt( possibleSpots - 1, number); 
                }
                if(!placed) {
                    randomSpot = rand.nextInt(placeableNumbers.size());
                    number = placeableNumbers.elementAt(randomSpot);
                    placeNumber(xCheck, yCheck, number);
                    numbersToBePlaced.removeElement(number);
                }
	        }
	    }
	    
	    
	    
	    //Top right corner
	    numbersToBePlaced.clear();
	    possibleSpotsForNumbers.clear();
	    xOfSection = 6;
	    yOfSection = 0;
	    for(int fillNumbers = 0; fillNumbers < 9; fillNumbers++){
	            possibleSpotsForNumbers.add(2);
	            //numbersToBePlaced.add(fillNumbers);
		}
		
		for(int yCheck = yOfSection; yCheck < yOfSection + 3; yCheck++){
		    for(int numberToCheck = 0; numberToCheck < 9; numberToCheck++ ){
                if(!rows[yCheck][numberToCheck] ) numbersToBePlaced.add( numberToCheck);
            }
            
            for(int spotChecker = xOfSection; spotChecker < xOfSection + 3; spotChecker++){
                placeableNumbers.clear();
                for(int checkPlaceables = 0; checkPlaceables < numbersToBePlaced.size(); checkPlaceables++){
                    number = numbersToBePlaced.elementAt(checkPlaceables);
                    if(canBePlaced(spotChecker, yCheck, number)){
                        placeableNumbers.add(number);
                        
                    }
                }
                if(placeableNumbers.size() == 1){
                    placeNumber(spotChecker, yCheck, number);
                    numbersToBePlaced.removeElement(number);
                }
            }
            
	        for(int xCheck = xOfSection; xCheck < xOfSection + 3; xCheck++) {
	            if(puzzle[xCheck][yCheck] == 0){
    	            placed = false;
    	            placeableNumbers.clear();
    	            for(int numberToCheck = 0; numberToCheck < numbersToBePlaced.size(); numberToCheck++ ){
    	                number = numbersToBePlaced.elementAt(numberToCheck);
    	                if(canBePlaced(xCheck, yCheck, number)) placeableNumbers.add( number);
    	            }
    	            
    	            
                    for(int placeableCheck = 0; placeableCheck < placeableNumbers.size(); placeableCheck++){
                        number = placeableNumbers.elementAt(placeableCheck);    
                        possibleSpots = possibleSpotsForNumbers.elementAt(number);
                        System.out.println("\nPossible Spots = "+possibleSpots+ "\n");
                        if( possibleSpots == 1){
                            System.out.println("1 possible spot");
                            placeNumber(xCheck, yCheck, number);
                            placed = true;
                        }
                        possibleSpotsForNumbers.setElementAt( possibleSpots - 1, number); 
                    }
                    if(!placed) {
                        randomSpot = rand.nextInt(placeableNumbers.size());
                        number = placeableNumbers.elementAt(randomSpot);
                        placeNumber(xCheck, yCheck, number);
                        numbersToBePlaced.removeElement(number);
                    }
    	        }
	        }
	    }
		
		
		//Mid level on right side
		numbersToBePlaced.clear();
	    possibleSpotsForNumbers.clear();
	    xOfSection = 6;
	    yOfSection = 3;
	    for(int fillNumbers = 0; fillNumbers < 9; fillNumbers++){
	            possibleSpotsForNumbers.add(2);
	            //numbersToBePlaced.add(fillNumbers);
		}
		
		for(int xCheck = xOfSection; xCheck < xOfSection + 3; xCheck++){
		    for(int numberToCheck = 0; numberToCheck < 9; numberToCheck++ ){
                if(!columns[xCheck][numberToCheck] ) numbersToBePlaced.add( numberToCheck);
            }
            
            for(int spotChecker = yOfSection; spotChecker < yOfSection + 3; spotChecker++){
                placeableNumbers.clear();
                for(int checkPlaceables = 0; checkPlaceables < numbersToBePlaced.size(); checkPlaceables++){
                    number = numbersToBePlaced.elementAt(checkPlaceables);
                    if(canBePlaced(xCheck, spotChecker, number)){
                        placeableNumbers.add(number);
                        
                    }
                }
                if(placeableNumbers.size() == 1){
                    placeNumber(xCheck, spotChecker, number);
                    numbersToBePlaced.removeElement(number);
                }
            }
            
	        for(int yCheck = yOfSection; yCheck < yOfSection + 3; yCheck++) {
	            if(puzzle[xCheck][yCheck] == 0){
    	            placed = false;
    	            placeableNumbers.clear();
    	            for(int numberToCheck = 0; numberToCheck < numbersToBePlaced.size(); numberToCheck++ ){
    	                number = numbersToBePlaced.elementAt(numberToCheck);
    	                if(canBePlaced(xCheck, yCheck, number)) placeableNumbers.add( number);
    	            }
    	            
    	            
                    for(int placeableCheck = 0; placeableCheck < placeableNumbers.size(); placeableCheck++){
                        number = placeableNumbers.elementAt(placeableCheck);    
                        possibleSpots = possibleSpotsForNumbers.elementAt(number);
                        System.out.println("\nPossible Spots = "+possibleSpots+ "\n");
                        if( possibleSpots == 1){
                            System.out.println("1 possible spot");
                            placeNumber(xCheck, yCheck, number);
                            placed = true;
                        }
                        possibleSpotsForNumbers.setElementAt( possibleSpots - 1, number); 
                    }
                    if(!placed) {
                        randomSpot = rand.nextInt(placeableNumbers.size());
                        number = placeableNumbers.elementAt(randomSpot);
                        placeNumber(xCheck, yCheck, number);
                        numbersToBePlaced.removeElement(number);
                    }
	            }
	        }
	    }
	    
	    
	    //Mid level on left side
	    numbersToBePlaced.clear();
	    possibleSpotsForNumbers.clear();
	    xOfSection = 0;
	    for(int fillNumbers = 0; fillNumbers < 9; fillNumbers++){
	            possibleSpotsForNumbers.add(2);
	            //numbersToBePlaced.add(fillNumbers);
		}
		
		for(int xCheck = xOfSection; xCheck < xOfSection + 3; xCheck++){
		    for(int numberToCheck = 0; numberToCheck < 9; numberToCheck++ ){
                if(!columns[xCheck][numberToCheck] ) numbersToBePlaced.add( numberToCheck);
            }
	        for(int yCheck = 3; yCheck < 6; yCheck++) {
	            placed = false;
	            placeableNumbers.clear();
	            for(int numberToCheck = 0; numberToCheck < numbersToBePlaced.size(); numberToCheck++ ){
	                number = numbersToBePlaced.elementAt(numberToCheck);
	                if(canBePlaced(xCheck, yCheck, number)) placeableNumbers.add( number);
	            }
	            
	            
                for(int placeableCheck = 0; placeableCheck < placeableNumbers.size(); placeableCheck++){
                    number = placeableNumbers.elementAt(placeableCheck);    
                    possibleSpots = possibleSpotsForNumbers.elementAt(number);
                    System.out.println("\nPossible Spots = "+possibleSpots+ "\n");
                    if( possibleSpots == 1){
                        System.out.println("1 possible spot");
                        placeNumber(xCheck, yCheck, number);
                        placed = true;
                    }
                    possibleSpotsForNumbers.setElementAt( possibleSpots - 1, number); 
                }
                if(!placed) {
                    randomSpot = rand.nextInt(placeableNumbers.size());
                    number = placeableNumbers.elementAt(randomSpot);
                    placeNumber(xCheck, yCheck, number);
                    numbersToBePlaced.removeElement(number);
                }
	        }
	    }
		
		
		
		//bottom left corner
		numbersToBePlaced.clear();
		possibleSpotsForNumbers.clear();
	    xOfSection = 0;
	    for(int fillNumbers = 0; fillNumbers < 9; fillNumbers++){
	            possibleSpotsForNumbers.add(2);
	            //numbersToBePlaced.add(fillNumbers);
		}
		
		for(int xCheck = xOfSection; xCheck < xOfSection + 3; xCheck++){
		    for(int numberToCheck = 0; numberToCheck < 9; numberToCheck++ ){
                if(!columns[xCheck][numberToCheck] ) numbersToBePlaced.add( numberToCheck);
            }
	        for(int yCheck = 6; yCheck < 9; yCheck++) {
	            placed = false;
	            placeableNumbers.clear();
	            for(int numberToCheck = 0; numberToCheck < numbersToBePlaced.size(); numberToCheck++ ){
	                number = numbersToBePlaced.elementAt(numberToCheck);
	                if(canBePlaced(xCheck, yCheck, number)) placeableNumbers.add( number);
	            }
	            
	            
                for(int placeableCheck = 0; placeableCheck < placeableNumbers.size(); placeableCheck++){
                    number = placeableNumbers.elementAt(placeableCheck);    
                    possibleSpots = possibleSpotsForNumbers.elementAt(number);
                    System.out.println("\nPossible Spots = "+possibleSpots+ "\n");
                    if( possibleSpots == 1){
                        System.out.println("1 possible spot");
                        placeNumber(xCheck, yCheck, number);
                        placed = true;
                    }
                    possibleSpotsForNumbers.setElementAt( possibleSpots - 1, number); 
                }
                if(!placed) {
                    randomSpot = rand.nextInt(placeableNumbers.size());
                    number = placeableNumbers.elementAt(randomSpot);
                    placeNumber(xCheck, yCheck, number);
                    numbersToBePlaced.removeElement(number);
                }
	        }
	    }
	    
	    
	    
	    //The last section to be filled
		numbersToBePlaced.clear();
		possibleSpotsForNumbers.clear();
	    xOfSection = 3;
	    for(int fillNumbers = 0; fillNumbers < 9; fillNumbers++){
	            possibleSpotsForNumbers.add(2);
	            //numbersToBePlaced.add(fillNumbers);
		}
		
		for(int xCheck = xOfSection; xCheck < xOfSection + 3; xCheck++){
		    for(int numberToCheck = 0; numberToCheck < 9; numberToCheck++ ){
                if(!columns[xCheck][numberToCheck] ) numbersToBePlaced.add( numberToCheck);
            }
	        for(int yCheck = 6; yCheck < 9; yCheck++) {
	            placed = false;
	            placeableNumbers.clear();
	            for(int numberToCheck = 0; numberToCheck < numbersToBePlaced.size(); numberToCheck++ ){
	                number = numbersToBePlaced.elementAt(numberToCheck);
	                if(canBePlaced(xCheck, yCheck, number)) placeableNumbers.add( number);
	            }
	            
	            
                for(int placeableCheck = 0; placeableCheck < placeableNumbers.size(); placeableCheck++){
                    number = placeableNumbers.elementAt(placeableCheck);    
                    possibleSpots = possibleSpotsForNumbers.elementAt(number);
                    System.out.println("\nPossible Spots = "+possibleSpots+ "\n");
                    if( possibleSpots == 1){
                        System.out.println("1 possible spot");
                        placeNumber(xCheck, yCheck, number);
                        placed = true;
                    }
                    possibleSpotsForNumbers.setElementAt( possibleSpots - 1, number); 
                }
                if(!placed) {
                    randomSpot = rand.nextInt(placeableNumbers.size());
                    number = placeableNumbers.elementAt(randomSpot);
                    placeNumber(xCheck, yCheck, number);
                    numbersToBePlaced.removeElement(number);
                }
	        }
	    }
		System.out.println("Count = "+count+"\nSkipped = "+skipped);
	}
	
	public static void removeNumber(int spotX, int spotY, int number){
	    int erasedNum;
        erasedNum = puzzle[spotX][spotY]-1;
		puzzle[spotX][spotY] = 0;
		columns[spotX][erasedNum] = false;
		
		sections[getSection(spotX, spotY)][erasedNum] = false;
        
        System.out.println(number + " removed from puzzle["+spotX+"]["+spotY+"]"); //Logging spot number is added an where so you can test if it matches up.
		printPuzzle();
		System.out.println("X = "+spotX);
		System.out.println("Y = "+spotY);
	}
	
	public static void placeNumber(int spotX, int spotY, int number){
	    sections[getSection(spotX,spotY)][number] = true;
        puzzle[spotX][spotY] = number+1;
        
        columns[spotX][number] = true;
        rows[spotY][number] = true;
        
        //xs.add(spotX );
        //.add(spotY);
        System.out.println((number+1) + " added to puzzle["+spotX+"]["+spotY+"]"); //Logging spot number is added an where so you can test if it matches up.
		printPuzzle();
		System.out.println("X = "+spotX);
		System.out.println("Y = "+spotY);
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
