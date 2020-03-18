import axios from '../util/axios.js'
import cfg from '../util/urlConfig'
const baseurl=cfg.service+'task/'

export const queryAll = (options) => axios.setAxiosGetPromise(baseurl+'queryAll')

export const queryById = (id) => axios.setAxiosGetPromise(baseurl+'queryById/'+id)

export const queryByUser = (userId) => axios.setAxiosGetPromise(baseurl+'queryByUser/'+userId)

export const queryByPublisher = (publisher) => axios.setAxiosGetPromise(baseurl+'queryByPublisher/'+publisher)

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (id) => axios.setAxiosDeletePromise(baseurl+'delete/'+id)