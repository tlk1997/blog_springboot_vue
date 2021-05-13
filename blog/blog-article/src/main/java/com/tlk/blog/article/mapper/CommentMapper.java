package com.tlk.blog.article.mapper;

import com.tlk.blog.entities.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;

import java.util.List;

/**
 * <p>
 * 评论信息表 Mapper 接口
 * </p>
 *
 * @author tlk
 * @since 2021-05-10
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> findByArticleId(@Param("articleId") String articleId);
}
