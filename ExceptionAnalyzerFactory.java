package exceptionAnalyzer;

public class ExceptionAnalyzerFactory {

	private static ExceptionAnalyzer customAnalyzer;
	
	public static ExceptionAnalyzer create() {
		if (customAnalyzer == null)
			return new FakeExceptionAnalyzer(false);

		return customAnalyzer;
	}

	public static void setCustomAnalyzer(ExceptionAnalyzer customAnalyzer) {
		ExceptionAnalyzerFactory.customAnalyzer = customAnalyzer;
	}
}
