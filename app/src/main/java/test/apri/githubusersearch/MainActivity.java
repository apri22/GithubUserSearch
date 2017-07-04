package test.apri.githubusersearch;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import test.apri.githubusersearch.model.SearchUserResult;
import test.apri.githubusersearch.model.User;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
    ListView.OnScrollListener, MainContract.View {


    SwipeRefreshLayout mSwipeRefresh;
    TextView mNoResultView;
    ListView mListViewResult;
    EditText mSearchView;
    ProgressBar mProgressBar;
    MainPresenter mPresenter;

    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this, this.getApplicationContext());
        mAdapter = new UserListAdapter(this, new ArrayList<User>());
        setupView();
    }

    private void setupView(){
        mSearchView = (EditText)findViewById(R.id.search_edit_text);
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mListViewResult = (ListView) findViewById(R.id.list_result);
        mNoResultView = (TextView) findViewById(R.id.no_result_view);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        mListViewResult.setAdapter(mAdapter);
        mListViewResult.setOnScrollListener(this);
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Utils.hideSoftKeyboard(MainActivity.this);
                    mPresenter.firstCallSearchUser(getSearchKeyword());
                    return true;
                }
                return false;
            }
        });

        mSwipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastItem = firstVisibleItem + visibleItemCount;
        if(totalItemCount == lastItem && mPresenter.shouldLoadMore() && !mAdapter.isLoadingShow()){
            mAdapter.showLoading(true);
            mPresenter.loadMoreSearchUser(getSearchKeyword());
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.firstCallSearchUser(getSearchKeyword());
    }


    private String getSearchKeyword(){
        return mSearchView == null ? "" : mSearchView.getText().toString();
    }


    @Override
    public void stopLoading() {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.showLoading(false);
        if(mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void clearUserResult() {
        mAdapter.mUsers.clear();
    }

    @Override
    public void noInternetConnection() {
        Toast.makeText(MainActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLimit(String errorMessage) {
        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchUserFailed(Throwable t) {
        Toast.makeText(MainActivity.this, R.string.something_when_wrong, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchUserSuccess(SearchUserResult result) {
        mAdapter.mUsers.addAll(result.getUsers());
        mAdapter.notifyDataSetChanged();

        if(result.getTotalCount() == 0){
            mListViewResult.setAlpha(0);
            mNoResultView.setVisibility(View.VISIBLE);
        }else{
            mListViewResult.setAlpha(1);
            mNoResultView.setVisibility(View.GONE);
        }
    }
}
