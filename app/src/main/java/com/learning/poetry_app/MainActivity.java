package com.learning.poetry_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.learning.poetry_app.API.ApiClient;
import com.learning.poetry_app.API.ApiInterface;
import com.learning.poetry_app.Adapter.PoetryAdapter;
import com.learning.poetry_app.Model.PoetryModel;
import com.learning.poetry_app.Response.GetPoetryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
    ApiInterface apiInterface;      //define Api interface & pass instances of retrofit and api inteface in initialization method below
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();                                   //create different methods for initialization and adapter
        getdata();          //call getData method which contains data in the adapter
        setSupportActionBar(toolbar);

//        setAdapter(poetryModelList);                  //remove calling of setAdapter() method and define it in the response to show response data into the adapter

    }

    private void initialize(){

        recyclerView = findViewById(R.id.poetry_recyclerView);

        Retrofit retrofit = ApiClient.getClient();              //After define api interface create Instance of Retrofit
        apiInterface = retrofit.create(ApiInterface.class);
        toolbar = findViewById(R.id.main_toolbar);

    }

    //Use this method to get data from the api using getpoetry[Created in API interface] call interface and using enqueue method
    private void getdata(){

        apiInterface.getpoetry().enqueue(new Callback<GetPoetryResponse>() {            //enque method with Callback interface
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {

                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){        //retrieve data using status parms from the api
                            setAdapter(response.body().getData());          //send data into recyclerView using setAdapter method and get data into adapter using response.getData
                        }
                        else{
                            Toast.makeText(MainActivity.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch(Exception e ){
                    Log.e("exp",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());       //getLocalizedMessage() method to print failure
            }
        });
    }

    //Use separate method for adapter to set data from the api
    private void setAdapter(List<PoetryModel>poetryModels){

        poetryAdapter = new PoetryAdapter(poetryModels,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(poetryAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.add_poetry:
                Intent intent = new Intent(MainActivity.this,AddPoetry.class);
                startActivity(intent);
                Toast.makeText(this, "Add poetry clicked", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}