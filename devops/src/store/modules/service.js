import {get_serviceList} from "@/api/service.js"

const state = {
  data: [],
}

const mutations = {
  set_data: (state, data) => {
    state.data = data
  },

}

const actions = {
  reload_data({ commit }) {
    get_serviceList().then(response=>{
      commit('set_data', response.data)
      console.log("----set data----")
    })

  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
