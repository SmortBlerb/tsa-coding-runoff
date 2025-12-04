import java.util.*;

public class MorseCodeTranslator {

    static final Map<Character, String> englishToMorse = new HashMap<>();
    static final Map<String, Character> morseToEnglish = new HashMap<>();

    static {
    	//english to morse code
        englishToMorse.put('A', ".-");
        englishToMorse.put('B', "-...");
        englishToMorse.put('C', "-.-.");
        englishToMorse.put('D', "-..");
        englishToMorse.put('E', ".");
        englishToMorse.put('F', "..-.");
        englishToMorse.put('G', "--.");
        englishToMorse.put('H', "....");
        englishToMorse.put('I', "..");
        englishToMorse.put('J', ".---");
        englishToMorse.put('K', "-.-");
        englishToMorse.put('L', ".-..");
        englishToMorse.put('M', "--");
        englishToMorse.put('N', "-.");
        englishToMorse.put('O', "---");
        englishToMorse.put('P', ".--.");
        englishToMorse.put('Q', "--.-");
        englishToMorse.put('R', ".-.");
        englishToMorse.put('S', "...");
        englishToMorse.put('T', "-");
        englishToMorse.put('U', "..-");
        englishToMorse.put('V', "...-");
        englishToMorse.put('W', ".--");
        englishToMorse.put('X', "-..-");
        englishToMorse.put('Y', "-.--");
        englishToMorse.put('Z', "--..");

        englishToMorse.put('0', "-----");
        englishToMorse.put('1', ".----");
        englishToMorse.put('2', "..---");
        englishToMorse.put('3', "...--");
        englishToMorse.put('4', "....-");
        englishToMorse.put('5', ".....");
        englishToMorse.put('6', "-....");
        englishToMorse.put('7', "--...");
        englishToMorse.put('8', "---..");
        englishToMorse.put('9', "----.");

        englishToMorse.put('.', ".-.-.-");
        englishToMorse.put(',', "--..--");
        englishToMorse.put('?', "..--..");
        englishToMorse.put('!', "-.-.--");
        englishToMorse.put('/', "-..-.");
        englishToMorse.put('(', "-.--.");
        englishToMorse.put(')', "-.--.-");

        //reverses the key and value so it turns morse code into english
        for(Map.Entry<Character, String> reverse : englishToMorse.entrySet()) {
            morseToEnglish.put(reverse.getValue(), reverse.getKey());
        }
    }

    public static String englishToMorse(String text) {
    	//string builder so that appending new words is easier
        StringBuilder morse = new StringBuilder();
        //turns text into upper case
        text = text.toUpperCase();
        
        //loops through each character to check for spaces
        for(char english : text.toCharArray()) {
            if(english == ' ') {
            	//if there is a space, add / to the StringBuilder so it seperates the words
                morse.append("/ ");
            //if there is a valid english character, find the morse code of that letter and add that to the StringBuilder
            } else if(englishToMorse.containsKey(english)) {
            	//add a space after the letter to seperate letters
                morse.append(englishToMorse.get(english)).append(" ");
            } else {
            	//if it is not any of the valid characters, report error
                return "Error: Invalid character or Morse sequence detected.";
            }
        }
        //return the toString of the code combined
        return morse.toString();
    }

    
    public static String morseToEnglish(String morse) {
    	//StringBuilder again so that the words can be efficiently put into 1 string
        StringBuilder english = new StringBuilder();
        //splits the morse code into words when there is a /
        String[] sentence = morse.split(" / ");

        //looks through each word
        for(String word : sentence) {
        	//when the words are split using a " ", it is an individual letter so the words are split into letters
            String[] letters = word.split(" ");
            for(String letter : letters) {
            	//converts the morse code into english
                if (morseToEnglish.containsKey(letter)) {
                	//appends it to the StringBuilder
                    english.append(morseToEnglish.get(letter));
                } else {
                	//if there is an invalid character or morse character, return error
                    return "Error: Invalid character or Morse sequence detected.";
                }
            }
            //add a space after each word
            english.append(" ");
        }
        //return the toString
        return english.toString();
    }

    //simulates real time morse
    public static void realTime(String morse) {
    	//converts the morse letter into individual dots and dashes as characters
        for(char letter : morse.toCharArray()) {
        	//print out the first letter
            System.out.print(letter);
            try {
            	//if it is a dot, wait 200ms
                if(letter == '.') {
                	Thread.sleep(200);
                //if it is a dash, wait 600ms
                } else if(letter == '-') {
                	Thread.sleep(600);
                } else {
                //if it is a space wait 300ms
                	Thread.sleep(300);
                }
            //catches InterruptedException because Thread.Sleep can have that error
            } catch(InterruptedException e) {
            	//prevents the thread from being interrupted
                Thread.currentThread().interrupt();
            }
        }
        //goes to next line
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Select mode:");
            System.out.println("1. English to Morse");
            System.out.println("2. Morse to English");
            System.out.println("3. Exit");
            System.out.print("> ");
            String choice = scanner.nextLine();
            //english to morse
            if(choice.equals("1")) {
                System.out.println("Enter text to convert:");
                String text = scanner.nextLine();
                String morse = englishToMorse(text);
                System.out.println("\nMorse Code: ");
                realTime(morse);
            //morse to english
            } else if(choice.equals("2")) {
                System.out.println("Enter Morse code:");
                String code = scanner.nextLine();
                System.out.println("\nEnglish: ");
                System.out.println(morseToEnglish(code));
            //exits program
            } else if(choice.equals("3")) {
                System.out.println("Exiting program...");
                break;

            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
}
