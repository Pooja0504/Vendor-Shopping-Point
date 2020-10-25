package com.shopping_point.vendor_shopping_point.model;

public class UpdateApiResponse {

    private String name;
    private String email;
    private String phone_no;
    private boolean error;
    private String message;

    public UpdateApiResponse(String name, String email, String phone_no, boolean error, String message) {
        this.name = name;
        this.email = email;
        this.phone_no = phone_no;
        this.error = error;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
