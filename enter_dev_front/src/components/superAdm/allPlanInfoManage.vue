<template>

  <!--Add a training plan information-->
  <el-dialog
      v-model="centerAddDialogVisible"
      title="Add a training plan"
      width="30%"
      align-center
  >
    <el-form :model="addForm" label-width="120px">
      <el-form-item label="计划所属机构">
        <el-select-v2
            v-model="addForm.mechanismId"
            placeholder="机构编号"
            :options="options"
        />
      </el-form-item>
      <el-form-item label="计划描述">
        <el-input v-model="addForm.trainIntroduce"/>
      </el-form-item>
      <el-form-item label="计划类型">
        <el-input v-model="addForm.trainType"/>
      </el-form-item>
      <el-form-item label="计划周期（周）">
        <el-select-v2
            v-model="addForm.trainPeriod"
            placeholder="计划周期"
            :options="options"
        />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-col :span="15">
          <el-date-picker
              v-model="addForm.startTime"
              type="datetime"
              placeholder="Pick a date"
              style="width: 100%"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-col :span="15">
          <el-date-picker
              v-model="addForm.endTime"
              type="datetime"
              placeholder="Pick a date"
              style="width: 100%"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-col>
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
<!--  修改信息-->
  <el-dialog
      v-model="centerUpdateDialogVisible"
      title="update a training plan"
      width="30%"
      align-center
  >
    <el-form :model="updateForm" label-width="120px">
      <el-form-item label="计划描述">
        <el-input text v-model="updateForm.trainIntroduce"/>
      </el-form-item>
      <el-form-item label="计划类型">
        <el-input v-model="updateForm.trainType"/>
      </el-form-item>
      <el-form-item label="计划周期（周）">
        <el-select-v2
            v-model="updateForm.trainPeriod"
            placeholder="计划周期"
            :options="options"
        />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-col :span="15">
          <el-date-picker
              v-model="updateForm.startTime"
              type="datetime"
              placeholder="Pick a date"
              style="width: 100%"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-col :span="15">
          <el-date-picker
              v-model="updateForm.endTime"
              type="datetime"
              placeholder="Pick a date"
              style="width: 100%"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="计划状态">
        <el-select v-model="updateForm.trainStatus" placeholder="please select">
          <el-option label="进行中" value="进行中" />
          <el-option label="已结束" value="已结束" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitUpdate">Submit</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-affix :offset="20" style="padding-left: 90%">
    <el-tooltip
        class="box-item"
        effect="dark"
        content="Add a training plan"
        placement="left-start"
    >
      <el-button type="success" size="large" circle icon="Plus" @click="addUser"/>
    </el-tooltip>
  </el-affix>
  <el-row style="padding-left: 8%;padding-top: 20px;">
    <el-space wrap>
      <el-col v-for="trainInfo in allPlansInfo" :span="5">
        <el-space wrap>
          <el-card>
            <div style="height: 20px;">
              <el-row>
                <el-col>
                  <el-tooltip
                      class="box-item"
                      effect="light"
                      content="计划编号"
                      placement="top-start"
                  >
                    <el-button circle text>{{ trainInfo.id }}</el-button>
                  </el-tooltip>
                  <el-tooltip
                      class="box-item"
                      effect="light"
                      content="Delete!"
                      placement="top-start"
                  >
                    <el-button id="deleteBtn" size="default" text type="danger" @click="deletePlan(trainInfo.id)"
                               icon="Close" circle/>
                  </el-tooltip>
                </el-col>
              </el-row>
            </div>
            <div>
              <p style="border-top: 1px solid #f6f1f1;font-size: small; word-wrap: break-word">机构编号: {{ trainInfo.mechanismId }}  计划类型: {{ trainInfo.trainType }}</p>
            </div>
            <div style="height: 100px;">
              <p style="border-top: 2px solid #ccc6c6;font-size: small; word-wrap: break-word">{{ trainInfo.trainIntroduce }}</p>
            </div>
            <div class="footBtb" v-if="trainInfo.trainStatus==='进行中'">
              <el-tooltip
                  class="box-item"
                  effect="light"
                  content="计划进行中"
                  placement="top"
              >
                <el-button text type="success" size="default" @click="editPlanInfo(trainInfo)">Edit</el-button>
              </el-tooltip>
            </div>
            <div class="footBtb" v-else>
              <el-tooltip
                  class="box-item"
                  effect="light"
                  content="计划已结束"
                  placement="top"
              >
                <el-button text type="success" size="default" @click="editPlanInfo(trainInfo)">Edit</el-button>
              </el-tooltip>
            </div>
          </el-card>
        </el-space>
      </el-col>
    </el-space>
  </el-row>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";

interface trainInfo {
  id: number,
  founder: number,
  mechanismId: number,
  trainIntroduce: string,
  trainType: string,
  trainPeriod: number,
  startTime: string,
  endTime: string,
  trainStatus: string
}

const planTips = ref("进行中")

const options = Array.from({length: 100}).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
const mechanism_id = ref(); //接收路由参数（登录用户所属的机构编号）
const adm_id = ref() //接收路由参数（登陆用户的id）
const route = useRoute()
const allPlansInfo: trainInfo[] = reactive([])
//增加一个培训计划
const centerAddDialogVisible = ref(false)
const addForm: trainInfo = reactive({
  id: 1,
  founder: 1,
  mechanismId: 1,
  trainIntroduce: "",
  trainType: "",
  trainPeriod: 4,
  startTime: "2000-02-02 00:00:00",
  endTime: "2000-02-02 00:00:00",
  trainStatus: ""
})
const addClose = () => {
  centerAddDialogVisible.value = false
  addForm.trainIntroduce = "";
  addForm.trainType = "";
  addForm.trainPeriod = 4;
  addForm.startTime = "2000-02-02 00:00:00";
  addForm.endTime = "2000-02-02 00:00:00"
}
const addUser = () => {
  centerAddDialogVisible.value = true
  addForm.trainIntroduce = "";
  addForm.trainType = "";
  addForm.trainPeriod = 4;
  addForm.startTime = "2000-02-02 00:00:00";
  addForm.endTime = "2000-02-02 00:00:00"
}
const onSubmitAdd = () => {
  axios.post("http://localhost:9090/training-parameter-entity/addTrainPlan", addForm).then(res => {
    ElMessage({
      message: '添加成功！',
      type: 'success'
    })
    addForm.id = res.data
    centerAddDialogVisible.value = false
    allPlansInfo.splice(0, allPlansInfo.length)
    axios.get("http://localhost:9090/training-parameter-entity/getAllPlanInfo").then(res => {
      allPlansInfo.push(...res.data)
    })
  })
}
//删除一个计划
const dialogVisible = ref(false)
const deleteId = ref(999)
const deletePlan = (index: number) => {
  deleteId.value = index
  dialogVisible.value = true
}
const deleteMakeSure = () => {
  axios.get("http://localhost:9090/training-parameter-entity/deleteTrainPlan/" + deleteId.value).then(res => {
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
  let deleteIndex = allPlansInfo.findIndex((plan) => {
    return plan.id === deleteId.value
  })
  allPlansInfo.splice(deleteIndex,1)
}
//update
const centerUpdateDialogVisible = ref(false)
const editPlanInfo = (res:trainInfo) =>{
  updateForm.id=res.id;updateForm.founder=res.founder;updateForm.mechanismId=res.mechanismId;updateForm.trainIntroduce=res.trainIntroduce;
  updateForm.trainStatus=res.trainStatus;updateForm.trainType=res.trainType;updateForm.trainPeriod=res.trainPeriod;updateForm.startTime=res.startTime;
  updateForm.endTime=res.endTime
  centerUpdateDialogVisible.value=true
  allPlansInfo.splice(0,allPlansInfo.length)
  axios.get("http://localhost:9090/training-parameter-entity/getAllPlanInfo").then(res => {
    allPlansInfo.push(...res.data)
  })
}
const updateForm:trainInfo = reactive({
  id: 1,
  founder: 1,
  mechanismId: 1,
  trainIntroduce: "",
  trainType: "",
  trainPeriod: 4,
  startTime: "2000-02-02 00:00:00",
  endTime: "2000-02-02 00:00:00",
  trainStatus: "",
})
const onSubmitUpdate = () => {
  axios.post("http://localhost:9090/training-parameter-entity/updateTrainPlan",updateForm).then(res=>{
    ElMessage({
      message:'update successful',
      type:'success'
    })
  })
  centerUpdateDialogVisible.value=false
}


onMounted(() => {
  axios.get("http://localhost:9090/training-parameter-entity/getAllPlanInfo").then(res => {
    allPlansInfo.push(...res.data)
  })
})
</script>

<style scoped>
.el-card {
  height: 300px;
  width: 300px;
  background-color: #fcfbfb;
  font-family: 楷体, serif;
}

.el-card:hover {
  box-shadow: 1px 2px 5px rgba(33, 13, 13, 0.97);
}

.footBtb {
  /*padding-top: 42%;*/
}

#deleteBtn {
  position: absolute;
  margin-left: 50px;
}
</style>
