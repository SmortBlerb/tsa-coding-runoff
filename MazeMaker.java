import java.util.Scanner;
import java.util.Random;

public class MazeMaker {
    private static final char WALL = '#';
    private static final char PATH = ' ';
    private static final char SOLUTION = '.';
    private char[][] maze;
    private int rows;
    private int cols;
    private Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MazeMaker mazeMaker = new MazeMaker();

        while(true) {
            System.out.print("Enter maze rows (>=5): ");
            String inputRows = scanner.nextLine();
            System.out.print("Enter maze columns (>=5): ");
            String inputCols = scanner.nextLine();

            try {
                int rows = Integer.parseInt(inputRows);
                int cols = Integer.parseInt(inputCols);

                if(rows < 5 || cols < 5) {
                    System.out.println("Rows and columns must be >=5.");
                    continue;
                }

                mazeMaker.generateMaze(rows, cols);
                System.out.println("\nGenerated Maze:");
                mazeMaker.displayMaze();

                System.out.println("\nSolved Maze:");
                mazeMaker.solveMaze();
                mazeMaker.displayMaze();

                System.out.print("\nGenerate another maze? (y/n): ");
                String again = scanner.nextLine();
                if(!again.equalsIgnoreCase("y")) break;

            } catch(NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
        }

        scanner.close();
    }

    public void generateMaze(int rows, int cols) {	
        if (rows % 2 == 0) rows--;
        if (cols % 2 == 0) cols--;
        this.rows = rows;
        this.cols = cols;
        maze = new char[rows][cols];

        for(int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                maze[r][c] = WALL;
            }
        }
        maze[0][1] = PATH;
        maze[rows - 1][cols - 2] = PATH;
        int startRow = 1;
        int startCol = 1;

        carvePath(startRow, startCol);
    }

    private void carvePath(int r, int c) {
        maze[r][c] = PATH;

        int[] upDown = {-2, 2, 0, 0};
        int[] leftRight = {0, 0, -2, 2};
        shuffleArray(upDown, leftRight);

        for (int i = 0; i < 4; i++) {
            int newLeftRight = r + upDown[i];
            int newUpDown = c + leftRight[i];

            if (isInBounds(newLeftRight, newUpDown) && maze[newLeftRight][newUpDown] == WALL) {
                int wallRow = r + upDown[i] / 2;
                int wallCol = c + leftRight[i] / 2;
                if (isInBounds(wallRow, wallCol)) {
                    maze[wallRow][wallCol] = PATH;
                }
                carvePath(newLeftRight, newUpDown);
            }
        }
    }

    private void shuffleArray(int[] upDown, int[] leftRight) {
        for (int i = upDown.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = upDown[i]; upDown[i] = upDown[j]; upDown[j] = temp;
            temp = leftRight[i]; leftRight[i] = leftRight[j]; leftRight[j] = temp;
        }
    }

    private boolean isInBounds(int r, int c) {
        return r > 0 && r < rows && c > 0 && c < cols;
    }

    public void displayMaze() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(maze[r][c]);
            }
            System.out.println();
        }
    }

}
