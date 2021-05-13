package com.tlk.blog.article.service;

import com.tlk.blog.article.req.LabelREQ;
import com.tlk.blog.entities.Label;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.util.base.Result;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author tlk
 * @since 2021-05-07
 */
public interface ILabelService extends IService<Label> {
    Result queryPage(LabelREQ req);
}
