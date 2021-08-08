package ca.nait.rcharitra1.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;

public class HistoricalScoreActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    ListView view;
    View mainView;
    SharedPreferences prefs;
    DbManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_score);
        view = findViewById(R.id.list_view);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        mainView = findViewById(R.id.activity_historical_data);

        String bgColorKey= getResources().getString(R.string.preference_main_bg);
        String bgColor = prefs.getString(bgColorKey, "#FFEB3B");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

        dbManager = new DbManager(this);


    }

    @Override
    protected void onResume()
    {
        database = dbManager.getReadableDatabase();
        cursor = database.query(DbManager.TABLE_NAME, null, null, null, null, null, DbManager.C_SCORE_PERCENTAGE + " DESC");
        startManagingCursor(cursor);
        ScoreAdapter adapter = new ScoreAdapter(this, cursor);
        view.setAdapter(adapter);
        super.onResume();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColorKey= getResources().getString(R.string.preference_main_bg);
        String bgColor = prefs.getString(bgColorKey, "#FFEB3B");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }
}