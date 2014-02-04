package GUI;

public class TimeConversion {
	
	/*
	 * For 100 iterations, print to console
	 * "Fizz" if divisible by 2
	 * "Buzz" if divisible by 5
	 * "FizzBuzz" if divisible by 10
	 */
	
	public static void main(String[] args)	{
		
		int a = 15;
		int b = 24;
		int c = 5;
		int d = 16;
		
		int smallest = -1;
		
//		int[] original = new int[] {15, 24, 5, 16};
		int[] set = new int[]{15, 24, 5, 16};
		
		for (int i = 0; i < set.length; i++)	{
			if (set[i] < smallest || smallest == -1)	{
				smallest = set[i];
			}
		}

		System.out.println("The smallest number is: " + smallest);
		
	}

}
