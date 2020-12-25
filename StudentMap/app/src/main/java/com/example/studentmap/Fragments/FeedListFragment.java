package com.example.studentmap.Fragments;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.studentmap.Network.Entity.Post;
import com.example.studentmap.Network.Entity.User;
import com.example.studentmap.Network.RequestMakerModel;
import com.example.studentmap.R;
import com.example.studentmap.RoomDb.Entity.Postdb;
import com.example.studentmap.RoomDb.RoomViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class FeedListFragment extends Fragment {

    ArrayList<Post> postList = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();
    boolean networkFlag;
    OkHttpClient client;
    String img;
    SharedPreferences myPreferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPreferences = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed, container, false);
        Log.d("InonCreateView", "true");
        RecyclerView recyclerView = rootView.findViewById(R.id.feed_recycler);


        if(isOnline() == true){
            RequestMakerModel model = new ViewModelProvider(this).get(RequestMakerModel.class);
            LiveData<ArrayList<Post>> data = model.getAllPosts();
            OkHttpClient client = new OkHttpClient();
            Log.d("Network_sTATUS", "Online");
            networkFlag = true;
            model.GetAllPosts(client);
            data.observe(getViewLifecycleOwner(), new Observer<ArrayList<com.example.studentmap.Network.Entity.Post>>() {
                @Override
                public void onChanged(ArrayList<Post> posts) {
                    postList.clear();
                    postList.addAll(posts);
                    addToDb(posts);


                    RequestMakerModel model = ViewModelProviders.of(getActivity()).get(RequestMakerModel.class);
                    LiveData<ArrayList<User>> data = model.getAllUsersData();
                    OkHttpClient client = new OkHttpClient();


                    model.GetAllUsers(client);

                    data.observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
                                @Override
                                public void onChanged(ArrayList<User> users) {
                                    userList.addAll(users);
                                    Log.d("List", users.get(0).getName());

                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    feedAdapter adapter = new feedAdapter(postList,userList);
                                    recyclerView.setAdapter(adapter);
                                }
                            });

//                        @Override
//                        public void onChanged(User user) {
//                            if(user.getName() == "NOT_FOUND") {
//
//                                Log.d("Answer", "User not found");
//
//                            }else{
//                                Glide.with(getContext()).load(user.getImg()).into(holder.avatar);
////                            Log.d("Image Url", user.getImg());
//                                Log.d("User", user.getLogin());
//
//                            }
//
//                        }
//                    });


//                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    feedAdapter adapter = new feedAdapter(postList,userList);
//                    recyclerView.setAdapter(adapter);




                }
            });
        }else{
            Log.d("Network_sTATUS", "OFline");
            networkFlag = false;
            RoomViewModel roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
            roomViewModel.getAllPosts().observe(getViewLifecycleOwner(), new Observer<List<Postdb>>() {
                @Override
                public void onChanged(List<Postdb> postdbs) {
                    Log.d("Size", Integer.toString(postdbs.size()));

                    ArrayList<Post> postsList = new ArrayList<>();

                    for(int i =0; i < postdbs.size(); i++){
                        Post post = new Post();

                        post.setRating(postdbs.get(i).getRating());
                        post.setLogin(postdbs.get(i).getLogin());
                        post.setName(postdbs.get(i).getName());
                        post.setLocation(postdbs.get(i).getLocation());
                        post.setText(postdbs.get(i).getText());
                        post.setDate(postdbs.get(i).getDate());
                        post.setImg(postdbs.get(i).getImg());

//                        File f =new File(postdbs.get(i).getImg(), postdbs.get(i).getName() + ".jpg");
//                        try {
//                            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }


                        postsList.add(post);
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    feedAdapter adapter = new feedAdapter(postsList, new ArrayList<User>());
                    recyclerView.setAdapter(adapter);


                }
            });
        }





        return rootView;
    }




    void addToDb(ArrayList<Post> posts) {

         Postdb postdb;
        RoomViewModel roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        roomViewModel.deleteAllPosts();

        for ( int i = 0; i < posts.size(); i++) {
            final int a = i;


//            postdb.setName(posts.get(i).getName());
//            postdb.setLocation(posts.get(i).getLocation());
//            postdb.setDate(posts.get(i).getDate());
//            postdb.setText(posts.get(i).getText());
//            //postdb.setImg(posts.get(i).getImg());
//            postdb.setRating(posts.get(i).getRating());
//            postdb.setLogin(posts.get(i).getLogin());
          String postsName = posts.get(i).getName();




            Glide.with(getContext())
                    .asBitmap().load(posts.get(i).getImg())
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                            Bitmap image = resource;
                            ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
                            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                            // Create imageDir
                            File mypath = new File(directory, postsName + ".jpg");
                            FileOutputStream fos = null;

                            try {
                                fos = new FileOutputStream(mypath);
                               image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }finally {

                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            Postdb postdb = new Postdb();

                            postdb.setName(posts.get(a).getName());
                            postdb.setLocation(posts.get(a).getLocation());
                            postdb.setDate(posts.get(a).getDate());
                            postdb.setText(posts.get(a).getText());
                            //postdb.setImg(posts.get(i).getImg());
                            postdb.setRating(posts.get(a).getRating());
                            postdb.setLogin(posts.get(a).getLogin());
                            String postsName = posts.get(a).getName();
                            postdb.setImg(mypath.getAbsolutePath());
                            Log.d("Directory",mypath.getAbsolutePath() );

                            roomViewModel.addPost(postdb);




                            return false;

                        }
                    }).submit();


//


        }
    }

    public class feedAdapter extends RecyclerView.Adapter<feedViewHolder>{

        ArrayList<Post> mData;
        ArrayList<User> users;



        public feedAdapter(ArrayList<Post> postList, ArrayList<User> userList){
            mData = postList;
            users = userList;
        }


        @NonNull
        @Override
        public feedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
            return new feedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull feedViewHolder holder, int position) {
            Post post = mData.get(mData.size()-1-position);
            holder.userName.setText(post.getLogin());
            holder.place.setText(post.getLocation());
            holder.review.setText(post.getText());
            holder.ratingBar.setRating(post.getRating());
            String login = post.getLogin();


            Log.d("Rating", Float.toString(post.getRating()));





            if(networkFlag == true){
                String imgUri = getImg(users,post.getLogin());
                Log.d("InRecyclerView", "Online");
                Glide.with(getContext()).load(post.getImg()).into(holder.photo);
                //Log.d("ImageUri",imgUri);
                Glide.with(getContext()).load(imgUri).into(holder.avatar);

//                RequestMakerModel model = ViewModelProviders.of(getActivity()).get(RequestMakerModel.class);
//                LiveData<User> data = model.getUserData();
//                client = new OkHttpClient();
//
//
//                model.GetUserByLogin(client,login);
//
//                data.observe(getViewLifecycleOwner(), new Observer<User>() {
//                    @Override
//                    public void onChanged(User user) {
//                        if(user.getName() == "NOT_FOUND") {
//
//                            Log.d("Answer", "User not found");
//
//                        }else{
//                            Glide.with(getContext()).load(user.getImg()).into(holder.avatar);
////                            Log.d("Image Url", user.getImg());
//                            Log.d("User", user.getLogin());
//
//                        }
//
//                    }
//                });



            }else{
                Log.d("InRecyclerView", "OFline");
                if(post.getImg() != null) {

                    Uri savedImageUri = Uri.parse(post.getImg());
                    Log.d("savedImageUri", savedImageUri.toString());
                    holder.photo.setImageURI(savedImageUri);
                }
            }

//                File f =new File(post.getImg(), post.getName());
//                try {
//                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//                    holder.photo.setImageBitmap(b);
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }



           //Glide.with(getContext()).load(post.getImg()).into(holder.photo);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    public class feedViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout post;
        public ImageView avatar;
        public TextView userName;
        public TextView place;
        public ImageView photo;
        public TextView review;
        RatingBar ratingBar;
        public feedViewHolder(@NonNull View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.postContainer);
            avatar = itemView.findViewById(R.id.avatar);
            userName = itemView.findViewById(R.id.user_name);
            place = itemView.findViewById(R.id.place);
            photo = itemView.findViewById(R.id.photo);
            review = itemView.findViewById(R.id.review);
            ratingBar = itemView.findViewById(R.id.ratingBar1);
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm =  (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


//    void getUser(String login, String password) {
//        RequestMakerModel model = ViewModelProviders.of(this).get(RequestMakerModel.class);
//        LiveData<User> data = model.getUserData();
//        client = new OkHttpClient();
//
//        model.GetUserByLoginAndPassword(client,login,password);
//
//        data.observe(getViewLifecycleOwner(), new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//                if(user.getName() == "NOT_FOUND") {
//
//                    Log.d("Answer", "User not found");
//
//
//                }else{
//
//
//
//                }
//
//            }
//        });
//
//    }

    String getImg(ArrayList<User> user, String login){

        Log.d("Login", login);

         for(int i =0; i < user.size(); i++) {
             Log.d("login",user.get(i).getLogin());
             if(user.get(i).getLogin().equals(login)){
                 Log.d("ImageUri", user.get(i).getImg());
                 return user.get(i).getImg();

             }
         }
         return "";
    }

}
