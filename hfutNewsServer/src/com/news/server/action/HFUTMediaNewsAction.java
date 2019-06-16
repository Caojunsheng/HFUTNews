package com.news.server.action;

import com.news.server.dao.HFUTMediaNewsDao;
import com.news.server.model.HFUTMediaNews;
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
public class HFUTMediaNewsAction extends ActionSupportUtil{
    @Resource
    private HFUTMediaNewsDao hfutMediaNewsDao;
    private HFUTMediaNews hfutMediaNews;

    public HFUTMediaNews getHfutMediaNews() {
        return hfutMediaNews;
    }

    public void setHfutMediaNews(HFUTMediaNews hfutMediaNews) {
        this.hfutMediaNews = hfutMediaNews;
    }
    /**
     * http://localhost:8080/getAllMediaNews
     */
    @Action("getAllMediaNews")
    public void getAllAllNews(){
        List<HFUTMediaNews> mediaNews=hfutMediaNewsDao.selectAllMediaNews();
        if (mediaNews==null){
            renderText("medianews null");
        } else {
            JSONArray json = new JSONArray();
            for (HFUTMediaNews medianew : mediaNews) {
                json.add(medianew.MediaNewsToJSON());
            }
            renderJson(json.toString());
        }
    }
}
