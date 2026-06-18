import java.util.List;
import java.util.logging.Logger;

/**
 * Worker class processes tasks from the shared queue.
 * Each worker runs as a separate thread.
 */
public class Worker implements Runnable {

    // Logger for tracking execution
    private static final Logger logger =
            Logger.getLogger(Worker.class.getName());

    private SharedTaskQueue queue;
    private List<String> results;

    public Worker(
            SharedTaskQueue queue,
            List<String> results) {

        this.queue = queue;
        this.results = results;
    }

    /**
     * Main worker execution logic.
     */
    @Override
    public void run() {

        logger.info(
                Thread.currentThread().getName()
                        + " started");

        try {

            while (true) {

                // Retrieve task from queue
                Task task = queue.getTask();

                // Stop worker if termination signal received
                if (task.getId() == -1) {
                    break;
                }

                // Simulate processing delay
                Thread.sleep(1000);

                // Create processed result
                String result =
                        "Processed Task "
                                + task.getId();

                // Synchronize access to shared results list
                synchronized (results) {
                    results.add(result);
                }

                logger.info(result);
            }

        } catch (InterruptedException e) {

            logger.severe(
                    "Worker interrupted: "
                            + e.getMessage());
        }

        logger.info(
                Thread.currentThread().getName()
                        + " completed");
    }
}