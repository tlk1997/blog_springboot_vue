<template>
    <div class="app-container">
        <el-form :inline="true" size="mini">
            <el-form-item label="菜单名称: ">
                <el-input v-model.trim="query.name"/>
            </el-form-item>
            <el-form-item>
                <el-button icon="el-icon-search" type="primary" @click="fetchData">查询</el-button>
                <el-button icon="el-icon-refresh" class="filter-item" @click="reload">重置</el-button>
                <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="handleAdd(0)">新增</el-button>
            </el-form-item>
        </el-form>


        <el-table
            :data="list"
            row-key="id"
            border
            highlight-current-row
            style="width: 100%">
            <el-table-column align="center" type="index" label="序号" width="60"></el-table-column>
            <el-table-column align="left" prop="name" label="名称"></el-table-column>
            <el-table-column align="center" prop="url" label="请求地址"></el-table-column>
            <el-table-column align="center" prop="code" label="权限标识"></el-table-column>
            <el-table-column align="center" prop="type" label="类型">
                <template slot-scope="scope">
                    <span v-if="scope.row.type === 1">目录</span>
                    <span v-if="scope.row.type === 2">菜单</span>
                    <span v-if="scope.row.type === 3">按钮</span>
                </template>
            </el-table-column>
            <el-table-column align="center" prop="code" label="图标">
                <template slot-scope="scope">
                    <i :class="scope.row.icon"/>
                </template>
            </el-table-column>
            <el-table-column align="center" prop="sort" label="排序"></el-table-column>
            <el-table-column align="center" label="操作" width="260">
                <template slot-scope="scope">
                    <!-- <el-button @click="handleAdd(0)" size="mini" type="primary" icon="el-icon-circle-plus-outline">新增</el-button> -->
                    <el-button @click="handleAdd(scope.row.id)" size="mini" type="primary" icon="el-icon-circle-plus-outline">新增</el-button>
                    <el-button @click="handleEdit(scope.row.id)" size="mini">编辑</el-button>
                    <el-button @click="handleDelete(scope.row.id)" size="mini" type="danger">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <edit :title="edit.title" :visible="edit.visible" :formData="edit.formData" :remoteClose="remoteClose"/>
    </div>
</template>

<script>
import api from '@/api/menu'
import Edit from './edit.vue'
export default {
    components:{Edit},
    data() {
        return {
            query: {},
            list: [],
            edit:{
                title:'',
                visible:false,
                formData:{}
            }
        }
    },
    created(){
        this.fetchData()
    },
    methods:{
        fetchData(){
            api.getList(this.query).then(response => {
                this.list = response.data
                console.log(response)
            })
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
                    this.edit.title = '编辑'
                    this.edit.visible = true
                }
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
        reload(){
            this.query = {},
            this.fetchData()
        },
        remoteClose(){
            this.edit.formData = {}
            this.edit.visible = false
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