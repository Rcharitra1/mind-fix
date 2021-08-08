package ca.nait.rcharitra1.finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ScoreActivity extends BaseActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences prefs;
    View mainView;
    int correct;
    int total;
    double winRatio=0.0;
    static int [] difficulty = new int []{1, 2, 3};
    TextView scoreDisplay;
    DbManager dbManager;
    SQLiteDatabase database;

    Button replayBtn;
    Button highScoresBtn;

    String level="";
    String username="user";
    String category="";
    String score="0/0";

    static final String TAG="ScoreActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Bundle bundle = getIntent().getExtras();
        String correctAnswers = bundle.getString("CorrectAnswers");
        String totalQuestions = bundle.getString("TotalQuestions");
        level = bundle.getString("Level");
        category = bundle.getString("Category");
        total = Integer.parseInt(totalQuestions);
        correct = Integer.parseInt(correctAnswers);
        highScoresBtn = findViewById(R.id.view_scores);
        replayBtn= findViewById(R.id.replay);
        scoreDisplay = findViewById(R.id.score_view);
        score =correct+"/"+total;

        winRatio = (double) correct / (double) total;



        scoreDisplay.setText(score);

        if(winRatio<0.5)
        {
            scoreDisplay.setTextColor(Color.RED);
        }else if(winRatio>0.5 && winRatio <0.7)
        {
            scoreDisplay.setTextColor(Color.YELLOW);
        }
        else
        {
            scoreDisplay.setTextColor(Color.GREEN);
        }

        if(level.equals("easy"))
        {
            winRatio*= difficulty[0];
        }else if(level.equals("medium"))
        {
            winRatio*=difficulty[1];
        }else
        {
            winRatio*=difficulty[2];
        }

        Log.d(TAG, "onCreate: "+winRatio+","+correct+","+total+level);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        String userKey = getResources().getString(R.string.preference_username);
        username = prefs.getString(userKey, "user");
        mainView = findViewById(R.id.activity_score);

        String bgColorKey= getResources().getString(R.string.preference_main_bg);
        String bgColor = prefs.getString(bgColorKey, "#FFEB3B");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

        dbManager = new DbManager(this);

        highScoresBtn.setOnClickListener(this);
        replayBtn.setOnClickListener(this);
        writeToDb();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.view_scores:
            {
                startActivity(new Intent(this, HistoricalScoreActivity.class));
                break;
            }

            case R.id.replay:
            {
                startActivity(new Intent(this, MainActivity.class));
                break;
            }
        }
    }

    private void writeToDb()
    {
        database = dbManager.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(DbManager.C_LEVEL, level);
        values.put(DbManager.C_SCORE, score);
        values.put(DbManager.C_USERNAME, username);
        values.put(DbManager.C_SCORE_PERCENTAGE, winRatio);
        try {
            database.insertOrThrow(DbManager.TABLE_NAME, null, values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String userKey = getResources().getString(R.string.preference_username);
        username = prefs.getString(userKey, "user");
        String bgColorKey= getResources().getString(R.string.preference_main_bg);
        String bgColor = prefs.getString(bgColorKey, "#FFEB3B");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }
}