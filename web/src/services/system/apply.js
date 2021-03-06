import axios from '../util/axios.js'
import cfg from '../util/urlConfig'
const baseurl=cfg.service+'apply/'


export const queryAll = (options) => axios.setAxiosGetPromise(baseurl+'queryAll')

export const queryById = (id) => axios.setAxiosGetPromise(baseurl+'queryById/'+id)

export const queryByFrom = (applyUser) => axios.setAxiosGetPromise(baseurl+'queryByFrom/'+applyUser)

export const queryByTo = (approvalUser) => axios.setAxiosGetPromise(baseurl+'queryByTo/'+approvalUser)

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (id) => axios.setAxiosDeletePromise(baseurl+'delete/'+id)
