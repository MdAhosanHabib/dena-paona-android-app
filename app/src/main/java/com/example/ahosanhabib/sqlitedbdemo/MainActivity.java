package com.example.ahosanhabib.sqlitedbdemo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText nametext,agetext,gendertext,idtext;
    private Button addbutton,displaydatabutton,updatebutton,deletebutton;

    MyDataBaseHelper myDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        nametext = findViewById(R.id.nameeditID);
        agetext = findViewById(R.id.ageeditID);
        gendertext = findViewById(R.id.gendereditID);
        idtext = findViewById(R.id.idwithupdateID);
        addbutton = findViewById(R.id.buttonID);
        displaydatabutton = findViewById(R.id.displaybuttonID);
        updatebutton = findViewById(R.id.updatebuttonID);
        deletebutton = findViewById(R.id.deletebuttonID);

        addbutton.setOnClickListener(this);
        displaydatabutton.setOnClickListener(this);
        updatebutton.setOnClickListener(this);
        deletebutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = nametext.getText().toString();
        String age = agetext.getText().toString();
        String gender = gendertext.getText().toString();
        String id = idtext.getText().toString();

        if(v.getId()==R.id.buttonID){

            long rowid = myDataBaseHelper.insertdata(name,age,gender);
            if(rowid==-1){
                Toast.makeText(getApplicationContext(),"Unsuccessfull Insert",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Data insert succesfully",Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId()==R.id.displaybuttonID){
            Cursor cursor = myDataBaseHelper.displaydata();
            if(cursor.getCount()==0){
                showdata("Error","No Data Found");
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()){
                stringBuffer.append("ক্রমিক: "+cursor.getString(0)+"\n");
                stringBuffer.append("নাম: "+cursor.getString(1)+"\n");
                stringBuffer.append("টাকার পরিমাণ: "+cursor.getString(2)+"\n");
                stringBuffer.append("তারিখ: "+cursor.getString(3)+"\n\n\n");
            }
            showdata("পাওনা",stringBuffer.toString());
        }
        if(v.getId()==R.id.updatebuttonID){

            Boolean update = myDataBaseHelper.upDateData(id,name,age,gender);
            if(update==true){
                Toast.makeText(getApplicationContext(),"Data update succesfully",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Not update",Toast.LENGTH_SHORT).show();
            }

        }
        if(v.getId()==R.id.deletebuttonID){

            int value = myDataBaseHelper.deleteData(id);
            if(value>0){
                Toast.makeText(getApplicationContext(),"Delete Successfully",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Not Deleted",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void showdata(String title, String data){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder  alertDialogBuilder;
        alertDialogBuilder = new  AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setIcon(R.drawable.question);
        alertDialogBuilder.setTitle("সতর্কতা!");
        alertDialogBuilder.setMessage("আপনি কি বেরিয়ে যেতে চান?");
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("না", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
