package com.xxh.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity //jpa对应实体注解
@Table(name = "t_blog")
public class Blog {
    /**
     * 博客实体类
     */
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    //内容变成 longtext类型
    @Basic(fetch = FetchType.LAZY) //懒加载 使用时才加载 比使用不加载
    @Lob
    private String content;
    private String firstPicture; //首图
    private String flag ; //标记
    private Integer views ; //浏览量
    private boolean appreciation; //赞赏开启
    private boolean shareStatement; //版权开启
    private boolean commentabled; //评论开启
    private boolean published ;//是否发布
    private boolean recommend; //推荐
    private String description; //博客描述

    //不需要绑定数据库
    @Transient
    private String tagIds; //多个标签

    @Temporal(TemporalType.TIMESTAMP) //指定时间类型
    private Date creatTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    //多篇博客对应一个分类
    @ManyToOne //关系维护端
    private Type type;

    //多篇博客对应多个标签
    //设定新增关系 级联新增
    @ManyToMany(cascade = {CascadeType.PERSIST })
    //新增 博客里含tag 该tag也会新增到t_tag表里
    private List<Tag> tags = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    //多篇博客 对应一个用户/管理员
    @ManyToOne
    private User user;

    //一篇博客 对应 多个评论
    @OneToMany(mappedBy = "blog") //关系被维护方
    private List<Comment> comments = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog() {
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", description='" + description + '\'' +
                ", tagIds='" + tagIds + '\'' +
                ", creatTime=" + creatTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
