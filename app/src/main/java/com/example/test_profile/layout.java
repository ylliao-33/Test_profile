package com.example.test_profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class layout extends AppCompatActivity {
    private ImageView changeName;
    private ImageView changeEmail;
    private TextView name_text;
    private ImageView mBack;
    private TextView mSave;
    private TextView changeProfile;
    private PopupWindow profile_choice;
    private Uri imageUri;
    private ImageView mRegion;
    private TextView changeRegion;
    private String detected_location;
    private TextView changeMJ;
    private ImageView modifyMJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        //Listener for changing name operation.
        changeName = findViewById(R.id.modify_name);
        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cur_name;
                cur_name = findViewById(R.id.tv_name);
                String name = cur_name.getText().toString();
                Intent intent = new Intent(layout.this, modify_name.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

        //Listener for changing email operation.
        changeEmail = findViewById(R.id.modify_email);
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cur_email;
                cur_email = findViewById(R.id.tv_email);
                String name = cur_email.getText().toString();
                Intent intent = new Intent(layout.this, modify_name.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        // Listener for going back arrow.
        mBack = findViewById(R.id.iv_return);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Listener for updating information.
        mSave = findViewById(R.id.tv_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast mytoast = Toast.makeText(getApplicationContext(), "Information saved successfully！", Toast.LENGTH_LONG);
                mytoast.show();
            }
        });

        //Listener for changing profile textview.
        changeProfile = findViewById(R.id.tv_changeProfile);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.activity_profile_popup, null);
                TextView tv_cancel_pop = (TextView) view.findViewById(R.id.tv_cancel_pop);
                tv_cancel_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        profile_choice.dismiss();
                    }
                });

                profile_choice = new PopupWindow(view, 450, ViewGroup.LayoutParams.WRAP_CONTENT);
                profile_choice.setOutsideTouchable(true);
                profile_choice.setFocusable(true);
                profile_choice.showAtLocation(view, Gravity.CENTER, 0, 0);
                //Listener for choosing photo from album.
                final TextView tv_choose_album = (TextView) view.findViewById(R.id.tv_choose_album);
                tv_choose_album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        startActivityForResult(intent, 2);
                        profile_choice.dismiss();
                    }
                });
                //Listener for taking photo.
                final TextView tv_take_photo = (TextView) view.findViewById(R.id.tv_take_photo);
                tv_take_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File photoTaken = new File(getExternalCacheDir(), "photo_taken.jpg");
                        try//determine whether the picture is existing.
                        {
                            if (photoTaken.exists()) {
                                photoTaken.delete();
                            }
                            photoTaken.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (Build.VERSION.SDK_INT >= 24) {
                            imageUri = FileProvider.getUriForFile(layout.this,
                                    "com.example.test_profile.fileprovider", photoTaken);
                        } else {
                            imageUri = Uri.fromFile(photoTaken);
                        }
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, 3);


                        profile_choice.dismiss();
                    }
                });
            }
        });

        //Modify region;
        mRegion = findViewById(R.id.modify_region);
        mRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start location service here:
                detected_location = "Melbourne";
                //

                AlertDialog.Builder builder = new AlertDialog.Builder(layout.this);
                builder.setMessage("Switch to the current city "+ detected_location +" ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                changeRegion = findViewById(R.id.tv_region);
                                changeRegion.setText(detected_location);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeRegion = findViewById(R.id.tv_region);
                        String name = changeRegion.getText().toString();
                        Intent intent = new Intent(layout.this, modify_name.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 4);
                    }
                }).show();

            }
        });

        //Modify email.
        modifyMJ = findViewById(R.id.modify_major);
        modifyMJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMJ = findViewById(R.id.tv_major);
                String name = changeMJ.getText().toString();
                Intent intent = new Intent(layout.this, modify_name.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivityForResult(intent, 5);
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            String back_name = data.getExtras().getString("back_name");
            name_text = findViewById(R.id.tv_name);
            name_text.setText(back_name);
        }
        if (requestCode == 1){
            String back_name = data.getExtras().getString("back_name");
            name_text = findViewById(R.id.tv_email);
            name_text.setText(back_name);
        }
        if(requestCode == 2 && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            ImageView iv = findViewById(R.id.imageView6);
            iv.setImageURI(data.getData());
        }
        if((requestCode == 3 && resultCode==RESULT_OK)) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                ImageView iv = findViewById(R.id.imageView6);
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == 4 && resultCode==RESULT_OK ){
            String back_name = data.getExtras().getString("back_name");
            changeRegion = findViewById(R.id.tv_region);
            changeRegion.setText(back_name);
        }
        if(requestCode == 5){
            String back_name = data.getExtras().getString("back_name");
            changeMJ = findViewById(R.id.tv_major);
            changeMJ.setText(back_name);
        }

    }
}


