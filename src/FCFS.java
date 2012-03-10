import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;



public class FCFS {
	private String fileName;//Input FileName
	public static Scanner scan;//scanner for parsing input file
	private FileReader fReader;//file reader for reading input file
	private static int noOfProcesses;//total no. of processes
	private static int cpuCycle;//Keeps track of CPU Cycles
	ArrayList<Integer> arrListProcId=new ArrayList<Integer>();
	
	public FCFS(String s){
		fileName=s;
		cpuCycle=0;
		
		try{
			fReader = new FileReader(fileName);
			scan = new Scanner(fReader);
			noOfProcesses =0;
		}
		catch (FileNotFoundException ex) {
        	System.out.println(this.fileName+" not Found.Program will exit...");
        	System.exit(1);
        }
		catch(InputMismatchException ex){
			System.out.println("Incorrect format of Input File. Program will exit ...");
        	System.exit(1);
		}
	}
	
	public int GetInput(){
		if(scan.hasNextInt()){
			
			return scan.nextInt();
		}
		else{
			
			return 0;
		}
	}
	
	public void AlgoFirstComeFirstServe(){
		//Count the number of processes in the input file
		
		while(scan.hasNextLine()){
			scan.nextLine();
			noOfProcesses = noOfProcesses + 1;
		}
		
		ArrayList<Integer> arrList=new ArrayList<Integer>();
		
		LinkedList<Integer> linkList=new LinkedList<Integer>();
		Process [] instanceProcess=new Process[noOfProcesses];//array of Process instances
		try{
			
		//set the scanner again to the start of the file	
			fReader = new FileReader(fileName);
			scan = new Scanner(fReader);}
		catch(FileNotFoundException ex){
			System.exit(1);
		}
		//Get the relevant details of each process and set them
		for (int i = 0; i < noOfProcesses; i++) {
			int var1=0, var2=0,var3=0,var4=0;
			if(scan.hasNextInt()){
				 var1 = scan.nextInt();
			}
			if(scan.hasNextInt()){
				 var2 = scan.nextInt();
			}
			if(scan.hasNextInt()){
				 var3 = scan.nextInt();
			}
			if(scan.hasNextInt()){
				 var4 = scan.nextInt();
			}
			
			instanceProcess[i] = new Process();
			instanceProcess[i].SetProcessID(var1);
			instanceProcess[i].SetCpuBurst(var2);
			instanceProcess[i].SetIo(var3);
			instanceProcess[i].SetArrivalTime(var4);
			instanceProcess[i].SetRemainingTime(var2);
		}
		
		//Fetch Arrival time for each process in the input
		for (int i=0;i<noOfProcesses;i++)
		{
			arrList.add(instanceProcess[i].GetArrivalTime());
			arrListProcId.add(instanceProcess[i].GetProcessID());
			//arrListProcId.add(instanceProcess[i].GetTurnAroundTime());
		}
		//arrListProcId = arrList;
		
		Collections.sort(arrList);//Sorting the arrival times
		//Collections.sort(arrListProcId);
		for (int i=0;i<noOfProcesses;i++)
		{
			for(int j=0;j<noOfProcesses;j++)
			{
				if (Integer.parseInt(arrList.get(i).toString())==instanceProcess[j].GetArrivalTime() && !linkList.contains(j))
				{
					linkList.add(j);
				}
			}
		}
		//Displaying the sorted input based on arrival time
		
		Process[] obj=new Process[noOfProcesses];
		for (int i=0;i<noOfProcesses;i++)
		{
			int processNum= linkList.get(i);
					
			//sorted objects of process
			obj[i]=instanceProcess[processNum];
		}
		
		System.out.println("The Scheduling Algorithm being used is 'First Come First Served'");
		
		
		int i=0,flag=0;//flag variable will keep a count of processes terminated
		int firstInQ;//the first element in queue
		int det=0;
		String cpuState="free";//initial CPU state is set to free
		LinkedList<Integer> readyList=new LinkedList<Integer>();
		LinkedList<Integer> runningList = new LinkedList<Integer>();
		String str=Scheduler.flag;
		
		
		while(flag!=noOfProcesses){
			
			/******************For block for finding elements that are ready**************/
			//Changing state from un-started to ready
			for (int k = 0; k < noOfProcesses; k++) {
		
				if(obj[k].GetArrivalTime()==cpuCycle && obj[k].GetState().equalsIgnoreCase("unstarted"))
				{
						obj[k].SetState("ready");
						readyList.add(k);
						obj[k].SetCurrentState("ready");
				}
			}
			/***********************End of For block for *********************************/
			
			/************************Accessing elements in Ready list*******************/
			//Accessing elements of ready List
			if(readyList.size()>0)
			{
				
				firstInQ=readyList.getFirst();
				
				if(obj[firstInQ].GetState().equalsIgnoreCase("ready") && cpuState.equalsIgnoreCase("free"))
				{
					obj[firstInQ].SetState("running"); //changing the state of first element of readyList from ready to running
					obj[firstInQ].SetCurrentState("running");
					runningList.add(firstInQ);
					cpuState="busy";
					readyList.removeFirst();//removing element from list
					
					//Increment current CPU Burst
					obj[firstInQ].SetCurrentCPUBurst(obj[firstInQ].GetCurrentCPUBurst() + 1);
					
				}
				
				else {
					
					if(readyList.size() >= 1){
						for (int j = 1 ; j <= readyList.size(); j++) {
							if(obj[firstInQ].GetState().equalsIgnoreCase("ready") && cpuState.equalsIgnoreCase("busy")){
							
							}
						}
					}
					
				}
				
								
			}
			/***************************End of accessing Ready list*******************/
			int lpCounter = 0;
			for(i=0;i<noOfProcesses;i++)
			{ 
				if((obj[i].GetCurrentState().equalsIgnoreCase("ready") || obj[i].GetCurrentState().equalsIgnoreCase("running") ) && str.equals("verbose")){
					for (int j = 0; j < noOfProcesses; j++) {
						if(obj[j].GetCurrentState().equalsIgnoreCase("blocked")){
							det=1;
						}
						
					}
					
					if(det != 1){
						if(lpCounter == 0){
							System.out.print(cpuCycle+" "+obj[i].GetProcessID()+":"+obj[i].GetCurrentState());
							lpCounter++;
						}
						else{
							System.out.print(" "+obj[i].GetProcessID()+":"+obj[i].GetCurrentState());
						}
						
					}
					else{
						det=0;
						System.out.print(" "+obj[i].GetProcessID()+":"+obj[i].GetCurrentState());
					}
				
					
				}		
			}
			/****************For loop for operations on ready elements*****************/
			//Changing state from Running to Block
			for(int j=0;j<noOfProcesses;j++)
			{
				if(obj[j].GetState().equalsIgnoreCase("running"))
				{		
					
					int timeRemaining=obj[j].GetRemainingTime()-1;//remaining execution time
					obj[j].SetRemainingTime(timeRemaining);
					
					
					if(timeRemaining==0)
					{
						obj[j].SetState("terminated");
						obj[j].SetCurrentState("terminated");
						obj[j].SetFinishTime(cpuCycle);
						
						obj[j].SetTurnaroundTime(obj[j].GetTurnAroundTime());
						
						cpuState="free";
						flag++;
					}
					else if (timeRemaining!=0 && (obj[j].GetIo()-obj[j].GetCurrentIOBurst()) > 0 && (obj[j].GetCurrentCPUBurst() >= (obj[j].GetCpuBurst()/2)))//the process has finished its burst and will move to block state
					{
						obj[j].SetState("blocked");
						obj[j].SetCurrentState("blocked");
						cpuState="free";
						
						
						//Set current IO Burst
						obj[j].SetCurrentIOBurst(obj[j].GetCurrentIOBurst()+1);
						
						if(readyList.size()>0){
							firstInQ=readyList.getFirst();
							if(obj[firstInQ].GetState().equalsIgnoreCase("ready") && cpuState.equalsIgnoreCase("free"))
							{
								
								obj[firstInQ].SetState("running"); //changing the state of first element of readyList from ready to running
								obj[firstInQ].SetCurrentState("running");
								cpuState="busy";
								readyList.removeFirst();//removing element from list
								
							}	
						}
						
					 }
					else if(timeRemaining!=0  && readyList.size() > 0 && (obj[readyList.getFirst()].arrivalTime > obj[j].arrivalTime)){
						obj[j].SetCurrentCPUBurst(obj[j].GetCurrentCPUBurst() + 1);
						obj[j].SetRemainingTime(timeRemaining);
					}
					else if (timeRemaining!=0 && (obj[j].GetIo()-obj[j].GetCurrentIOBurst()) == 0 && readyList.size() > 0){
						firstInQ=readyList.getFirst();
						
						obj[j].SetState("ready");
						obj[j].SetCurrentState("ready");
						cpuState="free";
						readyList.add(j);
						
						if(obj[firstInQ].GetState().equalsIgnoreCase("ready") && cpuState.equalsIgnoreCase("free"))
						{
							obj[firstInQ].SetState("running"); //changing the state of first element of readyList from ready to running
							obj[firstInQ].SetCurrentState("running");
							cpuState="busy";
							readyList.removeFirst();//removing element from list
							
						}
					}
					else
					{
						 obj[j].SetCurrentCPUBurst(obj[j].GetCurrentCPUBurst() + 1);
						 obj[j].SetRemainingTime(timeRemaining);
					}
				}
				else if(obj[j].GetState().equalsIgnoreCase("blocked"))
				{	
					
					int remainingIOBurst=obj[j].GetIo()-obj[j].GetCurrentIOBurst();
					obj[j].SetCurrentIOBurst(obj[j].GetCurrentIOBurst()+1);
					if (remainingIOBurst==0)
					{
						if(readyList.size() == 1){
							firstInQ=readyList.getFirst();
							if(obj[j].arrivalTime < obj[firstInQ].arrivalTime){
								
								obj[j].SetState("running");
								obj[j].SetCurrentState("running");
								cpuState="busy";
							
							}
						}
						else{
							obj[j].SetState("ready");
							obj[j].SetCurrentState("ready");
							readyList.add(j);
						}
					}
						
				}
				else if(noOfProcesses == flag+readyList.size()){
					firstInQ=readyList.getFirst();
								
					obj[firstInQ].SetState("running");
					obj[firstInQ].SetCurrentState("running");
					
					cpuState = "busy";
					
					obj[firstInQ].SetCurrentCPUBurst(obj[firstInQ].GetCurrentCPUBurst() + 1);
					obj[firstInQ].SetRemainingTime(obj[firstInQ].GetCpuBurst() - obj[firstInQ].GetCurrentCPUBurst()+1);
					readyList.removeFirst();
					
					
				}
				
				}//End of for loop
			/**************************************end of For loop**************************/
			
			
			cpuCycle++;//incrementing the cpu cycle
			System.out.println();
			int lpCntr=0;
			/**********************For loop for cycle increment************************/
			for(i=0;i<noOfProcesses;i++)
			{
				if((obj[i].GetCurrentState().equalsIgnoreCase("blocked")) && str.equals("verbose")){
					if(lpCntr == 0){
						System.out.print(cpuCycle+" "+obj[i].GetProcessID()+":"+obj[i].GetCurrentState());
						lpCntr++;
					}
					else{
						System.out.print(" "+obj[i].GetProcessID()+":"+obj[i].GetCurrentState());
					}
					
					
				}
					
						
			
			}
			/*********************************End of For loop for cycle increment***********/
			
			
		
		} //End of While loop
		
		this.output(obj);//calls the output function for displaying the required output
		 
	}
	
	/**
	 * This function displays the required output on the console
	 * @param obj an object of Process class
	 */
	private void output(Process[] p){
		int totalFinish=0;
		double totalCpuTime=0;
		double cpuUtilization=0;
		double result=0;
		
		for(int i=0;i<noOfProcesses;i++)
		{
			if(p[i].GetFinishTime()>totalFinish)
			{
			  totalFinish=p[i].GetFinishTime();
			}
			
			totalCpuTime+=p[i].GetCpuBurst();
			//totalCpuTime = totalCpuTime;
			
			
		}
		result=(totalCpuTime-0.5)/totalFinish;
		cpuUtilization = result * 100;
		cpuUtilization = Math.round(cpuUtilization);
		cpuUtilization = cpuUtilization/100;
		
		System.out.println();
		System.out.println();
		System.out.println("Finishing Time: "+totalFinish);
		System.out.println("CPU Utilization: "+cpuUtilization);
		
		for (int i = 0; i < arrListProcId.size(); i++) {
			int tempProcId = arrListProcId.get(i);
			int tempTurnAround = 0;
			for (int j = 0; j < noOfProcesses; j++) {
				if(p[j].GetProcessID() == tempProcId){
					tempTurnAround = p[j].GetTurnAroundTime();
					break;
				}
			}
			System.out.println("Turnaround Process "+tempProcId+": "+(tempTurnAround+1));
		}
		
	}
}
