<template>
	<div>
		<el-dialog title="项目申请" :visible.sync="dialogTableVisible" width="40%" @close="quit">


    <ServiceForm ref="serviceForm"></ServiceForm>


  <div align="middle">
  	<el-button type="primary" @click="submit">提交</el-button>
    <el-button type="primary" @click="quit">取消</el-button>


  </div>


		</el-dialog>
	</div>
</template>

<script>
  import ServiceForm from "./ServiceForm.vue"
  import {service_add} from "@/api/service.js"
  import { mapActions } from 'vuex'

	export default {
    components: {
      ServiceForm
    },
		methods: {

      ...mapActions('service',['reload_data']) ,

      submit(){
        service_add(this.$refs.serviceForm.form)
        this.$emit("Ereload")
       // this.reload_data("reload_data")
        this.dialogTableVisible = false
        this.$parent.showDialog=false
        /*
        let body = document.getElementById("form")
        body.childNodes.forEach(child=>body.removeChild(child))
        */
      },
			quit(){
       // console.log(this.$refs.serviceForm.form)
				this.dialogTableVisible = false
         this.$parent.showDialog=false
        /*
				let body = document.getElementById("form")
				body.childNodes.forEach(child=>body.removeChild(child))
        */
			}
		},
		mounted(){
			console.log(this.record)
      console.log(this.reload)

		},
		props:{
			record:{},
      reload:'',
		},
		data (){
			return {
				dialogTableVisible: true
			}
		}

	}



</script>

<style>
</style>
