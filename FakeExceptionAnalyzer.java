package exceptionAnalyzer;

public class FakeExceptionAnalyzer implements ExceptionAnalyzer {
	
	private boolean isCritical;
	
	public FakeExceptionAnalyzer(boolean isCritical) {
		this.isCritical = isCritical;
	}
	
	@Override
	public boolean isCritical(Exception exception) {
		return isCritical;
	}
}
