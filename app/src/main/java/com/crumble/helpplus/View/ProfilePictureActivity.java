package com.crumble.helpplus.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Controller.ProfilePictureController;
import com.crumble.helpplus.R;
import com.google.android.gms.common.internal.Constants;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.View.ProfileActivity.LoadImageFromWebOperations;

public class ProfilePictureActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;
    private RequestQueue queue;
    private Drawable profileImage = null;
    private ImageView myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermission("android.permission.READ_EXTERNAL_STORAGE",100);
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        handleSSLHandshake();
        setContentView(R.layout.activity_profile_picture);
        myImage = (ImageView) findViewById(R.id.profilePic);
        picturePath = getConnectedUser().getImage();

        Thread t = new Thread(()->{
            profileImage = LoadImageFromWebOperations(getConnectedUser().getImage());
            myImage.post(()->{myImage.setImageDrawable(profileImage);});
        });
        t.start();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void goToProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void changePicture(View view) {
        ProfilePictureController.changePicture(this,queue,picturePath);
        //use the picturePath to get picture and save it on database. -- if path is not null
    }

    public void selectPicture(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            // String picturePath contains the path of selected Image
            refreshImage();
        }
    }
    public void refreshImage()
    {
        File imgFile = new  File(picturePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            myImage.setImageBitmap(myBitmap);
        }
    }
    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                ProfilePictureActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            ProfilePictureActivity.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(ProfilePictureActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
    public void updateUI(String text){
        TextView profilePictureResponseText=(TextView)this.findViewById(R.id.profilePictureResponseText);
        profilePictureResponseText.setText(text);
        profilePictureResponseText.setVisibility(View.VISIBLE);
    }
}
