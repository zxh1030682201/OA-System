import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {
  async dept_queryAll({commit},options){
    return (await services.dept_queryAll(options)).data
  },
  async dept_queryByName({commit},options){
    return (await services.dept_queryByName(options)).data
  },
  async dept_queryById({commit},options){
    return (await services.dept_queryById(options)).data
  },
  async dept_add({commit},options){
    return (await services.dept_add(options))
  },
  async dept_update({commit},options){
    return (await services.dept_update(options))
  },
  async dept_delete({commit},options){
    return (await services.dept_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}