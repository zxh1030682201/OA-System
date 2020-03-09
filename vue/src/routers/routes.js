import Login from '../views/basic/Login.vue'
import NotFound from '../views/basic/404.vue'
import Home from '../views/basic/Home.vue'

import Message from '../views/person/Message.vue'
import Calender from '../views/person/Calender.vue'
import MyInfo from '../views/person/MyInfo'

import Apply from '../views/smartOA/Apply'
import Attendance from '../views/smartOA/Attendance'
import Task from '../views/smartOA/Task'

import Announcement from '../views/admin/Announcement'
import User from '../views/admin/User.vue'
import Department from '../views/admin/Department'
import Team from '../views/admin/Team'


let routes = [{
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },{
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },{
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    },{
        path: '/',
        component: Home,
        name: '个人功能',
        iconCls: 'el-icon-message',
        children: [{ 
            path: '/myInfo',
            component: MyInfo,
            name: '个人资料'
        },{
            path: '/message',
            component: Message,
            name: '我的消息'
        },{ 
            path: '/calender',
            component: Calender,
            name: '我的日历'
        }]
    },
    {
        path: '/',
        component: Home,
        name: '智能办公',
        iconCls: 'el-icon-menu',
        children: [{ 
            path: '/atd',
            component: Attendance,
            name: '考勤'
        },{ 
            path: '/apply',
            component: Apply,
            name: '审批'
        },{ 
            path: '/task',
            component: Task,
            name: '任务'
        },]
    },{
        path: '/',
        component: Home,
        name: '高级管理',
        iconCls: 'el-icon-setting',//图标样式class
        children: [{
            path: '/user',
            component: User,
            name: '用户管理'
        },{
            path:'/dept',
            component: Department,
            name: '部门管理'
        },{
            path:'/team',
            component:Team,
            name: '项目管理'
        },{
            path:'/anno',
            component:Announcement,
            name: '公告管理'
        }]
    }
];

export default routes;