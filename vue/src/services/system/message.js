import axios from '../util/axios.js'
import cfg from '../util/urlConfig'
const baseurl=cfg.service+'msg/'

export const queryAll = (options) => axios.setAxiosGetPromise(baseurl+'queryAll')

export const queryById = (id) => axios.setAxiosGetPromise(baseurl+'queryById/'+id)

export const queryByS = (sender) => axios.setAxiosGetPromise(baseurl+'queryByS/'+sender)

export const queryByRU = (receiver) => axios.setAxiosGetPromise(baseurl+'queryByRU/'+receiver)

export const queryByRR = (receiver) => axios.setAxiosGetPromise(baseurl+'queryByRR/'+receiver)

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (id) => axios.setAxiosDeletePromise(baseurl+'delete/'+id)