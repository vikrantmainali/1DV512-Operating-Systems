import java.util.concurrent.locks.*;

public class ChopStick 
{
	private final int id;
	private Lock lock;
	
	public ChopStick(int id)
	{
		this.id = id;
		lock = new ReentrantLock(); //creating a new lock instant for every chopstick
	
	}
	
	public int getId() //method to get id of chopsticks
	{
		return id;
	}
	
	public Lock getLock() //method to get the lock of each chopstick (allows us to lock() and unlock()
	{
		return lock;
	}
	
	public void setLock(Lock lock) //setter method to set locks to chopsticks
	{
		this.lock = lock;
	}
	
	public boolean lockAvailable() //checks if lock is available
	{
		return lock.tryLock();
	}
}