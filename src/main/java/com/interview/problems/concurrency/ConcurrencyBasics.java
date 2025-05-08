package com.interview.problems.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * Basic Java concurrency examples for interview preparation
 * Covers thread creation, synchronization, concurrent collections,
 * locks, atomic variables, thread pools, and completable futures
 */
public class ConcurrencyBasics {

    //==================== THREAD CREATION EXAMPLES ======================
    
    /**
     * Example 1: Creating a thread by extending Thread class
     * 
     * Time Complexity: O(1) for thread creation
     * Space Complexity: O(1)
     */
    public static class ExtendThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread running: " + Thread.currentThread().getName());
        }
    }
    
    /**
     * Example 2: Creating a thread by implementing Runnable interface
     * 
     * Time Complexity: O(1) for thread creation
     * Space Complexity: O(1)
     */
    public static class ImplementRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable running: " + Thread.currentThread().getName());
        }
    }
    
    /**
     * Example 3: Creating a thread with a lambda expression
     * 
     * Time Complexity: O(1) for thread creation
     * Space Complexity: O(1)
     */
    public static void createThreadWithLambda() {
        Thread thread = new Thread(() -> {
            System.out.println("Lambda thread running: " + Thread.currentThread().getName());
        });
        thread.start();
    }
    
    /**
     * Example 4: Creating a thread with Callable (returns a value)
     * 
     * Time Complexity: O(1) for thread creation
     * Space Complexity: O(1)
     */
    public static void createThreadWithCallable() throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> {
            Thread.sleep(1000); // Simulate work
            return "Callable result from " + Thread.currentThread().getName();
        };
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(callable);
        
        System.out.println("Waiting for callable result...");
        String result = future.get(); // Blocks until the result is available
        System.out.println(result);
        
        executor.shutdown();
    }
    
    //==================== SYNCHRONIZATION EXAMPLES ======================
    
    /**
     * Example 5: Synchronized method for thread safety
     * 
     * Time Complexity: O(1) for method calls
     * Space Complexity: O(1)
     */
    private int counter = 0;
    
    public synchronized void incrementCounter() {
        counter++;
    }
    
    public synchronized int getCounter() {
        return counter;
    }
    
    /**
     * Example 6: Synchronized block for finer-grained control
     * 
     * Time Complexity: O(1) for method calls
     * Space Complexity: O(1)
     */
    private int blockCounter = 0;
    private final Object lock = new Object();
    
    public void incrementWithSyncBlock() {
        // Only synchronize the critical section
        synchronized (lock) {
            blockCounter++;
        }
        // Other non-synchronized code can go here
    }
    
    public int getBlockCounter() {
        synchronized (lock) {
            return blockCounter;
        }
    }
    
    /**
     * Example 7: Using volatile for visibility across threads
     * 
     * Time Complexity: O(1) for variable access
     * Space Complexity: O(1)
     */
    private volatile boolean running = true;
    
    public void toggleRunning() {
        running = !running;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Example 8: Thread-safe initialization with Double-Checked Locking
     * 
     * Time Complexity: O(1) for accessor method
     * Space Complexity: O(1) for singleton instance
     */
    private static volatile ConcurrencyBasics instance;
    
    public static ConcurrencyBasics getInstance() {
        if (instance == null) {
            synchronized (ConcurrencyBasics.class) {
                if (instance == null) {
                    instance = new ConcurrencyBasics();
                }
            }
        }
        return instance;
    }
    
    //==================== ATOMIC VARIABLES EXAMPLES ====================
    
    /**
     * Example 9: Using AtomicInteger for thread-safe operations
     * 
     * Time Complexity: O(1) for atomic operations
     * Space Complexity: O(1)
     */
    private AtomicInteger atomicCounter = new AtomicInteger(0);
    
    public void incrementAtomic() {
        atomicCounter.incrementAndGet();
    }
    
    public int getAtomicCounter() {
        return atomicCounter.get();
    }
    
    /**
     * Example 10: Compare and set operations with AtomicInteger
     * 
     * Time Complexity: O(1) for compareAndSet operation
     * Space Complexity: O(1)
     */
    public boolean updateAtomicIfEquals(int expected, int update) {
        return atomicCounter.compareAndSet(expected, update);
    }
    
    //==================== LOCKS EXAMPLES ====================
    
    /**
     * Example 11: Using ReentrantLock for explicit locking
     * 
     * Time Complexity: O(1) for lock operations
     * Space Complexity: O(1)
     */
    private int lockCounter = 0;
    private final Lock reentrantLock = new ReentrantLock();
    
    public void incrementWithLock() {
        reentrantLock.lock();
        try {
            lockCounter++;
        } finally {
            reentrantLock.unlock();
        }
    }
    
    public int getLockCounter() {
        reentrantLock.lock();
        try {
            return lockCounter;
        } finally {
            reentrantLock.unlock();
        }
    }
    
    /**
     * Example 12: Trying to acquire a lock with timeout
     * 
     * Time Complexity: O(1) for tryLock operation
     * Space Complexity: O(1)
     */
    public boolean incrementWithTimeout(long timeout, TimeUnit unit) {
        try {
            if (reentrantLock.tryLock(timeout, unit)) {
                try {
                    lockCounter++;
                    return true;
                } finally {
                    reentrantLock.unlock();
                }
            }
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * Example 13: Using ReadWriteLock for reader preference
     * 
     * Time Complexity: O(1) for lock operations
     * Space Complexity: O(1)
     */
    private int rwLockCounter = 0;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    
    public void incrementWithWriteLock() {
        writeLock.lock();
        try {
            rwLockCounter++;
        } finally {
            writeLock.unlock();
        }
    }
    
    public int getWithReadLock() {
        readLock.lock();
        try {
            return rwLockCounter;
        } finally {
            readLock.unlock();
        }
    }
    
    //==================== THREAD POOL EXAMPLES ====================
    
    /**
     * Example 14: Using a fixed thread pool
     * 
     * Time Complexity: O(1) for task submission
     * Space Complexity: O(n) where n is the number of tasks in the queue
     */
    public static void fixedThreadPoolExample() {
        // Create a thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Submit 5 tasks
        for (int i = 0; i < 5; i++) {
            final int taskNum = i;
            executor.submit(() -> {
                System.out.println("Task " + taskNum + " executed by " + 
                                   Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // Shutdown the executor
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 15: Using a scheduled thread pool
     * 
     * Time Complexity: O(1) for task scheduling
     * Space Complexity: O(n) where n is the number of scheduled tasks
     */
    public static void scheduledThreadPoolExample() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        // Schedule a task to run after 2 seconds
        scheduler.schedule(() -> {
            System.out.println("Delayed task executed");
        }, 2, TimeUnit.SECONDS);
        
        // Schedule a task to run every 3 seconds with an initial delay of 0 seconds
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Fixed rate task executed at " + System.currentTimeMillis());
        }, 0, 3, TimeUnit.SECONDS);
        
        // For example purposes only, don't do this in production
        try {
            Thread.sleep(10000); // Let the scheduler run for 10 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        scheduler.shutdown();
    }
    
    //==================== CONCURRENT COLLECTIONS EXAMPLES ====================
    
    /**
     * Example 16: Using ConcurrentHashMap for thread-safe map operations
     * 
     * Time Complexity: O(1) average case for get/put operations
     * Space Complexity: O(n) where n is the number of entries
     */
    public static void concurrentHashMapExample() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        
        // Populate the map concurrently
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 100; i++) {
            final String key = "key" + i;
            executor.submit(() -> map.put(key, key.hashCode()));
        }
        
        // Check if a key exists and update atomically
        executor.submit(() -> {
            map.computeIfPresent("key5", (k, v) -> v + 100);
            System.out.println("Updated key5: " + map.get("key5"));
        });
        
        // Wait for all tasks to complete
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Print map size
        System.out.println("ConcurrentHashMap size: " + map.size());
    }
    
    /**
     * Example 17: Using CopyOnWriteArrayList for thread-safe list operations
     * 
     * Time Complexity: O(n) for modifications, O(1) for reads
     * Space Complexity: O(n) where n is the number of elements
     */
    public static void copyOnWriteArrayListExample() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        
        // Add elements concurrently
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.submit(() -> {
                list.add("Item " + index);
                // Iterators are snapshot views of the list at the time of creation
                for (String item : list) {
                    System.out.println(Thread.currentThread().getName() + " sees: " + item);
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Final list size: " + list.size());
    }
    
    /**
     * Example 18: Using BlockingQueue for producer-consumer pattern
     * 
     * Time Complexity: O(1) for put/take operations
     * Space Complexity: O(capacity) where capacity is the queue capacity
     */
    public static void blockingQueueExample() {
        // Create a blocking queue with capacity of 5
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        
        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    String item = "Item " + i;
                    queue.put(item); // Blocks if queue is full
                    System.out.println("Produced: " + item);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    String item = queue.take(); // Blocks if queue is empty
                    System.out.println("Consumed: " + item);
                    Thread.sleep(200); // Consumer is slower than producer
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    //==================== COMPLETABLE FUTURE EXAMPLES ====================
    
    /**
     * Example 19: Using CompletableFuture for asynchronous computations
     * 
     * Time Complexity: O(1) for operation chaining
     * Space Complexity: O(1) per CompletableFuture instance
     */
    public static void completableFutureExample() {
        // Create a CompletableFuture that returns a value
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Result from a separate thread";
        });
        
        // Chain operations
        CompletableFuture<String> transformedFuture = future
            .thenApply(s -> s + " - transformed")
            .thenApply(String::toUpperCase);
        
        // Add a completion handler
        transformedFuture.thenAccept(result -> 
            System.out.println("Received: " + result)
        );
        
        // Wait for completion to see the result in example
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 20: Combining multiple CompletableFutures
     * 
     * Time Complexity: O(1) for combination operations
     * Space Complexity: O(n) where n is the number of futures
     */
    public static void combineFuturesExample() {
        // Create two independent futures
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "First result";
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Second result";
        });
        
        // Combine results when both complete
        CompletableFuture<String> combined = future1.thenCombine(
            future2,
            (result1, result2) -> result1 + " and " + result2
        );
        
        // Or do something when either completes
        CompletableFuture<Void> either = CompletableFuture.anyOf(future1, future2)
            .thenAccept(result -> System.out.println("First to complete: " + result));
        
        // Handle errors
        combined.exceptionally(ex -> {
            System.out.println("Error occurred: " + ex.getMessage());
            return "Error result";
        });
        
        // Wait for the combined future to complete
        System.out.println("Combined result: " + combined.join());
    }
    
    /**
     * Helper method to sleep with exception handling
     */
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    //==================== FORK/JOIN FRAMEWORK EXAMPLES ====================
    
    /**
     * Example 21: Using ForkJoinPool for parallel computation
     * 
     * Time Complexity: O(n/p) where n is the input size and p is parallelism
     * Space Complexity: O(log n) for the recursion stack
     */
    public static void forkJoinPoolExample() {
        // Create a list of numbers
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            numbers.add(i);
        }
        
        // Create a ForkJoinPool
        ForkJoinPool pool = ForkJoinPool.commonPool();
        
        // Create a task to compute the sum
        SumTask task = new SumTask(numbers, 0, numbers.size());
        
        // Execute the task
        long result = pool.invoke(task);
        
        System.out.println("Sum: " + result);
        System.out.println("Expected: " + (1000 * 1001 / 2)); // Formula for sum of 1..n
    }
    
    /**
     * ForkJoinTask to compute the sum of a list of integers
     */
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 100;
        private final List<Integer> numbers;
        private final int start;
        private final int end;
        
        public SumTask(List<Integer> numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected Long compute() {
            int length = end - start;
            if (length <= THRESHOLD) {
                // Base case: compute directly
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += numbers.get(i);
                }
                return sum;
            } else {
                // Recursive case: fork and join
                int mid = start + length / 2;
                SumTask leftTask = new SumTask(numbers, start, mid);
                SumTask rightTask = new SumTask(numbers, mid, end);
                
                // Fork right task
                rightTask.fork();
                
                // Compute left task directly
                long leftResult = leftTask.compute();
                
                // Join right task
                long rightResult = rightTask.join();
                
                // Combine results
                return leftResult + rightResult;
            }
        }
    }
    
    //==================== THREAD COORDINATION EXAMPLES ====================
    
    /**
     * Example 22: Using CountDownLatch for coordination
     * 
     * Time Complexity: O(1) for countdown and await operations
     * Space Complexity: O(1)
     */
    public static void countDownLatchExample() {
        // Create a CountDownLatch with count of 3
        CountDownLatch latch = new CountDownLatch(3);
        
        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Submit tasks
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    // Simulate work
                    Thread.sleep(1000 + taskId * 500);
                    System.out.println("Task " + taskId + " completed");
                    
                    // Count down the latch
                    latch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // Wait for all tasks to complete
        try {
            System.out.println("Waiting for all tasks to complete...");
            latch.await();
            System.out.println("All tasks completed!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        executor.shutdown();
    }
    
    /**
     * Example 23: Using CyclicBarrier for coordination
     * 
     * Time Complexity: O(1) for await operations
     * Space Complexity: O(n) where n is the number of parties
     */
    public static void cyclicBarrierExample() {
        // Create a CyclicBarrier with count of 3 and a runnable
        CyclicBarrier barrier = new CyclicBarrier(3, () -> 
            System.out.println("Barrier reached, executing barrier action!")
        );
        
        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Submit tasks
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    // Phase 1
                    System.out.println("Task " + taskId + " starting phase 1");
                    Thread.sleep(1000 + taskId * 500);
                    System.out.println("Task " + taskId + " waiting at barrier 1");
                    barrier.await();
                    
                    // Phase 2
                    System.out.println("Task " + taskId + " starting phase 2");
                    Thread.sleep(1000 + taskId * 500);
                    System.out.println("Task " + taskId + " waiting at barrier 2");
                    barrier.await();
                    
                    System.out.println("Task " + taskId + " completed");
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 24: Using Semaphore for limiting concurrent access
     * 
     * Time Complexity: O(1) for acquire/release operations
     * Space Complexity: O(1)
     */
    public static void semaphoreExample() {
        // Create a Semaphore with 3 permits
        Semaphore semaphore = new Semaphore(3);
        
        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        // Submit 10 tasks, but only 3 can run concurrently
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    System.out.println("Task " + taskId + " is trying to acquire a permit");
                    semaphore.acquire();
                    
                    System.out.println("Task " + taskId + " acquired a permit. " +
                                       "Available permits: " + semaphore.availablePermits());
                    
                    // Simulate work
                    Thread.sleep(2000);
                    
                    // Release the permit
                    System.out.println("Task " + taskId + " is releasing the permit");
                    semaphore.release();
                    
                    System.out.println("Task " + taskId + " released a permit. " +
                                       "Available permits: " + semaphore.availablePermits());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 25: Using Phaser for multi-phase coordination
     * 
     * Time Complexity: O(1) for registration and arrival operations
     * Space Complexity: O(n) where n is the number of registered parties
     */
    public static void phaserExample() {
        // Create a Phaser
        Phaser phaser = new Phaser(1); // Register self
        
        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Submit tasks
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            phaser.register(); // Register each task
            
            executor.submit(() -> {
                try {
                    // Phase 1
                    System.out.println("Task " + taskId + " executing phase 1");
                    Thread.sleep(taskId * 500);
                    System.out.println("Task " + taskId + " completed phase 1");
                    phaser.arriveAndAwaitAdvance(); // Wait for all to arrive
                    
                    // Phase 2
                    System.out.println("Task " + taskId + " executing phase 2");
                    Thread.sleep(taskId * 500);
                    System.out.println("Task " + taskId + " completed phase 2");
                    phaser.arriveAndAwaitAdvance(); // Wait for all to arrive
                    
                    // Phase 3
                    System.out.println("Task " + taskId + " executing phase 3");
                    Thread.sleep(taskId * 500);
                    System.out.println("Task " + taskId + " completed phase 3");
                    phaser.arriveAndDeregister(); // Done with all phases
                    
                    System.out.println("Task " + taskId + " completed all phases");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    phaser.arriveAndDeregister();
                }
            });
        }
        
        // Wait for all tasks to complete and then deregister self
        phaser.arriveAndAwaitAdvance(); // Wait for phase 1
        System.out.println("All tasks completed phase 1");
        
        phaser.arriveAndAwaitAdvance(); // Wait for phase 2
        System.out.println("All tasks completed phase 2");
        
        phaser.arriveAndDeregister(); // Deregister self
        
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("========== JAVA CONCURRENCY BASICS ==========");
        
        System.out.println("\n----- Thread Creation Examples -----");
        Thread t1 = new ExtendThread();
        t1.start();
        
        Thread t2 = new Thread(new ImplementRunnable());
        t2.start();
        
        createThreadWithLambda();
        
        createThreadWithCallable();
        
        System.out.println("\n----- Synchronization Examples -----");
        ConcurrencyBasics instance = new ConcurrencyBasics();
        
        // Create multiple threads to increment the counter
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executor.submit(instance::incrementCounter);
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("Synchronized Counter: " + instance.getCounter());
        System.out.println("Expected: 1000");
        
        System.out.println("\n----- Atomic Variable Examples -----");
        ExecutorService atomicExecutor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            atomicExecutor.submit(instance::incrementAtomic);
        }
        
        atomicExecutor.shutdown();
        atomicExecutor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("Atomic Counter: " + instance.getAtomicCounter());
        System.out.println("Expected: 1000");
        
        System.out.println("\n----- Lock Examples -----");
        ExecutorService lockExecutor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            lockExecutor.submit(instance::incrementWithLock);
        }
        
        lockExecutor.shutdown();
        lockExecutor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("Lock Counter: " + instance.getLockCounter());
        System.out.println("Expected: 1000");
        
        System.out.println("\n----- Thread Pool Examples -----");
        fixedThreadPoolExample();
        
        System.out.println("\n----- Concurrent Collections Examples -----");
        concurrentHashMapExample();
        copyOnWriteArrayListExample();
        
        System.out.println("\n----- Producer-Consumer Example with BlockingQueue -----");
        blockingQueueExample();
        
        System.out.println("\n----- CompletableFuture Examples -----");
        completableFutureExample();
        combineFuturesExample();
        
        System.out.println("\n----- Fork/Join Framework Example -----");
        forkJoinPoolExample();
        
        System.out.println("\n----- Thread Coordination Examples -----");
        countDownLatchExample();
        cyclicBarrierExample();
        semaphoreExample();
        phaserExample();
        
        System.out.println("\n----- Advanced Producer-Consumer Example -----");
        ExecutorService producerConsumerExecutor = Executors.newFixedThreadPool(2);
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        
        // Producer
        producerConsumerExecutor.submit(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    String message = "Message " + i;
                    blockingQueue.put(message);
                    System.out.println("Produced: " + message);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        // Consumer
        producerConsumerExecutor.submit(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    String message = blockingQueue.take();
                    System.out.println("Consumed: " + message);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        producerConsumerExecutor.shutdown();
        producerConsumerExecutor.awaitTermination(10, TimeUnit.SECONDS);
    }
}