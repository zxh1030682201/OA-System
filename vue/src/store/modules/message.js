import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async msg_queryAll({commit},options){
    return (await services.msg_queryAll(options)).data
  },
  async msg_queryById({commit},options){
    return (await services.msg_queryById(options)).data
  },
  async msg_queryByS({commit},options){
    return (await services.msg_queryByS(options)).data
  },
  async msg_queryByRU({commit},options){
    return (await services.msg_queryByRU(options)).data
  },
  async msg_queryByRR({commit},options){
    return (await services.msg_queryByRR(options)).data
  },
  async msg_add({commit},options){
    return (await services.msg_add(options))
  },
  async msg_update({commit},options){
    return (await services.msg_update(options))
  },
  async msg_delete({commit},options){
    return (await services.msg_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}