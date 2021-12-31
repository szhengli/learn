import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import changeControl from './modules/changeControl'
import { INCREMENT } from './mutation-type.js'
Vue.use(Vuex)

const store = new Vuex.Store({
	modules: {
	  changeControl
	},
	getters,
	state:{
		count: 0,
		long: 100,
		river:100,
	},
	mutations: {
		[INCREMENT](state, payload){
			state.count +=state.count + payload.number + payload.multiply
			},
		trippleLong(state, payload){
			state.long = state.long * 3* payload.number
		}	
		},
	getters:{
		doubleCount: state=>{
			return state.count * 2
			},
		doubleArea: (state, getters) => {
			return getters.doubleCount * state.long
			}
		},
	actions: {
		trippleLong ({commit}, payload){
			setTimeout(()=>{commit("trippleLong", payload)},1000)
			
		}
	}
	
})

export default store
