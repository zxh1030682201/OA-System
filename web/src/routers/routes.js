import Login from '../views/basic/Login.vue'
import NotFound from '../views/basic/404.vue'
import NoAccess from '../views/basic/403'
import Home from '../views/basic/Home.vue'

import Message from '../views/person/Message.vue'
import Calender from '../views/person/Calender.vue'
import MyInfo from '../views/person/MyInfo'

import Apply from '../views/smartOA/Apply'
import Attendance from '../views/smartOA/Attendance'
import Task from '../views/smartOA/Task'
import Meeting from '../views/smartOA/Meeting'

import Announcement from '../views/admin/Announcement'
import User from '../views/admin/User.vue'
import Department from '../views/admin/Department'
import Team from '../views/admin/Team'


let routes = [{
        path: '/login',
        component: Login,
        role: 0,
        name: '',
        hidden: true
    },{
        path: '/404',
        component: NotFound,
        role: 0,
        name: '',
        hidden: true
    },{
        path: '/403',
        component: NoAccess,
        role: 0,
        name: '',
        hidden: true
    },{
        path: '*',
        hidden: true,
        role: 0,
        redirect: { path: '/404' }
    },{
        path: '/',
        component: Home,
        role: 1,
        name: '个人功能',
        iconCls: 'el-icon-message',
        children: [{ 
            path: '/myInfo',
            component: MyInfo,
            role: 1,
            name: '个人资料'
        },{
            path: '/message',
            component: Message,
            role: 1,
            name: '我的消息'
        },{ 
            path: '/calender',
            component: Calender,
            role: 1,
            name: '我的日程'
        }]
    },
    {
        path: '/',
        component: Home,
        name: '智能办公',
        role: 1,
        iconCls: 'el-icon-menu',
        children: [{ 
            path: '/atd',
            component: Attendance,
            role: 1,
            name: '考勤'
        },{
            path: '/meeting',
            component: Meeting,
            role: 1,
            name: '会议'
        },{ 
            path: '/apply',
            component: Apply,
            role: 1,
            name: '审批'
        },{ 
            path: '/task',
            component: Task,
            role: 1,
            name: '任务'
        },]
    },
    {
        path: '/',
        component: Home,
        role:2,
        name: '高级管理',
        iconCls: 'el-icon-setting',//图标样式class
        children: [{
            path:'/anno',
            component:Announcement,
            role: 3,
            name: '公告管理',
        },{
            path: '/user',
            component: User,
            role: 2,
            name: '用户管理'
        },{
            path:'/dept',
            component: Department,
            role: 2,
            name: '部门管理'
        },{
            path:'/team',
            component:Team,
            role: 3,
            name: '项目管理'
        }]
    }
];

export default routes;