package com.organizer.testapp.organizer;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Schmaggis on 07.01.2015.
 */
public class ExpenseItem extends Item {

    private int _creditor_id; //person who paid the amount
    private double _creditor_amount; //whole amount that was paid

    //private List<DebitorContact> DebitorContacts = new ArrayList<DebitorContact>();
    // List of debitors who have to pay a share of the amount paid by the creditor
    // => in Datenbank hintgerlegt: DebitorContacts

    public ExpenseItem (int item_id, String name, String category, Date date, Uri imageURI, int creditor_id, double amount) {
        super(item_id, name, category, date, imageURI);

        _creditor_id = creditor_id;
        _creditor_amount = amount;
    }

    public int get_creditor_id() {
        return _creditor_id;
    }

    public double get_creditor_amount() {
        return _creditor_amount;
    }

    public List<DebitorContact> getDebitorContacts() {
        List<DebitorContact> debitors = new ArrayList<DebitorContact>();
        //ToDo:
        //debitors.addAll(GetDebitorsOfItemFromDatabase(super.getId()));
        return debitors;
    }

    public void addDebitorContacts(DebitorContact debitorContact) {
        //ToDo:
        // DebitorContacts.add(debitorContact);
    }
}
