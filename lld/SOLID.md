# SOLID Principles

- A set of principles to build software components.
- Makes the code maintainable, easy to understand.
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
