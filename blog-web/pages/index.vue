<template>
  <!-- 主体内容 -->
  <div>
    <el-row type="flex" justify="space-between">
      <!-- 左侧 -->
      <el-col class="hidden-sm-and-down" :md="3">
        <el-divider content-position="left">技术频道</el-divider>
        <el-menu actice-text-color="#ffffff" router :default-active="$route.path">
        <el-menu-item index="/">推荐</el-menu-item>
        <el-menu-item v-for="item in categoryList" :key="item.id" :index="'/' + item.id">
        {{item.name}}
        </el-menu-item>
        <!-- <el-menu-item index="/2">前端</el-menu-item>
        <el-menu-item index="/3">Python</el-menu-item>
        <el-menu-item index="/4">小程序</el-menu-item> -->
        </el-menu>
      </el-col>
      <!-- 中间 -->
      <!-- 24 24 16 -->
      <el-col :xs="24" :sm="24" :md="16"> 
        <div class="blog-center">
          <div class="banner">
            <el-carousel height="230px">
              <el-carousel-item v-for="item in mainAdvertList" :key="item.id">
                <a :href="item.advertUrl" :target="item.advertTarget">
                  <img :src="item.imageUrl" >
                </a>
              </el-carousel-item>
            </el-carousel>
          </div>
          <nuxt-child />
        </div>
      </el-col>
      <!-- 右侧 -->
      <el-col class="hidden-sm-and-down" :md="5">
        <el-row>
          <el-col>
            <el-card class="right-card" shadow="hovor" :body-style="{padding: '10px'}">
              <p> 课程推荐</p>
              <el-carousel height="210px">
                <el-carousel-item v-for="item in courseAdvertList" :key="item.id">
                  <!-- <a target="_blank" href="http://www.baidu.com">
                    <img src="~/assets/image/tlk.jpg">
                  </a> 
                  <span> canvas 必备动画效果大全</span> -->

                  <nuxt-link :target="item.advertTarget" :to="item.advertUrl">
                    <img :src="item.imageUrl">
                    <span> {{item.title}}</span>
                  </nuxt-link>
                  
                </el-carousel-item>
              </el-carousel>
            </el-card>
          </el-col>
        </el-row>
      </el-col> 
    </el-row>
  </div>
</template>

<script>
export default {
  async asyncData({app}){
    //查询技术频道
    const {data : categoryList} = await app.$getCategoryList()
    //查询滚动广告
    const {data : mainAdvertList} = await app.$getAdvertList(1)
    //获取课程推荐
    const {data : courseAdvertList} = await app.$getAdvertList(2)
    return { categoryList , mainAdvertList , courseAdvertList}
  },
}
</script>

<style scoped>
  @import '@/assets/css/blog/index.css';
</style>
