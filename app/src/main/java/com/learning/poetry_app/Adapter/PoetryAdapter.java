package com.learning.poetry_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.poetry_app.API.ApiClient;
import com.learning.poetry_app.API.ApiInterface;
import com.learning.poetry_app.Model.PoetryModel;
import com.learning.poetry_app.R;
import com.learning.poetry_app.Response.DeletetResponse;
import com.learning.poetry_app.UpdatePoetry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends  RecyclerView.Adapter<PoetryAdapter.ViewHolder> {

    List<PoetryModel> poetryModels;
    Context context;
    ApiInterface apiInterface;              //define apiInterface globally to assign in program everywhere

    public PoetryAdapter(List<PoetryModel> poetryModels, Context context) {
        this.poetryModels = poetryModels;
        this.context = context;
        Retrofit retrofit = ApiClient.getClient();              //To initialize api with the adapter when we call adapter in main activity
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view=  LayoutInflater.from(context).inflate(R.layout.poetry_list_design,parent,false);          //USe this method parent and attach to root rather than set value of root null

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.poetName.setText(poetryModels.get(position).getPoet_name().toString());
        holder.poetry_data.setText(poetryModels.get(position).getPoetry_data().toString());
        holder.date_time.setText(poetryModels.get(position).getDate_time().toString());
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Use position to delete particular data at the position from the list in deletepoetry() method
                deletepoetry(poetryModels.get(position).getId()+"",position);        //use null at the end of declaration to create id in string
            }
        });

        holder.update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id",poetryModels.get(position).getId());
                intent.putExtra("p_data",poetryModels.get(position).getPoetry_data());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView poetName,poetry_data,date_time;
        AppCompatButton update_btn,delete_btn;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            poetName = itemView.findViewById(R.id.tv_poetName);
            poetry_data =itemView.findViewById(R.id.tv_poetryData);
            date_time = itemView.findViewById(R.id.tv_time);

            update_btn = itemView.findViewById(R.id.update_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);



        }
    }



    //create method to delete from the api use this method directly in onCLickListner on delete button

    private void  deletepoetry(String id,int pose ){            //USe pose parameter as position when you call method in onBind

        apiInterface.deletepoetry(id).enqueue(new Callback<DeletetResponse>() {
            @Override
            public void onResponse(Call<DeletetResponse> call, Response<DeletetResponse> response) {

                try{
                    if(response!=null){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                        //In response of Api if status =1 remove data from that position
                        if(response.body().getStatus().equals("1")){
                            poetryModels.remove(pose);
                            notifyDataSetChanged();             //Use to update  your list whenever you insert or delete data from the list
                        }
                    }

                }catch(Exception e){
                    Log.e("Exp",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletetResponse> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
            }
        });

    }
}
