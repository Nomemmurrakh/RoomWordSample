package com.example.roomwordsample.dao;

import com.example.roomwordsample.models.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WordService {

    @GET("words?_order=asc")
    Call<List<Word>> getAlphabetizedWords();

    @POST("words")
    Call<Word> insert(@Body Word word);
}
