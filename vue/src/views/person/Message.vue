<template>
  <div>
    <br>
    <div class="container">
      <el-tabs v-model="message" type="border-card">
        <!-- 公告信息 -->
        <el-tab-pane :label="`公告信息`" name="first">
          <el-table :data="annos" :show-header="false" style="width: 100%" height="550">
              <el-table-column prop="annoContent" :show-overflow-tooltip="true"></el-table-column>
              <el-table-column prop="annoTime" width="200"></el-table-column>
          </el-table>

        </el-tab-pane>

        <!-- 未读信息 -->
        <el-tab-pane :label="`未读消息(${unread.length})`" name="second" >
            <el-table :data="unread" :show-header="false" style="width: 100%" height="520">
              <el-table-column prop="msgTheme" label="主题" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <span class="message-title" @click="handleCheckMsg(scope.row)">{{scope.row.msgTheme}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="sendTime" label="发送时间" width="180" >
              </el-table-column>
              <el-table-column width="120">
                  <template slot-scope="scope">
                      <el-button size="small" @click="readSubmit(scope.$index,scope.row)">标为已读</el-button>
                  </template>
              </el-table-column>
            </el-table>
            <div class="handle-row">
                <el-button type="primary" @click="handleAdd()">发送新消息</el-button>
                <el-button type="danger">全部标为已读</el-button>
            </div>
        </el-tab-pane>

        <!-- 已读信息 -->
        <el-tab-pane :label="`已读消息(${read.length})`" name="third">
          <el-table :data="read" :show-header="false" style="width: 100%" height="550">
            <el-table-column prop="msgTheme" label="主题"  :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span class="message-readed-title" @click="handleCheckMsg(scope.row)">{{scope.row.msgTheme}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="sendTime" label="发送时间" width="180" >
            </el-table-column>
            <el-table-column width="120">
                <template slot-scope="scope">
                    <el-button size="small" type="danger" @click="delSubmit(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- 已读信息 -->
        <el-tab-pane :label="`已发送消息(${send.length})`" name="fourth">
          <el-table :data="send" :show-header="false" style="width: 100%" height="550">
            <el-table-column prop="msgTheme" label="主题"  :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span class="message-readed-title" @click="handleCheckMsg(scope.row)">{{scope.row.msgTheme}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="sendTime" label="发送时间" width="180" >
            </el-table-column>
            <el-table-column width="120">
                <template slot-scope="scope">
                    <el-button size="small" type="danger" @click="delSubmit(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>


      </el-tabs>

      <!-- 查看信息详情 -->
      <el-dialog title="信息详情" :visible.sync="msgFormVisible">
        <el-form :model="msgForm" label-width="80px" ref="msgForm">
          <el-form-item label="发送人" prop="senderName" >
            <el-input v-model="msgForm.senderName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="主题" prop="msgTheme" >
            <el-input v-model="msgForm.msgTheme" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="内容" prop="msgContent" >
            <el-input v-model="msgForm.msgContent" type="textarea" :rows="3"></el-input>
          </el-form-item>
          <el-form-item label="发送时间" prop="sendTime" >
            <el-input v-model="msgForm.sendTime" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
      </el-dialog>

      <!-- 新建消息 -->
      <el-dialog title="信息详情" :visible.sync="newMsgVisible">
        <el-form :model="newMsgForm" label-width="80px" ref="newMsgForm">
          <el-form-item label="接收人" prop="receiver" >
            <el-select v-model="newMsgForm.receiver" placeholder="请选择上级部门" filterable>
              <el-option
                v-for="user in users"
                :key="user.userId"
                :label="user.name"
                :value="user.userId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="主题" prop="msgTheme" >
            <el-input v-model="newMsgForm.msgTheme" placeholder="请输入标题" clearable></el-input>
          </el-form-item>
          <el-form-item label="内容" prop="msgContent" >
            <el-input v-model="newMsgForm.msgContent" type="textarea" placeholder="请输入内容" :rows="3"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="newMsgVisible = false">取消</el-button>
          <el-button type="primary" @click.native="sendSubmit()">发送</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
	import util from '../../common/js/util'
  export default {
    data() {
      return {
        message: 'first',
        showHeader: false,
        msgFormVisible:false,
        newMsgVisible:false,
        annos:[],
        loginUser:{},
        users:[],
        unread: [],
        read: [],
        send:[],
        msgForm:{
          senderName:'',
          sendTime:'',
          msgTheme:'',
          msgContent:''
        },
        newMsgForm:{
          sender:1,
          receiver:1,
          msgTheme:'',
          msgContent:'',
          sendTime:util.formatDate.format(new Date(),'yyyy-MM-dd hh:mm:ss'),
          readed:0
        }
      }
    },
    computed: {
    },
    methods: {

      //查询登录的用户
      getLoginUser(){
        var user = sessionStorage.getItem('user');
        this.loginUser = JSON.parse(user)
      },


      // 获取所有公告信息
      getAllAnno(){
        this.$store.dispatch('anno_queryAll').then(res=>{
          this.annos = res
        })
      },
      // 获取所有发送的消息
      getAllSend(){
        this.$store.dispatch('msg_queryByS',this.loginUser.userId).then(res=>{
          this.send=res
        })
      },
      // 获取所有未读信息
      getUnReadMsg(){
        this.$store.dispatch('msg_queryByRU',this.loginUser.userId).then(res=>{
          this.unread=res
        })
      },
      // 获取所有已读信息
      getReadedMsg(){
        this.$store.dispatch('msg_queryByRR',this.loginUser.userId).then(res=>{
          this.read=res
        })
      },
      // 获取所有用户信息
      getAllUsers(){
        this.$store.dispatch('user_queryAll').then(res=>{
          this.users=res
        })
      },

      //显示信息详情窗口
      handleCheckMsg(row){
        this.msgFormVisible=true
        this.msgForm=Object.assign({},row)
      },
      //显示新增信息窗口
      handleAdd(){
        this.newMsgVisible = true
      },



      //发送新消息
      sendSubmit(){
        this.$refs.newMsgForm.validate((valid) => {
					if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              let para = Object.assign({}, this.newMsgForm);
              para.sender=this.loginUser.userId
              this.$store.dispatch('msg_add',para).then(res=>{
                if(res.data == "添加消息成功"){
                  this.$message({
                    message: "发送成功",
                    type: 'success'
                  });
                  this.$refs['newMsgForm'].resetFields();
                  this.newMsgVisible = false;
                  this.getUnReadMsg()
                  this.getAllSend()
                }else{
                  this.$message({
                    message: res.data,
                    type: 'error'
                  });
                }
              })
              
            })
          }
        })
      },
      // 标记已读
      readSubmit(index,row) {
        let para = Object.assign({},row)
        para.readed=1
        console.log(para)
        this.$store.dispatch('msg_update',para).then(res=>{
          if(res.data=="OK"){
            this.$message({
              message: "消息已读",
              type: 'success'
            })
            this.getUnReadMsg()
            this.getReadedMsg()
          }else{
            this.$message({
              message: res.data,
              type: 'error'
            });
          }
        })
      },
      // 删除消息
      delSubmit(index,row) {
        this.$store.dispatch('msg_delete',row.msgId).then(res=>{
          if(res.data=="OK"){
            this.$message({
              message: "删除消息成功",
              type: 'success'
            })
            this.getReadedMsg()
            this.getAllSend()
          }else{
            this.$message({
              message: res.data,
              type: 'error'
            });
          }
        })
      },

    },
    created(){
      this.getLoginUser()
      this.getAllAnno()
      this.getAllUsers()
      this.getUnReadMsg()
      this.getReadedMsg()
      this.getAllSend()
    }
  }

</script>

<style>
.message-title{
  cursor: pointer;
  color: #3399ff
}
.message-readed-title{
  cursor: pointer;
}
.handle-row{
  margin-top: 10px;
}
</style>