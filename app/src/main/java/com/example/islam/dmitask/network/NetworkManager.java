package com.example.islam.dmitask.network;

import com.example.islam.dmitask.db.dto.BookDetail;
import com.example.islam.dmitask.models.Book;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by islam on 10/09/16.
 */
public interface NetworkManager {
    @GET("api/v1/secure/items")
    Observable<ArrayList<Book>> getBooks(@Query("offset") String offset,
                                         @Query("count") String count);

    @GET
    Observable<BookDetail> getBookDetails(@Url String absoluteURL);
}
