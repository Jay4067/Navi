package com.learning.poetry_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.learning.poetry_app.API.ApiClient;
import com.learning.poetry_app.API.ApiInterface;
import com.learning.poetry_app.Response.DeletetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetryData;
    AppCompatButton button;
    ApiInterface apiInterface;
    int poetryId;
    String poetryDataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);

        initialization();
        setToolbar();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p_data = poetryData.getText().toString();
                if(p_data.equals("")){
                    poetryData.setError("Field is Empty");
                }else{
                    callApi(p_data,poetryId+"");
                    Toast.makeText(UpdatePoetry.this, "Call Api", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initialization(){

        toolbar = findViewById(R.id.update_poetry_toolbar);
        poetryData = findViewById(R.id.update_poetry_data);
        button = findViewById(R.id.update_poetry_btn);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);


        //get data from the main activity and set it in edit text
        poetryId = getIntent().getIntExtra("p_id",0);
        poetryDataString = getIntent().getStringExtra("p_data");

        poetryData.setText(poetryDataString);

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

    //Give method two parameters as per ask for parameters in interface in apiInterface
    private void callApi(String p_data,String p_id){

        apiInterface.updatepoetry(p_data, p_id).enqueue(new Callback<DeletetResponse>() {
            @Override
            public void onResponse(Call<DeletetResponse> call, Response<DeletetResponse> response) {

                try{

                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(UpdatePoetry.this, "Data is Updated", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UpdatePoetry.this, "Data is not Updated", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("Update Exception",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletetResponse> call, Throwable t) {
                Log.e("update fail",t.getLocalizedMessage());
            }
        });
    }



}