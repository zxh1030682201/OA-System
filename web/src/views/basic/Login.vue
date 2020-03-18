<template>
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">系统登录</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox v-model="isRememberPW" class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2()" :loading="logining">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  export default {
    data() {
      return {
        logining: false,
        ruleForm2: {
          account: '',
          checkPass: ''
        },
        rules2: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
          ],
          checkPass: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ]
        },
        isRememberPW: false
      };
    },
    methods: {
      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            this.logining = true;
            var loginParams = { username: this.ruleForm2.account, password: this.ruleForm2.checkPass };
            this.$store.dispatch('login',loginParams).then(res=>{
              this.logining = false;
              let { msg, status, data } = res;
              if (status !== 200){
                this.$message({
                  message: msg,
                  type: 'error'
                });
              }
              if(data == ''){
                this.$message({
                  message: '用户名或密码不正确',
                  type: 'error'
                });
              }else{
                sessionStorage.setItem('user', JSON.stringify(data));
                sessionStorage.setItem('isRememberPW',this.isRememberPW);
                this.$router.push({ path: '/message' });
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
    },
    created(){
      if(sessionStorage.getItem('isRememberPW') == 'true'){
        this.isRememberPW = true
      }
      if(sessionStorage.getItem('user') !== null){
        this.ruleForm2.account=JSON.parse(sessionStorage.getItem('user')).username
        this.ruleForm2.checkPass=JSON.parse(sessionStorage.getItem('user')).password
      }
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>