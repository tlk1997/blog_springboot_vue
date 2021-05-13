package com.tlk.blog.question.mapper;

import com.tlk.blog.entities.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
/**
 * <p>
 * 回答信息表 Mapper 接口
 * </p>
 *
 * @author tlk
 * @since 2021-05-11
 */
public interface ReplyMapper extends BaseMapper<Reply> {

    List<Reply> findByQuestionId(String questionId);

}
