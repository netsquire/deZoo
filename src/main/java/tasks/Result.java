package tasks;

import piano.ProcessingStatus;

public class Result<T> {

    private String id;
    private Class<T> resultType;
    private Object result;
    private ProcessingStatus status;

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

    public Class<T> getResultType() {
        return resultType;
    }

    public void setResultType(Class<T> resultType) {
        this.resultType = resultType;
    }

    public ProcessingStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessingStatus status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}