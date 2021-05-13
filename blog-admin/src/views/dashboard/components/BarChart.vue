<template>
  <div ref="main" :class="className" :style="{height:height,width:width}"></div>
</template>

<script>
import * as echarts from 'echarts'
import resize from './mixins/resize'

export default {
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
        xAxisData:{
            type:Array,
            default:() =>['2021-01','2021-02','2021-03','2021-04','2021-05','2021-06']
        },
        seriesData:{
            type:Array,
            default:() => [10,52,200,334,390,330]
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
                    text:'近6个月发布文章数',
                    left:'center'
                },
                tooltip:{
                    trigger:'axis',
                    axisPointer:{
                        type: 'shadow'
                    }
                },
                grid:{
                    left:'3%',
                    right:'4%',
                    bottom:'3%',
                    containLabel:true
                },
                xAxis:[
                    {
                        type: 'category',
                        data: this.xAxisData,
                        axisTick:{
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis:[
                    {
                        type: 'value'
                    }
                ],
                series:[
                    {
                        name:'发布数',
                        type:'bar',
                        barWidth:'60%',
                        data:this.seriesData, 
                    }
                ]
            })
        }
    }
}
</script>