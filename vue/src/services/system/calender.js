import axios from '../util/axios.js'
const baseurl='http://localhost:7000/cld/'

export const queryAll = (options) => axios.setAxiosGetPromise(baseurl+'queryAll')

export const queryById = (id) => axios.setAxiosGetPromise(baseurl+'queryById/'+id)

export const queryByUser = (userId) => axios.setAxiosGetPromise(baseurl+'queryByUser/'+userId)

export const queryByUD = (options) => axios.setAxiosGetPromise(baseurl+'queryByUD',options)

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (cldId) => axios.setAxiosDeletePromise(baseurl+'delete/'+cldId)