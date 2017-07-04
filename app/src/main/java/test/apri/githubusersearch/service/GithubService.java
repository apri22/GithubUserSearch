package test.apri.githubusersearch.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.apri.githubusersearch.model.SearchUserResult;

/**
 * GithubUserSearch
 * Created by dwiaprianto on 03 July 2017.
 * Description
 *
 * dwiaprianto22@gmail.com
 */

public interface GithubService {

    @GET("search/users")
    Call<SearchUserResult> searchUser(@Query("q") String keyword,
                                      @Query("page") int page,
                                      @Query("per_page") int limit);
}
