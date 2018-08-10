package com.revolut.web.dto;

/**
 * Error body for exceptional replies.
 */
public class ErrorDTO {

    /**
     * just message for simplicity
     */
    private String message;

    public ErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ErrorDTO withMessage(String message){
        return new ErrorDTO(message);
    }
}
