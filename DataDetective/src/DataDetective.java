import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataDetective {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        char[] inCharArray = in.toCharArray();

        while(!checkInput(inCharArray)) {
            System.out.println("WRONG");
                
            in = input.nextLine();
            inCharArray = in.toCharArray();
        }
    }

    public static boolean checkInput(char[] input) {
        for(int i = 0; i < input.length; i++) {
            if (!(Character.isDigit(input[i]) || input[i] == ',' || input[i] == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static int[] inputToList(String input) {
        int right;
        String temp = input;
        ArrayList<Integer> answer = new ArrayList<>();

        while(temp.contains(",")) {
            right = temp.indexOf(",");
            answer.add(Integer.valueOf(temp.substring(0, right)));

            temp = temp.substring(right + 2, temp.length());
        }
        answer.add(Integer.valueOf(temp.substring(0, temp.length())));

        return answer.stream().mapToInt(i -> i).toArray();
    }

    public static void AnalyzeData(int[] data) {
        int[] sortedData = data;
        Arrays.sort(sortedData);
        
        // Actual data we want
        int dataSize = sortedData.length;
        double mean;
        int median = 0;
        int mode = 0;
        int range;
        double standardDeviation;

        // Neccessary variables to track in order to find data
        double total = 0;
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
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
        mean = (double)(total / dataSize); // Mean found!
        range = high - low;

        if(sortedData.length % 2 != 0) {
            median = sortedData[(int)((sortedData.length / 2) + 0.5)];
        } else {
            median = sortedData[sortedData.length / 2] + (sortedData.length / 2) + 1 / 2;
        }

        for(int i = 0; i < sortedData.length - 1; i++) {
            if(sortedData[i] == sortedData[i + 1]) {
                modeAppearences++;
            } else {
                modeAppearences = 0;
            }

            if(modeAppearences > prevModeAppearences) {
                mode = sortedData[i];
            }
        }

        for(int i = 0; i < sortedData.length; i++) { // Getting the squared difference for below
            squaredDiffs.add(Math.pow(sortedData[i] - mean, 2));
        }
        
        for(int i = 0; i < squaredDiffs.size(); i++) { // Standard deviation
            squaredDiffSum += squaredDiffs.get(i);
        }
        standardDeviation = Math.sqrt((double)squaredDiffSum / (double)squaredDiffs.size());

        System.out.println("Mean = " + mean);
        System.out.println("Median = " + median);
        System.out.println("Mode = " + mode);
        System.out.println("Range = " + range);
        System.out.println("Standard Deviation = " + standardDeviation);
    }
}
