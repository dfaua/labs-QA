package exceptionAnalyzer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileExceptionAnalyzer implements ExceptionAnalyzer {
	
	private final List<Class<? extends Exception>> criticalExceptions = new ArrayList<>();
	
	public FileExceptionAnalyzer(String filePath) {
		try {
			readExceptions(filePath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void readExceptions(String filePath) throws IOException, ClassNotFoundException {
		try (var inputStream = new BufferedInputStream(new FileInputStream(filePath))) {
			var properties = new Properties();
			properties.load(inputStream);
			
			String[] exceptionNames = properties.getProperty("critical.exceptions").split(",\\s*");
			for (String exceptionName : exceptionNames) {
				criticalExceptions.add((Class<? extends Exception>) Class.forName(exceptionName));
			}
		}
	}
	
	@Override
	public boolean isCritical(Exception exception) {
		Class<?> exceptionClass = exception.getClass();
		for (var criticalExceptionClass : criticalExceptions) {
			if (criticalExceptionClass.isAssignableFrom(exceptionClass)) {
				return true;
			}
		}
		
		return false;
	}
}
