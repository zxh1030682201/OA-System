import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async apply_queryAll({commit},options){
    return (await services.apply_queryAll(options)).data
  },
  async apply_queryById({commit},options){
    return (await services.apply_queryById(options)).data
  },
  async apply_queryByFrom({commit},options){
    return (await services.apply_queryByFrom(options)).data
  },
  async apply_queryByTo({commit},options){
    return (await services.apply_queryByTo(options)).data
  },
  async apply_add({commit},options){
    return (await services.apply_add(options))
  },
  async apply_update({commit},options){
    return (await services.apply_update(options))
  },
  async apply_delete({commit},options){
    return (await services.apply_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}