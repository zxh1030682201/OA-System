import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async anno_queryAll({commit},options){
    return (await services.anno_queryAll(options)).data
  },
  async anno_queryById({commit},options){
    return (await services.anno_queryById(options)).data
  },
  async anno_add({commit},options){
    return (await services.anno_add(options))
  },
  async anno_update({commit},options){
    return (await services.anno_update(options))
  },
  async anno_delete({commit},options){
    return (await services.anno_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}