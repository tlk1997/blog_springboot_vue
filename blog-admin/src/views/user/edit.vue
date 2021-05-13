<template>
    <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="500px">
        <el-form
            :rules = rules
            ref="formData"
            :model="formData"
            label-width="100px"
            style="width: 400px"
            status-icon>
            <el-form-item label="用户名" prop="username">
                <el-input v-model="formData.username" maxlength="50"/>
            </el-form-item>
            <el-form-item label="昵称" prop="nickName">
                <el-input v-model="formData.nickName" maxlength="50"/>
            </el-form-item>
            <el-form-item label="手机号" prop="mobile">
                <el-input v-model="formData.mobile" maxlength="11"/>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="formData.email" />
            </el-form-item>
            <el-form-item label="账号过期" prop="isAccountNonExpired">
                <el-radio-group v-model="formData.isAccountNonExpired">
                    <el-radio :label="1" border>未过期</el-radio>
                    <el-radio :label="0" border>已过期</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="账号锁定" prop="isAccountNonLocked">
                <el-radio-group v-model="formData.isAccountNonLocked">
                    <el-radio :label="1" border>未锁定</el-radio>
                    <el-radio :label="0" border>已锁定</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="密码过期" prop="isCredentialsNonExpired">
                <el-radio-group v-model="formData.isCredentialsNonExpired">
                    <el-radio :label="1" border>未过期</el-radio>
                    <el-radio :label="0" border>已过期</el-radio>
                </el-radio-group>
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
        }
    },
    data(){
        return{
            rules:{
                username:[{required: true ,message:"请输入用户名" , trigger: 'blur'} ,
                          {min: 6 , max : 30, message:"长度在6到30个字符之间" , trugger: 'blur'}],
                nickName:[{required: true , message:'请输入昵称' , trigger: 'blur'}],
                mobile:[{required: true , message:'请输入手机号' , trigger: 'blur'}],
                isAccountNonExpired:[{required: true , message:'请选择' , trigger: 'change'}],
                isAccountNonLocked:[{required: true , message:'请选择' , trigger: 'change'}],
                isCredentialsNonExpired:[{required: true , message:'请选择' , trigger: 'change'}],

            }
        }
    }
}
</script>