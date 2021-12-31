<template>
</template>

<script>
</script>

<style>
</style>
<template>  
	<div align="center">
		</br> </br>

		
		  
		  <el-table 
		    :data="data"
		    style="width: 100%"
		     >
			 <el-table-column
			   prop="id"
			   label="id"
			   width="180"
			   >
			 </el-table-column>
		  	<el-table-column 
		  	  prop="name"
		  	  label="名称"
		  	  width="180"
		  	  >
		  	</el-table-column>
		    <el-table-column
		      prop= "company"
		      label="公司"
		      width="180">
		    </el-table-column>
		    <el-table-column
		      prop="status"
		      label="状态"
		      width="180">
			  
			<template slot-scope="scope">
			  <el-tag
				:type="get_status(scope)"
				disable-transitions>{{scope.row.status}}</el-tag>
			</template>
		    </el-table-column>
			
		      <el-table-column
		        align="right">
				
		        <template slot="header" slot-scope="scope">
		          操作
		        </template>
				
		        <template slot-scope="scope">
					
					<el-button  v-if="scope.row.operator"
					  size="mini"
					  type="danger"
					  @click="handleApprove(scope.$index, scope.row)">批准</el-button>
					  
					<el-button
					  size="mini"
					  type="primary"
					  @click="showAprrove(scope.row.record)">审批信息</el-button>
				     	
		        </template>
		      </el-table-column>
		  </el-table>
		  
		<el-pagination
		  background
		  @current-change="change_page"
		  layout="prev, pager, next"
		  :total="total">
		</el-pagination>

		<div id="reports">
			
		</div>


  
  </div>
</template>

<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>

<script>
  import Mdialog from './components/Mdialog.vue'
  import Vue from 'vue'
export default {
		components:{
			Mdialog
		},
	    methods: {
			showAprrove(record){
				let body = document.getElementById("reports")
				let report = document.createElement('div')
				report.setAttribute('id', 'report')
				body.appendChild(report)
				new Vue({
					el: '#report',
					render: (h)=>{
								return h(
										Mdialog,
										{
											props: {
												record: record
											}
										}
								)
								}
				})
			
			},
			reload(){
				var self=this
				this.axios.get('/api/work/computer_list/', {
					branch: '20210831',
					lastName: 'Flintstone'
				  })
				  .then(function (response) {
					self.total = response.data.length	
					
					self.datas= response.data;		
					
					self.data = self.datas.slice(0, 10)
				//	console.log(self.data)
				//	console.log(self.options);
					
				  })
				  .catch(function (error) {
					console.log(error);
				  });
				  
			},

		  change_page(id){
			 console.log(id) 
			 this.data = this.datas.slice((id-1)*10, id*10)
			// console.log(this.data)
			 
		  },
		  
		  handleDetail(index, row) {
			console.log(index, row);
		  },
		  handleApprove(index, row) {
			let it = this
			this.axios.post('/api/work/computer_approve/', {
				id: row.id
			  })
			  .then(function (response) {
				it.reload()
			//	console.log(response.data);
				
			  })
			  .catch(function (error) {
				console.log(error);
			  });
			  
			  
			console.log(index, row);
		  }
		 
	    },
	
	computed:{
		get_status: function(){
			return function(scope){
				let style = ""
				switch (scope.row.status) {
					case "pending":
						style = "warning"
						break;
					case "approved":
						style = "danger"
						break ;
					case "closed":
						style = "success"
						break ;
					default:
						style = "info"
				}
	
				return style
			}
		}
	}	,
	
	 mounted(){   
				this.reload()
				},
	 data(){
		 return {
			 show: 'show me yyy',
			 datas:[],
			 record:[],
			 pageid:0,
			 data:[],
			 total:0,
			 dialogTableVisible: false,
			 options: [],
			 branch: '',
			 show: false
		 }
	 }
 
 }
 
 
</script>

