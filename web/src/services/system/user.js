import axios from '../util/axios.js'
import cfg from '../util/urlConfig'
const baseurl=cfg.service+'user/'

export const login = (options) => axios.setAxiosPostPromise(baseurl+'login',options)

export const queryAll = (query) => axios.setAxiosGetPromise(baseurl+'queryAll')

export const queryPage = (query) => axios.setAxiosGetPromise(baseurl+'queryPage',query)

export const queryById = (userId) => axios.setAxiosGetPromise(baseurl+'queryById/'+userId)

export const queryByName = (options) => axios.setAxiosGetPromise(baseurl+'queryByName',options)

export const queryByDept = (deptId) => axios.setAxiosGetPromise(baseurl+'queryByDept/'+deptId)

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (userId) => axios.setAxiosDeletePromise(baseurl+'delete/'+userId)
