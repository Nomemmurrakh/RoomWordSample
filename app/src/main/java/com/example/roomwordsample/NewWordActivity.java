package com.example.roomwordsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.roomwordsample.databinding.ActivityNewWordBinding;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.roomwordsample.REPLY";

    private ActivityNewWordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(binding.editNewWord.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = binding.editNewWord.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

    }
}