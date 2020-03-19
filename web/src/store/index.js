import Vue from 'vue'
import Vuex from 'vuex'
import validate from './validate'
import announcement from './modules/announcement'
import apply from './modules/apply'
import attendance from './modules/attendance'
import calender from './modules/calender'
import department from './modules/department'
import message from './modules/message'
import task from './modules/task'
import team from './modules/team'
import user from './modules/user'
import meeting from './modules/meeting'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    validate,
    user,
    announcement,
    apply,
    attendance,
    calender,
    department,
    message,
    task,
    team,
    meeting
  }
})
