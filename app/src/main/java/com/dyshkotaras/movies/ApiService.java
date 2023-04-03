package com.dyshkotaras.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("discover/movie?api_key=68cea46f5752df5e407a4dccf82c1522&language=ru_RU&sort_by=vote_count.desc&include_adult=true&include_video=true&page=1&primary_release_date.gte=2015-01-01&vote_count.gte=1000&vote_average.gte=7&vote_average.lte=10&with_watch_monetization_types=flatrate")
    Single<MoviesList> loadMovies();
}
