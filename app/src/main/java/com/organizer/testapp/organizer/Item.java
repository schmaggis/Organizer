package com.organizer.testapp.organizer;

import android.net.Uri;

import java.util.Date;

/**
 * Created by Schmaggis on 07.01.2015.
 */
public class Item {

    private int _id;
    private String _name;
    private String _category;
    private Date _date;
    private Uri _imageURI;


    public Item (int id, String name, String category, Date date, Uri imageURI) {
        _id = id;
        _name = name;
        _category = category;
        _date = date;
        _imageURI = imageURI;
    }

    public int getId() { return _id; }

    public String getName() {
        return _name;
    }

    public String getCategory() {
        return _category;
    }

    public Date getDate() {
        return _date;
    }

    public Uri getImageURI() { return _imageURI; }

}
