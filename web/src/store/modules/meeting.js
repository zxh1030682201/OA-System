import services from '../../services'

const state = {
}
const mutations = {
}

const actions = {

  async meeting_queryById({commit},options){
    return (await services.meeting_queryById(options)).data
  },
  async meeting_queryByMember({commit},options){
    return (await services.meeting_queryByMember(options)).data
  },
  async meeting_queryByOrganizer({commit},options){
    return (await services.meeting_queryByOrganizer(options)).data
  },
  async meeting_add({commit},options){
    return (await services.meeting_add(options))
  },
  async meeting_update({commit},options){
    return (await services.meeting_update(options))
  },
  async meeting_delete({commit},options){
    return (await services.meeting_delete(options))
  }
}


export default {
  state,
  actions,
  mutations
}