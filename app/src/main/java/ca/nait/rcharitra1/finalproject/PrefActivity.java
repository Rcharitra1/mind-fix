package ca.nait.rcharitra1.finalproject;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.annotation.Nullable;


public class PrefActivity extends PreferenceActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
