<template>
  
</template>

<script>
export default {
  data() {
    return {
      loginUser:{},
    }
  },
  created(){
    this.getLoginUser()
  },
  methods:{
    //查询登录的用户
    getLoginUser(){
      var user = sessionStorage.getItem('user');
      this.loginUser = JSON.parse(user)
    },
  }
}
</script>

<style>

</style>