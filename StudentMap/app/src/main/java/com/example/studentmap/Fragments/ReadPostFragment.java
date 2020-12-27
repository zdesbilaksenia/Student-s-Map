package com.example.studentmap.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.studentmap.R;

public class ReadPostFragment extends Fragment {

    Button back_button;
    ImageView post_image;
    TextView post_text;
    TextView location_text;
    RatingBar rating_bar;

    String post;
    String img;
    String location;
    Float rating;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_read_post, container, false);

        back_button = (Button) rootView.findViewById(R.id.back_button);
        post_image = (ImageView) rootView.findViewById(R.id.imagePost);
        post_text = (TextView) rootView.findViewById(R.id.text_post1);
       // rating_bar = (RatingBar) rootView.findViewById(R.id.ratingBar2);
        location_text = (TextView) rootView.findViewById(R.id.location);

        Bundle bundle = getArguments();
        if(bundle != null){

            img = bundle.getString("img");
            post = bundle.getString("text");
            rating = bundle.getFloat("rating");
            location = bundle.getString("location");

        }
        if(!img.isEmpty()){
            Glide.with(getContext()).load(img).into(post_image);
        }
        if(!post.isEmpty()){
            post_text.setText(post);
        }
        if(!location.isEmpty()){
            location_text.setText(location);
        }
//        rating_bar.setRating(rating);

       back_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              end();
           }
       });

        return rootView;
    }

    void end(){
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("FeedFragment");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment, "FeedFragment").commit();
    }

}