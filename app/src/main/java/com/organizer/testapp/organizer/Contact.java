package com.organizer.testapp.organizer;

import android.net.Uri;

/**
 * Created by Johnny Manson on 23.07.13.
 */
public class Contact {

    private int _id;
    private String _name;
    private String _phone;
    private String _email;
    private String _address;
    private Uri _imageURI;


    public Contact(int id, String name, String phone, String email, String address, Uri imageURI) {
        _id = id;
        _name = name;
        _phone = phone;
        _email = email;
        _address = address;
        _imageURI = imageURI;
    }

    public int getId() { return _id; }

    public String getName() {
        return _name;
    }

    public String getPhone() {
        return _phone;
    }

    public String getEmail() {
        return _email;
    }

    public String getAddress() {
        return _address;
    }

    public Uri getImageURI() { return _imageURI; }
}
