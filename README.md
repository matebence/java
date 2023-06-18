# Generics

Type parameter Names:
- E - Element
- N - Number
- T - Type
- K - Key
- V - Value

## Type Erasure

Java uses type erasure to implement generics. It replaces all generic type parameters with their bound or **Object** for unbounded type parameters.

## Type Inference

The type inference algorithm tries to find the most specific type that works with all of the arguments.

## Wildcards

Subtyping is a fundamental principle in object oriented programming

## Type of bounds

- Lower bound
    - You can not read items from a List<? super T> because you can not guarantee what list it is really pointing to - we can read Objects exclusively
    - We can insert subtypes of T into a List<? super T>
- Upper bound
    - We can read items from a List<? extends T>
    - We can not insert items into a List<? extends T>

## Comparison

- Wildcard
    - We do not have access to the actual type
    - we can later on reference ?
    - Wildcard can handle a single bound
        - ? extends Number
        - ? super Number

- Bounded types
    - we can access the T generic type
    - we can later on reference T
    - bounded type parameters can handle multiple bound
        - T extends Number & Comprable<T>

```java
public class Store<T> {

    private T item;

    public Store(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    // Only extends with multiple bounds
    public <E extends String & Comparable<String>> void iterate(List<E> elements) {
        for (E e : elements) {
            System.out.print(e);
        }
    }
    //  Extends or Super with one bound
    public void showAll(List<? extends Number> list) {
        for (Number n : list) {
            System.out.println(n);
        }
    }
    //  Extends or Super with one bound
    public void addOne(List<? super Number> list) {
        list.add(1);
    }
}
```

```java
public class App {

    public static void main(String[] args) {
        Store<Double> doubleStore = new Store<>(10.00);
        Store<Integer> integerStore = new Store<>(100);

        System.out.println(doubleStore.getItem());
        System.out.println(integerStore.getItem());

        doubleStore.setItem(20.0);
        System.out.println(doubleStore.getItem());

        integerStore.setItem(200);
        System.out.println(integerStore.getItem());

        integerStore.iterate(List.of("A", "B", "C"));

        doubleStore.addOne(List.of(1,2,3));
        doubleStore.showAll(List.of(4,5,6));
    }
}
```

# Lambdas

Syntax:

- () - Lambda input parameters
- -> - Arrow
- {} - Lambda body

```java
@FunctionalInterface
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
```

```java
public class App {

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.print("Running ...");
            }
        };

        Runnable runnableLamda = () -> {
            System.out.print("Running ...");
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Thread lamdaThread = new Thread(runnableLamda);
        lamdaThread.start();
    }
}
```


## Lambda references

- ClassName::instance-method-name
- ClassName::static-instance-method-name
- Instance::methodName

```java
public void hello(String s) {
    System.out.print(s);
}

public void lambdaExample() {
    // We replace the lambda syntax () -> {}
    Consumer<String> consumer = s -> {
        System.out.print(s);
    };
    Consumer<String> reference = this::hello;
}
```

## Lambdas and functional interfaces

```java
public class Bike {

    private String name;
    private String value;

    public Bike(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
```

```java
public class Student {

    private String name;
    private int gradeLevel;
    private String gender;
    private int noteBooks;
    private double gpa;
    private Optional<Bike> bike;
    private List<String> activities = new ArrayList<>();

    public Student(){
    }

    public Student(String name, int gradeLevel, double gpa, String gender, int noteBooks, List<String> activities) {
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.gpa = gpa;
        this.gender = gender;
        this.noteBooks = noteBooks;
        this.activities = activities;
    }

    public Student(String name, int gradeLevel, double gpa, String gender, List<String> activities) {
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.gpa = gpa;
        this.gender = gender;
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNoteBooks() {
        return noteBooks;
    }

    public void setNoteBooks(int noteBooks) {
        this.noteBooks = noteBooks;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public Optional<Bike> getBike() {
        return bike;
    }

    public void setBike(Optional<Bike> bike) {
        this.bike = bike;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public void printListOfActivities(){
        System.out.println("List of Activities are : " + this.activities);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gradeLevel=" + gradeLevel +
                ", gpa=" + gpa +
                ", gender='" + gender + '\'' +
                ", activities=" + activities +
                '}';
    }
}
```

```java
public class StudentDataBase {

    public static Supplier<Student> studentSupplier = () -> new Student("Adam", 2, 4.0, "male", Arrays.asList("swimming", "basketball", "volleyball"));

    public static Optional<Student> getOptionalStudent() {
        Student student = new Student("Adam", 2, 4.0, "male", Arrays.asList("swimming", "basketball", "volleyball"));
        Bike bike = new Bike("Client123", "Client123");
        student.setBike(Optional.of(bike));
        return Optional.of(student);
    }


    /**
     * Total of 6 students in the database.
     *
     * @return
     */
    public static List<Student> getAllStudents() {

        /**
         * 2nd grade students
         */
        Student student1 = new Student("Adam", 2, 3.6, "male", 10, Arrays.asList("swimming", "basketball", "volleyball"));
        Student student2 = new Student("Jenny", 2, 3.8, "female", 11, Arrays.asList("swimming", "gymnastics", "soccer"));
        /**
         * 3rd grade students
         */
        Student student3 = new Student("Emily", 3, 4.0, "female", 12, Arrays.asList("swimming", "gymnastics", "aerobics"));
        Student student4 = new Student("Dave", 3, 4.0, "male", 15, Arrays.asList("swimming", "gymnastics", "soccer"));
        /**
         * 4th grade students
         */
        Student student5 = new Student("Sophia", 4, 3.5, "female", 10, Arrays.asList("swimming", "dancing", "football"));
        Student student6 = new Student("James", 4, 3.9, "male", 22, Arrays.asList("swimming", "basketball", "baseball", "football"));

        return Arrays.asList(student1, student2, student3, student4, student5, student6);
    }
}
```

### Consumer & BiConsumer

```java
public class App {

    public static void main(String[] args) {
        Consumer<Student> c2 = System.out::println;
        StudentDataBase.getAllStudents().forEach(c2);

        Consumer<Student> c3 = (student) -> System.out.println(student.getName());
        Consumer<Student> c4 = (student) -> System.out.println(student.getActivities());

        StudentDataBase.getAllStudents().forEach(e -> {
            if (e.getGradeLevel() >= 3) {
                c3.andThen(c4).andThen(c2).accept(e);
            }
        });

        BiConsumer<String, String> biConsumer = (a, b) -> {
            System.out.println(a  +" and the next is " + b);
        };

        biConsumer.accept("Bence", "Mate");

        BiConsumer<Integer, Integer> multiply = (a,b) -> {
            System.out.println("The result is " + (a*b));
        };

        BiConsumer<Integer, Integer> division = (a,b) -> {
            System.out.println("The result is " + (a/b));
        };

        multiply.andThen(division).accept(10, 5);
    }
}
```

### Supplier

```java
public class App {

    public static  Supplier<Student> studentSupplier = () -> new Student("Adam",2,4.0,"male", Arrays.asList("swimming", "basketball","volleyball"));

    public static Supplier<List<Student>> studentsSupplier = StudentDataBase::getAllStudents;

    public static void main(String[] args) {
        Student student = studentSupplier.get();

        System.out.println("Student is : " + student);

        System.out.println("Students are : " + studentsSupplier.get());
    }
}
```

### Predicate & BiPredicate

```java
public class App {

    BiPredicate<Integer, Double> biPredicate = (gradeLevel, gpa) -> gradeLevel >= 3 && gpa >= 3.9;
    BiConsumer<String, List<String>> studentBiConsumer = (name, activities) -> System.out.println(name + " : " + activities);

    Consumer<Student> studentConsumer = (student -> {
        if (biPredicate.test(student.getGradeLevel(), student.getGpa())) {
            studentBiConsumer.accept(student.getName(), student.getActivities());
        }
    });

    public static void main(String[] args) {
        Predicate<Integer> p = (i) -> {
            return i % 2 == 0;
        };

        p.test(4);
        Predicate<Integer> p1 = (i) -> i % 2 == 0;
        p1.test(4);

        Predicate<Integer> p2 = (i) -> i % 5 == 0;
        p1.and(p2).test(9);
        p1.or(p2).test(9);
        p1.or(p2).negate().test(9);
    }
}
```

### Function & BiFunction

```java
public class App {

    static Predicate<Student> p1 = (s) -> s.getGradeLevel() >= 3;

    static BiFunction<List<Student>, Predicate<Student>, Map<String, Double>> biFunction = (students, studentPredicate) -> {
        Map<String, Double> studentGradeMap = new HashMap<>();
        students.forEach((student -> {

            if (studentPredicate.test(student)) {
                studentGradeMap.put(student.getName(), student.getGpa());
            }
        }));
        return studentGradeMap;

    };

    static Function<List<Student>, Map<String, Double>> function = (students -> {
        Map<String, Double> studentGradeMap = new HashMap<>();
        students.forEach((student -> {

            if (p1.test(student)) {
                studentGradeMap.put(student.getName(), student.getGpa());
            }
        }));

        return studentGradeMap;
    });

    public static void main(String[] args) {
        System.out.println(function.apply(StudentDataBase.getAllStudents()));
    }
}
```

### UnaryOperator & BinaryOperator

```java
public class App {

    static UnaryOperator<String> unaryOperator = (s)->s.concat("Default");
    static BinaryOperator<Integer> binaryOperator = (a,b) -> a*b;

    public static void main(String[] args) {
        System.out.println(unaryOperator.apply("java8"));
        System.out.println(binaryOperator.apply(5,5));
    }
}
```

## Stream API

```java
public class App {

    public static void main(String[] args) {
        // DATA MANIPULATION AND TRANSFORMATION
        // Transforming data with map
        List<String> studentNames = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(studentNames.toString());

        // Transforming data with map, nested List
        List<List<String>> studentActivities = StudentDataBase.getAllStudents().stream()
                .map(Student::getActivities)
                .collect(Collectors.toList());
        System.out.println(studentActivities.toString());

        // Removing outer wrappers via flatMap
        // It removes the nested structure, if it's a list of string then we want just the List
        List<String> studentActivitiesAsSingleList = StudentDataBase.getAllStudents().stream()
                .map(Student::getActivities)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println(studentActivitiesAsSingleList.toString());

        // Filter down the items
        List<Student> filteredStudents = StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa() > 1)
                .collect(Collectors.toList());
        System.out.println(filteredStudents.toString());

        // This is a terminal operation. Used to reduce the content of a stream to a single value
        Optional<Student> studentWithHighestGpa = StudentDataBase.getAllStudents().stream()
                .reduce((student, student2) -> student.getGpa() > student2.getGpa() ? student : student2);
        System.out.println(studentWithHighestGpa.toString());

        // The Java stream peek method mainly to support debugging
        List<Student> listOfStudents = StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa() > 1)
                .peek(student -> System.out.println("Debugging: " + student.getName()))
                .collect(Collectors.toList());

        // SKIPPING OR LIMITING DATA PARTS
        Stream.of(1,2,3,4,5)
                .skip(2)
                .forEach(System.out::println);

        Stream.of(1,2,3,4,5)
                .limit(2)
                .forEach(System.out::println);



        // CONDITIONAL OUTCOME FOR THE WHOLE LIST
        boolean allStudentMatches = StudentDataBase.getAllStudents().stream()
                .allMatch(student -> student.getGpa() > 1);
        System.out.println("allStudentMatches " + allStudentMatches);

        boolean noneStudentMatches = StudentDataBase.getAllStudents().stream()
                .noneMatch(student -> student.getGpa() > 1);
        System.out.println("noneStudentMatches " + noneStudentMatches);

        boolean anyStudentMatches = StudentDataBase.getAllStudents().stream()
                .anyMatch(student -> student.getGpa() > 1);
        System.out.println("anyStudentMatches " + anyStudentMatches);



        // FINDING AN ELEMENT IN THE LIST
        Optional<Student> findFirstStudent = StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa() > 1)
                .findFirst();
        findFirstStudent.ifPresent(System.out::println);

        Optional<Student> findAnyStudent = StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa() > 1)
                .findFirst();
        findAnyStudent.ifPresent(System.out::println);



        // STREAM FACTORY
        Stream<String> stringStream = Stream.of("adam","dan","jenny","dave");
        stringStream.map(String::toUpperCase).forEach(System.out::println);

        List<Integer> integerIterate  = Stream.iterate(1, x->x*2)
                .limit(10)
                .map(Integer::new)
                .collect(Collectors.toList());

        System.out.println("iterate : " + integerIterate);

        List<Integer> integerGenerate  = Stream.generate(new Random()::nextInt)
                .limit(10)
                .collect(Collectors.toList());
        System.out.println("generate : " + integerGenerate);



        // AGGREGATE FUNCTIONS
        List<Double> sortedGpa = StudentDataBase.getAllStudents().stream()
                .map(Student::getGpa)
                .distinct()
                .sorted(Comparator.comparing(Double::doubleValue).reversed())
                .collect(Collectors.toList());
        System.out.println("distinct sortedGpa : " + sortedGpa);

        long countGpa = StudentDataBase.getAllStudents().stream()
                .map(Student::getGpa)
                .distinct()
                .sorted(Comparator.comparing(o -> o))
                .count();
        System.out.println("countGpa : " + countGpa);

        OptionalDouble maxGpa = StudentDataBase.getAllStudents().stream()
                .map(Student::getGpa)
                .distinct()
                .sorted(Comparator.comparing(o -> o))
                .mapToDouble(Double::doubleValue)
                .max();
        maxGpa.ifPresent(System.out::println);

        OptionalDouble minGpa = StudentDataBase.getAllStudents().stream()
                .map(Student::getGpa)
                .distinct()
                .sorted(Comparator.comparing(o -> o))
                .mapToDouble(Double::doubleValue)
                .min();
        minGpa.ifPresent(System.out::println);

        OptionalDouble averageGpa = StudentDataBase.getAllStudents().stream()
                .map(Student::getGpa)
                .distinct()
                .sorted(Comparator.comparing(o -> o))
                .mapToDouble(Double::doubleValue)
                .average();
        averageGpa.ifPresent(System.out::println);

        double sumGpa = StudentDataBase.getAllStudents().stream()
                .map(Student::getGpa)
                .distinct()
                .sorted(Comparator.comparing(o -> o))
                .mapToDouble(Double::doubleValue)
                .sum();
        System.out.println("sumGpa" + sumGpa);



        // INT, LONG & DOUBLE IN STREAMS VIA BOXING AND UNBOXING

        // Unboxing int to Integer
        // (IntStream, LongStream, DoubleStream)
        // (mapToInt, mapToDouble, mapToObj)
        List<Integer> integerList = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("list" + integerList);


        // Unboxing Integer to int
        // (IntStream, LongStream, DoubleStream)
        // (mapToInt, mapToDouble, mapToObj)
        int result = List.of(1,2,4,5).stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println(result);




        // TERMINAL API
                // [Adam-Jenny-Emily-Dave-Sophia-James]
        String result = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining("-","[","]"));
        System.out.println(result);

        // to List, Set and Map
        List<String> studentNameList = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(studentNameList);

        Map<String, Student> studentMap = StudentDataBase.getAllStudents().stream()
                .collect(Collectors.toMap(key -> key.getName(), value -> value));
        System.out.println(studentMap);

        Set<String> studentNameSet = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.toSet());
        System.out.println(studentNameSet);

        // Count, Max, Min Sum and Average
        Long countResult = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.counting());
        System.out.println(countResult);

        Optional<String> maxResult = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.maxBy(Comparator.comparing(String::valueOf)));
        System.out.println(maxResult);

        Optional<String> minResult = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.minBy(Comparator.comparing(String::valueOf)));
        System.out.println(minResult);

        Integer sumResult = StudentDataBase.getAllStudents().stream()
                .collect(Collectors.summingInt(value -> value.getGradeLevel()));
        System.out.println(sumResult);

        Double averageResult = StudentDataBase.getAllStudents().stream()
                .collect(Collectors.averagingDouble(value -> value.getGradeLevel()));
        System.out.println(averageResult);

        // Group By
        Map<String,List<Student>> groupByGpa =  StudentDataBase.getAllStudents()
                .stream()
                .collect(Collectors.groupingBy(student -> student.getGpa()>= 3.8 ?  "OUTSTANDING" : "AVERAGE"));
        System.out.println(groupByGpa);

        Map<Integer,  Map<String,List<Student>>> groupByGpaDownStream =  StudentDataBase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGradeLevel,
                        Collectors.groupingBy(student -> student.getGpa()>= 3.8 ?  "OUTSTANDING" : "AVERAGE")));
        System.out.println(groupByGpaDownStream);

        LinkedHashMap<String,Set<Student>> groupByGpaDownStreamSupplier = StudentDataBase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getName,LinkedHashMap::new,
                        Collectors.toSet()));
        System.out.println(groupByGpaDownStreamSupplier);

        Map<Integer, Optional<Student>> groupByGpaDownStreamMaxBy =  StudentDataBase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGradeLevel, Collectors.maxBy(Comparator.comparingDouble(Student::getGpa))
                ));
        System.out.println(groupByGpaDownStreamMaxBy);

        // Similar to groupingBy but with predicate
        Predicate<Student> gpaPredicate = (student) -> student.getGpa()>=3.8;

        Map<Boolean,Map<String, List<String>>> studentPartitionByGpa = StudentDataBase.getAllStudents()
                .stream()
                .collect(Collectors.partitioningBy(gpaPredicate, Collectors.toMap(Student::getName,Student::getActivities)));
        System.out.println(studentPartitionByGpa);
    }
}
```

## Optional API

```java

public class App {

    public static void main(String[] args) {
        Student student = new Student();
        // It expects a valid value, otherwise NPE
        Optional<Student> optionalStudent = Optional.of(student);
        // We can handle here null
        Optional<Student> optionalNullStudent = Optional.ofNullable(null);
        // We should return this instead of null
        Optional<Student> optionalEmptyStudent = Optional.empty();

        // orElse, orElseGet and orElseThrow
        optionalEmptyStudent.orElseThrow(RuntimeException::new);
        optionalNullStudent.orElse(new Student());
        optionalNullStudent.orElseGet(StudentDataBase.studentSupplier);

        // isPresent and ifPresent
        boolean result = optionalNullStudent.isPresent();
        System.out.println(result);
        optionalNullStudent.ifPresent(System.out::println);

        // map, flatMap and filter
        Optional.ofNullable(StudentDataBase.studentSupplier.get())
                .flatMap(Student::getBike) // Removes double Optional<Optional<Bike>> wrapper
                .filter(e -> e.getName().equals("BMX"))
                .ifPresent(System.out::println);

        Optional.ofNullable(StudentDataBase.studentSupplier.get())
                .map(Student::getGender)
                .filter(e -> e.equals("male"))
                .ifPresent(System.out::println);
    }
}
```

## LocalDate, LocalTime and LocalDateTime

### LocalDate

```java
public class App {

    public static void main(String[] args) {
        // Define
        LocalDate dateNow = LocalDate.now();
        System.out.println(dateNow);

        LocalDate localDate = LocalDate.of(2023, 6, 6);
        System.out.println(localDate);

        // Getting information
        System.out.println(localDate.getYear());
        System.out.println(localDate.getMonth());
        System.out.println(localDate.getMonthValue());
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getDayOfWeek());
        System.out.println(localDate.getDayOfYear());

        // Modifying value via plus, minus
        localDate = localDate.plusYears(1);
        localDate = localDate.minusDays(1);
        localDate = localDate.plusMonths(1);
        System.out.println(localDate);

        // Modifying value via with, TemporalAdjuster
        localDate = localDate.with(TemporalAdjusters.firstDayOfYear());
        System.out.println(localDate);

        // Modifying value via ChronoUnit, ChronoField
        localDate = localDate.plus(4, ChronoUnit.DAYS);
        System.out.println(localDate);
        localDate = localDate.with(ChronoField.YEAR, 2050);
        System.out.println(localDate);

        // Check methods
        System.out.println(localDate.isSupported(ChronoUnit.SECONDS));
        System.out.println(localDate.isLeapYear());
    }
}
```

### LocalTime

```java

public class App {

    public static void main(String[] args) {
        // Define
        LocalTime timeNow = LocalTime.now();
        System.out.println(timeNow);

        LocalTime localTime = LocalTime.of(12, 0, 0);
        System.out.println(localTime);

        // Getting information
        System.out.println(localTime.getHour());
        System.out.println(localTime.getMinute());
        System.out.println(localTime.getSecond());

        // Modifying value via plus, minus
        localTime = localTime.plusHours(1);
        localTime = localTime.minusMinutes(1);
        localTime = localTime.plusSeconds(20);
        System.out.println(localTime);

        // Modifying value via ChronoUnit, ChronoField
        localTime = localTime.plus(4, ChronoUnit.HOURS);
        System.out.println(localTime);
        localTime = localTime.with(ChronoField.MINUTE_OF_HOUR, 30);
        System.out.println(localTime);

        // Check methods
        System.out.println(localTime.isSupported(ChronoUnit.SECONDS));
        System.out.println(localTime.isAfter(LocalTime.now()));
    }
}
```

### LocalDatime

```java
ublic class App {

    public static void main(String[] args) {
        // Define
        LocalDateTime localDateTimeNw = LocalDateTime.now();
        System.out.println(localDateTimeNw);

        LocalDateTime localDateTime = LocalDateTime.of(2023, 6,6, 15,0,0);
        System.out.println(localDateTime);

        // Getting information
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonth());
        System.out.println(localDateTime.getMonthValue());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getDayOfWeek());
        System.out.println(localDateTime.getDayOfYear());

        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getSecond());

        // Modifying value via plus, minus
        localDateTime = localDateTime.plusYears(1);
        localDateTime = localDateTime.minusDays(1);
        localDateTime = localDateTime.plusMonths(1);
        System.out.println(localDateTime);

        localDateTime = localDateTime.plusHours(1);
        localDateTime = localDateTime.minusMinutes(1);
        localDateTime = localDateTime.plusSeconds(20);
        System.out.println(localDateTime);

        // Modifying value via with, TemporalAdjuster
        localDateTime = localDateTime.with(TemporalAdjusters.firstDayOfYear());
        System.out.println(localDateTime);

        // Modifying value via ChronoUnit, ChronoField
        localDateTime = localDateTime.plus(4, ChronoUnit.DAYS);
        System.out.println(localDateTime);
        localDateTime = localDateTime.with(ChronoField.YEAR, 2050);
        System.out.println(localDateTime);

        localDateTime = localDateTime.plus(4, ChronoUnit.HOURS);
        System.out.println(localDateTime);
        localDateTime = localDateTime.with(ChronoField.MINUTE_OF_HOUR, 30);
        System.out.println(localDateTime);

        // Check methods
        System.out.println(localDateTime.isSupported(ChronoUnit.SECONDS));
        System.out.println(localDateTime.isAfter(LocalDateTime.now()));
    }
}
```

### Converting from LocalDate/LocalTime to LocalDateTime

```java
public class App {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTimeFromDate = localDate.atTime(LocalTime.now());
        System.out.println(localDateTimeFromDate);

        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTimeFromTime = localTime.atDate(LocalDate.now());
        System.out.println(localDateTimeFromTime);
    }
}
```

### Using Period

```java
public class App {

    public static void main(String[] args) {
        LocalDate from = LocalDate.of(2023, 1,1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        Period periodUntil = from.until(to);
        System.out.println(periodUntil.getDays());
        System.out.println(periodUntil.getYears());

        Period periodBetween = Period.between(from, to);
        System.out.println(periodBetween.getDays());
        System.out.println(periodBetween.getYears());

        Period period = Period.ofDays(10);
        System.out.println(period);
    }
}
```

### Using Duration

```java

public class App {

    public static void main(String[] args) {
        LocalTime from = LocalTime.of(0,0,0);
        LocalTime to = LocalTime.of(23, 59, 59);

        long durationUntil = from.until(to, ChronoUnit.MINUTES);
        System.out.println(durationUntil);

        Duration duration = Duration.of(10, ChronoUnit.HOURS);
        System.out.println(duration.getSeconds());

        Duration durationBetween = Duration.between(from, to);
        System.out.println(durationBetween.getSeconds());
    }
}
```

### Using Instant

```java
public class App {

    public static void main(String[] args) {
        // Represents the date and time in unix timestamp in a machine readable format
        Instant instant = Instant.now();
        System.out.println(instant);

        Duration duration = Duration.between(instant, Instant.ofEpochMilli(1111111));
        System.out.println(duration);
    }
}
```

### TimeZones

```java
public class App {

    public static void main(String[] args) {
        // Gathering information
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        ZoneOffset zoneOffset = zonedDateTime.getOffset();
        System.out.println(zoneOffset);

        ZoneId zoneId = zonedDateTime.getZone();
        System.out.println(zoneId);

        ZoneId.getAvailableZoneIds().forEach(zone -> System.out.println("zone : " + zone));
        System.out.println(ZonedDateTime.now(ZoneId.of("Asia/Pontianak")));

        // Setting zone for LocalDateTime
        LocalDateTime localDateTimeSystemDefault = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDateTime localDateTimeZoneId = LocalDateTime.now(ZoneId.of("America/Detroit"));
        System.out.println(localDateTimeSystemDefault);
        System.out.println(localDateTimeZoneId);

        // Converting LocalDateTime to ZonedDateTime
        ZonedDateTime  zonedDateTimeFromLocalDateTime = localDateTimeZoneId.atZone(ZoneId.of("America/Chicago"));
        System.out.println(zonedDateTimeFromLocalDateTime);

        // Converting LocalDateTime to OffsetDateTime
        OffsetDateTime offsetDateTime = localDateTimeZoneId.atOffset(ZoneOffset.ofHours(-6));
        System.out.println("offsetDateTime : " + offsetDateTime);
    }
}
```

### Parse and format LocalDateTime

```java

public class App {

    public static void main(String[] args) {
        // LocalDate
        String date = "2018-04-28";
        LocalDate localDate = LocalDate.parse(date);
        LocalDate localDateParse = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);

        System.out.println(localDate);
        System.out.println(localDateParse);

        String customDate = "2018|04|28";
        LocalDate localDateParseCustom = LocalDate.parse(customDate, DateTimeFormatter.ofPattern("yyyy|MM|dd"));
        String localDateValue = DateTimeFormatter.ofPattern("yyyy|MM|dd").format(LocalDate.now());
        System.out.println(localDateParseCustom);
        System.out.println(localDateValue);

        // LocalTime
        String time = "13:00";
        LocalTime localTime = LocalTime.parse(time);
        LocalTime localTimeParse = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);

        System.out.println(localTime);
        System.out.println(localTimeParse);

        String customTime = "13*00";
        LocalTime localTimeParseCustom = LocalTime.parse(customTime, DateTimeFormatter.ofPattern("HH*mm"));
        String localTimeValue = DateTimeFormatter.ofPattern("HH*mm").format(LocalTime.now());
        System.out.println(localTimeParseCustom);
        System.out.println(localTimeValue);

        // LocalDateTime
        String dateTime = "2018-04-18T14:33:33";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        LocalDateTime localDateTimeParse = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);

        System.out.println(localDateTime);
        System.out.println(localDateTimeParse);

        String customDateTime = "2018-04-1814|33|33";
        LocalDateTime localDateTimeParseCustom = LocalDateTime.parse(customDateTime,DateTimeFormatter.ofPattern("yyyy-MM-ddHH|mm|ss"));
        String localDateTimeValue = DateTimeFormatter.ofPattern("yyyy-MM-ddHH|mm|ss").format(LocalDateTime.now());
        System.out.println(localDateTimeValue);
        System.out.println(localDateTimeParseCustom);
    }
}
```

### Convert SQL Date to LocalDate and LocalDate to SQL Date

```java
public class App {

    public static void main(String[] args) {

        /**
         *  java.util.Date to LocalDate and vice versa
         */

        Date date = new Date();
        System.out.println("converted Local Date : "+ date.toInstant().atZone(ZoneId.of("America/Chicago")).toLocalDate());

        LocalDate localDate = LocalDate.now();
        Date date1 = new Date().from(localDate.atTime(LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("java.util.date is : " + date1);


        /**
         *  LocalDate to java.sql.Date
         */

        java.sql.Date date2 = java.sql.Date.valueOf(localDate);
        System.out.println("java.sql.date is : " + date2);


        LocalDate localDate2 = date2.toLocalDate();
        System.out.println("converted Local Date : " + localDate2);

    }
}
```

## Annotations

```java

public class App {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        // Getting constructor

        // Class<?> myClass = MyClass.class;
        // Class<?> myClass = Class.forName("com.bence.mate.MyClass");
        MyClass myClassObject = new MyClass();
        Class<?> myClass = myClassObject.getClass();

        Arrays.stream(myClass.getDeclaredConstructors()).forEach(System.out::println);
        Arrays.stream(myClass.getConstructors()).forEach(System.out::println);
        System.out.println(myClass.getSuperclass());
        System.out.println(myClass.getInterfaces());

        Constructor<?> myClassConstructor = myClass.getDeclaredConstructor(Integer.class);
        myClassConstructor.setAccessible(true);

        MyClass instance = (MyClass) myClassConstructor.newInstance(15);
        System.out.println(instance.getValue());



        // Getting fields

        //all public elements in that class and its super class
        Arrays.stream(myClass.getFields()).forEach(System.out::println);
        //all the elements present in that class only
        Arrays.stream(myClass.getDeclaredFields()).forEach(System.out::println);

        Field field = myClass.getField("name");
        field.set(myClassObject, "ecneb");
        System.out.println(myClassObject.getName());



        // Getting methods

        //all public methods in that class and its super class
        Arrays.stream(myClass.getMethods()).forEach(System.out::println);
        //all the elements present in that class only
        Arrays.stream(myClass.getDeclaredMethods()).forEach(System.out::println);

        Method method = myClass.getMethod("doWork");
        method.invoke(myClassObject);



        // Understanding modifiers

        //if 0 is not public
        //if 1 is public
        int i = method.getModifiers() & Modifier.PUBLIC;
        System.out.println(i);
        System.out.println(Modifier.isPublic(method.getModifiers()));



        // Checking for annotations
        Arrays.stream(myClass.getAnnotations()).filter(e -> e.annotationType().equals(MostUsed.class))
                .forEach(System.out::println);
        Arrays.stream(myClass.getDeclaredMethods()).filter(e -> e.isAnnotationPresent(MostUsed.class))
                .forEach(System.out::println);

        Class<?> clss = Class.forName("com.bence.mate.MyClass");
        Constructor<?> constructor = clss.getConstructor();
        MyClass u = (MyClass) constructor.newInstance();

        Method[] methods = clss.getMethods();
        for (Method item : methods) {
            if (item.isAnnotationPresent(MostUsed.class)) {
                item.invoke(u); //calling the method
            }
        }
    }
}
```

    [access specifier] @interface <AnnotationName> {
                <DataType> <memberName>() [default value];
    }

Type of annotations:
- Marker annotations -@Override
- Single-values Annotations - @SuppressWarnings("raw")
- Multi values annotations - annotations with more then one value

In java we have:
- General purpose Annotations
    - @Override
    - @Supresswarnings
    - @Depricated
- Meta annotations
    - Annotations for create new custom annotations
- Custom Annotations

Creating custom annotation:
- @Inherited - the child will inherit the annotation 
- @Target - where should be used field method constructor etc ...
- @Documented - is used including the annotation to the javadocs
- @Retentation - life span for annotation 
    - RetentionPolicy.SOURCE: Discard during the compile. These annotations don't make any sense after the compile has completed, so they aren't written to the bytecode.
    - Example: @Override, @SuppressWarnings
    - RetentionPolicy.CLASS: Discard during class load. Useful when doing bytecode-level post-processing. Somewhat surprisingly, this is the default.
    - RetentionPolicy.RUNTIME: Do not discard. The annotation should be available for reflection at runtime. Example: @Deprecated

```java
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.Method})
@Retentation({RetentationPolicy.RUNTIME})
public @interface MostUsed {
    String value() default "java"
}

@MostUsed
public class Utility {

    void doStuff() {
        System.out.println("Doing something")
    }


    @MostUsed(name = "maven")
    void doStuff(String arg) {
        System.out.println("Doing something" + arg)
    }

    void doStuff(int i) {
        System.out.println("Doing something" + i)
    }
}
```

```java
@Repetable(value = Designations.class)
@Retention(RUNTIME)
@Target(ElementType.Type)
public @interface Designation {

    String value() default "Employee";
}

@Retentation(RUNTIME)
@Target(ElementType.TYPE)
public @interface Designations {

    public Designation[] value();
}

@Designation("Manager")
@Designation("Consultant")
public class Employee {
}
```

### Reflection alternative for better performance

Method handles API is a upgrade to reflections API. Which is faster. The reason why reflection is slow is the authentication, it checks if its oke to use reflection
on every call.

```java
public class App {

    public static void main(String[] args) throws Throwable {
        //Initialize lookup
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        //Getting student class
        Class<?> studentClass = lookup.findClass(Student.class.getName());
        MethodType stringType = MethodType.methodType(String.class);

        //Getting info
        Student s = new Student();
        s.setCourse("Java");

        MethodHandle getCourseHandle = lookup.findVirtual(Student.class, "getCourse", stringType);
        System.out.println(getCourseHandle.invoke(s));

        //No argument constructor
        MethodType noArgumentType = MethodType.methodType(void.class);
        MethodHandle noArgsHandleConstructor = lookup.findConstructor(studentClass, noArgumentType);
        Student noArgStudent = (Student) noArgsHandleConstructor.invoke();


        //All argument constructor
        MethodType allArgumentType = MethodType.methodType(void.class, String.class, String.class);
        MethodHandle AllArgsHandleConstructor = lookup.findConstructor(studentClass, allArgumentType);
        Student allArgStudent = (Student) AllArgsHandleConstructor.invoke("A", "B");

        //Getter & Setter
        MethodType setterType = MethodType.methodType(void.class, String.class);
        MethodHandle setNameHandle = lookup.findVirtual(studentClass, "setName", setterType);
        setNameHandle.invoke(s, "ecneb");


        //Using static method
        MethodType staticType = MethodType.methodType(void.class, int.class);
        MethodHandle setNameOfStudentHandle = lookup.findStatic(studentClass, "setNameOfStudents", staticType);
        setNameOfStudentHandle.invoke(2);

        //Find public getter and setter
        MethodHandle findGetter1 = lookup.findGetter(studentClass, "name", String.class);
        MethodHandle findSetter1 = lookup.findSetter(studentClass, "name", String.class);
        findSetter1.invoke(s, "Test");

        //Find private getter and setter
        MethodHandles.Lookup privateLookupIn = MethodHandles.privateLookupIn(studentClass, lookup);
        MethodHandle findGetter2 = privateLookupIn.findGetter(studentClass, "name", String.class);
        MethodHandle findSetter2 = privateLookupIn.findSetter(studentClass, "name", String.class);
        findSetter2.invoke(s, "Test");
    }
}
```

## Default vs Static methods

- Default
    - The default keyword is used to identify a default method in a interface
    - Can be overrriden in the implementation class

- Static
    - Similar to default method
    - This cannot be overriden by the implementation class

# Anonymous classes

```java
public class App {

    public static void main(String[] args) {
        Animal myAnimal = new Animal();
        myAnimal.makeNoise();

        // It's a one time use class
        Animal bigFoot = new Animal() {
            @Override
            public void makeNoise() {
                System.out.println("Grawnleeedgehe");
            }
        };
        bigFoot.makeNoise();

        // It's a one time use class
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Running");
            }
        };
    }
}
```

# Local and inner classes

```java
public class OuterClass {

    private int number = 6;

    public void hyThere() {
        System.out.println("Hey there");

        // We can access it, without any problems
        InnerClass innerClass = new InnerClass();
        innerClass.hyThere();

        class LocalInnerClass {
            Integer localClass = 1;

            public void hyThere() {
                System.out.println("Hey there from inner");
            }
        }

        LocalInnerClass localInnerClass = new LocalInnerClass();
        localInnerClass.hyThere();
    }

    public class InnerClass {
        private int innerNumber = 8;

        public void hyThere() {
            System.out.println("Hey there from inner");
        }
    }
}
```

# String

A String is an unavoidable type of variable while writing any application program. String references are used to store various attributes like username, password, etc. In Java, String objects are immutable. Immutable simply means unmodifiable or unchangeable.

```java
public class App {

    public static void main(String[] args) {
        String value = "ecneb";
        // Second is pointing to value
        String second = "ecneb";

        // It a completely new value of 'ecneb'
        String third = new String("ecneb");

        String empty = "";
        for (char ch : third.toCharArray()) {
            // this means, that in each step we create a new String variable,
            // We should use StringBuilder insteads
            empty += ch;
        }
        System.out.println(empty);
    }
}
```

# Final

The final keyword in java is used to restrict the user. The java final keyword can be used in many context. Final can be:

- variable
    - If you make any variable as final, you cannot change the value of final variable(It will be constant).
    - Blank final varaibles can be initialized only in Constructor
- method
    - If you make any method as final, you cannot override it.
- class
    - If you make any class as final, you cannot extend it.

# Comparator and Comparable 

```java
public class Player implements Comparable<Player> {
    private int ranking;
    private String name;
    private int age;
    
    // constructor, getters, setters  
    
    @Override
    public int compareTo(Player otherPlayer) {
        return Integer.compare(getRanking(), otherPlayer.getRanking());
    }
}
```

```java
public static void main(String[] args) {
    List<Player> footballTeam = new ArrayList<>();
    Player player1 = new Player(59, "John", 20);
    Player player2 = new Player(67, "Roger", 22);
    Player player3 = new Player(45, "Steven", 24);
    footballTeam.add(player1);
    footballTeam.add(player2);
    footballTeam.add(player3);

    System.out.println("Before Sorting : " + footballTeam);
    Collections.sort(footballTeam);
    System.out.println("After Sorting : " + footballTeam);
}
```

```java
public class PlayerRankingComparator implements Comparator<Player> {

    @Override
    public int compare(Player firstPlayer, Player secondPlayer) {
       return Integer.compare(firstPlayer.getRanking(), secondPlayer.getRanking());
    }

}
```

```java
public static void main(String[] args) {
    List<Player> footballTeam = new ArrayList<>();
    Player player1 = new Player(59, "John", 20);
    Player player2 = new Player(67, "Roger", 22);
    Player player3 = new Player(45, "Steven", 24);
    footballTeam.add(player1);
    footballTeam.add(player2);
    footballTeam.add(player3);

    System.out.println("Before Sorting : " + footballTeam);
    PlayerRankingComparator playerComparator = new PlayerRankingComparator();
    Collections.sort(footballTeam, playerComparator);
    System.out.println("After Sorting : " + footballTeam);
}
```