import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async admin_queryAll({commit},options){
    return (await services.admin_queryAll(options)).data
  },
  async admin_add({commit},options){
    return (await services.admin_add(options))
  },
  async admin_update({commit},options){
    return (await services.admin_update(options))
  },
  async admin_delete({commit},options){
    return (await services.admin_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}