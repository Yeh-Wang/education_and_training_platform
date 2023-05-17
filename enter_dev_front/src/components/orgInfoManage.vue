<template>
  <div>
    <el-row>
      <el-col :span="2"/>
      <el-col :span="4"/>
      <el-col :span="10">
        <el-button size="default" @click="one_url">机构信息</el-button>
        <el-divider direction="vertical"/>
        <el-button size="default" @click="three_url">机构管理员信息</el-button>
<!--        <el-divider direction="vertical"/>-->
<!--        <el-button size="default" @click="two_url">机构资源管理</el-button>-->
      </el-col>
      <el-col :span="4"/>
      <el-col :span="2"/>
      <el-col :span="2">
        <el-button text type="primary" @click="showUpdate">Edit My Info</el-button>
      </el-col>
    </el-row>
  </div>

  <!--    Update a user Information-->
  <el-dialog
      v-model="centerUpdateDialogVisible"
      title="Update your Information"
      width="30%"
      align-center
  >
    <el-form :model="updateForm" label-width="120px">
      <el-form-item label="姓名">
        <el-input v-model="updateForm.realName"/>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="updateForm.userSexy" placeholder="please select your zone">
          <el-option label="man" value="man"/>
          <el-option label="woman" value="woman"/>
          <el-option label="other" value="other"/>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-select-v2
            v-model="updateForm.userAge"
            placeholder="年龄"
            :options="options"
        />
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="updateForm.userName"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="updateForm.userPwd"/>
      </el-form-item>
      <el-form-item label="Mobile Phone">
        <el-input maxlength="11" show-word-limit v-model="updateForm.userPhone"/>
      </el-form-item>
      <el-form-item label="Address">
        <el-input v-model="updateForm.userAddress"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitUpdate">Submit</el-button>
        <el-button @click="updateClose">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <!--  预览视频窗口-->
  <el-dialog v-model="videoDialog" align-center>
    <vue3VideoPlay
        v-bind="videoOptions"
    />
  </el-dialog>

  <div>
    <el-card v-show="show_1" v-model="organizationInfo" style="height: 400px;width: 500px;margin-left: 25%;margin-top: 10%">
      <el-row>
        <el-col :span="24">
          <span>机构名称:{{organizationInfo.orgName}}</span>
        </el-col>
      </el-row>
      <el-divider />
      <span>机构类型:{{organizationInfo.orgType}}</span>
      <el-divider />
      <el-row>
        <el-col :span="24">
          机构描述:{{organizationInfo.orgType}}
        </el-col>
      </el-row>
      <el-divider />
      <el-row>
        <el-col :span="24">
          <span>机构简介视频:</span>
          <el-divider direction="vertical"/>
          <el-button circle type="success" size="small" icon="VideoPlay" @click="showVideo"/>
          <el-divider direction="vertical"/>
          <el-button circle type="primary" size="small" icon="Edit" @click="editVideo"/>
        </el-col>
      </el-row>
    </el-card>
    <el-table v-show="show_3" :data="allUserInfo" height="550" stripe :cell-style="{border:'1px solid var(--el-border-color)'}"
              style="width: 100%">
      <el-table-column label="用户编号" prop="userId"/>
      <el-table-column label="姓名" prop="realName"/>
      <el-table-column label="用户名" prop="userName"/>
      <el-table-column label="年龄" prop="userAge"/>
      <el-table-column label="联系电话" prop="userPhone"/>
      <el-table-column label="住址" prop="userAddress"/>
    </el-table>
  </div>
  <!--  更改视频资源窗口-->
  <el-dialog v-model="uploadDialog" style="height: 600px;width: 700px" title="Upload resource" align-center>
  <el-upload
      v-model:file-list="uploadVideo"
      class="upload-demo"
      drag
      action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
      multiple
      :auto-upload="false"
      accept=".avi, .mov, .mp4, .flv"
  >
    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
    <div class="el-upload__text">
      Drop file here or <em>click to upload</em>
    </div>
  </el-upload>
    <div>
      <el-button @click="okBtn" type="primary">submit</el-button>
    </div>
  </el-dialog>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage, UploadUserFile} from "element-plus";

const videoDialog = ref(false)
const videoOptions = reactive({
  width: "100%", //播放器宽度
  height: "100%", //播放器高度
  color: "#409eff", //主题色
  title: "", //视频名称
  src: "",//视频源
  muted: false, //静音
  webFullScreen: false,
  speedRate: ["0.75", "1.0", "1.25", "1.5", "2.0"], //播放倍速
  autoPlay: false, //自动播放
  loop: false, //循环播放
  mirror: false, //镜像画面
  ligthOff: false, //关灯模式
  volume: 0.3, //默认音量大小
  control: true, //是否显示控制
  currentTime: 0,
  controlBtns: [
    "audioTrack",
    "quality",
    "speedRate",
    "volume",
    "setting",
    "pip",
    "pageFullScreen",
    "fullScreen",
  ], //显示所有按钮,
})

const uploadDialog = ref(false)
const showVideo = () =>{
  videoDialog.value=true
  videoOptions.src=organizationInfo.displayContent
}

const uploadVideo = ref<UploadUserFile[]>([
  {
    name:"",
    url:""
  }
])
const videoNames:string[] = reactive([])
const editVideo = () =>{
  uploadDialog.value=true
  uploadVideo.value.splice(0,uploadVideo.value.length)
}
const okBtn = () =>{
  videoNames.push(uploadVideo.value[0].name)
  organizationInfo.displayContent="http://localhost:9090/yeh/"+videoNames[0]
  axios.post("http://localhost:9090/organization-info-entity/updateOrgInfo",organizationInfo).then(res=>{
    ElMessage({
      message:'Edit successful',
      type:'success'
    })
  })
  videoNames.splice(0,videoNames.length)
  uploadVideo.value.splice(0,uploadVideo.value.length)
  uploadDialog.value=false
}

interface orgInfo {
  id: number;
  orgName: string;
  orgType: string;
  description: string;
  displayContent:string
}
interface userInfo{
  userId: number;
  userGrade: number;
  userMechanism: number;
  realName: string;
  userName: string;
  userPwd: string;
  userSexy: string;
  userAge: string;
  userPhone: string;
  userAddress: string;
}

const mechanism_id = ref(); //接收路由参数（登录用户所属的机构编号）
const adm_id = ref()
const route = useRoute()

const allUserInfo:userInfo[] = reactive([])
const organizationInfo:orgInfo = reactive({
  id: 1,
  orgName: "",
  orgType: "",
  description: "",
  displayContent:""
})

const show_1 = ref(true)
const show_3 = ref(false)
const one_url = () =>{
  show_1.value=true
  show_3.value=false
}
const three_url = () =>{
  show_1.value=false
  show_3.value=true
  allUserInfo.splice(0,allUserInfo.length)
  axios.get("http://localhost:9090/users-info-entity/getUsersInfoByOrgId/" + mechanism_id.value + ',' + 1).then(res => {
    allUserInfo.push(...res.data)
  })
}

//update a user information
const options = Array.from({length: 100}).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
const centerUpdateDialogVisible = ref(false)
const updateForm: userInfo = reactive({
  userId: 1,
  userGrade: 1,
  userMechanism: 1,
  realName: "",
  userName: "",
  userPwd: "",
  userSexy: "",
  userAge: "",
  userPhone: "",
  userAddress: ""
})
const showUpdate = () => {
  centerUpdateDialogVisible.value = true
  let id = ref(999)
  id.value=adm_id.value
  let updateIndex_1 = ref(999)
  for(let i=0;i<allUserInfo.length;i++){
    if(allUserInfo[i].userId == id.value){
      updateIndex_1.value = i;
      break;
    }
  }
  updateForm.userId = allUserInfo[updateIndex_1.value].userId;updateForm.userMechanism = allUserInfo[updateIndex_1.value].userMechanism;updateForm.userGrade = allUserInfo[updateIndex_1.value].userGrade
  updateForm.realName=allUserInfo[updateIndex_1.value].realName;updateForm.userSexy=allUserInfo[updateIndex_1.value].userSexy;updateForm.userAge=allUserInfo[updateIndex_1.value].userAge;updateForm.userName=allUserInfo[updateIndex_1.value].userName;
  updateForm.userPwd=allUserInfo[updateIndex_1.value].userPwd;updateForm.userPhone=allUserInfo[updateIndex_1.value].userPhone;updateForm.userAddress=allUserInfo[updateIndex_1.value].userAddress
}
const onSubmitUpdate = () => {
  axios.post("http://localhost:9090/users-info-entity/updateUserInfo", updateForm).then(res => {
    if (res.data === 1) {
      ElMessage({
        message: '更新成功！',
        type: 'success'
      })
      allUserInfo.splice(0,allUserInfo.length)
      axios.get("http://localhost:9090/users-info-entity/getUsersInfoByOrgId/" + mechanism_id.value + ',' + 1).then(res => {
        allUserInfo.push(...res.data)
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

onMounted(() => {
  mechanism_id.value = route.params.id
  adm_id.value=route.params.adm_id
  axios.get("http://localhost:9090/organization-info-entity/getOrgInfoById/"+mechanism_id.value).then(res=>{
    organizationInfo.id=res.data.id;organizationInfo.orgName=res.data.orgName;organizationInfo.orgType=res.data.orgType;organizationInfo.description=res.data.description
    organizationInfo.displayContent=res.data.displayContent
  })
})
</script>

<style scoped>

</style>
