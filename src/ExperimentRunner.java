import java.util.*;
import com.cs210x.*;

/**
  * Class to deduce the identity of mystery data structures.
  */

public class ExperimentRunner {
	private static final int NUM_DATA_STRUCTURES_TO_DEDUCE = 5;
	private static final int NUM_RECURRENCES = 100; // the number of repeated method calls to form average time

	// Main Method
	public static void main(String[] args) {
		final String cs210XTeamIDForProject4 = "vsinha2";
		final int[] Ns = {10, 20, 30, 40, 50, 60, 70, 80, 90,
				100, 200, 300, 400, 500, 600, 700, 800, 900,
				110, 210, 310, 410, 510, 610, 710, 810, 910,
				120, 220, 320, 420, 520, 620, 720, 820, 920,
				130, 230, 330, 430, 530, 630, 730, 830, 930,
				140, 240, 340, 440, 540, 640, 740, 840, 940,
				150, 250, 350, 450, 550, 650, 750, 850, 950,
				160, 260, 360, 460, 560, 660, 760, 860, 960,
				170, 270, 370, 470, 570, 670, 770, 870, 970,
				180, 280, 380, 480, 580, 680, 780, 880, 980,
				190, 290, 390, 490, 590, 690, 790, 890, 990, 1000,
				1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900,
				2110, 2210, 2310, 2410, 2510, 2610, 2710, 2810, 2910,
				3120, 3220, 3320, 3420, 3520, 3620, 3720, 3820, 3920,
				4130, 4230, 4330, 4430, 4530, 4630, 4730, 4830, 4930,
				5140, 5240, 5340, 5440, 5540, 5640, 5740, 5840, 5940,
				6150, 6250, 6350, 6450, 6550, 6650, 6750, 6850, 6950,
				7160, 7260, 7360, 7460, 7560, 7660, 7760, 7860, 7960,
				8170, 8270, 8370, 8470, 8570, 8670, 8770, 8870, 8970,
				9180, 9280, 9380, 9480, 9580, 9680, 9780, 9880, 9980,
				10000};

		// Fetch the collections whose type you must deduce.
		@SuppressWarnings("unchecked")
		final Collection210X<Integer>[] mysteryDataStructures =
				(Collection210X<Integer>[]) new Collection210X[NUM_DATA_STRUCTURES_TO_DEDUCE];
		
		// Prints out n values
		System.out.println("N: " + Arrays.toString(Ns) + "\n");
		
		// Prints out the time costs of all mystery data structures for all functions (add, remove, contains)
		for(int i = 0; i < NUM_DATA_STRUCTURES_TO_DEDUCE; i++) {
			mysteryDataStructures[i] = MysteryDataStructure.getMysteryDataStructure(
					cs210XTeamIDForProject4.hashCode(), i, new Integer(0));
			printValues(mysteryDataStructures[i], i, Ns);
		}
	}

	/**
	 * Prints out the average time cost of add, remove, and contains functions of the given data structure
	 * of the specified data structure using the given list of Ns
	 * @param dataStructure the mystery data structure
	 * @param num the number associated with the mystery data structure
	 * @param Ns the list of possible number of elements in the dataStructure
	 */
	private static void printValues(Collection210X<Integer> dataStructure, int num, int[] Ns) {
		// Get an array of times for each method acted on the data structure
		final long[] addTimes = getAverageTimeCost(dataStructure, "add", Ns);
		final long[] removeTimes = getAverageTimeCost(dataStructure, "remove", Ns);
		final long[] containsTimes = getAverageTimeCost(dataStructure, "contains", Ns);
		System.out.println("Data structure " + num);
		System.out.println("Add times: " + Arrays.toString(addTimes));
		System.out.println("Remove times: " + Arrays.toString(removeTimes));
		System.out.println("Contains times: " + Arrays.toString(containsTimes) + "\n");
	}
	
	/**
	 * Returns an array of the average time costs of the given method for all ints in instance variable Ns 
	 * for the given data structure
	 * @param dataStructure the mystery data structure
	 * @param method the method ("contains", "add", "remove") to test
	 * @return an array of the average times for the given method (contains(o), add(o), or remove(o))
	 * and given data structure
	 */
	private static long[] getAverageTimeCost(Collection210X<Integer> dataStructure, String method, int[] Ns) {
		final long[] averageTimes = new long[Ns.length];

		// loops through each N (number of data in dataStructure)
		for (int i = 0; i < Ns.length; i++) {
			long sum = 0;
			fillDataStructure(dataStructure, Ns[i]);
			// repeats calculation multiple times to get an average time
			for (int j = 0; j < NUM_RECURRENCES; j++) {
				final long start = CPUClock.getNumTicks();
				// performs the method from the given string
				doMethod(dataStructure, method, i, Ns);
				final long end = CPUClock.getNumTicks();
				sum += (end - start);
			}
			// takes the average
			averageTimes[i] = sum / NUM_RECURRENCES;
		}
		return averageTimes;
	}

	/**
	 * Fills each data structure to the given N
	 * @param dataStructure the mystery data structure
	 * @param N the number of elements in the mystery data structure
	 */
	private static void fillDataStructure(Collection210X<Integer> dataStructure, int N) {
		dataStructure.clear();
		for (int i = 0; i < N; i++) {
			dataStructure.add(new Integer(i));
		}
	}

	/**
	 * Performs the specified method with the given data structure with Ns[i] elements
	 * @param dataStructure the mystery data structure
	 * @param method the method ("contains", "add", "remove") to test
	 * @param i the element number in the mystery data structure
	 * @param Ns the list of possible number of elements in the mystery data structure
	 */
	private static void doMethod(Collection210X<Integer> dataStructure, String method, int i, int[] Ns) {
		final Random random = new Random();
		final int numToFind = random.nextInt(Ns[i]);
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
