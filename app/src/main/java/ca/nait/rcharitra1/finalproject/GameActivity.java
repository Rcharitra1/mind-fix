package ca.nait.rcharitra1.finalproject;


import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import ca.nait.rcharitra1.finalproject.data.AnswerListAsyncResponse;
import ca.nait.rcharitra1.finalproject.data.QuestionBank;
import ca.nait.rcharitra1.finalproject.model.Question;

public class GameActivity extends BaseActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    static  final String TAG="GameActivity";
    Button option_one;
    Button option_two;
    Button option_three;
    Button option_four;
    TextView questionString;
    SharedPreferences prefs;
    View mainView;
    boolean isFlipQuestionUsed=false;
    int questionsList;

    Button half_options;
    Button flip_question;
    int i =0;
    int totalQuestion=0;
    int correctAnswers = 0;
    List<Question> questionList;
    String categoryName;
    String difficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        Bundle bundle = getIntent().getExtras();
        String categoryid = bundle.getString("CategoryId");
        categoryName = bundle.getString("Category");
        difficulty = bundle.getString("Level");
        String numberOfQuestions = bundle.getString("Questions");
        int questions = Integer.parseInt(numberOfQuestions.trim());
        totalQuestion = questions;
        questions+=1;
        numberOfQuestions = questions+"";
        Log.d(TAG, "onCreate: "+numberOfQuestions);
        questionsList = totalQuestion;


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        mainView = findViewById(R.id.activity_game);

        String bgColorKey= getResources().getString(R.string.preference_main_bg);
        String bgColor = prefs.getString(bgColorKey, "#efefef");
        mainView.setBackgroundColor(Color.parseColor(bgColor));


        /*Instantiate variables*/

        questionString = findViewById(R.id.question);
        option_one = findViewById(R.id.button_option_1);
        option_two = findViewById(R.id.button_option_2);
        option_three = findViewById(R.id.button_option_3);
        option_four = findViewById(R.id.button_option_4);
        flip_question = findViewById(R.id.flip_question);
        half_options = findViewById(R.id.half_options);

        questionList=new QuestionBank(
                categoryid,
                numberOfQuestions,
                difficulty
        ).GetQuestions(new AnswerListAsyncResponse()
        {
            @Override
            public void isFinished(List<Question> questionArrayList)
            {

                Question question = questionArrayList.get(i);

                questionString.setText(question.getQuestionString());
                List<String> random = randomizeArray(questionArrayList.get(i).getInCorrectAnswers());

                if(random.size()>2)
                {
                    option_three.setText(random.get(2));
                    option_four.setText(random.get(3));
                }else
                {
                    option_three.setVisibility(View.GONE);
                    option_four.setVisibility(View.GONE);
                }
                option_one.setText(random.get(0));
                option_two.setText(random.get(1));
            }




        });

        /*Set on click listeners*/

        option_one.setOnClickListener(this);
        option_two.setOnClickListener(this);
        option_three.setOnClickListener(this);
        option_four.setOnClickListener(this);
        flip_question.setOnClickListener(this);
        half_options.setOnClickListener(this);

    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_option_1:
            {
                checkAnswer(option_one.getText().toString());
                updateView();
                break;
            }
            case R.id.button_option_2:
            {
                checkAnswer(option_two.getText().toString());
                updateView();
                break;

            }

            case R.id.button_option_3:
            {
                checkAnswer(option_three.getText().toString());
                updateView();
                break;
            }

            case R.id.button_option_4:
            {
                checkAnswer(option_four.getText().toString());
                updateView();
                break;
            }

            case R.id.flip_question:
            {
                isFlipQuestionUsed=true;

                updateView();
                flip_question.setEnabled(false);
                isFlipQuestionUsed = false;
                break;
            }

            case R.id.half_options:
            {
                halfOption();
                break;
            }
        }
    }


    private void checkAnswer(String answerToCheck)
    {

        Question question=questionList.get(i);
        if(!(question.getAnswerString().compareTo(answerToCheck)==0))
        {
            shakeAnimation();
        }else
        {
            correctAnswers++;
            enlargeAnimation();
        }
    }

    private void updateView()
    {
        if(isFlipQuestionUsed==true)
        {
            questionsList++;
        }

        Log.d(TAG, "updateView: "+questionsList);

        i++;
        Log.d(TAG, "updateView: "+i);
        if(i<questionsList)
        {
            Log.d(TAG, "updateView: "+totalQuestion);
            Log.d(TAG, "updateView: "+i);
            option_three.setVisibility(View.VISIBLE);
            option_four.setVisibility(View.VISIBLE);


            Question question = questionList.get(i);
//            Log.d(TAG, "UpdateView: "+question.getInCorrectAnswers().length);

            questionString.setText(question.getQuestionString());
            List<String> random = randomizeArray(question.getInCorrectAnswers());
            if(random.size()<4)
            {
                option_three.setVisibility(View.GONE);
                option_four.setVisibility(View.GONE);
            }else
            {
                option_four.setText(random.get(3));
                option_three.setText(random.get(2));
            }

            option_one.setText(random.get(0));
            option_two.setText(random.get(1));
        }else
        {
            Bundle bundle = new Bundle();
            bundle.putString("TotalQuestions", totalQuestion+"");
            bundle.putString("CorrectAnswers", correctAnswers+"");
            bundle.putString("Category", categoryName);
            bundle.putString("Level", difficulty);
//            Log.d(TAG, "updateView: "+totalQuestion+correctAnswers+categoryName+difficulty);

            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }





    }


    private void halfOption()
    {
        Log.d(TAG, "half_Option: ");
        Question question =questionList.get(i);
        if(question.getInCorrectAnswers().length<4)
        {
            Toast.makeText(this, "50/50 is not applicable on True/False questions", Toast.LENGTH_LONG).show();
        }else
        {
            List<String>incorrectOptions = Arrays.asList(question.getInCorrectAnswers());
            int indexOfCurrentQuestion = incorrectOptions.indexOf(question.getAnswerString());
            Random rnd = new Random();
            boolean isNotUnique = false;
            int indexNotRemove = 0;
            while(isNotUnique==false)
            {
                int randomNumber = rnd.nextInt(4);

                if(randomNumber!=indexOfCurrentQuestion)
                {
                    isNotUnique=true;
                    indexNotRemove=randomNumber;
                }

            }

            String[] newArray;
            if(indexNotRemove<indexOfCurrentQuestion)
            {
                newArray = new String[]
                        {
                                question.getInCorrectAnswers()[indexNotRemove],
                                question.getInCorrectAnswers()[indexOfCurrentQuestion]
                        };
            }else
            {
                newArray= new String[]
                        {
                                question.getInCorrectAnswers()[indexOfCurrentQuestion],
                                question.getInCorrectAnswers()[indexNotRemove]
                        };
            }

            question.setInCorrectAnswers(newArray);
            option_one.setText(question.getInCorrectAnswers()[0]);
            option_two.setText(question.getInCorrectAnswers()[1]);
            option_three.setVisibility(View.GONE);
            option_four.setVisibility(View.GONE);

            half_options.setEnabled(false);



        }
    }

    private List<String> randomizeArray(String[] array)
    {
        List<String> randomArray;
        randomArray = Arrays.asList(array);
        if(array.length>2)
        {
            Collections.shuffle(randomArray);
        }

        return randomArray;
    }

    private void shakeAnimation()
    {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        CardView cardView = findViewById(R.id.card_view);
        cardView.setAnimation(shake);



        shake.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation)
            {
                cardView.setCardBackgroundColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {

              cardView.setCardBackgroundColor(Color.parseColor("#FFC107"));
              cardView.setPreventCornerOverlap(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                cardView.setCardBackgroundColor(Color.parseColor("#FFC107"));
                cardView.setPreventCornerOverlap(true);

            }
        });


    }

    private void enlargeAnimation()
    {
        final ValueAnimator anim = ValueAnimator.ofFloat(1f, 1.1f);
        anim.setDuration(300);
        CardView cardView = findViewById(R.id.card_view);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cardView.setScaleX((Float) animation.getAnimatedValue());
                cardView.setScaleY((Float) animation.getAnimatedValue());
            }
        });


        anim.setRepeatCount(1);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        String bgColorKey= getResources().getString(R.string.preference_main_bg);
        String bgColor = prefs.getString(bgColorKey, "#FFEB3B");
        mainView.setBackgroundColor(Color.parseColor(bgColor));


    }
}