package com.tlk.blog.question.service;

import com.tlk.blog.entities.Reply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.util.base.Result;

/**
 * <p>
 * 回答信息表 服务类
 * </p>
 *
 * @author tlk
 * @since 2021-05-11
 */
public interface IReplyService extends IService<Reply> {

    Result findByQuestionId(String questionId);

    Result deleteById(String id);

    Result add(Reply reply);
}
