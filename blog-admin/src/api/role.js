import request from '@/utils/request'

export default {
    getList(query , current = 1 , size = 20){
        return request({
            url:`/system/role/search`,
            method:'post',
            data:{...query ,current ,size}
        })
    },
    add(data){
        return request({
            url: `/system/role`,
            method: 'post',
            data
        })
    },
    getById(id){
        return request({
            url : `/system/role/${id}`,
            method: 'get'
        })
    },
    update(data){
        return request({
            url : `/system/role`,
            method: 'put',
            data,
        })
    },
    deleteById(id){
        return request({
            url : `/system/role/${id}`,
            method : 'delete'
        })
    },
    getMenuIdsByRoleId(id){
        return request({
            url : `/system/role/${id}/menu/ids`,
            method: 'get'
        })
    },
    saveRoleMenu(id , menuIds){
        return request({
            url :  `/system/role/${id}/menu/save`,
            method : 'post',
            data:menuIds
        })
    }
}