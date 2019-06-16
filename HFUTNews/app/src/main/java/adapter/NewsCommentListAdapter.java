package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caojunsheng.hfutnews.AllNewsDetailActivity;
import com.example.caojunsheng.hfutnews.R;

import java.util.List;

/**
 * Created by caojunsheng on 2017/5/22.
 */

public class NewsCommentListAdapter extends BaseAdapter {
    private List<String> commentList;
    private Context context;
    private EditText etComment;

    public NewsCommentListAdapter(List<String> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        Activity activity = (Activity) context;
//        Log.i("test",activity.toString());
        if (commentList == null || commentList.size() == 0) {
            view = null;
        } else {
            if (convertView != null) {
                view = convertView;
            } else {
                view = View.inflate(context, R.layout.newsdetail_comment_list_item, null);//将一个布局文件转换成一个view对象
            }
            TextView item_tv_commentuser = (TextView) view.findViewById(R.id.tv_commentuser);
            TextView item_tv_commentcontent = (TextView) view.findViewById(R.id.tv_commentcontent);
            etComment = (EditText) activity.findViewById(R.id.et_comment);
            String str = commentList.get(position);
            String user = str.substring(0, str.indexOf(";")) + "：";
            String content = str.substring(str.indexOf(";") + 1, str.length());
            item_tv_commentuser.setText(user);
            item_tv_commentcontent.setText(content);
        }
        notifyDataSetChanged();
        return view;
    }
}
