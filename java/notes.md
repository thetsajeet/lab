# Java Basics

- Java is an OOPs language
- WORA -> Write Once Run Anywhere.
- Only requirement is to have JVM installed on the machine.
- JDK -> Java Development Kit contains softwares such as Javac to convert java source code to byte code, Javadoc for debugging, documentation, etc., source files for java core classes, and JRE (which included JVM)
- JRE -> Java Runtime Environment -> to run Java applications on your machines. Contains JVM, compiled core classes (such as arraylist, etc.) meant for end users. Can install without JDK since it's only for running java code for end users.
- JVM -> Where actual java programs are run.

```
JDK
├── javac (Java Compiler)
├── jshell, javadoc, etc.
├── JRE
│   ├── JVM
│   └── Java Class Libraries
```

- source code written in .java
- complication transforms into bytecode .class
- jvm converts bytecode to binaryfiles
- .java --javac--> .class --jvm--> .exe / .out

- Everything is written inside classes only.
- Sample java program:

```java
class Test {
    public static void main(String args[]) {
        System.out.println("Hello world");
    }
}
```

```
MyJavaApp/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── app/
│       │               └── Main.java
│       └── resources/
│           └── application.properties
├── test/
│   └── java/
│       └── com/
│           └── example/
│               └── app/
│                   └── MainTest.java
├── build/ or target/         (generated after build)
├── lib/                      (optional: external JARs)
├── pom.xml                   (for Maven)
└── build.gradle              (for Gradle)
```

- packages are folders / repos.
- naming: com.example.name (reverse domain name)

## Data Types in Java

- **Primitive DataTypes**:
- integral: byte, short, int, long (1, 2, 4, 8)
- decimal: float, double (4, 8)
- boolean: boolean (1)
- character: char (2) '' only
- characters are mapped to a int value (unicode) UTF 16
- To access max and min value of datatypes: Int.MIN_VALUE, Int.MAX_VALUE
- **Primitive Classes**:
- Integer, Short, Long, etc.
- wrappers around primitve datatypes.
- implicit vs explicit conversion
- String is not primitve. It's a class
- jvm stores primitives such as int, bool in stack
- jvm creates a new object in heap memory
- for strings, jvm creates a string pool for initialization inside "";

eg:

```java
String str1 = "Hello";
String str2 = "Hello";
String str3 = new String("Hello");

// str1 == str2 ? true
// str1 == str3 ? false
// str1.equals(str3) ? true
```

- strings initialized using "" are stored in string pool.
- str1 creates the "" and stores in string pool.
- str2 tries to create, but since it's already created it points to the "" created in stringpool (not created again)
- str3 creates a new "" in heap
- while comparing str1 and str2, since both reference to the same address -> they are equal.
- while comparing str1 and str3, since both point to different address -> they are not equal.
- to compare content, use method str1.equals(str3)
- Strings are immutable.
- don't just use `a.toUpperCase()` but rather `a = a.toUpperCase()`
- stack vs heap vs stringpool

## Arithmetic and Bitwise

- usual operators.
- << left shift & >> right shift

## Control flow, loops

- if, else, switch, while, for, etc. usual, do while

## Array

- Fixed size of sequential collection of elements.
- int[] a -> declaration
- new int[5] -> initialization

## OOPs

- Class: blueprint for creating objects with fields and methods
- Object: Instance of a class
- Encapsulation, Abstraction, Polymorphism, Inheritance
- Inheritance: Single, Multillevel, Hierarchical
- Multiple Inheritance can be achieved by Interface
- Method overloading: Compile time polymorphism
- Method overriding: Run time polymorphism
- Interface can have abstract methods, static constants, static methods, default methods in interface
- static methods can't be overriden, default methods can be overriden.
- abstract class can have constructors.
- access modifiers: default, public, private, protected

## Multithreading

- CPU contains core.
- Core: individual processing unit with CPU.
- Program is software / code.
- Process is program that is being run.
- Thread is the smallest unit of execution within a process. (web browser that is running is a process and each tab is a thread)
- Multitasking: OS to run multiple processes to run on multiple cores => true parallelism.
- On single-core: rapid switching between multiple processes.
- Multithreading: Execute multiple threads within a single process concurrently. (in a single tab, multiple threads to load js, handle user input, etc)
- Multithreading << Multitasking (level)
- in single core -> jvm gives the illusion of multithreading by rapid switching. in multiple cores -> multithreading
- Main thread runs the main method.
- Thread.currentThread().getName() -> main
- ways to create multithreading:
- 1st way:

```java
public class NumberCounter extends Thread {
    @Override
    public run() {
        // logic
    }
}

public class SumCounter extends Runnable {
    @Override
    public run() {
        // logic
    }
}

public class Counter {
    public static void main(String args[]) {
        NumberCounter t1 = new NumberCounter();
        t1.start();

        SumCounter sc = new SumCounter();
        Thread t2 = new Thread(sc);
        t2.start();

        try{
            t1.join();
            t2.join();
        } catch(Exception e) {
            // both t done.
        }

        // done with threadings.

    }
}
```

- to prevent multithreading from read / write when other is processing -> synchronized.

## Collection Framework

- Provides a set of interfaces to support interoperability, common interface, and consistency.
- Iterable (root) -> Collection -> List, Set, Queue .... Map ->
- Commonly used:
- List -> ArrayList & LinkedList
- Set -> HashSet & LinkedhashSet
- Map -> HashMap & LinkedHashMap
- Use generics when giving wrapper types: ArrayList<Integer> l1 = new ArrayList()
- List<Integer> l = new ArrayList<>(); => BCPDCO
