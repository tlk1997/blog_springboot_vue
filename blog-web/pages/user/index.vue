<template>
    <div >
        <!-- 个人信息 -->
        <el-row class="main-top">
            <div class="avatar">
                <el-tooltip content="点击上传头像" effect="light">
                    <el-upload
                        accept="image/*"
                        action=""
                        :show-file-list="false"
                        :http-request="uploadMainImg"
                        >
                        <el-avatar :size="120" :src="this.userInfo.imageUrl" >{{userInfo.nickName}}</el-avatar>
                    </el-upload>
                </el-tooltip>
            </div>
            <div class="info">
                <div>
                    <span class="meta-block">&nbsp;&nbsp;&nbsp;昵称：</span>
                    <span class="name">{{userInfo.nickName}}</span>
                </div>
                <div>
                    <span class="meta-block">用户名：</span>
                    <span class="name">{{userInfo.username}}</span>
                </div>
            </div>
        </el-row>
        <el-row >
            <el-tabs value="public" @tab-click="handleClick" >
                <el-tab-pane label="公开文章" name="public">
                    <article-list name="public" :page="query" :listData="articleList" @fetch-data="findUserArticleList"/>
                </el-tab-pane>
                <el-tab-pane label="私密文章" name="nopublic">
                    <article-list name="nopublic" :page="query" :listData="articleList" @fetch-data="findUserArticleList"/>
                </el-tab-pane>
                <el-tab-pane label="我的提问" name="question">
                    <question-list name="question" :page="query" :listData="questionList" @fetch-data="findUserQuestionList"/>
                </el-tab-pane>
                <el-tab-pane label="修改个人资料" name="user">
                    <user-edit :loading="loading" :formData="userInfo" @submitForm="submitUserForm" />
                </el-tab-pane>
                <el-tab-pane label="修改密码" name="password">
                    <user-password :loading="loading" :formData="passwordData" @submitForm="submitPasswordForm" />
                </el-tab-pane>
            </el-tabs> 
        </el-row>
    </div>
</template>
<script>
import ArticleList from '@/components/article/List'
import QuestionList from '@/components/question/List'
import UserEdit from '@/components/user/Edit'
import UserPassword from '@/components/user/Password'

export default {
    middleware:'auth',

    components: { QuestionList, ArticleList, UserEdit, UserPassword }, 

    data() {
        return {
            loading: false,
            userInfo: {
                imageUrl: ''
            },
            passwordData: { // 修改密码表单
                userId: this.$store.state.userInfo.uid
            },
            questionList:[]
        }
    },

    methods: {
        handleClick(tab, event) {
            switch (tab.paneName) {
                case 'public':
                    this.findUserArticleList(tab.paneName , 1)
                    break;
                case 'nopublic':
                    this.findUserArticleList(tab.paneName , 1)
                    break;
                case 'question':
                    this.findUserQuestionList(tab.paneName , 1)
                    break;
                case 'user':
                    // 用户不用查询，在加载此页面时已经查询了
                    break;
            }
        },
        
        // 上传头像
        async uploadMainImg(file) {
            const fileData = new FormData()
            fileData.append('file' , file.file)
            try {
                const {data} = await this.$uploadImg(fileData)
                this.deleteImg()
                this.userInfo.imageUrl = data
                this.$updateUserInfo(this.userInfo)
            }catch(error){
                this.$message.error('上传失败')
            }
        },

        // 删除头像, 上传成功后删除原来的头像
        deleteImg() {
            if(this.userInfo.imageUrl){
                this.$deleteImg(this.userInfo.imageUrl)
            }
        },

        // 提交修改个人资料
        async submitUserForm() {
            this.loading = true
            const {code , message} = await this.$updateUserInfo(this.userInfo)
            if (code === 20000){
                this.$message.success('修改成功')
            }else{
                this.$message.error(message)
            }
            this.loading = false
        },

        // 修改密码
        async submitPasswordForm() {
            this.loading = true
            this.passwordData.userId = this.$store.state.userInfo && this.$store.state.userInfo.uid
            const {code , message} = await this.$updatePassword(this.passwordData)
            console.log(message)
            if (code === 20000){
                this.passwordData = {}
                this.$store.dispatch('UserLogout')
            }else{
                this.$message.err(response.message)
            }
            this.loading = false
        },

        async findUserArticleList(paneName , current){
            this.query.current = current
            this.query.isPublic = paneName === 'public' ? 1 : 0
            const {data} = await this.$findUserArticle(this.query)
            this.query.total = data.total
            this.articleList = data.records
        },
        async findUserQuestionList(paneName , current){
            this.query.current = current
            delete this.query.isPublic
            const {data} =await this.$findUserQuestion(this.query)
            this.query.total = data.total
            this.questionList = data.records
        },
    },

    async asyncData( {app , store} ) {
        const userId = store.state.userInfo && store.state.userInfo.uid
        // 1. 查询用户信息
        const { data:userInfo} = await app.$getUserInfo(userId)
        // 2. 查询公开文章列表
        const query = {
            userId,
            isPublic:1,//0未公开，1公开
            current:1,
            size:20,
            total:0
        }
        const { data } = await app.$findUserArticle(query)

        query.total = data.total
        return {userInfo , query , articleList:data.records}
    },

}
</script>
<style scoped>
.main-top {
    padding: 30px 0;
}
.avatar{
    float:left;
}
.info {
    margin: 30px 0 0 140px;
}
.info .name {
    font-size: 18px;
    font-weight: 500;
    padding-top: 10px;
}
.info .meta-block {
    font-size: 14px;
    color: #969696;
}
</style>