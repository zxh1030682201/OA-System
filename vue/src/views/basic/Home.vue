<template>
  <el-row class="container">
    <el-col
      :span="24"
      class="header"
    >
			<!-- 左上角logo -->
      <el-col
        :span="10"
        class="logo"
      >
        {{sysName}}
      </el-col>
			<!-- 右上角登录信息 -->
      <el-col
        :span="4"
        class="userinfo"

      >
        <!-- 未读信息 -->
        <span class="btn-bell">
          <el-tooltip
              :content="message?`有${message}条未读消息`:`消息中心`"
              placement="bottom"
          >
              <router-link to="/message">
                  <i class="el-icon-bell"></i>
              </router-link>
          </el-tooltip>
          <span class="btn-bell-badge" v-if="message"></span>
        </span>
        <el-dropdown trigger="hover">
          <span class="el-dropdown-link userinfo-inner">你好,{{sysUserName}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="toMessage">我的消息</el-dropdown-item>
            <el-dropdown-item @click.native="toMyInfo">个人资料</el-dropdown-item>
            <el-dropdown-item
              divided
              @click.native="logout"
            >退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        
      </el-col>
    </el-col>
    <el-col
      :span="24"
      class="main"
    >
		<!-- 左侧导航栏 -->
      <aside class="menu-expanded">
        <el-menu
          :default-active="$route.path"
          class="el-menu-vertical-demo"
          router
        >
          <div
            v-for="(item,index) in $router.options.routes"
						:key="index"
          >
						<!-- 一级菜单 -->
            <el-submenu
						  v-if="!item.hidden"
              :index="index+''"
            >
              <template slot="title"><i :class="item.iconCls"></i>{{item.name}}</template>
              <el-menu-item
                v-for="child in item.children"
                :index="child.path"
                :key="child.path"
              >{{child.name}}</el-menu-item>
            </el-submenu>

          </div>
        </el-menu>
      </aside>

      <section class="content-container">
        <div class="grid-content bg-purple-light">
          <!-- 页面title -->
					<el-col
            :span="24"
            class="breadcrumb-container"
          >
            <strong class="title">{{$route.name}}</strong>
            <el-breadcrumb
              separator="/"
              class="breadcrumb-inner"
            >
              <el-breadcrumb-item
                v-for="item in $route.matched"
                :key="item.path"
              >
                {{ item.name }}
              </el-breadcrumb-item>
            </el-breadcrumb>
          </el-col>
					<!-- 页面内容 -->
          <el-col
            :span="24"
            class="content-wrapper"
          >
            <transition
              name="fade"
              mode="out-in"
            >
              <router-view></router-view>
            </transition>
          </el-col>

        </div>
      </section>


    </el-col>
  </el-row>
</template>

<script>
export default {
  data () {
    return {
      sysName: 'OASYS',
      loginUser:{},
      sysUserName: '',
      sysUserAvatar: '',
      message:0
    }
  },
  methods: {
    onSubmit () {
      console.log('submit!');
    },

    //退出登录
    logout(){
      this.$confirm('确认退出吗?', '提示', {
      }).then(() => {
        sessionStorage.removeItem('user');
        this.$router.push('/login');
      })
    },
    toMessage(){
      this.$router.push('/message')
    },
    toMyInfo(){
      this.$router.push('/myInfo')
    },
    getURMsg(){
      this.$store.dispatch('msg_queryByRU',this.loginUser.userId).then(res=>{
        this.message=res.length
        console.log(this.message)
      })
    }

  },
  created(){
    var user = sessionStorage.getItem('user');
    if (user) {
      console.log("当前登录的用户\n"+user)
      this.loginUser = JSON.parse(user);
      this.sysUserName = this.loginUser.name || '';
    }
    this.getURMsg()
  }
}

</script>

<style scoped lang="scss">
@import "~scss_vars";

.btn-bell {
  margin-right: 10px;
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}
.btn-bell .el-icon-bell {
    color: #fff;
}


.container {
  position: absolute;
  top: 0px;
  bottom: 0px;
  width: 100%;
  .header {
    height: 60px;
    line-height: 60px;
    background: $color-primary;
    color: #fff;
    .userinfo {
      text-align: right;
      padding-right: 35px;
      float: right;
      .userinfo-inner {
        cursor: pointer;
        color: #fff;
        img {
          width: 40px;
          height: 40px;
          border-radius: 20px;
          margin: 10px 0px 10px 10px;
          float: right;
        }
      }
    }
    .logo {
      width:230px;
      height: 60px;
      font-size: 22px;
      padding-left: 20px;
      padding-right: 20px;
      border-color: rgba(238, 241, 146, 0.3);
      border-right-width: 1px;
      border-right-style: solid;
      img {
        width: 40px;
        float: left;
        margin: 10px 10px 10px 18px;
      }
      .txt {
        color: #fff;
      }
    }
    .logo-width {
      width: 230px;
    }
    .tools {
      padding: 0px 23px;
      width: 14px;
      height: 60px;
      line-height: 60px;
      cursor: pointer;
    }
  }
  .main {
    display: flex;
    // background: #324057;
    position: absolute;
    top: 60px;
    bottom: 0px;
    overflow: hidden;
    aside {
      flex: 0 0 230px;
      width: 230px;
      // position: absolute;
      // top: 0px;
      // bottom: 0px;
      .el-menu {
        height: 100%;
      }
    }
    .menu-expanded {
      flex: 0 0 230px;
      width: 230px;
    }
    .content-container {
      // background: #f1f2f7;
      flex: 1;
      // position: absolute;
      // right: 0px;
      // top: 0px;
      // bottom: 0px;
      // left: 230px;
      overflow-y: scroll;
      padding: 20px;
      .breadcrumb-container {
        //margin-bottom: 15px;
        .title {
          width: 200px;
          float: left;
          color: #475669;
        }
        .breadcrumb-inner {
          float: right;
        }
      }
      .content-wrapper {
        background-color: #fff;
        box-sizing: border-box;
      }
    }
  }
}
</style>