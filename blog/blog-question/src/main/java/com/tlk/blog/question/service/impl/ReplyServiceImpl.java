package com.tlk.blog.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlk.blog.entities.Question;
import com.tlk.blog.entities.Reply;
import com.tlk.blog.question.mapper.QuestionMapper;
import com.tlk.blog.question.mapper.ReplyMapper;
import com.tlk.blog.question.service.IReplyService;
import com.tlk.blog.util.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * 回答信息表 服务实现类
 * </p>
 *
 * @author tlk
 * @since 2021-05-11
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

    @Override
    public Result findByQuestionId(String questionId) {
        if(StringUtils.isEmpty(questionId)) {
            return Result.error("问题ID不能为空");
        }

        List<Reply> list = baseMapper.findByQuestionId(questionId);
        return Result.ok(list);
    }

    @Autowired
    private QuestionMapper questionMapper; // 忽略它，红色不是错误的

    @Transactional // 事务管理，如果出了异常会进行回滚
    @Override
    public Result deleteById(String id) {
        if(StringUtils.isEmpty(id)) {
            return Result.error("回答评论id不能为空");
        }
        // 收集要删除 的回答评论id
        List<String> ids = new ArrayList<>();
        ids.add(id); // 将当前传递的回答评论id放到集合
        // 递归收集子评论id
        this.getIds(ids, id);
        // 1. 查询回答信息
        Reply reply = baseMapper.selectById(id);
        // 批量删除回答评论信息
        int size = baseMapper.deleteBatchIds(ids);
        // 通过上面 1. 中查询到回答信息的问题id去查询问题信息
        if(size > 0) {
            Question question = questionMapper.selectById(reply.getQuestionId());
            // 更新问题表中的回复数
            question.setReply( question.getReply() - size );
            questionMapper.updateById(question);
        }
        return Result.ok();
    }

    private void getIds(List<String> ids, String parentId) {
        // 查询子评论信息
        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<Reply> replyList = baseMapper.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(replyList)) {
            for(Reply reply: replyList) {
                // 获取要删除的评论id
                String id = reply.getId();
                ids.add(id);
                // 递归继续将id作为parentId查询子评论
                this.getIds(ids, id);
            }
        }
    }

    @Transactional //开启事务
    @Override
    public Result add(Reply reply) {
        // 新增回答信息
        boolean ok = this.save(reply);
        if(ok) {
            // 更新问题表中的回复数
            Question question = questionMapper.selectById(reply.getQuestionId());
            question.setReply( question.getReply() + 1 ) ;
            questionMapper.updateById(question);
        }
        return Result.ok();
    }
}
