package com.crumble.helpplus.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.Model.User;
import com.crumble.helpplus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.View.FriendsActivity.friendUser;
import static com.crumble.helpplus.View.ProfileActivity.LoadImageFromWebOperations;

public class FriendPageActivity extends AppCompatActivity {
    private FirebaseUser firebaseConnectedUser;
    private ImageView profileIcon1;
    private TextView usernameText1;
    private TextView gradeText1;
    private TextView idText1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseConnectedUser = User.getFirebaseConnectedUser();
        setContentView(R.layout.activity_friend_page);
        profileIcon1=(ImageView)this.findViewById(R.id.profileIcon1);
        usernameText1=(TextView)this.findViewById(R.id.usernameText1);
        gradeText1=(TextView)this.findViewById(R.id.gradeText1);
        idText1=(TextView)this.findViewById(R.id.idText1);

        setProfPic();

        usernameText1.setText(friendUser.getNickname());
        gradeText1.setText(gradeText1.getText()+" "+(((Double)friendUser.getAverageGrade()).toString()));
        idText1.setText(idText1.getText()+" "+(((Integer)friendUser.getId()).toString())+"  ");

    }

    public void setProfPic()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(friendUser.getImage());

        final long TEN_MEGABYTE = 1024 * 1024 * 10;
        islandRef.getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileIcon1.setImageBitmap(Bitmap.createScaledBitmap(bmp, profileIcon1.getWidth(), profileIcon1.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                profileIcon1.setImageResource(R.drawable.profile_base);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        //update UI

    }
    public void goToFriendsList(View view)
    {
        Intent intent=new Intent(this,FriendsActivity.class);
        startActivity(intent);
    }

}
