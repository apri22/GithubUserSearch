package test.apri.githubusersearch;

import test.apri.githubusersearch.model.SearchUserResult;

/**
 * GithubUserSearch
 * Created by dwiaprianto on 04 July 2017.
 * Description
 *
 * dwiaprianto22@gmail.com
 */

public interface MainContract {

    interface View{
        void stopLoading();
        void clearUserResult();
        void noInternetConnection();
        void onLimit(String errorMessage);
        void searchUserFailed(Throwable t);
        void searchUserSuccess(SearchUserResult result);
    }
}
