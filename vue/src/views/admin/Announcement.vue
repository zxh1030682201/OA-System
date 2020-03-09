<template>
	<section>
		<!--列表-->
		<el-table :data="annos" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" height="550">
			<el-table-column type="selection">
			</el-table-column>

			<el-table-column prop="annoId" label="公告编号" width="100" sortable>
			</el-table-column>
			<el-table-column prop="annoContent" label="公告内容" :show-overflow-tooltip="true" sortable>
			</el-table-column>
			<el-table-column prop="annoTime" label="发布时间" width="180" sortable>
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
      <el-button type="primary" @click="handleAdd">新增公告</el-button>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible">
			<el-form :model="editForm" label-width="80px" :rules="FormRules" ref="editForm">
				<el-form-item label="公告内容" prop="annoContent">
					<el-input v-model="editForm.annoContent" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>

			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible">
			<el-form :model="addForm" label-width="80px" :rules="FormRules" ref="addForm">
				<el-form-item label="公告内容" prop="annoContent">
					<el-input v-model="addForm.annoContent" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import util from '../../common/js/util'

	export default {
		data() {
			return {
        listLoading:false,
        annos:[],
        sels: [],//列表选中列
				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				FormRules: {
					annoContent: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
				},
				//编辑界面数据
				editForm: {
					annoId: 0,
					annoContent: '',
					annoTime:''
				},
				//新增界面数据
				addForm: {
					annoId: 0,
					annoContent: '',
					annoTime:util.formatDate.format(new Date(),'yyyy-MM-dd hh:mm:ss')
				}

			}
		},
		methods: {
			selsChange: function (sels) {
				this.sels = sels;
			},
      getAllAnno(){
        this.$store.dispatch('anno_queryAll').then(res=>{
          this.annos = res
        })
      },
			//显示编辑界面
			handleEdit(index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd() {
				this.addFormVisible = true;
			},


			//编辑
			editSubmit() {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							let para = Object.assign({}, this.editForm);
							this.$store.dispatch('anno_update',para).then(res=>{
								this.editLoading = false;
								if(res.data == "修改公告成功"){
									this.$message({
										message: res.data,
										type: 'success'
									})
									this.$refs['editForm'].resetFields();
									this.editFormVisible = false;
									this.getAllAnno();
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

			//新增
			addSubmit() {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							let para = Object.assign({}, this.addForm);
							this.$store.dispatch('anno_add',para).then(res=>{
								this.addLoading = false;
								if(res.data == "添加公告成功"){
									this.$message({
										message: res.data,
										type: 'success'
									});
									this.$refs['addForm'].resetFields();
									this.addFormVisible = false;
									this.getAllAnno();
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

			//删除
			handleDel(index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$store.dispatch('anno_delete',row.annoId).then(res =>{
						this.listLoading = false
						console.log(res.data)
						if(res.data == '删除公告成功'){
							this.$message({
								message: res.data,
								type: 'success'
							});
							this.getAllAnno()
						}else{
							this.$message({
									message: res.data,
									type: 'error'
							});
						}
						
					})

				})
			},

			//批量删除
			batchRemove() {
				let ids = this.sels.map(item => item.annoId)
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					for(let i = 0;i<ids.length;i++){
						this.$store.dispatch('anno_delete',ids[i]).then(res=>{
							this.getAllAnno()
						})
					}
					this.$message({
						message: '删除成功',
						type: 'success'
					});
					this.listLoading = false
				})
      }
      
		},
		mounted() {
		},
		created() {
			this.getAllAnno()
		}
	}

</script>

<style scoped>

</style>