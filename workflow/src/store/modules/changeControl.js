
const state = {
  sidebar: {
    requester: "james"
  },
  device: 'desktop'
}

const mutations = {
  changeSidebar: (state, payload) => {
    state.sidebar.requester = payload.reuqester
  },

  changeDevice: (state, device) => {
    state.device = device
  }
}

const actions = {

  changeSidebar({ commit }, payload) {
    commit('changeSidebar', payload)
  },
  changeDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
