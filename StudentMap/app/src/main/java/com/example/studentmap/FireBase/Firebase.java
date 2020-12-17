package com.example.studentmap.FireBase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Firebase {

    MutableLiveData<String> livedataUri;

    public LiveData<String> getUridata(){
        if(livedataUri == null){
            livedataUri = new MutableLiveData<>();
        }
        return livedataUri;

    }

    public void uploadImage(Uri imageUri, StorageReference mStorageRef) {


        final StorageReference mRef = mStorageRef.child(System.currentTimeMillis() + "_image");
        UploadTask up = mRef.putFile(imageUri);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                // Ссылка на картинку
                Log.d("URI", task.getResult().toString());
                livedataUri.postValue(task.getResult().toString());

            }
        });
    }


}