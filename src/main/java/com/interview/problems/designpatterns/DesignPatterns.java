package com.interview.problems.designpatterns;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of common design patterns in Java
 * This class demonstrates the following patterns:
 * 1. Singleton
 * 2. Factory Method
 * 3. Abstract Factory
 * 4. Builder
 * 5. Prototype
 * 6. Adapter
 * 7. Decorator
 * 8. Composite
 * 9. Proxy
 * 10. Observer
 * 11. Strategy
 * 12. Command
 * 13. Template Method
 * 14. Iterator
 * 15. State
 */
public class DesignPatterns {

    public static void main(String[] args) {
        // Demonstrate each pattern
        System.out.println("===== Design Patterns Examples =====");
        
        // 1. Singleton Pattern
        System.out.println("\n----- Singleton Pattern -----");
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println("Are the singleton instances the same? " + (singleton1 == singleton2));
        
        // 2. Factory Method Pattern
        System.out.println("\n----- Factory Method Pattern -----");
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.createShape("circle");
        Shape rectangle = shapeFactory.createShape("rectangle");
        circle.draw();
        rectangle.draw();
        
        // 3. Abstract Factory Pattern
        System.out.println("\n----- Abstract Factory Pattern -----");
        GUIFactory windowsFactory = new WindowsFactory();
        Button winButton = windowsFactory.createButton();
        TextField winTextField = windowsFactory.createTextField();
        winButton.click();
        winTextField.input("Windows Text");
        
        GUIFactory macFactory = new MacFactory();
        Button macButton = macFactory.createButton();
        TextField macTextField = macFactory.createTextField();
        macButton.click();
        macTextField.input("Mac Text");
        
        // 4. Builder Pattern
        System.out.println("\n----- Builder Pattern -----");
        Computer computer = new Computer.Builder()
            .setCPU("Intel i7")
            .setRAM("16GB")
            .setStorage("512GB SSD")
            .setGPU("NVIDIA RTX 3080")
            .build();
        System.out.println(computer);
        
        // 5. Prototype Pattern
        System.out.println("\n----- Prototype Pattern -----");
        PrototypeRegistry registry = new PrototypeRegistry();
        
        Document document1 = registry.createDocument("report");
        document1.setContent("This is a report");
        System.out.println("Document 1: " + document1.getType() + " - " + document1.getContent());
        
        Document document2 = document1.clone();
        document2.setContent("This is a cloned report with modified content");
        System.out.println("Document 2: " + document2.getType() + " - " + document2.getContent());
        
        // 6. Adapter Pattern
        System.out.println("\n----- Adapter Pattern -----");
        LegacyRectangle legacyRectangle = new LegacyRectangle();
        Shape rectangleAdapter = new RectangleAdapter(legacyRectangle);
        rectangleAdapter.draw();
        
        // 7. Decorator Pattern
        System.out.println("\n----- Decorator Pattern -----");
        Coffee simpleCoffee = new SimpleCoffee();
        System.out.println("Simple Coffee: $" + simpleCoffee.getCost() + ", " + simpleCoffee.getDescription());
        
        Coffee milkCoffee = new MilkDecorator(simpleCoffee);
        System.out.println("Coffee with Milk: $" + milkCoffee.getCost() + ", " + milkCoffee.getDescription());
        
        Coffee sugarMilkCoffee = new SugarDecorator(milkCoffee);
        System.out.println("Coffee with Milk and Sugar: $" + sugarMilkCoffee.getCost() + ", " + sugarMilkCoffee.getDescription());
        
        // 8. Composite Pattern
        System.out.println("\n----- Composite Pattern -----");
        FileSystemComponent file1 = new File("document.txt", 100);
        FileSystemComponent file2 = new File("image.jpg", 200);
        FileSystemComponent file3 = new File("data.xlsx", 300);
        
        Directory subDir = new Directory("SubDirectory");
        subDir.add(file2);
        subDir.add(file3);
        
        Directory rootDir = new Directory("RootDirectory");
        rootDir.add(file1);
        rootDir.add(subDir);
        
        System.out.println("Root Directory Size: " + rootDir.getSize() + "KB");
        rootDir.print(""); // Print the entire structure
        
        // 9. Proxy Pattern
        System.out.println("\n----- Proxy Pattern -----");
        Image realImage = new RealImage("photo.jpg");
        realImage.display(); // Loading and displaying
        realImage.display(); // Just displaying (already loaded)
        
        System.out.println("\nWith Proxy:");
        Image proxyImage = new ProxyImage("photo.jpg");
        proxyImage.display(); // Loading and displaying through proxy
        proxyImage.display(); // Just displaying (already loaded through proxy)
        
        // 10. Observer Pattern
        System.out.println("\n----- Observer Pattern -----");
        WeatherStation weatherStation = new WeatherStation();
        
        WeatherDisplay phoneDisplay = new PhoneDisplay();
        WeatherDisplay windowDisplay = new WindowDisplay();
        
        weatherStation.addObserver(phoneDisplay);
        weatherStation.addObserver(windowDisplay);
        
        weatherStation.setMeasurements(25.0f, 65.0f, 1013.0f); // Notify all observers
        weatherStation.setMeasurements(26.0f, 70.0f, 1010.0f); // Notify all observers
        
        weatherStation.removeObserver(phoneDisplay);
        weatherStation.setMeasurements(23.0f, 80.0f, 1007.0f); // Notify only windowDisplay
        
        // 11. Strategy Pattern
        System.out.println("\n----- Strategy Pattern -----");
        Compressor zipCompressor = new Compressor(new ZipCompressionStrategy());
        zipCompressor.compress("file.txt");
        
        zipCompressor.setStrategy(new RarCompressionStrategy());
        zipCompressor.compress("file.txt");
        
        // 12. Command Pattern
        System.out.println("\n----- Command Pattern -----");
        Light light = new Light();
        Television tv = new Television();
        
        RemoteControl remote = new RemoteControl();
        
        remote.setCommand(0, new LightOnCommand(light), new LightOffCommand(light));
        remote.setCommand(1, new TVOnCommand(tv), new TVOffCommand(tv));
        
        remote.pressOnButton(0); // Turn on light
        remote.pressOnButton(1); // Turn on TV
        remote.pressOffButton(0); // Turn off light
        remote.pressOffButton(1); // Turn off TV
        remote.pressUndoButton(); // Undo last command (TV off)
        
        // 13. Template Method Pattern
        System.out.println("\n----- Template Method Pattern -----");
        DataMiner csvMiner = new CSVDataMiner();
        csvMiner.mine("data.csv");
        
        DataMiner docMiner = new DocDataMiner();
        docMiner.mine("data.doc");
        
        // 14. Iterator Pattern
        System.out.println("\n----- Iterator Pattern -----");
        BookCollection books = new BookCollection();
        books.addBook(new Book("Design Patterns", "Gang of Four"));
        books.addBook(new Book("Clean Code", "Robert C. Martin"));
        books.addBook(new Book("Refactoring", "Martin Fowler"));
        
        Iterator<Book> iterator = books.createIterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println("Book: " + book.getName() + " by " + book.getAuthor());
        }
        
        // 15. State Pattern
        System.out.println("\n----- State Pattern -----");
        VendingMachine vendingMachine = new VendingMachine(5);
        vendingMachine.insertCoin();
        vendingMachine.selectProduct();
        vendingMachine.dispense();
        
        vendingMachine.insertCoin();
        vendingMachine.insertCoin(); // Can't insert more coins when already has one
        vendingMachine.selectProduct();
        vendingMachine.dispense();
        
        vendingMachine.selectProduct(); // Can't select when no coin
        vendingMachine.dispense(); // Can't dispense when no product selected
    }
}

// 1. Singleton Pattern
class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {
        // Private constructor to prevent instantiation
    }
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
    
    public void doSomething() {
        System.out.println("Singleton is doing something");
    }
}

// 2. Factory Method Pattern
interface Shape {
    void draw();
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle");
    }
}

class ShapeFactory {
    public Shape createShape(String type) {
        if (type.equalsIgnoreCase("circle")) {
            return new Circle();
        } else if (type.equalsIgnoreCase("rectangle")) {
            return new Rectangle();
        } else {
            throw new IllegalArgumentException("Unknown shape type: " + type);
        }
    }
}

// 3. Abstract Factory Pattern
interface Button {
    void click();
}

interface TextField {
    void input(String text);
}

interface GUIFactory {
    Button createButton();
    TextField createTextField();
}

class WindowsButton implements Button {
    @Override
    public void click() {
        System.out.println("Windows Button clicked");
    }
}

class WindowsTextField implements TextField {
    @Override
    public void input(String text) {
        System.out.println("Windows TextField input: " + text);
    }
}

class MacButton implements Button {
    @Override
    public void click() {
        System.out.println("Mac Button clicked");
    }
}

class MacTextField implements TextField {
    @Override
    public void input(String text) {
        System.out.println("Mac TextField input: " + text);
    }
}

class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public TextField createTextField() {
        return new WindowsTextField();
    }
}

class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public TextField createTextField() {
        return new MacTextField();
    }
}

// 4. Builder Pattern
class Computer {
    private final String cpu;
    private final String ram;
    private final String storage;
    private final String gpu;
    
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
    }
    
    @Override
    public String toString() {
        return "Computer [CPU=" + cpu + ", RAM=" + ram + ", Storage=" + storage + ", GPU=" + gpu + "]";
    }
    
    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String gpu;
        
        public Builder setCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }
        
        public Builder setRAM(String ram) {
            this.ram = ram;
            return this;
        }
        
        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public Builder setGPU(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
    }
}

// 5. Prototype Pattern
interface Cloneable<T> {
    T clone();
}

abstract class Document implements Cloneable<Document> {
    private String type;
    private String content;
    
    public Document(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}

class Report extends Document {
    public Report() {
        super("Report");
    }
    
    @Override
    public Document clone() {
        Report report = new Report();
        report.setContent(this.getContent());
        return report;
    }
}

class Email extends Document {
    public Email() {
        super("Email");
    }
    
    @Override
    public Document clone() {
        Email email = new Email();
        email.setContent(this.getContent());
        return email;
    }
}

class PrototypeRegistry {
    private Map<String, Document> prototypes = new HashMap<>();
    
    public PrototypeRegistry() {
        prototypes.put("report", new Report());
        prototypes.put("email", new Email());
    }
    
    public Document createDocument(String type) {
        Document prototype = prototypes.get(type);
        if (prototype == null) {
            throw new IllegalArgumentException("Unknown document type: " + type);
        }
        return prototype.clone();
    }
}

// 6. Adapter Pattern
class LegacyRectangle {
    public void draw(int x1, int y1, int x2, int y2) {
        System.out.println("Legacy Rectangle drawing: (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")");
    }
}

class RectangleAdapter implements Shape {
    private LegacyRectangle legacyRectangle;
    
    public RectangleAdapter(LegacyRectangle legacyRectangle) {
        this.legacyRectangle = legacyRectangle;
    }
    
    @Override
    public void draw() {
        // Adapt the interface by calling the legacy rectangle
        legacyRectangle.draw(0, 0, 100, 100);
    }
}

// 7. Decorator Pattern
interface Coffee {
    double getCost();
    String getDescription();
}

class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 2.0;
    }
    
    @Override
    public String getDescription() {
        return "Simple coffee";
    }
}

abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " with milk";
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public double getCost() {
        return super.getCost() + 0.2;
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " with sugar";
    }
}

// 8. Composite Pattern
abstract class FileSystemComponent {
    protected String name;
    
    public FileSystemComponent(String name) {
        this.name = name;
    }
    
    public abstract int getSize();
    public abstract void print(String prefix);
}

class File extends FileSystemComponent {
    private int size;
    
    public File(String name, int size) {
        super(name);
        this.size = size;
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    @Override
    public void print(String prefix) {
        System.out.println(prefix + "File: " + name + " (" + size + "KB)");
    }
}

class Directory extends FileSystemComponent {
    private List<FileSystemComponent> children = new ArrayList<>();
    
    public Directory(String name) {
        super(name);
    }
    
    public void add(FileSystemComponent component) {
        children.add(component);
    }
    
    public void remove(FileSystemComponent component) {
        children.remove(component);
    }
    
    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : children) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
    
    @Override
    public void print(String prefix) {
        System.out.println(prefix + "Directory: " + name + " (" + getSize() + "KB)");
        for (FileSystemComponent component : children) {
            component.print(prefix + "  ");
        }
    }
}

// 9. Proxy Pattern
interface Image {
    void display();
}

class RealImage implements Image {
    private String filename;
    private boolean loaded = false;
    
    public RealImage(String filename) {
        this.filename = filename;
    }
    
    private void loadFromDisk() {
        System.out.println("Loading image: " + filename);
        loaded = true;
    }
    
    @Override
    public void display() {
        if (!loaded) {
            loadFromDisk();
        }
        System.out.println("Displaying image: " + filename);
    }
}

class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;
    
    public ProxyImage(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}

// 10. Observer Pattern
interface Observer {
    void update(float temp, float humidity, float pressure);
}

interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

class WeatherStation implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private float temperature;
    private float humidity;
    private float pressure;
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
    
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
}

interface WeatherDisplay extends Observer {
    void display();
}

class PhoneDisplay implements WeatherDisplay {
    private float temperature;
    private float humidity;
    private float pressure;
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
    
    @Override
    public void display() {
        System.out.println("Phone Display: " + temperature + "°C, " + humidity + "% humidity, " + pressure + " hPa");
    }
}

class WindowDisplay implements WeatherDisplay {
    private float temperature;
    private float humidity;
    private float pressure;
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
    
    @Override
    public void display() {
        System.out.println("Window Display: " + temperature + "°C, " + humidity + "% humidity, " + pressure + " hPa");
    }
}

// 11. Strategy Pattern
interface CompressionStrategy {
    void compress(String filename);
}

class ZipCompressionStrategy implements CompressionStrategy {
    @Override
    public void compress(String filename) {
        System.out.println("Compressing " + filename + " using ZIP compression");
    }
}

class RarCompressionStrategy implements CompressionStrategy {
    @Override
    public void compress(String filename) {
        System.out.println("Compressing " + filename + " using RAR compression");
    }
}

class Compressor {
    private CompressionStrategy strategy;
    
    public Compressor(CompressionStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(CompressionStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void compress(String filename) {
        strategy.compress(filename);
    }
}

// 12. Command Pattern
interface Command {
    void execute();
    void undo();
}

class Light {
    public void turnOn() {
        System.out.println("Light is ON");
    }
    
    public void turnOff() {
        System.out.println("Light is OFF");
    }
}

class Television {
    public void turnOn() {
        System.out.println("TV is ON");
    }
    
    public void turnOff() {
        System.out.println("TV is OFF");
    }
}

class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOn();
    }
    
    @Override
    public void undo() {
        light.turnOff();
    }
}

class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOff();
    }
    
    @Override
    public void undo() {
        light.turnOn();
    }
}

class TVOnCommand implements Command {
    private Television tv;
    
    public TVOnCommand(Television tv) {
        this.tv = tv;
    }
    
    @Override
    public void execute() {
        tv.turnOn();
    }
    
    @Override
    public void undo() {
        tv.turnOff();
    }
}

class TVOffCommand implements Command {
    private Television tv;
    
    public TVOffCommand(Television tv) {
        this.tv = tv;
    }
    
    @Override
    public void execute() {
        tv.turnOff();
    }
    
    @Override
    public void undo() {
        tv.turnOn();
    }
}

class RemoteControl {
    private Command[][] commands;
    private Command lastCommand;
    
    public RemoteControl() {
        commands = new Command[5][2]; // 5 slots, on and off for each
    }
    
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        commands[slot][0] = onCommand;
        commands[slot][1] = offCommand;
    }
    
    public void pressOnButton(int slot) {
        commands[slot][0].execute();
        lastCommand = commands[slot][0];
    }
    
    public void pressOffButton(int slot) {
        commands[slot][1].execute();
        lastCommand = commands[slot][1];
    }
    
    public void pressUndoButton() {
        if (lastCommand != null) {
            System.out.println("Undoing last command...");
            lastCommand.undo();
        }
    }
}

// 13. Template Method Pattern
abstract class DataMiner {
    // Template method defines the algorithm
    public final void mine(String filename) {
        String data = openFile(filename);
        String parsedData = parseData(data);
        String analysis = analyzeData(parsedData);
        sendReport(analysis);
        closeFile(filename);
    }
    
    // These steps are fixed and will be the same for all subclasses
    private String openFile(String filename) {
        System.out.println("Opening file: " + filename);
        return "Raw data from " + filename;
    }
    
    private void closeFile(String filename) {
        System.out.println("Closing file: " + filename);
    }
    
    private void sendReport(String analysis) {
        System.out.println("Sending report: " + analysis);
    }
    
    // These steps will vary and are implemented by subclasses
    protected abstract String parseData(String data);
    protected abstract String analyzeData(String data);
}

class CSVDataMiner extends DataMiner {
    @Override
    protected String parseData(String data) {
        System.out.println("Parsing CSV data");
        return "Parsed CSV data";
    }
    
    @Override
    protected String analyzeData(String data) {
        System.out.println("Analyzing CSV data using statistical methods");
        return "CSV Analysis Result";
    }
}

class DocDataMiner extends DataMiner {
    @Override
    protected String parseData(String data) {
        System.out.println("Parsing DOC data using text extraction");
        return "Parsed DOC data";
    }
    
    @Override
    protected String analyzeData(String data) {
        System.out.println("Analyzing DOC data using natural language processing");
        return "DOC Analysis Result";
    }
}

// 14. Iterator Pattern
class Book {
    private String name;
    private String author;
    
    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAuthor() {
        return author;
    }
}

interface Iterator<T> {
    boolean hasNext();
    T next();
}

interface Collection<T> {
    Iterator<T> createIterator();
}

class BookIterator implements Iterator<Book> {
    private List<Book> books;
    private int position = 0;
    
    public BookIterator(List<Book> books) {
        this.books = books;
    }
    
    @Override
    public boolean hasNext() {
        return position < books.size();
    }
    
    @Override
    public Book next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return books.get(position++);
    }
}

class BookCollection implements Collection<Book> {
    private List<Book> books = new ArrayList<>();
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    @Override
    public Iterator<Book> createIterator() {
        return new BookIterator(books);
    }
}

// 15. State Pattern
interface State {
    void insertCoin(VendingMachine machine);
    void selectProduct(VendingMachine machine);
    void dispense(VendingMachine machine);
}

class NoCoinState implements State {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Coin inserted");
        machine.setState(machine.getHasCoinState());
    }
    
    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Please insert a coin first");
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Please insert a coin first");
    }
}

class HasCoinState implements State {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("You already inserted a coin");
    }
    
    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Product selected");
        machine.setState(machine.getProductSelectedState());
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Please select a product first");
    }
}

class ProductSelectedState implements State {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Please wait, product being dispensed");
    }
    
    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Product already selected");
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Product dispensed");
        machine.decrementCount();
        
        if (machine.getCount() > 0) {
            machine.setState(machine.getNoCoinState());
        } else {
            System.out.println("Out of products");
            machine.setState(machine.getSoldOutState());
        }
    }
}

class SoldOutState implements State {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Machine is sold out");
    }
    
    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Machine is sold out");
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("No product to dispense");
    }
}

class VendingMachine {
    private State noCoinState;
    private State hasCoinState;
    private State productSelectedState;
    private State soldOutState;
    
    private State currentState;
    private int count;
    
    public VendingMachine(int count) {
        this.noCoinState = new NoCoinState();
        this.hasCoinState = new HasCoinState();
        this.productSelectedState = new ProductSelectedState();
        this.soldOutState = new SoldOutState();
        
        this.count = count;
        if (count > 0) {
            currentState = noCoinState;
        } else {
            currentState = soldOutState;
        }
    }
    
    public void insertCoin() {
        currentState.insertCoin(this);
    }
    
    public void selectProduct() {
        currentState.selectProduct(this);
    }
    
    public void dispense() {
        currentState.dispense(this);
    }
    
    public void setState(State state) {
        this.currentState = state;
    }
    
    public State getNoCoinState() {
        return noCoinState;
    }
    
    public State getHasCoinState() {
        return hasCoinState;
    }
    
    public State getProductSelectedState() {
        return productSelectedState;
    }
    
    public State getSoldOutState() {
        return soldOutState;
    }
    
    public int getCount() {
        return count;
    }
    
    public void decrementCount() {
        count--;
    }
}