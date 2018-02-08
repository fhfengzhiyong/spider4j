package com.yida.spider4j.crawler.test.fang.processor;

import com.yida.spider4j.crawler.core.Page;
import com.yida.spider4j.crawler.processor.SimpleDetailPageProcessor;
import com.yida.spider4j.crawler.processor.param.PageProcessorParam;
import com.yida.spider4j.crawler.selector.ExpressionType;
import com.yida.spider4j.crawler.utils.common.StringUtils;

/**
 * @author fengzy
 * @date 2/8/2018
 */
public class FangDetailPageProcessor extends SimpleDetailPageProcessor {
    public FangDetailPageProcessor(PageProcessorParam pageProcessorParam) {
        super(pageProcessorParam);
    }

    @Override
    public void process(Page page) {
        //链家编号
        String lianjiaNO = page.getHtml(ExpressionType.JSOUP).jsoup("div[class=houseRecord] > span[class=houseNum]", "text").get();
        if (StringUtils.isNotEmpty(lianjiaNO)) {
            lianjiaNO = lianjiaNO.replace("链家编号：", "").replace("房源编号：", "").replaceAll("\\s+", "");
        }
        page.putField("lianjiaNO", lianjiaNO);
    }
}
