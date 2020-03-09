import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async team_queryAll({commit},options){
    return (await services.team_queryAll(options)).data
  },
  async team_queryById({commit},options){
    return (await services.team_queryById(options)).data
  },
  async team_queryByLeader({commit},options){
    return (await services.team_queryByLeader(options)).data
  },
  async team_add({commit},options){
    return (await services.team_add(options))
  },
  async team_update({commit},options){
    return (await services.team_update(options))
  },
  async team_delete({commit},options){
    return (await services.team_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}