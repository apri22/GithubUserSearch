package test.apri.githubusersearch;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.apri.githubusersearch.model.User;

/**
 * GithubUserSearch
 * Created by dwiaprianto on 04 July 2017.
 * Description
 *
 * dwiaprianto22@gmail.com
 */

public class UserListAdapter extends BaseAdapter {

    private boolean showLoading = false;
    public List<User> mUsers;
    private Context mContext;

    public UserListAdapter(Context context, List<User> users) {
        mUsers = users;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 0) {
            UserListHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.user_list_view_item, parent, false);
                viewHolder = new UserListHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (UserListHolder) convertView.getTag();
            }

            User user = getItem(position);
            if(user != null) {
                viewHolder.mTextName.setText(user.getUsername());
                Picasso.with(mContext).load(user.getAvatarUrl()).into(viewHolder.mAvatar);
            }
        }else{
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.bottom_loading, parent, false);
            }
            convertView.findViewById(R.id.progressBar2).setVisibility(isLoadingShow() ? View.VISIBLE : View.GONE);
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mUsers.size() == position && showLoading ? 1 : 0;
    }

    @Override
    public int getCount() {
        return showLoading ? mUsers.size() + 1 : mUsers.size();
    }

    @Override
    public User getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    void showLoading(boolean show){
        showLoading = show;
        notifyDataSetChanged();
    }

    boolean isLoadingShow(){
        return showLoading;
    }

    private class UserListHolder{
        private TextView mTextName;
        private ImageView mAvatar;

        UserListHolder(View view){
            mTextName = (TextView)view.findViewById(R.id.textView);
            mAvatar = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
