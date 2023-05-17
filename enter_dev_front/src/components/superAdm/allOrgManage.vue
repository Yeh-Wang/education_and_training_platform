<template>
  <el-row>
    <el-col :span="22"/>
    <el-col :span="2">
      <el-tooltip
          class="box-item"
          effect="dark"
          content="Add a organization"
          placement="top"
      >
        <el-button type="primary" size="default" circle icon="Plus" @click="addOrganization"/>
      </el-tooltip>
    </el-col>
  </el-row>

  <!--  add-->
  <el-dialog
      v-model="centerAddDialogVisible"
      title="Add a organization Information"
      width="30%"
      align-center
  >
    <el-form :model="addForm" label-width="120px">
      <el-form-item label="机构名称">
        <el-input v-model="addForm.orgName"/>
      </el-form-item>
      <el-form-item label="机构类型">
        <el-input v-model="addForm.orgType"/>
      </el-form-item>
      <el-form-item label="机构描述">
        <el-input v-model="addForm.description"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitAdd">Submit</el-button>
        <el-button @click="addClose">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <!--    删除确认框-->
  <el-dialog
      v-model="dialogVisible"
      title="Tips"
      width="30%"
  >
    <span>Are you sure?</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="deleteMakeSure">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--  update-->
  <el-dialog
      v-model="centerUpdateDialogVisible"
      title="Update a organization Information"
      width="30%"
      align-center
  >
    <el-form :model="updateForm" label-width="120px">
      <el-form-item label="机构名称">
        <el-input v-model="updateForm.orgName"/>
      </el-form-item>
      <el-form-item label="机构类型">
        <el-input v-model="updateForm.orgType"/>
      </el-form-item>
      <el-form-item label="机构描述">
        <el-input v-model="updateForm.description"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitUpdate">Submit</el-button>
        <el-button @click="updateClose">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-table :data="allOrgInfo"
            :default-sort="{ prop: 'id', order: 'descending' }"
            height="550" stripe :cell-style="{border:'1px solid var(--el-border-color)'}"
            style="width: 100%">
    <el-table-column label="机构编号" sortable prop="id"/>
    <el-table-column label="机构名称" prop="orgName"/>
    <el-table-column label="机构类型" prop="orgType"/>
    <el-table-column label="机构描述" prop="description"/>
    <el-table-column align="right">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
        >Edit
        </el-button
        >
        <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
        >Delete
        </el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";


interface orgInfo {
  id: number;
  orgName: string;
  orgType: string;
  description: string;
  displayContent: string
}

const allOrgInfo: orgInfo[] = reactive([])
//add
const centerAddDialogVisible = ref(false)
const addForm: orgInfo = reactive({
  id: 1,
  orgName: "",
  orgType: "",
  description: "",
  displayContent: "",
})
const addOrganization = () => {
  centerAddDialogVisible.value = true
  addForm.orgName = '';
  addForm.orgType = '';
  addForm.description = '';
  addForm.displayContent = '';
}
const onSubmitAdd = () => {

  axios.post("http://localhost:9090/organization-info-entity/addOrgInfo", addForm).then(res => {
    ElMessage({
      message: '添加成功！',
      type: 'success'
    })
    addForm.id = res.data
    centerAddDialogVisible.value = false
    axios.get("http://localhost:9090/organization-info-entity/getAllOrgInfo").then(res => {
      allOrgInfo.splice(0, allOrgInfo.length)
      allOrgInfo.push(...res.data)
    })
  })
}
const addClose = () => {
  centerAddDialogVisible.value = false
  addForm.orgName = '';
  addForm.orgType = '';
  addForm.description = '';
  addForm.displayContent = '';
}
//delete
const dialogVisible = ref(false)
const deleteId = ref(999)
const deleteMakeSure = () => {
  let deleteIndex = allOrgInfo.findIndex((user) => {
    return user.id === deleteId.value
  })
  allOrgInfo.splice(deleteIndex, 1)
  axios.get("http://localhost:9090/organization-info-entity/deleteOrgInfo/" + deleteId.value).then(res => {
    if (res.data === 1) {
      ElMessage({
        message: '删除成功！',
        type: 'success'
      })
      dialogVisible.value = false
    } else {
      ElMessage.error("删除失败，请重新删除")
      dialogVisible.value = false
    }
  })
}
//update
const centerUpdateDialogVisible = ref(false)
let updateForm: orgInfo = {
  id: 1,
  orgName: "",
  orgType: "",
  description: "",
  displayContent: "",
}
const onSubmitUpdate = () => {
  axios.post("http://localhost:9090/organization-info-entity/updateOrgInfo", updateForm).then(res => {
    if (res.data === 1) {
      ElMessage({
        message: '更新成功！',
        type: 'success'
      })
      centerUpdateDialogVisible.value = false
    } else {
      ElMessage.error("更新失败，请重新更新")
      centerUpdateDialogVisible.value = false
    }
  })
}
const updateClose = () => {
  centerUpdateDialogVisible.value = false
}

const handleEdit = (index: number, row: orgInfo) => {
  updateForm = row
  centerUpdateDialogVisible.value = true
}
const handleDelete = (index: number, row: orgInfo) => {
  deleteId.value = row.id
  dialogVisible.value = true
}

onMounted(() => {
  axios.get("http://localhost:9090/organization-info-entity/getAllOrgInfo").then(res => {
    allOrgInfo.push(...res.data)
  })
  console.log(allOrgInfo)
})
</script>

<style scoped>

</style>
