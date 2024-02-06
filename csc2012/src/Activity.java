import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Activity class which contains the activity information such as activityName and tickets.
 * Activity constructor, takes two parameters, one for the activity name and one for the number of available tickets
 * when a new activity is made.
 */
public class Activity implements Comparable<Activity> {
    private String activityName;
    private Integer tickets;

    /*constructor for activity class*/
    public Activity(String activityName, Integer tickets) {
        this.activityName = activityName;
        this.tickets = tickets;
    }

    /**
     * Setters and Getters for the activity class.
     */

    /**
     * setActivityName function takes an activity name as a parameter and sets the activityName of the object to it.
     * @param activityName String of the activity name.
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    /**
     * setTickets function takes a ticket number as a parameter and sets the tickets variable of the object to it.
     * @param tickets Integer of the ticket number.
     */
    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }

    /**
     * Getter for the activity name that returns the Activity activity name variable.
     * @return String of the activity name.
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * Getter for the ticket number that returns the Activity tickets number variable.
     * @return Integer tickets, the number of tickets available for this activity.
     */
    public Integer getTicketNo() {
        return tickets;
    }

    /**
     *  Boolean method to return true if both Activity objects have the same name and false otherwise
     * takes a parameter to differentiate from the default equals()
     */
    public boolean equals(Activity otherActivity) {
        return (activityName.equals(otherActivity.activityName));
    }

    /**
     * Boolean method to check if the activity input from the user is present in the SortedArrayList of activities.
     * returns true if both Activity objects have the same name and false otherwise alongside printing a message.
     *
     * @param activityArray The SortedArrayList of type Activity read from the inputs.txt file.
     * @param activityName String input from the user for the activity name.
     * @return return true if both Activity objects have the same name and false otherwise.
     */
    public static Boolean activityExists(SortedArrayList<Activity> activityArray, String activityName) {

        boolean activityExistsInfo = false;

        for (int i = 0; i < activityArray.size(); i++) {
            if (activityArray.get(i).getActivityName().equalsIgnoreCase(activityName)) {
                activityExistsInfo = true;
                break;
            }
        }
        if (activityExistsInfo) {
            System.out.println("\nThe activity entered is an available activity\n");
            return true;
        } else {
            System.out.println("\nThe activity entered is not an available activity\n");
            return false;
        }
    }

    /**
     *  Update ticket number of the activity the user has input by reducing the amount by the number
     *  of tickets the user wishes to buy. Works out the difference between current number of tickets and the updated
     *  number after tickets are bought. If newTicketNo > 0 the new ticket number is set using the setTickets() setter.
     *
     *  @param activityArray The SortedArrayList of type Activity that contains the available activities.
     *  @param activityName String user input of the activity to update.
     *  @param ticketsBought Integer number of tickets to buy from the user input.
     *  @param firstName user input of the first name of the customer.
     *  @return returns true if tickets successfully bought, false otherwise.
     */
    public static Boolean buyUpdate(SortedArrayList<Activity> activityArray, String activityName, Integer ticketsBought, String firstName) throws IOException {

        for (int i = 0; i < activityArray.size(); i++) {

            if (activityArray.get(i).getActivityName().toLowerCase().equals(activityName)) {
                Integer oldTicketNo = activityArray.get(i).getTicketNo();
                Integer newTicketNo = oldTicketNo - ticketsBought;

                if (newTicketNo >= 0) {
                    activityArray.get(i).setTickets(newTicketNo);
                    System.out.println(ticketsBought + " tickets have been successfully bought");
                    System.out.println("The new number of remaining tickets for " + activityName + " is " + activityArray.get(i).getTicketNo() + " \n");
                    return true;
                } else {
                    System.out.println("There are not enough tickets left to buy that many");
                    System.out.println(activityName + " has " + oldTicketNo + " tickets left\n");

                    Activity.letterOutput(firstName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Function to print a letter to the letter.txt file when customer was unable to successfully buy tickets.
     * wraps a filewriter to a bufferedwriter and then wraps that to a printwriter so that each message is printed to a
     * new line.
     *
     * @param firstName String name input by the user.
     * @throws IOException error message if letter.txt cannot be opened or found.
     */
    public static void letterOutput(String firstName) throws IOException {
        String letterText = "I am sorry to inform you that the recent tickets you wished to buy are either unavailable or" +
                " there are not enough tickets to fulfill your request. We are very sorry for this inconvenience" +
                " and have made sure to refund your transaction. If you still wish to purchase tickets you may be" +
                " able to try again at a later date or may purchase a lower number of tickets if they are available\n";

        try (FileWriter letterWriter = new FileWriter("src\\letters.txt", true);
                BufferedWriter bw = new BufferedWriter(letterWriter);
                PrintWriter pw = new PrintWriter(bw);) {

            pw.println("dear " + firstName);
            pw.println(letterText);
            pw.close();
            System.out.println("A letter has been printed to the customer file to let them know that the tickets could not be purchased.\n");

        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that is called once each time the program is executed which wipes the letter.txt file by overwriting
     * its contents with an empty string.
     *
     * @throws IOException error message if the letters.txt file could not be written to or found.
     */
    public static void letterFileWiper() throws IOException {
        PrintWriter printWiper = new PrintWriter("src\\letters.txt");
        printWiper.print("");
        printWiper.close();
    }

    /**
     * Function that sets the number of tickets for the activity that matches the activity name input by the user.
     * If tickets are cancelled successfully determined by the activityCancelUpdate fucntion, the Activity associated
     * will have its available tickets updated to the new number of tickets.
     *
     * @param activityArray The SortedArrayList of type Activity that contains the activities read from inputs.txt.
     * @param activityName String name of the activity input by the user.
     * @param ticketsCancelled Integer number of tickets to be cancelled input by the user.
     */
    public static void cancelUpdate(SortedArrayList<Activity> activityArray, String activityName, Integer ticketsCancelled) {
        for (int i = 0; i < activityArray.size(); i++) {

            if (activityArray.get(i).getActivityName().toLowerCase().equals(activityName)) {
                Integer oldTicketNo = activityArray.get(i).getTicketNo();
                Integer newTicketNo = oldTicketNo + ticketsCancelled;

                activityArray.get(i).setTickets(newTicketNo);
                System.out.println("The new number of remaining tickets for " + activityName + " is " + activityArray.get(i).getTicketNo() + " \n");
            }
        }
    }

    /**
     * Function that iterates through the ArrayList of activities and prints the activity names and the associated
     * number of tickets.
     *
     * @param activity SortedArrayList of type Activity containing all the activities read from input.txt.
     */
    public static void activityPrint(SortedArrayList<Activity> activity) {
        for (int i = 0; i < activity.size(); i++) {
            String activityName = activity.get(i).getActivityName();
            Integer ticketNo = activity.get(i).getTicketNo();
            System.out.println("Name:      " + activityName);
            System.out.println("Tickets:   " + ticketNo);
            System.out.println("--------------------------------");
        }
        System.out.println("\n");
    }

    /**
     * Method that overrides the compareTo method in the Comparable interface to compare Activity class activity names
     */
    @Override
    public int compareTo(Activity activity) {
        return this.getActivityName().compareTo(activity.getActivityName());
    }
}
