<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="姓名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsersByPage()">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd()">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="users" highlight-current-row @selection-change="selsChange()" style="width: 100%;">
			<el-table-column type="selection">
			</el-table-column>
			<el-table-column type="index" width="100">
			</el-table-column>
			<el-table-column prop="userId" label="工号" sortable>
			</el-table-column>
			<el-table-column prop="name" label="姓名" width="120" sortable>
			</el-table-column>
			<el-table-column prop="username" label="用户名" :show-overflow-tooltip="true" sortable>
			</el-table-column>
			<el-table-column prop="role" label="权限" :formatter="formatRole" sortable>
			</el-table-column>
			<el-table-column prop="gender" label="性别" width="100" :formatter="formatSex" sortable>
			</el-table-column>
			<el-table-column prop="tel" label="电话" :show-overflow-tooltip="true" sortable>
			</el-table-column>
			<el-table-column prop="email" label="邮箱" :show-overflow-tooltip="true" sortable>
			</el-table-column>
			<el-table-column prop="deptName" label="部门"  sortable>
			</el-table-column>
			<el-table-column prop="userStatus" label="状态" :formatter="formatStatus" sortable>
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
			<el-button type="danger" @click="batchRemove()" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible">
			<el-form :model="editForm" label-width="80px" :rules="FormRules" ref="editForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="用户名" prop="username">
					<el-input v-model="editForm.username" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="权限" prop="role">
					<el-radio-group v-model="editForm.role">
						<el-radio class="radio" :label="1">普通员工</el-radio>
						<el-radio class="radio" :label="2">管理员</el-radio>
						<el-radio class="radio" :label="3">超级管理员</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input v-model="editForm.password" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group v-model="editForm.gender">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="电话" prop="tel">
					<el-input v-model="editForm.tel" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="editForm.email" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="部门名字" prop="deptId" >
					<el-select v-model="editForm.deptId" placeholder="请选择部门">
						<el-option
							v-for="dept in depts"
							:key="dept.deptId"
							:label="dept.deptName"
							:value="dept.deptId">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="状态" prop="userStatus">
					<el-radio-group v-model="editForm.userStatus">
						<el-radio class="radio" :label="0">已离职</el-radio>
						<el-radio class="radio" :label="1">正式员工</el-radio>
						<el-radio class="radio" :label="2">试用期</el-radio>
						<el-radio class="radio" :label="3">实习期</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit()" >提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible">
			<el-form :model="addForm" label-width="80px" :rules="FormRules" ref="addForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="用户名" prop="username">
					<el-input v-model="addForm.username" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="权限" prop="role">
					<el-radio-group v-model="addForm.role">
						<el-radio class="radio" :label="1">普通员工</el-radio>
						<el-radio class="radio" :label="2">管理员</el-radio>
						<el-radio class="radio" :label="3">超级管理员</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input v-model="addForm.password" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别" prop="gender">
					<el-radio-group v-model="addForm.gender">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="电话" prop="tel">
					<el-input v-model="addForm.tel" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="addForm.email" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="部门名字" prop="deptId">
					<el-select v-model="addForm.deptId" placeholder="请选择部门">
						<el-option
							v-for="dept in depts"
							:key="dept.deptId"
							:label="dept.deptName"
							:value="dept.deptId">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="状态" prop="userStatus">
					<el-radio-group v-model="addForm.userStatus">
						<el-radio class="radio" :label="0">已离职</el-radio>
						<el-radio class="radio" :label="1">正式员工</el-radio>
						<el-radio class="radio" :label="2">试用期</el-radio>
						<el-radio class="radio" :label="3">实习期</el-radio>
					</el-radio-group>
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
	import util from '../../common/js/util'

	export default {
		data() {
			return {
				filters: {
					name: ''
				},
				loginUser:{},
				users: [],
				depts:[],
				total: 0,
				page: 1,

				sels: [],//列表选中列
				editFormVisible: false,//编辑界面是否显示

				addFormVisible: false,//新增界面是否显示


				FormRules: {
					name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
					username:[{ required: true, message: '请输入用户名', trigger: 'blur' }],
					password:[{ required: true, message: '请输入密码', trigger: 'blur' }],
					tel:[{ required: true, message: '请填写电话', trigger: 'blur' }],
					email:[{ required: true, message: '请填写邮箱', trigger: 'blur' }],
				},

				//编辑界面数据
				editForm: {
					userId: 0,
					name: '',
					username:'',
					role:1,
					password:'',
					gender: 1,
					tel:'',
					email: '',
					deptId: 1,
					userStatus: 1
				},

				//新增界面数据
				addForm: {
					name: '',
					username:'',
					role:1,
					password:'',
					gender: 1,
					tel:'',
					email: '',
					deptId: 1,
					userStatus: 1
				}

			}
		},
		created() {
			this.getLoginUser()
			this.getUsersByPage()
			this.getAllDepts()
		},
		methods: {
			//查询登录的用户
      getLoginUser(){
        var user = sessionStorage.getItem('user');
        this.loginUser = JSON.parse(user)
      },

			// 多选切换
			selsChange: function (sels) {
				this.sels = sels;
			},

			//性别显示转换
			formatSex(row, column) {
				return row.gender == 1 ? '男' : row.gender == 0 ? '女' : '未知';
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
			//状态转换
			formatRole(row,column){
					switch(row.role){
						case 1:
							return '普通员工'
							break
						case 2:
							return '管理员'
							break
						case 3:
							return '超级管理员'
							break
					}
			},
			//更改页码操作
			handleCurrentChange(val) {
				this.page = val;
				this.getUsersByPage();
			},

			//查询所有的部门
			getAllDepts(){
				this.$store.dispatch('dept_queryAll').then(res=>{
					this.depts=res
				})
			},
			//获取用户
			getUsersByPage() {
				let para = {
					page: this.page,
					name: this.filters.name
				};
				//获取总数
				this.$store.dispatch('user_queryByName',para).then(res=>{
					this.total=res.length
				})
				//获取单页
				this.$store.dispatch('user_queryPage',para).then(res=>{
					this.users = res
				})
			},

			//编辑操作
			handleEdit(index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//新增操作
			handleAdd() {
				this.addFormVisible = true;
			},
			//删除操作
			handleDel(index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.$store.dispatch('user_delete',row.userId).then(res =>{
						if(res.data == '删除用户成功'){
							this.$message({
								message: res.data,
								type: 'success'
							});
							this.getUsersByPage()
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
			editSubmit() {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							let para = Object.assign({}, this.editForm);
							this.$store.dispatch('user_update',para).then(res=>{
								if(res.data == "修改用户成功"){
									this.$message({
										message: res.data,
										type: 'success'
									})
									this.$refs['editForm'].resetFields();
									this.editFormVisible = false;
									this.getUsersByPage();
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


			//确认新增
			addSubmit() {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {

							let para = Object.assign({}, this.addForm);
							this.$store.dispatch('user_add',para).then(res=>{

								if(res.data == "添加用户成功"){
									this.$message({
										message: res.data,
										type: 'success'
									});
									this.$refs['addForm'].resetFields();
									this.addFormVisible = false;
									this.getUsersByPage();
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

			//批量删除
			batchRemove() {
				let ids = this.sels.map(item => item.userId)
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {

					for(let i = 0;i<ids.length;i++){
						this.$store.dispatch('user_delete',ids[i]).then(res=>{
							this.getUsersByPage()
						})
					}
					this.$message({
						message: '删除成功',
						type: 'success'
					});

				})
			},

		},

	}

</script>

<style scoped>

</style>