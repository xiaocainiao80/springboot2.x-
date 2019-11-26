package com.xxh.blog.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class GetNullPropertyNames {
    /**
     * 获取所有属性为空的数组
     */
    public static String[] getNullPropertyNames(Object source){
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPeopertyNames =new ArrayList<>();
        for (PropertyDescriptor pd : pds){
            String propertyName = pd.getName();
            if(beanWrapper.getPropertyValue(propertyName) == null) {
                nullPeopertyNames.add(propertyName);
            }
        }
        return nullPeopertyNames.toArray(new String[nullPeopertyNames.size()]);
    }
}
