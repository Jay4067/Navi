package com.learning.poetry_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learning.poetry_app.API.ApiClient;
import com.learning.poetry_app.API.ApiInterface;
import com.learning.poetry_app.Response.DeletetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetName,poetryData;
    AppCompatButton button;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);

        initialization();
        setToolbar();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String poetryDataString = poetryData.getText().toString();
                String poetNameString = poetName.getText().toString();

                if(poetryDataString.equals("")){

                    poetryData.setError("Field is Empty");

                }else{
                    if(poetNameString.equals("")){
                        poetName.setError("Field is empty");
                    }
                    else{
                        callApi(poetryDataString,poetNameString);
                    }
                }
            }
        });
    }

    private void initialization(){
        toolbar = findViewById(R.id.add_poetry_toolbar);
        poetName = findViewById(R.id.add_poet_name);
        poetryData = findViewById(R.id.add_poetry_data);
        button = findViewById(R.id.submit_btn);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

    }

    //Draw focus on toolbar's method
    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);              //To provide back button in toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();                           //used finish method to end activity
            }
        });
    }

    //callApi method for each query or api
    //After creating this method pass this mwthod to submit button

    private void callApi(String poetryData , String poetryName){

        apiInterface.addpoetry(poetryData,poetryName).enqueue(new Callback<DeletetResponse>() {
            @Override
            public void onResponse(Call<DeletetResponse> call, Response<DeletetResponse> response) {

                try{
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(AddPoetry.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddPoetry.this, "Not Added", Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    Log.e("exception",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletetResponse> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }






}


