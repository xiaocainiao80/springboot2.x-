package com.xxh.blog.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Calendar;

public class FileUploadUtil {

    /*
     *图片上传工具类
     */
    public static String upload(HttpServletRequest request, MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //Calendar.getInstance()是获取一个Calendar对象并可以进行时间的计算，时区的指定
        Calendar date = Calendar.getInstance();
        //获得日期路径,MONTH个值的初始值是0，因此我们要用它来表示正确的月份时就需要加1。
        File dateFile = new File(date.get(Calendar.YEAR)+"/"+(date.get(Calendar.MONTH)+1)+"/"+(date.get(Calendar.DATE)));
        fileName = dateFile +"/"+fileName;
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
