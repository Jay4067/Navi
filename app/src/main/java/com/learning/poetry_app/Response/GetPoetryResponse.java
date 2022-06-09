package com.learning.poetry_app.Response;

import com.learning.poetry_app.Model.PoetryModel;

import java.util.List;

//In format of API's Json data you want to retrieve from the api
//create below class as define it's requirement in upper comment
//GetPoetry response work as a model class for the api response
//After this create Api interface


public class GetPoetryResponse  {

    String status;
    String message;
    List<PoetryModel> data;

    public GetPoetryResponse(String status, String message, List<PoetryModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PoetryModel> getData() {
        return data;
    }

    public void setData(List<PoetryModel> data) {
        this.data = data;
    }
}
