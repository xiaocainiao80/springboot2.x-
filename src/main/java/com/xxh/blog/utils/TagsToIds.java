package com.xxh.blog.utils;

import com.xxh.blog.po.Tag;
import java.util.List;

public class TagsToIds {

    //将List<Tag> 转成 1，2，3，4，5形式
    public static String tagsToIds(List<Tag> tags){
        StringBuffer ids = new StringBuffer();
        if(!tags.isEmpty()) {
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
        }
        return ids.toString();
    }
}
