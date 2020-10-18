package com.shopping_point.vendor_shopping_point.model;

public class Vendor {

    private int id;
    private String organisation_name;
    private String email;
    private String password;
    private String phone_no;
    private boolean isActive;

    public Vendor(String organisation_name, String email, String phone_no, String password) {
        this.organisation_name = organisation_name;
        this.email = email;
        this.phone_no=phone_no;
        this.password = password;

    }

    public Vendor(int id, String organisation_name, String email, String password, String phone_no, boolean isActive) {
        this.id = id;
        this.organisation_name = organisation_name;
        this.email = email;
        this.password = password;
        this.phone_no=phone_no;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getOrganisation_name() {
        return organisation_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone_no(){return phone_no;}

    public boolean isActive() {
        return isActive;
    }

}
