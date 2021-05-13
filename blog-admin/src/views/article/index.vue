<template>
    <div class="app-container">
         <el-form :inline="true"  size="mini">
            <el-form-item label="文章标题:">
                <el-input v-model.trim="query.title"></el-input>
            </el-form-item>
            <el-form-item label="状态：">
                <el-select v-model="query.status" clearable filterable style="width: 100px">
                    <el-option :value="1" label="未审核"></el-option>
                    <el-option :value="2" label="审核通过"></el-option>
                    <el-option :value="3" label="审核未通过"></el-option>
                    <el-option :value="4" label="已删除"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button icon='el-icon-search' type="primary" @click="queryData">查询</el-button>
                <el-button icon='el-icon-refresh' class="filter-item" @click="reload">重置</el-button>
            </el-form-item>
        </el-form>
         <el-table
            :data="list"
            border
            highlight-current-row
            style="width: 100%">
            <el-table-column align="center" type="index" label="序号" width="60"></el-table-column>
            <el-table-column align="center" prop="title" label="文章标题"></el-table-column>
            <el-table-column align="center" prop="viewCount" label="浏览量"></el-table-column>
            <el-table-column align="center" prop="thumhup" label="点赞数"></el-table-column>
            <el-table-column align="center" prop="ispublic" label="是否公开">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.ispublic === 0" type="danger">不公开</el-tag>
                    <el-tag v-if="scope.row.ispublic === 1" type="warning">公开</el-tag>
                </template>

            </el-table-column>
            <el-table-column align="center" prop="status" label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status === 1" >未审核</el-tag>
                    <el-tag v-if="scope.row.status === 2" type="success">审核通过</el-tag>
                    <el-tag v-if="scope.row.status === 3" type="warning">审核未通过</el-tag>
                    <el-tag v-if="scope.row.status === 4" type="danger">已删除</el-tag>

                </template>            
            </el-table-column>
            <el-table-column align="center" prop="updateDate" label="最后更新时间">
                <template slot-scope="scope">
                    {{getFormat(scope.row.updateDate)}}
                </template>
            </el-table-column>
            <el-table-column align="left" label="操作" width="220">
                <template slot-scope="scope">
                    <el-button size="mini" @click="openView(scope.row.id)" type="primary">查看</el-button>
                    <el-button size="mini" v-if="scope.row.status === 1" @click="openAudit(scope.row.id)" type="success">审核</el-button>
                    <el-button size="mini" v-if="scope.row.status !== 0" @click="handleDelete(scope.row.id)" type="danger">删除</el-button>
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
        <audit :id="audit.id" :isAudit="audit.isAudit" :title="audit.title" :visible="audit.visible" :remoteClose="remoteClose"/>
    </div>
</template>
<script>
import api from '@/api/article'
import { format } from '@/utils/date'
import Audit from './audit.vue'
export default{
    components:{Audit},
    name: 'Article',
    data(){
        return{
            list:[],
            page:{
                total:0,
                current:1,
                size:20
            },
            query:{},
            audit:{
                id:null,
                isAudit:true,
                visible:false,
                title:'',
            }
        }
    },
    created(){
        this.fetchData()
    },
    methods:{
        fetchData(){
            api.getList(this.query , this.page.current , this.page.size).then(response =>{
                this.list = response.data.records
                this.page.total = response.data.total
            })
        },
        getFormat(date){
            return format(date)
        },
        openAudit(id){
            console.log('审核',id)
            this.audit.id = id
            this.audit.isAudit = true
            this.audit.title = '审核文章'
            this.audit.visible = true
        },
        handleDelete(id){
            console.log('删除',id)
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
        queryData(){
            this.page.currrent = 1
            this.fetchData()
        },
        reload(){
            this.query={}
            this.fetchData()
        },
        remoteClose(){
            this.audit.visible = false;
            this.fetchData()
        },
        openView(id){
            this.audit.id = id
            this.audit.isAudit = false
            this.audit.title = '文章详情'
            this.audit.visible = true
        }
    }
}
</script>

<style scoped>
.app-container {
    margin: 30px;
}
</style>
