package com.news.server.action;

import com.news.server.dao.AdviceDao;
import com.news.server.model.Advice;
import com.news.server.utils.ActionSupportUtil;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * Created by caojunsheng on 2017/5/22.
 */
@Controller
public class AdviceAction extends ActionSupportUtil{
    @Resource
    private AdviceDao adviceDao;
    private Advice advice;

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    /**
     * http://localhost:8080/addAdvice?advice.content=hello&advice.date=2017/5/22&advice.adviceuser=cjs
     * @throws UnsupportedEncodingException
     */
    @Action(value = "addAdvice")
    public void addAdvice() throws UnsupportedEncodingException {
        advice.setContent(new String(advice.getContent().getBytes("ISO-8859-1"), "UTF-8"));
        advice.setAdviceuser(new String(advice.getAdviceuser().getBytes("ISO-8859-1"), "UTF-8"));
        adviceDao.addAdvice(advice);
        renderText("success");
    }
}
