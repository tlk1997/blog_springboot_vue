<template>
  <div class="app-container">
    <el-form :inline="true" size="mini">
      <el-form-item label="广告标题:">
        <el-input v-model.trim="query.name"></el-input>
      </el-form-item>
      <el-form-item label="状态：">
        <el-select v-model="query.status" clearable filterable style="width: 85px">
          <el-option v-for="item in statusOptions"
                     :key="item.code"
                     :label="item.name"
                     :value="item.code">
          </el-option>
       </el-select>
      </el-form-item>
      <el-form-item>
          <el-button icon='el-icon-search' type="primary" @click="queryData">查询</el-button>
          <el-button icon='el-icon-refresh' class="filter-item" @click="reload">重置</el-button>
          <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="openAdd">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
            :data="list"
            border
            highlight-current-row
            style="width: 100%">
       <el-table-column align="center" type="index" label="序号" width="60"></el-table-column>
        <el-table-column align="center" prop="title" label="广告标题">
        </el-table-column>
        <el-table-column align="center" label="广告图片">
          <template slot-scope="scope">
            <el-image :src="scope.row.imageUrl" :preview-src-list="[scope.row.imageUrl]" style="width: 90px;heigut: 60px"/>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="advertUrl" label="广告链接"></el-table-column>
        <el-table-column align="center" prop="STATUS" label="状态">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 0" type="danger">禁用</el-tag>
            <el-tag v-if="scope.row.status === 1" type="warning">正常</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" >
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
    <edit :title="edit.title" :visible="edit.visible" :oldImageUrl="edit.oldImageUrl" :formData="edit.formData" :remoteClose="remoteClose"/>
  </div>
</template>


<script>

import api from '@/api/advert'
import Edit from './edit.vue'
const statusOptions = [
        {code: 0 , name:'禁用'},
        {code: 1 , name:'正常'}
    ]
export default {
  components:{Edit},
  data(){
    return {
      list:[],
      page:{
        total:0,
        current:1,
        size:20
      },
      edit:{
        title:'',
        visible:false,
        oldImageUrl:null,
        formData:{
          imageUrl:null
        }
      },
      link:'',
      query:{},
      statusOptions,
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
    handleEdit(id){
      api.getById(id).then(response => {
        if(response.code === 20000){
          this.edit.formData = response.data
          this.edit.oldImageUrl = response.data.imageUrl
        }
      })
      this.edit.title = '编辑'
      this.edit.visible = true
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
      this.edit.formData = {imageUrl:null}
      this.edit.visible = false
      this.fetchData()
    },
    openAdd(){
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
