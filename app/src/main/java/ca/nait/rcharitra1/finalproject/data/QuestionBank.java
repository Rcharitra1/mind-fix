package ca.nait.rcharitra1.finalproject.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.nait.rcharitra1.finalproject.controller.AppController;
import ca.nait.rcharitra1.finalproject.model.Question;


public class QuestionBank{

    static String TAG="QuestionBank";

    private String category="10";
    private String numberOfQuestions = "10";
    private String difficulty = "easy";
    private List<Question> questionList = new ArrayList<Question>();

    public List<Question> GetQuestions(final AnswerListAsyncResponse callback)
    {
        String url = "https://opentdb.com/api.php?amount="+numberOfQuestions+"&category="+category+"&difficulty="+difficulty;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {

                            JSONArray array = response.getJSONArray("results");


                            for (int i=0; i<array.length();i++)
                            {
                                Question question = new Question();

                                question.setQuestionString(array.getJSONObject(i).getString("question"));
                                question.setAnswerString(array.getJSONObject(i).getString("correct_answer"));
                                JSONArray myArray = array.getJSONObject(i).getJSONArray("incorrect_answers");
                                String []holdingArray;
                                if(myArray.length()<2)
                                {
                                    holdingArray = new String[]
                                            {
                                                    myArray.getString(0).toString(),
                                                    question.getAnswerString()
                                            };
                                }else
                                {
                                    holdingArray = new String[]{
                                            myArray.get(0).toString(),
                                            myArray.get(1).toString(),
                                            myArray.get(2).toString(),
                                            question.getAnswerString()
                                    };
                                }

                                question.setQuestionString(CorrectString(question.getQuestionString()));
                                question.setAnswerString(CorrectString(question.getAnswerString()));

                                for(int j=0; j< holdingArray.length; j++)
                                {
                                    holdingArray[j]=CorrectString(holdingArray[j]);
                                }


                                Arrays.sort(holdingArray);




                                question.setInCorrectAnswers(holdingArray);
                                questionList.add(question);

                            }




                        }catch (Exception e)
                        {
                            Log.d(TAG, "onResponse: "+"Error while loading");
                        }
                        if(null !=callback) callback.isFinished(questionList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);


        return questionList;
    }

    public QuestionBank(String category, String numberOfQuestions, String difficulty)
    {
        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.difficulty= difficulty;
    }

    public QuestionBank()
    {

    }

    private String CorrectString(String checkString)
    {

        if(checkString.contains("&quot;")  || checkString.contains("#039;")|| checkString.contains("&amp;") || checkString.contains("&'") || checkString.contains("*"))
        {
            checkString=(checkString.replaceAll("&amp;", "&"));
            checkString=(checkString.replaceAll("#039;", "'"));

            checkString=(checkString.replaceAll("&quot;", "''"));
            checkString= checkString.replaceAll("&'", "'");
            checkString= checkString.replaceAll("'*'", "");


        }

        return  checkString;
    }



}
