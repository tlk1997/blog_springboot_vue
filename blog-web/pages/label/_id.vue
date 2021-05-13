<template>
    <div>
        <el-breadcrumb>
            <el-breadcrumb-item :to="{path: '/label' }">标签</el-breadcrumb-item>
            <el-breadcrumb-item :to="{path: $route.path, query:{name: $route.query.name} }">
                {{$route.query.name}}
            </el-breadcrumb-item>
            <el-breadcrumb-item>标签动态</el-breadcrumb-item>
        </el-breadcrumb>

        <div class="label-tab">
            <el-tabs value="question" @tab-click="handleClick" type="card">
                <el-tab-pane label="技术问答" name="question">
                    <QuestionList name="question" :page="page" :listData="questionList" @fetch-data="fetchData"/>
                </el-tab-pane>
                <el-tab-pane label="博客文章" name="article">
                    <ArticleList name="article" :page="page" :listData="articleList" @fetch-data="fetchData"/>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>
<script>
import QuestionList from '@/components/question/List'
import ArticleList from '@/components/article/List'

export default {
    data () {
        return {
            articleList : []
        }
    },
    components: {QuestionList , ArticleList},
    validate({params}){
        return /^\d+$/.test(params.id)
    },
    methods:{
        handleClick(tab){
            this.fetchData(tab.paneName , 1)
        },
    
        async fetchData(paneName , current) {
            this.page.current = current

            let response = null
            switch(paneName){
            case 'question':
                response = await this.$getQuestionListByLabelId (this.page , this.$route.params.id)
                this.page.total = response.data.total
                this.questionList = response.data.records
                break;
            case 'article':
                const query = {...this.page , labelId:this.$route.params.id}
                response = await this.$getArticleList(query)
                this.page.total = response.data.total
                this.articleList = response.data.records
                break;
            }
        },
    },
    async asyncData({ params , app}){
        const page = {
            current : 1,
            size:20,
            total:0
        }
        const { data } = await app.$getQuestionListByLabelId(page , params.id)
        page.total = data.total
        return {page , questionList: data.records}  

    },
}
</script>
<style scoped>
.label-tab{
    margin-top:30px
}
</style>