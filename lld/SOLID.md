# SOLID Principles

- A set of principles to build good software components.
- Makes the code maintainable and easy to understand.
- Aim for:
  - High cohesion: degree of inter-relatedness between software components. Eg: calculate area and perimeter.
  - Loose coupling: Software components must have low dependencies on external behaviors. Eg: calculate area and save it to db

## S -> Single Responsibility Principle

- All software components must have one and only one reason to change

```java
class Square {
    int side;

    Square(int s) {
        side = s;
    }

    int getArea() {
        return s*s;
    }

    int getPerimeter() {
        return 4*s;
    }

    void save() {
        // save to mysql db
    }
}
```

- area and perimeter are related (high cohesion), but save is not that related.
- Save also is dependent on the type of db being used. Later if we switch a different db, we have to rewrite save logic inside square.

```java
class Square {
    int side;

    Square(int s) {
        side = s;
    }

    int getArea() {};
    int getPerimeter() {};
    void save() {
        new SquareRepo().save(this);
    }
}

class SquareRepo {
    void save(Square s) {
        // save to mysql
    }
}
```

- this way if we want to change the logic of save, we will do in square repo only

## O - Open Closed Principle

- OCP suggests software components must be open for extension but closed for modifications

```java
class AreaCalculation {
    int calculate(Square s) {
        return s.area();
    }

    int calculate(Rectangle r) {
        return r.area();
    }
}
```

- this way more methods are overloaded and original class is being modified

```java
interface Shape {
    int area();
}

class AreaCalculation {
    int calculate(Shape s) {
        return s.area();
    }
}


class Square implements Shape {}
class Rectangle implements Shape {}
```

- this way original class won't change, if new shapes are added

## L - Liskov's substitution principle

- LSP states that objects must be replaceable by their subtypes without affecting the correctness of the program
- LSP can be achieved by:
  - break the hierarchy solution (use interfaces)
  - tell don't ask (overwriting methods in derived classes)

```java
class Car {
    int getInnerWidth() {}
}

class RaceCar extends Car {
    @Override
    int getInnerWidth() {throw error}
    int getCockpitWidth() {}
}
```

- with this approach, race car doesn't have an innerwidth, so when called throws error

- break the hierarchy way:

```java
interface Vehicle {
    int getWidth() {}
}

class Car implements Vehicle {
    int getWidth() {
        return getInnerWidth();
    }

    int getInnerWidth() {};
}

class RaceCar implements Vehicle {
    int getWidth() {
        return getCockpitWidth();
    }

    int getCockpitWidth() {}
}
```

- tell don't ask.
- client should not have to check if `isInstanceOf` car or racecar

```java
class Car {
    int getInnerWidth() {}
}

class RaceCar extends Car {
    int getInnerWidth() {
        return getCockpitWidth();
    }

    int getCockpitWidth() {}
}
```

## I - Interface Segregation Principle

- ISP states that client must not be forced to implement methods it doesn't need

```java
interface IMultiFunction {
    void print();
    void scan();
}

class Printer implements IMultiFunction {
    void print() {}
    void scan() {} // throw error
}

class Copier implements IMultiFunction {
    void print() {}
    void scan() {}
}
```

- Printer doesn't need a scan method, so it throws error when called

```java
interface IPrint {
    void print();
}

interface IScan {
    void scan();
}

class Printer implements IPrint {
    void print();
}

class Copier implements IPrint, IScan {
    void print();
    void scan();
}
```

- This way printer only implements the interfaces it needs
- Identifying ISP violations:
  - fat interfaces
  - low cohesion
  - unimplemented methods in implementations

## D - Dependency Inversion

- DI states that high level modules should not depend on low level modules, both must depend on abstractions. Abstractions must not depend on details. Details must depend on abstractions.

```java
class ProductCatalog {
    void getProducts() {
        SQLRepo sql = new SQLRepo();
        sql.listAll()
    }
}

class SQLRepo {
    void listAll() {
        new SQLConnection("").select()
    }
}
```

- Here ProductCatalog is high level module which is dependent on low level module.

```java
class ProductCatalog {
    void getProducts() {
        Repo r = RepoFactory.create()
    }
}

interface Repo {
    void listAll() {}
}

class SQLRepo implements Repo {
    void listAll() {
        new SQLConnection("").select()
    }
}

class RepoFactory {
    static create() {
        return new SQLRepo();
    }
}
```

- Product catalog and SQLRepo both depend on repo interface.
- Repo factory is used to create instance of repo being used
