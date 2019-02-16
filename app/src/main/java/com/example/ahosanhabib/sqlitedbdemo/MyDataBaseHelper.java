package com.example.ahosanhabib.sqlitedbdemo;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String database_name = "student.db";
    private static final String table_name = "student_details";
    private static final String ID = "_id";
    private static final String Name = "Name";
    private static final String Age = "Age";
    private static final String Gender = "Gender";
    private static final int version = 3;
    private static final String drop_table= "DROP TABLE IF EXISTS "+table_name;
    private static final String displayalldata= "SELECT * FROM "+table_name;
    private static final String create_table = "CREATE TABLE "+table_name+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Name+" VARCHAR(255),"+Age+" INTEGER,"+Gender+" VARCHAR(15))";

    private Context context;

    public MyDataBaseHelper(Context context) {
        super(context, database_name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            Toast.makeText(context,"Oncreate is called",Toast.LENGTH_SHORT).show();
            db.execSQL(create_table);
        }catch (Exception e){
            Toast.makeText(context,"Exeption: "+e,Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            Toast.makeText(context,"OnUpgrate is called",Toast.LENGTH_SHORT).show();
            db.execSQL(drop_table);
            onCreate(db);
        }catch (Exception e){
            Toast.makeText(context,"Exeption: "+e,Toast.LENGTH_SHORT).show();
        }

    }
    public long insertdata(String name,String age,String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name,name);
        contentValues.put(Age,age);
        contentValues.put(Gender,gender);
        long rowid = sqLiteDatabase.insert(table_name,null,contentValues);
        return rowid;
    }
    public Cursor displaydata(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(displayalldata,null);
        return cursor;
    }
    public boolean upDateData(String id, String name, String age, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(Name,name);
        contentValues.put(Age,age);
        contentValues.put(Gender,gender);

        sqLiteDatabase.update(table_name,contentValues,ID+" = ?",new String[]{id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(table_name,ID+" = ?",new String[]{id});
    }
}
