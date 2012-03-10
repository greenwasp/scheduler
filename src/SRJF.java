import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


public class SRJF {
	private String fileName;//stores the name of input file
	public static Scanner scan;//scanner for parsing input file
	private FileReader fReader;//file reader for reading input file
	private static int noOfProcesses;//total no. of processes
	private static int cpuCycle;//Keeps track of CPU Cycles
	ArrayList<Integer> arrListProcId=new ArrayList<Integer>();
	
	public SRJF(String s1){
		fileName=s1;
		cpuCycle=0;
		
		try {
			
			fReader = new FileReader(fileName);
			scan= new Scanner (fReader);
			
		} catch (FileNotFoundException e) {
        	System.out.println(this.fileName+" Not Found.Program will exit...");
        	System.exit(1);
        }
        catch (InputMismatchException e)
        {
        	System.out.println("Incorrect format of Input File.Program will exit ...");
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
	
	public void ProcessShortestRunningJobFirst(){
		//Count the number of processes in the input file
		while(scan.hasNextLine()){
			scan.nextLine();
			noOfProcesses = noOfProcesses + 1;
		}
		try{
		//set the scanner again to the start of the file	
			fReader = new FileReader(fileName);
			scan = new Scanner(fReader);}
		catch(FileNotFoundException ex){
				System.exit(1);
			}
		ArrayList<Integer> arraylist=new ArrayList<Integer>();
		LinkedList<Integer> linkList=new LinkedList<Integer>();
		Process [] objProcess=new Process[noOfProcesses];//array of Process instances
		Process [] obj=new Process[noOfProcesses];
		
		for (int i=0;i<noOfProcesses;i++)
		{
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
			objProcess[i]=new Process();
			objProcess[i].SetProcessID(var1);
			objProcess[i].SetCpuBurst(var2);
			objProcess[i].SetIo(var3);
			objProcess[i].SetArrivalTime(var4);
			objProcess[i].SetRemainingTime(var2);
			
		}
		for (int i=0;i<noOfProcesses;i++)
		{
			arraylist.add(objProcess[i].GetArrivalTime());
			arrListProcId.add(objProcess[i].GetProcessID());
		}
		Collections.sort(arraylist);//Sorting the arrival times
		for (int i=0;i<noOfProcesses;i++)
		{
			for(int j=0;j<noOfProcesses;j++)
			{
				if ( Integer.parseInt(arraylist.get(i).toString())==objProcess[j].GetArrivalTime() && !linkList.contains(j))
				{
					linkList.add(j);
				}
			}
		}
		//Displaying the sorted input based on arrival time
		for (int i=0;i<noOfProcesses;i++)
		{
			int processNum=linkList.get(i);
			obj[i]=objProcess[processNum];
			//sorted objects of process
		}
		//System.out.println("\nThe scheduling algorithm used was Shortest Job First");
		
		
		//flag variable will keep a count of processes terminated
		int i=0,u,flag=0;
		int firstInQ;
		String cpuState="free";
		LinkedList<Integer> readyList=new LinkedList<Integer>();
		cpuCycle=0;
		//main.flag is checked for displaying the detailed output
		String str=Scheduler.flag;
		
		while(flag != noOfProcesses){
			/********************************************************************/
			/***********Changing state from un-started to ready*****************/
			/********************************************************************/
			for(i=0;i<noOfProcesses;i++)
			{
				if(obj[i].GetArrivalTime()==cpuCycle && obj[i].GetState().equalsIgnoreCase("unstarted"))
				{
					obj[i].SetState("ready");
					obj[i].SetCurrentState("ready");
					readyList.add(i);
				}
			}
			/********************************************************************/
			/*changing the state of first element of readyList from ready to running*/
			/********************************************************************/
			if(readyList.size()> 0)
			{
				firstInQ=readyList.getFirst();
				int initializeProNum=firstInQ;
				
				for(int j=0;j<readyList.size();j++)
				{
					if(obj[readyList.get(j)].GetRemainingTime()<obj[initializeProNum].GetRemainingTime() && readyList.get(j)!=initializeProNum)
					{
						initializeProNum=readyList.get(j);//shortest job
					}
				}
				if(obj[initializeProNum].GetState().equalsIgnoreCase("ready") && cpuState.equalsIgnoreCase("free"))
				{
					obj[initializeProNum].SetState("running");
					obj[initializeProNum].SetCurrentState("running");
					cpuState="busy";
					for (int j=0;j<readyList.size();j++)
					{
						if(readyList.get(j)==initializeProNum)
						{
							readyList.remove(j);
						}
					}
					obj[initializeProNum].SetCurrentCPUBurst(obj[initializeProNum].GetCurrentCPUBurst() + 1);
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
				//System.out.print(cpuCycle+" "+obj[initializeProNum].GetProcessID()+":"+ obj[initializeProNum].GetCurrentState());
			}
			else{
				for (int j = 0; j < noOfProcesses; j++) {
					if(obj[j].GetCurrentState().equalsIgnoreCase("running")){
						
						System.out.print(" "+obj[j].GetProcessID()+":"+obj[j].GetCurrentState());
					}
				}
			}
			
			//Changing state from Running to Block
			for(i=0;i<noOfProcesses;i++)
			{
				if(obj[i].GetState().equalsIgnoreCase("running"))
				{
					int remainingCpuBurst= obj[i].GetCpuBurst() - obj[i].GetCurrentCPUBurst();//cpu burst remaining
					int timeRemaining=obj[i].GetRemainingTime()-1;//remaining execution time
					obj[i].SetRemainingTime(timeRemaining);
					
					if(remainingCpuBurst==0)
					{
						obj[i].SetState("terminated");
						obj[i].SetCurrentState("terminated");
						obj[i].SetFinishTime(cpuCycle);
						obj[i].SetTurnaroundTime(obj[i].GetTurnAroundTime());
						cpuState="free";
						flag++;
					}
					else if (timeRemaining!=0 && (obj[i].GetIo()-obj[i].GetCurrentIOBurst()) > 0 && (obj[i].GetCurrentCPUBurst() >= (obj[i].GetCpuBurst()/2)))//the process has finished its burst and will move to block state
					{
						obj[i].SetState("blocked");
						obj[i].SetCurrentState("blocked");
						cpuState="free";
						obj[i].SetCurrentIOBurst(obj[i].GetCurrentIOBurst() + 1);
					}
					else
					{
						obj[i].SetCurrentCPUBurst(obj[i].GetCurrentCPUBurst() + 1);
						obj[i].SetRemainingTime(timeRemaining);
					}
				}
				else 
				{	
					if(obj[i].GetState().equalsIgnoreCase("blocked"))//moving from blocked state to ready state once the IO burst has finished
					{
						int remainingIOBurst=obj[i].GetIo()-obj[i].GetCurrentIOBurst();
						obj[i].SetCurrentIOBurst(obj[i].GetCurrentIOBurst()+1);
						
						if (remainingIOBurst==0)
						{
							obj[i].SetState("ready");
							obj[i].SetCurrentState("ready");
							readyList.add(i);
							
							if(cpuState=="busy"){
								firstInQ = readyList.getFirst();
								Process tempObj = new Process();
								for (int j = 0; j < noOfProcesses; j++) {
									if(obj[j].GetCurrentState()=="running"){
										tempObj = obj[j];
										if(obj[i].GetRemainingTime()< obj[j].GetRemainingTime())
										{
											cpuState = "busy";
											obj[i].SetState("running");
											obj[i].SetCurrentState("running");
											readyList.remove(i);
											obj[i].SetCurrentCPUBurst(obj[i].GetCurrentCPUBurst() + 1);
											obj[j].SetState("ready");
											obj[j].SetCurrentState("ready");
											readyList.add(j);
											
										}
									}
								}
								
							}
						
						}
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
		
		Output(obj);//calls the output function for displaying the required output	
		
	}
	
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
		for (int i = 0; i < noOfProcesses; i++) {
			int tempProcId = arrListProcId.get(i);
			int tempTurnAround = 0;
			for (int j = 0; j < arrListProcId.size(); j++) {
				
				if(p[j].GetProcessID() == tempProcId){
					tempTurnAround = p[j].GetTurnAroundTime();
					break;
				}
			}
			System.out.println("Turnaround Process "+tempProcId+": "+(tempTurnAround+1));
		}
		
	}
	
}
