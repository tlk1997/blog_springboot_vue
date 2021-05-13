<template>
    <div class="app-container">
        <el-form :inline="true" size="mini">
            <el-form-item label="标签名称:">
                <el-input v-model.trim="query.name"/>
            </el-form-item>
            <el-form-item label="分类名称:">
                <el-select v-model="query.categoryId" clearable filterable>
                    <el-option v-for="item in categoryList"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button icon='el-icon-search' type="primary" @click="queryData">查询</el-button>
                <el-button icon='el-icon-refresh' class="filter-item" @click="reload">重置</el-button>
                <el-button size="mini" type="primary" icon="el-icon-circle-plus-outline" @click="openAdd">新增</el-button>
            </el-form-item>
        </el-form>
         <el-table
            :data="list"
            border
            highlight-current-row
            style="width: 100%">
            <el-table-column align="center" type="index" label="序号" width="60"></el-table-column>
            <el-table-column align="center" prop="name" label="标签名称" ></el-table-column>
            <el-table-column align="center" prop="categoryName" label="分类名称"></el-table-column>
            <el-table-column align="center" label="操作" width="260">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row.id)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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
        <edit :categoryList="categoryList" :title="edit.title" :visible="edit.visible" :formData="edit.formData" :remoteClose="remoteClose"/>
    </div>

</template>
<script>
import api from '@/api/label'
import categoryApi from '@/api/category'
import Edit from './edit'
export default {
    components:{Edit},
    name:'Label',
    data(){
        return {
            list : [],
            page:{
                total: 0,
                currrent: 1,
                size:20,
            },
            query: {},
            categoryList:[],
            edit:{
                title: '',
                visible: false,
                formData:{}
            }
        }
    },
    
    created(){
        this.fetchData()
        this.getCategoryList()
    },
    methods:{
        fetchData(){
            api.getList(this.query , this.page.currrent , this.page.size).then(response => {
                this.list = response.data.records
                this.page.total = response.data.total
            })
        },
        handleEdit(id){
            console.log('编辑',id)
            api.getById(id).then(response => {
                if(response.code === 20000){
                    this.edit.formData = response.data
                    this.edit.visible = true
                    this.edit.title = '编辑'
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
        handleSizeChange(val){
            this.page.size = val
            this.fetchData()
        },
        handleCurrentChange(val){
            this.page.current = val
            this.fetchData()
        },
        getCategoryList(){
            categoryApi.getNormalList().then(response => {
                console.log(response)
                this.categoryList = response.data
            })
        },
        queryData(){
            this.page.currrent = 1
            this.fetchData()
        },
        reload(){
            this.query={}
            this.fetchData()
        },
        remoteClose(){
            this.edit.formData = {}
            this.edit.visible = false
            this.fetchData()
        },
        openAdd(){
            this.getCategoryList()
            this.edit.visible = true
            this.edit.title = '新增'
        }
    }
}
</script>

<style scoped>
.app-container {
    margin: 30px;
}
</style>