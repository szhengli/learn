<template>
<div>

  <h2>  hello {{ $route.params.id}} </h2>
<br>
  <router-view></router-view>
</div>

</template>

<script>
export default {
  name: "Demo"
}
</script>

<style scoped>

</style>
