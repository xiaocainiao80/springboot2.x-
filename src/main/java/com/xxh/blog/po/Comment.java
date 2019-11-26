package com.xxh.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class Comment {
    /**
     * 评论实体类
     */
    @Id
    @GeneratedValue
    private Long id;
    //标记是否是管理员
    private boolean adminComment;

    private String nickname; //用户名
    private String email;//
    private String content;
    private String avatar; //图像
    @Temporal(TemporalType.TIMESTAMP)//指定时间的类型
    private Date creatTime;

    /**
     * 评论自关联映射
     * parentComment ->  replyComtent
     */
    @OneToMany(mappedBy = "parentComment") //被维护方
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;

    //多个评论 对应 一篇博客
    @ManyToOne
    private Blog blog;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", adminComment=" + adminComment +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", creatTime=" + creatTime +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", blog=" + blog +
                '}';
    }
}
