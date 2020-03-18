import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async task_queryAll({commit},options){
    return (await services.task_queryAll(options)).data
  },
  async task_queryById({commit},options){
    return (await services.task_queryById(options)).data
  },
  async task_queryByUser({commit},options){
    return (await services.task_queryByUser(options)).data
  },
  async task_queryByPublisher({commit},options){
    return (await services.task_queryByPublisher(options)).data
  },
  async task_add({commit},options){
    return (await services.task_add(options))
  },
  async task_update({commit},options){
    return (await services.task_update(options))
  },
  async task_delete({commit},options){
    return (await services.task_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}