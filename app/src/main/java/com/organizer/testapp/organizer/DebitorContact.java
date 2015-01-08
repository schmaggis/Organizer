package com.organizer.testapp.organizer;

import android.net.Uri;

/**
 * Created by Schmaggis on 07.01.2015.
 */
public class DebitorContact {

    private int _id;
    private int _debitor_contact_id;
    private int _item_id;
    private double _amount;

    public DebitorContact(int id, int debitor_contact_id, int item_id, double amount){
        _id = id;
        _debitor_contact_id = debitor_contact_id;
        _item_id = item_id;
        _amount = amount;
    }

    public int get_id() {
        return _id;
    }

    public int getDebitorContactId() { return _debitor_contact_id; }

    public int getItemId() { return _item_id; }

    public double getAmount() {
        return _amount;
    }

}
