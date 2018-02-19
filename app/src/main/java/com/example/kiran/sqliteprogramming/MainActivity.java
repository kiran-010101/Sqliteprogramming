package com.example.kiran.sqliteprogramming;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hello";
    databasehelper mdb;
    EditText mname,msurname,mmarks,mid;
    Button btn;
    Button btnviewall;
    Button btnupdate;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdb = new databasehelper(this);//calling constructor so that database and table are built
        mname = (EditText)findViewById(R.id.editTextname);
        msurname = (EditText)findViewById(R.id.editTextsurname);
        mmarks = (EditText)findViewById(R.id.editTextmarks);
        btn = (Button)findViewById(R.id.btnadd);
        btnviewall = (Button)findViewById(R.id.btnview);
        btnupdate = (Button)findViewById(R.id.btnupdate);
        mid= (EditText)findViewById(R.id.editid);
        delete=(Button)findViewById(R.id.btndelete);

        adddata();//calling on oncreate function
        viewall();//calling on oncreate
        updatedata();
        deletedata();
    }

    public void deletedata(){

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer deleterows = mdb.deletedata(mid.getText().toString());
                if(deleterows>0)
                    Toast.makeText(MainActivity.this,"SOME DATA DELETED",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"NOTHING DELETED",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void updatedata(){

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isupdated = mdb.updatedata(mid.getText().toString(), mname.getText().toString(), msurname.getText().toString()
                        , mmarks.getText().toString());
                if (isupdated == true)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
             else
                    Toast.makeText(MainActivity.this, "Data NOT Updateted", Toast.LENGTH_LONG).show();





            }
        });
    }

    public void adddata(){
     btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
                             /*go to databasehelper class insert data function and return true or false depending in wheteher data is
                insert or not...return true if inserted else return false*/
             boolean isinsert = mdb.insertdata(mname.getText().toString(), msurname.getText().toString(), mmarks.getText().toString());
             if (isinsert == true)
                 Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
             else
                 Toast.makeText(MainActivity.this, "Data NOT Inserted", Toast.LENGTH_LONG).show();
         }
     });

    }

    public void viewall(){
        btnviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = mdb.getalldata();//getalldata() function return object of cursor class
                if(res.getCount() == 0){
                    //message show
                    showmessage("ERROR","NOTHING FOUND");

                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+"\n");
                    //buffer is used to store data..."ID: is ur column name...res is where data is store..
                    //res.getstring(0) point to ID column as out column start from 0 in SQlite though we have written ID as col-1
                    buffer.append("NAME :"+res.getString(1)+"\n");//point to col-2 as res.getstring(1)..
                    buffer.append("SURNAME :"+res.getString(2)+"\n");//point to col-3 as res.getstring(2)..
                    buffer.append("MARKS :"+res.getString(3)+"\n\n");
                    //all data is now in buffer


                }

                //show all data
                showmessage("Data store",buffer.toString());//printing data of buffer by calling showmessage function


            }


        });



    }

    public void showmessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);//we can cancel after it is used
        builder.setTitle(title);//alert dialog has title and message
        builder.setMessage(message);

        Log.d(TAG,message);

        builder.show();

    }
}