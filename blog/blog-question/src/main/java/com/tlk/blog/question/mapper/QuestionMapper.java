package com.tlk.blog.question.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tlk.blog.entities.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlk.blog.feign.req.UserInfoREQ;
import feign.Param;
import java.util.List;

/**
 * <p>
 * 问题信息表 Mapper 接口
 * </p>
 *
 * @author tlk
 * @since 2021-05-11
 */
public interface QuestionMapper extends BaseMapper<Question> {
    IPage<Question> findListByLabelId(IPage<Question> page , @Param("labelId") String labelId);

    Question findQuestionAndLabelIdsById(String id);

    boolean deleteQuestionLabel(@Param("questionId") String questionId);

    boolean saveQuestionLabel(@Param("questionId") String questionId,
                              @Param("labelIds") List<String> labelIds);
    boolean updateUserInfo(UserInfoREQ req);
}
