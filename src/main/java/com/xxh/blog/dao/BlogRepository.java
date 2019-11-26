package com.xxh.blog.dao;

import com.xxh.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    //JpaSpecificationExecutor<Blog> 封装了联合查询 方法
    Blog findByTitleLessThanEqual(String title);

    //发送 发布的博客一页数据
    @Query("select b from Blog b where b.recommend = true and b.published = true")
    List<Blog> findTop(Pageable pageable);

    //利用查找条件 全局搜索 博客
    @Query("select b from Blog b where b.title like ?1 or  b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    //更新浏览量
    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

    //按年排序
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by " +
            "function('date_format',b.updateTime,'%Y') order by year DESC ")
    List<String> findGroup();

    //按年查询 博客
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);

    //发送size大小 推荐并且发布的博客数据
    @Query(nativeQuery = true,value = "select * from t_blog  where published = true and recommend = true  order by update_time DESC limit ?")
    List<Blog> findsize(int size);

    //查询分页发布的博客
    @Query("select b from Blog b where b.published = true")
    Page<Blog> findList(Pageable pageable);
}
