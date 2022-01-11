package com.example.roomwordsample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.roomwordsample.adapters.WordListAdapter;
import com.example.roomwordsample.databinding.ActivityMainBinding;
import com.example.roomwordsample.models.Word;
import com.example.roomwordsample.viewmodel.LiveConnection;
import com.example.roomwordsample.viewmodel.WordViewModel;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_NEW_WORD = 1;

    private ActivityMainBinding binding;
    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listOfWords.setLayoutManager(new LinearLayoutManager(this));
        WordListAdapter wordAdapter = new WordListAdapter();
        binding.listOfWords.setAdapter(wordAdapter);

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        LiveConnection liveConnection = new LiveConnection(this);
        liveConnection.observe(this, isNetworkAvailable -> {
            Log.d("TAG", "onCreate: " + isNetworkAvailable);
            wordViewModel.setNetworkAvailable(isNetworkAvailable);
            wordViewModel.getAlphabetizedWords().observe(this, wordAdapter::submitList);
        });

        binding.fab.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, NewWordActivity.class), REQUEST_NEW_WORD);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NEW_WORD && resultCode == RESULT_OK){
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            wordViewModel.insert(word);
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    "Word cannot be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }
}