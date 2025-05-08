package com.interview.problems.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentMap;

/**
 * Java Streams API examples for interview preparation
 * Covers creating streams, intermediate operations, terminal operations,
 * collectors, parallel streams, and common stream patterns
 */
public class StreamsExamples {

    public static void main(String[] args) {
        System.out.println("========== JAVA STREAMS API EXAMPLES ==========");
        
        // Sample data for examples
        List<Person> people = Arrays.asList(
            new Person("Alice", 28, "Software Engineer", 75000, Gender.FEMALE),
            new Person("Bob", 35, "Product Manager", 95000, Gender.MALE),
            new Person("Charlie", 42, "Director", 120000, Gender.MALE),
            new Person("Diana", 30, "Software Engineer", 85000, Gender.FEMALE),
            new Person("Edward", 25, "Designer", 65000, Gender.MALE),
            new Person("Fiona", 33, "Software Engineer", 90000, Gender.FEMALE),
            new Person("George", 45, "CTO", 150000, Gender.MALE),
            new Person("Hannah", 37, "Product Manager", 100000, Gender.FEMALE)
        );
        
        // Example 1: Creating Streams
        System.out.println("\n----- Creating Streams -----");
        creatingStreams();
        
        // Example 2: Basic Stream Operations
        System.out.println("\n----- Basic Stream Operations -----");
        basicStreamOperations(people);
        
        // Example 3: Filtering and Mapping
        System.out.println("\n----- Filtering and Mapping -----");
        filteringAndMapping(people);
        
        // Example 4: Sorting
        System.out.println("\n----- Sorting -----");
        sorting(people);
        
        // Example 5: Finding and Matching
        System.out.println("\n----- Finding and Matching -----");
        findingAndMatching(people);
        
        // Example 6: Reduction
        System.out.println("\n----- Reduction -----");
        reduction(people);
        
        // Example 7: Collecting
        System.out.println("\n----- Collecting -----");
        collecting(people);
        
        // Example 8: Grouping and Partitioning
        System.out.println("\n----- Grouping and Partitioning -----");
        groupingAndPartitioning(people);
        
        // Example 9: Primitive Streams
        System.out.println("\n----- Primitive Streams -----");
        primitiveStreams();
        
        // Example 10: Flat Mapping
        System.out.println("\n----- Flat Mapping -----");
        flatMapping();
        
        // Example 11: Parallel Streams
        System.out.println("\n----- Parallel Streams -----");
        parallelStreams();
        
        // Example 12: Operation Composition
        System.out.println("\n----- Operation Composition -----");
        operationComposition(people);
        
        // Example 13: Infinite Streams
        System.out.println("\n----- Infinite Streams -----");
        infiniteStreams();
        
        // Example 14: Stream Reductions
        System.out.println("\n----- Stream Reductions -----");
        streamReductions(people);
        
        // Example 15: Custom Collectors
        System.out.println("\n----- Custom Collectors -----");
        customCollectors(people);
    }
    
    /**
     * Example 1: Different ways to create streams
     */
    private static void creatingStreams() {
        // 1. Creating a stream from a collection
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        Stream<String> streamFromCollection = list.stream();
        System.out.println("Stream from collection: " + streamFromCollection.collect(Collectors.joining(", ")));
        
        // 2. Creating a stream from array
        String[] array = {"apple", "banana", "cherry"};
        Stream<String> streamFromArray = Arrays.stream(array);
        System.out.println("Stream from array: " + streamFromArray.collect(Collectors.joining(", ")));
        
        // 3. Creating a stream of specific values
        Stream<String> streamOfValues = Stream.of("apple", "banana", "cherry");
        System.out.println("Stream of values: " + streamOfValues.collect(Collectors.joining(", ")));
        
        // 4. Creating an empty stream
        Stream<String> emptyStream = Stream.empty();
        System.out.println("Empty stream count: " + emptyStream.count());
        
        // 5. Creating an infinite stream with generate
        Stream<String> generatedStream = Stream.generate(() -> "element").limit(3);
        System.out.println("Generated stream: " + generatedStream.collect(Collectors.joining(", ")));
        
        // 6. Creating an infinite stream with iterate
        Stream<Integer> iteratedStream = Stream.iterate(1, n -> n + 2).limit(5);
        System.out.println("Iterated stream: " + iteratedStream.collect(Collectors.joining(", ")));
        
        // 7. Creating a stream from a file (lines)
        // Stream<String> fileStream = Files.lines(Paths.get("file.txt"));
        
        // 8. Creating a stream from a string
        IntStream streamFromString = "hello".chars();
        System.out.println("Stream from string: " + 
            streamFromString.mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining(", ")));
        
        // 9. Creating a stream from a range of numbers
        IntStream streamFromRange = IntStream.rangeClosed(1, 5);
        System.out.println("Stream from range: " + 
            streamFromRange.boxed().map(Object::toString).collect(Collectors.joining(", ")));
    }
    
    /**
     * Example 2: Basic Stream Operations
     */
    private static void basicStreamOperations(List<Person> people) {
        // forEach - performs an action for each element
        System.out.println("All people names:");
        people.stream()
            .map(Person::getName)
            .forEach(name -> System.out.print(name + ", "));
        System.out.println();
        
        // count - count elements in the stream
        long count = people.stream()
            .filter(person -> person.getAge() > 30)
            .count();
        System.out.println("Number of people over 30: " + count);
        
        // max/min - find max or min element by comparator
        Optional<Person> oldestPerson = people.stream()
            .max(Comparator.comparing(Person::getAge));
        System.out.println("Oldest person: " + oldestPerson.map(Person::getName).orElse("None"));
        
        // distinct - remove duplicates
        List<String> jobs = people.stream()
            .map(Person::getJob)
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Unique jobs: " + jobs);
        
        // limit - limit the number of elements
        List<String> firstThreeNames = people.stream()
            .map(Person::getName)
            .limit(3)
            .collect(Collectors.toList());
        System.out.println("First three names: " + firstThreeNames);
        
        // skip - skip the first n elements
        List<String> afterFirstTwo = people.stream()
            .map(Person::getName)
            .skip(2)
            .collect(Collectors.toList());
        System.out.println("Names after skipping first two: " + afterFirstTwo);
    }
    
    /**
     * Example 3: Filtering and Mapping
     */
    private static void filteringAndMapping(List<Person> people) {
        // filter - filter elements by predicate
        List<Person> engineers = people.stream()
            .filter(person -> person.getJob().equals("Software Engineer"))
            .collect(Collectors.toList());
        System.out.println("Engineers: " + engineers.stream().map(Person::getName).collect(Collectors.toList()));
        
        // map - transform each element
        List<String> upperCaseNames = people.stream()
            .map(Person::getName)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Uppercase names: " + upperCaseNames);
        
        // mapToInt/mapToDouble - transform to primitive stream
        double averageSalary = people.stream()
            .mapToInt(Person::getSalary)
            .average()
            .orElse(0);
        System.out.println("Average salary: $" + averageSalary);
        
        // peek - perform action on each element without affecting the stream
        List<String> namesPeeked = people.stream()
            .filter(person -> person.getAge() > 30)
            .peek(person -> System.out.println("Filtered: " + person.getName()))
            .map(Person::getName)
            .peek(name -> System.out.println("Mapped: " + name))
            .collect(Collectors.toList());
        System.out.println("Names after peek: " + namesPeeked);
        
        // Complex filtering with multiple conditions
        List<Person> complexFilter = people.stream()
            .filter(person -> person.getAge() > 30)
            .filter(person -> person.getSalary() > 90000)
            .filter(person -> person.getGender() == Gender.FEMALE)
            .collect(Collectors.toList());
        System.out.println("Women over 30 with salary over $90k: " + 
            complexFilter.stream().map(Person::getName).collect(Collectors.toList()));
    }
    
    /**
     * Example 4: Sorting
     */
    private static void sorting(List<Person> people) {
        // Basic sorting
        List<String> sortedNames = people.stream()
            .map(Person::getName)
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Names sorted alphabetically: " + sortedNames);
        
        // Sorting with a comparator
        List<Person> sortedByAge = people.stream()
            .sorted(Comparator.comparingInt(Person::getAge))
            .collect(Collectors.toList());
        System.out.println("People sorted by age: " + 
            sortedByAge.stream().map(p -> p.getName() + " (" + p.getAge() + ")").collect(Collectors.toList()));
        
        // Sorting with multiple criteria
        List<Person> sortedByJobAndSalary = people.stream()
            .sorted(Comparator.comparing(Person::getJob)
                   .thenComparingInt(Person::getSalary).reversed())
            .collect(Collectors.toList());
        System.out.println("People sorted by job and then by salary (desc): " + 
            sortedByJobAndSalary.stream()
                .map(p -> p.getName() + " (" + p.getJob() + ", $" + p.getSalary() + ")")
                .collect(Collectors.toList()));
        
        // Sorting with null values
        List<Person> withNulls = new ArrayList<>(people);
        withNulls.add(new Person(null, 50, "Consultant", 110000, Gender.MALE));
        
        List<String> sortedWithNulls = withNulls.stream()
            .map(Person::getName)
            .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
            .collect(Collectors.toList());
        System.out.println("Names sorted with nulls last: " + sortedWithNulls);
    }
    
    /**
     * Example 5: Finding and Matching
     */
    private static void findingAndMatching(List<Person> people) {
        // anyMatch - check if any element matches the predicate
        boolean anyEngineers = people.stream()
            .anyMatch(person -> person.getJob().equals("Software Engineer"));
        System.out.println("Are there any engineers? " + anyEngineers);
        
        // allMatch - check if all elements match the predicate
        boolean allOver20 = people.stream()
            .allMatch(person -> person.getAge() > 20);
        System.out.println("Are all people over 20? " + allOver20);
        
        // noneMatch - check if no elements match the predicate
        boolean noTeenagers = people.stream()
            .noneMatch(person -> person.getAge() < 20);
        System.out.println("Are there no teenagers? " + noTeenagers);
        
        // findFirst - find the first element that matches
        Optional<Person> firstEngineer = people.stream()
            .filter(person -> person.getJob().equals("Software Engineer"))
            .findFirst();
        System.out.println("First engineer: " + firstEngineer.map(Person::getName).orElse("None"));
        
        // findAny - find any element that matches (useful for parallel streams)
        Optional<Person> anyFemale = people.stream()
            .filter(person -> person.getGender() == Gender.FEMALE)
            .findAny();
        System.out.println("Any female: " + anyFemale.map(Person::getName).orElse("None"));
    }
    
    /**
     * Example 6: Reduction
     */
    private static void reduction(List<Person> people) {
        // reduce - combine elements into a single result
        int totalAge = people.stream()
            .map(Person::getAge)
            .reduce(0, Integer::sum);
        System.out.println("Total age: " + totalAge);
        
        // reduce with a more complex accumulator
        Optional<Person> personWithHighestSalary = people.stream()
            .reduce((p1, p2) -> p1.getSalary() > p2.getSalary() ? p1 : p2);
        System.out.println("Person with highest salary: " + 
            personWithHighestSalary.map(p -> p.getName() + " ($" + p.getSalary() + ")").orElse("None"));
        
        // reduce with identity and combiner
        int totalSalary = people.stream()
            .reduce(0, 
                    (sum, person) -> sum + person.getSalary(), 
                    Integer::sum);
        System.out.println("Total salary: $" + totalSalary);
        
        // Creating a comma-separated list of names with reduce
        String allNames = people.stream()
            .map(Person::getName)
            .reduce("", (s1, s2) -> s1.isEmpty() ? s2 : s1 + ", " + s2);
        System.out.println("All names: " + allNames);
    }
    
    /**
     * Example 7: Collecting
     */
    private static void collecting(List<Person> people) {
        // Collect to list
        List<String> namesList = people.stream()
            .map(Person::getName)
            .collect(Collectors.toList());
        System.out.println("Names list: " + namesList);
        
        // Collect to set
        Set<String> jobsSet = people.stream()
            .map(Person::getJob)
            .collect(Collectors.toSet());
        System.out.println("Jobs set: " + jobsSet);
        
        // Collect to map
        Map<String, Integer> namesToAges = people.stream()
            .collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println("Names to ages map: " + namesToAges);
        
        // Collect with joining
        String namesCsv = people.stream()
            .map(Person::getName)
            .collect(Collectors.joining(", ", "Names: [", "]"));
        System.out.println(namesCsv);
        
        // Collect with counting
        long count = people.stream()
            .collect(Collectors.counting());
        System.out.println("Count: " + count);
        
        // Collect with averaging
        double avgAge = people.stream()
            .collect(Collectors.averagingInt(Person::getAge));
        System.out.println("Average age: " + avgAge);
        
        // Collect with summing
        int totalSalary = people.stream()
            .collect(Collectors.summingInt(Person::getSalary));
        System.out.println("Total salary: $" + totalSalary);
        
        // Collect statistics
        IntSummaryStatistics salaryStats = people.stream()
            .collect(Collectors.summarizingInt(Person::getSalary));
        System.out.println("Salary statistics: " + salaryStats);
        
        // Collect by reducing
        Optional<Person> oldestPerson = people.stream()
            .collect(Collectors.reducing((p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2));
        System.out.println("Oldest person: " + oldestPerson.map(Person::getName).orElse("None"));
        
        // Collect with filtering
        List<String> engineerNames = people.stream()
            .collect(Collectors.filtering(
                p -> p.getJob().equals("Software Engineer"),
                Collectors.mapping(Person::getName, Collectors.toList())
            ));
        System.out.println("Engineer names: " + engineerNames);
    }
    
    /**
     * Example 8: Grouping and Partitioning
     */
    private static void groupingAndPartitioning(List<Person> people) {
        // Basic grouping by a property
        Map<String, List<Person>> peopleByJob = people.stream()
            .collect(Collectors.groupingBy(Person::getJob));
        System.out.println("People grouped by job:");
        peopleByJob.forEach((job, persons) -> {
            System.out.println(job + ": " + persons.stream().map(Person::getName).collect(Collectors.joining(", ")));
        });
        
        // Grouping and counting
        Map<String, Long> jobCounts = people.stream()
            .collect(Collectors.groupingBy(Person::getJob, Collectors.counting()));
        System.out.println("Job counts: " + jobCounts);
        
        // Grouping with downstream mapping
        Map<String, List<String>> peopleNamesByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.mapping(Person::getName, Collectors.toList())
            ));
        System.out.println("People names by job: " + peopleNamesByJob);
        
        // Multi-level grouping
        Map<String, Map<Gender, List<Person>>> peopleByJobAndGender = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.groupingBy(Person::getGender)
            ));
        System.out.println("People grouped by job and gender:");
        peopleByJobAndGender.forEach((job, genderMap) -> {
            System.out.println(job + ":");
            genderMap.forEach((gender, persons) -> {
                System.out.println("  " + gender + ": " + 
                    persons.stream().map(Person::getName).collect(Collectors.joining(", ")));
            });
        });
        
        // Partitioning (special case of grouping with boolean predicate)
        Map<Boolean, List<Person>> partitionedByAge = people.stream()
            .collect(Collectors.partitioningBy(person -> person.getAge() > 30));
        System.out.println("People over 30: " + 
            partitionedByAge.get(true).stream().map(Person::getName).collect(Collectors.joining(", ")));
        System.out.println("People 30 or younger: " + 
            partitionedByAge.get(false).stream().map(Person::getName).collect(Collectors.joining(", ")));
        
        // Partitioning with downstream collector
        Map<Boolean, Double> averageSalaryByGender = people.stream()
            .collect(Collectors.partitioningBy(
                person -> person.getGender() == Gender.MALE,
                Collectors.averagingDouble(Person::getSalary)
            ));
        System.out.println("Average male salary: $" + averageSalaryByGender.get(true));
        System.out.println("Average female salary: $" + averageSalaryByGender.get(false));
    }
    
    /**
     * Example 9: Primitive Streams
     */
    private static void primitiveStreams() {
        // IntStream
        IntStream intStream = IntStream.range(1, 6);
        System.out.println("Sum of 1 to 5: " + intStream.sum());
        
        // DoubleStream
        DoubleStream doubleStream = DoubleStream.of(1.1, 2.2, 3.3, 4.4, 5.5);
        System.out.println("Average of doubles: " + doubleStream.average().orElse(0));
        
        // LongStream
        LongStream longStream = LongStream.rangeClosed(1, 5);
        System.out.println("Product of 1 to 5: " + longStream.reduce(1, (a, b) -> a * b));
        
        // Boxing and unboxing
        Stream<Integer> boxedStream = IntStream.range(1, 6).boxed();
        IntStream unboxedStream = Stream.of(1, 2, 3, 4, 5).mapToInt(Integer::intValue);
        
        System.out.println("Boxed stream: " + boxedStream.collect(Collectors.toList()));
        System.out.println("Unboxed stream sum: " + unboxedStream.sum());
        
        // Statistics
        IntSummaryStatistics stats = IntStream.rangeClosed(1, 100).summaryStatistics();
        System.out.println("Statistics for 1-100: " + stats);
    }
    
    /**
     * Example 10: Flat Mapping
     */
    private static void flatMapping() {
        // Sample data
        List<List<String>> listOfLists = Arrays.asList(
            Arrays.asList("a", "b", "c"),
            Arrays.asList("d", "e"),
            Arrays.asList("f", "g", "h")
        );
        
        // Flatten a list of lists
        List<String> flatList = listOfLists.stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        System.out.println("Flattened list: " + flatList);
        
        // Word count example
        List<String> sentences = Arrays.asList(
            "Hello world",
            "Java streams are powerful",
            "Flat mapping is useful"
        );
        
        // Get all unique words
        List<String> uniqueWords = sentences.stream()
            .flatMap(sentence -> Arrays.stream(sentence.toLowerCase().split("\\s+")))
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Unique words: " + uniqueWords);
        
        // Create cartesian product
        List<String> list1 = Arrays.asList("a", "b");
        List<String> list2 = Arrays.asList("1", "2", "3");
        
        List<String> cartesianProduct = list1.stream()
            .flatMap(i -> list2.stream().map(j -> i + j))
            .collect(Collectors.toList());
        System.out.println("Cartesian product: " + cartesianProduct);
    }
    
    /**
     * Example 11: Parallel Streams
     */
    private static void parallelStreams() {
        // Create a large list for parallel processing
        List<Integer> largeList = IntStream.rangeClosed(1, 10_000_000)
            .boxed()
            .collect(Collectors.toList());
        
        // Sequential sum
        long start = System.currentTimeMillis();
        int sequentialSum = largeList.stream().reduce(0, Integer::sum);
        long sequentialTime = System.currentTimeMillis() - start;
        
        // Parallel sum
        start = System.currentTimeMillis();
        int parallelSum = largeList.parallelStream().reduce(0, Integer::sum);
        long parallelTime = System.currentTimeMillis() - start;
        
        System.out.println("Sequential sum: " + sequentialSum + " in " + sequentialTime + "ms");
        System.out.println("Parallel sum: " + parallelSum + " in " + parallelTime + "ms");
        
        // When parallel streams can be problematic
        
        // 1. Operations with state dependency (like forEach with mutable accumulator)
        List<Integer> result = new ArrayList<>();
        largeList.parallelStream().filter(i -> i % 1000000 == 0).forEach(result::add);
        System.out.println("Results may be added in unpredictable order: " + result);
        
        // 2. Operations with side effects
        StringBuilder sb = new StringBuilder();
        largeList.parallelStream().limit(5).forEach(i -> sb.append(i));
        System.out.println("StringBuilder with parallel stream (result may vary): " + sb.toString());
        
        // Better approach: Use a collector
        String collected = largeList.parallelStream()
            .limit(5)
            .map(Object::toString)
            .collect(Collectors.joining());
        System.out.println("Collected with parallel stream: " + collected);
        
        // 3. Order-based operations
        List<Integer> orderedList = largeList.parallelStream()
            .limit(5) // limit is order-based and may be less efficient in parallel
            .collect(Collectors.toList());
        System.out.println("First 5 elements (order preserved): " + orderedList);
        
        // Make a parallel stream sequential
        List<Integer> resequenced = largeList.parallelStream()
            .sequential()
            .limit(5)
            .collect(Collectors.toList());
        System.out.println("Resequenced stream: " + resequenced);
    }
    
    /**
     * Example 12: Operation Composition
     */
    private static void operationComposition(List<Person> people) {
        // Complex operation combining multiple stream operations
        Map<String, Double> averageSalaryByJobForOver30 = people.stream()
            .filter(person -> person.getAge() > 30)
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.averagingDouble(Person::getSalary)
            ));
        
        System.out.println("Average salary by job for people over 30:");
        averageSalaryByJobForOver30.forEach((job, avgSalary) -> 
            System.out.println(job + ": $" + avgSalary));
        
        // Another complex example: find the person with the highest salary in each gender
        Map<Gender, Optional<Person>> highestPaidByGender = people.stream()
            .collect(Collectors.groupingBy(
                Person::getGender,
                Collectors.maxBy(Comparator.comparingInt(Person::getSalary))
            ));
        
        System.out.println("Highest paid by gender:");
        highestPaidByGender.forEach((gender, person) -> 
            System.out.println(gender + ": " + 
                person.map(p -> p.getName() + " ($" + p.getSalary() + ")").orElse("None")));
        
        // Count people by age range
        Map<String, Long> countByAgeRange = people.stream()
            .collect(Collectors.groupingBy(
                person -> {
                    int age = person.getAge();
                    if (age < 30) return "Under 30";
                    else if (age < 40) return "30-39";
                    else return "40+";
                },
                Collectors.counting()
            ));
        
        System.out.println("Count by age range: " + countByAgeRange);
    }
    
    /**
     * Example 13: Infinite Streams
     */
    private static void infiniteStreams() {
        // Generate an infinite stream of random numbers
        Stream<Double> randomStream = Stream.generate(Math::random);
        System.out.println("5 random numbers:");
        randomStream.limit(5).forEach(System.out::println);
        
        // Generate an infinite stream of Fibonacci numbers
        Stream<Integer> fibonacci = Stream.iterate(
            new int[]{0, 1}, 
            f -> new int[]{f[1], f[0] + f[1]}
        ).map(f -> f[0]);
        
        System.out.println("First 10 Fibonacci numbers:");
        fibonacci.limit(10).forEach(num -> System.out.print(num + " "));
        System.out.println();
        
        // Generate an infinite stream of dates
        Stream<LocalDate> dates = Stream.iterate(
            LocalDate.now(), 
            date -> date.plusDays(1)
        );
        
        System.out.println("Next 5 days:");
        dates.limit(5).forEach(System.out::println);
        
        // Finding elements in an infinite stream
        int firstMultipleOf50 = Stream.iterate(1, n -> n + 1)
            .filter(n -> n % 50 == 0)
            .findFirst()
            .orElse(0);
        
        System.out.println("First multiple of 50: " + firstMultipleOf50);
    }
    
    /**
     * Example 14: Stream Reductions
     */
    private static void streamReductions(List<Person> people) {
        // Find youngest and oldest person
        Optional<Person> youngest = people.stream()
            .min(Comparator.comparingInt(Person::getAge));
        
        Optional<Person> oldest = people.stream()
            .max(Comparator.comparingInt(Person::getAge));
        
        System.out.println("Youngest: " + youngest.map(p -> p.getName() + " (" + p.getAge() + ")").orElse("None"));
        System.out.println("Oldest: " + oldest.map(p -> p.getName() + " (" + p.getAge() + ")").orElse("None"));
        
        // Calculate average and sum
        OptionalDouble averageAge = people.stream()
            .mapToInt(Person::getAge)
            .average();
        
        int totalSalary = people.stream()
            .mapToInt(Person::getSalary)
            .sum();
        
        System.out.println("Average age: " + averageAge.orElse(0));
        System.out.println("Total salary: $" + totalSalary);
        
        // Complex reduction: build a comma-separated string of names
        // Using collect
        String namesWithCollect = people.stream()
            .map(Person::getName)
            .collect(Collectors.joining(", "));
        
        // Using reduce
        String namesWithReduce = people.stream()
            .map(Person::getName)
            .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
        
        System.out.println("Names with collect: " + namesWithCollect);
        System.out.println("Names with reduce: " + namesWithReduce);
        
        // Custom reduction: find the person with the longest name
        Optional<Person> personWithLongestName = people.stream()
            .reduce((p1, p2) -> p1.getName().length() > p2.getName().length() ? p1 : p2);
        
        System.out.println("Person with longest name: " + 
            personWithLongestName.map(p -> p.getName() + " (" + p.getName().length() + " chars)").orElse("None"));
    }
    
    /**
     * Example 15: Custom Collectors
     */
    private static void customCollectors(List<Person> people) {
        // Convert to a custom collector (example: collecting to a frequency map)
        Map<String, Integer> jobFrequency = people.stream()
            .map(Person::getJob)
            .collect(Collectors.toMap(
                job -> job, // Key mapper
                job -> 1,   // Value mapper
                Integer::sum // Merger function for duplicate keys
            ));
        
        System.out.println("Job frequency: " + jobFrequency);
        
        // Custom collector with collectingAndThen
        String highestPaidPerson = people.stream()
            .collect(Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparingInt(Person::getSalary)),
                optional -> optional.map(p -> p.getName() + " ($" + p.getSalary() + ")").orElse("None")
            ));
        
        System.out.println("Highest paid person: " + highestPaidPerson);
        
        // Two-level grouping with custom collector
        Map<String, Map<String, List<Person>>> peopleByJobAndAgeRange = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.groupingBy(
                    person -> {
                        int age = person.getAge();
                        if (age < 30) return "Under 30";
                        else if (age < 40) return "30-39";
                        else return "40+";
                    }
                )
            ));
        
        System.out.println("People by job and age range:");
        peopleByJobAndAgeRange.forEach((job, ageRangeMap) -> {
            System.out.println(job + ":");
            ageRangeMap.forEach((ageRange, persons) -> {
                System.out.println("  " + ageRange + ": " + 
                    persons.stream().map(Person::getName).collect(Collectors.joining(", ")));
            });
        });
        
        // Concurrent collectors for parallel streams
        ConcurrentMap<String, List<Person>> concurrentMap = people.parallelStream()
            .collect(Collectors.groupingByConcurrent(Person::getJob));
        
        System.out.println("Concurrent grouping by job:");
        concurrentMap.forEach((job, persons) -> {
            System.out.println(job + ": " + 
                persons.stream().map(Person::getName).collect(Collectors.joining(", ")));
        });
    }
    
    // Person class for examples
    static class Person {
        private String name;
        private int age;
        private String job;
        private int salary;
        private Gender gender;
        
        public Person(String name, int age, String job, int salary, Gender gender) {
            this.name = name;
            this.age = age;
            this.job = job;
            this.salary = salary;
            this.gender = gender;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        public String getJob() {
            return job;
        }
        
        public int getSalary() {
            return salary;
        }
        
        public Gender getGender() {
            return gender;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    // Gender enum
    enum Gender {
        MALE, FEMALE
    }
}