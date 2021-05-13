<template>
    <div class="app-container">
        <el-form :inline="true" size="mini">
            <el-form-item label="用户号:">
                <el-input v-model.trim="query.username"/>
            </el-form-item>
            <el-form-item label="手机号:">
                <el-input v-model.trim="query.mobile"/>
            </el-form-item>
            <el-form-item>
                <el-button icon='el-icon-search' type="primary" @click="queryData">查询</el-button>
                <el-button icon='el-icon-refresh' class="filter-item" @click="reload">重置</el-button>
                <el-button type="primary" size="mini" icon='el-icon-circle-plus-outline' @click="handleAdd">新增</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="list" border highlight-current-row style="width: 100%">
            <el-table-column align="center" type="index" label="序号" width="60"></el-table-column>
            <el-table-column align="center" prop="username" label="用户名"></el-table-column>
            <el-table-column align="center" prop="nickName" label="昵称"></el-table-column>
            <el-table-column align="center" prop="mobile" label="手机号"></el-table-column>
            <el-table-column align="center" prop="email" label="邮箱"></el-table-column>
            <el-table-column align="center" prop="isAccountNonExpired" label="账号过期" width="80">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isAccountNonExpired === 0" type="danger">过期</el-tag>
                    <el-tag v-if="scope.row.isAccountNonExpired === 1" type="success">正常</el-tag>
                </template>
            </el-table-column>
            <el-table-column align="center" prop="isAccountNonLocked" label="账号锁定" width="80">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isAccountNonLocked === 0" type="danger">锁定</el-tag>
                    <el-tag v-if="scope.row.isAccountNonLocked === 1" type="success">正常</el-tag>
                </template>
            </el-table-column>
            <el-table-column align="center" prop="isCredentialsNonExpired" label="密码过期" width="80">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isCredentialsNonExpired === 0" type="danger">过期</el-tag>
                    <el-tag v-if="scope.row.isCredentialsNonExpired === 1" type="success">正常</el-tag>
                </template>
            </el-table-column>
            <el-table-column align="center" prop="isEnabled" label="是否可用" width="80">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isEnabled === 0" type="danger">已删除</el-tag>
                    <el-tag v-if="scope.row.isEnabled === 1" type="success">可用</el-tag>
                </template>
            </el-table-column>
            <el-table-column align="center" label="操作" width="340">
                <template slot-scope="scope" v-if="scope.row.isEnabled === 1">
                    <el-button @click="handleEdit(scope.row.id)" size="mini" >编辑</el-button>
                    <el-button @click="handleDelete(scope.row.id)" size="mini" type="danger">删除</el-button>
                    <el-button @click="handleRole(scope.row.id)" size="mini" type="success">设置角色</el-button>
                    <el-button @click="handlePwd(scope.row.id)" size="mini" type="success">密码修改</el-button>
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
        <el-dialog title = "设置角色" :visible.sync="role.visible" width="65%">
            <Role :roleIds="role.roleIds" @saveUserRole="saveUserRole" />
        </el-dialog>
        <Password title="修改密码" :userId="pwd.userId" :visible="pwd.visible" :remoteClose="remotePwdClose"/>
    </div>
 </template>

<script>
import api from '@/api/user2'
import Edit from './edit.vue'
import Role from '../role/index.vue'
import Password from './password.vue'
export default {
    components:{Edit , Role ,Password},
    data(){
        return{
            list:[],
            page:{
                total:0,
                current:1,
                size:20
            },
            query:{},
            edit:{
                title: '',
                visible: false,
                formData:{}
            },
            role:{
                userId : null,
                roleIds :[],
                visible : false
            },
            pwd:{
                visible :false,
                userId : ''
            }
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
                console.log(response)
            })
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
        reload(){
            this.query = {}
            this.fetchData()
        },
        handleAdd(){
            console.log('新增')
            this.edit.visible = true
            this.edit.title = '新增一默认密码与用户名一致'
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
        handleRole(id){
            console.log('设置角色')
            this.userId = id
            api.getRoleIdsByUserid(id).then(response => {
                this.role.roleIds = response.data
                this.role.visible = true

            })
        },
        handlePwd(id){
            console.log('修改密码')
            this.pwd.userId = id
            this.pwd.visible = true
        },
        remoteClose(){
            this.edit.formData = {}
            this.edit.visible = false
            this.fetchData()
        },
        saveUserRole(roleIds) {
            console.log('saveUserRole', roleIds)
            api.saveUserRole(this.role.userId, roleIds).then(response => {
                if(response.code === 20000) {
                    this.$message( {
                        message: '保存成功',
                        type: 'success'
                    } )
                    this.role.visible = false
                }else {
                    this.$message( {
                        message: '保存失败', 
                        type: 'error'
                    } )
                }
            })
        },
        remotePwdClose(){
            this.pwd.userId = null
            this.pwd.visible = false
            this.fetchData()
        }
        


    }
}
</script>

<style scoped>
.app-container {
    margin: 30px;
}
</style>