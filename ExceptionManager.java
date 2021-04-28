import java.util.List;

public class ExceptionManager {
	
	private final List<Class<? extends Exception>> criticalExceptions;
	private long criticalExceptionsCounter;
	private long usualExceptionsCounter;
	
	public ExceptionManager(List<Class<? extends Exception>> criticalExceptions) {
		this.criticalExceptions = criticalExceptions;
	}
	
	public boolean isCritical(Exception exception) {
		Class<?> exceptionClass = exception.getClass();
		for (var criticalExceptionClass : criticalExceptions) {
			if (criticalExceptionClass.isAssignableFrom(exceptionClass)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkAndIncrementCounter(Exception exception) {
		boolean isCritical = isCritical(exception);
		if (isCritical)
			criticalExceptionsCounter++;
		else
			usualExceptionsCounter++;
		
		return isCritical;
	}
	
	public long getCriticalExceptionsCounter() {
		return criticalExceptionsCounter;
	}
	
	public long getUsualExceptionsCounter() {
		return usualExceptionsCounter;
	}
}