package main

import (
	"fmt"
	"sync"
	"time"
)

// Task represents a unit of work.
type Task struct {
	ID   int
	Data string
}

/*
Worker function processes tasks from the channel.

id      -> Worker identifier
tasks   -> Shared task channel
results -> Shared results slice
mutex   -> Protects shared results
wg      -> WaitGroup for synchronization
*/
func worker(
	id int,
	tasks <-chan Task,
	results *[]string,
	mutex *sync.Mutex,
	wg *sync.WaitGroup) {

	// Mark worker completion
	defer wg.Done()

	fmt.Printf(
		"Worker %d started\n",
		id)

	// Receives tasks until channel closes
	for task := range tasks {

		
		time.Sleep(time.Second)

		
		result :=
			fmt.Sprintf(
				"Processed Task %d",
				task.ID)

		// Lock shared resource
		mutex.Lock()

		// Save result safely
		*results =
			append(
				*results,
				result)

		mutex.Unlock()

		fmt.Println(result)
	}

	fmt.Printf(
		"Worker %d completed\n",
		id)
}

func main() {

	// WaitGroup tracks active goroutines
	var wg sync.WaitGroup

	// Buffered channel acts as task queue
	tasks :=
		make(chan Task, 10)

	// Shared results storage
	results :=
		[]string{}

	// Mutex protects results slice
	var mutex sync.Mutex

	
	for i := 1; i <= 3; i++ {

		wg.Add(1)

		go worker(
			i,
			tasks,
			&results,
			&mutex,
			&wg)
	}

	// Generate tasks
	for i := 1; i <= 10; i++ {

		tasks <- Task{
			ID: i,
			Data: fmt.Sprintf(
				"Data-%d",
				i),
		}
	}

	// Close channel when no more tasks exist
	close(tasks)

	// Wait for all workers to finish
	wg.Wait()

	fmt.Println("\nFinal Results:")

	// Display processed results
	for _, result :=
		range results {

		fmt.Println(result)
	}
}