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
