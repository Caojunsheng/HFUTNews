package com.news.server.action;

import com.news.server.dao.HFUTNoticeNewsDao;
import com.news.server.model.HFUTNoticeNews;
import com.news.server.utils.ActionSupportUtil;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caojunsheng on 2017/4/23.
 */
@Controller
public class HFUTNoticeNewsAction extends ActionSupportUtil {
    @Resource
    private HFUTNoticeNewsDao hfutNoticeNewsDao;
    private HFUTNoticeNews hfutNoticeNews;

    /**
     * http://localhost:8080/getAllNoticeNews
     */
    @Action("getAllNoticeNews")
    public void getAllNoticeNews() {
        List<HFUTNoticeNews> noticeNews = hfutNoticeNewsDao.selectAllNoticeNews();
        if (noticeNews == null) {
            renderText("noticenews null");
        } else {
            JSONArray json = new JSONArray();
            for (HFUTNoticeNews noticenew : noticeNews) {
                json.add(noticenew.NoticeNewsToJSON());
            }
            renderJson(json.toString());
        }
    }

    public HFUTNoticeNews getHfutNoticeNews() {
        return hfutNoticeNews;
    }

    public void setHfutNoticeNews(HFUTNoticeNews hfutNoticeNews) {
        this.hfutNoticeNews = hfutNoticeNews;
    }
}
