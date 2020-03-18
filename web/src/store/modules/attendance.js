import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async atd_queryAll({commit},options){
    return (await services.atd_queryAll(options)).data
  },
  async atd_queryById({commit},options){
    return (await services.atd_queryById(options)).data
  },
  async atd_queryByUser({commit},options){
    return (await services.atd_queryByUser(options)).data
  },
  async atd_queryByUD({commit},options){
    return (await services.atd_queryByUD(options)).data
  },
  async atd_add({commit},options){
    return (await services.atd_add(options))
  },
  async atd_update({commit},options){
    return (await services.atd_update(options))
  },
  async atd_delete({commit},options){
    return (await services.atd_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}