<template>
    <div>
        <el-row type="flex">
            <el-col :xs="24" :sm="24" :md="18">
                <div class="article-left">
                    <el-card>
                        <!-- 标题 -->
                        <div class="atricle-title">
                            <h1> {{ data.title}} </h1>
                            <div class="article-count">
                                <nuxt-link to="`/user`" target="_blank" class="nickname">
                                    <i class="el-icon-user-solid"></i>{{data.nickName}}
                                </nuxt-link>
                                <span>
                                    <i class="el-icon-date"></i> {{getDateFormat(data.updateDate)}}
                                    <i class="el-icon-thumb"></i> {{data.thumhup}}
                                    <i class="el-icon-view"></i> {{data.viewCount}}
                                </span>

                                <nuxt-link v-if="this.$store.state.userInfo && $store.state.userInfo.uid === data.userId"
                                    :to="{path:'/article/edit', query:{id: data.id} }" class="nickname">
                                    &nbsp;&nbsp; 编辑
                                </nuxt-link>
                            </div>

                            <el-tag style="margin: 5px;" v-for="item in data.labelList" :key="item.id" size="small">
                                {{ item.name}}
                            </el-tag>
                        </div>
                        <!-- 内容 -->
                        <div class="article-content">
                            <div class="markdown-body" v-html="data.htmlContent"></div>
                        </div>
                        <el-button :disabled="!$store.state.userInfo" @click="handleThumb()" :plain="isThumb" icon="el-icon-thumb" type="primary" size="medium">赞</el-button>
                    </el-card>

                    <!-- 评论区 -->
                    <div>
                        <h2>评论区</h2>
                        <el-card v-if="!$store.state.userInfo">
                            <h4> 登录后参与交流{{$store.state.userInfo}}</h4>
                            <div>
                                <el-button @click="$store.dispatch('LoginPage')" type="primary" size="small">
                                    登录
                                </el-button>
                                <el-button @click="$store.dispatch('LoginPage')" type="primary" size="small" plain>
                                    注册
                                </el-button>
                            </div>
                        </el-card>
                        <el-card>
                            <tlk-comment :userId="userId" :userImage="userImage" :authorId="data.userId"
                                         :showComment="$store.state.userInfo ? true : false"
                                         @doSend="doSend" @doChildSend="doChildSend" @doRemove="doRemove"
                                         :commentList="commentList"
                                         >
                            </tlk-comment>
                        </el-card>
                    </div>
                </div>
            </el-col>

            <!-- 右侧 -->
            <el-col class="hidden-sm-and-down" :md="6">
                <el-row>
                    <el-col>
                        <tlk-affix :offset="80">
                            <tlk-directory parentClass="article-content"></tlk-directory>
                        </tlk-affix>
                    </el-col>
                </el-row>
            </el-col>

        </el-row>
    </div>
</template>

<script>
import '@/assets/css/md/github-markdown.css';
import '@/assets/css/md/github-min.css';

import {dateFormat} from '@/utils/date'

import TlkAffix from '@/components/common/Affix'
import TlkDirectory from '@/components/common/Directory'
import TlkComment from '@/components/common/Comment'


export default {
    components : { TlkAffix , TlkDirectory , TlkComment },
    data(){
        return {
            //是否已点赞
            isThumb : this.$cookies.get(`article-thumb-${this.$route.params.id}`) ? this.$cookies.get(`article-thumb-${this.$route.params.id}`) : false,

            //当前登录用户id
            userId: this.$store.state.userInfo && this.$store.state.userInfo.uid,

            //当前登录用户头像
            userImage: this.$store.state.userInfo && this.$store.state.userInfo.imageUrl,

        }
    },
    methods:{
        getDateFormat(date){
            return dateFormat(date)
        },
        doSend(content){
            console.log(`针对文章ID=${this.$route.params.id}发布评论内容:${content}`)
            this.doChildSend(content)
        },
        doChildSend(content,parentId=-1){
            const data = {
                content , 
                parentId,
                articleId : this.$route.params.id,
                userId:this.userId,
                userImage:this.userImage,
                nickName:this.$store.state.userInfo && this.$store.state.userInfo.nickName
            }
            this.$addComment(data).then(response => {
                if (response.code === 20000){
                    this.refreshComment()
                }
            })
            console.log(`对父评论ID=${parentId}发布评论内容:${content}`)
        },
        //删除评论
        async doRemove(id){
            console.log(`删除评论id${id}`)
            const { code } = await this.$deleteCommentById(id)
            if (code === 20000){
                this.refreshComment()
            }
        },
        //获取评论
        async refreshComment(){
            const { data } = await this.$getCommentListByArticleId(this.$route.params.id)
            this.commentList = data
        },
        
        async handleThumb(){
            // 点击后取反
            this.isThumb = !this.isThumb
            //1 点赞 -1 取消赞
            const count = this.isThumb ? 1 : -1
            //当前文章id
            const articleId = this.$route.params.id
            //更新点赞数
            const { code } = await this.$updateArticleThumb(articleId , count)
            if (code === 20000){
                this.data.thumhup = this.data.humhup + count
            }
            //保存到cookie中 
            this.$cookies.set(`article-thumb-${this.$route.params.id}` , this.isThumb , {
                maxAge : 60 * 60 * 24 * 365 * 5
            })
        }
    },
    validate({params}){
        return /^\d+$/.test(params.id)
    },
    head(){
        return {
            title : this.data.title,
        }
    },
    //获取数据
    async asyncData({params , app}){
        // 查询文章详情
        const { data } = await app.$getArticleById(params.id)

        //更新浏览数，判断Cookie是否已经存在
        const articleId = app.$cookies.get(`article-view-${data.id}`)

        //没有值，更新浏览数
        if (!articleId){
            const {code} = await app.$updateArticleViewCount(data.id)
            if (code === 20000){
                // 更新后页面+1浏览数
                data.viewCount++
            }
            // 保存到cookie中，关闭浏览器后删除
            app.$cookies.set(`article-view-${data.id}`)

        }
        const {data : commentList} = await app.$getCommentListByArticleId(data.id)
        return {data ,commentList}

    }
}
</script>
<style scoped>
  @import '@/assets/css/blog/article.css';


</style>