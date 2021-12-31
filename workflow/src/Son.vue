<template>
	<div>
	   <div >
	--------------- <br>
	 <el-button @click="demo">  son </el-button>
			   <br>
	-----------------<br>
	   </div>
	   
	</div>
	
</template>

<script>
	import { mapState } from "vuex"
	import { mapGetters } from "vuex"
	export default {
		methods:{
			demo(){
				this.$router.push('/foo')
				this.$emit("callshow", "msg from son")
			}
		},
		props: [ 'place' ],
		computed: {
			echo(){
				return "yyyy"
				},
			...mapGetters([
					'doubleCount',
					'doubleArea'
				])	,
			...mapState({
				count: state => state.count,
				trycount: 'count',
				countPlus(state){
					return state.count + this.mount
				}
				})
		}
				,
		data() {
			return {
				mount: 4,
				school: "beijing from son"
			}
		}  
  
  }

</script>

<style>
</style>
