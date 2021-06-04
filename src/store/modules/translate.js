import { getLangList } from '@/api/translate/translate'

const state = {
  languageList: []
}

const mutations = {
  SET_LANGUAGE_LIST: (state, list) => {
    state.languageList = list
  }
}

const actions = {
  async getLangList({ commit, state }) {
    if (state.languageList && state.languageList.length) {
      return state.languageList
    } else {
      const { data } = await getLangList()
      if (data) {
        const list = []
        data.map((val) => {
          list.push({
            label: val,
            value: val
          })
        })
        commit('SET_LANGUAGE_LIST', list)
        return state.languageList
      }
    }
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
