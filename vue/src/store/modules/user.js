import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async login({commit},options){
    return (await services.login(options))
  },
  async user_queryPage({commit},query){
    return (await services.user_queryPage(query)).data
  },
  async user_queryAll({commit}){
    return (await services.user_queryAll()).data
  },
  async user_queryById({commit},query){
    return (await services.user_queryById(query)).data
  },
  async user_queryByName({commit},name){
    return (await services.user_queryByName(name)).data
  },
  async user_queryByDept({commit},deptId){
    return (await services.user_queryByDept(deptId)).data
  },
  async user_add({commit},options){
    return await services.user_add(options)
  },
  async user_update({commit},options){
    return await services.user_udpate(options)
  },
  async user_delete({commit},userId){
    return await services.user_delete(userId)
  }
}




export default {
  state,
  actions,
  mutations
}
