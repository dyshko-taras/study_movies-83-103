package com.dyshkotaras.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @GET("discover/movie?api_key=68cea46f5752df5e407a4dccf82c1522&language=en_US&sort_by=vote_count.desc&include_adult=true&include_video=true&primary_release_date.gte=2015-01-01&vote_count.gte=1000&vote_average.gte=7&vote_average.lte=10&with_watch_monetization_types=flatrate")
    Single<MoviesList> loadMovies(@Query("page") int page);

    @GET("genre/movie/list?api_key=68cea46f5752df5e407a4dccf82c1522&language=en-US")
    Single<GenreList> loadGenres();

    @GET("movie/{id}/videos?api_key=68cea46f5752df5e407a4dccf82c1522&language=en-US")
    Single<TrailerList> loadTrailers(@Path("id") int id);
}
