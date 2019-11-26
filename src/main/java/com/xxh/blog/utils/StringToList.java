package com.xxh.blog.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StringToList {
    /**
     * 将字符串转化为list集合
     */
    public static List<Long> list(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null) {
            String[] idArrays = ids.split(",");
            for(int i=0;i<idArrays.length;i++) {
                list.add(Long.valueOf(idArrays[i]));
            }
        }
        return list;
    }
}
