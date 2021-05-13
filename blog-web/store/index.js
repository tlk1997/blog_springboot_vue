const state = () => ({
    userInfo : null, //用户信息对象
    accessToken : null, //访问令牌字符串
    refreshToken: null, // 刷新令牌字符串
})

const mutations = {

    //刷新页面后，重新更新状态
    UPDATE_ALL_STATE(state , data){
        state.userInfo = data.userInfo
        state.accessToken = data.accessToken
        state.refreshToken = data.refreshToken
    },
    //重置用户状态
    RESET_USER_STATE(state){
        //状态置空
        state.userInfo = null
        state.accessToken = null
        state.refreshToken = null
    }
}
// 定义行为
const actions = {
    //nuxt提供，每次发送请求都会先调用此方法，并且该方法第二个参数是context，第一个参数是store
    nuxtServerInit({commit} , {app}){
        const data = {}
        data.userInfo = app.$cookies.get('userInfo')
        // data.accessToken = app.$cookies.get(Key.accessTokenKey)
        // data.refreshToken = app.$cookies.get(Key.refreshTokenKey)
        // data.redirectURL = app.$cookies.get(Key.redirectURLKey)
        //更新状态值
        commit('UPDATE_ALL_STATE',data)

    },
    // 跳转登录页
    LoginPage({commit}) {
        //重置用户状态
        commit('RESET_USER_STATE')
        //跳转登录页，redirectURL 引发跳转到登录页的URL
        window.location.href = 
        `${process.env.authURL}?redirectURL=${window.location.href}`
    },

    UserLogout({commit}){
        commit('RESET_USER_STATE')
        window.location.href = 
        `${process.env.authURL}/logout`
    }
    
}

export default {
    state,
    mutations,
    actions
}