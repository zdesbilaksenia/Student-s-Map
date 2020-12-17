package com.example.studentmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmap.Network.RequestMakerModel;
import com.example.studentmap.RoomDb.Entity.Post;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class FeedListFragment extends Fragment {

    ArrayList<Post> postList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.feed_recycler);
        RequestMakerModel model = new ViewModelProvider(this).get(RequestMakerModel.class);
        //LiveData<ArrayList<com.example.studentmap.Network.Entity.Post>> data = model.getAllPosts();
        OkHttpClient client = new OkHttpClient();
        model.GetAllPosts(client);

        /*data.observe(getViewLifecycleOwner(), new Observer<ArrayList<com.example.studentmap.Network.Entity.Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                postList.addAll(posts);
            }
        });*/

        for (int i = 0; i < 10; i++){
            Post post = new Post();
            postList.add(post);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        feedAdapter adapter = new feedAdapter(postList);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public class feedAdapter extends RecyclerView.Adapter<feedViewHolder>{

        ArrayList<Post> mData;

        public feedAdapter(ArrayList<Post> postList){
            mData = postList;
        }

        @NonNull
        @Override
        public feedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
            return new feedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull feedViewHolder holder, int position) {
            Post post = mData.get(position);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    public class feedViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout post;
        public feedViewHolder(@NonNull View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.postContainer);
        }
    }
}
