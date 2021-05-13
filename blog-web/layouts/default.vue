<template>
  <div>
    <!-- 头部 -->
    <tlk-header />
    <!-- 中间主体区域 -->
    <div class="tlk-main">
      <nuxt />
    </div>
    <!-- 底部 -->
    <tlk-footer />

    <el-backtop :bottom="100">
      <div
        style="{
          height: 100%;
          width: 100%;
          background-color: #f2f5f6;
          box-shadow: 0 0 6px rgba(0,0,0, .12);
          text-align: center;
          line-height: 40px;
          color: #1989fa
         }"
        >
        UP
      </div>
    </el-backtop>
  </div>
</template>


<script>
import tlkHeader from '~/components/layout/Header'
import tlkFooter from '~/components/layout/Footer'
export default {
  components:{
    tlkHeader,
    tlkFooter,
  }
}
</script>

<style>
.tlk-main {
  padding-top: 90px;
  max-width:1140px;
  margin:0 auto;
}
</style>
