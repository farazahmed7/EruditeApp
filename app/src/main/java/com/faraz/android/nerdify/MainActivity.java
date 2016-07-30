package com.faraz.android.nerdify;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StreamChooseFragment.Callbacks,SemesterChooseFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.container,new StreamChooseFragment()).commit();
    }


    @Override
    public void attach(String data) {
        FragmentManager fm=getSupportFragmentManager();
       FragmentTransaction ft= fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("data",data);
// set Fragmentclass Arguments
        SemesterChooseFragment fragment = new SemesterChooseFragment();
       fragment.setArguments(bundle);
        Log.d("status",data);
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.container,fragment);
        ft.addToBackStack(null);
        ft.commit();


    }

    @Override
    public void attachSubject(String data) {

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("data",data);
// set Fragmentclass Arguments
        SubjectChooseFragment fragment = new SubjectChooseFragment();
        fragment.setArguments(bundle);
        Log.d("status",data);
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.container,fragment);
        ft.addToBackStack(null);
        ft.commit();


    }
}