package com.faraz.android.nerdify;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 7/31/2016.
 */
public class SubjectChooseFragment extends Fragment {

    private TextView mTextView;
    RecyclerView mRecyclerView;
    JSONArray jsonArray;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.subjectchoosefrag,container,false);



        mRecyclerView=(RecyclerView)view.findViewById(R.id.recycler2);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new Semester().execute();
        return view;

    }

    class Semester extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://farazahmed8.pythonanywhere.com/api/");

            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("stream", "B.Tech CS"));
                nameValuePairs.add(new BasicNameValuePair("sem", "1"));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                String json = EntityUtils.toString(response.getEntity());
                Log.d("status", response.getStatusLine() + "");

                jsonArray=new JSONArray(json);

                Log.d("response",json);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Adapter adapter=new Adapter();
            mRecyclerView.setAdapter(adapter);

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTitleTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;

        }
    }


    class Adapter extends RecyclerView.Adapter<ViewHolder>
    {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View v=inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            String text="";
            try {
                JSONObject rec = jsonArray.getJSONObject(position);
                text=rec.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            holder.mTitleTextView.setText(text);


        }

        @Override
        public int getItemCount() {
            if(jsonArray!=null)
                return jsonArray.length();
            else return 0;
        }
    }

}
