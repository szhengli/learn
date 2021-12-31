<template>
  <div>
 <el-button type="primary" size="mini" @click="showForm" icon="el-icon-plus">新建申请</el-button>
 <br>
  <br>
   <br>
    <br>
     <br>
    <el-table border
      :data="servicedata"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="ID"
        width="50">
      </el-table-column>

      <el-table-column
        prop="name"
        label="项目名"
        width="100">
      </el-table-column>



      <el-table-column
        prop="applyTime"
        label="申请时间"
        width="180">
      </el-table-column>

      <el-table-column
        prop="applicant"
        label="申请人"
        width="80">
      </el-table-column>

      <el-table-column
        prop="backend"
        label="前后端"
        width="100">

      </el-table-column>

      <el-table-column
        prop="category"
        label="类别"
        width="50">
      </el-table-column>

      <el-table-column
        prop="testDnsName"
        label="测试域名"
        width="180">
      </el-table-column>

      <el-table-column
        prop="proxyTo"
        label="反向代理"
        width="100">
      </el-table-column>

      <el-table-column
        prop="middlewares"
        label="中间件"
        width="220">

        <template slot-scope="scope">
          <el-tag v-for="item in scope.row.middlewares"
            type='style="info"'
            disable-transitions> {{item}}
          </el-tag>
        </template>

      </el-table-column>

      <el-table-column
        prop="status"
        label="状态" width="200">

			<template slot-scope="scope">
			  <el-tag
          :type="get_status(scope)"
          disable-transitions>{{scope.row.status}}
        </el-tag>
			</template>

      </el-table-column>

      <el-table-column
        align="right" >

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
      					  @click="showAprrove(scope.row.record)">处理详情</el-button>

        </template>
      </el-table-column>

    </el-table>

    <div id='form' > </div>
      <Mdialog  @Ereload="test" v-if="showDialog"></Mdialog>
    </div>

</template>

<script>
  import Mdialog from  "./components/Mdialog.vue"
  import Vue from 'vue'
  import { mapActions } from 'vuex'
  import { mapState } from 'vuex'
  import {service_approve} from "@/api/service.js"


  export default {
    mounted(){
      this.reload()
    },
    methods:{
      test(){
        setTimeout(()=>{
          this.reload()
        },500)

        console.log("-----------------can the call from soon-------------")
      },
      ...mapActions('service',['reload_data']) ,

      reload(){
        this.reload_data("reload_data")
        console.log(this.servicedata)
      },
      handleApprove(index, row) {
      			let it = this
      			service_approve({"id":row.id})
      			  .then(function (response) {

              it.reload()


      			  })
      			  .catch(function (error) {
      				console.log(error);
      			  });


      			console.log(index, row);
      },
      showForm(){
        this.showDialog=true
        /**
        let form =  document.getElementById('form')
        let child = document.createElement('div')
        child.setAttribute('id', 'reports')
        form.appendChild(child)

        new Vue({
          el: '#reports ',
          render: (h) => {
            var self = this
            return h(
              Mdialog,{on:{
                xxx : function(event){
                  console.log("---xxx--")
                }
              }}
            )
          },


        })

        **/
      },

    },
  computed:{
    ...mapState('service',{
                            servicedata: state=> state.data,
                      }) ,
		get_status: function(){
			return function(scope){
				let style = ""
				switch (scope.row.status) {
					case "approved":
						style = "warning"
						break;
					case "failed":
						style = "danger"
						break ;
					case "success":
						style = "success"
						break ;
          case "pending":
          style = ""
          break ;
					default:
						style = "info"
				}

				return style
			}
		}
	}	,
    components: {
        Mdialog
      },

    data(){
      return {
        city: 'suzhou',
        serviceList: [],
        showDialog: false

      }
    }
  }
</script>

<style>
</style>
