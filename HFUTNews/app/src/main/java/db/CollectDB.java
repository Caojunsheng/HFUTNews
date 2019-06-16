package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.AllNewsModel;

/**
 * Created by caojunsheng on 2017/5/19.
 */

public class CollectDB extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "COLLECT.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "collect_table";
    private final static String COLLECT_ID = "collect_id";
    private final static String COLLECT_DATE = "collect_date";
    private final static String COLLECT_TITLE = "collect_title";
    private final static String COLLECT_CONTENT = "collect_content";
    private final static String COLLECT_WRITER = "collect_writer";
    private final static String COLLECT_PHOTOER = "collect_photoer";
    private final static String COLLECT_EDITOR = "collect_editor";
    private final static String COLLECT_URL = "collect_url";
    private final static String COLLECT_IMGURL = "collect_imgurl";
    private List<String> urls = new ArrayList<>();

    public CollectDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COLLECT_ID
                + " INTEGER primary key autoincrement, " + COLLECT_DATE + " text, "
                + COLLECT_TITLE + " text," + COLLECT_CONTENT + " text,"
                + COLLECT_WRITER + " text," + COLLECT_PHOTOER + " text,"
                + COLLECT_EDITOR + " text," + COLLECT_URL + " text,"
                + COLLECT_IMGURL + " text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db
                .query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //增加操作
    public long insert(String date, String title, String content, String writer, String photoer, String editor, String url, String imgurl) {
        SQLiteDatabase db = this.getWritableDatabase();
/* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(COLLECT_DATE, date);
        cv.put(COLLECT_TITLE, title);
        cv.put(COLLECT_CONTENT, content);
        cv.put(COLLECT_WRITER, writer);
        cv.put(COLLECT_PHOTOER, photoer);
        cv.put(COLLECT_EDITOR, editor);
        cv.put(COLLECT_URL, url);
        cv.put(COLLECT_IMGURL, imgurl);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    //删除操作
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLLECT_ID + " = ?";
        String[] whereValue = {Integer.toString(id)};
        db.delete(TABLE_NAME, where, whereValue);
    }

    public List<AllNewsModel> query() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AllNewsModel> newsList = new ArrayList<AllNewsModel>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        //String date, String title, String content, String writer, String photoer, String editor, String url, String imgurl
        while (c.moveToNext()) {
            AllNewsModel news = new AllNewsModel();
            news.setId(c.getInt(c.getColumnIndex(COLLECT_ID)));
            news.setDate(c.getString(c.getColumnIndex(COLLECT_DATE)));
            news.setTitle(c.getString(c.getColumnIndex(COLLECT_TITLE)));
            news.setContent(c.getString(c.getColumnIndex(COLLECT_CONTENT)));
            news.setWriter(c.getString(c.getColumnIndex(COLLECT_WRITER)));
            news.setPhotoer(c.getString(c.getColumnIndex(COLLECT_PHOTOER)));
            news.setEditor(c.getString(c.getColumnIndex(COLLECT_EDITOR)));
            news.setUrl(c.getString(c.getColumnIndex(COLLECT_URL)));
            news.setImgurl(urls);
            newsList.add(news);
        }
        c.close();
        return newsList;
    }
}
