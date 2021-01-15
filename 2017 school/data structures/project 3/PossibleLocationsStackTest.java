package project3;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class contains JUnit tests for the PossibleLocationsStack
 * @author tracydong
 * @version 11/05/2017
 */

public class PossibleLocationsStackTest {

//tests for isEmpty
	@Test
	public void testisEmpty() {
		PossibleLocationsStack stackSample = new PossibleLocationsStack();  
		try {
			assertTrue("stackSample is empty",stackSample.isEmpty());
		} 
		catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testisEmptyAfterAdd() {
		try {
			PossibleLocationsStack stackSample = new PossibleLocationsStack();
			Location s = new Location(1,2);
			stackSample.add(s);
			assertFalse("not empty after adding an element",stackSample.isEmpty());
		} 
		catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testisEmptyAfterRemove() {
		try {	
			PossibleLocationsStack stackSample = new PossibleLocationsStack();
			Location s = new Location(1,2);
			stackSample.add(s);
			stackSample.remove();
			assertTrue("empty after removing",stackSample.isEmpty());
		} 
		catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

//tests for add	
	@Test
	public void testAddSize() {
		try {
			PossibleLocationsStack stackSample = new PossibleLocationsStack();
			Location s = new Location(1,2);
			stackSample.add(s);
			assertEquals("size is one when adding",stackSample.getSize(),1);
		}
		catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testAddHead() {
		PossibleLocationsStack stackSample = new PossibleLocationsStack();
		Location s = new Location(1,2);
		try {
			stackSample.add(s);
			assertEquals("head is newly added element",stackSample.getHead().getElement(),s);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testAddNull() {
		PossibleLocationsStack stackSample = new PossibleLocationsStack();
		Location s = new Location(1,2);
		Location a = null;
		try {
			stackSample.add(s);
			stackSample.add(a);
			assertEquals("adding null does nothing",stackSample.getSize(),1);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

//tests for remove
	@Test
	public void testRemove() {
		PossibleLocationsStack stackSample = new PossibleLocationsStack();
		for (int i =3; i<10; i++ ) {
			for (int j = 5; j<12; j++) {
				Location s = new Location(i,j);
				stackSample.add(s);
			}
		}		
		Location temp = stackSample.getHead().getElement();
		try {
			assertEquals("front is removed",temp,stackSample.remove());
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testRemoveSize0() {
		PossibleLocationsStack stackSample = new PossibleLocationsStack();
		Location s = new Location(1,2);
		stackSample.add(s);
		try {
			stackSample.remove();
			assertEquals("Removing produces size 0 for one Node stack",stackSample.getSize(),0);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testRemoveSize() {
		PossibleLocationsStack stackSample = new PossibleLocationsStack();
		Location s = new Location(1,2);
		stackSample.add(s);
		Location a = new Location(4,6);
		stackSample.add(a);
		try {
			stackSample.remove();
			assertEquals("removing produces the correct size",stackSample.getSize(),1);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testRemoveNull() {
		PossibleLocationsStack stackSample = new PossibleLocationsStack();
		try {
			assertNull("No node stack returns null",stackSample.remove());
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testRemoveHead() {
		PossibleLocationsStack stackSample = new PossibleLocationsStack();
		Location s = new Location(1,2);
		stackSample.add(s);
		Location temp = stackSample.getHead().getElement();
		try {
			assertEquals ("next node becomes head after remove",temp, stackSample.remove());
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

/** don't test getters	
//tests for getters
	@Test
	public void getSize() {
		try {
			PossibleLocationsStack stackSample = new PossibleLocationsStack();
			Location s = new Location(1,2);
			stackSample.add(s);
			assertEquals("gets size for 1 Node stack",stackSample.getSize(),1);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void getHead() {
		try {
			PossibleLocationsStack stackSample = new PossibleLocationsStack();
			Location s = new Location(1,2);
			stackSample.add(s);
			assertEquals("gets head",stackSample.getHead().getElement(),s);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}
*/	
}
