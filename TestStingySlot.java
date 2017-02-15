package lbw29;


import static org.junit.Assert.*;


import org.junit.*;


/**
 * Tests a stingy slot. Needs to be in same folder as a StingySlot class.
 * @author Lancelot Wathieu
 *
 */
public class TestStingySlot 
{

	StingySlot ss;
	int slotLength = 5;
	int maxNumber = 60;
	
	
	//Gets run before every test, re-initializes ss (a reset)
	@Before
	public void before()
	{
		ss = new StingySlot();
	}
	
	//Tests if the StingySlot returns correct payoff for 5 numbers of a kind
	@Test
	public void testJackpot()
	{		
		int[] slot = {3,3,3,3,3};
		ss.setSpin(slot);
		assertEquals( "Jackpot failed, incorrect payoff for: "+spinString(slot),1000000,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot returns correct payoff for 4 numbers of a kind
	@Test
	public void test4Same()
	{		
		int[] slot = {3,3,3,3,2};
		ss.setSpin(slot);
		assertEquals( "4 of a kind failed, incorrect payoff for: "+spinString(slot),10000,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot returns correct payoff for Full house (3 same, 2 same)
	@Test
	public void test3Same2Same()
	{		
		int[] slot = {3,2,3,3,2};
		ss.setSpin(slot);
		assertEquals( "Full house test failed, incorrect payoff for: "+spinString(slot),500,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot returns correct payoff for 3 same, 2 different
	@Test
	public void test3Same2Different()
	{		
		int[] slot = {3,3,5,2,3};
		ss.setSpin(slot);
		assertEquals( "3 of the same test failed, incorrect payoff for: "+spinString(slot),10,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot returns correct payoff for one pair, 3 different
	@Test
	public void test2Same3Different()
	{		
		int[] slot = {3,3,6,5,2};
		ss.setSpin(slot);
		assertEquals( "Single pair test failed, incorrect payoff for: "+spinString(slot),2,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot returns correct payoff for two pairs
	@Test
	public void test2Same2Same1Different()
	{		
		int[] slot = {3,6,3,2,6};
		ss.setSpin(slot);
		assertEquals( "Double pair test failed, incorrect payoff for: "+spinString(slot),4,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot returns correct payoff for occurrences of perfect square
	@Test
	public void testPerfectSquare()
	{
		int[] slot = {3,6,5,1,2};
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 1",.1,ss.payoff(), .000001);
		slot[3]=4;
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 4",.1,ss.payoff(), .000001);
		slot[3]=9;
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 9",.1,ss.payoff(), .000001);
		slot[3]=16;
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 16",.1,ss.payoff(), .000001);
		slot[3]=25;
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 25",.1,ss.payoff(), .000001);
		slot[3]=36;
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 36",.1,ss.payoff(), .000001);
		slot[3]=49;
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 49",.1,ss.payoff(), .000001);
		
		slot[2] = 16;
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 49 and 16",.1*2,ss.payoff(), .000001);
		
		slot[4] = 16;
		ss.setSpin(slot);
		assertEquals( "Failed perfect square: 49 once and 16 twice",.1*3+2,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot returns correct payoff for Occurrences of 42
	@Test
	public void test42Occurence()
	{		
		int[] slot = {3,6,5,42,2};
		ss.setSpin(slot);
		assertEquals( "Single 42 test failed, incorrect payoff for: "+spinString(slot),.35,ss.payoff(), .000001);
		slot[2] = 42;
		slot[0] = 42;
		ss.setSpin(slot);
		assertEquals( "Triple 42 test failed, incorrect payoff for: "+spinString(slot),.35*3+10,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot returns correct payoff for entries that are divisible by 17
	@Test
	public void test17Divisible()
	{		
		int[] slot = {3,6,5,17,2};
		ss.setSpin(slot);
		assertEquals( "Test failed for divisibility by 17: 17 occurred",.17,ss.payoff(), .000001);
		
		slot[3] = 34;
		ss.setSpin(slot);
		assertEquals( "Test failed for divisibility by 17: 34 occurred",.17,ss.payoff(), .000001);
		
		slot[3] = 51;
		ss.setSpin(slot);
		assertEquals( "Test failed for divisibility by 17: 51 occurred",.17,ss.payoff(), .000001);
		
		slot[0] = 17;
		ss.setSpin(slot);
		assertEquals( "Test failed for divisibility twice by 17",.17*2,ss.payoff(), .000001);
	}
	
	//Tests if the StingySlot is profitable. Runs slot randomly one million times and makes sure average payoff < 2
	@Test
	public void testSlotProfitable()
	{
		int numTimesToRun = 1000000;
		double totalPayoff = 0;
		System.out.println("Running the slot " + numTimesToRun + " times....");
		String error = "Slot is not profitable, average payoff: ";
		
		for (int i = 0; i < numTimesToRun ;i++)
		{
			ss.doSpin();
			totalPayoff += ss.payoff();
		}
		double averagePayoff = totalPayoff/numTimesToRun;
		
		System.out.println("Average Payout is " +averagePayoff);
		
		assertTrue(error+averagePayoff, averagePayoff < 2 );
	}
	
	//Tests StringySlot payoff() against a working payoff calculator, myPayoff(int[] arr). Compares 1000000 random slots
	@Test
	public void ultimateTester()
	{
		int numTimesToRun = 1000000;
		
		System.out.println("Running the Ultimate Tester " + numTimesToRun + " times....");
		String error = "Ultimate Tester failed for: ";
		
		for (int i = 0; i < numTimesToRun ;i++)
		{
			ss.doSpin();
			double myPayoff = myPayoff(ss.getSpin());
			
			if (!(myPayoff < ss.payoff()+.000001 && myPayoff > ss.payoff()-.000001 ))
			{
				//records first occurrence of ss.payoff that does not math myPayoff
				error += spinString(ss.getSpin());
				System.out.println(error);
			}
			
			assertEquals( error,myPayoff,ss.payoff(), .000001);
		}
		
	}
	
	//Calculates correct Payoff for array of slot entries. returns double
	public double myPayoff(final int[] spin)
	{
		double payoff = 0;
		
		//check if there are repeats, award payoff according to the repeatKey (see repeatKey(int[]) function).
		int repeatKey = repeatKey(spin);
		if (repeatKey == 1)
			payoff += 1000000;
		else if (repeatKey == 2)
			payoff += 10000;
		else if (repeatKey == 3)
			payoff += 500;
		else if (repeatKey == 4)
			payoff += 10;
		else if (repeatKey == 5)
			payoff += 2;
		else if (repeatKey == 6)
			payoff += 4;
		
		//award payoff based on occurrence of squares
		payoff += countOccurrence(spin,1)*.1;
		payoff += countOccurrence(spin,4)*.1;
		payoff += countOccurrence(spin,9)*.1;
		payoff += countOccurrence(spin,16)*.1;
		payoff += countOccurrence(spin,25)*.1;
		payoff += countOccurrence(spin,36)*.1;
		payoff += countOccurrence(spin,49)*.1;
		
		//award payoff based on occurrence of 42
		payoff += countOccurrence(spin,42)*.35;
		
		//award payoff based on divisibility by 17
		payoff += countOccurrence(spin,17)*.17;
		payoff += countOccurrence(spin,34)*.17;
		payoff += countOccurrence(spin,51)*.17;
		
		return payoff;
	}
	
	//returns the number of occurrences of "n" in slot
	private int countOccurrence(final int[] slot, int n)
	{
		int count = 0;
		for (int i = 0; i < slotLength; i++)
			if (slot[i] == n)
				count++;
		return count;
	}
	
	//takes 5-length array "slot" and returns an integer key that represents the following cases:
	//returns 0 if there are no repeats
	//returns 1 if all 5 numbers are the same
	//returns 2 if 4 of the numbers is the same
	//returns 3 if 3 numbers are the same and 2 are the same
	//returns 4 if 3 numbers are the same and the others are different
	//returns 5 if only one pair of numbers are the same
	//returns 6 if two pairs of numbers are the same
	private int repeatKey(final int[] slot)
	{
		//array that will hold occurrence of that position's slot value. 
		//Example: for slot = [ 7,7,3,7,3], multiples = [3,3,2,3,2]
		int [] multiples = {0,0,0,0,0};
		for (int i = 0; i < slotLength; i++)
		{
			for(int n = 0; n < slotLength; n++)
				if (slot[i] == slot[n])
					multiples[i] ++;
		}
		
		//decipher what case corresponds to the "multiples" values
		int key = 0;
		int twoAlready = 0;
		boolean threeAlready = false;
		for (int p = 0; p < slotLength; p++)
		{
			//means 5 numbers are same
			if(multiples[p] == 5)
				key = 1;
			//means 4 numbers are same
			if(multiples[p] == 4)
				key = 2;
			//means there is at least one pair - record number of times 2 occurs
			if(multiples[p] == 2)
				twoAlready++;
			//means there is three of a kind
			if(multiples[p] == 3)
				threeAlready = true;
		}
		//test if it is a full house or simply 3 same 2 different
		if (threeAlready)
		{
			if (twoAlready>0)
				key = 3;
			else
				key = 4;
		}
		//tests if there is exactly one pair, all other different
		else if (twoAlready == 2)
			key = 5;
		//tests if there are exactly 2 pairs, all other different
		else if (twoAlready == 4)
			key = 6;
		
		return key;
	}
	
	//Internal function that returns a string printout of a length 5 array (used to print slots)
	private String spinString(final int[] slot)
	{
		String spin = "[";
		for (int i = 0; i < slotLength; i++)
			spin = spin + slot[i] + " ";
		spin += "]";
		return spin;
	}
	
	
}
