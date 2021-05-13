<template>
    <!-- 底部 -->
    <!-- nofollow 告诉搜索引擎"不要追踪此网页上的链接或不要追踪此特定链接 -->
    <div class="tlk-footer">
      <div class="warp">
        <!-- <div class="top-line"></div> -->
        <el-divider>tlk's blog!!!</el-divider>
        <div class="footer-link">
          <a rel="nofollow" target="_blank" href="">aaa</a><span>/</span>
          <a rel="nofollow" target="_blank" href="">bbb</a><span>/</span>
          <a rel="nofollow" target="_blank" href="">ccc</a><span>/</span>
          <a rel="nofollow" target="_blank" href="">ddd</a>
        </div>
        
      </div>
      
    </div> 
</template>

<script>
export default {
    
}
</script>
<style scoped>
  /* 底部 */
  .tlk-footer {
    padding-top: 60px !important;
    padding-bottom: 50px !important;
    background: #fff;
  }
  .tlk-footer .warp {
      max-width: 1190px;
      margin: 0 auto;
  }
  .tlk-footer .warp .top-line{
    width: 1000px;
    height: 1px;
    background: #e3ebf0;
    margin: 0 auto;
  }
  .tlk-footer .warp .footer-link {
    height: 18px;
    line-height: 18px;
    text-align: center;
    margin-top: 40px;
    font-family: PingFangSC-Regular;
    font-size: 16px;    
  }
  
  .tlk-footer .warp .footer-link a {
    display: inline-block;
    color: #2C2C40;
  }

  .tlk-footer .warp .footer-link span {
    display: inline-block;
    color: #2C2C40;
    padding: 0 10px;
  }

  .tlk-footer .warp .footer-information-box {
    font-family: PingFangSC-Regular;
    text-align: center;
    margin: 30px 0;
    font-size: 12px;
    line-height: 12px;
    color: #2C2C40;
  }
  
</style>