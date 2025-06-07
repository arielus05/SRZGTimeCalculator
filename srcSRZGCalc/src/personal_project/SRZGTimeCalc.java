package personal_project;

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Sonic Riders: Zero Gravity Time Calculator
 * @author arielus05
 * @version 6/6/2025
 */
public class SRZGTimeCalc {
	/**
	 * The track list for Heroes Story
	 */
    private final String[] heroesStoryTracks = {
        "Megalo Station",
        "Botanical Kingdom",
        "MeteorTech Premises",
        "Aquatic Capital",
        "Gigan Rocks",
        "Crimson Crater"
    };
    
    /**
     * The track list for Babylon Story
     */
    private final String[] babylonStoryTracks = {
        "Gigan Device",
        "Nightside Rush",
        "Snowy Kingdom",
        "Meteortech Sparkworks",
        "Tempest Waterway",
        "Security Corridor",
        "Mobius Strip"
    };
    
    /**
     * The shared Scanner input that is used throughout the entire program
     */
    private Scanner input;
    /**
     * Heroes Story 2D array
     */
    private double[][] heroesStory;
    /**
     * Babylon Story 2D array
     */
    private double[][] babylonStory;
    /**
     * Total minutes, used for the final time
     */
    private int totalMinutes;
    /**
     * Total seconds, used for the final time
     */
    private double totalSeconds;
    /**
     * The total time (in seconds), used to help find totalMinutes and totalSeconds
     */
    private double totalTime;
    /**
     * incremented every time the user wishes to continue calculating time for story modes
     */
    private int runCounter;
    
    /**
     * Initializes the object's fields to their default values.
     * Heroes Story has 6 tracks, Babylon Story has 7
     * @param input it is shared throughout the entire program to be used for Scanner
     */
    public SRZGTimeCalc(Scanner input) {
        this.input = input;
        this.heroesStory = new double[6][2];
        this.babylonStory = new double[7][2];
        this.totalMinutes = 0;
        this.totalSeconds = 0;
        this.totalTime = 0;
        this.runCounter = 1;
    }
    
    /**
     * getter for heroesStoryTracks
     * @return heroesStoryTracks
     */
    public String[] getHeroesStoryTracks() {
    	return heroesStoryTracks;
    }
    
    /**
     * getter for babylonStoryTracks
     * @return babylonStoryTracks
     */
    public String[] getBabylonStoryTracks() {
    	return babylonStoryTracks;
    }
    
    /**
     * getter for heroesStory
     * @return heroesStory
     */
    public double[][] getHeroesStory() {
    	return heroesStory;
    }
    
    /**
     * getter for babylonStory
     * @return babylonStory
     */
    public double[][] getBabylonStory() {
    	return babylonStory;
    }
    
    /**
     * getter for totalTime
     * @return totalTime
     */
    public double getTotalTime() {
    	return totalTime;
    }
    
    /**
     * setter for totalTime
     * @param timeArray used to iteratively sum up for totalTime
     */
    public void setTotalTime(double[][] timeArray) {
    	totalTime = 0;
    	timeArray = setTimeArray(timeArray);
        
        for (int i = 0; i < timeArray.length; i++) {
        	totalTime += (timeArray[i][0] * 60) + timeArray[i][1];
        }
    }
    
    /**
     * getter for totalMinutes
     * @return totalMinutes
     */
    public double getTotalMinutes() {
    	return totalMinutes;
    }
    
    /**
     * setter for totalMinutes
     */
    public void setTotalMinutes() {
        totalMinutes = (int)Math.floor(totalTime / 60);
    }
    
    /**
     * getter for totalSeconds
     * @return totalSeconds
     */
    public double getTotalSeconds() {
    	return totalSeconds;
    }
    
    /**
     * setter for totalSeconds.
     * the seconds left over after the minutes from the total time
     */
    public void setTotalSeconds() {	
    	totalSeconds = totalTime - totalMinutes * 60;
    }
    
    /**
     * getter for runCounter
     * @return runCounter
     */
    public int getRunCounter() {
    	return runCounter;
    }
    
    /**
     * reset all the fields back to their default values
     */
    public void resetFields() {
    	heroesStory = new double[6][2];
        babylonStory = new double[7][2];
        totalMinutes = 0;
        totalSeconds = 0;
        totalTime = 0;
    }
    
    /**
     * If all the elements in the array are equal to zero, then it is considered empty.
     * helper method for setTimeArray()
     * @param array whatever array needs to be evaluated for its
     * @return true if empty, false if not
     */
    private boolean isEmptyArray(double[][] array) {
    	boolean empty = true;
    	for (int i = 0; i < array.length; i++) {
    		if ( (array[i][0] != 0) || (array[i][1] != 0) ) {
    			empty = false;
    		}
    	}
    	
    	return empty;
    }
    
    /**
     * Contains both the negativeInputTime and twoDecimalPlaces checks.
     * If both of these checks pass then the values Scanner input passed thru are so far appropriate.
     * helper method for setTimeArray()
     * @param minutes the minutes for the specific track
     * @param seconds the seconds for the specific track
     * @return true if the checks are passed, false otherwise
     */
    private boolean isValidTimeInput(int minutes, double seconds) {
    	// checks if the user inputted negative time
    	if (minutes < 0 || seconds < 0 || seconds >= 60) {
    		System.err.println("Negative time is not allowed. Try again.");
    		return false;
		}
    	
    	// checks if the user inputted more than 2 decimal places for seconds
    	double hundredths = Math.round(seconds * 100);
		if (Math.abs(seconds * 100 - hundredths) > 0.0001) {
			System.err.println("Too many digits in milliseconds (XX.XX expected). Try again.");
			return false;
		}
		
		return true;
    }
    
    /**
     * A general method to handle either Heroes or Babylon Story.
     * For each track, the user is prompted to input the minutes and seconds,
     * With lots of custom and normal exception handling.
     * To make sure the program only accepts valid values
     * @param timeArray used to store the minutes and seconds for each track in the specified story mode
     * @return an updated timeArray that is all filled in
     */
    public double[][] setTimeArray(double[][] timeArray) {
    	// empty check so if timeArr is already filled in then
    	// no need to use Scanner input again
    	if (!isEmptyArray(timeArray)) {
    		return timeArray;
    	}
    	
    	for (int i = 0; i < timeArray.length; i++) {
    		while (true) {
    			// user prompts
            	if (timeArray.length == 6) {
            		System.out.print("Time for " + heroesStoryTracks[i] + "? ");
            	}
            	else {
            		System.out.print("Time for " + babylonStoryTracks[i] + "? ");
            	}
            	
            	try {
            		String line = input.nextLine().trim();
            		
            		// if user enters nothing or only whitespace
                    if (line.isEmpty()) {
                        System.err.println("Input cannot be blank. Try again.");
                        i--;
                        continue;
                    }
                    
                    String[] parts = line.split("\\s+");
                    
                    // checks if the user inputted two numbers
                	if (parts.length != 2) {
                        System.err.println("Please enter exactly two numbers (minutes and seconds). Try again.");
                        continue;
                    }
                	
                    int minutes = Integer.parseInt(parts[0]);
                    double seconds = Double.parseDouble(parts[1]);
            		
                    // explained by comments in method
                    if (isValidTimeInput(minutes, seconds)) {
                    	timeArray[i][0] = minutes;
                    	timeArray[i][1] = seconds;
                        break;
                    }
            	}
            	// if the user inputs something invalid
            	catch (NumberFormatException nfe) {
            		System.err.println("Please only enter integers and decimals respectively. Try again.");
            	}
            	catch (Exception e) {
            		System.err.println("Unexpected error: " + e.getMessage());
            		e.printStackTrace();
            	}
    		}
    		
        }
    	
    	return timeArray;
    }
    
    /**
     * To format the seconds into how they are in SR:ZG.
     * helper method to getTimeArrayString() and getFinalTimeString().
     * @param seconds used to divide into whole seconds and milliseconds
     * @return the String version of seconds but in the time format: XX"XX
     */
    private String formatSeconds(double seconds) {
        int intSeconds = (int)seconds;
        int milliseconds = (int)Math.round((seconds - intSeconds) * 100);

        // pad seconds and milliseconds to 2 digits
        String secondsString = String.format("%02d", intSeconds);
        String millisString = String.format("%02d", milliseconds);
        
        return secondsString + "\"" + millisString;
    }
    
    /**
     * Organizes the data from double[][] timeArray into a readable manner.
     * Every row follows this format: "[track name]: X'YY"YY".
     * X = minutes, Y = seconds.
     * helper method for getStoryModeTimes()
     * @param timeArray iterated thru to help create its formatted String version
     * @param storyString used to help determine what story mode we are handling
     * @return a formatted String version of double[][] timeArray.
     */
    public String getTimeArrayString(double[][] timeArray, String storyString) {
    	StringBuilder sb = new StringBuilder();
        timeArray = setTimeArray(timeArray);
        
        sb.append("\n");
        sb.append(storyString + " - Track Times");
        sb.append("\n");

        for (int i = 0; i < timeArray.length; i++) {
            int minutes = (int)timeArray[i][0];
            double seconds = timeArray[i][1];
            
            // minutes and seconds in SR:ZG have a 0 in front of them if num < 10
            String minutesString = String.format("%02d", minutes);

            if (timeArray.length == 6) {
                sb.append(heroesStoryTracks[i]);
            }
            else {
            	sb.append(babylonStoryTracks[i]);
            }

            sb.append(": " + minutesString + "'" + formatSeconds(seconds));
            sb.append("\n");
        }

        sb.append("\n");
        
        return sb.toString();
    }
    
    /**
     * Where all the relevant data is in String form
     * @param storyString used to assist which story mode we are working with
     * @return the String outputs from both getTimeArrayString() and getFinalTimeString() combined, depending on storyString
     */
    public String getStoryModeTimes(String storyString) {
    	StringBuilder sb = new StringBuilder();
    	
        if (storyString.equals("Heroes Story")) {
        	setTotalTime(heroesStory);
        	sb.append(getTimeArrayString(heroesStory, storyString));
        }
        else if (storyString.equals("Babylon Story")) {
        	setTotalTime(babylonStory);
        	sb.append(getTimeArrayString(babylonStory, storyString));
        }
        else if (storyString.equals("All Stories")) {
        	double allStoriesTime = 0;
        	
        	sb.append(getStoryModeTimes("Heroes Story"));
        	allStoriesTime += totalTime;
        	
        	sb.append(getStoryModeTimes("Babylon Story"));
        	allStoriesTime += totalTime;
        	
        	totalTime = allStoriesTime;	
        	
        	sb.append("\n");
        }
        else {
        	return "lol pretty sure this is impossible to trigger but whatever??";
        }
        
        sb.append(getFinalTimeString(storyString));
        
        return sb.toString();
    }
    
    /**
     * helper method for getStoryModeTimes()
     * @param storyString used to provide story mode string as part of the final string
     * @return the final story mode time as a String and in this format: "Your X Story time is: X minutes and X.XX seconds. (X'XX"XX IGT)"
     */
    private String getFinalTimeString(String storyString) {
    	StringBuilder sb = new StringBuilder();
    	
    	setTotalMinutes();
        setTotalSeconds();
        
        // "Your X Story time is: X minutes and X.XX seconds. (X'XX"XX IGT)"
        sb.append("Your " + storyString + " time is: " + totalMinutes + " minutes and " +
                String.format("%.2f", totalSeconds) + " seconds. (" + 
        		String.format("%02d", totalMinutes) + "'" + 
                formatSeconds(totalSeconds) + " IGT)");
        sb.append("\n");
        
        return sb.toString();
    }
    
    /**
     * Tells the user the time input format they will use so that Scanner input can read it.
     * Run directly by main method
     */
    public void printFormatInstructions() {
    	System.out.println("------------------------------------------IMPORTANT------------------------------------------");
    	System.out.println("TIME INPUT FORMAT (ENTER EVERYTHING IN ONE LINE): X (minutes) X.XX (seconds & milliseconds).");
    	System.out.println("EXAMPLE: 02'59\"07 --> 2 59.07");
        System.out.println("IF TIME IS ONLY IN MINUTES/SECONDS, HAVE MINUTES/SECONDS SET AS 0.");
    }
    
    /**
     * Story mode prompts.
     * helper method for run()
     */
    private void printStoryModePrompt() {
    	System.out.println();
    	System.out.println("Which story mode do you want to add up?");
        System.out.println("1 - Heroes Story");
        System.out.println("2 - Babylon Story");
        System.out.println("3 - All Stories");
        System.out.println("4 - Quit");
        System.out.println();
    }
    
    /**
     * helper method for run()
     * @return a valid int that can be used by chooseStory in run()
     */
    private int getValidatedInt() {
        while (true) {
            String line = input.nextLine().trim();
            
            // if user enters nothing or only whitespace
            if (line.isEmpty()) {
                System.err.println("Input cannot be blank.");
                printStoryModePrompt();
                continue;
            }
            
            try {
            	return Integer.parseInt(line);
            }
            // if invalid input
            catch (NumberFormatException e) {
            	System.err.println("Please enter a valid integer.");
            	printStoryModePrompt();
            }
        }
    }
    
    /**
     * Writes relevant data to the times.txt text file.
     * helper method for run()
     * @param storyString used as a parameter for getStoryModeTimes() to help what gets written into the file
     */
    private void writeToTextFile(String storyString) {
    	String fileName = "times.txt";
    			
    	try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
    		writer.println("run #" + runCounter + ":");
            writer.println(getStoryModeTimes(storyString));
            writer.println();
        }
    	catch (IOException e) {
    		System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    /**
     * Clears the times.txt text file on runtime.
     * helper method for run()
     */
    private void clearTextFile() {
        try (@SuppressWarnings("unused")
		PrintWriter writer = new PrintWriter("times.txt")) {
            // opening in write mode with no content clears the file
        } catch (IOException e) {
            System.err.println("Failed to clear times.txt: " + e.getMessage());
        }
    }
    
    /**
     * Prints some messages relevant to the user, then the program exits from runtime.
     * helper method for rePromptStoryMode() and run()
     */
    private void quitProgram() {
    	System.out.println("All of the relevant data was written into times.txt.");
        System.out.println("Thanks for using SRZGTimeCalc :') -arielus05");
        System.exit(0);
    }
    
    /**
     * Prompts the user about whether they want to continue calculating story mode times or not.
     * If the user selects 'y', the program continues/reruns.
     * If not, the program terminates.
     * helper method for run()
     */
    private void promptContinueCalculator() {
        System.out.println("Would you like to calculate time for another story mode? (y/n)");
        String response = input.nextLine().trim();

        if (response.equalsIgnoreCase("n")) {	// user selects no
        	quitProgram();
        }
        // user selects something other than "y" or "n"
        else if (!response.equalsIgnoreCase("y")) {
        	System.out.println("Invalid input! Program will now soon terminate.");
        	quitProgram();
        }
        else {	// response == "y"
        	resetFields();
        	runCounter++;
        }
    }
    
    //test
    /**
     * Everything happening in the code ultimately gets printed from here.
     * Run directly by main method
     */
    public void run() {
    	// times.txt is cleared every time the program is run
    	clearTextFile();

        while (true) {
        	printStoryModePrompt();

            int chooseStory = 0;
            chooseStory = getValidatedInt();

            switch (chooseStory) {
                case 1:		// heroes story
                	System.out.println();
                	System.out.println(getStoryModeTimes("Heroes Story"));
                    writeToTextFile("Heroes Story");
                    break;
                case 2:		// babylon story
                	System.out.println();
                	System.out.println(getStoryModeTimes("Babylon Story"));
                    writeToTextFile("Babylon Story");
                    break;
                case 3:		// all stories
                	System.out.println();
                	System.out.println(getStoryModeTimes("All Stories"));
                    writeToTextFile("All Stories");
                    break;
                case 4:		// quit
                	System.out.println("The user chose to quit.");
                	quitProgram();
                default:
                    System.err.println("Enter a number from 1–4 lol");
                    continue; // skip the yes/no prompt if invalid input
            }
            
            promptContinueCalculator();
        }
    }
    
    /**
     * Main method
     * @param args for main method
     */
    public static void main(String[] args) {
        Scanner sharedInput = new Scanner(System.in);
        SRZGTimeCalc calc = new SRZGTimeCalc(sharedInput);
        calc.printFormatInstructions();
        calc.run();
        sharedInput.close();
    }
}