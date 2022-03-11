<template>

	<div>

    <h1> hello app </h1>
    <el-button @click="demo"> demo</el-button> <br>
    <el-button @click="redirects"> redirect</el-button>
    <p>
    <router-link :to="{ name: 'hello'}">  go hello </router-link>

    <router-link to="/about"> goto about</router-link>
    </p>

    <br>
    ------------------------router View Default --------------------------- <br>
    <router-view/>

    ------------------------router View south ---------------------------  <br>
    <router-view name="south"/>  <br>
    ------------------------router View north --------------------------- <br>
    <router-view name="north"/> <br>
	</div>
</template>

<script>




export default {
  data(){
    return {

    }
  },
  methods:{

    redirects(){
      this.$router.push("/home")
    },

  demo() {
    this.$router.push("/hello/detail")
  }


  }

}

</script>

<style>
@import "assets/style.css";



</style>
