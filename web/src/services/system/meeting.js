import axios from '../util/axios.js'
import cfg from '../util/urlConfig'
const baseurl=cfg.service+'meeting/'



export const queryById = (mtId) => axios.setAxiosGetPromise(baseurl+'queryById/'+mtId)

export const queryByOrganizer = (organizer) => axios.setAxiosGetPromise(baseurl+'queryByOrganizer/'+organizer)

export const queryByMember = (member) => axios.setAxiosGetPromise(baseurl+'queryByMember/'+member)

export const add = (options)=>axios.setAxiosPostPromise(baseurl+'add',options)

export const update = (options)=>axios.setAxiosPutPromise(baseurl+'update',options)

export const deleteD = (id) => axios.setAxiosDeletePromise(baseurl+'delete/'+id)