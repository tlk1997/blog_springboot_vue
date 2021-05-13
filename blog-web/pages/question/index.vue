<template>
    <div class="question-container">
        <el-row>
            <el-tabs value="hot" @tab-click="handleClick" type="card">
                <el-tab-pane label="热门回答" name="hot">
                    <List name="hot" :page="page" :listData="listData" @fetch-data="fetchData"/>
                </el-tab-pane>
                <el-tab-pane label="最新回答" name="new">
                    <List name="new" :page="page" :listData="listData" @fetch-data="fetchData"/>
                </el-tab-pane> 
                <el-tab-pane label="等待回答" name="wait">
                    <List name="wait" :page="page" :listData="listData" @fetch-data="fetchData"/>
                </el-tab-pane>        
            </el-tabs>
        </el-row>
    </div>       
</template>
<script>
import List from '@/components/question/List'
export default {
    components: {List},
    methods: {
        //点击标签时触发
        handleClick(tab){
            this.fetchData(tab.paneName , 1)
        },

        async fetchData(paneName , current){
            this.page.current = current
            let response = null
            switch(paneName){
                case 'hot':
                    response = await this.$getHotList(this.page)
                    break;
                case 'new':
                    response = await this.$getNewList(this.page)
                    break;
                case 'wait':
                    response = await this.$getWaitList(this.page)
                    break;
                default:
                    break;
            }
            if (response && response.code === 20000){
                this.page.total = response.data.total
                this.listData = response.data.records
            }
        }
    },
    //默认查询热门回答
    async asyncData({app}){
        //分页对象
        const page = {
            total: 0,//总记录数
            current:1,//当前页码
            size:20 //每页20条
        }
        const { data } = await app.$getHotList(page)
        page.total = data.total
        return { page , listData:data.records}
    }
}
</script>
<style scoped>
.question-container {
    margin : 10px;
}
</style>