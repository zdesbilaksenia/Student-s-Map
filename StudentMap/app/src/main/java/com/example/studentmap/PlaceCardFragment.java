package com.example.studentmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class PlaceCardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place,
                container, false);

        ImageView image = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView rating = view.findViewById(R.id.rating);

        Bundle bundle = this.getArguments();

        Place place = (Place) bundle.getSerializable("place");
        name.setText(place.getName());
        address.setText(place.getVicinity());
        ratingBar.setRating((float) place.getRating());
        rating.setText(String.valueOf(place.getRating()));
        if (place.getPhoto() != null) {
            Picasso.get().load(place.getPhoto()).into(image);
        } else {
            Picasso.get().load(place.getIcon()).into(image);
        }

        return view;
    }
}
