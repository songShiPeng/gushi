package com.song.learn.webmagic.wm;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.io.*;

/**
 * Created by songhsipeng on 16-12-15.
 */
public class FileEdit {
    private static File file;
    private static String pathName;

    public static void eidtTxt(String line) throws IOException {
        file = new File(pathName);
        if (!file.exists()) {
            file.createNewFile();
            System.err.println(pathName + "已创建！");
        }
        BufferedWriter out =new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, true)));
        out.write(line+"\n");
        out.close();
    }

    public FileEdit(String pathName){
        this.pathName = pathName;
    }

}
