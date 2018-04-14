import java.util.Random;

public class Philosopher implements Runnable {

	private int id;
	
	private final ChopStick leftChopStick;
	private final ChopStick rightChopStick;
	private States state;
	public volatile boolean philosophersAreEating = false;

	private Random randomGenerator = new Random();
	
	private int numberOfEatingTurns = 0;
	private int numberOfThinkingTurns = 0;
	private int numberOfHungryTurns = 0;

	private double thinkingTime = 0;
	private double eatingTime = 0;
	private double hungryTime = 0;
	 
	
	public enum States
	{
		EATING,
		THINKING,
		HUNGRY
	}
	
	public Philosopher(int id, ChopStick leftChopStick, ChopStick rightChopStick, int seed) {
		this.id = id;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
		
		/*
		 * set the seed for this philosopher. To differentiate the seed from the other philosophers, we add the philosopher id to the seed.
		 * the seed makes sure that the random numbers are the same every time the application is executed
		 * the random number is not the same between multiple calls within the same program execution 
		 */
		
		randomGenerator.setSeed(id+seed);
	}
	public int getId() {
		return id;
	}
	

	public double getAverageThinkingTime() {
		/* TODO
		 * Return the average thinking time
		 * Add comprehensive comments to explain your implementation
		 */
		return thinkingTime / numberOfThinkingTurns;
	}

	public double getAverageEatingTime() {
		/* TODO
		 * Return the average eating time
		 * Add comprehensive comments to explain your implementation
		 */
		return eatingTime / numberOfEatingTurns;
	}

	public double getAverageHungryTime() {
		/* TODO
		 * Return the average hungry time
		 * Add comprehensive comments to explain your implementation
		 */
		return hungryTime / numberOfHungryTurns;
	}
	
	public int getNumberOfThinkingTurns() {
		return numberOfThinkingTurns;
	}
	
	public int getNumberOfEatingTurns() {
		return numberOfEatingTurns;
	}
	
	public int getNumberOfHungryTurns() 
	{
		return numberOfHungryTurns;
	}

	public double getTotalThinkingTime() 
	{
		
		if(thinkingTime == 0) 
			{
				return 0;
			}
		else
		return thinkingTime;
	}

	public double getTotalEatingTime() 
	{
		if(eatingTime == 0) 
		{
			return 0;
		}
	else
		return eatingTime;
	}

	public double getTotalHungryTime() 
	{
		if(hungryTime == 0)
		{
			return 0;
		}
		else
		return hungryTime;
	}
	
	
	@Override
	public void run() {
		/* TODO
		 * Think,
		 * Hungry,
		 * Eat,
		 * Repeat until thread is interrupted
		 * Add comprehensive comments to explain your implementation, including deadlock prevention/detection
		 */
		try 
		{
			while (!philosophersAreEating) //while philosophers arent eating
			{	
				think(); //initial think state
				
				long startHungryTimer = System.currentTimeMillis(); //hungry timer starts
				long endHungryTimer;
		    	state = States.HUNGRY; //become hungry
		    	System.out.println("Philosopher " + getId() + " is " + state);
		    	
				pickUpLeftChopstick(); 
				pickUpRightChopstick();
				System.out.println("Philosopher " + getId() + " has picked up both ChopSticks."); //after both chopsticks are picked up, they can start eating
				
				eat();
				
				endHungryTimer = System.currentTimeMillis(); //finish hunger timer after theyve eaten
				this.hungryTime += endHungryTimer - startHungryTimer;
				numberOfHungryTurns++;
				
				putDownChopsticks(); //put down the chopsticks
			}
		}				
			catch (InterruptedException ie)
			{
				ie.printStackTrace();
			}
	}
	

    private void think() throws InterruptedException 
    //method to simulate thinking at a random generated time span
    {
        state = States.THINKING;
        System.out.println("Philosopher " + getId() + " is " + state);//prints out what state the philosopher is in
        int sleepTime = randomGenerator.nextInt(1000 - 0 + 1) + 0; //random generator between 0-1000
        
        this.thinkingTime += sleepTime;
        
        numberOfThinkingTurns++;
        
        try
    	{
    		
    		Thread.sleep(sleepTime);
    	}
    	catch (InterruptedException ie)
    	{
    		ie.printStackTrace();
    	}    
    }
    
    
    private void eat() throws InterruptedException
  //method to simulate eating at a random generated time span
    {
    	state = States.EATING;
    	System.out.println("Philosopher " + getId() + " is " + state); //prints out what state the philosopher is in
    	int eatTime = randomGenerator.nextInt(1000 - 0 + 1) + 0; //random generator between 0-1000
    
    	this.eatingTime += eatTime;
    	
    	numberOfEatingTurns++;
    	
    	try
    	{
    		Thread.sleep(eatTime);
    	}
    	catch (InterruptedException ie)
    	{
    		ie.printStackTrace();
    	}
    }
    
    private void pickUpLeftChopstick() //method to pick up the left chopstick (put locks)
    {
		leftChopStick.getLock().lock();
    }
    
	private void pickUpRightChopstick() //method to pick up the right chopstick (put locks)
	{
		rightChopStick.getLock().lock();
	}
	
	private void putDownChopsticks() //method to put down both chopsticks (release locks)
	{
		leftChopStick.getLock().unlock();
		rightChopStick.getLock().unlock();
		System.out.println("Philosopher " + id + " has put down both chopsticks.");
	}
}


