<template>
  <div >

  <el-select v-model="namespace" placeholder="请先选择环境" v-on:change="choose_namespace">
    <el-option
      v-for="item in namespaces"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
  <el-select v-model="pod" placeholder="请选择pod" v-on:change="choose_pod">
    <el-option
      v-for="item in pods"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>

    <div id="logs" style=" overflow-y: auto; background:#3f3f3f; color:#009000; font-size: 12px ; width: 1500px; height: 700px;">

    </div>

  </div>
</template>

<script>
import { podlog } from '@/api/podlog'
import { get_all_namespaces } from '@/api/kube'
import { get_podnames } from '@/api/kube'
  export default {
    destroyed(){
      console.log("fuck !!!!!!!!!!!!!!!!!!!!!!!!!!!")
      this.pod=""
      this.show= null
      this.first= true
    },
    methods:{
      choose_namespace(){
        let it = this
        sessionStorage.setItem('selected_namespace', it.namespace)
        get_podnames({"namespace": this.namespace}).then(function (response) {
          it.pods = response.data.pods
          var logs = document.getElementById("logs")
          console.log("#################")
          console.log(it.first)
          console.log("#################")
          if (it.first){
           it.choose_pod()
          }else{
            it.pod=""
            it.show= false
            logs.textContent=""
          }
          it.first = false
        })
      },
      choose_pod(){
          		let it = this
               it.show= false
               logs.textContent=""
               sessionStorage.setItem('selected_pod', it.pod)
          		this.podName = this.pod
          		this.namespace = this.namespace
          		this.randID = Math.random().toString(36).slice(-6);
          		podlog({namespace: it.namespace , podName: it.podName, randID:it.randID})
          		.then(response => {
          			console.log(response.data)
          		})
          		let randSid = Math.random().toString(36).slice(-6);
          		let url = "ws://192.168.2.221:5678/" + this.namespace +"_" + this.podName + "_"+ this.randID  +"_" + randSid
          		let ws = new WebSocket(url)
              it.show = true
          		ws.onmessage = function(event) {
                if (it.show || it.pod != ""){
                  var entry
                  var logs = document.getElementById("logs")
                  entry =document.createElement('p')
                  entry.textContent= event.data
                  logs.appendChild(entry)
                }
          		}

      }
    },
  	mounted(){
      console.log("happy@@@@@@@@@@@@@@@@@@@@@@@@@@")
      let it = this
      get_all_namespaces().then(function (response) {
        it.namespaces = response.data.namespaces
        console.log(it.namespaces)
  	})

    let selected_namespace = sessionStorage.getItem("selected_namespace")

    let selected_pod = sessionStorage.getItem("selected_pod")
    if (selected_namespace) {
      it.namespace = selected_namespace
       this.choose_namespace()
    }
    if (selected_pod) {
      it.pod = selected_pod
    }


    },

  	data(){
  		return {
        first: true,
        namespace: "",
        namespaces:[],
        pod: "",
        pods: [],
        msg:  '',
        namespace: '',
        podName: '',
        randID:'',
        show: false
  		}
  	}

  }


</script>

<style scoped>
.line{
  text-align: center;
}
</style>
