<template>
  <div>
    <br>
    <el-tabs type="border-card">
      <el-tab-pane label="我的任务">
        <el-container style="height:550px">
          <el-table :data="myTasks" highlight-current-row>
              <el-table-column prop="taskId" label="任务编号" width="100" sortable>
              </el-table-column>
              <el-table-column prop="taskContent" label="任务内容" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <span class="message-title" @click="handleDetail(scope.row.taskContent)">{{scope.row.taskContent}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="publisherName" label="发布人" width="100" sortable>
              </el-table-column>
              <el-table-column prop="startTime" label="开始时间" width="100" sortable>
              </el-table-column>
              <el-table-column prop="endTime" label="结束时间" width="100" sortable>
              </el-table-column>
              <el-table-column prop="taskStatus" label="任务状态" :formatter="formatStatus" width="100" sortable>
              </el-table-column>
              <el-table-column prop="createTime" label="发布时间" width="100" sortable>
              </el-table-column>
              <el-table-column label="操作" width="100">
                <template slot-scope="scope">
                  <el-button type="primary" size="small" @click="handleStart(scope.$index, scope.row)" v-if="scope.row.taskStatus==21">开始任务</el-button>
                  <el-button type="warning" size="small" @click="handleEnd(scope.$index, scope.row)" v-if="scope.row.taskStatus==22">完成任务</el-button>
                </template>
              </el-table-column>
            </el-table>
        </el-container>
      </el-tab-pane>

      <el-tab-pane label="我发布的任务" v-if="!(this.myTeams.length == 0)">
        <el-container style="height:550px">
          <el-main>
            <el-table :data="myPublishs" highlight-current-row>
              <el-table-column prop="taskId" label="任务编号" width="100" sortable>
              </el-table-column>
              <el-table-column prop="taskContent" label="任务内容" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <span class="message-title" @click="handleDetail(scope.row.taskContent)">{{scope.row.taskContent}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="taskUserName" label="任务接收人" width="120" sortable>
              </el-table-column>
              <el-table-column prop="startTime" label="开始时间" width="100" sortable>
              </el-table-column>
              <el-table-column prop="endTime" label="结束时间" width="100" sortable>
              </el-table-column>
              <el-table-column prop="taskStatus" label="任务状态" :formatter="formatStatus" width="100" sortable>
              </el-table-column>
              <el-table-column prop="createTime" label="发布时间" width="100" sortable>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                  <el-button type="warning" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                  <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
          <el-footer>
            <el-button type="primary" v-on:click="handleAdd()">发布新任务</el-button>
          </el-footer>
        </el-container>
      </el-tab-pane>
    </el-tabs>

    <!-- 任务详情页面 -->
    <el-dialog
      title="任务详情"
      :visible.sync="detailVisible"
      width="30%">
      <span>{{contentDetail}}</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="detailVisible = false">确 定</el-button>
      </span>
    </el-dialog>


		<!--新增界面-->
		<el-dialog title="新建任务" :visible.sync="addFormVisible">
			<el-form :model="addForm" label-width="80px" ref="addForm">
				<el-form-item label="任务接收人" prop="taskUser" >
					<el-select v-model="addForm.taskUser" placeholder="请选择任务接收人" filterable>
						<el-option
							v-for="user in teamMembers"
							:key="user.userId"
							:label="user.name"
							:value="user.userId">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="任务内容" prop="taskContent">
					<el-input v-model="addForm.taskContent" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="选择时间" prop="time">
          <el-date-picker
            v-model="addForm.time"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit()" >提交</el-button>
			</div>
		</el-dialog>

		<!--编辑界面-->
		<el-dialog title="编辑任务" :visible.sync="editFormVisible">
			<el-form :model="editForm" label-width="80px" ref="editForm">
				<el-form-item label="任务接收人" prop="taskUser" >
					<el-select v-model="editForm.taskUser" placeholder="请选择任务接收人" filterable>
						<el-option
							v-for="user in teamMembers"
							:key="user.userId"
							:label="user.name"
							:value="user.userId">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="任务内容" prop="taskContent">
					<el-input v-model="editForm.taskContent" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="选择时间" prop="time">
          <el-date-picker
            v-model="editForm.time"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
				<el-form-item label="任务状态" prop="taskStatus">
					<el-radio-group v-model="editForm.taskStatus">
						<el-radio class="radio" :label="21">未开始</el-radio>
						<el-radio class="radio" :label="22">进行中</el-radio>
						<el-radio class="radio" :label="23">已完成</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit()" >提交</el-button>
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
      myTasks:[],
      myPublishs:[],
      myTeams:[],
      teamMemberIdStr:'',
      teamMemberIds:[],
      teamMembers:[],

      detailVisible:false,
      addFormVisible:false,
      editFormVisible:false,

      contentDetail:'',
      addForm:{
        taskId: 0,
        taskUser: 0,
        taskContent:'',
        publisher: 1,
        taskStatus: 21,
        time:[],
        startTime:'',
        endTime:'',
        createTime:'',
      },
      editForm:{
        taskId: 0,
        taskUser: 1,
        taskContent:'',
        publisher: 1,
        taskStatus: 21,
        time:[],
        startTime:'',
        endTime:'',
        createTime:'',
      }
    }
  },
  methods:{
    //审批状态转换
    formatStatus(row,column){
        switch(row.taskStatus){
          case 21:
            return '未开始'
            break
          case 22:
            return '进行中'
            break
          case 23:
            return '已完成'
            break
        }
    },

    //查询登录的用户
    getLoginUser(){
      var user = sessionStorage.getItem('user');
      this.loginUser = JSON.parse(user)
    },

    // 查询所有发布给当前用户的任务
    getMyTasks(){
      this.$store.dispatch('task_queryByUser',this.loginUser.userId).then(res=>{
        this.myTasks=res
      })
    },

    // 查询所有当前用户发布的任务
    getMyPublishs(){
      this.$store.dispatch('task_queryByPublisher',this.loginUser.userId).then(res=>{
        this.myPublishs=res
      })
    },

    // 查询当前用户主管的项目
    getMyTeams(){
      this.$store.dispatch('team_queryByLeader',this.loginUser.userId).then(res=>{
        this.myTeams=res
        for(let i =0;i<res.length-1;i++){
          this.teamMemberIdStr=this.teamMemberIdStr+res[i].teamMembers+','
        }
        this.teamMemberIdStr=this.teamMemberIdStr+res[res.length-1].teamMembers

        this.teamMemberIds = this.teamMemberIdStr.split(",");
        this.teamMemberIds = this.teamMemberIds.filter((elem, index, self) => {
          return index == self.indexOf(elem)
        })
        for(let id of this.teamMemberIds){
          this.$store.dispatch('user_queryById',id).then(res=>{
            this.teamMembers.push(res)
          })
        }
      })
    },

    // 显示任务详情
    handleDetail(content){
      this.contentDetail=content
      this.detailVisible=true
    },



    // 我的任务界面开始任务
    handleStart(index,row){
      let para = Object.assign({},row)
      para.taskStatus = 22
      this.$confirm('确认开始任务吗？', '提示', {}).then(() => {
        this.$store.dispatch('task_update',para).then(res=>{
          if(res.data == "OK"){
            this.$message({
              message: '任务已开始',
              type: 'success'
            })
            this.getMyTasks()
          }else{
            this.$message({
              message: res.data,
              type: 'error'
            });
          }
        })
      })
    },
    // 我的任务界面结束任务
    handleEnd(index,row){
      let para = Object.assign({},row)
      para.taskStatus = 23
      this.$confirm('确认完成任务吗？', '提示', {}).then(() => {
        this.$store.dispatch('task_update',para).then(res=>{
          if(res.data == "OK"){
            this.$message({
              message: '任务已完成',
              type: 'success'
            })
            this.getMyTasks()
          }else{
            this.$message({
              message: res.data,
              type: 'error'
            });
          }
        })
      })
    },
    // 我的发布页面打开新建任务页面
    handleAdd(){
      this.addFormVisible=true
      this.addForm.taskUser=this.teamMembers[0].userId
    },
    // 我发布的界面 打开编辑页面
    handleEdit(index,row){
      this.editForm = row
      this.editForm.time=[new Date(Date.parse(row.startTime)),new Date(Date.parse(row.endTime))]
      this.editFormVisible=true
    },
    // 我的发布 删除任务
    handleDel(index,row){
      this.$confirm('确认删除该任务吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('task_delete',row.taskId).then(res =>{
          if(res.data == 'OK'){
            this.$message({
              message: '任务已删除',
              type: 'success'
            });
            this.getMyPublishs()
            this.getMyTasks()
          }else{
            this.$message({
                message: res.data,
                type: 'error'
            });
          }
        })
      })
    },


    // 编辑任务页面确认编辑
    editSubmit(){
      let para = Object.assign({},this.editForm)
      para.startTime=util.formatDate.format(para.time[0],'yyyy-MM-dd')
      para.endTime=util.formatDate.format(para.time[1],'yyyy-MM-dd')
      this.$confirm('确认更新任务吗？', '提示', {}).then(() => {
          this.$store.dispatch('task_update',para).then(res=>{
            if(res.data == "OK"){
              this.$message({
                message: "更新任务成功",
                type: 'success'
              })
              this.$refs['editForm'].resetFields();
              this.editFormVisible = false;
              this.getMyPublishs()
              this.getMyTasks()
            }else{
              this.$message({
                message: res.data,
                type: 'error'
              });
            }
          })
				})
    },
    // 新建任务页面 确认新增
    addSubmit(){
      let para=Object.assign({},this.addForm)
      para.startTime=util.formatDate.format(para.time[0],'yyyy-MM-dd')
      para.endTime=util.formatDate.format(para.time[1],'yyyy-MM-dd')
      para.publisher=this.loginUser.userId
      para.createTime=util.formatDate.format(new Date(),'yyyy-MM-dd')
      this.$confirm('确认新建任务吗？', '提示', {}).then(() => {
          this.$store.dispatch('task_add',para).then(res=>{
            if(res.data == "OK"){
              this.$message({
                message: "指派任务成功",
                type: 'success'
              })
              this.$refs['addForm'].resetFields();
              this.addFormVisible = false;
              this.getMyPublishs()
              this.getMyTasks()
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
    this.getLoginUser()
    this.getMyTasks()
    this.getMyPublishs()
    this.getMyTeams()
  }
}
</script>

<style scoped>
.message-title{
  cursor: pointer;
  color: #3399ff
}
</style>