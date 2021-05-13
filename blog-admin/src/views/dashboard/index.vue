<template>
  <div class="dashboard-container">
    <panel-group :userTotal="userTotal" :articleTotal="articleTotal" :questionTotal="questionTotal" />
    <el-row :gutter="40">
      <el-col :xs="24" :sm="24" :lg="12">
        <el-card>
          <pie-chart v-if="flag" :legendData="categoryTotal.nameList" :seriesData="categoryTotal.nameAndValueList" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="12">
        <el-card>
          <bar-chart v-if="flag" :xAxisData="monthArticleTotal.yearMonthList" :seriesData="monthArticleTotal.articleTotalList"/>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PieChart from './components/PieChart.vue'
import BarChart from './components/BarChart.vue'
import PanelGroup from './components/PanelGroup.vue'
import api from '@/api/home'
export default {
  name: 'Dashboard',
  components:{PieChart , BarChart, PanelGroup},
  data(){
    return{
      userTotal:0,
      articleTotal:0,
      questionTotal:0,
      flag:false,
      categoryTotal:{},
      monthArticleTotal:{}
    }
  },
  mounted(){
    this.getTotal()
    this.getArticleTotal()
  },
  methods:{
    async getTotal() {
      const{data : userTotal}= await api.getUserTotal()
      this.userTotal = userTotal
      const{data: articleTotal} = await api.getArticleTotal()
      this.articleTotal = articleTotal
      const{data: questionTotal}= await api.getQuestionTotal()
      this.questionTotal = questionTotal
    },
    async getArticleTotal(){
      const {data: categoryTotal} = await api.getCategoryTotal()
      this.categoryTotal = categoryTotal
      const{data : monthArticleTotal} = await api.getMonthArticleTotal()
      this.monthArticleTotal = monthArticleTotal
      this.flag = true
    }
  }
}
</script>
<style lang="scss" scoped>
.dashboard {
  &-container{
    margin: 30px
  }
  &-text{
    font-size:30px;
    line-height:46px;
  }
}
</style>
