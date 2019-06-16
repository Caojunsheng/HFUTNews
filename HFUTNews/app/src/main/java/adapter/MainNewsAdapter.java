package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.caojunsheng.hfutnews.MainActivity;
import com.example.caojunsheng.hfutnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.MainNewsModel;

/**
 * Created by caojunsheng on 2017/4/18.
 */

public class MainNewsAdapter extends BaseAdapter {
    private ArrayList<MainNewsModel> arrayList;
    private Context context;

    public MainNewsAdapter(ArrayList<MainNewsModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        //1.复用converView优化listview,创建一个view作为getview的返回值用来显示一个条目
        if (convertView != null) {
            view = convertView;
        } else {
            view = View.inflate(context, R.layout.news_item, null);//将一个布局文件转换成一个view对象
        }
        //2.获取view上的子控件对象
        ImageView item_iv_icon = (ImageView) view.findViewById(R.id.iv_news);
        TextView item_tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView item_tv_date = (TextView) view.findViewById(R.id.tv_date);
        //3.获取postion位置条目对应的list集合中的新闻数据，Bean对象
        MainNewsModel newsBean = arrayList.get(position);
        //4.将数据设置给这些子控件做显示
//        item_iv_icon.setImageDrawable(newsBean.);//设置imageView的图片
        item_tv_title.setText(newsBean.getTitle());
        item_tv_date.setText(newsBean.getDate());
//        Log.i("imgurl链接:", newsBean.getId()+".....图片个数"+newsBean.getImgurl().size());
        if (newsBean.getImgurl().size()==0) {
            item_iv_icon.setVisibility(View.GONE);
        } else {
            Picasso.with(context).load(newsBean.getImgurl().get(0)).fit().placeholder(R.mipmap.loading).error(R.mipmap.error).into(item_iv_icon);
//            Glide.with(context).load(newsBean.getImgurl()).into(item_iv_icon);
        }
        return view;
    }
}
