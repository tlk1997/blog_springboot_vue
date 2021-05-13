package com.tlk.blog.question.service;

import com.tlk.blog.entities.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tlk.blog.feign.req.UserInfoREQ;
import com.tlk.blog.question.req.QuestionUserREQ;
import com.tlk.blog.util.base.BaseRequest;
import com.tlk.blog.util.base.Result;

/**
 * <p>
 * 问题信息表 服务类
 * </p>
 *
 * @author tlk
 * @since 2021-05-11
 */
public interface IQuestionService extends IService<Question> {

    Result findHotList(BaseRequest<Question> req);

    Result findNewList(BaseRequest<Question> req);

    Result findWaitList(BaseRequest<Question> req);

    Result findListByLabelId(BaseRequest<Question> req, String labelId);

    Result findById(String id);

    Result updateViewCount(String id);

    Result updateOrSave(Question question);

    Result deleteById(String id);

    Result updateThumhup(String id, int count);

    Result findListByUserId(QuestionUserREQ req);

    Result getQuestionTotal();

    boolean updateUserInfo(UserInfoREQ req);

}
