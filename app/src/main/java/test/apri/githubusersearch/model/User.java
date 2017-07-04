package test.apri.githubusersearch.model;

import com.google.gson.annotations.SerializedName;

/**
 * GithubUserSearch
 * Created by dwiaprianto on 03 July 2017.
 * Description
 *
 * dwiaprianto22@gmail.com
 */

public class User {

    @SerializedName("login")
    private String username;
    @SerializedName("avatar_url")
    private String avatarUrl;

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
