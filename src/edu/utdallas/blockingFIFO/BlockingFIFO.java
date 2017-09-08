package edu.utdallas.blockingFIFO;
import edu.utdallas.taskExecutor.Task;

public class BlockingFIFO {
	Task[] queue;
	Task out;
	private Object notFull,notEmpty;
	private int start=0,end=0,size=0;
	public BlockingFIFO(int n){ 		//constructor
		queue = new Task[n];			//initializing maximum queue length
		notFull = new Object();			
		notEmpty = new Object();
	}
	public  void put(Task item) throws Exception{
		while(queue.length==size){		//if queue is full thread is blocked 
			synchronized(notFull){
				notFull.wait();
			}
		}
		synchronized(this){
			queue[end]=item;
			end = (end+1)%queue.length;
			size++;
			synchronized(notEmpty){	
				notEmpty.notify();
			}
		}
	}
	public Task take() throws Exception{
		while(size==0){				//if queue is empty thread is blocked
			synchronized(notEmpty){
				notEmpty.wait();
			}
		}
		synchronized(this){
			out = queue[start];
			start =(start+1)%queue.length;
			size--;
			synchronized(notFull){
				notFull.notify();
			}
			return out;
		}
	}
}
