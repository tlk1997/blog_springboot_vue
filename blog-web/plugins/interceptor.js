export default({store, route, redirect, $axios}) => {

    $axios.onRequest(config => {
        // console.log("请求拦截器")
        const accessToken = store.state.accessToken
        if(accessToken) {
            // Authorization: Bearer token
            config.headers.Authorization = 'Bearer ' + accessToken
        }
        // 请求头添加token
        return config
    })

    $axios.onResponse(response => {
        // console.log('响应拦截器：', response)
        // if(!store.state.accessToken) {
        //     sendRefreshRequest(route, store, redirect)
        // }
        return response
    })

    $axios.onError(error => {
        // console.log('响应异常：', error.response.status)
        if(error.response.status !== 401 ) {
            return Promise.reject(error)
        }
        // 401 发送刷新获取新令牌
        sendRefreshRequest(route, store, redirect)
        return  Promise.reject('令牌过期，重新登录')
    })

}

let isLock = true // 防止并发重复发送刷新令牌请求， true 还未发送，false正在请求刷新
 
const sendRefreshRequest = (route, store, redirect) => {
   
    if(isLock && store.state.refreshToken) {
        isLock = false
        // 发送请求到认证客户端，通过刷新令牌获取新令牌
        redirect(`${process.env.authURL}/refresh?redirectURL=${redirectURL(route)}`)
    }else {
        isLock = true
        // 没有刷新令牌，跳转到登录页
        // 重置用户状态
        // console.log('跳转到登录页')
        store.commit('RESET_USER_STATE')
        // 跳转到登录页
        
        redirect(`${process.env.authURL}?redirectURL=${redirectURL(route)}`)
    }
}  

// 获取重定向地址
const redirectURL = (route) => {
    // 客户端
    if(process.client) {
        return window.location.href
    }
    // 服务端 process.env._AXIOS_BASE_URL_  http://localhost:3000/api   http://blog.mengxuegu.com/api
    return process.env._AXIOS_BASE_URL_.replace('api', '') + route.path
}