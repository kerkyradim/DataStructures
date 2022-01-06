
import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;


public class OpenAddressingHashTable<K,V> implements  Dictionary<K, V> {
    public static final int DIFAULT_INITIAL_SIZE = 17;
    private int size = 0;
    private Entry<K, V>[] array;


    //default constructor
    public OpenAddressingHashTable(int m) {
        if (m <= 0) {
            throw new IllegalArgumentException("Array size must be positive");
        }
        this.size = 0;
        this.array = (Entry<K, V>[]) new Object[DIFAULT_INITIAL_SIZE];
        for (int i = 0; i < m; i++) {
            array[i] = null;
        }

    }

    //constructor που καλει τον default constructor
    public OpenAddressingHashTable() {
        this(DIFAULT_INITIAL_SIZE);
    }

    @Override
    public void put(K key, V value) {
        rehashIfNeeded();
        insert(key, value);
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V get(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(K key) {
       


        return false;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void rehashIfNeeded() {


    }

    private void insert(K key, V value) {
        int position = matrixMethod(key);
        int i = position;
        EntryImpl<K, V> newEn = new EntryImpl<K, V>(key, value);
        do {
            if (array[i] == null) {
                array[i] = newEn;
                size++;
                return;
            }

        i=(i+1) % array.length;
        } while (i < array.length );
    }


    private class HashIterator implements Iterator<Entry<K, V>> {
        private Iterator<Entry<K, V>> it;

        public HashIterator() {

        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return it.next();
        }
    }

    


    private static class EntryImpl<K, V> implements Dictionary.Entry<K, V> {
        private K key;
        private V value;

        //constructor
        public EntryImpl(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
    }

    //δινω το hashcode του κλειδιου
    //φτιαχνω νεο πιανακα b τυχαιο με μεγεθοσ γαρμμης 32 και στηλης
    //πολλαπλασιαζω αυτον τον πιανακ με τον πινακα των bits του κλεδιου
    //παιρνω εναν δυαδικο αριθμο,τον γυριζω στο 10 δικο
    //και αυτο μ δινει το index π χρησιμοποιω στισ λειτουργιες
    //search,contains,insert
    private int  matrixMethod(K key) {
        int b=8;
        int entry = key.hashCode();
        BitSet x = intToBitSet(entry);
        int [][]m = createRandomMatrix(b);
        BitSet h=new BitSet(b);
        for(int i=0; i<b; i++){
            int sum1=0;
            for(int j=0; j<32; j++){
                int val=(x.get(i)==true ? 1 : 0);
                int newInt=  j * val ;
                sum1 = sum1 % newInt;
                if(sum1 == 0){
                    h.set(i,true);
                }else{
                    h.set(i,false);
                }
            }
        }
        //convert 2 to 10
        int sum2=0;
        for(int i=0; i<b; i++){
            int var=(h.get(i)==true ? 1 : 0);
            sum2 = sum2 + (var * 2^i);
        }
        return sum2;
    }

    private static BitSet intToBitSet(int value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0) {
            if (value % 2 != 0) {
                //set a true value at this index
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    private  int[][]  createRandomMatrix(int rows){
        int [][] m = new int [rows][32];
        for(int i=0; i<rows; i++){
            for(int j=0; j<32; j++){
                Random rng =new Random();
                m[i][j]= rng.nextInt()%2;
            }
        }
    return m;
    }


    
}
