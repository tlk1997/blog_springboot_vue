export default({$axios},inject) => {
    //查询个人用户信息
    inject('getUserInfo' , id => $axios.$get(`/system/user/${id}`))
    //提交修改个人用户信息
    inject('updateUserInfo' , data => $axios.$put(`/system/user` , data))
    //查询个人公开或私密文章
    inject('findUserArticle' , query => $axios.$post(`/article/article/user` , query))
    //查询我的提问
    inject('findUserQuestion' , query => $axios.$post(`/question/question/user` , query))
    //校验原密码
    inject('checkOldPassword' , data => $axios.$post(`/system/user/check/password` , data))
    //提交修改新密码
    inject('updatePassword' , data => $axios.$put(`/system/user/password` , data))

}