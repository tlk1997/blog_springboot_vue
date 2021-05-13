<template>
  <div ref="main" :class="className" :style="{height:height,width:width}"></div>
</template>

<script>
import * as echarts from 'echarts'
import resize from './mixins/resize'
// require('echarts/theme/macarons')

export default{
    mixins: [resize],
    props:{
        className:{
            type:String,
            default:'chart'
        },
        width:{
            type:String,
            default:'500px'
        },
        height:{
            type:String,
            default:'400px'
        },
        legendData:{
            type:Array,
            default:() =>['前端','Java','IOS','大数据','AI','区块链']
        },
        seriesData:{
            type:Array,
            default:() => [
                {value:335,name:'前端'},
                {value:315,name:'Java'},
                {value:365,name:'IOS'},
                {value:435,name:'大数据'},
                {value:125,name:'AI'},
                {value:275,name:'区块链'},
            ]
        }
    },
    data(){
        return{
            chart:null
        }
    },
    mounted(){
        this.$nextTick(() => {
            this.initChart()
        })
    },
    beforeDestroy(){
        if (!this.chart){
            return
        }
        this.chart.dispose()
        this.chart = null
    },
    methods:{
        initChart(){
            this.chart = echarts.init(this.$refs.main)
            this.chart.setOption({
                title:{
                    text:'各技术频道文章统计',
                    left:'center'
                },
                tooltip:{
                    trigger:'item',
                    formmatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend:{
                    orient: 'vertical',
                    left:'left',
                    data: this.legendData
                },
                series:[
                    {
                        name:'统计内容',
                        type:'pie',
                        radius:'55%',
                        center:['50%' , '50%'],
                        data:this.seriesData,
                        emphasis: {
                            itemStyle:{
                                shadowBlur:10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0,0,0,0.5)'
                            }
                        }
                    }
                ]
            })
        }
    }
}
</script>