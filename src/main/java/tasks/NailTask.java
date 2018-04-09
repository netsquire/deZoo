package tasks;

import hammer.Hammer;
import hammer.ProcessingStatus;

public class NailTask<T> {

	Class<T> type;

	public Result process(String id) {

		T input = Hammer.get("select item from table where id=?" + id, (Class<T>) getClass());

		T output = null; // some calculation & processing

		String resultId = Hammer.put("insert or update ... into...", output);

		return new Result(resultId, ProcessingStatus.SUCCESS);
	}

	public Class<T> getType() {
		return type;
	}

	class Result<T> {

		String id;
		Class<T> type;
		ProcessingStatus status;

		<T> Result(String id, ProcessingStatus status) {
			this.id = id;
			this.status = status;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Class<T> getType() {
			return type;
		}

		public void setType(Class<T> type) {
			this.type = type;
		}

		public ProcessingStatus getStatus() {
			return status;
		}

		public void setStatus(ProcessingStatus status) {
			this.status = status;
		}
	}
}
