import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataDetective {
    public static void main(String[] args) throws Exception {
        // Input variables
        Scanner input = new Scanner(System.in);
        String in = "";
        char[] inCharArray;

        // Analysis variables
        double[] data;

        while(!in.equals("-1")) { // -1 is exit code
            // Explain what to do
            System.out.println("--- DATA ANALYZER ---");
            System.out.println("Format data as: 12, 45, 22, etc.");
            System.out.println("Input JUST -1 to exit\n");
            System.out.print("Data: ");
            in = input.nextLine();
            inCharArray = in.toCharArray();

            // Check use input for invalid characters
            while(!checkInput(inCharArray)) {
                System.out.println("Invalid input, please try again");
                
                System.out.print("Data: ");
                in = input.nextLine();
                inCharArray = in.toCharArray();
            }
            
            // Don't analyze data if user exits
            if(in.equals("-1")) {
            } else {
                data = inputToList(in);
                AnalyzeData(data);
            }
        }
        System.out.println("[Program exited]");
        input.close();
    }

    public static boolean checkInput(char[] input) {
        if(input.length == 0) return false; // Check that there is an actual input (if the user only pressed enter)
        if(input[0] == '.' || input[0] == ','|| input[0] == ' ' || input[0] == '-') return false; // Check if start of input is valid
        
        // Check each character to be sure nothing is EVIL (not allowed)
        for(int i = 0; i < input.length; i++) {
            if (!(Character.isDigit(input[i]) || input[i] == ','  || input[i] == '.' || input[i] == ' ' || input[i] == '-')) {
                return false;
            }

            if(i == input.length - 1) {
                if (input[i] == ',') {
                    return false;
                }
            } else if ((input[i] == ',' || input[i] == '.') && input[i + 1] == ',' || input[i + 1] == '.') {
                return false;
            }
        }
        return true;
    }

    public static double[] inputToList(String input) {
        // Pointer and temp string to sift through string for actual numbers
        int right;
        String temp = input;
        String number;
        ArrayList<Double> answer = new ArrayList<>();

        // Loop while more than the last number exists
        while(temp.contains(",")) {
            // Update pointer, get number, and add number to answer list
            right = temp.indexOf(",");
            number = temp.substring(0, right);
            answer.add(Double.valueOf(number));
            // Increment right depending on if there is a space after the comma or not
            if(temp.charAt(right + 1) == ' ') {
                right++;
            }
            right++;
            // Adjust temp to no longer contain the added number
            temp = temp.substring(right, temp.length());
        }
        // Add the last number
        answer.add(Double.valueOf((temp.substring(0, temp.length()))));
        
        // Convert Integer Arraylist to string using a map (mini for-loop)
        return answer.stream().mapToDouble(i -> i).toArray();
    }

    public static void AnalyzeData(double[] data) throws InterruptedException {
        // Sort data before anything
        double[] sortedData = data;
        Arrays.sort(sortedData);
        System.out.println("\nSorted Data: " + Arrays.toString(sortedData));
        Thread.sleep(200);
        
        // Actual data we want
        int dataSize = sortedData.length;
        double mean;
        double median;
        double mode = 0;
        double range;
        double standardDeviation;

        // Neccessary variables to track in order to find data
        double total = 0;
        double low = Integer.MAX_VALUE;
        double high = Integer.MIN_VALUE;
        ArrayList<Double> squaredDiffs = new ArrayList<>();
        double squaredDiffSum = 0;
        int modeAppearences = 0;
        int prevModeAppearences = 0;

        for(int i = 0; i < sortedData.length; i++) { // Loop through the list of ints to get a bunch of sub-data
            total += sortedData[i]; // Sum total of data values

            if(sortedData[i] < low) { // Lowest and highest values
                low = data[i];
            }
            if(sortedData[i] > high) {
                high = data[i];
            }
        }
        mean = (double)(total / dataSize); // Mean found (1/5)!
        range = high - low; // Range found (2/5)!

        if(sortedData.length % 2 != 0) { // If odd, just get the middle term
            median = sortedData[(int)((double)(sortedData.length / 2) + 0.5)];
        } else { // If even, get the number between the two middle terms
            median = (double)(sortedData[(sortedData.length / 2) - 1] + sortedData[sortedData.length / 2]) / 2.0;
            System.out.println(sortedData[(sortedData.length / 2) - 1] + sortedData[sortedData.length / 2]);
        }
        // Median found (3/5)!

        for(int i = 0; i < sortedData.length - 1; i++) { // Check how many times the mode appears in a sorted array by checking the next element
            if(sortedData[i] == sortedData[i + 1]) {
                modeAppearences++;
            } else {
                modeAppearences = 0;
            }

            if(modeAppearences > prevModeAppearences) { // Check if number beat old mode 'high-score'
                mode = sortedData[i];
                prevModeAppearences = modeAppearences;
            }
        }
        // Mode found (4/5)!

        for(int i = 0; i < sortedData.length; i++) { // Getting the squared difference for below
            squaredDiffs.add(Math.pow(sortedData[i] - mean, 2));
        }
        
        for(int i = 0; i < squaredDiffs.size(); i++) { // Standard deviation
            squaredDiffSum += squaredDiffs.get(i);
        }
        standardDeviation = Math.sqrt((double)squaredDiffSum / (double)squaredDiffs.size()); // Standard deviation found (5/5)!

        System.out.println("Mean = " + mean);
        Thread.sleep(350); // Delays so the user has time to understand the program did something
        System.out.println("Median = " + median);
        Thread.sleep(350);
        if(prevModeAppearences > 0) {
            System.out.println("Mode = " + mode);
        } else {
            System.out.println("Mode = No mode present"); // If no numbers repeat, there is no mode
        }
        Thread.sleep(350);
        System.out.println("Range = " + range);
        Thread.sleep(350);
        System.out.println("Standard Deviation = " + standardDeviation + "\n");
        Thread.sleep(1000);
    }
}