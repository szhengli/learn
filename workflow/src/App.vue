<template>

	<div>

	<button @click="runJenkins" > test</button>


    <div id="content" style=" overflow: auto; height: 700px;">

    <pre v-html="jenkinsData"> </pre>
      <img :src="imageUrl" v-if="showSpinning">
    </div>


	</div>
</template>

<script>

import Vue from 'vue'

import Son from "./Son.vue"

import { mapState } from 'vuex'
import { mapMutations } from 'vuex'
import { mapActions } from 'vuex'
import { createNamespacedHelpers } from 'vuex'

export default {
  data(){
    return {
      jenkinsData:"",
      imageUrl: require("./assets/spinner.gif"),
      showSpinning: false,
    }
  },
  methods:{



    runJob(url , start){
      let it = this
      console.log(url)
      let content = document.getElementById("content")
      this.axios({  url:url,
                    method: "post",
                    data:{start:start},
                    auth: {
                      username: "zhengli",
                      password:"115bd0cf2201e09d371cf236d7380d24bd"
                    },
      }).then(res=>{
        content.scrollTop=content.scrollHeight
        it.jenkinsData = res.data
        it.showSpinning = true


       content.scrollTop=content.scrollHeight
        //content.scrollIntoView()
        if (res.headers["x-more-data"] == "true") {
          let start = res.headers["x-text-size"]
           setTimeout(x=>{
             it.runJob(url, start, it)
           }, 500)
        } else {
          it.showSpinning = false
        }
      }).catch(err=>{
        if (400 < err.response.statusCode < 499 ){
          setTimeout(x=>{
            it.runJob(url, start, it)
          }, 500)
        }
      })


    },

    runJenkins(){
      let it = this
      let  url = "http://localhost:8081/jenkins/build";
      let jenkinsUrl = "https://jenkins.cnzhonglunnet.com/view/JAVA-V5-PRE/job/prev5-zl-omsv5/"



      this.axios({
        url: url
      }).then(res=>{
        let id = res.data
        console.log(res)
        let jobUrl = jenkinsUrl + id + "/logText/progressiveHtml"

        setTimeout(x=>{
            it.runJob(jobUrl, 0)
        }, 1000)

      })

    },
  }

}

</script>

<style>
@import "assets/style.css";



</style>
