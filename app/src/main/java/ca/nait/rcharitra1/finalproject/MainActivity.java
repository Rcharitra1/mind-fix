package ca.nait.rcharitra1.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ca.nait.rcharitra1.finalproject.data.QuestionBank;

public class MainActivity extends BaseActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener
{
    SharedPreferences prefs;
    Button start;
    Spinner categories;
    TextView numberOfQuestions;
    RadioGroup difficulty;
    View mainView;
    static final String TAG="MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start_button);
        categories = findViewById(R.id.categories);
        numberOfQuestions = findViewById(R.id.number_of_questions);
        difficulty = findViewById(R.id.difficulty);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mainView = findViewById(R.id.activity_main);

        String bgColorKey= getResources().getString(R.string.preference_main_bg);
        String bgColor = prefs.getString(bgColorKey, "#FFEB3B");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
        categories.setAdapter(adapter);

        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.start_button:
                {
                    String selectedCategory =categories.getSelectedItem().toString();
                    int difficultyLevel = difficulty.getCheckedRadioButtonId();
                    String getLevel = getCheckedItem(difficultyLevel);
                    String getNumberOfQuestions = numberOfQuestions.getText().toString();
                    String categoryid=getCategoryID(selectedCategory);

                    try{
                        if(getLevel.length()==0 || getNumberOfQuestions.length()==0 || categoryid.length()==0)
                        {
                            Toast.makeText(this, "Select a difficulty level, category and number of questions to begin", Toast.LENGTH_LONG).show();
                        }else
                        {
                            int numberOfQuestions = Integer.parseInt(getNumberOfQuestions);
                            if(numberOfQuestions<5 || numberOfQuestions>30)
                            {
                                Toast.makeText(this, "The questions can be min 5 and max of 30", Toast.LENGTH_LONG).show();
                            }else
                            {
                                Intent intent = new Intent(this, GameActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("Category", selectedCategory);
                                bundle.putString("CategoryId", categoryid);
                                bundle.putString("Questions", getNumberOfQuestions);
                                bundle.putString("Level", getLevel);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        }


                    }catch (Exception e)
                    {
                        Log.d(TAG, "onClick: "+e);
                    }


                    break;
                }
        }

    }

    public String getCheckedItem (int id)
    {
        String level ="";
        switch (id)
        {
            case R.id.easy:
            {
                level = "easy";
                break;
            }

            case R.id.medium:
            {
                level = "medium";
                break;
            }

            case R.id.difficult:
                {
                level = "hard";
                break;
            }
        }
        return level;
    }

    public String getCategoryID(String category)
    {
        String categoryId="";
        switch (category.trim().toLowerCase())
        {
            case "vehicles":
            {
                categoryId="28";
                break;
            }

            case "entertainment":
            {
                categoryId="14";
                break;
            }
            case "sports":
            {
                categoryId="21";
                break;
            }

            case "history":
            {
                categoryId="23";
                break;
            }

            case "animals":
            {
                categoryId="27";
                break;
            }


        }

        return categoryId;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColorKey= getResources().getString(R.string.preference_main_bg);
        String bgColor = prefs.getString(bgColorKey, "#FFEB3B");
        mainView.setBackgroundColor(Color.parseColor(bgColor));

    }
}