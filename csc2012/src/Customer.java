import java.io.FileWriter;
import java.io.IOException;

/**
 * The Customer class which contains the customer information such as first and last name as String variables.
 * Also contains a SortedArrayList of Activities to store maximum 3 activities per customer.
 * Customer constructor, takes two parameters, one for the first name and one for the second name when a new customer is made.
 */
public class Customer implements Comparable<Customer> {
    private String firstName;
    private String lastName;
    private SortedArrayList<Activity> customerActivities = new SortedArrayList<>();

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getters and setters for the Customer class.
     */

    /**
     * GetFirstName() returns the first name variable of the customer.
     * @return firstName String variable.
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * getLastName() returns the lastName variable of the customer.
     * @return lastName String variable.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * getCustomersActivityList() returns the SortedArrayList of customer activities.
     * @return customerActivities SortedArrayList of type Activity.
     */
    public SortedArrayList<Activity> getCustomersActivityList() {
        return customerActivities;
    }

    /**
     * SetFirstName() takes a parameter which is then set to the firstName variable of the Customer.
     * @param firstName firstName String variable.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * SetLastName() takes a parameter which is then set to the lastName variable of the Customer.
     * @param lastName lastName String variable.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /* Boolean method to return true if both customers have the same name and false otherwise.
    * takes a parameter to differentiate from the default equals().
    * currently not in use but may be useful if program is expanded. */

    public boolean equals(Customer otherCustomer) {
        return (firstName.equals(otherCustomer.firstName)
                && lastName.equals(otherCustomer.lastName));
    }

    /**
     * Method to replace toString() in the customer class, takes the first name and the last name adding a space
     * to return a concatenated string of the full name */
    public String toString()
    {
        return firstName + " " + lastName;
    }

    /**
     * Function that takes a Customer ArrayList and using a for loop iterates through all customer objects.
     * for each customer the toString() is called to concatenate the names and then the name is printed to the console.
     * @param customer SortedArrayList of type Customer containing all the customers read from input.txt.
     */
    public static void customerPrint(SortedArrayList<Customer> customer) {
        for (int i = 0; i < customer.size(); i++) {
            String fullName = customer.get(i).toString();
            System.out.println(fullName);
        }
        System.out.println("--------------------------------\n");
    }

    /**
     * Function that iterates through all customers in the Customer ArrayList and checks to see if the names match
     * the user input names. If the names match the function returns true and a registered message is printed.
     *
     * @param customerArray SortedArrayList of type Customer containing all the customers read from input.txt.
     * @param first String input from user for the customers first name.
     * @param last String input from user for the customers last name.
     * @return returns true if the customer is registered in the Customer ArrayList and false if they are not.
     */
    public static Boolean registered(SortedArrayList<Customer> customerArray, String first, String last) {

        boolean regInfo = false;

        for (int i = 0; i < customerArray.size(); i++) {
            if (customerArray.get(i).getFirstName().equalsIgnoreCase(first)
                    && customerArray.get(i).getLastName().equalsIgnoreCase(last)) {
                regInfo = true;
                break;
            }
        }
        if (regInfo) {
            System.out.println("\nThe name entered is a registered customer\n");
            return true;
        } else {
            System.out.println("\nThe name entered is not a registered customer\n");
            return false;
        }
    }

    /**
     * Function to add the activities to the ArrayList of customer activities. The activity name input that is entered
     * when tickets are bought is checked to see if it is already in the ArrayList. If it is not the ArrayList is checked
     * to see if the Arraylist has less than 3 activities. If both of these conditions are true the Activity constructor
     * makes a new Activity and adds it to the ArrayList of customer activities. After each potential result the activity
     * list for that customer is printed to the screen along with an appropriate message.
     *
     * @param customer SortedArrayList of type Customer containing all the customers read from input.txt.
     * @param ticketsBought Integer user input for the number of tickets bought.
     * @param activityName String input from user for the name of the activity tickets are bought for.
     * @param firstName String user input for the customers first name.
     * @param lastName String user input for the customers Last name.
     */
    public static void activityBuyUpdate(SortedArrayList<Customer> customer, Integer ticketsBought, String activityName, String firstName, String lastName) {

        for (int i = 0; i < customer.size(); i++) {
            SortedArrayList<Activity> activityList = customer.get(i).getCustomersActivityList();
            Activity tempActivity = new Activity(activityName, ticketsBought);

            if (customer.get(i).getFirstName().equalsIgnoreCase(firstName)
            && customer.get(i).getLastName().equalsIgnoreCase(lastName)) {


                if (activityList.size() == 0) {
                    activityList.insert(tempActivity);
                    System.out.println("The activity " + activityName + " has now been registered with customer " + firstName + " " + lastName);
                    System.out.println("--------------------------------");
                    Activity.activityPrint(activityList);
                    break;
                }
                if ((activityList.size() >=1) && (activityList.size() < 4)) {
                    for (int j = 0; j < activityList.size(); j++) {
                        if (activityList.get(j).getActivityName().equals(activityName)) {
                            System.out.println("That activity is already registered with customer " + firstName + " " + lastName);
                            Integer oldTicketNo = activityList.get(j).getTicketNo();
                            Integer newTicketNo = activityList.get(j).getTicketNo() + ticketsBought;
                            activityList.get(j).setTickets(newTicketNo);
                            System.out.println("The ticketsBought count has been updated to " + newTicketNo + " from " + oldTicketNo);
                            System.out.println("\n--------------------------------");
                            Activity.activityPrint(activityList);
                            break;
                        } else if (activityList.size() < 3 && !(activityList.get(j).getActivityName().equals(activityName))) {
                            activityList.insert(tempActivity);
                            System.out.println("The activity " + activityName + " has now with customer " + firstName + " " + lastName);
                            System.out.println("\n--------------------------------");
                            Activity.activityPrint(activityList);
                            break;
                        } else {
                            System.out.println("This customers activity list was unable to be updated and may be full");
                            System.out.println("The list of registered activities for this customer is shown below");
                            System.out.println("\n--------------------------------");
                            Activity.activityPrint(activityList);
                        }
                    }
                }
            }
        }
    }

    /**
     * Function to update the ticket number for the inputted activity name. First the activity name is checked against
     * the ArrayList of customer activities to see if it is a registered activity. If the tickets cancelled number is larger
     * than the current number of tickets the customer has nothing will change and a letter will be printed to the
     * letters.txt file to explain the reason. If the tickets for that customer activity are reduced to 0 then the activity
     * is removed from the list. If the tickets cancelled < than the customers current number of tickets then the number is
     * simply updated.
     *
     * @param customer SortedArrayList of type Customer containing all the customers read from input.txt.
     * @param ticketsCancelled Integer number of tickets that were cancelled.
     * @param activityName String containing the name of the activity to be updated.
     * @param firstName String variable for the first name of the customer.
     * @param lastName String variable for the last name of the customer
     * @return return true if tickets were successfully cancelled, false otherwise
     */
    public static Boolean activityCancelUpdate(SortedArrayList<Customer> customer, Integer ticketsCancelled, String activityName, String firstName, String lastName) {

        Boolean cancelStatus = false;

        for (int i = 0; i < customer.size(); i++) {
            SortedArrayList<Activity> activityList = customer.get(i).getCustomersActivityList();

            if (customer.get(i).getFirstName().equalsIgnoreCase(firstName)
                    && customer.get(i).getLastName().equalsIgnoreCase(lastName)) {

                if (activityList.size() == 0) {
                    System.out.println("\nThis customer has no registered activities");
                    break;
                }
                else if (activityList.size() < 4) {
                    for (int j = 0; j < activityList.size(); j++) {

                        if (activityList.get(j).getActivityName().equals(activityName)) {
                            System.out.println("\n" + activityName + " is registered with customer " + firstName + " " + lastName);
                            Integer oldTicketNo = activityList.get(j).getTicketNo();
                            Integer newTicketNo = activityList.get(j).getTicketNo() - ticketsCancelled;

                            if (newTicketNo > 0) {
                                activityList.get(j).setTickets(newTicketNo);
                                System.out.println(ticketsCancelled + " tickets have been cancelled successfully. This customer now has " + newTicketNo + " tickets");
                                System.out.println("--------------------------------");
                                Activity.activityPrint(activityList);
                                cancelStatus = true;

                            } else if (newTicketNo == 0) {
                                activityList.remove(j);
                                System.out.println(ticketsCancelled + " tickets have been cancelled successfully");
                                System.out.println("There are no remaining tickets for " + activityName + " so the activity has been removed");
                                System.out.println("--------------------------------");
                                Activity.activityPrint(activityList);
                                cancelStatus = true;

                            } else {
                                System.out.println("\nThis customer only has " + oldTicketNo + " tickets and cannot cancel " + ticketsCancelled + " tickets");
                                System.out.println("\n--------------------------------");
                                break;
                            }
                        }
                    }
                }
            }
        }
        return cancelStatus;
    }

    /**
     * method that overrides the compareTo method in the Comparable interface to compare customer last names
     * */
    @Override
    public int compareTo(Customer customer) {
        if (this.getLastName().compareTo(customer.getLastName()) == 0) {
            return this.getFirstName().compareTo(customer.getFirstName());
        } else {
            return this.getLastName().compareTo(customer.getLastName());
        }
    }
}
