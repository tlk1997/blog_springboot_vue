<template>
    <!--弹窗 :close-on-click-modal="false" 点击遮罩层不关闭窗口 -->
    <div>
        <!-- status-icon 当表单校验不通过时, 输入框右侧有个 x 小图标 -->
        <el-form
        ref="formData"
        :model="formData"
        label-width="100px"
        label-position="right"
        :rules="rules"
          >
          <el-form-item label="标题:" prop="title" >
              <el-input v-model="formData.title" placeholder="请输入标题"  
                        maxlength="50" show-word-limit/>
          </el-form-item>
          <el-form-item label="标签:" prop="labelIds" >
            <!--:show-all-levels="false" 仅显示最后一级， :filterable="true" 可搜索 
                props{ multiple: true 多选， emitPath: false 只返回子节点的value } -->
            <el-cascader 
                        :disabled="disabled"
                        style="display: block"
                        v-model="formData.labelIds"
                        :options="labelOptions"  
                        :show-all-levels="false"
                        :props="{ multiple: true, emitPath: false, children: 'labelList', value: 'id', label: 'name'}" 
                        :filterable="true"
                        clearable/>
          </el-form-item>
          
       
          <el-form-item label="内容:" prop="content">
	          <!-- 主体内容 -->
            <client-only>
            <mavon-editor :autofocus="false"
                           ref="md" v-model="formData.mdContent"
                           @change="getMdHtml"
                           @imgAdd="uploadContentImg"
                           @imgDel="delContentImg"/>
            </client-only>
          </el-form-item>
        </el-form>
      	<el-row type="flex" justify="center">
            <el-button type="primary" @click="submitForm('formData')">发布问题</el-button>
        </el-row>
    </div>
</template>

<script>

export default {
    middleware:'auth',
    validate ({query}){
      //如果传递id修改
      if(query.id){
        // 必须是number类型
        return /^\d+$/.test(query.id)
      }
      return true
    },
    data() {
      const validateLabel = (rule , value , callback) =>{
        if (value && value.length > 5){
          this.disabled = true
          callback(new Error('最多可选5个标签'))
        }else{
          callback()
          this.disabled = false
        }
      }
      const validateContent = (rule , value , callback) => {
        if (this.formData.mdContent && this.formData.htmlContent){
          callback()
        }else{
          callback(new Error('请求输入文章内容'))
        }
      }
        return {
          rules: {
            title:[{required: true, message:'请输入标题',trigger:'blur'}],
            labelIds: [
              {required: true, message:'请输入标签',trigger:'blur'},
              { validator: validateLabel , trigger:'change'},
            ],
            content:[{validator: validateContent,trigger:'change'}]
          },
          formData: {
            imageUrl:null,
            userId:this.$store.state.userInfo.uid,
            userImage:this.$store.state.userInfo.imageUrl,
            nickName:this.$store.state.userInfo.nickName
          }, // 表单数据
          disabled: false, // 标签输入框默认可输入
          labelOptions: [ // 标签下拉框
          ],
        }
    },
    async asyncData({ query , app}){
        const { data } = await app.$getCategoryAndLabel()
        if (query.id){
          const {data: formData} = await app.$getQuestionById(query.id)

        return {labelOptions : data , formData}
        }
        return {labelOptions : data}
    },
    methods: {
        // 提交表单
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
              if (valid){
                console.log(this.formData)
                this.submitData()
              }else{
                return false
              }
            })
        },
        async submitData(){
          let response = null
          if (this.formData.id){
              response = await this.$updateQuestion(this.formData)
          }else{
              response = await this.$addQuestion(this.formData)
          }
          if (response.code === 20000){
            this.$message.success('提交成功')
            this.$router.push(`/question/${response.data}`)
          }
        },
        //上传主图
        uploadMainImg(file) {
          const data = new FormData();
          data.append('file' , file.file);

          this.$uploadImg(data).then(response => {
            this.deleteImg()
            this.formData.imageUrl = response.data
          }).catch(() => {
            this.$message.error('上传失败')
          })
        },
        // 删除主图
        deleteImg(){
          if (this.formData.imageUrl){
            this.$deleteImg(this.formData.imageUrl)
          }
        },
        getMdHtml(mdContent,htmlContent){
          console.log('mdContent',mdContent)
          console.log('htmlContent',htmlContent)
          this.formData.mdContent = mdContent
          this.formData.htmlContent = htmlContent
        },
        uploadContentImg(pos,file){
          var fd = new FormData();
          fd.append('file' , file);
          this.$uploadImg(fd).then(response => {
            console.log(response)
            this.$refs.md.$img2Url(pos , response.data)
          })

        },
        delContentImg(urlAndFileArr) {
          const fileUrl = urlAndFileArr[0]
          const file = urlAndFileArr[1]
          console.log('删除图片',fileUrl , file)
          this.$deleteImg(fileUrl)
        }
    },
}

</script>


<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>