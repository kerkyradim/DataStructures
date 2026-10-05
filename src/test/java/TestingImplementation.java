import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestingImplementation {

    private static final int SIZE = 8;
    private Dictionary<Integer, Integer> hashTable;

    @Before
    public void setUpMethod() {
        this.hashTable = new OpenAddressingHashTable<>(SIZE);
    }

    /**
     * A test in order to test put method
     */

    @Test
    public void Test1() {
        for (int i = 0; i < SIZE; i++) {

            hashTable.put(i, i + 1);
            assertTrue((i + 1) == hashTable.size());

        }

        for (Dictionary.Entry<Integer, Integer> e : hashTable) {
            System.out.println("Integer " + e.getKey() + " has value:" + e.getValue());
        }
        System.out.println("------NEXT_TEST------");

    }

    /**
     * A test in order to test remove and isEmpty methods
     */
    @Test
    public void Test2() {

        for (int key = 0; key <SIZE; key++) {
            System.out.println("Element pushed is:" +key+" whith value:" +(key+1));
            hashTable.put(key, key + 1);
        }

        for (int key = 0; key <SIZE; key++) {
            int k = hashTable.remove(key);
            assertEquals(k, key + 1);
        }
        assertTrue(hashTable.isEmpty());
        System.out.println("------NEXT_TEST------");

    }

    /**
     * A test in order to test size method
     */
    @Test
    public void Test3() {
        for (int i = 0; i < 2 * SIZE; i++) {
            hashTable.put(i, i + 1);
            System.out.println("Key pushed is:" + i);
            hashTable.contains(i);
        }
        int i = 0;
        while (hashTable.size() > 3) {
            hashTable.remove(i);
            i++;
        }
        assertTrue(hashTable.size() == 3);

        System.out.println("------NEXT_TEST------");
    }

    /**
     * A test for testing get,contains,size methods
     */
    @Test
    public void Test4() {
        ArrayList<Integer> values = new ArrayList<>();
        Random rng = new Random(20);
        int counter = 0;
        for (int i = 0; i < 2 * SIZE; i++) {
            int n = rng.nextInt(100);
            values.add(n);
            hashTable.put(n, n + 20);
            counter++;
        }

        for (Dictionary.Entry<Integer, Integer> e : hashTable) {
            System.out.println("Integer " + e.getKey() + " has value:" + e.getValue());
            assertTrue(counter == hashTable.size());
        }

        for (Integer v : values) {
            assertTrue(hashTable.get(v) == v + 20);
        }

        for (Integer v : values) {
            if (hashTable.contains(v)) {
                hashTable.remove(v);
                counter--;
                assertTrue(counter == hashTable.size());
            }
        }
    }
}




