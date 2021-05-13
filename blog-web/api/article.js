export default({$axios},inject) => {
    // 查询技术频道列表接口
    inject('getCategoryList' , () => $axios.$get('/article/api/category/list'))

    // 获取指定位置的广告信息
    inject('getAdvertList' , position => $axios.$get(`/article/api/advert/show/${position}`))

    //获取文章列表分页接口
    inject('getArticleList' , query => $axios.$post('/article/api/article/list' , query))

    //查看文章详情
    inject('getArticleById' , id => $axios.$get(`/article/api/article/${id}`))

    //更新文章浏览数
    inject('updateArticleViewCount' , id => $axios.$put(`/article/api/article/vieCount/${id}`))

    //更新点赞数
    inject('updateArticleThumb' , (articleId , count) => $axios.$put(`/article/article/thumb/${articleId}/${count}`))

    //通过文章ID查询所有评论数据接口
    inject('getCommentListByArticleId' , articleId => $axios.$get(`/article/api/comment/list/${articleId}`))

    //新增文章评论
    inject('addComment' , data => $axios.$post(`/article/comment`,data))
    
    //删除评论
    inject('deleteCommentById' , id => $axios.$post(`/article/comment/${id}`))

    //获取所有正常状态的分类和标签
    inject('getCategoryAndLabel' , () => $axios.$get(`/article/api/category/label/list`))

    //新增文章
    inject('addArticle' , data => $axios.$post(`/article/article` , data))

    // 修改文章
    inject('updateArticle' , data => $axios.$put(`/article/article`, data))
}