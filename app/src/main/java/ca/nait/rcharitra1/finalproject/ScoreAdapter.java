package ca.nait.rcharitra1.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



public class ScoreAdapter extends SimpleCursorAdapter {

    static final String[] columns = {DbManager.C_USERNAME, DbManager.C_SCORE, DbManager.C_DATE,DbManager.C_LEVEL};
    static final int [] ids = {R.id.custom_username, R.id.custom_score, R.id.custom_date, R.id.custom_difficulty};
    public ScoreAdapter(Context context, Cursor cursor)
    {
        super(context, R.layout.custom_cursor_row, cursor, columns, ids);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView date = (TextView) view.findViewById(R.id.custom_date);
        String dateString = date.getText().toString();
        dateString = dateString.substring(0, 11);
        date.setText(dateString);
    }
}
