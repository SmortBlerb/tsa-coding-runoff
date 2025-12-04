import java.util.Scanner;
import java.util.Random;

public class MazeMaker {
	//creates the variables, the wall, path, and solution should not be changed so they are constants
    private static final char WALL = '#';
    private static final char PATH = ' ';
    private static final char SOLUTION = '.';
    private char[][] maze;
    private int rows;
    private int cols;
    private Random random = new Random();

    public static void main(String[] args) {
    	//creates a scanner to let user input
        Scanner scanner = new Scanner(System.in);
        MazeMaker mazeMaker = new MazeMaker();

        //allows the user to input the desired maze
        while(true) {
            System.out.print("Enter maze rows (>=5): ");
            String inputRows = scanner.nextLine();
            System.out.print("Enter maze columns (>=5): ");
            String inputCols = scanner.nextLine();

            try {
            	//tries to put the inputted number into integers
                int rows = Integer.parseInt(inputRows);
                int cols = Integer.parseInt(inputCols);

                //if the user inputs a number less than 5, sends error message
                if(rows < 5 || cols < 5) {
                    System.out.println("Rows and columns must be >=5.");
                    continue;
                }
                
                //creates the maze
                mazeMaker.generateMaze(rows, cols);
                System.out.println("\nGenerated Maze:");
                mazeMaker.displayMaze();

                //creates the solved maze
                System.out.println("\nSolved Maze:");
                mazeMaker.solveMaze();
                mazeMaker.displayMaze();

                //asks the user if they want to generate another maze or quit the program
                System.out.print("\nGenerate another maze? (y/n): ");
                String again = scanner.nextLine();
                if(!again.equalsIgnoreCase("y")) {
                	//quits the program if the user says no
                	break;
                }

            //if the user does not input a number, provide error message
            } catch(NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
        }

        scanner.close();
    }

    //generates maze
    public void generateMaze(int rows, int cols) {
    	//makes sure the rows and columns are odd numbers, makes it odd if the user inputted an even number
        if(rows % 2 == 0) {
        	rows--;
        }
        if(cols % 2 == 0) {
        	cols--;
        }
        //sets variables
        this.rows = rows;
        this.cols = cols;
        maze = new char[rows][cols];

        //creates a row x column of all walls
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                maze[r][c] = WALL;
            }
        }
        //sets the start of the maze to a path, always going to be at (0, 1)
        maze[0][1] = PATH;
        //sets the end of the maze to a path, always going to be at (row - 1, column - 1)
        maze[rows - 1][cols - 2] = PATH;
        //starts carving out the maze 1 unit below the start, so it will always connect the maze to (1, 1)
        int startRow = 1;
        int startCol = 1;

        //carves the maze
        carvePath(startRow, startCol);
    }
    //carves the maze
    private void carvePath(int r, int c) {
    	//sets (1, 1) to be a path
        maze[r][c] = PATH;

        //gives directions, go up, down, left, or right by 2 units each time so that the maze does not carve out something like a 4x4 path or larger/prevent paths merging
        int[] upDown = {-2, 2, 0, 0};
        int[] leftRight = {0, 0, -2, 2};
        //shuffles the array so that going up, down, left, or right is random
        shuffleArray(upDown, leftRight);

        //loops over the 4 possible directions
        for(int i = 0; i < 4; i++) {
        	//computes the coordinates of the next cell which is 2 units away from the current one in the random direction that is decided above
            int newLeftRight = r + upDown[i];
            int newUpDown = c + leftRight[i];

            //checks if the cell is valid, ensure that it does not go outside of the maze and ensures that it does not carve into other paths, only walls
            if(isInBounds(newLeftRight, newUpDown) && maze[newLeftRight][newUpDown] == WALL) {
            	//since it moves 2 units, this code replaces the middle wall block with a path
                int wallRow = r + upDown[i] / 2;
                int wallCol = c + leftRight[i] / 2;
                if (isInBounds(wallRow, wallCol)) {
                	//turns the wall in between into a path, connecting the 2 paths into a line of 3
                    maze[wallRow][wallCol] = PATH;
                }
                //repeats, carves out a new path in a random direction
                carvePath(newLeftRight, newUpDown);
            }
        }
    }

    //shuffle's array, replaces the elements using the standard shuffle algorithm
    private void shuffleArray(int[] upDown, int[] leftRight) {
        for(int i = upDown.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = upDown[i]; upDown[i] = upDown[j]; upDown[j] = temp;
            temp = leftRight[i]; leftRight[i] = leftRight[j]; leftRight[j] = temp;
        }
    }

    //makes sure that the maze is within bounds
    private boolean isInBounds(int r, int c) {
        return r > 0 && r < rows && c > 0 && c < cols;
    }

    //displays the maze
    public void displayMaze() {
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                System.out.print(maze[r][c]);
            }
            //displays the next line of the maze so that there are r amount of rows
            System.out.println();
        }
    }


    //check if the coordinate is within the maze
    private boolean isWithinMaze(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
    
    //searches the maze for every possible path until an exit is found
    private boolean search(int r, int c, boolean[][] checked) {
    	//if the next block is not within the maze, a wall block, or a already checked path block, return finding the exit as false
        if(!isWithinMaze(r, c) || maze[r][c] == WALL || checked[r][c]) {
        	return false;
        }
        
        //marks the path block as true, so that the code above cannot revisit the same path block (this will always begin at a dead end, starting from dead ends, it goes backwards and checks all possible branches and at first, it should only mark it when the cell is surrounded by 3 walls)
        checked[r][c] = true;

        //check if we reached the exit
        if(r == rows - 1 && c == cols - 2) {
            maze[r][c] = SOLUTION;
            //if we reached the exit, return true
            return true;
        }
        
        //look up, down, left, and right for possible moves
        int[] upDown = {-1, 1, 0, 0};
        int[] leftRight = {0, 0, -1, 1};

        //check every direction until a possible path is found
        for(int i = 0; i < 4; i++) {
        	//mark the new coordinate (cell) that the direction goes in
            int newLeftRight = r + upDown[i];
            int newUpDown = c + leftRight[i];
            
            //recursively checks the direction to see if there are any possible moves, if not, go back to the previous cell and marks the current one as checked
            if(search(newLeftRight, newUpDown, checked)) {
            	//checks for exit
                maze[r][c] = SOLUTION;
                //returns true when exit is found
                return true;
            }
        }
        //if exit is not found, return false and repeat until it is found
        return false;
    }
    
    public boolean solveMaze() {
        boolean[][] checked = new boolean[rows][cols];
        //begins the search and marks the entrance as checked so the search function does not go out of the maze
        return search(0, 1, checked);
    }

}
