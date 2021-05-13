package com.tlk.blog.article.service;

import com.tlk.blog.article.req.ArticleListREQ;
import com.tlk.blog.article.req.ArticleREQ;
import com.tlk.blog.article.req.ArticleUserREQ;
import com.tlk.blog.article.service.impl.ArticleServiceImpl;
import com.tlk.blog.entities.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.feign.req.UserInfoREQ;
import com.tlk.blog.util.base.Result;
import com.tlk.blog.util.enums.ArticleStatusEnum;

/**
 * <p>
 * 文章信息表 服务类
 * </p>
 *
 * @author tlk
 * @since 2021-05-09
 */
public interface IArticleService extends IService<Article> {
    Result queryPage(ArticleREQ req);

    Result findArticleAndLabelById(String id);

    Result updateOrSave(Article article);

    Result updateStatus(String id , ArticleStatusEnum statusEnum);

    Result findListByUserId(ArticleUserREQ req);

    Result updateThumhup(String id , int count);

    Result updateViewCount(String id);

    Result findListByLabelIdOrCategoryId(ArticleListREQ req);

    Result getArticleTotal();

    Result selectCategoryTotal();

    Result selectMonthArticleTotal();

    boolean updateUserInfo(UserInfoREQ req);

}
