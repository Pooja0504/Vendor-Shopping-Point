package com.shopping_point.vendor_shopping_point.model;

public class AddProductApiResponse {

    private int id;
    private boolean error;
    private String message;
    private Vendor vendor;

    public AddProductApiResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Vendor getVendor() {
        return vendor;
    }

}
