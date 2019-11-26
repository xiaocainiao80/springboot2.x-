package com.xxh.blog.service.impl;

import com.xxh.blog.dao.BlogRepository;
import com.xxh.blog.exception.NotFoundException;
import com.xxh.blog.po.Blog;
import com.xxh.blog.po.Type;
import com.xxh.blog.service.BlogService;
import com.xxh.blog.utils.GetNullPropertyNames;
import com.xxh.blog.utils.MarkdownUtil;
import com.xxh.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    //分页动态查询
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        //分页动态查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //动态 级联查询
                //CriteriaQuery<?> criteriaQuery 查询容器
                //CriteriaBuilder 条件
                List<Predicate> predicates = new ArrayList<>();
                //predicates 封装条件 博客标题
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                //分类 查询
                if(blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                //是否推荐
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                //将条件 放入 容器
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

                return null;
            }
        },pageable);
    }

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        //新增博客
        if(blog.getId() == null) {
            blog.setCreatTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } //修改博客
        else {
            //更新修改时间
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    //利用查询条件 全局搜索博客
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Map<String, List<Blog>> findYearBlog() {
        Map<String,List<Blog>> map = new HashMap<>();
        //查询出所有年
        List<String> years = blogRepository.findGroup();
        for (String year : years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            //动态 级联查询
            //CriteriaQuery<?> criteriaQuery 查询容器
            //CriteriaBuilder 条件
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return  cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listSize(int size) {
        return blogRepository.findsize(size);
    }

    //更新浏览量 需要事务
    @Transactional
    @Override
    //前端展示 博客详情 markdown转html
    public Blog getAndConvert(Long id) {
        //获取blog
        Blog blog = blogRepository.findById(id).orElse(null);
        if(blog == null){
            //抛出找不到异常
            throw new NotFoundException("博客资源不存在");
        }
        /* 这样 hibnet 会进数据库
        String content = blog.getContent();
        //转html 并扩展
        MarkdownUtil.markdownToHtmlExtensions(content);
        blog.setContent(content);
        */
        //浏览量 加一
        blogRepository.updateViews(blog.getId());
        //用一个 临时博客类 来代替博客 这样不会进数据库
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b,new GetNullPropertyNames().getNullPropertyNames(blog));
        String content = b.getContent();
        b.setContent(MarkdownUtil.markdownToHtmlExtensions(content));
        return b;
    }


    @Override
    //发送 size大小的推荐数据
    public List<Blog> listBlogTop(int size) {
        Sort sort =  Sort.by(Sort.Direction.DESC,"updateTime");
        //以size分页
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    //发送分页数据
    public Page<Blog> listBlog(Pageable pageable) {
        //发送发布的文章分页信息
        return blogRepository.findList(pageable);
    }

    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        //先查询 是否存在
        Blog blog1 = blogRepository.findById(id).orElse(null);
        if(blog1 == null) {
            //抛出 异常
            throw  new NotFoundException("该博客不存在!");
        }
        //过滤属性为空 的数组 不复制
        BeanUtils.copyProperties(blog,blog1,new GetNullPropertyNames().getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
