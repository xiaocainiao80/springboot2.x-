package com.xxh.blog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_tag")
public class Tag {
    /**
     * 标签实体
     */
    @Id
    @GeneratedValue
    private Long id;
    //效验不能为空 传递消息
    @NotBlank(message = "标签名称不能为空!")
    private String name;

    //多个标签 对应 多篇博客
    @ManyToMany(mappedBy = "tags")//被维护的一端
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
