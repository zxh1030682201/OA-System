<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true">
				<el-form-item>
					<el-button type="primary" @click="handleAdd()">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="admins" highlight-current-row style="width: 100%;" height="550">
			<el-table-column prop="adminId" label="管理员编号" sortable>
			</el-table-column>
			<el-table-column prop="userId" label="管理员id" sortable>
			</el-table-column>
			<el-table-column prop="name" label="管理员名字" sortable>
			</el-table-column>
			<el-table-column prop="username" label="管理员用户名" sortable>
			</el-table-column>
			<el-table-column label="操作" width="250">
				<template slot-scope="scope">
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>


		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible">
			<el-form :model="addForm" label-width="80px" ref="addForm">

				<el-form-item label="管理员" prop="userId" >
					<el-select v-model="addForm.userId" placeholder="请选择管理员" filterable>
						<el-option
							v-for="user in users"
							:key="user.userId"
							:label="user.name"
							:value="user.userId">
						</el-option>
					</el-select>
				</el-form-item>

			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit()" >提交</el-button>
			</div>
		</el-dialog>

	</section>
</template>

<script>
export default {
  data() {
    return {
      isAdmin:false,
      admins:[],
      users:[],
      addFormVisible:false,
      addForm:{
        userId: 1
      }
    }
  },
  methods:{

    //检查当前登录者是否是管理员
    checkAdmin(){
      if(this.isAdmin == false){
        this.$message({
          message: '权限不足,请联系管理员',
          type: 'error'
        });
        this.$router.push({ path: '/message' });
      }
    },

    // 获取全部admins
    getAllAdmins(){
      this.$store.dispatch('admin_queryAll').then(res=>{
        this.admins=res
        for(let admin of res){
          if(admin.userId == JSON.parse(sessionStorage.getItem('user')).userId){
            this.isAdmin = true
            break
          }
        }
        this.checkAdmin()
      })
    },
    // 获取全部用户
    getAllUser(){
      this.$store.dispatch('user_queryAll').then(res=>{
        this.users=res
      })
    },


    //新增操作
    handleAdd(index, row){
      this.addFormVisible = true
    },

    //删除操作
    handleDel(index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('admin_delete',row.adminId).then(res =>{
          if(res.data == '删除成功'){
            this.$message({
              message: res.data,
              type: 'success'
            });
            this.getAllAdmins()
          }else{
            this.$message({
              message: res.data,
              type: 'error'
            });
          }
        })
      })
    },

    //确认新增
    addSubmit(){
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            let para = Object.assign({}, this.addForm);
            this.$store.dispatch('admin_add',para).then(res=>{
              if(res.data == "添加成功"){
                this.$message({
                  message: '添加成功',
                  type: 'success'
                });
                this.$refs['addForm'].resetFields();
                this.addFormVisible = false;
                this.getAllAdmins()
              }else{
                this.$message({
                  message: res.data,
                  type: 'error'
                });
              }
            })
          });
        }
      });
    },
  },
  created(){
    this.getAllAdmins()
    this.getAllUser()
  }
}
</script>

<style>

</style>