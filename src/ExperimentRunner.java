import java.util.*;
import com.cs210x.*;

/**
  * Class to deduce the identity of mystery data structures.
  */
public class ExperimentRunner {
	private static final int NUM_DATA_STRUCTURES_TO_DEDUCE = 5;

	public static void main (String[] args) {
		final String cs210XTeamIDForProject4 = "vsinha2";

		final int[] Ns = { 1, 2, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200,
				300, 400, 500, 600, 700, 800, 900, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };

		// Fetch the collections whose type you must deduce.
		// Note -- you are free to change the type parameter from Integer to whatever you want. In this
		// case, make sure to replace (over the next 4 lines of code) Integer with whatever class you prefer.
		// In addition, you'll need to pass the method getMysteryDataStructure a "sample" (an instance) of 
		// the class you want the collection to store.
		@SuppressWarnings("unchecked")
		final Collection210X<Integer>[] mysteryDataStructures =
				(Collection210X<Integer>[]) new Collection210X[NUM_DATA_STRUCTURES_TO_DEDUCE];
		for (int i = 0; i < NUM_DATA_STRUCTURES_TO_DEDUCE; i++) {
			mysteryDataStructures[i] = MysteryDataStructure.getMysteryDataStructure(cs210XTeamIDForProject4.hashCode(),
					i, new Integer(0));
		}

		// Write your code here...
		final int N = 100;
		for (int j = 0; j < NUM_DATA_STRUCTURES_TO_DEDUCE; j++) {  // populate the mystery data structure with 100 numbers
			for (int i = 0; i < N; i++) {
				mysteryDataStructures[j].add(new Integer(i));
			}
		}

		// for the first data structure
		long[] addTimes = getAverageTimeCost(mysteryDataStructures[0], "add", Ns);
		long[] removeTimes = getAverageTimeCost(mysteryDataStructures[0], "remove", Ns);
		long[] containsTimes = getAverageTimeCost(mysteryDataStructures[0], "contains", Ns);


		// Write a table of numbers (for different N -- here, we are just showing one value for simplicity) showing
		// the relationship between N and the time-cost associated with searching (with the contains method) through
		// a collection of N data.
		System.out.println("N\tT (contains(o))");
		System.out.println(Arrays.toString(addTimes));
		System.out.println(Arrays.toString(removeTimes));
		System.out.println(Arrays.toString(containsTimes));
	}

	/**
	 * Returns an array of the average time costs for all ints in instance variable Ns for the given data structure
	 * @param dataStructure The mystery data structure
	 * @param method The method ("contains", "add", "remove") to test
	 * @return An array of the average times for the given method (contains(o), add(o), or remove(o0and given data structure
	 */
	private static long[] getAverageTimeCost(Collection210X<Integer> dataStructure, String method, int[] Ns) {
		final int numRecurrences = 5;
		final long[] averageTimes = new long[Ns.length];

		for (int i = 0; i < Ns.length; i++) {
			long sum = 0;
			for (int j = 0; j < numRecurrences; j++) {
				final long start = CPUClock.getNumTicks();
				doMethod(dataStructure, method, i, Ns);
				final long end = CPUClock.getNumTicks();
				sum += (end - start);
			}
			averageTimes[i] = sum / numRecurrences;
		}
		return averageTimes;
	}

	private static void doMethod(Collection210X<Integer> dataStructure, String method, int i, int[] Ns) {
		if (method.equals("contains")) {
			final boolean result = dataStructure.contains(Ns[i]);
		} else if (method.equals("remove")) {
			dataStructure.remove(Ns[i]);
		} else if (method.equals("add")) {
			dataStructure.add(Ns[i]);
		} else {
			throw new Error("Not a correct method");
		}
	}

}