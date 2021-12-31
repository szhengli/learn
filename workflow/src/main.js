import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import router from './router.js'
import store from './store'



Vue.use(ElementUI)
Vue.use(VueAxios, axios)




new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})


