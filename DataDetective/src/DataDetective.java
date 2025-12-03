import java.util.ArrayList;

public class DataDetective {
    public static void main(String[] args) throws Exception {
        int[] e = inputToList("88, 92, 75, 92, 100");
        AnalyzeData(e);
        System.out.println("done");
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
        // Actual data we want
        int dataSize = data.length;
        double mean;
        int median = 0;
        int mode = 0;
        int range;
        double standardDeviation;

        // Neccessary variables to track in order to find data
        double total = 0;
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
        double trueMiddle;
        double medianDiff = Integer.MAX_VALUE;
        ArrayList<Double> squaredDiffs = new ArrayList<>();
        double squaredDiffSum = 0;
        double avgSquaredDiffs; 
        String dataString = data.toString();

        for(int i = 0; i < data.length; i++) { // Loop through the list of ints to get a bunch of sub-data
            total += data[i]; // Sum total of data values

            if(data[i] < low) { // Lowest and highest values
                low = data[i];
            }
            if(data[i] > high) {
                high = data[i];
            }
        }
        mean = (double)(total / dataSize); // Mean found!
        range = high - low;
        trueMiddle = (double)((high + low) / 2);
        System.out.println(low);

        for(int i = 0; i < data.length; i++) { // Second pass with more data
            if (Math.abs(trueMiddle - data[i]) < medianDiff) {
                median = data[i];
                medianDiff = Math.abs(trueMiddle - data[i]);
            }
            squaredDiffs.add(Math.sqrt((double)(mean - data[i])));
        }
        
        for(int i = 0; i < squaredDiffs.size(); i++) { // Third pass for standard deviation
            squaredDiffSum += squaredDiffs.get(i);
        }
        standardDeviation = Math.sqrt((double)(squaredDiffSum / squaredDiffs.size()));

        System.out.println("Mean = " + mean);
        System.out.println("Median = " + median);
        System.out.println("Mode = " + mode);
        System.out.println("Range = " + range);
        System.out.println("Standard Deviation = " + standardDeviation);
    }
}
