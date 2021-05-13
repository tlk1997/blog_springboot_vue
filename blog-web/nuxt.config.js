export default {
  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'tlk-blog',
    htmlAttrs: {
      lang: 'en'
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: 'tlk-blog' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/tlk.ico' }
    ]
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [
    //elementui各组件样式
    'element-ui/lib/theme-chalk/index.css',
    //自定义主题样式
    '~/assets/theme/index.css',
    //自适应隐藏显示样式
    'element-ui/lib/theme-chalk/display.css',
    //项目自定义全局样式
    '~/assets/css/global.css',
    //加上mavon-editor组件要使用的样式
    'mavon-editor/dist/css/index.css'

  ],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    '~/plugins/element-ui.js',
    '~/plugins/interceptor.js',
    '~/api/article.js',
    '~/api/common',
    '~/api/question',
    '~/api/user',
    
    {src: '@/plugins/mavon-editor.js' , ssr:false},

  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
  ],

  env: {
    authURL: process.env.NODE_ENV === 'dev' ? '//localhost:7000' : '// xxx' // xxx为生产环境
  },
  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    '@nuxtjs/axios',
    'cookie-universal-nuxt',
  ],
  axios: {
    proxy: true,
    prefix: '/api'
  },
  proxy: {
    '/api': {
      // target: ' https://mock.mengxuegu.com/mock/60794c10bb30fd30f7169b36/blog-web',
      target: 'http://localhost:8001',
      pathRewrite: {'^/api' : ''},
      changeOrigin:true
    }
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
    //将位于node-modules目录下的element-ui导出
    transpile: [/'^element-ui'/],
    extend (config , ctx){

    }
  }
}
