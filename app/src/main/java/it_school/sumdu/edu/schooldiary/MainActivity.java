package it_school.sumdu.edu.schooldiary;

import android.content.ContentValues; import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase; import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList; import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd, btnGetData;
    EditText txtSubject, txtDate, txtTask, txtDateQuery;
    DBHelper dbHelper;
    static final String TABLE_DIARY = "mydiarytable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //описание кнопки добавления
        btnAdd = (Button) findViewById(R.id.button);
        btnAdd.setOnClickListener(this);

        //описание кнопки извлечения информации
        btnGetData = (Button) findViewById(R.id.button2);
        btnGetData.setOnClickListener(this);
        txtSubject = (EditText) findViewById(R.id.editText);
        txtDate = (EditText) findViewById(R.id.editText2);
        txtTask = (EditText) findViewById(R.id.editText3);
        txtDateQuery = (EditText) findViewById(R.id.editText4);
        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String subject = txtSubject.getText().toString();
        String date = txtDate.getText().toString();
        String task = txtTask.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.button:
                // подготовим данные для вставки в виде пар: наименование столбца
                cv.put("subject", subject);
                cv.put("date", date);
                cv.put("task", task);

                // вставляем запись и получаем ее ID
                db.insert("mydiarytable", null, cv);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Новое домашнее задание добавлено в дневник",Toast.LENGTH_SHORT);
                toast.show(); txtSubject.getText().clear();
                txtDate.getText().clear();
                txtTask.getText().clear();
                break;
            case R.id.button2:
                List<String> subjectlist = new ArrayList<String>();
                List<String> tasklist= new ArrayList<String>();
                String dateQuery = txtDateQuery.getText().toString();
                String sqlQuery = "select * "
                        + "from " + TABLE_DIARY
                        + " where date = ?";
                Cursor c = db.rawQuery(sqlQuery, new String[]{dateQuery});
                String cursorSubject, cursorTask;
                if (c.moveToFirst()) {
                    do {
                        cursorSubject = c.getString(c.getColumnIndex("subject"));
                        subjectlist.add(cursorSubject);
                        cursorTask = c.getString(c.getColumnIndex("task"));
                        tasklist.add(cursorTask);
                    } while (c.moveToNext());
                }
                c.close();
                if ((subjectlist.isEmpty())&&(tasklist.isEmpty())) {
                    Toast t = Toast.makeText(getApplicationContext(), "На эту дату домашнего задания нет", Toast.LENGTH_LONG);
                    t.show();
                } else {
                    Intent intent = new Intent(this, DiaryDetail.class);
                    intent.putStringArrayListExtra("subjectlist", (ArrayList<String>) subjectlist);
                    intent.putStringArrayListExtra("tasklist", (ArrayList<String>) tasklist);
                    startActivity(intent);
                }
                break;
        }

        // закрываем подключение к БД
        dbHelper.close();
    }
    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDiary", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            db.execSQL("create table mydiarytable ("
                    + "id integer primary key autoincrement,"
                    + "subject text,"
                    + "date text,"
                    + "task text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
