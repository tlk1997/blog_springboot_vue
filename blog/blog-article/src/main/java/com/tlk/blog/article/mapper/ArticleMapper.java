package com.tlk.blog.article.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tlk.blog.article.req.ArticleListREQ;
import com.tlk.blog.entities.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlk.blog.feign.req.UserInfoREQ;
import feign.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章信息表 Mapper 接口
 * </p>
 *
 * @author tlk
 * @since 2021-05-09
 */
public interface ArticleMapper extends BaseMapper<Article> {

    Article findArticleAndLabelById(String id);

    Boolean deleteArticleLabel(@Param("articleId") String articleId);

    Boolean saveArticleLabel(@Param("articleId") String articleId,
                             @Param("labelIds") List<String> labelIds);

    IPage<Article> findListByLabelIdOrCategoryId(IPage<Article> page , @Param("req") ArticleListREQ req);

    List<Map<String,Object>> selectCategoryTotal();

    List<Map<String,Object>> selectMonthArticleTotal();

    boolean updateUserInfo(UserInfoREQ req);
}
