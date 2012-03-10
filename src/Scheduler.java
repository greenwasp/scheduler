import java.util.Scanner;


public class Scheduler {
	//this flag will be used to select the type of output to be displayed
	public static String flag;
	
	
	public static void main(String args[]){
		try {
			
			/*args = new String[2];
			args[0]="scheduler_in1.txt";
			args[1]= "verbose";*/
			flag=args[1];
			
		} 
		catch(ArrayIndexOutOfBoundsException ex){
			flag="";
			System.out.println("The details of exception are: "+ex);
			System.out.println("Program will exit..");
			System.exit(1);
		}
		catch (Exception ex) {
			flag="";
			System.out.println("Program will exit..");
			System.exit(1);
			
		}
			FCFS objFCFS = new FCFS(args[0]);
			RRQ2 objRRQ2 = new RRQ2(args[0]);
			SRJF objSRJF = new SRJF(args[0]);
			Scanner scan = new Scanner(System.in);
			String usrInput; //variable to take user input after output of each algorithm
		
		
		//Process Input using First Come First Serve Algorithm
		objFCFS.AlgoFirstComeFirstServe();
		
		
		//Process Input using Round Robin Algorithm with Quantum 2
		System.out.print("\nOutput of Round Robin is next .Press a key to continue... \n");
		usrInput = scan.nextLine();
		objRRQ2.ProcessAlgoRoundRobin();
		
		System.out.print("\nOutput of Shortest Running Job First is next .Press a key to continue... \n");
		usrInput = scan.nextLine();
		objSRJF.ProcessShortestRunningJobFirst();
		
		
	}

}
