import request from '@/utils/request'

export default{
    getUserTotal(){
        return request({
            url:`/system/user/total`,
            method:'get'
        })
    },
    getArticleTotal(){
        return request({
            url:`/article/article/total`,
            method:'get'
        })
    },
    getQuestionTotal(){
        return request({
            url:`/question/question/total`,
            method:'get'
        })
    },
    getCategoryTotal(){
        return request({
            url:`/article/article/category/total`,
            method:'get'
        })
    },
    getMonthArticleTotal(){
        return request({
            url:`/article/article/month/total`,
            method:'get'
        })
    }

}