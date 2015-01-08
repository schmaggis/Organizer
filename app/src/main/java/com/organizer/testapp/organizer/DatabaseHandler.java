package com.organizer.testapp.organizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Johnny Manson on 19.01.14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "organizer",
    TABLE_CONTACTS = "contacts",
    KEY_ID = "id",
    KEY_NAME = "name",
    KEY_PHONE = "phone",
    KEY_EMAIL = "email",
    KEY_ADDRESS = "address",
    KEY_IMAGEURI = "imageUri",



    TABLE_DEBITORS = "debitors",
    //KEY_ID = "id",
    KEY_DEBITOR_CONTACT_ID = "debitor_contact_id",
    KEY_ITEM_ID = "item_id",
    KEY_AMOUNT = "amount",

    TABLE_EXPENSE_ITEMS = "expense_items",
    //KEY_ID = "id",
    //KEY_NAME = "name",
    KEY_CATEGORY = "category",
    KEY_DATE = "date",
    //KEY_IMAGEURI = "imageUri",
    KEY_CREDITOR_ID = "creditor_id"
    ;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT," + KEY_EMAIL + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_IMAGEURI + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_DEBITORS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DEBITOR_CONTACT_ID + " INTEGER," + KEY_ITEM_ID + " INTEGER," + KEY_AMOUNT + " DOUBLE)" );
        db.execSQL("CREATE TABLE " + TABLE_EXPENSE_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_DATE + " DATE," + KEY_IMAGEURI + " TEXT," + KEY_CREDITOR_ID + " INTEGER," + KEY_AMOUNT + " INTEGER)" );

     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_ITEMS);

        onCreate(db);
    }

    /*****************************/
    /* *** CONTACT - METHODS *** */
    /*****************************/

    public void createContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_IMAGEURI, contact.getImageURI().toString());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS, KEY_IMAGEURI }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5)));
        db.close();
        cursor.close();
        return contact;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + "=?", new String[] { String.valueOf(contact.getId()) });
        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_IMAGEURI, contact.getImageURI().toString());

        int rowsAffected = db.update(TABLE_CONTACTS, values, KEY_ID + "=?", new String[] { String.valueOf(contact.getId()) });
        db.close();

        return rowsAffected;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);

        if (cursor.moveToFirst()) {
            do {
                contacts.add(new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }


    /*****************************/
    /* *** Item - METHODS *** */
    /*****************************/


    public void createExpenseItem(ExpenseItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, item.getName());
        values.put(KEY_CATEGORY, item.getCategory());
        SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-DD");
        values.put(KEY_DATE, dateformat.format(item.getDate()));
        values.put(KEY_IMAGEURI, item.getImageURI().toString());
        values.put(KEY_CREDITOR_ID, item.get_creditor_id());
        values.put(KEY_AMOUNT, item.get_creditor_amount());

        db.insert(TABLE_EXPENSE_ITEMS, null, values);
        db.close();
    }

    public ExpenseItem getExpenseItem(int id) {
        ExpenseItem item = null;

        String _name;
        String _category;
        Uri _imageuri;
        SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-DD");
        Date _date;
        Double _amount;
        int _creditor_id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXPENSE_ITEMS, new String[] { KEY_ID, KEY_NAME, KEY_CATEGORY, KEY_DATE, KEY_IMAGEURI, KEY_CREDITOR_ID, KEY_AMOUNT }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        try {
            _name = cursor.getString(1);
            _category = cursor.getString(2);

            _date = dateformat.parse(cursor.getString(3));

            _imageuri = Uri.parse(cursor.getString(4));
            _creditor_id = Integer.parseInt(cursor.getString(5));
            _amount = Double.parseDouble(cursor.getString(6));

            item = new ExpenseItem(id, _name, _category, _date, _imageuri, _creditor_id, _amount);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.close();
        cursor.close();
        return item;
    }

    public void deleteExpenseItem(ExpenseItem item) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_EXPENSE_ITEMS, KEY_ID + "=?", new String[] { String.valueOf(item.getId()) });
        db.close();
    }

    public int getExpenseItemCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE_ITEMS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateExpenseItem(ExpenseItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, item.getName());
        values.put(KEY_CATEGORY, item.getCategory());
        SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-DD");
        values.put(KEY_DATE, dateformat.format(item.getDate()));
        values.put(KEY_IMAGEURI, item.getImageURI().toString());
        values.put(KEY_CREDITOR_ID, item.get_creditor_id());
        values.put(KEY_AMOUNT, item.get_creditor_amount());

        int rowsAffected = db.update(TABLE_EXPENSE_ITEMS, values, KEY_ID + "=?", new String[] { String.valueOf(item.getId()) });
        db.close();

        return rowsAffected;
    }

    public List<ExpenseItem> getExpenseItems() {
        List<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();

        int _id;
        String _name;
        String _category;
        Uri _imageuri;
        SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-DD");
        Date _date;
        Double _amount;
        int _creditor_id;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE_ITEMS, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    _id = Integer.parseInt(cursor.getString(0));
                    _name = cursor.getString(1);
                    _category = cursor.getString(2);

                    _date = dateformat.parse(cursor.getString(3));

                    _imageuri = Uri.parse(cursor.getString(4));
                    _creditor_id = Integer.parseInt(cursor.getString(5));
                    _amount = Double.parseDouble(cursor.getString(6));

                    expenseItems.add(new ExpenseItem(_id, _name, _category, _date, _imageuri, _creditor_id, _amount));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expenseItems;
    }


}
