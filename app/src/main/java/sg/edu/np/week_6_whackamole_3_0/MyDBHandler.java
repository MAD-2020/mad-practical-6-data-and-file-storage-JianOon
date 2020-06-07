package sg.edu.np.week_6_whackamole_3_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    /*
        The Database has the following properties:
        1. Database name is WhackAMole.db
        2. The Columns consist of
            a. Username
            b. Password
            c. Level
            d. Score
        3. Add user method for adding user into the Database.
        4. Find user method that finds the current position of the user and his corresponding
           data information - username, password, level highest score for each level
        5. Delete user method that deletes based on the username
        6. To replace the data in the database, we would make use of find user, delete user and add user

        The database shall look like the following:

        Username | Password | Level | Score
        --------------------------------------
        User A   | XXX      | 1     |    0
        User A   | XXX      | 2     |    0
        User A   | XXX      | 3     |    0
        User A   | XXX      | 4     |    0
        User A   | XXX      | 5     |    0
        User A   | XXX      | 6     |    0
        User A   | XXX      | 7     |    0
        User A   | XXX      | 8     |    0
        User A   | XXX      | 9     |    0
        User A   | XXX      | 10    |    0
        User B   | YYY      | 1     |    0
        User B   | YYY      | 2     |    0

     */
    public static final String Table_User = "User";
    public static final String COLUMN_Name = "Name";
    public static final String COLUMN_Password = "Password";
    public static final String COLUMN_Level = "Level";
    public static final String COLUMN_Score = "Score";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WhackAMole.db";
    private static final String FILENAME = "MyDBHandler.java";
    private static final String TAG = "Whack-A-Mole3.0!";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /* HINT:
            This is triggered on DB creation.
            Log.v(TAG, "DB Created: " + CREATE_ACCOUNTS_TABLE);
         */
        String CREATE_USER_TABLE = "CREATE TABLE " + Table_User + "(" + COLUMN_Name + " TEXT," + COLUMN_Password + " TEXT)";
        String CREATE_LEVEL_TABLE = "CREATE TABLE LEVEL( " + COLUMN_Name +" TEXT, " +COLUMN_Level + " INTEGER , "+COLUMN_Score +" Integer)";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_LEVEL_TABLE);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        /* HINT:
            This is triggered if there is a new version found. ALL DATA are replaced and irreversible.
         */
        db.execSQL("DROP TABLE IF EXISTS " + Table_User);
        db.execSQL("DROP TABLE IF EXISTS " + "LEVEL");
        onCreate(db);

    }
    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Table_User);
        db.execSQL("DROP TABLE IF EXISTS " + "LEVEL");
        onCreate(db);
    }

    public void addUser(UserData userData)
    {
            /* HINT:
                This adds the user to the database based on the information given.

             */
        ContentValues values = new ContentValues();
        values.put(COLUMN_Name, userData.getMyUserName());
        values.put(COLUMN_Password, userData.getMyPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(Table_User, null, values);
        db.close();
        Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());

    }

    public void loadnew(UserData userData){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 1 ; i<=10 ; i++){
            values.put(COLUMN_Name, userData.getMyUserName());
            values.put(String.valueOf(COLUMN_Level), i);
            values.put(String.valueOf(COLUMN_Score), 0);
            Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
            db.insert("LEVEL", null, values);
        }

        db.close();
        Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
    }


    public UserData findUser(String username) {
        String query = "SELECT * FROM " + Table_User + " WHERE Name = '" + username + "'";
        Log.v(TAG,query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserData user = new UserData();
        if(cursor.moveToFirst()){
            do{

                String uName =cursor.getString(0);
                String uPw = cursor.getString(1);
                Log.v(TAG,uName + uPw);
                if(uName != null && uPw != null){
                    user.setMyUserName(uName);
                    user.setMyPassword(uPw);
                }

            }while(cursor.moveToNext());
            return user;}

        else{
            Log.v(TAG, FILENAME+ ": No data found!");
            return null;}





        /* HINT:
            This finds the user that is specified and returns the data information if it is found.
            If not found, it will return a null.
            Log.v(TAG, FILENAME +": Find user form database: " + query);

            The following should be used in getting the query data.
            you may modify the code to suit your design.

            if(cursor.moveToFirst()){
                do{
                    ...
                    .....
                    ...
                }while(cursor.moveToNext());
                Log.v(TAG, FILENAME + ": QueryData: " + queryData.getLevels().toString() + queryData.getScores().toString());
            }
            else{
                Log.v(TAG, FILENAME+ ": No data found!");
            }
         */

        }

    public void debug(){
        String query = "SELECT * FROM " + Table_User;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
    }

    public void deleteAccount (String username) {
    /* HINT:
        This finds and delete the user data in the database.
        This is not reversible.
        Log.v(TAG, FILENAME + ": Database delete user: " + query);
     */
        String run = "DELETE FROM " + Table_User + " WHERE Name = " + username;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(run);

    }
    public UserData getlnS(UserData userz){
        ArrayList<Integer> levellist = new ArrayList<Integer>();
        ArrayList<Integer> scorelist = new ArrayList<Integer>();
        Log.v(TAG,userz.getMyUserName());
        String query = "SELECT * FROM LEVEL WHERE Name = '" + userz.getMyUserName() + "'";
        Log.v("count",getcount("LEVEL"));
        Log.v(TAG,query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        do{
            levellist.add(Integer.parseInt(cursor.getString(1)));
            scorelist.add(Integer.parseInt(cursor.getString(2)));
            }

        while(cursor.moveToNext());
        userz.setLevels(levellist);
        userz.setScores(scorelist);
        return userz;

    }
    public String getcount(String table){
        String query = "SELECT COUNT(*) FROM '"  + table +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String count;
        cursor.moveToFirst();
        do{
            count = cursor.getString(0);
        }while(cursor.moveToNext());

        return count;
    }
    public void updatescore(Integer score, String name, Integer level){
        String query = "Update LEVEL SET Score = '" + score + "' WHERE Name = '" + name + "' AND Level = '" + level + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }



}
