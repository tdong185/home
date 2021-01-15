package project3;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class contains JUnit tests for the PossibleLocationsQueue
 * @author tracydong
 * @version 11/05/2017
 */

public class PossibleLocationsQueueTest {

	//tests for isEmpty 	
	@Test
	public void testisEmpty() {
		PossibleLocationsQueue queueSample = new PossibleLocationsQueue();  
		try {
			assertTrue("test is empty", queueSample.isEmpty());
		} 
		catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testisEmptyAfterAdd() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
			Location s = new Location(1,2);
			queueSample.add(s);
			assertFalse("not empty after add",queueSample.isEmpty());
		} 
		catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testisEmptyAfterRemoveSizeOne() {
		try {	
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
			Location s = new Location(1,2);
			queueSample.add(s);
			queueSample.remove();
			assertTrue("one element list is empty after remove",queueSample.isEmpty());
		} 
		catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testisEmptyAfterRemoveSizeMoreOne() {
		try {	
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
			Location s = new Location(1,2);
			Location a = new Location(1,3);
			queueSample.add(s);
			queueSample.add(a);
			queueSample.remove();
			assertFalse("two element list is not empty after remove",queueSample.isEmpty());
		} 
		catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	//tests for defult constructor	
	@Test
	public void testConstructorDefault() {
		PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
		assertEquals("queue created with default constructor has capacity of 10",queueSample.getCapacity(),10);
	}

	//tests for one parameter constructor
	@Test
	public void testConstructorOneParam() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue(15);
			assertEquals("queue created with constructor with parameter created with equivalent capacity",queueSample.getCapacity(),15);
		}catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testConstructorOneParamNegative() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue(-5);
			assertEquals("queue created with constructor with negative parameter"
					+ "input is given default capacity of 10",queueSample.getCapacity(),10);
		}catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testConstructorOneParamZero() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue(0);
			assertEquals("queue created with constructor with parameter zero"
					+ "input is given default capacity of 10",queueSample.getCapacity(),10);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}
	/** don't do tests for getters
//tests for getters	
	@Test
	public void getCapacity() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
			assertEquals("gets capacity of 10",queueSample.getCapacity(),10);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void getSize() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
			assertEquals("gets size of 0",queueSample.getSize(),0);
		}catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void getFront() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
			assertNull("returns null for front when queueSample is empty",queueSample.getData()[queueSample.getFront()]);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}
	 */

	//tests for add	
	@Test
	public void testAddSize() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
			//		System.out.println(queueSample.getSize());
			Location s = new Location(1,2);
			queueSample.add(s);
			assertEquals("size of one when add to empty queueSample",queueSample.getSize(),1);
		}catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testAddFront() {
		PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
		Location s = new Location(1,2);
		try {
			queueSample.add(s);
			assertSame("front is newly added object",queueSample.getData()[queueSample.getFront()],s);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testAddNull() {
		PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
		Location a = new Location (1,2);
		Location s = null;
		try {
			queueSample.add(a);
			queueSample.add(s);
			assertEquals(queueSample.getSize(),1);
			assertEquals("front stays same after adding a null object",queueSample.getData()[queueSample.getFront()],a);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	//tests for remove	
	@Test
	public void testRemoveSize() {
		PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
		Location a = new Location (1,2);
		Location s = new Location (2,3);
		try {
			queueSample.add(a);
			queueSample.add(s);
			queueSample.remove();
			assertEquals("size decreases by one with remove",queueSample.getSize(),1);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testRemoveNull() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
			assertNull("trying to remove from empty list returns null",queueSample.getData()[queueSample.getFront()]);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	@Test
	public void testRemoveFront() {
		PossibleLocationsQueue queueSample = new PossibleLocationsQueue();
		Location a = new Location (1,2);
		Location s = new Location (2,3);
		try {
			queueSample.add(a);
			queueSample.add(s);
			queueSample.remove();
			assertEquals("removing front makes next element the front",queueSample.getData()[queueSample.getFront()],s);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}

	//tests for makeLarger	
	@Test
	public void testMakeLargerCapacity() {
		try {
			PossibleLocationsQueue queueSample = new PossibleLocationsQueue(2);
			Location x = new Location (1,2);
			Location y = new Location (2,3);
			Location z = new Location (3,4);
			queueSample.add(x);
			queueSample.add(y);
			queueSample.add(z);
			assertEquals("capacity increases 1.5x",queueSample.getCapacity(),3);
		} catch (Exception e) {
			fail("Exception thrown incorrectly."+e.getMessage());
		}
	}
	
	@Test
	public void testMakeLargerOutofOrder(){
		PossibleLocationsQueue old = new PossibleLocationsQueue(2);
		Location x = new Location (1,2);
		Location y = new Location (2,3);
		Location z = new Location (3,4);
		old.add(x);
		old.add(y);
		old.remove();
		old.add(x);
		old.add(z);
		for(int i=0;i<old.getSize();i++) {
			assertEquals("all elements are copied over correctly when array is not in order",old.getData()[i],old.getData()[((old.getFront()+i)%old.getSize())]);
		}
	}
}
