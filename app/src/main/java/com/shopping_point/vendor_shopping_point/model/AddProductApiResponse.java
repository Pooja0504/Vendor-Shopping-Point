package com.shopping_point.vendor_shopping_point.model;

public class AddProductApiResponse {

    private boolean error;
    private String message;


    public AddProductApiResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }


    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }


}
