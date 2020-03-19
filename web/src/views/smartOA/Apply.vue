<template>
  <div>
    <br>
    <el-tabs type="border-card">
      <el-tab-pane label="我的申请">
        <el-container style="height:550px">
          <el-main>
            <el-table :data="myApplys" highlight-current-row>
              <el-table-column prop="applyId" label="申请编号" sortable>
              </el-table-column>
              <el-table-column prop="applyType" label="申请类型" :formatter="formatStatus1" sortable>
              </el-table-column>
              <el-table-column prop="startTime" label="开始时间" sortable>
              </el-table-column>
              <el-table-column prop="endTime" label="结束时间" sortable>
              </el-table-column>
              <el-table-column prop="applyReason" label="申请事由" :show-overflow-tooltip="true" sortable>
              </el-table-column>
              <el-table-column prop="applyStatus" label="申请状态" :formatter="formatStatus" sortable>
              </el-table-column>
              <el-table-column prop="approvalReason" label="驳回原因" sortable>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)" v-if="scope.row.applyStatus==11">撤回</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
          <el-footer>
            <el-button type="primary" v-on:click="handleAdd()">新建申请</el-button>
          </el-footer>
        </el-container>
      </el-tab-pane>

      <el-tab-pane label="我的审批" v-if="this.loginUser.role >= 3">
        <el-container style="height:550px">
          <el-main>
            <!--列表-->
            <el-table :data="myApprovals" highlight-current-row>
              <el-table-column prop="applyId" label="申请编号" sortable>
              </el-table-column>
              <el-table-column prop="applyUserName" label="申请人" sortable>
              </el-table-column>
              <el-table-column prop="applyType" label="申请类型" :formatter="formatStatus1" sortable>
              </el-table-column>
              <el-table-column prop="startTime" label="开始时间" sortable>
              </el-table-column>
              <el-table-column prop="endTime" label="结束时间" sortable>
              </el-table-column>
              <el-table-column prop="applyReason" label="申请事由" :show-overflow-tooltip="true" sortable>
              </el-table-column>
              <el-table-column prop="applyStatus" label="申请状态" :formatter="formatStatus" sortable>
              </el-table-column>
              <el-table-column prop="approvalReason" label="驳回原因" sortable>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                  <el-button type="warning" size="small" @click="handleCancel(scope.$index, scope.row)" v-if="scope.row.applyStatus == 11">驳回</el-button>
                  <el-button type="primary" size="small" @click="handlePass(scope.$index, scope.row)" v-if="scope.row.applyStatus == 11">通过</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
        </el-container>
      </el-tab-pane>
    </el-tabs>


    <el-dialog title="驳回理由" :visible.sync="editVisible">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="驳回理由" prop="approvalReason">
					<el-input v-model="editForm.approvalReason" auto-complete="off"></el-input>
				</el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
				<el-button @click.native="editVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" >提交</el-button>
			</div>
    </el-dialog>


		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="newVisible">
			<el-form :model="newForm" label-width="80px" :rules="FormRules" ref="newForm">

				<el-form-item label="申请类别" prop="applyType">
					<el-radio-group v-model="newForm.applyType">
						<el-radio class="radio" :label="31">病假</el-radio>
						<el-radio class="radio" :label="32">事假</el-radio>
            <el-radio class="radio" :label="33">其他</el-radio>
					</el-radio-group>
				</el-form-item>


				<el-form-item label="请假事由" prop="applyReason">
					<el-input v-model="newForm.applyReason" auto-complete="off"></el-input>
				</el-form-item>

        <el-form-item label="选择时间" prop="time">
              <el-date-picker
                v-model="newForm.time"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
        </el-form-item>

			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="newVisible = false">取消</el-button>
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
        value1: '',
        loginUser:{},
        loginUserDept:{},
        loginUserSDept:{},
        myApplys:[],
        myApprovals:[],

				FormRules: {
          applyReason: [{ required: true, message: '请填写申请缘由', trigger: 'blur' }],
          time:[{ required: true, message: '请填写时间', trigger: 'blur' }]
				},

				editFormRules: {
          approvalReason: [{ required: true, message: '请填写驳回理由', trigger: 'blur' }]
				},

        newVisible:false,

        editVisible:false,
        newForm:{
          applyId: 0,
          applyUser: 0,
          approvalUser: 0,
          applyType: 31,
          applyReason: '',
          time:{},
          startTime:'',
          endTime:'',
          applyStatus: 11
        },

        editForm:{
          applyId: 0,
          applyUser: 0,
          approvalUser: 0,
          applyType: 31,
          applyReason: '',
          approvalReason:'',
          time:{},
          startTime:'',
          endTime:'',
          applyStatus: 11
        }
      };
    },
    methods: {
      //审批状态转换
			formatStatus(row,column){
					switch(row.applyStatus){
						case 11:
							return '未审批'
							break
						case 12:
							return '审批通过'
							break
						case 13:
							return '审批驳回'
							break
					}
      },
      //申请状态转换
			formatStatus1(row,column){
					switch(row.applyType){
						case 31:
							return '病假'
							break
						case 32:
							return '事假'
							break
						case 33:
							return '其他'
							break
					}
      },
      

      //查询登录的用户
      getLoginUser(){
        var user = sessionStorage.getItem('user');
        this.loginUser = JSON.parse(user)
      },
      // 查询登录的用户的部门
      getLoginUserDept(){
        this.$store.dispatch('dept_queryById',this.loginUser.deptId).then(res=>{
          this.loginUserDept=res
          this.$store.dispatch('dept_queryById',this.loginUserDept.sdeptId).then(res=>{
            this.loginUserSDept=res
          })
        })
        
      },
      // 查询登录用户的申请
      getAllMyApply(){
        this.$store.dispatch('apply_queryByFrom',this.loginUser.userId).then(res=>{
          this.myApplys=res
        })
      },
      // 查询登录用户的审批
      getAllMyApprovals(){
        this.$store.dispatch('apply_queryByTo',this.loginUser.userId).then(res=>{
          this.myApprovals=res
        })
      },


      // 新建申请操作
      handleAdd(){
        this.newVisible = true
      },
      // 驳回操作
      handleCancel(index,row){
        this.editForm = Object.assign({},row);
        this.editForm.applyStatus=13
        this.editVisible = true
      },
      // 通过操作
      handlePass(index,row){
        this.editForm = Object.assign({},row);
        this.editForm.applyStatus=12
        this.editForm.approvalReason="审批通过"
        this.$confirm('确认通过申请吗？', '提示', {}).then(() => {
          this.$store.dispatch('apply_update',this.editForm).then(res=>{
            if(res.data == "修改申请成功"){
              this.$message({
                message: "通过成功",
                type: 'success'
              })
              this.editVisible = false;
              this.getAllMyApprovals()
            }else{
              this.$message({
                message: res.data,
                type: 'error'
              })
            }
          })
        })
      },
      // 删除操作
      handleDel(index,row){
        this.$confirm('确定撤回该申请吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.$store.dispatch('apply_delete',row.applyId).then(res =>{
						if(res.data == '删除申请成功'){
							this.$message({
								message: res.data,
								type: 'success'
							});
							this.getAllMyApply()
						}else{
							this.$message({
									message: res.data,
									type: 'error'
							});
						}
					})
				})
      },


      // 确认新建
      addSubmit(){
        this.newForm.applyUser=this.loginUser.userId
        if(this.loginUser.userId==this.loginUserDept.deptMId){
          this.newForm.approvalUser=this.loginUserSDept.deptMId
        }else{
          this.newForm.approvalUser=this.loginUserDept.deptMId
        }
        this.newForm.startTime=util.formatDate.format(this.newForm.time[0],'yyyy-MM-dd'),
        this.newForm.endTime=util.formatDate.format(this.newForm.time[1],'yyyy-MM-dd'),
        this.$confirm('确认提交申请吗？', '提示', {}).then(() => {
          this.$store.dispatch('apply_add',this.newForm).then(res=>{
            if(res.data == "新增申请成功"){
              this.$message({
                message: res.data,
                type: 'success'
              })
              this.$refs['newForm'].resetFields();
              this.value1={}
              this.newVisible = false;
              this.getAllMyApply()
            }else{
              this.$message({
                message: res.data,
                type: 'error'
              });
            }
          })
				})
      },
      // 确认驳回
      editSubmit(){
        this.$confirm('确认驳回申请吗？', '提示', {}).then(() => {
          this.$store.dispatch('apply_update',this.editForm).then(res=>{
            if(res.data == "修改申请成功"){
              this.$message({
                message: "驳回成功",
                type: 'success'
              })
              this.$refs['editForm'].resetFields();
              this.editVisible = false;
              this.getAllMyApprovals()
            }else{
              this.$message({
                message: res.data,
                type: 'error'
              })
            }
          })
        })
      },

  },
  created(){
    this.getLoginUser()
    this.getLoginUserDept()
    this.getAllMyApply()
    this.getAllMyApprovals()
  }
}
</script>

<style scoped>

</style>