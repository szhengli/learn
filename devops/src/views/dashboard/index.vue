<template>
  <div class="dashboard-container">







</br>
<iframe src="http://192.168.1.125:3000/d-solo/_aouuJPWe/redis-sim?orgId=1&refresh=30s&from=1637729458747&to=1637740258747&var-envredisname=sim_accms&var-instance=redis:%2F%2F192.168.3.12:7020&theme=light&panelId=11" width="450" height="200" frameborder="0"></iframe>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
