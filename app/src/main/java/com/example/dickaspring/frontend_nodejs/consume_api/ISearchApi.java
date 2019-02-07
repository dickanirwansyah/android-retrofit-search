package com.example.dickaspring.frontend_nodejs.consume_api;



import com.example.dickaspring.frontend_nodejs.model.Person;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ISearchApi {

    @GET(value = "person")
    Observable<List<Person>> getPersonList();

    @POST(value = "search")
    @FormUrlEncoded
    Observable<List<Person>> searchPerson(@Field("search") String searchQuery);
}
