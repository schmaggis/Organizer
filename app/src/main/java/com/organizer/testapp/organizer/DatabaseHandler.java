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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBITORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_ITEMS);

        onCreate(db);
    }

    /*****************************/
    /* *** CONTACT - METHODS *** */
    /*****************************/

    public void insertContact(Contact contact) {
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
    /* *** DebitorContact - METHODS *** */
    /*****************************/

    public void insertDebitorContact(DebitorContact dContact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DEBITOR_CONTACT_ID, dContact.getDebitorContactId());
        values.put(KEY_ITEM_ID, dContact.getItemId());
        values.put(KEY_AMOUNT, dContact.getAmount());

        db.insert(TABLE_DEBITORS, null, values);
        db.close();
    }

    public DebitorContact getDebitorContact(int id) {
        DebitorContact _dContact = null;

        int _debitor_contact_id;
        int _item_id;
        Double _amount;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_DEBITORS, new String[] { KEY_ID, KEY_DEBITOR_CONTACT_ID, KEY_ITEM_ID, KEY_AMOUNT }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null) {
            cursor.moveToFirst();

            _debitor_contact_id = Integer.parseInt(cursor.getString(1));
            _item_id = Integer.parseInt(cursor.getString(2));
            _amount = Double.parseDouble(cursor.getString(3));

            _dContact = new DebitorContact(id, _debitor_contact_id, _item_id, _amount);
        }

        db.close();
        cursor.close();
        return _dContact;
    }

    public void deleteDebitorContact(DebitorContact dContact) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_DEBITORS, KEY_ID + "=?", new String[] { String.valueOf(dContact.getId()) });
        db.close();
    }

    public int getDebitorContactCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEBITORS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateDebitorContact(DebitorContact dContact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DEBITOR_CONTACT_ID, dContact.getDebitorContactId());
        values.put(KEY_ITEM_ID, dContact.getItemId());
        values.put(KEY_AMOUNT, dContact.getAmount());

        int rowsAffected = db.update(TABLE_DEBITORS, values, KEY_ID + "=?", new String[] { String.valueOf(dContact.getId()) });
        db.close();

        return rowsAffected;
    }

    public List<DebitorContact> getAllDebitorContacts() {
        List<DebitorContact> _debitorContacts = new ArrayList<DebitorContact>();

        int _id;
        int _debitor_contact_id;
        int _item_id;
        Double _amount;


        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEBITORS, null);

        if (cursor.moveToFirst()) {
            do {
                _id = Integer.parseInt(cursor.getString(0));
                _debitor_contact_id = Integer.parseInt(cursor.getString(1));
                _item_id = Integer.parseInt(cursor.getString(2));
                _amount = Double.parseDouble(cursor.getString(3));

                _debitorContacts.add(new DebitorContact(_id, _debitor_contact_id, _item_id, _amount));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return _debitorContacts;
    }

    public List<DebitorContact> getAllDebitorContactsOfExItem(int itemID) {
        List<DebitorContact> _debitorContacts = new ArrayList<DebitorContact>();

        int _id;
        int _debitor_contact_id;
        int _item_id;
        Double _amount;


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_DEBITORS, new String[] { KEY_ID, KEY_DEBITOR_CONTACT_ID, KEY_ITEM_ID, KEY_AMOUNT }, KEY_ITEM_ID + "=?", new String[] { String.valueOf(itemID) }, null, null, null, null );

        if (cursor.moveToFirst()) {
            do {
                _id = Integer.parseInt(cursor.getString(0));
                _debitor_contact_id = Integer.parseInt(cursor.getString(1));
                _item_id = Integer.parseInt(cursor.getString(2));
                _amount = Double.parseDouble(cursor.getString(3));

                _debitorContacts.add(new DebitorContact(_id, _debitor_contact_id, _item_id, _amount));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return _debitorContacts;
    }


    /*****************************/
    /* *** Item - METHODS *** */
    /*****************************/


    public void insertExpenseItem(ExpenseItem item) {
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

        if (cursor != null) {
            cursor.moveToFirst();

            try {
                _name = cursor.getString(1);
                _category = cursor.getString(2);
                _date = dateformat.parse(cursor.getString(3));
                _imageuri = Uri.parse(cursor.getString(4));
                _creditor_id = Integer.parseInt(cursor.getString(5));
                _amount = Double.parseDouble(cursor.getString(6));

                item = new ExpenseItem(id, _name, _category, _date, _imageuri, _creditor_id, _amount, null);
            } catch (ParseException e) {
                e.printStackTrace();
            }
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

    public List<ExpenseItem> getAllExpenseItems() {
        List<ExpenseItem> _expenseItems = new ArrayList<ExpenseItem>();

        int _id;
        String _name;
        String _category;
        Uri _imageuri;
        SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-DD");
        Date _date;
        int _creditor_id;
        Double _amount;
        List<DebitorContact> _debitorContacts;


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

                    _debitorContacts = getAllDebitorContactsOfExItem(_id);

                    _expenseItems.add(new ExpenseItem(_id, _name, _category, _date, _imageuri, _creditor_id, _amount, _debitorContacts));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return _expenseItems;
    }
}
