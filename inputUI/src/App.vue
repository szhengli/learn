<template>
</template>

<script>
</script>

<style>
</style>
<template>
	<div align="center">
		</br> </br>
						分支发版情况查询  </br> </br>
			  <el-select v-model="branch" placeholder="分支选择" @change="show_branch" >
			    <el-option
			      v-for="item in options"
			      :key="item.value"
			      :label="item.label"
			      :value="item.value">
			    </el-option>
			  </el-select>

			  </el-aside>


		  <el-table v-if="this.show"
		    :data="datas"
		    style="width: 100%"
		    :row-class-name="tableRowClassName" >
		  	<el-table-column
		  	  prop="service"
		  	  label="服务"
		  	  width="180"
		  	  >
		  	</el-table-column>
		    <el-table-column
		      prop= "requested"
		      label="发布单提交"
		      width="180">
		    </el-table-column>
		    <el-table-column
		      prop="block"
		      label="封板"
		      width="180">
		    </el-table-column>
		  	<el-table-column
		  	  prop="released"
		  	  label="已发布"
		  	  >
		  	</el-table-column>
			<el-table-column
			  prop="created"
			  label="创建时间"
			  >
			</el-table-column>
			<el-table-column
			  prop="deleted"
			  label="删除时间"
			  >
			</el-table-column>

		  </el-table>







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

export default {

	    methods: {
	      tableRowClassName({row, rowIndex}) {
			if (this.datas[rowIndex].released == "yes" ){
					return  'success-row';
				} else if (this.datas[rowIndex].released == "yes"){
					 return 'warning-row';
				}
				return '';
			},

		 show_branch(){
			 var self=this
			 console.log(this.branch)
			 sessionStorage.setItem('selectedRelease', this.branch)
			 let selectedRelease = sessionStorage.getItem("selectedRelease")
			 console.log(sessionStorage.getItem("selectedRelease") + "  ////////")

			 this.axios.post('/api/svn/get_release_records/', {
			     branch: selectedRelease,
			     lastName: 'Flintstone'
			   })
			   .then(function (response) {
			 	self.datas = response.data;
			 //	console.log(self.datas);

			   })
			   .catch(function (error) {
			     console.log(error);
			   });
			   self.show = true
		 }

	    },


	 mounted(){
				this.axios({ method: 'post',
				             url:"http://localhost:8080/demo/hello3",
				             headers: {'city' : 'suzhou'},
						   }).then(function(response){
					console.log(response.data)
				}).catch(function(err){
					console.log(err)
				})

		           var self=this
					this.axios.get('/api/svn/get_all_branches/', {
						branch: '20210831',
						lastName: 'Flintstone'
					  })
					  .then(function (response) {

						self.options= response.data;
					//	console.log(self.options);

					  })
					  .catch(function (error) {
						console.log(error);
					  });


					  let selectedRelease = sessionStorage.getItem("selectedRelease")
					  if (selectedRelease){
						  this.branch = selectedRelease
						  console.log("//////")
						  console.log(selectedRelease)
						  this.show_branch()
					  }else{
						  console.log("!!!!!!!!!!!!")
						  //console.log(selectedRelease)
					  }


				},
	 data(){
		 return {
			 show: 'show me yyy',
			 datas:[],
			 options: [],
			 branch: '',
			 show: false
		 }
	 }

 }


</script>

