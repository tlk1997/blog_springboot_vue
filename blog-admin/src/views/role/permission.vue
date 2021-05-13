<template>
    <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="500px">
        <el-form ref="formData" v-loading="loading" label-width="80px">
           <el-tree 
            ref="tree" 
            :data="menuList" 
            :default-checked-keys="menuIds" 
            :props="{children: 'children' , label: 'name'}" 
            node-key="id" 
            show-checkbox accordion highlight-current/>
            <el-form-item align="right">
                <el-button size="mini" @click="handleClose">取 消</el-button>
                <el-button size="mini" type= "primary" @click="submitForm('formData')">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
import menuApi from '@/api/menu'
import roleApi from '@/api/role'

export default {
    watch:{
        visible: function(val){
            if(val){
                this.getMenuList()
            }
        }
    },
    props:{
        roleId:null,
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
        handleClose(done){
            this.menuIds = []
            this.menuList = []
            this.remoteClose()
        },
        submitForm(){
            const checkedMenuIds = this.$refs.tree.getCheckedKeys()
            roleApi.saveRoleMenu(this.roleId , checkedMenuIds).then(response => {
                if (response.code === 20000){
                    this.$message({
                        message: '保存成功',
                        type: 'success'
                    })
                    this.handleClose()
                }else{
                    this.$message({
                        type:'error',
                        message:'保存失败'
                    })
                }
            })
        },
        getMenuList(){
            this.loading = true
            menuApi.getList({}).then(response => {
                this.menuList = response.data
                this.getMenuIdsByRoleId(this,this.roleId)
                this.loading = false
            })
        },
        getMenuIdsByRoleId(roleId){
            roleApi.getMenuIdsByRoleId(roleId).then(response => {
                this.menuIds = response.data
            })
        }
    },
    data(){
        return{
            loading: false,
            menuList:[],
            menuIds:[]
        }
    }
}
</script>