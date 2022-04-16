package ru.smith.engine_lv.api.dto.common.response;

public class ErrorPayload {
    private final ErrorCode code;
    private final String reason;

    private ErrorPayload() {
        this.code = null;
        this.reason = null;
    }

    public ErrorPayload(ErrorCode code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
