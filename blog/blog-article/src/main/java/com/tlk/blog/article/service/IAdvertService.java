package com.tlk.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.article.req.AdvertREQ;
import com.tlk.blog.entities.Advert;
import com.tlk.blog.util.base.Result;

import java.util.List;

/**
 * <p>
 * 广告信息表 服务实现类
 * </p>
 *
 * @author tlk
 * @since 2021-05-10
 */
public interface IAdvertService extends IService<Advert> {

    Result queryPage(AdvertREQ req);

    Result deleteById(String id);

    Result findByPosition(int position);
}
