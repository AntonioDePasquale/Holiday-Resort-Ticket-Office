import java.util.ArrayList;

/**
 * The sorted ArrayList class that implements the Comparable interface that takes a generic object type E.
 * by implementing comparable when the ArrayLists are created they will inherit the compareTo method.
 * The compareTo method has been overwritten in both the Activity and the Customer classes. The insert
 * method below uses compareTo to sort each class object as it is inserted into the arraylist by the conditions
 * outlined in the overwritten methods in the corresponding classes.
 *
 * The for loop below took elements from a solution found on stackoverflow, to avoid any problems it is cited below.
 * Akash Sharma, 2020, Stack Overflow, Available at: stackoverflow.com/questions/65071282/my-generic-sortedarraylist-class-wont-work
 *
 * @param <E> generic object type that in this case will be either an Activity or a Customer class.
 */
public class SortedArrayList <E extends Comparable<E>> extends ArrayList<E> {

    public void insert(E e) {
        this.add(e);
        int endIndex = 0;

        for (endIndex = this.size() - 1; endIndex > 0 && this.get(endIndex - 1).compareTo(e) > 0; endIndex--) {
            this.set(endIndex, this.get(endIndex - 1));
        }
        this.set(endIndex, e);
    }
}





