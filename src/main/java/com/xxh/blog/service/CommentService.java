package com.xxh.blog.service;

import com.xxh.blog.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    Page<Comment> listComments(Pageable pageable);

    void deleteComment(Long id);
}
