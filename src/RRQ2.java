import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


public class RRQ2 {
	
	private String fileName;//stores the name of input file
	public static Scanner scan;//scanner for parsing input file
	private FileReader fReader;//file reader for reading input file
	private static int noOfProcesses;//total no. of processes
	private static int cpuCycle;//Keeps track of CPU Cycles
	ArrayList<Integer> arrListProcId=new ArrayList<Integer>();
	
	public RRQ2(String s1){
		fileName=s1;
		cpuCycle=0;
		
		try{
			fReader = new FileReader(fileName);
			scan= new Scanner (fReader);
		}
		catch(FileNotFoundException ex){
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
		else
			return 0;
	
	}
	
	public void ProcessAlgoRoundRobin(){
		//Count the number of processes in the input file
		while(scan.hasNextLine()){
			scan.nextLine();
			noOfProcesses = noOfProcesses + 1;
		}
		
		try{
	
				fReader = new FileReader(fileName); //set the scanner again to the start of the file	
				scan = new Scanner(fReader);}
		catch(FileNotFoundException ex){
				System.exit(1);
		}
		
		ArrayList<Integer> arrList=new ArrayList<Integer>();
		LinkedList<Integer> linkList=new LinkedList<Integer>();
		Process [] objProcess=new Process[noOfProcesses];//array of Process instances
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
			
			objProcess[i] = new Process();
			objProcess[i].SetProcessID(var1);
			objProcess[i].SetCpuBurst(var2);
			objProcess[i].SetIo(var3);
			objProcess[i].SetArrivalTime(var4);
			objProcess[i].SetRemainingTime(var2);
		}
		
		//Fetch Arrival time for each process in the input
		for (int i=0;i<noOfProcesses;i++)
		{
			arrList.add(objProcess[i].GetArrivalTime());
			arrListProcId.add(objProcess[i].GetProcessID());
		}
		//arrListProcId = arrList;
		
		Collections.sort(arrList);//Sorting the arrival times
		//Collections.sort(arrListProcId);
		for (int i=0;i<noOfProcesses;i++)
		{
			for(int j=0;j<noOfProcesses;j++)
			{
				if (Integer.parseInt(arrList.get(i).toString())==objProcess[j].GetArrivalTime() && !linkList.contains(j))
				{
					linkList.add(j);
				}
			}
		}
	
		Process[] obj=new Process[noOfProcesses];
		for (int i=0;i<noOfProcesses;i++)
		{
			int processNum=linkList.get(i);
			
			//sorted objects of process
			obj[i]=objProcess[processNum];
		}
	
		//flag variable will keep a count of processes terminated
		int i=0,u,flag=0,quantum=2;
		int firstInQ;
		String cpuState="free";
		LinkedList<Integer> readyList=new LinkedList<Integer>();
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
			
			
			//Changing state of first element of list from ready to running
			if(readyList.size() > 0){
				
				if(cpuCycle == 10){
					firstInQ = readyList.getLast();
				}
				else{
					firstInQ=readyList.getFirst();
				}
				
				if(obj[firstInQ].GetState().equalsIgnoreCase("ready") && cpuState.equalsIgnoreCase("free"))
				{
					obj[firstInQ].SetState("running");	
					obj[firstInQ].SetCurrentState("running");
					cpuState="busy";
					if(cpuCycle == 10){
						readyList.removeLast();
					}
					else{
						readyList.removeFirst();
					}
					//Increment current CPU Burst
					//obj[firstInQ].SetCurrentCPUBurst(obj[firstInQ].GetCurrentCPUBurst() + 1);
					
					obj[firstInQ].SetRemainingCpuBurst(obj[firstInQ].GetCpuBurst() - obj[firstInQ].GetCurrentCPUBurst());
					if(obj[firstInQ].GetRemainingCpuBurst()==0)
					{
						obj[firstInQ].SetRemainingCpuBurst(obj[firstInQ].GetCpuBurst() - obj[firstInQ].GetCurrentCPUBurst());
					}
					
					if (obj[firstInQ].GetRemainingCpuBurst() > quantum)
					{
						obj[firstInQ].SetPreempt(true);
						obj[firstInQ].SetCurrentCPUBurst(obj[firstInQ].GetCurrentCPUBurst() + 1);
						obj[firstInQ].SetRemainingCpuBurst(quantum - obj[firstInQ].GetCurrentCPUBurst());
						obj[firstInQ].SetRemainingTime(obj[firstInQ].GetCpuBurst()- quantum);
					}
					else
					{
						obj[firstInQ].SetPreempt(false);
						obj[firstInQ].SetCurrentCPUBurst(obj[firstInQ].GetCurrentCPUBurst() + 1);
						
						obj[firstInQ].SetRemainingCpuBurst(obj[firstInQ].GetCpuBurst()-obj[firstInQ].GetCurrentCPUBurst());
					}
					
				  }
				
				for (int j = 0; j < noOfProcesses; j++) {
					if(obj[j].GetCurrentState().equalsIgnoreCase("ready")){
						System.out.print(" "+obj[j].GetProcessID()+":"+obj[j].GetCurrentState());
					}
					else if(obj[j].GetCurrentState().equalsIgnoreCase("running")){
						if(cpuCycle == 0){
							System.out.print(cpuCycle+" "+obj[j].GetProcessID()+":"+obj[j].GetCurrentState());
						}
						else
							System.out.print(" "+obj[j].GetProcessID()+":"+obj[j].GetCurrentState());
					}
					
				}
				
				
				
			 }
			else{
				for (int j = 0; j < noOfProcesses; j++) {
					if(obj[j].GetCurrentState().equalsIgnoreCase("running")){
						
						System.out.print(" "+obj[j].GetProcessID()+":"+obj[j].GetCurrentState());
					}
				}
			}
			
			//Changing state from Running to Block
			for (int j = 0; j < noOfProcesses; j++) {
				int timeRemaining=0;
				if(obj[j].GetState().equalsIgnoreCase("running"))
				{
					if(obj[j].GetCurrentCPUBurst() == quantum && (obj[j].GetCpuBurst()- obj[j].GetCurrentCPUBurst() > 0)){
						if(obj[j].GetIo() - obj[j].GetCurrentIOBurst() > 0){
							obj[j].SetState("blocked");
							obj[j].SetCurrentState("blocked");
							cpuState="free";
							obj[j].SetCurrentIOBurst(obj[j].GetCurrentIOBurst()+1);
						}
						else if(obj[j].GetIo() - obj[j].GetCurrentIOBurst() == 0){
							obj[j].SetState("ready");
							obj[j].SetCurrentState("ready");
							cpuState="free";
							readyList.add(j);
							
						}
						timeRemaining = obj[j].GetCpuBurst() - quantum;
						obj[j].SetRemainingTime(obj[j].GetCpuBurst() - quantum);
					}
					else{
						//int remainingCpuBurst=obj[i].GetCurrentCPUBurst()-1;
						timeRemaining=obj[j].GetCpuBurst()-obj[j].GetCurrentCPUBurst();//remaining execution time
						obj[j].SetRemainingTime(timeRemaining);
					}
					
					if(timeRemaining==0)
					{
						obj[j].SetState("terminated");
						obj[j].SetCurrentState("terminated");
						obj[j].SetFinishTime(cpuCycle);
						obj[j].SetTurnaroundTime(obj[j].GetTurnAroundTime());
						cpuState="free";
						flag++;
					}
					else if (timeRemaining!=0 && (obj[j].GetIo() - obj[j].GetCurrentIOBurst() > 0) && (obj[j].GetCurrentCPUBurst() >= obj[j].GetCpuBurst()/2))//the process has finished its burst and will move to block state
					{
						if(obj[j].GetPreempt()==false)
						{
							obj[j].SetState("blocked");
							obj[j].SetCurrentState("blocked");
							cpuState="free";
							obj[j].SetCurrentIOBurst(obj[j].GetCurrentIOBurst()+1);
						}
						else if(obj[j].GetPreempt()==true && obj[j].GetRemainingCpuBurst() > 0){
							//obj[j].SetCurrentCPUBurst(obj[j].GetCurrentCPUBurst() + 1);
							//obj[j].SetRemainingTime(timeRemaining-1);
						}
						else
						{
							obj[j].SetState("ready");
							obj[j].SetCurrentState("ready");
							System.out.print(" "+obj[j].GetProcessID()+":"+obj[j].GetCurrentState());
							//cpuState="free";
							readyList.add(j);
							obj[j].SetCurrentIOBurst(0);
						}
					}
					else if(timeRemaining!=0 && obj[j].GetIo() ==0 ){
						if(obj[j].GetCurrentCPUBurst() != quantum){
							obj[j].SetCurrentCPUBurst(obj[j].GetCurrentCPUBurst() + 1);
							obj[j].SetRemainingCpuBurst(obj[j].GetCpuBurst()-obj[j].GetCurrentCPUBurst());
						}
						
					}
					
					else if (!readyList.contains(obj[j])){
						obj[j].SetCurrentCPUBurst(obj[j].GetCurrentCPUBurst() + 1);
						obj[j].SetRemainingTime(timeRemaining);
					}
				}
				else if(obj[j].GetState().equalsIgnoreCase("blocked"))//moving from blocked state to ready state once the IO burst has finished
				{
						int remainingIOBurst=obj[j].GetIo()-obj[j].GetCurrentIOBurst();
						obj[j].SetCurrentIOBurst(obj[j].GetCurrentIOBurst() + 1);
						
						if (remainingIOBurst==0)
						{
							obj[j].SetState("ready");
							obj[j].SetCurrentState("ready");
							
							readyList.add(j);					
						}
				}
				else if(obj[j].GetState().equalsIgnoreCase("ready") && cpuState == "free" && readyList.size() == 1){
					obj[j].SetState("running");	
					obj[j].SetCurrentState("running");
					cpuState="busy";
					readyList.removeFirst();
					
					obj[j].SetRemainingCpuBurst(obj[j].GetCpuBurst() - obj[j].GetCurrentCPUBurst());
					if(obj[j].GetRemainingCpuBurst()==0)
					{
						obj[j].SetRemainingCpuBurst(obj[j].GetCpuBurst() - obj[j].GetCurrentCPUBurst());
					}
					
					if (obj[j].GetRemainingCpuBurst() > quantum)
					{
						obj[j].SetPreempt(true);
						obj[j].SetCurrentCPUBurst(obj[j].GetCurrentCPUBurst() + 1);
						obj[j].SetRemainingCpuBurst(quantum - obj[j].GetCurrentCPUBurst());
						obj[j].SetRemainingTime(obj[j].GetCpuBurst()- quantum);
					}
					else
					{
						obj[j].SetPreempt(false);
						obj[j].SetCurrentCPUBurst(obj[j].GetCurrentCPUBurst() + 1);
						obj[j].SetRemainingCpuBurst(obj[j].GetCpuBurst()-obj[j].GetCurrentCPUBurst());
					}
					
				}
				
			}
			
			cpuCycle++;//incrementing the cpu cycle
			
			//displaying the detailed output
			if(str.equalsIgnoreCase("verbose") && flag!=noOfProcesses){
				System.out.print("\n"+cpuCycle);
			}
			for(i=0;i<noOfProcesses;i++)
			{
				if(obj[i].GetCurrentState().equalsIgnoreCase("blocked")){
						System.out.print(" "+obj[i].GetProcessID()+":"+obj[i].GetCurrentState());
					}
			
			}
			
			
		}//end of while loop
		
		this.Output(obj);//calls the output function for displaying the required output
		
	} //end of method
	
	
	private void Output(Process[] p){
		int totalFinish=0;
		double totalCpuTime=0;
		double cpuUtilization=0;
		double result = 0;
		
		for(int i=0;i<noOfProcesses;i++)
		{
			if(p[i].GetFinishTime()>totalFinish)
			{
			  totalFinish=p[i].GetFinishTime();
			}
			
			totalCpuTime+=p[i].GetCpuBurst();
			
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
}//end of class
