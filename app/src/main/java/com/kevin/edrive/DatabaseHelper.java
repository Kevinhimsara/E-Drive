package com.kevin.edrive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "edrive.db";
    private static final int DATABASE_VERSION = 1;

    // Table 01 - Vendors
    public static final String TABLE_VENDORS = "Vendors";
    public static final String COLUMN_VENDORID = "vendorid";
    public static final String COLUMN_SHOPNAME = "ShopName";
    public static final String COLUMN_VENDORNAME = "VendorName";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_CONTACT = "Contact";
    public static final String COLUMN_PASSWORD = "Password";

    // Table 02 - UserInfo
    public static final String TABLE_USERINFO = "Userinfo";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_USERNAME = "UserName";
    public static final String COLUMN_BLOODTYPE = "BloodType";
    public static final String COLUMN_EMERGENCYCONTACT = "EmergencyContact";

    // Table 03 - VehicleInfo
    public static final String TABLE_VEHICLEINFO = "Vehicleinfo";
    public static final String COLUMN_VEHICLEID = "vehicleid";
    public static final String COLUMN_VEHICLENUMBER = "VehicleNumber";
    public static final String COLUMN_VEHICLEMODEL = "VehicleModel";
    public static final String COLUMN_CHARGERTYPE = "ChargerType";
    public static final String COLUMN_CHARGEROUTPUT = "ChargerOutput";

    //Table 04 - Vendor Login
    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    //Table 04 - Vendor SignUp
    private static final String TABLE_SIGNUP = "signup";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE01 = "CREATE TABLE " + TABLE_VENDORS + " (" +
                COLUMN_VENDORID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SHOPNAME + " TEXT, " +
                COLUMN_VENDORNAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_CONTACT + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE01);

        String CREATE_TABLE02 = "CREATE TABLE " + TABLE_USERINFO + " (" +
                COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_BLOODTYPE + " TEXT, " +
                COLUMN_EMERGENCYCONTACT + " TEXT)";
        db.execSQL(CREATE_TABLE02);

        String CREATE_TABLE03 = "CREATE TABLE " + TABLE_VEHICLEINFO + " (" +
                COLUMN_VEHICLEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VEHICLENUMBER + " TEXT, " +
                COLUMN_VEHICLEMODEL + " TEXT, " +
                COLUMN_CHARGERTYPE + " TEXT, " +
                COLUMN_CHARGEROUTPUT + " TEXT)";
        db.execSQL(CREATE_TABLE03);

//===================================================================================================
        //Login page

        String CREATE_TABLE04 = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE04);

        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, "Admin");
        values.put(COL_PASSWORD, "12345");
        db.insert(TABLE_USERS, null, values);

        String CREATE_TABLE05 = "CREATE TABLE " + TABLE_SIGNUP + " (" +
                COLUMN_SHOPNAME + " TEXT, " +
                COLUMN_VENDORNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE05);
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
//========================================================================================================

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERINFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLEINFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNUP);
        onCreate(db);
    }

//==========================================================================================================
    //Vendor Signup page
    public boolean insertVendorSingUp(String shopName, String vendorName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHOPNAME, shopName);
        values.put(COLUMN_VENDORNAME, vendorName);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_SIGNUP, null, values);
        return result != -1;
    }

//============================================================================================================
    //Setting up the Shop Profile

    public boolean upsertShopProfile(String shopName, String vendorName, String address, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHOPNAME, shopName);
        values.put(COLUMN_VENDORNAME, vendorName);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_CONTACT, contact);

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_VENDORS + " LIMIT 1", null);
        long result;
        if (cursor.moveToFirst()) {
            result = db.update(TABLE_VENDORS, values, COLUMN_VENDORID + " = ?", new String[]{"1"});
        } else {
            result = db.insert(TABLE_VENDORS, null, values);
        }
        cursor.close();
        return result != -1;
    }
//========================================================================================================================

    // Get Shop Profile
    public Cursor getShopProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_VENDORS + " LIMIT 1", null);
    }

    // USERINFO TABLE FUNCTIONS

    // Insert new user
    public boolean insertUser(String name, String bloodType, String emergencyContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, name);
        values.put(COLUMN_BLOODTYPE, bloodType);
        values.put(COLUMN_EMERGENCYCONTACT, emergencyContact);
        long result = db.insert(TABLE_USERINFO, null, values);
        return result != -1;
    }

    // Get all users
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERINFO, null);
    }


    // VEHICLEINFO TABLE FUNCTIONS

    // Insert vehicle
    public boolean insertVehicle(String number, String model, String chargerType, String chargerOutput) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VEHICLENUMBER, number);
        values.put(COLUMN_VEHICLEMODEL, model);
        values.put(COLUMN_CHARGERTYPE, chargerType);
        values.put(COLUMN_CHARGEROUTPUT, chargerOutput);
        long result = db.insert(TABLE_VEHICLEINFO, null, values);
        return result != -1;
    }

    // Get all vehicles
    public Cursor getAllVehicles() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_VEHICLEINFO, null);
    }
}
