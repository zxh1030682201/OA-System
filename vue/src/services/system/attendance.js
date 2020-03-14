import axios from '../util/axios.js'
const baseurl='http://192.168.1.6:7000/atd/'

export const queryAll = (options) => axios.setAxiosGetPromise(baseurl+'queryAll')

export const queryById = (id) => axios.setAxiosGetPromise(baseurl+'queryById/'+id)

export const queryByUser = (userId) => axios.setAxiosGetPromise(baseurl+'queryByUser/'+userId)

export const queryByUD = (options) => axios.setAxiosGetPromise(baseurl+'queryByUD',options)

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (id) => axios.setAxiosDeletePromise(baseurl+'delete/'+id)
