package com.faraz.android.nerdify;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
    DownloadManager mDownloadManager;
    Uri uri;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.subjectchoosefrag,container,false);


        mDownloadManager=(DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.recycler2);


        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView mTitleTextView;
        RelativeLayout rl;
        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.subjectlistitem);
            rl=(RelativeLayout)itemView.findViewById(R.id.rl);
            rl.setOnClickListener(this);

        }

        public void action(int pos,String text)
        {
            position=pos;

          mTitleTextView.setText(text);
            if(position==0)
               rl.setBackgroundColor(getResources().getColor(R.color.red));
            else if(position==1)
               rl.setBackgroundColor(getResources().getColor(R.color.orange));
            else if(position==2)
               rl.setBackgroundColor(getResources().getColor(R.color.purple));
            else if (position==3)
                rl.setBackgroundColor(getResources().getColor(R.color.purpledark));
            else if (position==4)
               rl.setBackgroundColor(getResources().getColor(R.color.green));
            else if (position==5)
                rl.setBackgroundColor(getResources().getColor(R.color.grey));


        }
        @Override
        public void onClick(View v) {


            if ((position==1)) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://thinkdiff.co.in/erudite/upload_erudite/JH_SEM1/chem.pdf"));
                request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS, "chem.pdf");
                long download_id = mDownloadManager.enqueue(request);
            }

        }
    }


    class Adapter extends RecyclerView.Adapter<ViewHolder>
    {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View v=inflater.inflate(R.layout.subjectslistlayout,parent,false);
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
            holder.action(position,text);





        }

        @Override
        public int getItemCount() {
            if(jsonArray!=null)
                return jsonArray.length();
            else return 0;
        }
    }

}
