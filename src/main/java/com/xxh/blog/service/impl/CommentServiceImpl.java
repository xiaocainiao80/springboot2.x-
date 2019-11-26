package com.xxh.blog.service.impl;

import com.xxh.blog.dao.CommentRepository;
import com.xxh.blog.po.Comment;
import com.xxh.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC,"creatTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);

        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        //获取父级评论id
        Long parentCommentId = comment.getParentComment().getId();
        //处理父子级评论 前端默认为-1
        if(parentCommentId != -1) {
            //那就是子评论 它的父级就是该评论
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else {
            //无父
            comment.setParentComment(null);
        }
        comment.setCreatTime(new Date());
        return commentRepository.save(comment);
    }


    /**
     * 循环每个顶级评论的节点
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return  commentsView;

    }

    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply1 : replys){
                //循环迭代 找出子代 ，存放在tempReplys集合中
                    recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出所有子代的集合
    private List<Comment>  tempReplys = new ArrayList<>();

    /**
     * 递归迭代 剥洋葱
     */
    private void recursively(Comment comment){
        tempReplys.add(comment);//顶级节点放到临时存放集合
        if(comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }

    }

    //分页展示评论
    @Override
    public Page<Comment> listComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
