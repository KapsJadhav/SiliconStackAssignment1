package com.kaps.siliconstack.model.repository;


import com.kaps.siliconstack.model.Note;
import com.kaps.siliconstack.model.helper.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SiliconStackApiService {


    @GET(AppConstants.ApiNames.GET_ALL_TASK)
    Call<List<Note>> getAllNotes();

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.ADD_TASK)
    Call<Note> addNote(@Field("user_id") String sUserId, @Field("title") String sTitle,
                       @Field("body") String sBody, @Field("note") String sNote,
                       @Field("status") String sStatus);


    @FormUrlEncoded
    @POST(AppConstants.ApiNames.EDIT_TASK)
    Call<Note> updateNote(@Field("id") String sId, @Field("user_id") String sUserId,
                        @Field("title") String sTitle, @Field("body") String sBody,
                        @Field("note") String sNote, @Field("status") String sStatus);


    @FormUrlEncoded
    @POST(AppConstants.ApiNames.DELETE_TASK)
    Call<Note> deleteNote(@Field("id") String sId, @Field("user_id") String sUserId);

}