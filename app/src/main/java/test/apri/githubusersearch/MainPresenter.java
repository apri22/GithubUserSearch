package test.apri.githubusersearch;

import org.json.JSONObject;

import android.content.Context;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.apri.githubusersearch.model.SearchUserResult;
import test.apri.githubusersearch.service.RequestBuilder;

/**
 * GithubUserSearch
 * Created by dwiaprianto on 04 July 2017.
 * Description
 *
 * dwiaprianto22@gmail.com
 */

public class MainPresenter {

    private final int TOTAL_PER_PAGE = 10;
    int page = 0;
    boolean isNoResult = false;
    private RequestBuilder mRequestBuilder;
    private Context mContext;

    private MainContract.View mView;

    public MainPresenter(MainContract.View view, Context context){
        this.mView = view;
        this.mContext = context;

        mRequestBuilder = new RequestBuilder();
    }

    public boolean shouldLoadMore(){
        return !isNoResult && page >= 1;
    }


    public void loadMoreSearchUser(String keyword){
        page++;
        searchUser(keyword);
    }

    public void firstCallSearchUser(String keyword){
        page = 1;
        isNoResult = false;
        searchUser(keyword);
    }

    private void searchUser(String keyword) {
        if (!Utils.isNetworkConnectionAvailable(mContext)) {
            mView.stopLoading();
            mView.noInternetConnection();
            return;
        }

        mRequestBuilder.getGithubService().searchUser(keyword, page, TOTAL_PER_PAGE)
            .enqueue(new Callback<SearchUserResult>() {
                @Override
                public void onResponse(Call<SearchUserResult> call, Response<SearchUserResult> response) {

                    mView.stopLoading();

                    if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                        page--;
                        try {
                            String raw = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(raw);
                            mView.onLimit(jsonObject.get("message").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    SearchUserResult result = response.body();
                    if (result != null) {
                        if (page == 1) {
                            mView.clearUserResult();
                        }

                        isNoResult = result.isIncompleteResult();
                        mView.searchUserSuccess(result);
                    }
                }

                @Override
                public void onFailure(Call<SearchUserResult> call, Throwable t) {
                    mView.stopLoading();
                    mView.searchUserFailed(t);
                }
            });
    }

}
