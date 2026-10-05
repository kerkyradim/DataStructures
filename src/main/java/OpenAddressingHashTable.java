
    import java.lang.reflect.Array;
    import java.util.Iterator;
    import java.util.NoSuchElementException;

    public class OpenAddressingHashTable<K,V> implements  Dictionary<K,V> {
    public static final int DEFAULT_INITIAL_SIZE = 8;
    private int size ;
    private Entry<K, V>[] array;
    private  int [][]m;
    private int b;




    //default constructor
    public OpenAddressingHashTable(int m) {

        if (m <= 0) {
            throw new IllegalArgumentException("Array size must be positive");
        }

        this.size = 0;
        this.array = (Entry<K, V>[]) Array.newInstance(Entry.class, m);
        this.b=calculateB(array.length);
        this.m=new int[this.b][32];
        this.m=RandomMatrix(this.b);
    }


    public OpenAddressingHashTable() {
        this(DEFAULT_INITIAL_SIZE);
    }

    /**
     * Method responsible for inserting a new Entry element through insert method
     * @param key   a new key
     * @param value a new value
     * */
    @Override
    public void put(K key, V value) {
        rehashIfNeeded();
        insert(key, value);
    }

    /**
     * Method responsible for removing a key
     * @param key the key
     * @return V The value associated with the  key
     */
    @Override
    public V remove(K key) {
        rehashIfNeeded();
        if (!contains(key)) {
            return null;
        }
        int i=matrixMethod(key);
        V tempValue=array[i].getValue();
        array[i]=null;
        //array[i]=null;
        int j=(i+1)%array.length;
        while(array[j] != null){
            int newPos=matrixMethod(array[j].getKey());
            if (newPos <= i) {
                Entry<K, V> temp = array[j];
                array[j] = array[i];
                array[i] = temp;
                i=j;
            }
            j = (j+1)%array.length;
        }
        size--;

        System.out.println("Value of removed key:"+tempValue);
        return tempValue;
    }

    /**
     *Method responsible for getting an element
     * @param key the key
     * @return V The value associated with the key
     */
    @Override
    public V get(K key) {
        int position = matrixMethod(key);
        int i = position;
       while(array[i] != null) {
             if ((array[i].getKey().equals(key))) {
                return array[i].getValue();
             }
             i = (i+1)%array.length;
       }
        //not found
        return null;

    }

    /**
     * Method responsible for checking if a key exist
     * @param key the key
     * @return boolean If key exist returns true
     */
    @Override
    public boolean contains(K key) {
        if(key == null){
            throw new IllegalArgumentException("argument to contain() is null");
        }
        return get(key) != null;
    }

    /**
     * Method responsible for checking if the array is empty
     * @return boolean If array is empty returns true
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Method responsible for getting current size
     * @return int size;
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method responsible for deleting all the elements in the array
     */
    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
    }

    /**
     * Method responsible for iterating all the elements in the array
     * @return Iterator HashIterator
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashIterator();
    }

    /**
     * Method responsible for checking if rehash is needed
     */
    private void rehashIfNeeded() {
        int newLength;
        if(size == array.length ){
             newLength = array.length*2;
             System.out.println("here doubling");

        }else if(size < (array.length / 4) && array.length > 2*DEFAULT_INITIAL_SIZE){
             newLength = array.length/2;
             System.out.println("here cropped");

        }else{
             //do nothing
             return;
        }

        OpenAddressingHashTable<K,V> newHashTable= new OpenAddressingHashTable<>(newLength);

        for(Entry<K,V> e: this){
            newHashTable.insert(e.getKey(),e.getValue());
        }
        this.array = newHashTable.array;
        this.size = newHashTable.size;
        this.m = newHashTable.m;
        this.b = newHashTable.b;
    }

    /**
     * Method responsible for inserting a new Entry element
     * @param key a new key
     * @param value a new value
     */
    private void insert(K key, V value) {
        int pos = matrixMethod(key);
        int i = pos;
        EntryImpl<K,V> e = new EntryImpl<>(key,value);
        boolean FLAG = true;
        while(FLAG ){
            if(array[pos] == null){
                array[pos] = e;
                FLAG=false;
                size++;
            }else if(array[pos].getKey().equals(key)){
                array[pos] = e;
                FLAG = false;
            }else{
                i = (i+1)%array.length;
                pos = i;
            }
        }

    }

    /**
     * Class responsible for creating a HashIterator object
     */
    private class HashIterator implements Iterator<Entry<K, V>> {
    int curr;

    public HashIterator() {
        curr = 0;
    }

        @Override
        public boolean hasNext() {
            while(curr<array.length){
                if(array[curr] != null){
                return true;
            }
        curr++;
        }
        return false;
        }

        @Override
        public Entry<K, V> next() {
        while(!hasNext()){
             throw new NoSuchElementException();
        }

        return array[curr++];
        }
    }

    /**
     * Class responsible for creating a new Entry object
     * @param <K> a new key
     * @param <V> a new value
     */
     private static class EntryImpl<K, V> implements Dictionary.Entry<K, V> {
        private K key;
        private V value;

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


    /**
     * Method responsible for creating universal hashing process
     * @param key the key,which value is analyzed in bits
     * @return int The position where delete,insert and search functions start
     */
    private int  matrixMethod(K key) {
        int[] x; //array with length=32
        int entry = key.hashCode();
        x = getBitsFromInt(entry);
        int[] h = new int[b]; //output

        for (int i = 0; i < b; i++) {
            int sum1 = 0;
            for (int j = 0; j < 32; j++) {
                sum1 = (sum1 + m[i][j] * x[j]) % 2;
            }
            h[i] = sum1;
        }

        //convert 2 to int
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<b; i++){
            stringBuilder.append(h[i]);
        }
        String newString=stringBuilder.toString();
        int pos = Integer.parseInt(newString, 2);
        return pos;
    }

    /**
     * Method responsible for reading an int value bit by bit
     * @param value The value,which analyzed in bits
     * @return int[] An array with boolean values,represent the bits
     */
    private int[] getBitsFromInt(int value) {
        int[] bits = new int[32];
        int mask = 1 << 31;
        for (int bit = 0; bit < 32; bit++) {
            bits[bit] = ((value & mask) == 0 ? 0 : 1);
            value <<= 1;
        }
        return bits;
    }

    /**
     * Method responsible for creating a new random matrix with only 0-1 values
     * @param rows Denotes the rows of the array
     * @return int[][] The matrix
     */
    private int[][] RandomMatrix(int rows){
        for(int i=0; i<rows; i++){
            for(int j=0; j<32; j++){
                m[i][j] = (int) Math.round(Math.random());
            }
        }
        return  m;
    }

    /**
     * Method responsible for calculating the rows of the random matrix m
     * @param length Denotes the length of the array
     * @return int The rows
     */
    private int calculateB(int length){
        int b = (int)(Math.log(length) / Math.log(2));
        return b;
    }
}

