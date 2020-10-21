package com.shopping_point.vendor_shopping_point.model;

public class AddProductApiResponse {

    private boolean error;
    private String message;
    private Product product;

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

    public Product getProduct() {
        return product;
    }
}
