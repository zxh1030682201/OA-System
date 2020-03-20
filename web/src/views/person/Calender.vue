<template>
  <div>
    <br>
    <el-tabs v-model="message" type="border-card">

      <el-tab-pane label="日程" name="first">
        <el-container style="height:550px">
          <el-main>
            <el-calendar v-model="value" @dblclick.native="handleClick()">
              <template
                  slot="dateCell"
                  slot-scope="{date, data}">
                  <span> {{ data.day.split('-').slice(1).join('-') }} </span>
                  <br><br>
                  <span style="color:#cc3300"> {{dealMyDate(data.day)}} </span>
                </template>
            </el-calendar>
          </el-main>
        </el-container>
      </el-tab-pane>

      <el-tab-pane label="日程列表" name="second">
        <el-container c>
          <el-main>
            <!-- 列表 -->
            <el-table :data="userClds" highlight-current-row @selection-change="selsChange" style="width: 100%;" height="550">
              <el-table-column type="selection">
              </el-table-column>
              <el-table-column type="index" width="100">
              </el-table-column>
              <el-table-column prop="cldDate" label="记录日期" width="120" >
              </el-table-column>
              <el-table-column prop="cldContent" label="内容" :show-overflow-tooltip="true" >
              </el-table-column>
              <el-table-column prop="createTime" label="创建时间" width="100"  >
              </el-table-column>

              <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                  <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                  <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!--工具条-->
            <el-col :span="24" class="toolbar">
              <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
            </el-col>
          </el-main>
        </el-container>
      </el-tab-pane>
    </el-tabs>

      <!-- 新建日历 -->
      <el-dialog title="新建日历详情" :visible.sync="newCldVisible">
        <el-form :model="newCld" label-width="80px" ref="cld">

          <el-form-item label="日程安排" prop="cldContent" >
            <el-input v-model="newCld.cldContent" maxlength="20" placeholder="请输入备忘录（不超过20个字符）" show-word-limit></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="handleCancel()">取消</el-button>
          <el-button type="primary" @click.native="addSubmit()">添加</el-button>
        </div>
      </el-dialog>

      <!-- 查看日历 -->
      <el-dialog title="查看日历详情" :visible.sync="editCldVisible">
        <el-form :model="editCld" label-width="80px" ref="cld">

          <el-form-item label="日程安排" prop="cldContent" >
            <el-input v-model="editCld.cldContent" maxlength="20" show-word-limit></el-input>
          </el-form-item>

          <el-form-item label="创建时间" prop="createTime" >
            <el-input v-model="editCld.createTime" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="editCldVisible = false">取消</el-button>
          <el-button type="primary" @click.native="editSubmit()">更新</el-button>
          <el-button type="danger" @click.native="delSubmit()">删除</el-button>
        </div>
      </el-dialog>

  </div>

</template>

<script>
	import util from '../../common/js/util'
  export default {
    data() {
      return {
        message: 'first',
        value: new Date(),
        newCldVisible:false,
        editCldVisible:false,
        loginUser:{},
        userClds:[],
        myOrganized:[],
        myMeeting:[],
        sels: [],//列表选中列
        newCld:{
          cldId:0,
          cldUser:1,
          cldDate:'',
          cldContent:'',
          createTime:''
        },
        editCld:{
          cldId:0,
          cldUser:1,
          cldDate:'',
          cldContent:'',
          createTime:''
        }
      }
    },
    created(){
      this.getLoginUser()
      this.getUserClds()
      this.getMyMeetings()
    },
    methods: {
      selsChange: function (sels) {
				this.sels = sels;
      },
      //查询登录的用户
      getLoginUser(){
        var user = sessionStorage.getItem('user');
        this.loginUser = JSON.parse(user)
      },
      // 查询用户的所有日历
      getUserClds(){
        this.$store.dispatch('cld_queryByUser',this.loginUser.userId).then(res=>{
          this.userClds=res
        })
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


      // 点击日历事件
      handleClick(){
        let para={
          cldDate:util.formatDate.format(this.value,'yyyy-MM-dd'),
          cldUser:this.loginUser.userId
        }
        this.$store.dispatch('cld_queryByUD',para).then(res=>{
          if(res === ''){
            console.log("这天没安排")
            this.newCldVisible=true
          }else{
            console.log(res)
            this.editCldVisible=true
            this.editCld=res
          }
        })
      },
      handleCancel(){
        this.newCldVisible = false 
        this.newCld.cldContent = ''
      },
      // 列表页面删除
      handleDel(index,row){
        this.editCld.cldId=row.cldId
        this.delSubmit()
      },
      // 列表页面编辑
      handleEdit(index,row){
        this.editCldVisible = true
        this.editCld = row
      },



      // 新建日历记录
      addSubmit(){
        let para={
          cldUser:this.loginUser.userId,
          cldDate:util.formatDate.format(this.value,'yyyy-MM-dd'),
          cldContent:this.newCld.cldContent,
          createTime:util.formatDate.format(new Date(),'yyyy-MM-dd hh:mm:ss'),
        }
        this.$store.dispatch('cld_add',para).then(res=>{
          if(res.data == 'OK'){
							this.$message({
								message: "添加成功",
								type: 'success'
              });
              this.newCldVisible = false
              this.newCld.cldContent=''
              this.getUserClds()
						}else{
							this.$message({
									message: res.data,
									type: 'error'
							});
						}
        })
      },
      // 日历页面编辑
      editSubmit(){
        this.$confirm('确认删除该记录吗?', '提示', {
          type: 'warning'
        }).then(() => {
          this.$store.dispatch('cld_update',this.editCld).then(res=>{
            if(res.data == 'OK'){
                this.$message({
                  message: "修改成功",
                  type: 'success'
                });
                this.editCldVisible = false
                this.getUserClds()
              }else{
                this.$message({
                    message: res.data,
                    type: 'error'
                });
              }
          })
        })
      },
      // 日历页面删除
      delSubmit(){
        this.$store.dispatch('cld_delete',this.editCld.cldId).then(res=>{
          if(res.data == 'OK'){
							this.$message({
								message: "删除成功",
								type: 'success'
              });
              this.editCldVisible = false
              this.getUserClds()
						}else{
							this.$message({
									message: res.data,
									type: 'error'
							});
						}
        })
      },
      //批量删除
			batchRemove() {
				let ids = this.sels.map(item => item.cldId)
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					for(let i = 0;i<ids.length;i++){
						this.$store.dispatch('cld_delete',ids[i]).then(res=>{
							this.getUserClds()
						})
					}
					this.$message({
						message: '删除成功',
						type: 'success'
					});
				})
      },
      
      // 处理日历现实内容
      dealMyDate(day) {
            let res = ""
            let meeting = ""
            for(let i=0; i< this.userClds.length; i++){
              if(this.userClds[i].cldDate == day) {
                  res = this.userClds[i].cldContent+"；"
                  break
              }
            }
            for(let j=0; j<this.myMeeting.length ; j++){
              if(this.myMeeting[j].mtTime.indexOf(day) != -1) {
                  meeting = "会议待参加"
                  break
              }
            }
            return res+meeting
        }

    }
  }

</script>

<style>
.is-selected {
    color: #1989FA;
  }
</style>