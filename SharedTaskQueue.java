import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * SharedTaskQueue provides a thread-safe queue
 * that multiple worker threads can access safely.
 */
public class SharedTaskQueue {

    // Thread-safe queue implementation
    private BlockingQueue<Task> queue =
            new LinkedBlockingQueue<>();

    /**
     * Add a task to the queue.
     */
    public void addTask(Task task) {
        queue.add(task);
    }

    /**
     * Retrieve a task from the queue.
     * If the queue is empty, the thread waits.
      */
    public Task getTask()
            throws InterruptedException {

        return queue.take();
    }
}