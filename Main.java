import java.util.*;
import java.util.concurrent.*;

/**
 * Main class starts the Data Processing System.
 */
public class Main {

    public static void main(String[] args) {

        // Create shared task queue
        SharedTaskQueue queue =
                new SharedTaskQueue();

        // Thread-safe results list
        List<String> results =
                Collections.synchronizedList(
                        new ArrayList<>());

        // Number of worker threads
        int workerCount = 3;

        // Create thread pool
        ExecutorService executor =
                Executors.newFixedThreadPool(
                        workerCount);

        // Start worker threads
        for (int i = 0; i < workerCount; i++) {

            executor.execute(
                    new Worker(
                            queue,
                            results));
        }

        // Add sample tasks
        for (int i = 1; i <= 10; i++) {

            queue.addTask(
                    new Task(
                            i,
                            "Data-" + i));
        }

        // Add termination tasks
        // One for each worker
        for (int i = 0; i < workerCount; i++) {

            queue.addTask(
                    new Task(
                            -1,
                            "STOP"));
        }

        // Shutdown executor
        executor.shutdown();

        try {

            // Wait for all workers to finish
            executor.awaitTermination(
                    1,
                    TimeUnit.MINUTES);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        System.out.println("\nFinal Results:");

        results.forEach(
                System.out::println);
    }
}