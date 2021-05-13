<template>
    <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="600px">
        <el-form
            :rules="rules"
            status-icon
            ref="formData"
            :model="formData"
            label-width="100px"
            label-position="right"
            style="width: 500px;">
            <el-form-item label="广告图片" prop="imageUrl">
                <el-upload class="avatar-uploader"
                           accept="image/*"
                           action=""
                           :show-file-list="true"
                           :http-request="uploadMainImg">
                    <img v-if="formData.imageUrl" :src="formData.imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"/>
                </el-upload>
            </el-form-item>
            <el-form-item label="广告标题:" prop="title">
                <el-input v-model="formData.title"/>
            </el-form-item>
            <el-form-item label="广告链接:" prop="advertUrl">
                <el-input v-model="formData.advertUrl"/>
            </el-form-item>
            <el-form-item label="跳转方式:" prop="advertTarget">
                <el-select v-model="formData.advertTarget" clearable style="width: 185px">
                    <el-option label="新窗口打开" value="_blank"/>
                    <el-option label="当前窗口打开" value="_self"/>
                </el-select>
            </el-form-item>
            <el-form-item label="广告位置:" prop="position">
                <el-input v-model="formData.position"/>
            </el-form-item>
            <el-form-item label="状态:" prop="status">
                <el-radio-group v-model="formData.status">
                    <el-radio :label="1">正常</el-radio>
                    <el-radio :label="0">禁用</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="排序:" prop="sort">
                <el-input-number style="width: 300px;" v-model="formData.sort" :min="1" :max="10000"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button size="mini" @click="handleClose">取消</el-button> 
            <el-button size="mini" type="primary" @click="submitForm('formData')">确定</el-button> 
        </div>
    </el-dialog>
</template>

<script>
import api from '@/api/advert'
import commonApi from '@/api/common'
export default {
    props:{
        visible:{
            type:Boolean,
            default:false
        },
        title:{
            type:String,
            default:''
        },
        formData:{
            type:Object,
            default:{}
        },
        oldImageUrl: String,
        remoteClose:Function
    },
    methods:{
        handleClose(done){
            this.$refs['formData'].resetFields()
            this.remoteClose()
        },
        submitForm(formName){
            this.$refs[formName].validate((valid) => {
                if(valid){
                    this.submitData()
                }else{
                    return false;
                }
            })

        },
        uploadMainImg(file){
            const data = new FormData()
            data.append('file',file.file)

            commonApi.uploadImg(data).then(response => {
                this.deleteImg()
                this.formData.imageUrl = response.data
            }).catch(() => {
                this.$message({
                    showClose:true,
                    message:'上传失败',
                    type:'error'
                });
            })
        },
        deleteImg(){
            if (this.formData.imageUrl && this.formData.imageUrl !== this.oldImageUrl){
                commonApi.deleteImg(this.formData.imageUrl)
            }
        },
        async submitData(){
            let response = null
            if(this.formData.id){
                response = await api.update(this.formData)
                console.log('修改成功')
            }else{

                response = await api.add(this.formData)
                console.log('新增成功')

            }

            if(response.code === 20000){
                this.$message({
                    message: '保存成功',
                    type: 'success'
                })
                this.handleClose()
            }else{
                this.$message({
                    message: '保存失败',
                    type: 'error'
                })
            }
        }
    },
    data(){
        return{
            rules: {
                imageUrl:[ 
                    {required: true , message: '请上传图片广告' , trigger: 'blur'}
                ],
                title: [ 
                    {required: true , message: '请输入广告标题' , trigger: 'blur'}
                ],
                advertUrl:[
                    {required: true , message: '请选择广告链接' , trigger: 'blur'}
                ],
                status:[
                    {required: true , message: '请选择状态' , trigger: 'change'}
                ],
                position:[
                    {required: true , message: '请选择广告位置' , trigger: 'blur'}
                ],
                sort:[
                    {required: true , message: '请输入排序' , trigger: 'blur'}
                ],

            }
        }
    }
}
</script>
<style scoped>
.avatar-uploader .el-upload {
    border:1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover{
    border-color: #409EFF;
}
.avatar-uploader-icon{
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height:178px;
    text-align: center;
}
.avatar{
    width:178px;
    height:178px;
    display:block
}
</style>   

