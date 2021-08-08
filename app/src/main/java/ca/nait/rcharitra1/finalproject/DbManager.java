package ca.nait.rcharitra1.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbManager extends SQLiteOpenHelper {
    static final String TAG="DbManager";
    static final String DB_NAME = "questionnaire.db";
    static final int DB_VERSION = 2;
    static final String TABLE_NAME="scores";
    static final String C_ID = BaseColumns._ID;
    static final String C_USERNAME = "username";
    static final String C_SCORE = "score";
    static final String C_LEVEL="level";
    static final String C_SCORE_PERCENTAGE="scorepercentage";
    static final String C_DATE = "date";
    public DbManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table "+TABLE_NAME+" ("+C_ID+ " INTEGER primary key AUTOINCREMENT, "+C_USERNAME
                +" text, "+ C_SCORE+ " text, "+ C_LEVEL+" text, "+C_DATE+ " datetime DEFAULT CURRENT_TIMESTAMP, " +C_SCORE_PERCENTAGE+ " REAL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
}
