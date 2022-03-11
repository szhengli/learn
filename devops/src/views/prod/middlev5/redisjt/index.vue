<template>
  <div style="padding:30px;">
    <el-select v-model="envredisname" placeholder="请选择Redis集群" @change="choose_method" >
        <el-option
          v-for="item in optionsEnvredisname"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>

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
             align="right"
            @change="info"  >
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
<br>
<iframe :src=urlConnectedClients  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlResponseTime  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlNetworkThroughput  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlConnectionUsage  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlConnectionPerSec  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlCpu  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlMemory  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlMemoryFreg  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlExpiredKey  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlSlowLog  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlIOPS  width="33%" height="250" frameborder="0"></iframe>
<iframe :src=urlTTL  width="33%" height="250" frameborder="0"></iframe>
</div>
</template>

<script>
export default {
  mounted() {
    console.log('@@@@@@@@@@@@@@@@@@@@@@@@')
  },

  methods: {
    use_relative() {
      this.absolue=false
    },
    choose_method(){
      if (this.timemethod == "relative" ){
        this.use_relative()
      }
    },
      info(){
        this.absolue=true
        console.log("---------------")
        console.log(this.absoluterRange[0].getTime())
        console.log(this.urlN)
           console.log("---------------")
      }
  },
  computed:{
    defautl_range(){
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      return [start, end];
    },

    urlConnectedClients(){
      var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=48"
      }
      return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=48"
    },

    urlResponseTime(){
      var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=34"
      }
      return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=34"
    },

    urlNetworkThroughput(){
      var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=43"
      }
      return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=43"
    },

    urlCpu(){
      var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=41"
      }
      return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=41"
    },
    urlMemory(){
      var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=30"
      }
      return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=30"
    },

    urlMemoryFreg(){
      var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=32"
      }
      return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=32"
    },
    urlConnectionUsage(){
      var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=33"
      }
      return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=33"
    },

    urlConnectionPerSec(){
      var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
      if (this.absolue){
        let start = this.absoluterRange[0].getTime()
        let end = this.absoluterRange[1].getTime()
        return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=44"
      }
      return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=44"
    },

 urlSlowLog(){
   var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
   if (this.absolue){
     let start = this.absoluterRange[0].getTime()
     let end = this.absoluterRange[1].getTime()
     return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=36"
   }
   return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=36"
 },

 urlExpiredKey(){
   var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
   if (this.absolue){
     let start = this.absoluterRange[0].getTime()
     let end = this.absoluterRange[1].getTime()
     return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=39"
   }
   return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=39"
 },

 urlIOPS(){
   var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
   if (this.absolue){
     let start = this.absoluterRange[0].getTime()
     let end = this.absoluterRange[1].getTime()
     return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=42"
   }
   return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=42"
 },
 
 urlTTL(){
   var urlRoot = this.grafanaUrlRoot + "v3sheng-chan-huan-jing-redis?orgId=1&refresh=30s&var-envredisname=" + this.envredisname
   if (this.absolue){
     let start = this.absoluterRange[0].getTime()
     let end = this.absoluterRange[1].getTime()
     return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&from="+ start +"&to="+ end+ "&panelId=40"
   }
   return  urlRoot + "&var-instance=redis:%2F%2F192.168.3.10:7000&var-node=localhost:9121&var-master_address=All&"+ this.timerange + " &panelId=40"
 },
 
  },
  data() {
    return {
      grafanaUrlRoot: "http://172.19.233.39:3000/d-solo/3oNtmckMk2/",
      envredisname:'v3_passport',
      optionsEnvredisname: [
      { value: 'v3_passport',  label: 'v3_passport' } , { value: 'v3_acts',  label: 'v3_acts' } ,
       { value: 'v3_dwms',  label: 'v3_dwms' },
      { value: 'v3_basic',  label: 'v3_basic' } , { value: 'v3_chms',  label: 'v3_chms' } ,
      { value: 'v3_dcms',  label: 'v3_dcms' } , { value: 'v3_accms',  label: 'v3_accms' },
      { value: 'v3_entry',  label: 'v3_entry' } , { value: 'v3_fms',  label: 'v3_fcms' } ,
      { value: 'v3_fp',  label: 'v3_fp' } , { value: 'v3_ifms',  label: 'v3_ifms' },
      { value: 'v3_jobms',  label: 'v3_jobms' } , { value: 'v3_jxms',  label: 'v3_jxms' } ,
      { value: 'v3_mbms',  label: 'v3_mbms' } , { value: 'v3_mrms',  label: 'v3_mrms' },
      { value: 'v3_oms',  label: 'v3_oms' } , { value: 'v3_osrms',  label: 'v3_osrms' } ,
       { value: 'v3_pays',  label: 'v3_pays' },
      { value: 'v3_posms',  label: 'v3_posms' } , { value: 'v3_salems',  label: 'v3_salems' } ,
      { value: 'v3_scpms',  label: 'v3_scpms' } , { value: 'v3_sms',  label: 'v3_sms' },
      { value: 'v3_stms',  label: 'v3_stms' } , { value: 'v3_urms',  label: 'v3_urms' } ,
      { value: 'v3_wsms',  label: 'v3_wsms' } , { value: 'v3_wxms',  label: 'v3_wxms' },
      { value: 'v3_zkms',  label: 'v3_zkms' } , { value: 'v3_zlscms',  label: 'v3_zlscms' },
              ] ,

        timemethod: "relative",
        optionsTimeSelectMethond: [{
          value: 'relative',
          label: '相对时间选择'
        }, {
          value: 'absolute',
          label: '绝对时间选择'
        }],

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
