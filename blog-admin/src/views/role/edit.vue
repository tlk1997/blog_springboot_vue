<template>
    <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="500px">
        <el-form
            :rules = rules
            ref="formData"
            :model="formData"
            label-width="100px"
            style="width: 400px"
            status-icon>
            <el-form-item label="名称" prop="name">
                <el-input v-model="formData.name"/>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input v-model="formData.remark" type="textarea" :autosize="{ minRows: 2 , maxRows: 4}" placeholder="请输入内容"/>
            </el-form-item>
            <el-form-item align="right">
                <el-button size="mini" @click="handleClose">取 消</el-button>
                <el-button size="mini" type= "primary" @click="submitForm('formData')">确 定</el-button>

            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
import api from '@/api/menu'
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
                    return false
                }
            })
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
                    message:'保存成功',
                    type:'success'
                })
                this.handleClose()
            }else{
                this.$message({
                    message:'保存失败',
                    type:'error'
                })
            }
        },
        
    },
    data(){
        return{
            rules:{
                name:[{required: true ,message:"请输入菜单名称" , trigger: 'blur'}],
            }
        }
    }
}
</script>