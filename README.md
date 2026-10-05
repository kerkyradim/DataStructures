# Open Addressing Hash Table (Java)

**Course:** Data Structures — Harokopio University of Athens  
**Author:** [Kerkyra Dimisianou](https://github.com/kerkyradim) · 
**Repository:** [kerkyradim/DataStructures](https://github.com/kerkyradim/DataStructures)

Implementation of a **dictionary ADT** using an **open addressing hash table** with **linear probing**, custom **matrix hashing** (bit-set + random matrix), dynamic **rehashing**, and a **word-frequency** demo (`App`).

---

## Project overview

| Component | Role |
|-----------|------|
| `Dictionary.java` | Dictionary / `Entry` interface (`put`, `get`, `remove`, `contains`, iterator, …) |
| `OpenAddressingHashTable.java` | Hash table implementation (open addressing, rehash, `HashIterator`, `EntryImpl`) |
| `App.java` | Reads text from stdin and counts word frequencies |
| `TestingImplementation.java` | Manual / demo tests |

**Report:** [`docs/REPORT.md`](docs/REPORT.md) (corrected summary) · original submission: [`docs/OpenAddressingHashTableReport.docx`](docs/OpenAddressingHashTableReport.docx)

---

## Build & run

Requirements: **JDK 17+**, **Maven**

```bash
mvn clean package
java -cp target/ErgasiaHashMap-1.0-SNAPSHOT.jar App
```

Paste or pipe text, then end input (Ctrl+Z on Windows, Ctrl+D on Linux/macOS). The program prints each token and its frequency.

```bash
mvn test
```

---

## Concepts (hash maps)

- Hash function maps keys to table indices; **collisions** resolved by **probing** the next slot  
- **Load factor** triggers **rehash** into a larger array  
- Iterator walks occupied entries for the frequency summary in `App`

---

## License

Academic coursework — reference use with attribution.
