<template>
  <div>
    <br>
    <el-tabs type="border-card">
      <el-tab-pane label="我的会议">
        <el-container style="height:550px">
          <el-main>
            <el-table :data="myMeeting" highlight-current-row>
              <el-table-column prop="mtId" label="会议编号" sortable>
              </el-table-column>
              <el-table-column prop="organizerName" label="发起人" sortable>
              </el-table-column>
              <el-table-column prop="mtTime" label="会议时间" :show-overflow-tooltip="true" sortable>
              </el-table-column>
              <el-table-column prop="mtPlace" label="会议地点" :show-overflow-tooltip="true" sortable>
              </el-table-column>
            </el-table>
          </el-main>
        </el-container>
      </el-tab-pane>
      <el-tab-pane label="我发起的" v-if="loginUser.role >= 2">
        <el-container style="height:550px">
          <el-main style="height:530px">
            <el-table :data="myOrganized" highlight-current-row>
              <el-table-column prop="mtId" label="会议编号" sortable>
              </el-table-column>
              <el-table-column prop="memberName" label="参会人" sortable>
              </el-table-column>
              <el-table-column prop="mtTime" label="会议时间" :show-overflow-tooltip="true" sortable>
              </el-table-column>
              <el-table-column prop="mtPlace" label="会议地点" :show-overflow-tooltip="true" sortable>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
          <el-footer>
            <el-button type="primary" @click.native="handleAdd()">发布新会议</el-button>
          </el-footer>
        </el-container>
      </el-tab-pane>
    </el-tabs>

		<!--新增界面-->
		<el-dialog title="新建会议" :visible.sync="addFormVisible">

			<el-form :model="addForm" label-width="80px" :rules="FormRules" ref="addForm">
        <el-form-item label="会议地点" prop="mtPlace">
					<el-radio-group v-model="addForm.mtPlace">
						<el-radio-button class="radio" label="南会议室">南会议室</el-radio-button>
						<el-radio-button class="radio" label="北会议室">北会议室</el-radio-button>
					</el-radio-group>
				</el-form-item>
        <el-form-item label="会议时间" prop="mtTime">
					<el-date-picker
            v-model="addForm.mtTime"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
				</el-form-item>

        <el-form-item label="参会人">
          <el-container style="height:200px">
            <el-main>
              <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
              <el-checkbox-group v-model="newMembers" @change="handleCheckedChange">
                <el-checkbox v-for="member in myMeetingMembers" :label="member.userId" :key="member.userId">{{member.name}}</el-checkbox>
              </el-checkbox-group>
            </el-main>
          </el-container>
        </el-form-item>

			</el-form>

			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit()" >提交</el-button>
			</div>
		</el-dialog>


  </div>
</template>

<script>
import util from '../../common/js/util'
export default {
  data() {
    return {
      loginUser:{},
      myOrganized:[],
      myMeeting:[],
      teamMemberIdStr:'',
      myTeamMemberIds:[],
      myMeetingMembers:[],//登陆者有权限选择参加会议的人
      newMembers:[],//新建会议参与人员ID
      addFormVisible:false,
      addForm:{
        mtId:0,
        organizer: 0,
        member: 1,
        mtTime:'',
        mtPlace:'南会议室'
      },
      FormRules: {
        mtTime:[{ required: true, message: '请选择会议时间', trigger: 'blur' }],
        mtPlace:[{ required: true, message: '请选择会议地点', trigger: 'blur' }]
      },
      checkAll: true,
      isIndeterminate: false
    }
  },
  created(){
    this.getLoginUser()
    this.getMyMeetings()
    this.getNewMmebers()
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
      })
      let para={
        member:this.loginUser.userId,
        date:util.formatDate.format(new Date(),'yyyy-MM-dd hh:mm')
      }
      this.$store.dispatch('meeting_queryMeeting',para).then(res=>{
        this.myMeeting=res
      })
    },
    // 获取当前用户可以发起会议的所有人
    getNewMmebers(){
      if(this.loginUser.role == 2){
        this.$store.dispatch('team_queryByLeader',this.loginUser.userId).then(res=>{
          for(let i =0;i<res.length-1;i++){
            this.teamMemberIdStr=this.teamMemberIdStr+res[i].teamMembers+','
          }
          this.teamMemberIdStr=this.teamMemberIdStr+res[res.length-1].teamMembers
          this.myTeamMemberIds=this.teamMemberIdStr.split(',')
          this.myTeamMemberIds = this.myTeamMemberIds.filter((elem, index, self) => {
            return index == self.indexOf(elem)
          })
          for(let id of this.myTeamMemberIds){
            this.$store.dispatch('user_queryById',id).then(res=>{
              this.myMeetingMembers.push(res)
              this.newMembers.push(res.userId)
            })
          }
        })
      }
      if(this.loginUser.role == 3){
        this.$store.dispatch('user_queryByDept',this.loginUser.deptId).then(res=>{
          this.myMeetingMembers=res
          for(let member of res){
            this.newMembers.push(member.userId)
          }
        })
      }
      if(this.loginUser.role == 4){
        this.$store.dispatch('user_queryAll').then(res=>{
          this.myMeetingMembers=res
          for(let member of res){
            this.newMembers.push(member.userId)
          }
        })
      }
    },
    handleAdd(){
      this.addForm.mtTime=''
      this.addFormVisible=true
    },
    handleDel(index,row){
      this.$confirm('确定删除该会议吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.$store.dispatch('meeting_delete',row.mtId).then(res =>{
						if(res.data == 'OK'){
							this.$message({
								message: "删除会议成功",
								type: 'success'
              });
							this.getMyMeetings()
						}else{
							this.$message({
									message: res.data,
									type: 'error'
							});
						}
					})
				})
    },
    addSubmit(){
      this.addForm.mtTime=util.formatDate.format(this.addForm.mtTime,'yyyy-MM-dd hh:mm')
      this.addForm.organizer=this.loginUser.userId
      let paras=[]
      this.$confirm('确认发布吗？', '提示', {}).then(() => {
        for(let member of this.newMembers){
          let para = Object.assign({}, this.addForm);
          para.member=member
          this.$store.dispatch('meeting_add',para).then(res=>{
            if(res.data != "OK"){
              this.$message({
                message: res.data,
                type: 'error'
              });
            }
          })
        }
        this.addFormVisible = false;
        this.$message({
          message: "发布成功",
          type: 'sucess'
        });
        this.getMyMeetings();
      })
    },
    handleCheckAllChange(val){
        if(val){
          for(let member of this.myMeetingMembers){
            this.newMembers.push(member.userId)
          }
        }else{
          this.newMembers=[]
        }
        this.isIndeterminate = false;
    },
    handleCheckedChange(value){
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.myMeetingMembers.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.myMeetingMembers.length;
    }

  }
}
</script>

<style>

</style>