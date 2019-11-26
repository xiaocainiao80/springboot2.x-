package com.xxh.blog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_type")
public class Type {
    /**
     * 分类实体
     */

    @Id
    @GeneratedValue
    private Long id;

    //效验不能为空 传递消息
    @NotBlank(message="分类名称不能为空!")
    private String name;

    //一个分类包含多篇博客
    @OneToMany(mappedBy = "type") //关系被维护的一端
    private List<Blog> blogs = new ArrayList<>();

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Type() {
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
