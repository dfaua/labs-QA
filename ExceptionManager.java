package exceptionManager;

import exceptionAnalyzer.ExceptionAnalyzer;
import server.Server;

public interface ExceptionManager {
	
	void handle(Exception exception);
	
	void setServer(Server server);
	
	void setExceptionAnalyzer(ExceptionAnalyzer exceptionAnalyzer);
	
	int getCriticalExceptionsCount();
	
	int getUsualExceptionsCount();
	
	int getFailedServerSends();
}
