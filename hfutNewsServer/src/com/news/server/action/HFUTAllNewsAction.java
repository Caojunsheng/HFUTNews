package com.news.server.action;

import com.news.server.dao.HFUTAllNewsDao;
import com.news.server.model.HFUTAllNews;
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
public class HFUTAllNewsAction extends ActionSupportUtil{
    @Resource
    private HFUTAllNewsDao hfutAllNewsDao;
    private HFUTAllNews hfutAllNews;

    public HFUTAllNews getHfutAllNews() {
        return hfutAllNews;
    }

    public void setHfutAllNews(HFUTAllNews hfutAllNews) {
        this.hfutAllNews = hfutAllNews;
    }

    /**
     * http://localhost:8080/getAllAllNews
     */
    @Action("getAllAllNews")
    public void getAllAllNews(){
        List<HFUTAllNews> allNews=hfutAllNewsDao.selectAllAllNews();
        if (allNews==null){
            renderText("allnews null");
        } else {
            JSONArray json = new JSONArray();
            for (HFUTAllNews allnew : allNews) {
                json.add(allnew.AllNewsToJSON());
            }
            renderJson(json.toString());
        }
    }
}
