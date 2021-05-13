<template>
    <div class="app-container">
        <el-form :inline="true" size="mini">
            <el-form-item label="角色名称:">
                <el-input v-model.trim="query.name"/>
            </el-form-item>
            <el-form-item>
                <el-button icon='el-icon-search' type="primary" @click="queryData">查询</el-button>
                <el-button icon='el-icon-refresh' class="filter-item" @click="reload">重置</el-button>
                <el-button v-if="!roleIds" type="primary" size="mini" icon='el-icon-circle-plus-outline' @click="handleAdd">新增</el-button>
                <el-button v-if="roleIds" icon="el-icon-circle-plus-outline" type="success" @click="handleUserRole">设置角色</el-button>

            </el-form-item>
        </el-form>
        <el-table row-key="id" ref="dataTable" :data="list" @selection-change="handleSelectionChange" stripe border style="width: 100%">
            <el-table-column v-if="roleIds" type="selection" reverse-selection align="center" width="60"></el-table-column>
            <el-table-column align="center" type="index" label="序号" width="60"></el-table-column>
            <el-table-column align="center" prop="name" label="角色名称"></el-table-column>
            <el-table-column align="center" prop="remark" label="备注"></el-table-column>
            <el-table-column v-if="!roleIds" align="center" label="操作">
                <template slot-scope="scope">
                    <el-button @click="handlePermission(scope.row.id)" size="mini" type="success">分配权限</el-button>
                    <el-button @click="handleEdit(scope.row.id)" size="mini" >编辑</el-button>
                    <el-button @click="handleDelete(scope.row.id)" size="mini" type="danger">删除</el-button>

                </template>
            </el-table-column>
                
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="page.current"
            :page-sizes="[10,20,50]"
            layout="total,sizes,prev, pager, next,jumper"
            :page-size="page.size"
            :total="page.total">
        </el-pagination>
        <edit :title="edit.title" :visible="edit.visible" :formData="edit.formData" :remoteClose="remoteClose"/>
        <permission title="分配权限" :visible="per.visible" :remoteClose="remotePerClose" :roleId="per.roleId"/>
    </div>
 </template>

<script>
import api from '@/api/role'
import Edit from './edit.vue'
import Permission from './permission.vue'

export default {
    components:{Edit , Permission},
    
    props:{
        roleIds : null
    },
    data(){
        return{
            list:[],
            page:{
                total:0,
                current:1,
                size:20
            },
            query: {},
            edit:{
                title: '',
                visible:false,
                formData:{}
            },
            per:{
                roleId: null,
                visible: false,
            },
            checkedRoleList:[]
        }
    },
    created(){
        this.fetchData()
    },
    methods:{
        fetchData(){
            api.getList(this.query , this.page.current , this.page.size).then(response => {
                this.list = response.data.records
                this.page.total = response.data.total
                this.checkedRoles()
                console.log(response)
            })
        },
        checkedRoles(){
            this.$refs.dataTable.clearSelection()
            if(this.roleIds){
                console.log('roleIds' , this.roleIds)
                console.log('list')
                this.list.forEach(item => {
                    if(this.roleIds.indexOf(item.id) !== -1){
                        this.$refs.dataTable.toggleRowSelection(item,true)
                    }
                })
            }
        },
        handleAdd(id){
            console.log('新增')
            this.edit.formData.parentId = id
            this.edit.visible = true
            this.edit.title = '新增'
        },
        handleEdit(id){
            console.log('编辑')
            api.getById(id).then(response => {
                if(response.code === 20000){
                    this.edit.formData = response.data
                }
                this.edit.title = '编辑'
                this.edit.visible = true
                 
             })
        },
        handleDelete(id){
            console.log('删除')
            this.$confirm('确认删除这条记录吗？' ,'提示',{
                cancelButtonText:'取消',
                confirmButtonText:'确定',
                type: 'warning'
            }).then(() => {
                api.deleteById(id).then(response => {
                    this.$message({
                        type: response.code === 20000 ? 'success' : 'error',
                        message: response.message
                    })
                    console.log('删除成功')
                    this.fetchData()
                })
            }).catch(()=> {

            })
        },
        handlePermission(id){
            console.log('分配权限')
            this.per.visible = true
            this.per.roleId = id
        },
        reload(){
            this.query = {},
            this.fetchData()
        },
        remoteClose(){
            this.edit.formData = {}
            this.edit.visible = false
            this.fetchData()
        },
        handleSizeChange(val){
            this.page.size = val
            this.fetchData()
        },
        handleCurrentChange(val){
            this.page.current = val
            this.fetchData()
        },
        queryData(){
            this.page.current = 1
            this.fetchData()
        },
        remotePerClose(){
            this.per.visible = false
            this.per.roleId = null
            this.fetchData()
        },
        handleSelectionChange(val){
            this.checkedRoleList = val
        },
        handleUserRole(){
            const CheckedRoleIds = []
            this.checkedRoleList.forEach(item => {
                CheckedRoleIds.push(item.id)
            })
            console.log('CheckedRoleIds',CheckedRoleIds)
            this.$emit('saveUserRole',CheckedRoleIds)

        },
        
    },
    watch :{
        roleIds() {
            this.query = []
            this.queryData()
        }
    },
        
    
}
</script>

<style scoped>
.app-container {
    margin: 30px;
}
</style>