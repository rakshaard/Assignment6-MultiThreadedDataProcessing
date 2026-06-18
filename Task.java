/**
 * Task class represents a single unit of work.
 * Each task has an ID and some data to process.
 */
public class Task {

    private int id;
    private String data;

    /**
     * Constructor to initialize task information.
     *
     * @param id Unique task identifier
     * @param data Task data
     */
    public Task(int id, String data) {
        this.id = id;
        this.data = data;
    }

    /**
     * Returns task ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns task data.
     */
    public String getData() {
        return data;
    }
}