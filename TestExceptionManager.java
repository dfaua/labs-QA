import exceptionAnalyzer.ExceptionAnalyzer;
import exceptionAnalyzer.ExceptionAnalyzerFactory;
import exceptionAnalyzer.FakeExceptionAnalyzer;
import exceptionManager.ExceptionManager;
import exceptionManager.ExceptionManagerImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import server.Server;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class TestExceptionManager {
	
	private ExceptionManager exceptionManager;
	private Server server;

	@BeforeAll
	private static void initFactory() {
		ExceptionAnalyzerFactory.setCustomAnalyzer(new FakeExceptionAnalyzer(true));
	}
	
	@BeforeEach
	private void setUp() {
		Server server = mock(Server.class);
		when(server.sendException(any()))
				.thenReturn(true);
		this.server = server;
		
		var exceptionAnalyzer = mock(ExceptionAnalyzer.class);
		when(exceptionAnalyzer.isCritical(any()))
				.then(invocation -> !(invocation.getArgumentAt(0, Exception.class) instanceof CriticalException));
		
		exceptionManager = new ExceptionManagerImpl(server, exceptionAnalyzer);
	}
	
	@ParameterizedTest
	@MethodSource("criticalExceptionsSource")
	public void testCriticalExceptionsCount(Exception exception) {
		var exceptionAnalyzer = ExceptionAnalyzerFactory.create();
		exceptionManager.setExceptionAnalyzer(exceptionAnalyzer);
		
		exceptionManager.handle(exception);
		assertEquals(1, exceptionManager.getCriticalExceptionsCount());
		assertEquals(0, exceptionManager.getUsualExceptionsCount());
	}
	
	@ParameterizedTest
	@MethodSource("usualExceptionsSource")
	public void testUsualExceptionsCount(Exception exception) {
		var exceptionAnalyzer = new FakeExceptionAnalyzer(false);
		exceptionManager.setExceptionAnalyzer(exceptionAnalyzer);
		
		exceptionManager.handle(exception);
		assertEquals(0, exceptionManager.getCriticalExceptionsCount());
		assertEquals(1, exceptionManager.getUsualExceptionsCount());
	}
	
	@Test
	public void testExceptionsCount() {
		List<Exception> criticalExceptions = getCriticalExceptions();
		List<Exception> usualExceptions = getUsualExceptions();
		Stream.concat(criticalExceptions.stream(), usualExceptions.stream())
				.forEach(e -> exceptionManager.handle(e));
		
		assertEquals(criticalExceptions.size(), exceptionManager.getCriticalExceptionsCount());
		assertEquals(usualExceptions.size(), exceptionManager.getUsualExceptionsCount());
	}
	
	@ParameterizedTest
	@MethodSource({"criticalExceptionsSource", "usualExceptionsSource"})
	public void testZeroFailedServerSends(Exception exception) {
		exceptionManager.handle(exception);
		assertEquals(0, exceptionManager.getFailedServerSends());
		verifyServerSendingTimes(1);
	}
	
	@Test
	public void testFailedServerSends() {
		Server server = mock(Server.class);
		when(server.sendException(any()))
				.thenReturn(false);
		this.server = server;
		exceptionManager.setServer(server);
		
		List<Exception> criticalExceptions = getCriticalExceptions();
		List<Exception> usualExceptions = getUsualExceptions();
		Stream.concat(criticalExceptions.stream(), usualExceptions.stream())
				.forEach(e -> exceptionManager.handle(e));
		
		int expectedFailedSends = criticalExceptions.size() + usualExceptions.size();
		assertEquals(expectedFailedSends, exceptionManager.getFailedServerSends());
		verifyServerSendingTimes(expectedFailedSends);
	}
	
	private void verifyServerSendingTimes(int expected) {
		verify(server, times(expected)).sendException(any());
	}
	
	private static Stream<Exception> criticalExceptionsSource() {
		return getCriticalExceptions().stream();
	}
	
	private static List<Exception> getCriticalExceptions() {
		return List.of(
				new CriticalException(),
				new BadArgumentsException(),
				new CriticalException(),
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
	
}