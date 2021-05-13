<template>
    <!-- 文章列表 -->
    <div id="list-container">
        <ul class="note-list">
            <li :class="{'have-img': item.imageUrl}" v-for="item in articleList" :key="item.id">
                <div class="content">
                    <nuxt-link :to="`/article/${item.id}`" target="_blank">
                        <p class="title"> {{item.title}} </p>
                        <p class="abstract"> {{item.summary}}</p>
                    </nuxt-link>
                    <div class="meta">
                        <nuxt-link :to="`/user`" target="_blank" class="nickname">
                        <i class="el-icon-user-solid"></i> {{item.nickname}}
                        </nuxt-link>
                        <span><i class="el-icon-thumb"></i>{{item.thumhup}}</span>
                        <span><i class="el-icon-view"></i>{{item.viewCount}}</span>
                    </div>
                    <div v-if="item.imageUrl">
                        <nuxt-link :to="`/article/${item.id}`" class="wrap-img" target="_blank">
                            <img :src="item.imageUrl">
                        </nuxt-link>
                    </div>
                </div>
            </li>
        </ul>

        <!-- 分页 -->
        <el-row class="page" type="flex" justify="center">
            <el-tag v-if="noMore || articleList.length === 0" type="primary">
                没有更多了
            </el-tag>
            <el-button v-else @click="load" :loading="loading" type="primary" size="small" round>
                {{loading ? '加载中':'点击加载更多' }}
            </el-button>
        </el-row>
    </div>

</template>

<script>
export default {

    data() {
        return {
            loading : false,
            noMore : false
        }
    },
    methods: {
        async load(){
            this.loading = true
            this.query.current++;
            const {data} = await this.$getArticleList(this.query)
            if(data.records && data.records.length > 0){
                this.articleList = this.articleList.concat(data.records)
            }else{
                this.noMore = true
            }
            this.loading = false
        }
    },
    //校验路由参数是否有效
    validate ( { params }){
        const id = params.id ? params.id : 0
        return /^\d+$/.test(id)
    },

    async asyncData({params , app}){
        const categoryId = params.id ? params.id : null

        const query = { categoryId , current: 1 ,size: 20}
        const {data } = await app.$getArticleList(query)
        return { query , articleList: data.records}
    },
}
</script>

<style scoped>
  @import '@/assets/css/blog/list.css';
</style>