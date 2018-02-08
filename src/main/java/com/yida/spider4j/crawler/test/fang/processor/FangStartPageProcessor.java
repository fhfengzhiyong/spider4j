package com.yida.spider4j.crawler.test.fang.processor;

import com.yida.spider4j.crawler.core.Page;
import com.yida.spider4j.crawler.processor.SimpleStartPageProcessor;
import com.yida.spider4j.crawler.processor.param.PageProcessorParam;
import com.yida.spider4j.crawler.selector.Selectable;
import com.yida.spider4j.crawler.utils.Constant;
import com.yida.spider4j.crawler.utils.common.StringUtils;

import java.util.List;

/**
 * @author fengzy
 * @date 2/8/2018
 */
public class FangStartPageProcessor extends SimpleStartPageProcessor{

    public FangStartPageProcessor(PageProcessorParam pageProcessorParam) {
        super(pageProcessorParam);
    }

    /**
     * 探测总页数
     * @param page
     * @param pageSize  每页显示大小
     * @return
     */
    @Override
    public int determineTotalPage(Page page, int pageSize) {
        if (page == null) {
            return 0;
        }
        String text = page.getHtml().jsoup("ui[class=clearfix] > li[class=f1] > strong").get();
        if (StringUtils.isEmpty(text)) {
            return 0;
        }
        text = StringUtils.getNumberFromString(text);
        if (StringUtils.isEmpty(text)) {
            return 0;
        }
        try {
            int total = Integer.valueOf(text);
            if (pageSize <= 0) {
                pageSize = Constant.PAGE_SIZE;
            }
            return (total % pageSize == 0) ? (total / pageSize) : (total / pageSize) + 1;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 探测每页大小
     * @param page
     * @return
     */
    @Override
    public int determinePageSize(Page page) {
        if (page == null) {
            return Constant.PAGE_SIZE;
        }
        List<Selectable> list = page.getHtml().jsoup("div[class=nl_con clearfix] >ul > li").nodes();
        if (null == list || list.size() <= 0) {
            return Constant.PAGE_SIZE;
        }
        return list.size();
    }

    /**
     * 构建下一页
     * @param page
     * @param currentPage
     * @param totalPage
     * @param pageSize
     * @return
     */
    @Override
    public String buildNextPageUrl(Page page, int currentPage, int totalPage, int pageSize) {
        return "http://newhouse.taiyuan.fang.com/house/s/b" + currentPage;
    }
    /**
     * @param @return
     * @return boolean
     * @throws
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: knownTotalPage
     * @Description: 告诉PageProcessor, 从已有的分页页面内容可否已知总页数
     * 需要用户实现
     */
    @Override
    public boolean knownTotalPage() {
        return true;
    }


    /**
     * @param @return
     * @return boolean
     * @throws
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: needPaging
     * @Description: 告诉PageProcessor, 列表页是否需要分页
     */
    @Override
    public boolean needPaging() {
        return true;
    }
}
