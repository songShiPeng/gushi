package com.song.learn.webmagic.wm;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;

/**
 * Created by songhsipeng on 16-12-15.
 */
public class FilePipline implements Pipeline{

    private File file;
    private String pathName;

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            if(null != resultItems.get("content")) {
                FileEdit.eidtTxt(resultItems.get("content").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
