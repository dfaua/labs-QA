package exceptionManager;

import exceptionAnalyzer.ExceptionAnalyzer;
import server.Server;

public class ExceptionManagerImpl implements ExceptionManager {
	
	private Server server;
	private ExceptionAnalyzer exceptionAnalyzer;
	private int criticalExceptionsCount;
	private int usualExceptionsCount;
	private int failedServerSends;
	
	public ExceptionManagerImpl(Server server, ExceptionAnalyzer exceptionAnalyzer) {
		this.server = server;
		this.exceptionAnalyzer = exceptionAnalyzer;
	}
	
	@Override
	public void handle(Exception exception) {
		boolean isCritical = exceptionAnalyzer.isCritical(exception);
		if (isCritical)
			criticalExceptionsCount++;
		else
			usualExceptionsCount++;
		
		boolean accepted = server.sendException(exception.toString());
		if (!accepted)
			failedServerSends++;
	}
	
	@Override
	public void setServer(Server server) {
		this.server = server;
	}
	
	@Override
	public void setExceptionAnalyzer(ExceptionAnalyzer exceptionAnalyzer) {
		this.exceptionAnalyzer = exceptionAnalyzer;
	}
	
	public int getCriticalExceptionsCount() {
		return criticalExceptionsCount;
	}
	
	public int getUsualExceptionsCount() {
		return usualExceptionsCount;
	}
	
	public int getFailedServerSends() {
		return failedServerSends;
	}
}