<template>
  <div>
    <br>
      <el-container style="height:620px">

        <el-main class="main">
          <el-calendar v-model="value">
            <template
              slot="dateCell"
              slot-scope="{date, data}">
              <span> {{ data.day.split('-').slice(1).join('-') }} </span>
              <br><br>
              <span :class="chooseColor(data.day)"> {{dealMyDate(data.day)}} </span>
            </template>
          </el-calendar>
        </el-main>

        <el-aside class="aside">

          <el-card class="box-card1">
            <div slot="header" class="clearfix">
              <span>今日考勤</span>
              <el-button style="margin-left:60px; padding: 3px 0" type="text" @click.native="checkIn">签到</el-button>
              <el-button style="float: right; padding: 3px 0" type="text" @click.native="checkOut">签退</el-button>
            </div>
            <div class="text item">
              <div>今日签到时间：{{todayCheckInTime}}</div>
              <br>
              <div>今日签退时间：{{todayCheckOutTime}}</div>
            </div>
          </el-card>

          <el-card class="box-card2">
            <div slot="header" class="clearfix">
              <span>本月考勤</span>
            </div>
            <div class="text item">
              <div>本月签到次数：{{this.atds.length}}</div>
              <br>
              <div>本月迟到次数：{{this.monLate}} </div>
              <br>
              <div>本月早退次数：{{this.monEarly}} </div>
          </div>
          </el-card>

        </el-aside>

      </el-container>
  </div>
</template>

<script>
import util from '../../common/js/util'
  
export default {
  data() {
    return {
      loginUser:{},
      atds:[],
      todayAtd:{},
      value: new Date(),
      message: 'first',
      monLate:0,
      monEarly:0,
      todayCheckInTime:'',
      todayCheckOutTime:'',
      
    }
  },
  created(){
    this.getLoginUser()
    this.getUserAllAtd()
    this.getUserToday()
  },

  methods:{
    //查询登录的用户
    getLoginUser(){
      var user = sessionStorage.getItem('user');
      this.loginUser = JSON.parse(user)
    },

    // 查询当前用户当月所有考勤记录
    getUserAllAtd(){
      let para={
        userId:this.loginUser.userId,
        date:util.formatDate.format(new Date(),'yyyy-MM'),
      }
      this.$store.dispatch('atd_queryByUD',para).then(res=>{
        this.atds=res
        for(let j=0 ; j < this.atds.length ; j++ ){
          if(this.atds[j].checkInTime > '09:00'){
            this.monLate++
          }
          if(this.atds[j].checkOutTime < '18:00'){
            this.monEarly++
          }
        }
      })
    },
    // 获取用户当天考勤记录
    getUserToday(){
      let para={
        userId:this.loginUser.userId,
        date:util.formatDate.format(new Date(),'yyyy-MM-dd'),
      }
      this.$store.dispatch('atd_queryByUD',para).then(res=>{
        this.todayAtd=res
        if(res.length == 0){
          this.todayCheckInTime='今日还未签到'
          this.todayCheckOutTime='今日还未签退'
        }else if(res[0].checkOutTime == ''){
          this.todayCheckInTime=res[0].checkInTime
          this.todayCheckOutTime='今日还未签退'
        }else{
          this.todayCheckInTime=res[0].checkInTime
          this.todayCheckOutTime=res[0].checkOutTime
        }
      })
    },
    // 签到
    checkIn(){
      let para={
        userId:this.loginUser.userId,
        date:util.formatDate.format(new Date(),'yyyy-MM-dd'),
      }
      let _this=this
      this.$store.dispatch('atd_queryByUD',para).then(res=>{
        if(res.length !== 0 ){
          this.$message({
            message: '已签到，请勿重复签到',
            type: 'error'
          });
        }else{
          let newAtd={
            atdUser:this.loginUser.userId,
            checkDate:util.formatDate.format(new Date(),'yyyy-MM-dd'),
            checkInTime:util.formatDate.format(new Date(),'hh:mm'),
            checkOutTime:''
          }
          this.$store.dispatch('atd_add',newAtd).then(res=>[
            this.$message({
              message: '签到成功',
              type: 'success'
            }),
            setTimeout('location.reload()',1000)
          ])
        }
      })
    },
    // 签退
    checkOut(){
      let para={
        userId:this.loginUser.userId,
        date:util.formatDate.format(new Date(),'yyyy-MM-dd'),
      }
      this.$store.dispatch('atd_queryByUD',para).then(res=>{
        if(res.length == 0 ){
          this.$message({
            message: '还未签到，请先签到',
            type: 'error'
          });
        }else{
          let eidtAtd={
            atdId:res[0].atdId,
            atdUser:this.loginUser.userId,
            checkDate:util.formatDate.format(new Date(),'yyyy-MM-dd'),
            checkInTime:res[0].checkInTime,
            checkOutTime:util.formatDate.format(new Date(),'hh:mm')
          }
          this.$store.dispatch('atd_update',eidtAtd).then(res=>{
            this.$message({
              message: '签退成功',
              type: 'success'
            })
            setTimeout('location.reload()',1000)
          })
        }
      })
    },
    // 处理日历显示内容
    dealMyDate(day) {
      let len = this.atds.length
      let res = ''
      let type = 0
      for(let i=0; i<len; i++){
          if(this.atds[i].checkDate == day) {
            res = this.atds[i].checkInTime+'---'+this.atds[i].checkOutTime
            break
          }
      }
      return res
    },
    chooseColor(day){
      for(let i=0; i<this.atds.length; i++){
          if(this.atds[i].checkDate == day) {
            if(this.atds[i].checkInTime > '09:00'){
              return 'chidao'
            }
            if(this.atds[i].checkOutTime < '18:00'){
              return 'zaotui'
            }
            return 'zhengchang'
          }
      }
    }

  }
}
</script>

<style scoped>
.aside {
  position: relative;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04); 
}
.main {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
.zhengchang {
  color: rgb(23, 190, 23);
}
.chidao {
  color: red;
}
.zaotui{
  color:brown
}

.calender{
  height:400px;
}
.text {
  font-size: 14px;
}
.item {
  margin-bottom: 18px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}
.box-card1 {
  width: 100%;
  height: 40%;
}
.box-card2 {
  width: 100%;
  height: 60%;
}
</style>