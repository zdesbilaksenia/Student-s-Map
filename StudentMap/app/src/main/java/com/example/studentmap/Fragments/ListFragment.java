package com.example.studentmap.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.studentmap.MapViewModel;
import com.example.studentmap.Place;
import com.example.studentmap.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ListFragment extends Fragment {

    MapViewModel mapViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list,
                container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TextView warning = view.findViewById(R.id.warning);

        mapViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(MapViewModel.class);
        LiveData<List<Place>> data = mapViewModel.getData();

        data.observe((LifecycleOwner) getActivity(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                warning.setVisibility(view.GONE);
                final MyAdapter adapter = new MyAdapter(places);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        List<Place> data;

        public MyAdapter(List<Place> data) {
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_of_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(data.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("place", data.get(holder.getAdapterPosition()));
                    PlaceCardFragment placeCardFragment = new PlaceCardFragment();
                    placeCardFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, placeCardFragment, "PlaceCardFragment").addToBackStack(null).commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView image;
            private final TextView name;
            private final TextView address;
            private final TextView distance;
            private final RatingBar ratingBar;
            private final TextView rating;

            public MyViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                name = itemView.findViewById(R.id.name);
                address = itemView.findViewById(R.id.address);
                distance = itemView.findViewById(R.id.distance);
                ratingBar = itemView.findViewById(R.id.ratingBar2);
                rating = itemView.findViewById(R.id.rating);
            }

            public void bind(Place place) {
                if (place.getPhoto() != null) {
                    Picasso.get().load(place.getPhoto()).into(image);
                } else {
                    Picasso.get().load(place.getIcon()).into(image);
                }
                name.setText(place.getName());
                address.setText(place.getVicinity());
                Location temp = new Location("");
                temp.setLongitude(place.getLongitude());
                temp.setLatitude(place.getLatitude());
                distance.setText(place.getDistance());
                ratingBar.setRating((float) place.getRating());
                rating.setText(String.valueOf(place.getRating()));
            }
        }
    }
}