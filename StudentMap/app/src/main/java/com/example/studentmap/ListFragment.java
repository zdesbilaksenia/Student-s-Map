package com.example.studentmap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list,
                container, false);

//        Button nextButton = (Button) view.findViewById(R.id.button_first);
//        nextButton.setOnClickListener(this);

        return view;
    }

}
