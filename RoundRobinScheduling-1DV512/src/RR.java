/*
 * File:	RR.java
 * Course: 	Operating Systems
 * Code: 	1DV512
 * Author: 	Suejb Memeti
 * Date: 	November, 2017
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class RR{

	// The list of processes to be scheduled
	public ArrayList<Process> processes;

	// the quantum time - which indicates the maximum allowable time a process can run once it is scheduled
	int tq;

	// keeps track of which process should be executed next
	public Queue<Process> schedulingQueue;
	

	// Class constructor
	public RR(ArrayList<Process> processes, int tq) {
		schedulingQueue = new LinkedList<Process>();
		this.processes = processes;
		this.tq = tq;
		}

	public void run() {
		/*
		 * This is where you put your code
		 * hints:
		 * 1. do not forget to sort the processes by arrival time
		 * 2. think about CPU idle times
		 */
		Process process = null; //initializing process as null
		int completedTime = 0;
		
		sortProcesses(processes); //sorting using arrival time
		
		while (processes.isEmpty() == false) //runs while process list has processes
		{		
			Iterator<Process> it; //iterator to iterate through the list of processes
			
			for (it = processes.iterator(); it.hasNext();) 
			{
				Process pr = it.next(); //initializing new element pr process as next while iterating
				
				if(completedTime >= pr.getArrivalTime()) 
					/*if the CT is greater than or equal to arrival time
					 *  this is the base case where arrival time is 0 (since CT is 0 to begin with)
					 */
				{
					schedulingQueue.add(pr); //add process to scheduling queue
					it.remove(); 
					/*cleans up the list and the iterator for further use (instead of using list.remove()
					 * to remove element since it still keeps it in the iterator
					 */
				}
			}
			
			if (process != null && process.getRemainingBurstTime() > 0)
				/*second case where the burst time of the process is greater than 0, it should be added to the queue
				 *  the process != null helps us get rid of NullPointerException
				 */
				
			{
				schedulingQueue.add(process);
			}
			
			process = schedulingQueue.poll(); 
			//.poll allows us to return and remove the head of the queue and if queue is empty, returns null
			
			if (process == null) //if the process is null then the completed time is incremented, this takes into account idle cpu times
			{
				completedTime++;
				continue;
			}
			else
			{
			int burstTime = process.getRemainingBurstTime(); 
					
			int active = burstTime;
			
			completedTime = completedTime + active;
			
			process.setRemainingBurstTime(burstTime - active); //get the remaining burst time for a process
			
			if (process.getRemainingBurstTime() == 0) 
				/*at completion of the process, set completed time to what is calculated
				 * set turn around time by subtracting arrival time from completed time and
				 * set waiting time by subtracting burst time from turnaround time
				 */
			{	
				process.setCompletedTime(completedTime);
				process.setTurnaroundTime(process.getCompletedTime() - process.getArrivalTime());
				process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
			}
			}
			printGanttChart();
		}
		}
	
	

	public void printProcesses() {
		// TODO Print the list of processes in form of a table here
		System.out.println(" __________________________________________________________________________________________");
		System.out.println("| Process Id | Arrival Time | Burst Time | Completed Time | Turnaround Time | Waiting Time |");
		System.out.println("|____________|______________|____________|________________|_________________|______________|");

		for (Process process : processes) {
			System.out.printf("|%7s     |%9s     |%7s     |%11s     |%12s     |%9s     |\n", 
					process.getProcessId(),process.getArrivalTime(), process.getBurstTime(), 
					process.getCompletedTime(), process.getTurnaroundTime(), process.getWaitingTime());
			System.out.println("|____________|______________|____________|________________|_________________|______________|");
		}
	}

	public void printGanttChart(){
		// TODO Print the demonstration of the scheduling algorithm using Gantt Chart
	
		}

		
	private void sortProcesses(ArrayList<Process> process)
	{
		process.sort((Process p1, Process p2) -> (p1.getArrivalTime() - p2.getArrivalTime()));
	}
}
