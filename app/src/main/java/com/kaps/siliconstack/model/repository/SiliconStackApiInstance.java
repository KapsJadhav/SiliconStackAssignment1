package com.kaps.siliconstack.model.repository;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.kaps.siliconstack.model.Callback.AddUpdateNoteCallback;
import com.kaps.siliconstack.model.Callback.NoteCallback;
import com.kaps.siliconstack.model.Note;
import com.kaps.siliconstack.model.helper.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class SiliconStackApiInstance {

    private static Retrofit retrofit;
    private static SiliconStackApiInstance siliconStackApiInstance;
    private static SiliconStackApiService siliconStackApiService;
    private static String TAG = "SiliconStackApiInstance";

    public SiliconStackApiInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder()
                            .addNetworkInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request.Builder builder = chain.request().newBuilder();
                                    builder.addHeader("Accept-Language", "en");
                                    Request request = builder.build();
                                    Response response = chain.proceed(request);
                                    return response;
                                }
                            })
                            .connectTimeout(180, TimeUnit.SECONDS)
                            .writeTimeout(180, TimeUnit.SECONDS)
                            .readTimeout(180, TimeUnit.SECONDS).build())
                    .baseUrl(AppConstants.ApiNames.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            siliconStackApiService = retrofit.create(SiliconStackApiService.class);
        }
    }

    public synchronized static SiliconStackApiInstance getInstance() {
        if (siliconStackApiInstance == null) {
            if (siliconStackApiInstance == null) {
                siliconStackApiInstance = new SiliconStackApiInstance();
            }
        }
        return siliconStackApiInstance;
    }

    public void getAllNotes(NoteCallback noteCallback, Context context) {
        if (!AppConstants.isProgressDialogShowing()) {
            AppConstants.showProgressDialog(context);
        }
        final MutableLiveData<List<Note>> loginRegisterResponseMutableLiveData = new MutableLiveData<>();
        siliconStackApiService.getAllNotes()
                .enqueue(new Callback<List<Note>>() {
                    @Override
                    public void onResponse(Call<List<Note>> call, retrofit2.Response<List<Note>> response) {
                        AppConstants.closeProgressDialog();
                        if (response != null && response.body() != null) {
                            JSONObject jsonObjectResponse = null;
                            try {
                                jsonObjectResponse = new JSONObject(new Gson().toJson(response.body()));
                                AppConstants.PrintLog(TAG, "Get All Task Response - " + jsonObjectResponse.toString());
                            } catch (JSONException e) {
                                AppConstants.PrintLog(TAG, "Get All Task Response JSON Error - " + e.getMessage());
                                e.printStackTrace();
                            }
                            loginRegisterResponseMutableLiveData.setValue(response.body());
                            if (response.body() != null) {
                                noteCallback.onSuccess(loginRegisterResponseMutableLiveData.getValue());
                            } else {
                                noteCallback.onError("No Task Found...");
                            }
                        } else {
                            AppConstants.showToastMessage(context, "Something went wrong... try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Note>> call, Throwable t) {
                        AppConstants.closeProgressDialog();
                        noteCallback.onError(t.getMessage());
                        AppConstants.PrintLog(TAG, "Get All Task Response Error -  " + t.getMessage());
                        loginRegisterResponseMutableLiveData.setValue(null);
                    }
                });
    }


    public void addNote(String sUserId, String sTitle, String sBody, String sNote, String sStatus,
                        AddUpdateNoteCallback addUpdateNoteCallback, Context context) {
        if (!AppConstants.isProgressDialogShowing()) {
            AppConstants.showProgressDialog(context);
        }
        AppConstants.PrintLog(TAG, "UserId : " + sUserId);
        AppConstants.PrintLog(TAG, "Title : " + sTitle);
        AppConstants.PrintLog(TAG, "Body : " + sBody);
        AppConstants.PrintLog(TAG, "Note : " + sNote);
        AppConstants.PrintLog(TAG, "Status : " + sStatus);

        final MutableLiveData<Note> userSignUp = new MutableLiveData<>();
        siliconStackApiService.addNote(sUserId, sTitle, sBody, sNote, sStatus)
                .enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, retrofit2.Response<Note> response) {
                        AppConstants.closeProgressDialog();
                        if (response != null && response.body() != null) {
                            JSONObject jsonObjectResponse = null;
                            try {
                                jsonObjectResponse = new JSONObject(new Gson().toJson(response.body()));
                                AppConstants.PrintLog(TAG, "Add Task Response - " + jsonObjectResponse.toString());
                            } catch (JSONException e) {
                                AppConstants.PrintLog(TAG, "Add Task Response JSON Error - " + e.getMessage());
                                e.printStackTrace();
                            }
                            userSignUp.setValue(response.body());
                            if (response.body() != null) {
                                addUpdateNoteCallback.onSuccessAddUpdateNote(userSignUp.getValue());
                            } else {
                                addUpdateNoteCallback.onError("Error");
                            }
                        } else {
                            AppConstants.showToastMessage(context, "Something went wrong... try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {
                        AppConstants.closeProgressDialog();
                        addUpdateNoteCallback.onError(t.getMessage());
                        AppConstants.PrintLog(TAG, "Add Task Response Error -  " + t.getMessage());
                        userSignUp.setValue(null);
                    }
                });
    }


    public void updateNote(String sId, String sUserId, String sTitle, String sBody, String sNote, String sStatus,
                           AddUpdateNoteCallback addUpdateNoteCallback, Context context) {
        if (!AppConstants.isProgressDialogShowing()) {
            AppConstants.showProgressDialog(context);
        }
        AppConstants.PrintLog(TAG, "Id : " + sId);
        AppConstants.PrintLog(TAG, "UserId : " + sUserId);
        AppConstants.PrintLog(TAG, "Title : " + sTitle);
        AppConstants.PrintLog(TAG, "Body : " + sBody);
        AppConstants.PrintLog(TAG, "Note : " + sNote);
        AppConstants.PrintLog(TAG, "Status : " + sStatus);

        final MutableLiveData<Note> userSignUp = new MutableLiveData<>();
        siliconStackApiService.updateNote(sId, sUserId, sTitle, sBody, sNote, sStatus)
                .enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, retrofit2.Response<Note> response) {
                        AppConstants.closeProgressDialog();
                        if (response != null && response.body() != null) {
                            JSONObject jsonObjectResponse = null;
                            try {
                                jsonObjectResponse = new JSONObject(new Gson().toJson(response.body()));
                                AppConstants.PrintLog(TAG, "Update Task Response - " + jsonObjectResponse.toString());
                            } catch (JSONException e) {
                                AppConstants.PrintLog(TAG, "Update Task Response JSON Error - " + e.getMessage());
                                e.printStackTrace();
                            }
                            userSignUp.setValue(response.body());
                            if (response.body() != null) {
                                addUpdateNoteCallback.onSuccessAddUpdateNote(userSignUp.getValue());
                            } else {
                                addUpdateNoteCallback.onError("Error");
                            }
                        } else {
                            AppConstants.showToastMessage(context, "Something went wrong... try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {
                        AppConstants.closeProgressDialog();
                        addUpdateNoteCallback.onError(t.getMessage());
                        AppConstants.PrintLog(TAG, "Update Task Response Error -  " + t.getMessage());
                        userSignUp.setValue(null);
                    }
                });
    }


    public void deleteNote(String sId, String sUserId, AddUpdateNoteCallback addUpdateNoteCallback, Context context) {
        if (!AppConstants.isProgressDialogShowing()) {
            AppConstants.showProgressDialog(context);
        }
        AppConstants.PrintLog(TAG, "Id : " + sId);
        AppConstants.PrintLog(TAG, "UserId : " + sUserId);

        final MutableLiveData<Note> userSignUp = new MutableLiveData<>();
        siliconStackApiService.deleteNote(sId, sUserId)
                .enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, retrofit2.Response<Note> response) {
                        AppConstants.closeProgressDialog();
                        if (response != null && response.body() != null) {
                            JSONObject jsonObjectResponse = null;
                            try {
                                jsonObjectResponse = new JSONObject(new Gson().toJson(response.body()));
                                AppConstants.PrintLog(TAG, "Delete Task Response - " + jsonObjectResponse.toString());
                            } catch (JSONException e) {
                                AppConstants.PrintLog(TAG, "Delete Task Response JSON Error - " + e.getMessage());
                                e.printStackTrace();
                            }
                            userSignUp.setValue(response.body());
                            if (response.body() != null) {
                                addUpdateNoteCallback.onSuccessAddUpdateNote(userSignUp.getValue());
                            } else {
                                addUpdateNoteCallback.onError("Error");
                            }
                        } else {
                            AppConstants.showToastMessage(context, "Something went wrong... try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {
                        AppConstants.closeProgressDialog();
                        addUpdateNoteCallback.onError(t.getMessage());
                        AppConstants.PrintLog(TAG, "Delete Task Response Error -  " + t.getMessage());
                        userSignUp.setValue(null);
                    }
                });
    }


}
