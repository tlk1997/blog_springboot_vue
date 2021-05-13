<template>
    <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="70%">

        <el-form ref="formData" :model="formData" label-width="100px" label-position="right">
            <el-form-item label="标题：">
                <el-input v-model="formData.title" readonly/>
            </el-form-item>
            <el-form-item label="标签:">
                <el-cascader
                    disabled
                    style="display: block"
                    v-model="formData.labelIds"
                    :options="labelOptions"
                    :props="{mutiple: true , emitPath: false,children: 'labelList' , value:'id' , label: 'name'}"
                    />
            </el-form-item>
            <el-form-item label="主图:">
                <img :src="formData.imageUrl" class="avatar" style="width:178px;height:178px;display:block">
            </el-form-item>
            <el-form-item label="是否公开：">
                <el-radio-group v-model="formData.ispublic" disabled>
                    <el-radio :label="0">不公开</el-radio>
                    <el-radio :label="1">公开</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="简介">
                <el-input v-model="formData.summary" type="textarea" :autosize="{minRows:2}" readonly/>
            </el-form-item>
            <el-form-item label="内容">
                <mavonEditor ref="md" v-model="formData.mdContent" :editable="false"/>
            </el-form-item>
            <el-form-item align="center" v-if="isAudit">
                <el-button @click="auditSuccess()" type="primary">审核通过</el-button>
                <el-button @click="auditFail()" type="primary">审核未通过</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
import api from '@/api/article'
import categoryApi from '@/api/category'

import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
    components :{mavonEditor},
    watch:{
        visible(val){
            if(val){
                this.getArticleById()
                this.getLabelOptions()
            }
        }
    },
    props:{
        id:null,
        isAudit:true,
        visible:{
            type:Boolean,
            default:false
        },
        title:{
            type:String,
            default:''
        },
        
        remoteClose:Function
    },
    methods:{
        handleClose(){
            this.remoteClose()
        },
        auditSuccess(){
            this.$confirm('确认审核通过吗？' ,'提示',{
                cancelButtonText:'取消',
                confirmButtonText:'确定',
                type: 'warning'
            }).then(() => {
                api.auditSuccess(this.id).then(response => {
                    this.$message({
                        type: response.code === 20000 ? 'success' : 'error',
                        message: '审核通过提交成功'
                    })
                    this.remoteClose()
                })
            }).catch(()=> {

            })
        },
        auditFail(){
            this.$confirm('确认审核不通过吗？' ,'提示',{
                cancelButtonText:'取消',
                confirmButtonText:'确定',
                type: 'warning'
            }).then(() => {
                api.auditSuccess(this.id).then(response => {
                    this.$message({
                        type: response.code === 20000 ? 'success' : 'error',
                        message: '审核未通过提交成功'
                    })
                    this.remoteClose()
                })
            }).catch(()=> {

            })
        },
        getArticleById(){
            api.getById(this.id).then(response => {
                this.formData = response.data
            })
        },
        getLabelOptions(){
            categoryApi.getCategoryAndLabel().then(response => {
                this.labelOptions = response.data
            })
        },
        
    },
    data(){
        return{
            labelOptions:[],
            formData:{},
        }
    }
}
</script>