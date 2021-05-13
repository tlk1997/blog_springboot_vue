package com.tlk.blog.article.service;

import com.tlk.blog.entities.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.util.base.Result;

/**
 * <p>
 * 评论信息表 服务类
 * </p>
 *
 * @author tlk
 * @since 2021-05-10
 */
public interface ICommentService extends IService<Comment> {

    Result findByArticleId(String articleId);

    Result deleteById(String id);

}
