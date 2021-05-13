<template>
    <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="400px">
            <el-form
                :rules="rules"
                status-icon
                ref="formData"
                :model="formData"
                label-width="100px"
                label-position="right"
                style="width: 300px;"
                >
                <el-form-item label="标签名称:" prop="name">
                    <el-input v-model="formData.name"/>
                </el-form-item>
                <el-form-item label="分类名称:" prop="categoryId">
                    <el-select v-model="formData.categoryId" clearable filterable>
                        <el-option v-for="item in categoryList"
                                   :key="item.id"
                                   :label="item.name"
                                   :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('formData')" size="mini">确定</el-button>
                    <el-button size="mini" @click="handleClose">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
</template>

<script>
import api from '@/api/label'
export default {
    props:{
        categoryList:{
            type:Array,
            default:[]
        },
        visible:{
            type:Boolean,
            default:false
        },
        title:{
            type:String,
            default:''
        },
        formData:{
            type: Object,
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
            this.$refs[formName].validate((valid) =>{
                if (valid){ 
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
            rules: {
                name:[ 
                    {required: true , message: '请输入标签名称' , trigger: 'blur'}
                ],
                categoryId: [ 
                    {required: true , message: '请输入分类名称' , trigger: 'change'}
                ],
                

            }
        }
    }
}
</script>