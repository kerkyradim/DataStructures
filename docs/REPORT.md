# Assignment report — Open Addressing Hash Table

**Course:** Data Structures (Harokopio University)  
**Author:** Kerkyra Dimisianou (IT22026)  
**Implementation:** Java · Maven project `ErgasiaHashMap`

> **Note:** The original Word report (`OpenAddressingHashTableReport.docx`) uses the title *Linear Hash Table*; the code in this repository implements an **open addressing hash table with linear probing** and a **matrix-based hash function**, as required by the assignment specification.

---

## 1. Goal

Build a generic **Dictionary** `K → V` backed by a hash table: insert, search, delete, resize, and iterate over entries. The demo application counts **word frequencies** in a text stream.

---

## 2. Design

### `Dictionary<K,V>`

Public API: `put`, `get`, `remove`, `contains`, `isEmpty`, `size`, `clear`, and `iterator` over `Entry<K,V>` (key / value accessors).

### `OpenAddressingHashTable<K,V>`

- **Storage:** array of `Entry` references; **open addressing** (linear probing) on collision  
- **Hashing:** `matrixMethod` — key `hashCode` as `BitSet`, multiply with a random binary matrix, map to index  
- **Rehash:** `rehashIfNeeded` grows the table when load exceeds threshold  
- **Inner types:** `EntryImpl`, `HashIterator` for traversal  

### `App`

Reads tokens from standard input, updates counts in the dictionary, then prints each word and its frequency.

---

## 3. Complexity (expected)

| Operation | Average case | Worst case |
|-----------|--------------|------------|
| `put` / `get` / `contains` | O(1) | O(n) |
| Rehash | O(n) | O(n) |
| Iterate all entries | O(n) | O(n) |

Worst case occurs with many collisions or poor hash distribution; rehashing keeps average performance reasonable.

---

## 4. How to reproduce

See root [README.md](../README.md): `mvn clean package` and run `App` with sample text input.

---

## 5. Files submitted

- Source: `src/main/java/*.java`  
- Tests: `src/test/java/TestingImplementation.java`  
- Written report: `docs/OpenAddressingHashTableReport.docx`
