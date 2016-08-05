package com.faraz.android.nerdify;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by abc on 7/30/2016.
 */
public class StreamChooseFragment extends Fragment {

    private Callbacks mCallbacks;
    Button cs;

    public interface Callbacks
    {
        public void attach(String data);

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.streamchoosefrag,container,false);
        cs=(Button)view.findViewById(R.id.Cs);

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.attach(cs.getText().toString());
            }
        });
        return view;

    }
}
