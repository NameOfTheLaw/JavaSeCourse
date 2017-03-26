Implementation | Ordering | Random Access | Key-Value Pairs | Allows Duplicates | Allows Null Values | Thread Safe | Blocking Operations
-------------- | -------- | ------------- | --------------- | ----------------- | ------------------ | ----------- | -------------------
ArrayList | Yes | Yes \ Yes | No | Yes | Yes | No | No
Vector | Yes | Yes \ Yes | No | Yes | Yes | Yes | Yes
Stack | Yes | Yes \ Yes | No | Yes | Yes | Yes | Yes
LinkedList | Yes | No \ No | No | Yes | Yes | No | No
PriorityQueue | Yes | No \ No | No | Yes | No | No | No
HashSet | No | No \ Yes if consider `contains(obj)` | No | No | Yes | No | No
LinkedHashSet | Yes | No \ Yes if consider `contains(obj)` | No | No | Yes | No | No
TreeSet | Yes | No \ No | No | No | No | No | No
HashMap | No | No \ Yes if consider `get(key)` | Yes | Yes | Yes | No | No
LinkedHashMap | Yes | No \ Yes if consider `get(key)` | Yes | Yes | Yes | No | No
TreeMap | Yes | No \ No | Yes | Yes | Yes | No | No

Random Access in means of java.util.RandomAccess `\` Random Access in means of constant time access
