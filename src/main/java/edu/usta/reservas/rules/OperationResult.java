package edu.usta.reservas.rules;

public class OperationResult {
    private final boolean success;
    private final String message;

    public OperationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }

    public static OperationResult ok() { return new OperationResult(true, "Validación exitosa."); }
    public static OperationResult fail(String message) { return new OperationResult(false, message); }
}