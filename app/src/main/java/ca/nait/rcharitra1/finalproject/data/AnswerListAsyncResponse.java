package ca.nait.rcharitra1.finalproject.data;

import java.util.ArrayList;
import java.util.List;

import ca.nait.rcharitra1.finalproject.model.Question;

public interface AnswerListAsyncResponse
{
    void isFinished(List<Question> questionArrayList);
}
