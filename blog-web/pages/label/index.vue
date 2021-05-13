<template>
    <div class="label-main">
        <el-row  :gutter="10">
          <el-col v-for="(category, index) in data" :key="index" :xs="24" :sm="24" :md="6" >
            <el-card shadow="hover">
                <!-- 类别名 -->
                <div slot="header">
                    <span>{{category.name}}</span>
                </div>
                <!-- 类别下的标签 -->
                <div>
                    <nuxt-link v-for="label in category.labelList" :key="label.id"
                                :to="{path: `/label/${label.id}`, query:{name: label.name}}">
                        <el-tag size="small">
                            {{label.name}}
                        </el-tag>
                    </nuxt-link>
                </div>
            </el-card>
          </el-col>
        </el-row>
    </div>
</template>
<script>
export default {
    
    async asyncData({ app }){
        //查询分类以及标签
        const { data } = await app.$getCategoryAndLabel()
        return { data }
    }

}
</script>

<style scoped>
.label-main{
    margin: 0 10px;
}
.el-col {
    margin-bottom: 20px;
}
.el-tag {
    margin-right: 5px;
}
.el-tag:hover {
    border-bottom: 1px solid #345DC2;
}
.el-card {
    height: 200px;
}
</style>