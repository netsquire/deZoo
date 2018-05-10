package piano;

public class EntityEvent {

    private String id;
    private String typeReference;
    private Operation operation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeReference() {
        return typeReference;
    }

    public void setTypeReference(String typeReference) {
        this.typeReference = typeReference;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public EntityEvent(String id, String typeReference, Operation operation) {
        this.id = id;
        this.typeReference = typeReference;
        this.operation = operation;
    }

    public enum Operation {
        DEFAULT("default"),
        CREATE("create"),
        SAVE("save"),
        UPDATE("update"),
        DELETE("delete");

        String operation;

        Operation(String op) {
            this.operation = op;
        }
    }
}
