
public class Process {

	private int processID;
	private int cpuBurst;
	//private int cpuTime;//
	private int currentCPUburst;//
	private int io;//
	private int currentIOburst;//
	int arrivalTime;
	private int finishTime;
	//private int blockedTime;
	private int turnAroundTime;
	//private int waitingTime;
	private static int ioTime; 
	private int remainingCpuBurst;
	//private String nextState;
	
	private int timeRemaining;
	private int preemptionTime;
	//int burst;
	private String state;
	private String currentState;
	private boolean preempt;

	
	//Constructor to initialize the Process variables
	public Process(){
		preempt=false;
		//blockedTime=0;
		finishTime=0;
		turnAroundTime=0;
		state="unstarted";
		currentState="unstarted";
		turnAroundTime=0;
		finishTime=0;
		ioTime=0;
		preemptionTime=0;
		//nextState="unstarted";
	}
	
	public void SetPreempt(boolean preemptValue){
		this.preempt = preemptValue;
	}
	
	public boolean GetPreempt(){
		return this.preempt;
	}
	public void SetProcessID(int pID){
		this.processID = pID;
	}
	public int GetProcessID(){
		return processID;
	}
	
	public void SetCurrentCPUBurst(int runTime)
	{
		this.currentCPUburst=runTime;
	}
	
	public int GetCurrentCPUBurst()
	{
		return currentCPUburst;
	}
	
	public void SetCurrentIOBurst(int runTime)
	{
		this.currentIOburst=runTime;
	}
	
	public int GetCurrentIOBurst()
	{
		return currentIOburst;
	}
	public void SetRemainingTime(int timeRemain)
	{
		this.timeRemaining=timeRemain;
	}
	
	public int GetRemainingTime()
	{
		return timeRemaining;
	}
	
	public void SetTurnaroundTime(int turn)
	{
		
		this.turnAroundTime=turn;
	}
	
	public int GetTurnAroundTime()
	{
		return turnAroundTime;
	}
	
	public void SetArrivalTime(int A)
	{
		
		this.arrivalTime=A;
	}
	
	public int GetArrivalTime()
	{
		return arrivalTime;
	}
	/*
	public void SetBlockedTime(int block)
	{
		this.blockedTime=block;
	}
	
	public int GetBlockedTime()
	{
		return blockedTime;
	}*/
	/*
	public void setWaitingTime(int wait)
	{
		this.waitingTime=wait;
	}
	
	public int getWaitingTime()
	{
		return waitingTime;
	}
	*/
	public void SetCpuBurst(int cpu)
	{
		
		this.cpuBurst=cpu;
	}
	
	public int GetCpuBurst()
	{
		return cpuBurst;
	}
/*	
	public void SetCpuTime(int cTime)
	{
		
		this.cpuTime=cTime;
		//this.timeRemaining=cTime;
	}
	
	public int GetCpuTime()
	{
		return cpuTime;
	}
	*/
	
	public void SetIo(int ioBurst)
	{
		
		this.io=ioBurst;
	}
	
	public int GetIo()
	{
		return io;
	}
	
	public void SetState(String st)
	{
		this.state=st;
	}
	
	public String GetState()
	{
		return state;
	}
	
	public void SetFinishTime(int fTime) {
		
		this.finishTime=fTime;
		turnAroundTime=finishTime-arrivalTime;
	}
	
	public int GetFinishTime() {
		
		return finishTime;
	}
	
	public void SetCurrentState(String str)
	{
		this.currentState=str;
	}
	
	public String GetCurrentState()
	{
		return currentState;
	}

	public static void SetIotime(int ioTime) {
		Process.ioTime = ioTime;
	}

	public static int GetIotime() {
		return ioTime;
	}

	public void SetPremptionTime(int pTime) {
		this.preemptionTime = pTime;
	}

	public int GetPremptionTime() {
		return preemptionTime;
	}
	
	public void SetRemainingCpuBurst(int remCpuBurst) {
		this.remainingCpuBurst = remCpuBurst;
	}

	public int GetRemainingCpuBurst() {
		return remainingCpuBurst;
	}
/*
	public void SetNextState(String nxtState) {
		this.nextState = nxtState;
	}

	public String GetNextState() {
		return nextState;
	}*/
}
