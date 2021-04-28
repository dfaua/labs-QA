import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestExceptionManager {
	
	private ExceptionManager exceptionManager;
	
	@BeforeEach
	private void setUp() {
		exceptionManager = new ExceptionManager(List.of(CriticalException.class, AnotherCriticalException.class));
	}
	
	@ParameterizedTest
	@MethodSource("criticalExceptionsSource")
	public void testIsCritical(Exception exception) {
		assertTrue(exceptionManager.isCritical(exception));
	}
	
	@ParameterizedTest
	@MethodSource("usualExceptionsSource")
	public void testIsNotCritical(Exception exception) {
		assertFalse(exceptionManager.isCritical(exception));
	}
	
	@ParameterizedTest
	@NullSource
	public void testIsCriticalWithNull(Exception exception) {
		assertThrows(NullPointerException.class, () -> exceptionManager.isCritical(exception));
	}
	
	@Test
	public void testCheckAndIncrementCounter() {
		var criticalExceptions = getCriticalExceptions();
		var notCriticalExceptions = getUsualExceptions();
		
		long criticalExceptionsCount = criticalExceptions.size();
		long notCriticalExceptionsCount = notCriticalExceptions.size();
		
		Stream.concat(criticalExceptions.stream(), notCriticalExceptions.stream())
				.forEach(e -> exceptionManager.checkAndIncrementCounter(e));
		
		assertEquals(criticalExceptionsCount, exceptionManager.getCriticalExceptionsCounter());
		assertEquals(notCriticalExceptionsCount, exceptionManager.getUsualExceptionsCounter());
	}
	
	private static Stream<Exception> criticalExceptionsSource() {
		return getCriticalExceptions().stream();
	}
	
	private static List<Exception> getCriticalExceptions() {
		return List.of(
				new CriticalException(),
				new BadArgumentsException(),
				new AnotherCriticalException(),
				new ClientException()
		);
	}
	
	private static Stream<Exception> usualExceptionsSource() {
		return getUsualExceptions().stream();
	}
	
	private static List<Exception> getUsualExceptions() {
		return List.of(
				new Exception(),
				new RuntimeException(),
				new ArithmeticException(),
				new NullPointerException()
		);
	}
	
	private static class CriticalException extends Exception {
	
	}
	
	private static class ClientException extends CriticalException {
	
	}
	
	private static class BadArgumentsException extends ClientException {
	
	}
	
	private static class AnotherCriticalException extends RuntimeException {
	
	}
	
}