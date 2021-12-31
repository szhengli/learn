<template>

	<div >





<el-form ref="form" :model="form" label-width="160px">

      <el-form-item label="服务名" >
        <el-input v-model="form.name" ></el-input>
      </el-form-item>

       <el-form-item label="前后端" >
          <el-radio-group v-model="form.backend" >
            <el-radio label="backend">后端</el-radio>
            <el-radio label="frontend">前端</el-radio>
          </el-radio-group>

       </el-form-item>

   <el-form-item label="类别" v-if="form.backend=='backend'" >
      <el-radio-group v-model="form.category">
        <el-radio label="ft">前台</el-radio>
        <el-radio label="zt">中台</el-radio>
        <el-radio label="jichu">基础</el-radio>
      </el-radio-group>
    </el-form-item>

        <el-form-item label="中间件" v-if="form.backend=='backend'">
          <el-checkbox-group v-model="form.middlewares">
            <el-checkbox label="redis"> redis集群</el-checkbox>
            <el-checkbox label="mqtopic">MQ Topic</el-checkbox>
            <el-checkbox label="database">数据库</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

    <el-form-item label="域名使用" v-if="form.backend=='backend'" >
        <el-radio-group v-model="form.useDomain" >
          <el-radio label="1">是</el-radio>
          <el-radio label="0">否</el-radio>
        </el-radio-group>
    </el-form-item>


    <el-form-item label="域名" v-if="form.backend=='frontend' || (form.backend=='backend' && form.useDomain == '1')">
      <el-input v-model="form.testDnsName" ></el-input>
    </el-form-item>

    <el-form-item label="反向代理" v-if="form.backend=='backend' && form.useDomain == '1' ">
      <el-radio-group v-model="form.proxyTo">
        <el-radio label="basic"> 前台网关</el-radio>
        <el-radio label="entry">中台网关</el-radio>
        <el-radio label="fpapi">基础网关</el-radio>
        <el-radio label="api">API网关</el-radio>
      </el-radio-group>

    </el-form-item>






  </el-form>


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
  methods:{
    test(){
      console.log(this.$parent.$data)
    }
  },
data() {
      return {

        form: {
          name: '',
          backend: "backend",
          category: '',
          middlewares: [],
          testDnsName : '',
          useDomain: "1",
          proxyTo:'',


        }
      }
    },

 }


</script>
