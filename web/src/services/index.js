import * as announcement from './system/announcement'
import * as apply from './system/apply'
import * as attendance from './system/attendance'
import * as calender from './system/calender'
import * as department from './system/department'
import * as message from './system/message'
import * as task from './system/task'
import * as team from './system/team'
import * as user from './system/user.js'
import * as meeting from './system/meeting'

export default {
  //user的方法
  user_queryAll:user.queryAll,
  user_queryById:user.queryById,
  user_queryByName:user.queryByName,
  user_queryByDept:user.queryByDept,
  user_add:user.add,
  user_udpate:user.update,
  user_delete:user.deleteD,
  login:user.login,
  user_queryPage:user.queryPage,

  //anno
  anno_queryAll:announcement.queryAll,
  anno_queryById:announcement.queryById,
  anno_add:announcement.add,
  anno_update:announcement.update,
  anno_delete:announcement.deleteD,

  //apply
  apply_queryAll:apply.queryAll,
  apply_queryById:apply.queryById,
  apply_queryByFrom:apply.queryByFrom,
  apply_queryByTo:apply.queryByTo,
  apply_add:apply.add,
  apply_update:apply.update,
  apply_delete:apply.deleteD,

  //attendance
  atd_queryAll:attendance.queryAll,
  atd_queryById:attendance.queryById,
  atd_queryByUser:attendance.queryByUser,
  atd_queryByUD:attendance.queryByUD,
  atd_add:attendance.add,
  atd_update:attendance.update,
  atd_delete:attendance.deleteD,

  //calender
  cld_queryAll:calender.queryAll,
  cld_queryById:calender.queryById,
  cld_queryByUser:calender.queryByUser,
  cld_queryByUD:calender.queryByUD,
  cld_add:calender.add,
  cld_update:calender.update,
  cld_delete:calender.deleteD,

  //department
  dept_queryAll:department.queryAll,
  dept_queryByName:department.queryByName,
  dept_queryById:department.queryById,
  dept_add:department.add,
  dept_update:department.update,
  dept_delete:department.deleteD,

  //message
  msg_queryAll:message.queryAll,
  msg_queryById:message.queryById,
  msg_queryByS:message.queryByS,
  msg_queryByRU:message.queryByRU,
  msg_queryByRR:message.queryByRR,
  msg_add:message.add,
  msg_update:message.update,
  msg_delete:message.deleteD,

  //task
  task_queryAll:task.queryAll,
  task_queryById:task.queryById,
  task_queryByUser:task.queryByUser,
  task_queryByPublisher:task.queryByPublisher,
  task_add:task.add,
  task_update:task.update,
  task_delete:task.deleteD,

  //team
  team_queryAll:team.queryAll,
  team_queryById:team.queryById,
  team_queryByLeader:team.queryByLeader,
  team_add:team.add,
  team_update:team.update,
  team_delete:team.deleteD,

  // meeting
  meeting_queryById:meeting.queryById,
  meeting_queryByOrganizer:meeting.queryByOrganizer,
  meeting_queryByMember:meeting.queryByMember,
  meeting_queryMeeting:meeting.queryMeeting,
  meeting_add:meeting.add,
  meeting_update:meeting.update,
  meeting_delete:meeting.deleteD
}
