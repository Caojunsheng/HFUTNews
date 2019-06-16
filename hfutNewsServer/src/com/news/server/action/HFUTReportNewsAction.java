package com.news.server.action;

import com.news.server.dao.HFUTReportNewsDao;
import com.news.server.model.HFUTReportNews;
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
public class HFUTReportNewsAction extends ActionSupportUtil {
    @Resource
    private HFUTReportNewsDao hfutReportNewsDao;
    private HFUTReportNews hfutReportNews;

    /**
     * http://localhost:8080/getAllReportNews
     */
    @Action("getAllReportNews")
    public void getAllReportNews() {
        List<HFUTReportNews> reportNews = hfutReportNewsDao.selectAllReportNews();
        if (reportNews == null) {
            renderText("reportnews null");
        } else {
            JSONArray json = new JSONArray();
            for (HFUTReportNews reportnew : reportNews) {
                json.add(reportnew.ReportNewsToJSON());
            }
            renderJson(json.toString());
        }
    }

    public HFUTReportNews getHfutReportNews() {
        return hfutReportNews;
    }

    public void setHfutReportNews(HFUTReportNews hfutReportNews) {
        this.hfutReportNews = hfutReportNews;
    }
}
