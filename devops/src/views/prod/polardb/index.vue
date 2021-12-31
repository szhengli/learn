<template>
  <div style="padding:30px;">
    <el-select v-model="timemethod" placeholder="请选择" @change="choose_method" >
        <el-option
          v-for="item in optionsTimeSelectMethond"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
         <span v-show="timemethod=='absolute'" >
           <el-date-picker
             v-model="absoluterRange"
             type="datetimerange"
             :picker-options="pickerOptions"
             range-separator="至"
             start-placeholder="开始日期"
             end-placeholder="结束日期"
             align="right" @change="info"  >
           </el-date-picker>
         </span>

    <span v-show="timemethod=='relative'">
      <el-select   v-model="timerange" placeholder="请选择" @change="use_relative" >
          <el-option
            v-for="item in optionsRelativeTime"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
     </span>

<iframe :src=urlQps width="100%" height="300" frameborder="0"></iframe>
<iframe :src=urlSlowQueriesPs width="100%" height="300" frameborder="0"></iframe>
<iframe :src=urlSysload width="100%" height="300" frameborder="0"></iframe>
  </div>
</template>

<script>

export default {
  methods:{
      use_relative(){
        this.absolue=false
      },
      choose_method(){
        if (this.timemethod == "relative" ){
          this.use_relative()
        }
      },
      info(){
        this.absolue=true
        /**
        console.log("---------------")
        console.log(this.absoluterRange[0].getTime())
        console.log(this.urlN)
           console.log("---------------")
           **/
      }
  },
  computed:{
    defautl_range(){
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      return [start, end];
    },
    urlQps(){
      let url_Qps = this.UrlPanel + "4"
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return url_Qps + "&from=" + start + "&to=" + end
      }
      return  url_Qps + this.timerange
    },
    urlSlowQueriesPs(){
      let slow_queries_ps = this.UrlPanel + "6"
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return slow_queries_ps + "&from="+ start +"&to="+ end
      }
      return  slow_queries_ps+ this.timerange
    },
    urlSysload(){
      let sys_load = this.UrlPanel + "2"
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return sys_load + "&from="+ start +"&to="+ end
      }
      return  sys_load + this.timerange
    },
  },
  data() {
    return {
        timemethod: "relative",
        optionsTimeSelectMethond: [{
          value: 'relative',
          label: '相对时间选择'
        }, {
          value: 'absolute',
          label: '绝对时间选择'
        }],
    UrlPanel: this.GLOBAL.InternalPrometheus + "/Jnop3ppnz/ploardb?orgId=1&theme=light&panelId=" ,
    pickerOptions: {
          shortcuts: [{
            text: '最近两天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 2);
              picker.$emit('pick', [start, end]);
            }
          },{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        absoluterRange: '',
        absolue: false,



      optionsRelativeTime: [{
               value: '&from=now-5m&to=now',
               label: 'Last 5 miniutes'
             }, {
               value: '&from=now-15m&to=now',
               label: 'Last 15 miniutes'
             }, {
               value: '&from=now-30m&to=now',
               label: 'Last 30 miniutes'
             }, {
               value: '&from=now-1h&to=now',
               label: 'Last 1 hour'
             }, {
               value: '&from=now-3h&to=now',
               label: 'Last 3 hours'
             },{
               value: '&from=now-6h&to=now',
               label: 'Last 6 hours'
             },{
               value: '&from=now-12h&to=now',
               label: 'Last 12 hours'
             },{
               value: '&from=now-24h&to=now',
               label: 'Last 24 hours'
             },{
               value: '&from=now-2d&to=now',
               label: 'Last 2 days'
             },{
               value: '&from=now-7d&to=now',
               label: 'Last 7 days'
             },{
               value: '&from=now-30d&to=now',
               label: 'Last 30 days'
             },
             ],
      timerange: '&from=now-3h&to=now',
    }
  }
}
</script>
