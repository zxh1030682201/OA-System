<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="部门名字"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getDepts()">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd()">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="depts" highlight-current-row style="width: 100%;" height="550">
			<el-table-column prop="deptId" label="部门编号" sortable>
			</el-table-column>
			<el-table-column prop="deptName" label="部门名字" sortable>
			</el-table-column>
			<el-table-column prop="sdeptName" label="上级部门" sortable>
			</el-table-column>
			<el-table-column prop="deptMName" label="部门主管" sortable>
			</el-table-column>
			<el-table-column label="操作" width="250">
				<template slot-scope="scope">
					<el-button size="small" @click="handleDU(scope.$index, scope.row)">查看员工</el-button>
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>



		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible">
			<el-form :model="editForm" label-width="80px" :rules="FormRules" ref="editForm">
				
				<el-form-item label="部门名字" prop="deptName">
					<el-input v-model="editForm.deptName" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="上级部门" prop="sdeptId" >
					<el-select v-model="editForm.sdeptId" placeholder="请选择上级部门">
						<el-option
							v-for="dept in chooseDepts"
							:key="dept.deptId"
							:label="dept.deptName"
							:value="dept.deptId">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="部门主管" prop="deptMId" >
					<el-select v-model="editForm.deptMId" placeholder="请选择上级部门" filterable>
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
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit()">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible">
			<el-form :model="addForm" label-width="80px" :rules="FormRules" ref="addForm">
				<el-form-item label="部门名字" prop="deptName">
					<el-input v-model="addForm.deptName" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="上级部门" prop="sdeptId" >
					<el-select v-model="addForm.sdeptId" placeholder="请选择上级部门">
						<el-option
							v-for="dept in chooseDepts"
							:key="dept.deptId"
							:label="dept.deptName"
							:value="dept.deptId">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="部门主管" prop="deptMId" >
					<el-select v-model="addForm.deptMId" placeholder="请选择部门主管" filterable>
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


		<!--查看部门员工界面-->
		<el-dialog title="查看部门员工" :visible.sync="deptUsersVisible" :close-on-click-modal="false">
			<!--列表-->
			<el-table :data="deptUsers" highlight-current-row style="width: 100%;" height="300px">
				<el-table-column prop="userId" label="工号" sortable>
				</el-table-column>
				<el-table-column prop="name" label="姓名" :show-overflow-tooltip="true" sortable>
				</el-table-column>
				<el-table-column prop="gender" label="性别" :formatter="formatSex" sortable>
				</el-table-column>
				<el-table-column prop="tel" label="电话" :show-overflow-tooltip="true" sortable>
				</el-table-column>
				<el-table-column prop="email" label="邮箱" :show-overflow-tooltip="true" sortable>
				</el-table-column>
				<el-table-column prop="userStatus" label="状态" :formatter="formatStatus" sortable>
				</el-table-column>
			</el-table>

		</el-dialog>

	</section>
</template>

<script>
	import util from '../../common/js/util'

	export default {
		data() {
			return {
				filters: {
					name: ''
				},
				isAdmi:false,

				users:[],	//编辑和新增时供选择的users

				deptUsers:[],//查询部门员工

				chooseDepts:[],//编辑和新增时供选择的depts

				depts:[],

				sels: [],//列表选中列

				deptUsersVisible: false,

				editFormVisible: false,//编辑界面是否显示

				addFormVisible: false,//新增界面是否显示

				FormRules: {
					deptName: [{ required: true, message: '请输入部门名称', trigger: 'blur' }]
				},

				//编辑界面数据
				editForm: {
					deptId:0,
					deptName:'',
					sdeptId:0,
					sdeptName:'',
					deptMId:0,
					deptMName:''
				},

				//新增界面数据
				addForm: {
					deptId:0,
					deptName:'',
					sdeptId:1,
					sdeptName:'',
					deptMId:1,
					deptMName:''
				}

			}
		},
		methods: {
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
			//查询所有部门
			getAllDept(){
				this.$store.dispatch('dept_queryAll').then(res=>{
					this.chooseDepts=res
				})
			},
			//查询所有用户
			getAllUser(){
				this.$store.dispatch('user_queryAll').then(res=>{
					this.users=res
				})
			},
			//条件查询部门
			getDepts(){
				let para={
					deptName:this.filters.name
				}
				this.$store.dispatch('dept_queryByName',para).then(res=>{
					this.depts=res
				})
			},
			//根据部门查询员工
			getUsersByDept(deptId){
				this.$store.dispatch('user_queryByDept',deptId).then(res=>{
					this.deptUsers=res
				})
			},


			//显示部门员工
			handleDU(index,row){
				this.deptUsersVisible = true
				this.getUsersByDept(row.deptId)
			},
			//编辑操作
			handleEdit(index, row){
				this.editFormVisible = true;
				this.getDepts()
				this.getAllUser()
				this.editForm = Object.assign({}, row);
			},
			//新增操作
			handleAdd(){
				this.getAllDept()
				this.getDepts()
				this.addFormVisible = true;
			},
			//删除操作
			handleDel(index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.$store.dispatch('dept_delete',row.deptId).then(res =>{
						if(res.data == '删除部门成功'){
							this.$message({
								message: res.data,
								type: 'success'
							});
							this.getDepts()
						}else{
							this.$message({
								message: res.data,
								type: 'error'
							});
						}
					})
				})
			},



			//确认编辑
			editSubmit(){
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							let para = Object.assign({}, this.editForm);
							this.$store.dispatch('dept_update',para).then(res=>{
								this.$message({
									message: '提交成功',
									type: 'success'
								})
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getDepts()
							})
						});
					}
				});
			},
			//确认新增
			addSubmit(){
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							let para = Object.assign({}, this.addForm);
							this.$store.dispatch('dept_add',para).then(res=>{
								if(res.data == "添加部门成功"){
									this.$message({
										message: '添加成功',
										type: 'success'
									});
									this.$refs['addForm'].resetFields();
									this.addFormVisible = false;
									this.getDepts()
								}else{
									this.$message({
										message: '错误',
										type: 'error'
									});
								}
							})
						});
					}
				});
			},




		},
		created() {
			this.getAllAdmins()
			this.getDepts()
			this.getAllUser()
			this.getAllDept()
		}
	}

</script>

<style scoped>

</style>