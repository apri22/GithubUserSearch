package test.apri.githubusersearch.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * GithubUserSearch
 * Created by dwiaprianto on 04 July 2017.
 * Description
 *
 * dwiaprianto22@gmail.com
 */

public class RequestBuilder {

    private GithubService mService;

    public RequestBuilder() {
        Gson gson = new GsonBuilder()
            .setLenient()
            .create();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
        mService = retrofit.create(GithubService.class);
    }

    public GithubService getGithubService() {
        return mService;
    }
}
