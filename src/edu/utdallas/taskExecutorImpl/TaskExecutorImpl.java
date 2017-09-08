package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

//Implementing Runnable interface to be used by thread

class TaskRunner implements Runnable{
	public BlockingFIFO queue;
	public TaskRunner(BlockingFIFO queue){
		this.queue = queue;
	}
	public void run(){
		while(true){
			try {
				Task newTask = queue.take();
				newTask.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

//Main class

public class TaskExecutorImpl implements TaskExecutor {
	private BlockingFIFO queue;
	public TaskExecutorImpl(int n){			//constructor
		 queue = new BlockingFIFO(100);
		 for(int i=0 ; i<n; i++){			//creating Thread pool
			 Thread t = new Thread(new TaskRunner(queue));
			 t.setName("TaskThread"+i);		//giving unique name for each thread in pool
			 t.start();
		 }
	}
	
	@Override
	public void addTask(Task task)		//adding task to the queue
	{
			try {
				queue.put(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
