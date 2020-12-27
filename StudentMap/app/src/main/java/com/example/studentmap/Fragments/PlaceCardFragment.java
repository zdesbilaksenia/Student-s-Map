package com.example.studentmap.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentmap.Place;
import com.example.studentmap.R;
import com.squareup.picasso.Picasso;

public class PlaceCardFragment extends Fragment {
    Button addPostButton;
    Button routeBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place,
                container, false);

        addPostButton = view.findViewById(R.id.btn_post);
        routeBtn = view.findViewById(R.id.btn_route);

        ImageView image = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar2);
        TextView rating = view.findViewById(R.id.rating);
        TextView dist = view.findViewById(R.id.dist);

        Bundle bundle = this.getArguments();

        Place place = (Place) bundle.getSerializable("place");
        name.setText(place.getName());
        address.setText(place.getVicinity());
        ratingBar.setRating((float) place.getRating());
        rating.setText(String.valueOf(place.getRating()));
        dist.setText(place.getDistance());
        if (place.getPhoto() != null) {
            Picasso.get().load(place.getPhoto()).into(image);
        } else {
            Picasso.get().load(place.getIcon()).into(image);
        }

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostMakerFragment postMakerFragment = new PostMakerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("address",place.getVicinity());
                bundle.putString("name", place.getName());
                postMakerFragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.fragmentContainer,postMakerFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment = new MapFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("place", place);
                mapFragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.fragmentContainer,mapFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}
