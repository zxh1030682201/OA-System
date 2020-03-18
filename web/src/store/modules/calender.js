import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async cld_queryAll({commit},options){
    return (await services.cld_queryAll(options)).data
  },
  async cld_queryById({commit},options){
    return (await services.cld_queryById(options)).data
  },
  async cld_queryByUser({commit},options){
    return (await services.cld_queryByUser(options)).data
  },
  async cld_queryByUD({commit},options){
    return (await services.cld_queryByUD(options)).data
  },
  async cld_add({commit},options){
    return (await services.cld_add(options))
  },
  async cld_update({commit},options){
    return (await services.cld_update(options))
  },
  async cld_delete({commit},options){
    return (await services.cld_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}