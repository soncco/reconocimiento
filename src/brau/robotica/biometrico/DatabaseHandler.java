package brau.robotica.biometrico;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "personas";

	// Contacts table name
	private static final String TABLE_PERSONA = "contacts";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PH_NO = "picture";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PERSONA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_PH_NO + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONA);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addContact(Persona persona) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, persona.getName()); // Contact Name
		values.put(KEY_PH_NO, persona.getPicture()); // Contact Phone

		// Inserting Row
		db.insert(TABLE_PERSONA, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Persona getPersona(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PERSONA, new String[] { KEY_ID,
				KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Persona contact = new Persona(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return contact;
	}
	
	Persona getPersonaByName(String name) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PERSONA, new String[] { KEY_ID,
				KEY_NAME, KEY_PH_NO }, KEY_NAME + "=?",
				new String[] { name }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Persona contact = new Persona(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return contact;
	}
	
	// Getting All Contacts
	public List<Persona> getAllContacts() {
		List<Persona> contactList = new ArrayList<Persona>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PERSONA;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Persona persona = new Persona();
				persona.setID(Integer.parseInt(cursor.getString(0)));
				persona.setName(cursor.getString(1));
				persona.setPicture(cursor.getString(2));
				// Adding contact to list
				contactList.add(persona);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}
	
	// Getting All Names
	public List<String> getAllNames() {
		List<Persona> contactList = getAllContacts();
		List<String> stringList = new ArrayList<String>();
		for (Persona cn : contactList) {
			if(!stringList.contains(cn.getName()))
				stringList.add(cn.getName());
            //L.i("N: " + cn.getName());
		}
        return stringList;
	}

	// Updating single contact
	public int updateContact(Persona persona) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, persona.getName());
		values.put(KEY_PH_NO, persona.getPicture());

		// updating row
		return db.update(TABLE_PERSONA, values, KEY_ID + " = ?",
				new String[] { String.valueOf(persona.getID()) });
	}

	// Deleting single contact
	public void deleteContact(Persona persona) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PERSONA, KEY_ID + " = ?",
				new String[] { String.valueOf(persona.getID()) });
		db.close();
	}


	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PERSONA;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
