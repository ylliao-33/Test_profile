package com.example.test_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class modify_name extends AppCompatActivity {

    private EditText mEditText;
    private TextView mSave;
    private ImageView mReturn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_name);

        mEditText = findViewById(R.id.et_entered_name);
        Bundle bundle = getIntent().getExtras();
        String mname = bundle.getString("name");
        mEditText.setText(mname);
        mSave = findViewById(R.id.tv_save_modified_name);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changed_name = mEditText.getText().toString();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("back_name",changed_name);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

        mReturn = findViewById(R.id.iv_just_return);
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify_name.this.finish();
            }
        });

    }
}

