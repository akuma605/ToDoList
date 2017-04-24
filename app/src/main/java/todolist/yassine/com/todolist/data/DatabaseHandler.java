package todolist.yassine.com.todolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import todolist.yassine.com.todolist.model.MyList;

/**
 * Created by yassine on 17-01-17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<MyList> toDoList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TODO_TABLE = "CREATE TABLE "
                +Constant.TABLE_NAME + " ("
                +Constant.DB_KEY_ID + " INTEGER PRIMARY KEY,"
                +Constant.DB_TITLE + " TEXT);";

        db.execSQL(CREATE_TODO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);

        onCreate(db);

    }

    public void deleteToDo(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constant.TABLE_NAME, Constant.DB_KEY_ID + " = ? ",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public void addToDo(MyList myList){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.DB_TITLE, myList.getTitle());

        db.insert(Constant.TABLE_NAME, null, values);

        //Log.v("success", "perfect");

        db.close();

    }

    public ArrayList<MyList> getToDo(){

        String selectQuery = " SELECT FROM * " + Constant.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constant.TABLE_NAME, new String[]{
                Constant.DB_KEY_ID,
                Constant.DB_TITLE,}, null, null, null, null, null);

        if (cursor.moveToFirst()){

            do {

                MyList list = new MyList();

                list.setTitle(cursor.getString(cursor.getColumnIndex(Constant.DB_TITLE)));
                list.setId(cursor.getInt(cursor.getColumnIndex(Constant.DB_KEY_ID)));

                toDoList.add(list);

            }while (cursor.moveToNext());

        }

        return toDoList;

    }
}
