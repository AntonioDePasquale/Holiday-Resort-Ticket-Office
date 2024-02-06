import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The MainProgram class that contains the main method.
 */
public class MainProgram {

    /**
     *Main method, contains all the functions needed to run the program.
     *The contained methods are placed here to mostly run only once (start being the exception).
     * The sorted arraylists for the customer class and the activity class are created here.
     * The scanner scan that is used throughout the program is created here.
     * The reader function which reads the input.txt file is here, so it is only read once.
     * The start function is called to present the option menu.
     */
    public static void main(String[] args) throws IOException {

        Activity.letterFileWiper();
        Scanner scan = new Scanner(System.in);
        SortedArrayList<Activity> activityArrayList = new SortedArrayList<>();
        SortedArrayList<Customer> customerArrayList = new SortedArrayList<>();
        reader(activityArrayList, customerArrayList);
        start(activityArrayList, customerArrayList, scan);
    }

    /**
     * The start function runs a while loop with the return from the optionMenu as a condition.
     * The optionMenu function is a boolean that returns true until the program is manually ended where
     * it returns false ending the while loop. while optionMenu keep returning true it will run itself recursively
     * to reload the menu after each switch statement is executed.
     *
     * @param activity the sorted array list of activities.
     * @param customer the sorted array list of customers.
     * @param scan the scanner to read user input.
     */
    public static void start(SortedArrayList<Activity> activity, SortedArrayList<Customer> customer, Scanner scan) throws IOException {

        while (optionMenu(activity, customer,scan)){
            optionMenu(activity, customer,scan);
        }
    }

    /**
     * Function to read the data line by line from input.txt file while the line is not null and line length > 0.
     * The first line is read and parsed as an int to a variable called activityNumber then multiplied by two to get the
     * number of lines to be read for activity data. for loop iterates through the activities in pairs of two. The first line is saved as
     * the activityName and the second line is saved as the ticketNumber. Both variables are parameters to create an Activity object using the constructor.
     * The next line read is the number of customers. Using the number of customers a for loop iterates through the next lines splitting each line into a
     * first and second name which are parameters used to create a Customer object with its constructor.
     * Both the Activity and Customer objects are inserted into sorted ArrayLists using the Insert function outlined in the sortedArrayList class.
     *
     * @param activityArrayList The sorted array list of activities.
     * @param customerArrayList the sorted array list of customers.
     */
    public static void reader(SortedArrayList<Activity> activityArrayList, SortedArrayList<Customer> customerArrayList) {

        ArrayList<String> fileLineArray = new ArrayList<>();
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader("src/input.txt"));
            String line = buffReader.readLine();

            while (line != null && line.length() > 0) {
                fileLineArray.add(line);
                line = buffReader.readLine();
            }
            Integer activityNumber = (Integer.parseInt(fileLineArray.get(0)) * 2);

            for (int i = 1; i < activityNumber; i = i + 2) {
                String activityName = fileLineArray.get(i);
                Integer ticketNo = Integer.parseInt(fileLineArray.get(i + 1));
                Activity tempActivity = new Activity(activityName, ticketNo);
                activityArrayList.insert(tempActivity);
            }
            for (int i = (activityNumber + 2); i < (fileLineArray.size()); i++) {
                String[] splitName = fileLineArray.get(i).split(" ");
                String firstName = splitName[0];
                String lastName = splitName[1];
                Customer tempCustomer = new Customer(firstName, lastName);
                customerArrayList.insert(tempCustomer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * optionMenu function to display switch statement options to the console and taking user input to execute each option.
     * f - returning false ends the start function and the optionMenu recursion is broken, ending the program.
     * a - calls the activityPrint function outlined in the Activity class. This prints the activity information.
     * c - calls the customerPrint function outlined in the Customer class. This prints the customer information.
     * t - systematically asks the user for customer name and activity name information. The inputs are saved as variables
     * and functions such as registered which checks to see if the name input is a valid customer. activityExists to check if
     * the activity input is a valid activity. buyUpdate and activityBuyUpdate to check if enough tickets are available and if
     * there are enough tickets to buy only then are the numbers updated accordingly. If a customer buys tickets for a new activity
     * it is added to that customers activity list up to a maximum of 3 activities. Each customer activity has its own customer ticket number.
     * r - Similar to the t statement code above which takes user input for the customer name, activity name and number of tickets to cancel.
     * activityCancelUpdate and cancelUpdate are functions which check if the customer has tickets to cancel and only if there are
     * enough tickets are the ticket numbers updated accordingly.
     *
     * @param activity the sorted ArrayList of activities.
     * @param customer the sorted ArrayList of customers
     * @param scan the scanner to read user input.
     * @return optionMenu function returns true or false. true will cause the function to run again as outlined in the start function.
     *         false will end the while loop in the start function ending the program.
     */
    public static boolean optionMenu(SortedArrayList<Activity> activity, SortedArrayList<Customer> customer, Scanner scan) {

        System.out.println("""
                    f - to finish running the program.
                    a - to display on the screen information about all the activities.
                    c - to display on the screen information about all the customers.
                    t - to update the stored data when tickets are bought by one of the registered customers.
                    r - to update the stored data when a registered customer cancels tickets for a booking.
                    """);

        switch (scan.next().charAt(0)) {
            case 'f':
                System.out.println("Exiting the program");
                return false;

            case 'a':
                System.out.println("The available activities and their ticket numbers are listed below.\n");
                System.out.println("--------------------------------");

                Activity.activityPrint(activity);
                break;

            case 'c':
                System.out.println("The current registered customers are listed below.\n");
                System.out.println("--------------------------------");

                Customer.customerPrint(customer);
                break;

            case 't':
                System.out.println("\nEnter the first name");
                String first = scan.next();
                System.out.println("\nEnter the last name");
                String last = scan.next();

                if (Customer.registered(customer, first, last)) {
                    System.out.println("Would you like to update this customers tickets?\n(Yes to update, any other key to return)\n");
                    String updateAnswer = scan.next().toLowerCase();
                    scan.nextLine();
                    if (!updateAnswer.equals("yes")) {
                        break;
                    } else {
                        System.out.println("Enter the Activity this customer wishes to buy tickets for\n");
                        String enteredActivity = scan.nextLine().toLowerCase();

                        if (Activity.activityExists(activity, enteredActivity)) {
                            System.out.println("To update this customers tickets enter the number of tickets they want to buy\n");
                            Integer ticketsBought = 0;

                            try {
                                ticketsBought = Integer.parseInt(scan.next());
                            } catch (NumberFormatException e) {
                                System.out.println("Enter a valid number only\n");
                                break;
                            }

                            try {
                                if (Activity.buyUpdate(activity, enteredActivity, ticketsBought, first)) {
                                    Customer.activityBuyUpdate(customer, ticketsBought, enteredActivity, first, last);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                break;

            case 'r':
                System.out.println("\nEnter the first name");
                String firstName = scan.next();
                System.out.println("\nEnter the last name");
                String lastName = scan.next();

                if (Customer.registered(customer, firstName, lastName)) {
                    System.out.println("Would you like to update this customers tickets?\n(Yes to update, any other key to return)\n");
                    String updateAnswer = scan.next().toLowerCase();
                    scan.nextLine();
                    if (!updateAnswer.equals("yes")) {
                        break;
                    } else {
                        if (Customer.registered(customer, firstName, lastName)) {
                            System.out.println("Enter the Activity this customer wants to cancel tickets for\n");
                            String enteredActivity = scan.nextLine().toLowerCase();

                            if (Activity.activityExists(activity, enteredActivity)) {
                                System.out.println("To update this customers tickets enter the number of tickets you wish to cancel\n");
                                Integer ticketsCancelled = 0;

                                try {
                                    ticketsCancelled = Integer.parseInt(scan.next());
                                } catch (NumberFormatException e) {
                                    System.out.println("Enter a valid number only\n");
                                    break;
                                }

                                if (Customer.activityCancelUpdate(customer, ticketsCancelled, enteredActivity, firstName, lastName)) {
                                    Activity.cancelUpdate(activity, enteredActivity, ticketsCancelled);
                                }
                            }
                        }
                    }
                }
                break;

            default:
                break;
        }
        return true;
    }
}

