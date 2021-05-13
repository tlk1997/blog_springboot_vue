package com.tlk.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tlk.blog.article.req.AdvertREQ;
import com.tlk.blog.entities.Advert;
import com.tlk.blog.article.mapper.AdvertMapper;
import com.tlk.blog.article.service.IAdvertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlk.blog.util.base.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 广告信息表 服务实现类
 * </p>
 *
 * @author tlk
 * @since 2021-05-10
 */
@Service
public class AdvertServiceImpl extends ServiceImpl<AdvertMapper, Advert> implements IAdvertService {

    @Override
    public Result queryPage(AdvertREQ req) {
        QueryWrapper<Advert> wrapper = new QueryWrapper<>();

        if(StringUtils.isNotEmpty(req.getTitle())) {
            wrapper.like("title", req.getTitle());
        }

        if(req.getStatus() != null) {
            wrapper.eq("status", req.getStatus());
        }
        wrapper.orderByDesc("status").orderByAsc("sort");
        IPage<Advert> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Transactional
    @Override
    public Result deleteById(String id) {
        // 1. 先通过广告id查询图片url
        String imageUrl = baseMapper.selectById(id).getImageUrl();

        // 2. 先删除表中的广告信息
        baseMapper.deleteById(id);


        return Result.ok();
    }

    @Override
    public Result findByPosition(int position) {
        QueryWrapper<Advert> wrapper = new QueryWrapper<>();
        // 状态是正常的 status=1
        wrapper.eq("status", 1);
        // 位置
        wrapper.eq("position", position);
        // 排序 sort 升序
        wrapper.orderByAsc("sort");

        return Result.ok( baseMapper.selectList(wrapper) );
    }
}
