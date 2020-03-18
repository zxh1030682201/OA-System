import axios from '../util/axios.js'
import cfg from '../util/urlConfig'
const baseurl=cfg.service+'admin/'

export const queryAll = (options) => axios.setAxiosGetPromise(baseurl+'queryAll')

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (adminId) => axios.setAxiosDeletePromise(baseurl+'delete/'+adminId)