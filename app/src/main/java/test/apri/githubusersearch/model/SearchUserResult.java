package test.apri.githubusersearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * GithubUserSearch
 * Created by dwiaprianto on 04 July 2017.
 * Description
 *
 * dwiaprianto22@gmail.com
 */

public class SearchUserResult {

    @SerializedName("total_count")
    private int totalCount;
    @SerializedName("incomplete_results")
    private boolean incompleteResult;
    @SerializedName("items")
    private List<User> users;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResult() {
        return incompleteResult;
    }

    public List<User> getUsers() {
        return users;
    }
}
