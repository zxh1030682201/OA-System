import axios from '../util/axios.js'
import cfg from '../util/urlConfig'
const baseurl=cfg.service+'anno/'

export const queryAll = (options) => axios.setAxiosGetPromise(baseurl+'queryAll')

export const queryById = (id) => axios.setAxiosGetPromise(baseurl+'queryById/'+id)

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (annoId) => axios.setAxiosDeletePromise(baseurl+'delete/'+annoId)
