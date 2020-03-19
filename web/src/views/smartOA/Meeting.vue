<template>
  
</template>

<script>
export default {
  data() {
    return {
      loginUser:{},
      myOrganized:[],
      myMeeting:[],
    }
  },
  created(){
    this.getLoginUser()
    this.getMyMeetings()
  },
  methods:{
    //查询登录的用户
    getLoginUser(){
      var user = sessionStorage.getItem('user');
      this.loginUser = JSON.parse(user)
    },
    getMyMeetings(){
      this.$store.dispatch('meeting_queryByOrganizer',this.loginUser.userId).then(res=>{
        this.myOrganized=res
        console.log(this.myOrganized)
      })
      this.$store.dispatch('meeting_queryByMember',this.loginUser.userId).then(res=>{
        this.myMeeting=res
        console.log(this.myMeeting)
      })
    }
  }
}
</script>

<style>

</style>