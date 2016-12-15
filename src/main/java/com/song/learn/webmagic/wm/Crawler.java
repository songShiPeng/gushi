package com.song.learn.webmagic.wm;

import com.google.gson.Gson;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.BloomFilterDuplicateRemover;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import  java.io.*;

/**
 * Created by yuecy on 16-10-14.
 */
public class Crawler implements PageProcessor {

    private static int limit = 10;  //最大抓取数量
    private static int examine = 1; //0为测试，1为实际抓取并入库
    private static int count = 0;   //不用动
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    public static void main(String[] args) {
        new FileEdit("/home/songhsipeng/a.txt");
        BloomFilterDuplicateRemover bloomFilterDuplicateRemover = new BloomFilterDuplicateRemover(9999);
        Spider.create(new Crawler()).addUrl("http://www.gushiwen.org/gushi/quansong.aspx")
                .setScheduler(new QueueScheduler()
                        .setDuplicateRemover(bloomFilterDuplicateRemover))
                .addPipeline(new FilePipline()).thread(5).start();
                //.addPipeline(new FilePipeline("F://webmagic//")).thread(5).start();
                // .addPipeline(new JsonFilePipeline("F:\\gushiwen\\")).thread(5).start();

    }


    public void process(Page page) {

        //Client client1 = client.getclient();
        List<String> list = null;
        list = page.getHtml().links().regex("http://[www|so]+\\.gushiwen\\.org/[view|GuShiWen]+_\\S+\\.aspx").all();
        page.addTargetRequests(list);
//        String url = page.getUrl().get();
//        String user = page.getHtml().xpath("//div[@class='pls favatar']/div[1]/div/a/text()").toString();
       // page.putField("title",page.getHtml().xpath("//h1/text()").toString());
        String title = page.getHtml().xpath("//h1/text()").toString();
       // page.putField("content",page.getHtml().xpath("//div[@class='authorShow']/p/text()").toString());
        String content = page.getHtml().xpath("//div[@class='authorShow']/p/text()").all().toString();
        String appreciation = page.getHtml().xpath("//div[@class='authorShow']/p/allText()").all().toString();
       // String appreciation1 ="";
        //String appreciation2 ="";


            // appreciation = page.getHtml().xpath("//div[@class='shileft']/div[8]/allText()").all().toString();
       /* if (page.getHtml().xpath("//div[@class='shileft']/div[8]/allText()").all().toString() == null){
            if (page.getHtml().xpath("//div[@class='shileft']/div[10]/allText()").all().toString() == null)
                appreciation2 = page.getHtml().xpath("//div[@class='shileft']/div[11]/allText()").all().toString();
            else
                appreciation2 = page.getHtml().xpath("//div[@class='shileft']/div[10]/allText()").all().toString();
        }
        else
            appreciation2 = page.getHtml().xpath("//div[@class='shileft']/div[8]/allText()").all().toString();*/
//        String publishTime = "";
//        if (page.getHtml().xpath("//div[@class='wp cl']/div[2]/div[1]/table/tbody/tr/td[2]/div[1]/div/div[2]/em/span/@title").toString() != null)
//            publishTime = page.getHtml().xpath("//div[@class='wp cl']/div[2]/div[1]/table/tbody/tr/td[2]/div[1]/div/div[2]/em/span/@title").toString();
//        else
//            publishTime = page.getHtml().xpath("//table[@class='plhin']/tbody/tr/td[2]/div[1]/div/div[2]/em/text()").regex("\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{2}:\\d{2}:\\d{2}").toString();
//        String category = page.getHtml().xpath("//div[@class='bm cl']/div/a[4]/text()").all().toString();
//        System.out.println(url);
//        System.out.println(user);
          System.out.println(title);
          System.out.println(content);
          System.out.println(appreciation);
       //System.out.println(appreciation1);
       // System.out.println(appreciation2);
//        System.out.println(publishTime);
//        System.out.println(category);
        if (examine == 1) {
            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("url", url);
//            map.put("user", user);
             map.put("title", title);
             map.put("content", content);
             map.put("appreciation", appreciation);
           //map.put("appreciation1", appreciation1);
           // map.put("appreciation2", appreciation2);
//            map.put("publishTime", publishTime);
//            map.put("category", category);
            String s = new Gson().toJson(map);
            //IndexResponse response = client1.prepareIndex("poem", "poem").setSource(s).get();
            page.putField("content",s);
        }
        count++;
        if (count >= limit)
            System.exit(0);
    }

    public Site getSite() {
        return site;
    }


}