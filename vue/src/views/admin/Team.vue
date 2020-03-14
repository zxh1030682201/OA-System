<template>
  <div>
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true">
				<el-form-item>
					<el-button type="primary" @click="handleAdd()">新建项目</el-button>
				</el-form-item>
			</el-form>
		</el-col>
    <!--列表-->
		<el-table :data="teams" highlight-current-row style="width: 100%;" height="550px">

			<el-table-column prop="teamId" label="项目编号" sortable>
			</el-table-column>
			<el-table-column prop="teamName" label="项目名字" sortable>
			</el-table-column>
			<el-table-column prop="teamLeaderName" label="项目负责人" sortable>
			</el-table-column>

			<el-table-column label="操作" width="150">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

    <!-- 编辑项目组 -->
    <el-dialog title="编辑项目" :visible.sync="editVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" ref="editForm" :rules="FormRules">
        <el-form-item label="项目名称" prop="teamName">
					<el-input v-model="editForm.teamName" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="负责人" prop="teamLeader" >
					<el-select v-model="editForm.teamLeader" placeholder="请选择项目负责人" filterable>
						<el-option
							v-for="user in users"
							:key="user.userId"
							:label="user.name"
							:value="user.userId">
						</el-option>
					</el-select>
				</el-form-item>
      </el-form>
      <!--列表-->
      <el-table :data="members" highlight-current-row  style="width: 100%;" height="300px">
        <el-table-column prop="userId" label="工号" width="80" sortable>
        </el-table-column>
        <el-table-column prop="name" label="姓名" sortable>
        </el-table-column>
        <el-table-column prop="username" label="用户名"  sortable>
        </el-table-column>
        <el-table-column label="操作" sortable>
          <template slot-scope="scope">
            <el-button type="danger" size="small" @click="handleTeamDel(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-select v-model="newTeamMember" placeholder="请选择成员" filterable>
          <el-option
            v-for="user in users"
            :key="user.userId"
            :label="user.name"
            :value="user.userId">
          </el-option>
        </el-select>
      <el-button type="primary" @click="handleTeamAdd()">新增成员</el-button>
      <el-button type="warning" @click="editSubmit()">完成编辑</el-button>

    </el-dialog>



    <!-- 新增项目 -->
    <el-dialog title="新增项目" :visible.sync="newVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px" ref="addForm" :rules="FormRules">
        <el-form-item label="项目名称" prop="teamName">
					<el-input v-model="addForm.teamName" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="负责人" prop="teamLeader" >
					<el-select v-model="addForm.teamLeader" placeholder="请选择项目负责人" filterable>
						<el-option
							v-for="user in users"
							:key="user.userId"
							:label="user.name"
							:value="user.userId">
						</el-option>
					</el-select>
				</el-form-item>
      </el-form>
      <!--列表-->
      <el-table :data="members" highlight-current-row style="width: 100%;" height="300px">
        <el-table-column prop="userId" label="工号" width="80" sortable>
        </el-table-column>
        <el-table-column prop="name" label="姓名"  sortable>
        </el-table-column>
        <el-table-column prop="username" label="用户名"  sortable>
        </el-table-column>
        <el-table-column label="操作" sortable>
          <template slot-scope="scope">
            <el-button type="danger" size="small" @click="handleTeamDel(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-select v-model="newTeamMember" placeholder="请选择成员" filterable>
        <el-option
          v-for="user in users"
          :key="user.userId"
          :label="user.name"
          :value="user.userId">
        </el-option>
      </el-select>
      <el-button type="primary" @click="handleTeamAdd()">新增成员</el-button>
      <el-button type="warning" @click="addSubmit()">完成新增</el-button>

    </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {
      isAdmin:false,

      editVisible: false,
      newVisible:false,

				FormRules: {
					teamName: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
				},

      newTeamMember: 1,
      users:[],
      teams:[],
      members:[],
      memberStr:'',
      memberIds:[],
      addForm:{
        teamId:0,
        teamName:'',
        teamLeader: 1,
      },
      editForm:{
        teamId:0,
        teamName:'',
        teamLeader: 1,
      },
    }
  },
  methods:{
    //状态转换
    formatStatus(row,column){
        switch(row.userStatus){
          case 0:
            return '已离职'
            break
          case 1:
            return '正式员工'
            break
          case 2:
            return '试用期'
            break
          case 3:
            return '实习期'
            break
        }
    },
    //性别显示转换
    formatSex(row, column){
      return row.gender == 1 ? '男' : row.gender == 0 ? '女' : '未知';
    },
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
    // 获取所有管理员
    getAllAdmins(){
      this.$store.dispatch('admin_queryAll').then(res=>{
        for(let admin of res){
          if(admin.userId == JSON.parse(sessionStorage.getItem('user')).userId){
            this.isAdmin = true
            break
          }
        }
        this.checkAdmin()
      })
    },
    // 获取所有项目
    getAllTeam(){
      this.$store.dispatch('team_queryAll').then(res=>{
        this.teams=res
      })
    },
    // 获取所有用户
    getAllUser(){
      this.$store.dispatch('user_queryAll').then(res=>{
        this.users=res
      })
    },


    // 新增项目组
    handleAdd(){
      this.memberIds=[],
      this.members=[]
      this.newVisible = true
    },
    // 编辑项目组
    handleEdit(index,row){
      this.memberIds=row.teamMembers.split(",");
      this.memberStr=this.memberIds.join(",");
      this.members = []
      for(let id of this.memberIds){
        this.$store.dispatch('user_queryById',id).then(res=>{
          this.members.push(res)
        })
      }
      this.editVisible =true
      this.editForm=row
    },
    // 删除项目组
    handleDel(index,row){
      this.$confirm('确认删除该项目吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.$store.dispatch('team_delete',row.teamId).then(res =>{
						if(res.data == 'OK'){
							this.$message({
								message: "删除项目组成功",
								type: 'success'
							});
							this.getAllTeam()
						}else{
							this.$message({
									message: res.data,
									type: 'error'
							});
						}
					})
				})
    },
  
    // 编辑新增项目时，新增项目组成员
    handleTeamAdd(){
      // 判断是否有重复成员
      for(let i of this.memberIds){
        if(i == this.newTeamMember){
          this.$message({
            message: "请勿添加重复成员",
            type: 'error'
          })
          return
        }
      }
      this.memberIds.push(this.newTeamMember)
      this.members = []
      for(let id of this.memberIds){
        this.$store.dispatch('user_queryById',id).then(res=>{
          this.members.push(res)
        })
      }
      this.memberStr=this.memberIds.join(",");
    },
    // 编辑项目时删除项目组成员
    handleTeamDel(index,row){
      // 从数组中删除这个id
      for(let i =0;i<this.memberIds.length;i++){
        if(row.userId == this.memberIds[i]){
          this.memberIds.splice(i, 1);
          break
        }
      }
      this.memberStr=this.memberIds.join(",");
      // 重新获取成员
      this.members = []
      for(let id of this.memberIds){
        this.$store.dispatch('user_queryById',id).then(res=>{
          this.members.push(res)
        })
      }
    },

    // 确认新增
    addSubmit(){
      let para={
        teamId:this.addForm.teamId,
        teamName:this.addForm.teamName,
        teamLeader:this.addForm.teamLeader,
        teamMembers:this.memberStr
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        this.$store.dispatch('team_add',para).then(res=>{
          if(res.data == "OK"){
            this.$message({
              message: "新增项目组成功",
              type: 'success'
            })
            this.newVisible = false;
            this.getAllTeam();
          }else{
            this.$message({
              message: res.data,
              type: 'error'
            });
          }
        })
      })
    },

    // 确认编辑
    editSubmit(){
      let para={
        teamId:this.editForm.teamId,
        teamName:this.editForm.teamName,
        teamLeader:this.editForm.teamLeader,
        teamMembers:this.memberStr
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        this.$store.dispatch('team_update',para).then(res=>{
          if(res.data == "OK"){
            this.$message({
              message: "更新项目组成功",
              type: 'success'
            })
            this.editVisible = false;
            this.getAllTeam();
          }else{
            this.$message({
              message: res.data,
              type: 'error'
            });
          }
        })
      })
    },

  },
  created(){
    this.getAllAdmins()
    this.getAllTeam()
    this.getAllUser()
  }
}
</script>

<style scoped>

.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>