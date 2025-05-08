package com.interview.problems.exceptions;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class demonstrates different exception handling techniques in Java
 * It covers try-catch-finally, multi-catch, try-with-resources, throwing and
 * propagating exceptions, custom exceptions, and best practices.
 */
public class ExceptionHandlingExamples {

    public static void main(String[] args) {
        System.out.println("===== EXCEPTION HANDLING EXAMPLES =====");
        
        // Example 1: Basic try-catch
        tryWithCatchExample();
        
        // Example 2: Multiple catch blocks
        multipleCatchExample();
        
        // Example 3: try-catch-finally
        tryCatchFinallyExample();
        
        // Example 4: Multi-catch (Java 7+)
        multiCatchExample();
        
        // Example 5: try-with-resources (Java 7+)
        tryWithResourcesExample();
        
        // Example 6: Throwing exceptions
        try {
            throwingExceptionsExample();
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
        
        // Example 7: Checked vs. Unchecked exceptions
        checkedVsUncheckedExample();
        
        // Example 8: Creating custom exceptions
        try {
            customExceptionExample("Invalid input");
        } catch (InvalidInputException e) {
            System.out.println("Caught custom exception: " + e.getMessage());
            System.out.println("Input was: " + e.getInput());
        }
        
        // Example 9: Exception propagation
        try {
            exceptionPropagationExample();
        } catch (Exception e) {
            System.out.println("Caught propagated exception: " + e.getMessage());
        }
        
        // Example 10: Finally and return values
        int result = finallyWithReturnExample();
        System.out.println("Result from finallyWithReturnExample: " + result);
        
        // Example 11: Using Exception with Java 8 features (lambda expressions)
        exceptionsInLambdasExample();
        
        // Example 12: Nested try blocks
        nestedTryExample();
        
        // Example 13: Suppressed exceptions (Java 7+)
        suppressedExceptionsExample();
        
        // Example 14: Rethrowing exceptions
        try {
            rethrowingExceptionsExample();
        } catch (Exception e) {
            System.out.println("Caught rethrown exception: " + e.getMessage());
        }
        
        // Example 15: Exception handling best practices
        bestPracticesExample();
    }
    
    /**
     * Example 1: Basic try-catch block
     */
    private static void tryWithCatchExample() {
        System.out.println("\n----- Basic try-catch example -----");
        try {
            // Code that might throw an exception
            int result = 10 / 0; // This will throw ArithmeticException
            System.out.println("Result: " + result); // This line will not be executed
        } catch (ArithmeticException e) {
            // Exception handler
            System.out.println("Caught an arithmetic exception: " + e.getMessage());
        }
        System.out.println("Execution continues after the try-catch block");
    }
    
    /**
     * Example 2: Multiple catch blocks
     */
    private static void multipleCatchExample() {
        System.out.println("\n----- Multiple catch blocks example -----");
        try {
            // Code that might throw different types of exceptions
            int[] array = new int[5];
            
            // Try uncommenting one of these lines to see different exceptions
            array[10] = 30; // This will throw ArrayIndexOutOfBoundsException
            //Integer.parseInt("abc"); // This will throw NumberFormatException
            //String str = null; str.length(); // This will throw NullPointerException
            
            System.out.println("This line will not be executed if an exception occurs");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        } catch (Exception e) {
            // Catch all other exceptions
            System.out.println("Caught a different exception: " + e.getClass().getName());
        }
    }
    
    /**
     * Example 3: try-catch-finally block
     */
    private static void tryCatchFinallyExample() {
        System.out.println("\n----- try-catch-finally example -----");
        FileInputStream fileInputStream = null;
        try {
            // Open a file that doesn't exist
            fileInputStream = new FileInputStream("non-existent-file.txt");
            // This code won't execute if the file doesn't exist
            int data = fileInputStream.read();
            while (data != -1) {
                System.out.print((char) data);
                data = fileInputStream.read();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        } finally {
            // This block always executes, regardless of whether an exception occurred
            System.out.println("Executing finally block");
            // Close the resource properly
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Example 4: Multi-catch (Java 7+)
     */
    private static void multiCatchExample() {
        System.out.println("\n----- Multi-catch example (Java 7+) -----");
        try {
            // Code that might throw different exceptions
            if (Math.random() > 0.5) {
                throw new IOException("IO error occurred");
            } else {
                throw new SQLException("SQL error occurred");
            }
        } catch (IOException | SQLException e) {
            // Handle multiple exception types in a single catch block
            System.out.println("Caught either IOException or SQLException: " + e.getMessage());
            System.out.println("Exception type: " + e.getClass().getName());
        }
    }
    
    /**
     * Example 5: try-with-resources (Java 7+)
     */
    private static void tryWithResourcesExample() {
        System.out.println("\n----- try-with-resources example (Java 7+) -----");
        
        // Resources declared in the try statement are automatically closed
        try (
            // These resources will be automatically closed at the end of the block
            FileReader reader = new FileReader("test-resource.txt");
            BufferedReader bufferedReader = new BufferedReader(reader)
        ) {
            String line = bufferedReader.readLine();
            System.out.println("Read line: " + line);
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
        
        // Simpler example with a string reader
        try (Scanner scanner = new Scanner("Hello World")) {
            System.out.println("Scanner read: " + scanner.next());
        }
    }
    
    /**
     * Example 6: Throwing exceptions
     */
    private static void throwingExceptionsExample() throws IOException {
        System.out.println("\n----- Throwing exceptions example -----");
        
        if (Math.random() > 0.5) {
            // Create and throw a new exception
            throw new IOException("Simulated IO error");
        }
        
        System.out.println("This line only executes if no exception is thrown");
    }
    
    /**
     * Example 7: Checked vs. Unchecked exceptions
     */
    private static void checkedVsUncheckedExample() {
        System.out.println("\n----- Checked vs. Unchecked exceptions example -----");
        
        try {
            // Unchecked exception (Runtime) - doesn't require explicit handling
            if (Math.random() > 0.75) {
                throw new RuntimeException("This is an unchecked exception");
            }
            
            // Checked exception - must be caught or declared in method signature
            if (Math.random() > 0.25) {
                throw new IOException("This is a checked exception");
            }
        } catch (RuntimeException e) {
            System.out.println("Caught unchecked exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught checked exception: " + e.getMessage());
        }
    }
    
    /**
     * Example 8: Creating custom exceptions
     */
    private static void customExceptionExample(String input) throws InvalidInputException {
        System.out.println("\n----- Custom exceptions example -----");
        
        if (input == null || input.contains("invalid")) {
            throw new InvalidInputException("Input validation failed", input);
        }
        
        System.out.println("Input is valid: " + input);
    }
    
    /**
     * Example 9: Exception propagation
     */
    private static void exceptionPropagationExample() {
        System.out.println("\n----- Exception propagation example -----");
        
        // This method doesn't catch the exception, so it propagates
        methodA();
    }
    
    private static void methodA() {
        // This method also doesn't catch the exception
        methodB();
    }
    
    private static void methodB() {
        // This method throws an exception that propagates up the call stack
        throw new NullPointerException("Exception thrown in methodB");
    }
    
    /**
     * Example 10: Finally and return values
     */
    private static int finallyWithReturnExample() {
        System.out.println("\n----- Finally with return example -----");
        try {
            System.out.println("In try block");
            if (Math.random() > 0.5) {
                return 1; // Try to return from the try block
            }
            throw new RuntimeException("Test exception");
        } catch (Exception e) {
            System.out.println("In catch block");
            return 2; // Try to return from the catch block
        } finally {
            System.out.println("In finally block - this always executes");
            // Uncommenting the next line would override any previous return value
            // return 3; 
        }
    }
    
    /**
     * Example 11: Using Exception with Java 8 features (lambda expressions)
     */
    private static void exceptionsInLambdasExample() {
        System.out.println("\n----- Exceptions in lambdas example -----");
        
        List<String> files = List.of("file1.txt", "file2.txt", "file3.txt");
        
        // Problem: Can't throw checked exceptions from lambda expressions
        // This won't compile: files.forEach(file -> new FileInputStream(file));
        
        // Solution 1: Try-catch inside lambda
        files.forEach(file -> {
            try {
                FileInputStream fis = new FileInputStream(file);
                // Process file...
                fis.close();
            } catch (IOException e) {
                System.out.println("Error processing file " + file + ": " + e.getMessage());
            }
        });
        
        // Solution 2: Use wrapper methods
        files.forEach(ExceptionHandlingExamples::processFileSafely);
        
        // Solution 3: Use functional interfaces with exceptions
        // Define a wrapper that converts checked exceptions to unchecked
        Function<String, FileInputStream> wrapper = file -> {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        };
        
        // Now we can use our wrapped function
        try {
            files.stream()
                 .map(wrapper)
                 .forEach(fis -> System.out.println("Opened file stream: " + fis));
        } catch (RuntimeException e) {
            System.out.println("Exception in stream: " + e.getCause().getMessage());
        }
    }
    
    private static void processFileSafely(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            // Process file...
            fis.close();
        } catch (IOException e) {
            System.out.println("Error processing file " + filename + ": " + e.getMessage());
        }
    }
    
    /**
     * Example 12: Nested try blocks
     */
    private static void nestedTryExample() {
        System.out.println("\n----- Nested try blocks example -----");
        
        try {
            System.out.println("Outer try block");
            
            try {
                System.out.println("Inner try block");
                int result = 10 / 0; // This will throw an ArithmeticException
                System.out.println("This won't execute");
            } catch (NullPointerException e) {
                // This catch block won't execute since the exception is ArithmeticException
                System.out.println("Inner catch: " + e.getMessage());
            } finally {
                System.out.println("Inner finally block");
            }
            
            System.out.println("This won't execute either");
        } catch (ArithmeticException e) {
            // This will catch the exception
            System.out.println("Outer catch: " + e.getMessage());
        } finally {
            System.out.println("Outer finally block");
        }
    }
    
    /**
     * Example 13: Suppressed exceptions (Java 7+)
     */
    private static void suppressedExceptionsExample() {
        System.out.println("\n----- Suppressed exceptions example (Java 7+) -----");
        
        try (AutoCloseableResource resource = new AutoCloseableResource()) {
            System.out.println("Using resource");
            throw new RuntimeException("Primary exception");
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
            
            // Get suppressed exceptions
            Throwable[] suppressed = e.getSuppressed();
            if (suppressed.length > 0) {
                System.out.println("With suppressed exceptions:");
                for (Throwable t : suppressed) {
                    System.out.println("  - " + t.getMessage());
                }
            }
        }
    }
    
    /**
     * Example 14: Rethrowing exceptions
     */
    private static void rethrowingExceptionsExample() throws Exception {
        System.out.println("\n----- Rethrowing exceptions example -----");
        
        try {
            // Original exception
            throw new IOException("Original IO error");
        } catch (IOException e) {
            System.out.println("Caught exception: " + e.getMessage());
            
            // Option 1: Rethrow the same exception
            // throw e;
            
            // Option 2: Wrap in another exception
            Exception newException = new Exception("Wrapped exception");
            newException.initCause(e);
            throw newException;
            
            // Option 3 (Java 7+): More precise rethrow
            // throw e; // Type of 'e' is known to be IOException
        }
    }
    
    /**
     * Example 15: Exception handling best practices
     */
    private static void bestPracticesExample() {
        System.out.println("\n----- Exception handling best practices example -----");
        
        // Best practice 1: Catch specific exceptions first
        try {
            // Some code that may throw exceptions
            if (Math.random() > 0.8) {
                throw new NullPointerException("Null value");
            } else if (Math.random() > 0.6) {
                throw new IllegalArgumentException("Bad argument");
            } else if (Math.random() > 0.4) {
                throw new IllegalStateException("Bad state");
            } else if (Math.random() > 0.2) {
                throw new RuntimeException("Generic runtime error");
            }
        } catch (NullPointerException e) {
            // Most specific catch first
            System.out.println("Handling null pointer: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // More specific than RuntimeException
            System.out.println("Handling illegal argument: " + e.getMessage());
        } catch (RuntimeException e) {
            // Less specific catch last
            System.out.println("Handling runtime exception: " + e.getMessage());
        }
        
        // Best practice 2: Don't catch exceptions you can't handle
        // Bad example (commented out):
        /*
        try {
            // Complex operation
        } catch (Exception e) {
            // Empty catch block or just logging - BAD PRACTICE
        }
        */
        
        // Best practice 3: Include useful details in exceptions
        try {
            validateInput(-5, "processValue");
        } catch (IllegalArgumentException e) {
            System.out.println("Well-formed exception message: " + e.getMessage());
        }
        
        // Best practice 4: Clean up resources properly
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("test.txt");
            // Process file...
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } finally {
            // Always close resources in finally block (pre-Java 7)
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
        
        // Best practice 5: Use try-with-resources (Java 7+)
        try (Scanner scanner = new Scanner("test data")) {
            // Use scanner...
            String data = scanner.nextLine();
            System.out.println("Read data: " + data);
        } // Scanner automatically closed
    }
    
    /**
     * Validate a parameter and throw an exception with useful details
     */
    private static void validateInput(int value, String operation) {
        if (value < 0) {
            throw new IllegalArgumentException(
                "Negative value (" + value + ") not allowed for operation: " + operation);
        }
    }
    
    /**
     * Custom AutoCloseable resource for demonstrating suppressed exceptions
     */
    static class AutoCloseableResource implements AutoCloseable {
        @Override
        public void close() throws Exception {
            System.out.println("Closing resource");
            throw new IllegalStateException("Exception during resource closing");
        }
    }
    
    /**
     * A utility for handling checked exceptions in lambdas
     */
    @FunctionalInterface
    interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
        
        static <T> Consumer<T> unchecked(ThrowingConsumer<T, Exception> consumer) {
            return t -> {
                try {
                    consumer.accept(t);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        }
    }
    
    /**
     * Custom exception class demonstrating a checked exception
     */
    static class InvalidInputException extends Exception {
        private final String input;
        
        public InvalidInputException(String message, String input) {
            super(message);
            this.input = input;
        }
        
        public String getInput() {
            return input;
        }
    }
}