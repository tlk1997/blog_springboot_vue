import request from '@/utils/request'

export default{
    getList(query , current=1 ,size=20){
        return request({
            url:`/system/user/search`,
            method:'post',
            data:{
                ...query,
                current,
                size
            }
        })
    },
    add(data){
        return request({
            url: `/system/user`,
            method: 'post',
            data
        })
    },
    getById(id){
        return request({
            url : `/system/user/${id}`,
            method: 'get'
        })
    },
    update(data){
        return request({
            url : `/system/user`,
            method: 'put',
            data,
        })
    },
    deleteById(id){
        return request({
            url : `/system/user/${id}`,
            method : 'delete'
        })
    },
    getRoleIdsByUserid(id){
        return request({
            url:`/system/user/${id}/role/ids`,
            method:'get'
        })
    },
    saveUserRole(id , roleIds){
        return request({
            url: `/system/user/${id}/role/save`,
            method:'post',
            data:roleIds
        })
    },
    updatePassword(data){
        return request({
            url:`/system/user/password`,
            method:'put',
            data
        })
    }
}