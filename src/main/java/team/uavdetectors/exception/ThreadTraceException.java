package team.uavdetectors.exception;

public class ThreadTraceException extends Exception {
	private final static String exceptionMsg = "ThreadTraceException";
	public ThreadTraceException() {
		super(exceptionMsg);
	}
}
