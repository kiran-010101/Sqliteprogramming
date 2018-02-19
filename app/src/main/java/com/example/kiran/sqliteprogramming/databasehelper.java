package com.example.kiran.sqliteprogramming;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kiran on 11/8/17.
 */

public class databasehelper extends SQLiteOpenHelper {


    public static final String Database_name = "Student.db";
    public static final String table_name = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";



    public databasehelper(Context context) {
        //to create database and table
        super(context, Database_name, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        onCreate(db);

    }

    public boolean insertdata(String name,String surname,String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        //to put data on databse we use content values..similat to adapter in recycle view
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        long result = db.insert(table_name,null,contentValues);//to insert in databse use class insert
        //if data is not inserted class insert will return value -1 i.e false..see in class insert..use ctrl key and mouse cursor to go there

        if (result==-1)
            return false;
            else
                return true;
        //if inserted then return true in main activity adddata
    }

    public Cursor getalldata(){
        //Cursor class is used to read,write data from our database
        SQLiteDatabase db = this.getWritableDatabase();//creating instance of sqlitedatabase
        Cursor resul = db.rawQuery("select * from "+table_name,null);
        return resul;
    }

    public boolean updatedata(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        db.update(table_name, contentValues, "ID=?", new String[]{id});//value is updated on the base of ID so it is very important
        //to update ..see table name,its values and from which field we have to update it i.e in this case  id
        return true;
    }

        public Integer deletedata(String id){//we delete data according ti id..
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name,"ID=?",new String[]{id});//here to delete we take tablename,and ask for "ID=?",our ID colun name.
        //and then we replaced ? (of ID=?) with our argument id....this fuction return integer (i.e number of row deleted)
/*delete class delete whole row..
             return db.delete(table_name,"ID=?",new String[]{id});
            this line just check the table with table_name,,and look at column ID(ID=?) and then check for id we have passesd
            as argument and then delete that whole row(i.e name,surname,id,marks)we can perform same function by
            passing name or by surname instead of id,,but name and surname can be same ..but ID is a primary key so
            we used it instead of other column*/


    }
}
