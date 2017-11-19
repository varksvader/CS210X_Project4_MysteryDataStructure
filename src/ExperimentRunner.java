import java.util.*;
import com.cs210x.*;

/**
  * Class to deduce the identity of mystery data structures.
  */
public class ExperimentRunner {
	private static final int NUM_DATA_STRUCTURES_TO_DEDUCE = 5;
	private static final int NUM_RECURRENCES = 100; // the number of repeated method calls to form average time

	public static void main (String[] args) {
		final String cs210XTeamIDForProject4 = "vsinha2";

		final int[] Ns = { 1, 2, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600, 700, 800, 900, 
				1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };

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
			printValues(mysteryDataStructures[i], i, Ns);
		}
	}

	/**
	 * Prints our the average time cost of add, remove, and contains functions
	 * @param dataStructure
	 * @param num
	 * @param Ns
	 */
	private static void printValues(Collection210X<Integer> dataStructure, int num, int[] Ns) {
		long[] addTimes = getAverageTimeCost(dataStructure, "add", Ns);
		long[] removeTimes = getAverageTimeCost(dataStructure, "remove", Ns);
		long[] containsTimes = getAverageTimeCost(dataStructure, "contains", Ns);
		System.out.println("Data structure " + num);
		System.out.println("N: " + Arrays.toString(Ns));
		System.out.println("Add times: " + Arrays.toString(addTimes));
		System.out.println("Remove times: " + Arrays.toString(removeTimes));
		System.out.println("Contains times: " + Arrays.toString(containsTimes) + "\n");
	}
	
	/**
	 * Returns an array of the average time costs for all ints in instance variable Ns for the given data structure
	 * @param dataStructure The mystery data structure
	 * @param method The method ("contains", "add", "remove") to test
	 * @return An array of the average times for the given method (contains(o), add(o), or remove(o0and given data structure
	 */
	private static long[] getAverageTimeCost(Collection210X<Integer> dataStructure, String method, int[] Ns) {
		final long[] averageTimes = new long[Ns.length];

		// loops through each N (number of data in dataStructure)
		for (int i = 0; i < Ns.length; i++) {
			long sum = 0;
			fillDataStructue(dataStructure, Ns[i]);
			// repeats calculation multiple times to get an average time
			for (int j = 0; j < NUM_RECURRENCES; j++) {
				final long start = CPUClock.getNumTicks();
				doMethod(dataStructure, method, i, Ns);
				final long end = CPUClock.getNumTicks();
				sum += (end - start);
			}
			averageTimes[i] = sum / NUM_RECURRENCES;
		}
		return averageTimes;
	}

	//Modify so we know what the max/min is, so we can test for heap
	/**
	 * Fills each data structure to the specified N
	 * @param dataStructure
	 * @param N
	 */
	private static void fillDataStructue(Collection210X<Integer> dataStructure, int N) {
		dataStructure.clear();
		for (int i = 0; i < N; i++) {
			dataStructure.add(new Integer(i));
		}
	}

	// Some time cost can be contributed to the if structure
	/**
	 * 
	 * @param dataStructure
	 * @param method
	 * @param i
	 * @param Ns
	 */
	private static void doMethod(Collection210X<Integer> dataStructure, String method, int i, int[] Ns) {
		Random random = new Random();
		int numToFind = random.nextInt(Ns[i]);
		if (method.equals("contains")) {
			dataStructure.contains(numToFind);
		} else if (method.equals("remove")) {
			dataStructure.remove(numToFind);
		} else if (method.equals("add")) {
			dataStructure.add(numToFind);
		} else {
			throw new Error("Not a correct method");
		}
	}

}
