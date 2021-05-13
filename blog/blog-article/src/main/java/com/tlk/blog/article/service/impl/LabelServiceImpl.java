package com.tlk.blog.article.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tlk.blog.article.req.LabelREQ;
import com.tlk.blog.entities.Label;
import com.tlk.blog.article.mapper.LabelMapper;
import com.tlk.blog.article.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlk.blog.util.base.Result;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author tlk
 * @since 2021-05-07
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

    @Override
    public Result queryPage(LabelREQ req) {
        IPage<Label> page = baseMapper.queryPage(req.getPage() , req);
        return Result.ok(page);
    }
    @Override
    public boolean updateById(Label label){
        label.setUpdateDate(new Date());
        return super.updateById(label);
    }
}
