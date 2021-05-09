package com.tbtk.map.error;

import java.util.Map;

/**
 * Custom response object to define the error JSON output.
 * <p>
 * https://medium.com/@sampathsl/exception-handling-for-rest-api-with-spring-boot-c5d5ba928f5b
 */
public class ExceptionResponse {

    private Integer status;
    private String path;
    private String message;
    private String timestamp;
    private String trace;

    public ExceptionResponse(int status, Map<String, Object> errorAttributes) {
        this.setStatus(status);
        this.setPath((String) errorAttributes.get("path"));
        this.setMessage((String) errorAttributes.get("message"));
        this.setTimestamp(errorAttributes.get("timestamp").toString());
        this.setTrace((String) errorAttributes.get("trace"));
    }

    public Integer getStatus() {
        return status;
    }

    private void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(String timeStamp) {
        this.timestamp = timeStamp;
    }

    public String getTrace() {
        return trace;
    }

    private void setTrace(String trace) {
        this.trace = trace;
    }

    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }
}
