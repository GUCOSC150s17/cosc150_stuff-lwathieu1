package lbw29;


import static org.junit.Assert.*;


import org.junit.*;


public class TestStingySlot 
{

	StingySlot ss;
	int slotLength=5;
	int maxNumber = 60;
	
	
	//Gets run before every test
	@Before
	public void before()
	{
		ss = new StingySlot();
	}
	
	@Test
	public void testJackpot()
	{		
		int[] slot = {3,3,3,3,3};
		ss.setSpin(slot);
		assertEquals( "Testing Jackpots",1000000,ss.payoff(), .000001);
	}
	
	@Test
	public void test4Same()
	{		
		int[] slot = {3,3,3,3,2};
		ss.setSpin(slot);
		assertEquals( "Testing 4 of the same",10000,ss.payoff(), .000001);
	}
	
	@Test
	public void test3Same2Same()
	{		
		int[] slot = {3,2,3,3,2};
		ss.setSpin(slot);
		assertEquals( "Testing 3 of the same, 2 of the same",500,ss.payoff(), .000001);
	}
	
	@Test
	public void test3Same2Different()
	{		
		int[] slot = {3,3,5,2,3};
		ss.setSpin(slot);
		assertEquals( "Testing 3 of the same, 2 different",10,ss.payoff(), .000001);
	}
	
	@Test
	public void test2Same3Different()
	{		
		int[] slot = {3,3,6,5,2};
		ss.setSpin(slot);
		assertEquals( "Testing 2 of the same, 3 Different",2,ss.payoff(), .000001);
	}
	
	@Test
	public void test2Same2Same1Different()
	{		
		int[] slot = {3,6,3,2,6};
		ss.setSpin(slot);
		assertEquals( "Testing 2 pairs and 1 different",4,ss.payoff(), .000001);
	}
	
	@Test
	public void testPerfectSquare()
	{
		int[] slot = {3,6,5,1,2};
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 1",.1,ss.payoff(), .000001);
		slot[3]=4;
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 4",.1,ss.payoff(), .000001);
		slot[3]=9;
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 9",.1,ss.payoff(), .000001);
		slot[3]=16;
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 16",.1,ss.payoff(), .000001);
		slot[3]=25;
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 25",.1,ss.payoff(), .000001);
		slot[3]=36;
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 36",.1,ss.payoff(), .000001);
		slot[3]=49;
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 49",.1,ss.payoff(), .000001);
		
		slot[2] = 16;
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 49 and 16",.1*2,ss.payoff(), .000001);
		
		slot[4] = 16;
		ss.setSpin(slot);
		assertEquals( "Testing perfect square: 49 and 16 twice",.1*3+2,ss.payoff(), .000001);
	}
	
	@Test
	public void test42Occurence()
	{		
		int[] slot = {3,6,5,42,2};
		ss.setSpin(slot);
		assertEquals( "Testing for one 42",.35,ss.payoff(), .000001);
		slot[2] = 42;
		slot[0] = 42;
		ss.setSpin(slot);
		assertEquals( "Testing for triple 42",.35*3+10,ss.payoff(), .000001);
	}
	
	@Test
	public void test17Divisible()
	{		
		int[] slot = {3,6,5,17,2};
		ss.setSpin(slot);
		assertEquals( "Testing for divisibility by 17",.17,ss.payoff(), .000001);
		
		slot[3] = 34;
		ss.setSpin(slot);
		assertEquals( "Testing for divisibility by 17",.17,ss.payoff(), .000001);
		
		slot[3] = 51;
		ss.setSpin(slot);
		assertEquals( "Testing for divisibility by 17",.17,ss.payoff(), .000001);
		
		slot[0] = 17;
		ss.setSpin(slot);
		assertEquals( "Testing for divisibility twice by 17",.17*2,ss.payoff(), .000001);
	}
	
	@Test
	public void testSlotProfitable()
	{
		int numTimesToRun = 1000000;
		double totalPayoff = 0;
		System.out.println("Running the slot " + numTimesToRun + " times....");
		for (int i = 0; i < numTimesToRun ;i++)
		{
			ss.doSpin();
			totalPayoff += ss.payoff();
		}
		double averagePayoff = totalPayoff/numTimesToRun;
		System.out.println("Average Payout is " +averagePayoff);
		assertTrue( "This is a profitable slot!  ", averagePayoff < 2 );
		
	}
	
	@Test
	public void ultimateTester()
	{
		int numTimesToRun = 1000000;
		
		System.out.println("Running the Ultimate Tester " + numTimesToRun + " times....");
		for (int i = 0; i < numTimesToRun ;i++)
		{
			ss.doSpin();
			assertEquals( "Testing for payoff Equality",myPayoff(ss.getSpin()),ss.payoff(), .000001);
		}
		
	}
	
	
	
	private int countOccurrence(final int[] arr, int n)
	{
		int count = 0;
		for (int i = 0; i < slotLength; i++)
			if (arr[i] == n)
				count++;
		return count;
	}
	
	//takes slot arr and returns an integer key that represents the following cases:
	//returns 0 if there are no repeats
	//returns 1 if all 5 numbers are the same
	//returns 2 if 4 of the numbers is the same
	//returns 3 if 3 numbers are the same and 2 are the same
	//returns 4 if 3 numbers are the same and the others are differents
	//returns 5 if only one pair of numbers are the same
	//returns 6 if two pairs of numbers are the same
	private int repeatKey(final int[] arr)
	{
		int [] multiples = {0,0,0,0,0};
		for (int i = 0; i < slotLength; i++)
		{
			for(int n = 0; n < slotLength; n++)
				if (arr[i] == arr[n])
					multiples[i] ++;
		}
		
		int key = 0;
		int twoAlready = 0;
		boolean threeAlready = false;
		for (int p = 0; p < slotLength; p++)
		{
			if(multiples[p] == 5)
				key = 1;
			if(multiples[p] == 4)
				key = 2;
			if(multiples[p] == 2)
				twoAlready++;
			if(multiples[p] == 3)
				threeAlready = true;
		}
		if (threeAlready)
		{
			if (twoAlready>0)
				key = 3;
			else
				key = 4;
		}
		else if (twoAlready == 2)
			key = 5;
		else if (twoAlready == 4)
			key = 6;
		return key;
	}
	
	public double myPayoff(int[] spin)
	{
		double payoff = 0;
		
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
		
		payoff += countOccurrence(spin,1)*.1;
		payoff += countOccurrence(spin,4)*.1;
		payoff += countOccurrence(spin,9)*.1;
		payoff += countOccurrence(spin,16)*.1;
		payoff += countOccurrence(spin,25)*.1;
		payoff += countOccurrence(spin,36)*.1;
		payoff += countOccurrence(spin,49)*.1;
		
		payoff += countOccurrence(spin,42)*.35;
		
		payoff += countOccurrence(spin,17)*.17;
		payoff += countOccurrence(spin,34)*.17;
		payoff += countOccurrence(spin,51)*.17;
		
		return payoff;
	}
	
	
}
