package ca.nait.rcharitra1.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity  extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.main_screen:
            {
                startActivity(new Intent(this, MainActivity.class));
                break;
            }
            case R.id.high_scores:
            {
                startActivity(new Intent(this, HistoricalScoreActivity.class));
                break;
            }

            case R.id.preferences:
            {
                startActivity(new Intent(this, PrefActivity.class));
            }
        }
        return true;
    }
}
