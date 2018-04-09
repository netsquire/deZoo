package hammer;

public enum ProcessingStatus {

	SUCCESS("success"),
	FAILURE("failed"),
	TO_BE_CONTINUE("need_work");

	private final String value;

	ProcessingStatus(String state) {
		value = state;
	}
}
