<template>
  <div>
    <br>
    <el-form :model="user" label-width="80px" :rules="FormRules" ref="addForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="user.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="用户名" prop="username">
					<el-input v-model="user.username" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input v-model="user.password" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别" prop="gender">
					<el-radio-group v-model="user.gender">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="电话" prop="tel">
					<el-input v-model="user.tel" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="user.email" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button type="primary" @click.native="editSubmit">更新</el-button>
			</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user:{},
      FormRules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        username:[{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password:[{ required: true, message: '请输入密码', trigger: 'blur' }],
        tel:[{ required: true, message: '请填写电话', trigger: 'blur' }],
        email:[{ required: true, message: '请填写邮箱', trigger: 'blur' }],
      },
    }
  },
  created(){
    this.getLoginUser()
  },
  methods:{
    getLoginUser(){
      this.user = JSON.parse(sessionStorage.getItem('user'))
    },
    getThisUser(){
      this.$store.dispatch('user_queryById',this.user.userId).then(res=>{
        this.user=res
        sessionStorage.setItem('user', JSON.stringify(res));
      })
    },
    			//编辑
		editSubmit() {
      this.$confirm('确认更新吗？', '提示', {}).then(() => {
        this.editLoading = true;
        this.$store.dispatch('user_update',this.user).then(res=>{
          this.editLoading = false;
          if(res.data == "修改用户成功"){
            this.$message({
              message: res.data,
              type: 'success'
            })
            this.editFormVisible = false;
            this.getThisUser();
          }else{
            this.$message({
              message: res.data,
              type: 'error'
            });
          }
        })
      });
		}
  }
}
</script>

<style scoped>

</style>