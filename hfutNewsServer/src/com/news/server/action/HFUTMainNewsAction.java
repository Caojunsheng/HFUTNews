package com.news.server.action;

import com.news.server.dao.HFUTMainNewsDao;
import com.news.server.model.HFUTMainNews;
import com.news.server.utils.ActionSupportUtil;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caojunsheng on 2017/3/15.
 */
@Controller
public class HFUTMainNewsAction extends ActionSupportUtil{
    @Resource
    private HFUTMainNewsDao hfutMainNewsDao;
    private HFUTMainNews hfutMainNews;

    public HFUTMainNews getHfutMainNews() {
        return hfutMainNews;
    }

    public void setHfutMainNews(HFUTMainNews hfutMainNews) {
        this.hfutMainNews = hfutMainNews;
    }

    /**
     * http://localhost:8080/getAllMainNews
     */
    @Action("getAllMainNews")
    public void getAllMainNews() {
        List<HFUTMainNews> mainNews = hfutMainNewsDao.selectAllMainNews();
        if (mainNews == null) {
            renderText("mainnews null");
        } else {
            JSONArray json = new JSONArray();
            for (HFUTMainNews mainnew : mainNews) {
                json.add(mainnew.MainNewsToJSON());
            }
            renderJson(json.toString());
        }
    }
}
