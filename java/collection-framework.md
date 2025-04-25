# Collection Framework

- Before java 1.2 there was vector, stack, array, etc. which had:
  - inconsistent style
  - no common interface
  - no interoperability
- Collection framework is a collection of interfaces for making development easier.
- Iterable interface is the root interface that Collection extends => for each loop
- Collection is the main interface that is extended by List, Set, Queue. Map is a separate interface.

## List interface

- List is an interface that extends Collection interface => ordered collection of elements, index-based access and duplicates allowed.
- Array is fixed size, ArrayList is dynamic.

```java
public class Main() {
    public static void main(String args[]) {
        List<Integer> list = new ArrayList<>(); // BCPDCO
        list.add(1);
        list.get(0);
        list.size();
        for(int x: list) {
            System.out.println(x);
        }
        list.contains(1);
        list.remove(2); // index
        list.add(index, element);
        list.set(index, element);
        System.out.println(list); // can be printed directly because it implements toString() Method from parent interfaces.
    }
}
```

- ArrayList internally is an array but can be dynamically resized.
- Capacity vs Size: capacity (internal array's size. size: number of elements stored)
- Growth Factor: 1.5 (grows 1.5 times the old array)
- Removing an element will shift all elements to the right of it, by one to the left => O(n)
- to reduce capacity -> list.trimToSize();
- Arrays.asList("", "", "") or Arrays.asList(arr)
- ArrayList<>(list2) // can init with ArrayList.
- List.of(...) // returns a list that is not modifiable. but can be used to create a new array list.
- To remove by object rather index -> list.remove(Integer.valueOf(1))
- list.toArray() // convert to array
- Collections.sort(list); // to sort
- list.sort(comparator) // can also sort this way

## Comparator

- Inteface to determine sorting order.

```java
class SComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        return s1.length - s2.length;
    }
    // -ve s1 s2
    // +ve s2 s1
}

// to use:
// list.sort(new SComparator())
```

- Can also use lambda expressions
- list.sort((a, b) -> a - b); or list.sort((a, b) -> {})
- Java 8 has comparing static method.
- comparator = Comparator.comparing(Class::value).reversed().thenComparing(Class:value2) ... etc.
- list.sort(comparator)

## LinkedList

- Items are stored in different memory location but are linked to form an ordered collection.
- Items are stored in node in LinkedList
- By default linked list is doublly linked list.

```java
LinkedList<Integer> l = new LinkedList<>();
.add();
.addLast();
.addFirst();
.get();
.getFirst();
.getLast();
.removeIf(x -> {});
.removeAll(list);
.
```

## Vector

- Synchronized unlike ArrayList and LinkedList. So there's also overhead as locking and unlocking.
- Use in multithreaded environment.
- Vector<Integer> v = new Vector<>();
- Vector by default increments 2x times. Or can be specified during init.

## Stack

- Stack extends Vector
- LIFO
- .push()
- .pop()
- .peek()
- .isEmpty()
- .size()
- can use stack as regular vector as well.
- .search() -> one based index
- LinkedList can be used as Stack

## CopyOnWriteArrayList

- a new list is created when write operation is made.
- read ops happen on stable list (fast) and write happens on new copy, once saved -> updated.
- read intensive environment.
- Read and modify at the same time: use this.
- Only when read operation is completed, the write updates are made, until then they are put on hold.

## Map

- Interface to maintain key-value pairs

## HashMap

- HashMap<Integer, String> map
- .put()
- .get()
- .containsKey()
- .containsValue()
- .keySet() // convert map keys to set to do for loop => for(int i : m.keySet()) {}
- It's not stored in order.
- .entrySet() => get's an iterable collection to run for loop and within use .getValue() and .getKey()
- .remove(key)
- .remove(key, value) // remove the
- not Synchronized
- can have null for key and value
- When you write custom classes to store in hashmap -> memory address of the object is key.
- If you have different objects with same internal values, override hashcode and equals method in the class to define how hashmap works.

## Internal structure of HashMap

- key, value, bucket, hash function
- key -> hash function -> index to store in bucket array
- Uses linkedlist (older) and balanced trees (red black trees newer) to store hashed keys when collision occurs.
- Resizing occurs when number of pairs are more than the load factor (0.75)
- When resized, all keys are rehashed. (array size is doubled)

## LinkedHashMap

- order is maintained (double linked list)
- slower due to maintaining order.
- accessOrder: false for insert order, : true for LRU
- .getOrDefault()
- .putIfAbsent()
- not thread safe

## WeakHashMap

- GarbageCollection -> When object is no longer in use, JVM automatically clears it using GC.
- WeakReference<> manually remove the garbage.
- WeakHashMap<>
- using String as key => is strong reference (strings are stored in string pools)
- WeakHashMap uses keys and are weakly referenced. Once keys are marked null and gc is called, key-value pair from WeakHashMap is removed. Not so in HashMap.

## IdentityHashMap

- object's hashcode is used to compare (not class's hashcode as hashmap)
- key1 = new String(""), key2 = new String(""). in hashmap when adding k1 and k2, only one k is added. but in IdentityHashMap two keys are added.

## Comparable

- Comparator vs Comparable
- Comparable interface to tell natural ordering of elements (defined in class). (compareTo)
- Comparator for custom sorting logic. (compare method)

## SortedMap

- Interface that extends Map.
- Sorting on the basis of keys.

## TreeMap

- Sorting on the basis of keys in natural order (the key class must implement comparable)
- .firstKey()
- .lastKey()
- .headMap() // upto what key
- .tailMap() // from which key
- can't access null
- TreeMap internally uses RedBlack Tree
- All operations are O(logN)

## NavigableMap

- Interface
- .lowerKey() // greatest key strictly less than a key or null
- .ceilingKey() // least key greater than or equal to key

## HashTable

- legacy class
- Synchronized
- no null key or value
- replaced by ConcurrentHashMap
- slower because of overhead.
- Hashtable all methods are Synchronized.
- Only one thread can modify at a time

## ConcurrentHashMap

- Extends ConcurrentMap interface
- Before java 8 -> segment based locking
- After java 8 -> compare and swap approach. (no locking except resizing or collision)
- Synchronized

## ImmutableMap

- Content is not modified/ added/ removed when instantiated.
- Map.of() for 10 k-v pairs.
- Map.ofEntries(Map.entry())

## EnumMap

- new EnumMap<>(CustomEnum.class)

## ConcurrentSkipListMap

- sorted and concurrent map
- internal structure is skip list (probabilistic data structure)
- sorted linked list skip over portions for faster access
- multiple layers contain ranges. value to find will be in O(logN)

## Lambda Expressions

- Java 8
- Lambda Expressions => () -> {}
- .sort((a, b) -> a - b)
- Lambda expressions are used to implement functional interface.
- Functional interface should only implement one method.

## Predicate

- Predicate is a functional interface for boolean valued function.
- Predicate<Integer> isEven = x -> x % 2 == 0; // holds a condition.
- isEven.test(4); // true
- T in Predicate is the type that is going to be used in the function body.
- Can combine predicates.

## Function

- Function<Integer, Integer> square = x -> x \* x;
- .addThen, .compose
- .apply

## Consumer

- Takes a value but doesn't return anything.
- Consumer<Integer> consumer = x -> System.out.println(x);
- consumer.accept(5);

## Supplier

- Only returns but doesn't take any value.
- .get()

## Other

- BiPredicate
- BiConsumer
- BiFunction
- UnaryOperator when both input and output is of same type in function
- BinaryOperator

## Method Reference

- .forEach(x -> print(x)); // consumer interface.
- .forEach(System.out::println); // use method reference without invoking it & in place of lambda expression

## Constructor Reference

- collection.stream().map(x -> new Y()).collect(Collectors.toList());
- collection.stream().map(Y::new).collect(Collectors.toList());
- in place of using new Y() -> use Y::new which is constructor reference.

## Streams

- source -> intermediate operations -> terminal operation.
- count of even numbers from a list => numbers.stream().filter(x -> x % 2 == 0).count()
- Creating a stream:
- 1. from collections => c.stream()
- 2. from arrays => Arrays.stream(array);
- 3. from stream.of -> Stream.of("a", "b")
- 4. Stream.generate(() -> 1) // Supplier
- 5. Stream.iterate(seed, function) // reducer type
- Intermediate operations:
- transform from one stream to another.
- will only run if terminal operation is invoked.
- .stream().filter() // no filter
- .stream().filter().count() // filtered and counts
- .stream().sorted()
- .stream().map(x -> x);
- .stream().distinct()
- Terminal ops:
- .collect(Collectors.toList()) // convert stream to another collection.
- .forEach() // consumer
- .reduce((x, y) -> x + y)
- .count()
- .anyMatch, .allMatch, .noneMatch
- .findFirst()
- .findAny()
- string -> chars => sentence.chars() // gives a int stream.
- sentence.chars().filter().count()
- stateful and stateless operations
- map is stateless, while sort is stateful
- streams can't be used after terminal operation.

## Parallel Stream

- .parallelStream() => running multiple threads where tasks are independent. eg: factorial of all numbers in a list.
- convert parallel to sequential => .sequential()

## Collectors

- .collect(Collectors.toList());
- .collect(Collectors.toSet());
- .collect(Collectors.toCollection(() -> new ArrayDeque<>())) // convert to any collection.
- Collectors.joining() // joining
- Summarizing statstical summary -> count, sum, avg => stats = Collectors.summarizingInt(x -> x) => stats.count, stats.avg
- words.stream().collect(Collectors.groupingBy(x -> x.length())) // groupby length
- groupby(classifier, downstream? [do something w classified collections])
- groupby => generate by length as key for treemap.
- partitionBy => split based on predicate.
- mapping => do something before collecting.

## Primitive Streams

- int[] n = {} => IntStream
- .boxed() => wrapper

## Iterable

- .iterator()
- with .hasNext() .next(), .remove(), ...

## Sets

- Set -> HashSet, LinkedHashSet, TreeSet, EnumSet
- Collection.synchronizedSet(set) // make it sync available for all collections.
- concurrentSkipListSet vs copyOnWriteArraySet

## Queues

- LinkedList can act as Queue
- add, remove, peek
- Queue is an interface.
- LinkedList extends Queue.
- remove throws exception, poll returns null
- element throws exception, peek returns null

## ArrayBlockingQueue

## PriorityQueue

- Customized ordering of elements inside queue.
- new PriorityQueue<>(Comparator.reverseOrder())

## Deque

- insertion and deletion at both ends.

## ArrayDeque

- implementation of Deque

## LinkedList for Deque

## BlockingQueue

- Threads
- waits for queue to be empty / non-empty for specific operations.
- ArrayBlockingQueue and LinkedBlockingQueue
- PriorityBlockingQueue
- SynchronousQueue (capacity at most 1)
- DelayQueue

## ConcurrentLinkedQueue

- lock-free thread safe

## ConcurrentLinkedDeque
