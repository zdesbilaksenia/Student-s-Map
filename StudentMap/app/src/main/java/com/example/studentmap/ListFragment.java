package com.example.studentmap;

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

        mapViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(MapViewModel.class);
        LiveData<List<Place>> data = mapViewModel.getData();

        data.observe((LifecycleOwner) getActivity(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
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
                ratingBar = itemView.findViewById(R.id.ratingBar);
                rating = itemView.findViewById(R.id.rating);
            }

            public void bind(Place place) {
                Picasso.get().load(place.getIcon()).into(image);
                name.setText(place.getName());
                address.setText(place.getVicinity());
                Location temp = new Location("");
                temp.setLongitude(place.getLongitude());
                temp.setLatitude(place.getLatitude());
                distance.setText(String.valueOf((double)(Math.ceil (temp.distanceTo(mapViewModel.getCurrentLocation()) / 100)) / 10) +"км");
                ratingBar.setRating((float) place.getRating());
                rating.setText(String.valueOf(place.getRating()));
            }
        }
    }
}
