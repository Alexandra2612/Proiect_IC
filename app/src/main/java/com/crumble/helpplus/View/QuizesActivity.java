package com.crumble.helpplus.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.crumble.helpplus.Model.User.getConnectedUser;

public class QuizesActivity extends AppCompatActivity {
    private ImageView profileIcon6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);
        profileIcon6=(ImageView)findViewById(R.id.profileIcon6);
        setProfPic();

    }

    public void setProfPic() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(getConnectedUser().getImage());

        final long TEN_MEGABYTE = 1024 * 1024 * 10;
        islandRef.getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileIcon6.setImageBitmap(Bitmap.createScaledBitmap(bmp, profileIcon6.getWidth(), profileIcon6.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                profileIcon6.setImageResource(R.drawable.profile_base);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //update UI

    }
    public void goToProfile(View view)
    {
        Intent intent=new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }
    public void goToQuizPage(View view)
    {
        Intent intent=new Intent(this,QuizActivity.class);
        startActivity(intent);
    }
}
