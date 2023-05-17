<template>
  <div>
    <el-row>
      <el-col :span="2"></el-col>
      <el-col :span="2"/>
      <el-col :span="12">
<!--        <el-button size="default" @click="three_url">试题资源</el-button>-->
<!--        <el-divider direction="vertical"/>-->
<!--        <el-button size="default" @click="four_url">视频资源</el-button>-->
      </el-col>
      <el-col :span="5"/>
      <el-col :span="3">
        <el-tooltip
            content="上传资源到资源库"
            placement="top">
          <el-button type="success" icon="Plus" @click="uploadResource">resource</el-button>
        </el-tooltip>
      </el-col>
    </el-row>
  </div>

  <!--  预览视频窗口-->
  <el-dialog v-model="videoDialog" :title="nowVideoName" @close="closeVideoDialog" align-center>
    <vue3VideoPlay
        id="videoInfo"
        v-bind="videoOptions"
    />
  </el-dialog>
  <!--  试题资源预览-->
  <el-dialog v-model="chooseDetail" align-center>
    <el-card v-model="chooseView" style="height: 100%;width: 100%">
      <el-row>
        <el-col :span="24">
          {{ chooseView.id }}.({{ chooseView.type }}) {{ chooseView.question }} ({{ chooseView.score }}分)
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <p>A:{{ chooseView.answerA }}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <p>B:{{ chooseView.answerB }}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <p>C:{{ chooseView.answerC }}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <p>D:{{ chooseView.answerD }}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <p style="border-top: 1px solid #efecec;font-size: small; word-wrap: break-word">
            正确答案:{{ chooseView.answerCorrect }}。解析:{{ chooseView.analysis }}
          </p>
        </el-col>
      </el-row>
    </el-card>
  </el-dialog>
  <el-dialog v-model="judgeDetail" align-center>
    <el-card v-model="judgeView" style="height: 100%;width: 100%">
      <el-row>
        <el-col :span="24">
          {{ judgeView.id }}.({{ judgeView.type }}) {{ judgeView.question }} ({{ judgeView.score }}分)
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <p style="border-top: 1px solid #efecec;font-size: small; word-wrap: break-word">
            正确答案:{{ judgeView.answerCorrect }}。解析:{{ judgeView.analysis }}
          </p>
        </el-col>
      </el-row>
    </el-card>
  </el-dialog>
  <!--    视频删除确认框-->
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
  <!--    选择题删除确认框-->
  <el-dialog
      v-model="chooseDialogVisible"
      title="Tips"
      width="30%"
  >
    <span>Are you sure?</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="chooseDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="deleteChooseMakeSure">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
  <!--    判断题删除确认框-->
  <el-dialog
      v-model="judgeDialogVisible"
      title="Tips"
      width="30%"
  >
    <span>Are you sure?</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="judgeDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="deleteJudgeMakeSure">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>

  <div>
    <!--      该培训计划资源-->
    <el-row>
      <el-col :span="24">
        <el-table :data="allVideoInfo" height="295" stripe
                  :cell-style="{border:'1px solid var(--el-border-color)'}"
                  style="width: 100%;border-bottom: 2px solid #ccc6c6">
          <el-table-column label="资源编号" prop="id"/>
          <el-table-column label="名称" prop="videoName"/>
          <!--          <el-table-column label="资源类型" prop="videoType"/>-->
          <el-table-column label="视频资源总时长" prop="videoTotalTime"/>
          <el-table-column align="right">
            <template #default="scope">
              <el-button circle size="small" type="primary" icon="VideoPlay" @click="videoShow(scope.$index, scope.row)" />
              <el-button
                  circle
                  size="small"
                  type="danger"
                  icon="Close"
                  @click="videoDelete(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-table :data="filterChooseData" height="310" stripe
                  :cell-style="{border:'1px solid var(--el-border-color)'}"
                  style="width: 100%;border-right: 1px solid #b6b0b0">
          <el-table-column label="试题编号" prop="id"/>
          <el-table-column label="试题类型" prop="type"/>
          <el-table-column label="试题内容" prop="question"/>
          <el-table-column align="right">
            <template #header>
              <el-input v-model="searchChoose" size="small" placeholder="Type to search"/>
            </template>
            <template #default="scope">
              <el-button
                  circle
                  size="small"
                  type="primary"
                  icon="More"
                  @click="chooseDetails(scope.$index, scope.row)"/>
              <el-button
                  circle
                  size="small"
                  type="danger"
                  icon="Close"
                  @click="chooseDelete(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12">
        <el-table :data="filterJudgeData" height="310" stripe
                  :cell-style="{border:'1px solid var(--el-border-color)'}"
                  style="width: 100%;">
          <el-table-column label="试题编号" prop="id"/>
          <el-table-column label="试题类型" prop="type"/>
          <el-table-column label="试题内容" prop="question"/>
          <el-table-column align="right">
            <template #header>
              <el-input v-model="searchJudge" size="small" placeholder="Type to search"/>
            </template>
            <template #default="scope">
              <el-button
                  circle
                  size="small"
                  type="primary"
                  icon="More"
                  @click="judgeDetails(scope.$index, scope.row)"/>
              <el-button
                  circle
                  size="small"
                  type="danger"
                  icon="Close"
                  @click="judgeDelete(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>

  <!--  上传资源窗口-->
  <el-dialog v-model="uploadDialog" style="height: 600px;width: 700px" title="Upload resource" align-center>
    <el-row>
      <el-col :span="4" />
      <el-col :span="16">
        <el-button size="small" @click="addChooseInfo">添加选择题资源</el-button>
        <el-divider direction="vertical"/>
        <el-button size="small" @click="addJudgeInfo">添加判断题资源</el-button>
        <el-divider direction="vertical"/>
        <el-button size="small" @click="addVideoInfo">上传视频资源</el-button>
      </el-col>
      <el-col :span="4" />
    </el-row>
    <el-row>
      <el-col :span="24"></el-col>
      <el-upload
          v-model:file-list="uploadVideo"
          v-show="show_upload"
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
      <el-card v-show="show_choose">
        <el-form :inline="true" :model="uploadChoose" label-width="120px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="问题">
                <el-input type="textarea" v-model="uploadChoose.question"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="类型">
                <el-input v-model="uploadChoose.type"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="选项A">
                <el-input v-model="uploadChoose.answerA"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="选项B">
                <el-input v-model="uploadChoose.answerB"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="选项C">
                <el-input v-model="uploadChoose.answerC"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="选项D">
                <el-input v-model="uploadChoose.answerD"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="正确答案">
                <el-input v-model="uploadChoose.answerCorrect"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="分数">
                <el-input v-model="uploadChoose.score"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="解析">
            <el-input type="textarea" v-model="uploadChoose.analysis"/>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card v-show="show_judge">
        <el-form :inline="true" :model="uploadJudge" label-width="120px">
          <el-form-item label="问题">
            <el-input type="textarea" v-model="uploadJudge.question"/>
          </el-form-item>
          <el-form-item label="类型">
            <el-input v-model="uploadJudge.type"/>
          </el-form-item>
          <el-form-item label="正确答案">
            <el-input v-model="uploadJudge.answerCorrect"/>
          </el-form-item>
          <el-form-item label="解析">
            <el-input type="textarea" v-model="uploadJudge.analysis"/>
          </el-form-item>
          <el-form-item label="分数">
            <el-input v-model="uploadJudge.score"/>
          </el-form-item>
        </el-form>
      </el-card>
    </el-row>
    <el-row>
      <el-col :span="20" />
      <el-col :span="4">
        <el-button type="success" size="default" @click="addResSubmit">确认上传</el-button>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script lang="ts" setup>
import axios from "axios";
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessage, UploadUserFile} from "element-plus";

interface videoInfo {
  id: number;
  founder: number;
  videoName: string;
  videoType: string;
  videoUrl: string;
  videoTotalTime: string;
}

interface chooseInfo {
  id: number;
  founder: number;
  type: string;
  question: string;
  answerA: string;
  answerB: string;
  answerC: string;
  answerD: string;
  answerCorrect: string;
  analysis: string;
  score: number
}

interface judgeInfo {
  id: number;
  founder: number;
  type: string;
  question: string;
  answerCorrect: string;
  analysis: string;
  score: number
}

//------------------------------------------------------------------------------------------------------资源对应的表格的数据
const allVideoInfo: videoInfo[] = reactive([])
const allChooseInfo: chooseInfo[] = reactive([])
const allJudgeInfo: judgeInfo[] = reactive([])

//上传资源到库
const uploadDialog = ref(false)
const uploadResource = () =>{
  uploadDialog.value=true;
  uploadVideo.value.splice(0,uploadVideo.value.length)
}
const indexUp = ref(0)  //1:choose  2:judge  3:video
const show_upload = ref(true)
const show_choose = ref(false)
const show_judge = ref(false)
const addChooseInfo = () =>{
  show_choose.value=true
  show_upload.value=false
  show_judge.value=false
  indexUp.value=1
}
const addJudgeInfo = () =>{
  show_choose.value=false
  show_judge.value=true
  show_upload.value=false
  indexUp.value=2
}
const addVideoInfo = () =>{
  show_upload.value=true
  show_choose.value=false
  show_judge.value=false
  indexUp.value=3
  uploadVideo.value.splice(0,uploadVideo.value.length)
}

const uploadVideo = ref<UploadUserFile[]>([
  {
    name:"",
    url:""
  }
])
const videoNames:string[] = reactive([])
const uploadChoose:chooseInfo = reactive({
  id: 1,
  founder: 1,
  type: "",
  question: "",
  answerA: "",
  answerB: "",
  answerC: "",
  answerD: "",
  answerCorrect: "",
  analysis: "",
  score: 1
})
const uploadJudge:judgeInfo = reactive({
  id: 1,
  founder: 1,
  type: "",
  question: "",
  answerCorrect: "",
  analysis: "",
  score: 2
})

const addResSubmit = () =>{
  if(indexUp.value===1){
    uploadChoose.founder=1
    axios.post("http://localhost:9090/choose-question-bank-entity/addChooseInfo",uploadChoose).then(res=>{
      if(res.data===1){
        ElMessage({
          message:'upload successful',
          type:'success'
        })
      }else{
        ElMessage({
          message:'upload failed',
          type:'error'
        })
      }
    })
    uploadChoose.id=1;uploadChoose.founder=1;uploadChoose.type="";uploadChoose.question="";uploadChoose.answerA="";uploadChoose.answerB="";
    uploadChoose.answerC="";uploadChoose.answerD="";uploadChoose.answerCorrect="";uploadChoose.analysis="";uploadChoose.score=1;
  }
  if(indexUp.value===2){
    uploadJudge.founder=1
    axios.post("http://localhost:9090/judge-question-bank-entity/addJudgeInfo",uploadJudge).then(res=>{
      if(res.data===1){
        ElMessage({
          message:'upload successful',
          type:'success'
        })
      }else{
        ElMessage({
          message:'upload failed',
          type:'error'
        })
      }
    })
    uploadJudge.id=1;uploadJudge.founder=1;uploadJudge.type="";uploadJudge.question="";uploadJudge.answerCorrect="";uploadJudge.analysis="";uploadJudge.score=1;
  }
  if(indexUp.value===3){
    for(let i=0;i<uploadVideo.value.length;i++){
      videoNames.push(uploadVideo.value[i].name)
    }
    axios.post("http://localhost:9090/video-resource-bank-entity/addSomeVideoInfo/"+1,videoNames).then(res =>{
      ElMessage({
        message:'upload successful',
        type:'success'
      })
    })
    videoNames.splice(0,videoNames.length)
    uploadVideo.value.splice(0,uploadVideo.value.length)
  }
}
//------------------------------------------------------------------------------------------------------<!--   视频预览-->
const nowVideoName = ref("")
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
const closeVideoDialog = () => {
  videoOptions.currentTime = 0
}
//------------------------------------------------------------------------------------------------------该培训计划资源管理

//视频
const videoShow = (index: number, videoInfo: videoInfo) => {  //视频预览按钮
  videoOptions.title = videoInfo.videoName
  videoOptions.src = videoInfo.videoUrl
  nowVideoName.value = videoInfo.videoName
  videoDialog.value = true
}
const dialogVisible = ref(false)
const videoDelete = (index: number, videoInfo: videoInfo) => {
  dialogVisible.value = true
  deleteVideoId.value = videoInfo.id
}
const deleteVideoId = ref(999)
const deleteMakeSure = () => {
  axios.get("http://localhost:9090/video-resource-bank-entity/deleteVideoInfo/" + deleteVideoId.value ).then(res => {
    ElMessage({
      message: '删除成功！',
      type: 'success'
    })
    dialogVisible.value = false
    let deleteIndex_1 = allVideoInfo.findIndex((videoInfo) => {
      return videoInfo.id === deleteVideoId.value
    })
    allVideoInfo.splice(deleteIndex_1, 1)
  })
}
//选择试题
const chooseView: chooseInfo = reactive({
  id: 1,
  founder: 1,
  type: "",
  question: "",
  answerA: "",
  answerB: "",
  answerC: "",
  answerD: "",
  answerCorrect: "",
  analysis: "",
  score: 1
})
const chooseDetail = ref(false)
const chooseDetails = (index: number, chooseEntity: chooseInfo) => {
  chooseView.id = chooseEntity.id;
  chooseView.question = chooseEntity.question;
  chooseView.score = chooseEntity.score;
  chooseView.answerA = chooseEntity.answerA;
  chooseView.answerB = chooseEntity.answerB;
  chooseView.answerC = chooseEntity.answerC;
  chooseView.answerD = chooseEntity.answerD;
  chooseView.answerCorrect = chooseEntity.answerCorrect;
  chooseView.analysis = chooseEntity.analysis;
  chooseView.type = chooseEntity.type
  chooseDetail.value = true
}
const chooseDelete = (index: number, chooseEntity: chooseInfo) => {
  chooseDialogVisible.value = true;
  deleteChooseId.value = chooseEntity.id
}
const searchChoose = ref('')
const filterChooseData = computed(() =>
    allChooseInfo.filter(
        (data) =>
            !searchChoose.value ||
            data.type.toLowerCase().includes(searchChoose.value.toLowerCase())
    )
)
const chooseDialogVisible = ref(false)
const deleteChooseId = ref(999)
const deleteChooseMakeSure = () => {
  axios.get("http://localhost:9090/choose-question-bank-entity/deleteChooseInfo/" + deleteChooseId.value).then(res => {
    ElMessage({
      message: '删除成功！',
      type: 'success'
    })
    chooseDialogVisible.value = false
    let deleteIndex_2 = allChooseInfo.findIndex((chooseInfo) => {
      return chooseInfo.id === deleteChooseId.value
    })
    allChooseInfo.splice(deleteIndex_2, 1)
  })
}

//判断试题
const judgeView: judgeInfo = reactive({
  id: 1,
  founder: 1,
  type: "",
  question: "",
  answerCorrect: "",
  analysis: "",
  score: 2
})
const judgeDetail = ref(false)
const judgeDetails = (index: number, judgeEntity: judgeInfo) => {
  judgeView.id = judgeEntity.id;
  judgeView.type = judgeEntity.type;
  judgeView.question = judgeEntity.question;
  judgeView.score = judgeEntity.score;
  judgeView.answerCorrect = judgeEntity.answerCorrect;
  judgeView.analysis = judgeEntity.analysis;
  judgeDetail.value = true
}
const judgeDelete = (index: number, judgeEntity: judgeInfo) => {
  judgeDialogVisible.value = true;
  deleteJudgeId.value = judgeEntity.id
}
const searchJudge = ref('')
const filterJudgeData = computed(() =>
    allJudgeInfo.filter(
        (data) =>
            !searchJudge.value ||
            data.type.toLowerCase().includes(searchJudge.value.toLowerCase())
    )
)
const judgeDialogVisible = ref(false)
const deleteJudgeId = ref(999)
const deleteJudgeMakeSure = () => {
  axios.get("http://localhost:9090/judge-question-bank-entity/deleteJudgeInfo/"+ deleteJudgeId.value).then(res => {
    ElMessage({
      message: '删除成功！',
      type: 'success'
    })
    judgeDialogVisible.value = false
    let deleteIndex_3 = allJudgeInfo.findIndex((judgeInfo) => {
      return judgeInfo.id === deleteJudgeId.value
    })
    allJudgeInfo.splice(deleteIndex_3, 1)
  })
}




onMounted(() => {
  axios.get("http://localhost:9090/video-resource-bank-entity/getAllVideoInfo").then(res => {
    allVideoInfo.splice(0, allVideoInfo.length)
    allVideoInfo.push(...res.data)
  })
  axios.get("http://localhost:9090/choose-question-bank-entity/getAllChooseInfo").then(res => {
    allChooseInfo.splice(0, allChooseInfo.length)
    allChooseInfo.push(...res.data)
  })
  axios.get("http://localhost:9090/judge-question-bank-entity/getAllJudgeInfo").then(res => {
    allJudgeInfo.splice(0, allJudgeInfo.length)
    allJudgeInfo.push(...res.data)
  })
})
</script>

<style scoped>

</style>
