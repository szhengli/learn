<template>
	<div>
		<el-dialog title="审批详情" :visible.sync="dialogTableVisible" width="20%" @close="quit">
			  <el-timeline>
				<el-timeline-item
				  v-for="(activity, index) in record"
				  :key="index"
				  :timestamp="activity.timestamp">
				  {{activity.content}}
				</el-timeline-item>
			  </el-timeline>
		  <div slot="footer" class="dialog-footer">
			
			<el-button type="primary" @click="quit">确 定</el-button>
		  </div>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		methods: {
			quit(){
				this.dialogTableVisible = false
				let body = document.getElementById("reports")
				body.childNodes.forEach(child=>body.removeChild(child))
			}
		},
		mounted(){
			console.log(this.record)
			
		},
		props:{
			record:{}, 
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
