package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caojunsheng.hfutnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.MainNewsModel;

/**
 * Created by caojunsheng on 2017/4/24.
 */

public class NewsDetailImgListAdapter extends BaseAdapter {
    private List<String> imgurlList;
    private Context context;

    public NewsDetailImgListAdapter(List<String> imgurlList, Context context) {
        this.imgurlList = imgurlList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgurlList.size();
    }

    @Override
    public Object getItem(int position) {
        return imgurlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (imgurlList == null || imgurlList.size() == 0) {
            view = null;
        } else {
            if (convertView != null) {
                view = convertView;
            } else {
                view = View.inflate(context, R.layout.newsdetail_img_list_item, null);//将一个布局文件转换成一个view对象
            }
            ImageView item_iv_icon = (ImageView) view.findViewById(R.id.newsdetail_img);
            String imgurl = imgurlList.get(position);
            Picasso.with(context).load(imgurl).placeholder(R.mipmap.loading).error(R.mipmap.error).into(item_iv_icon);
        }
        return view;
    }
}
