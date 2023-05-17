<template>

  <!--  预览视频窗口-->
  <el-dialog v-model="videoDialog" :title="nowVideoName" align-center>
    <vue3VideoPlay
        id="videoInfo"
        v-bind="videoOptions"
        @pause="onPause"
        @timeupdate="onTimeupdate"
    />
  </el-dialog>

  <el-table :data="allVideoInfo" height="600" stripe
            :cell-style="{border:'1px solid var(--el-border-color)'}"
  >
    <el-table-column label="资源编号" prop="id"/>
    <el-table-column label="名称" prop="videoName"/>
    <el-table-column label="视频资源总时长" prop="videoTotalTime"/>
    <el-table-column align="right">
      <template #default="scope">
        <el-button circle size="small" type="primary" icon="VideoPlay" @click="videoShow(scope.$index, scope.row)"/>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";

const plan_id = ref()
const user_id = ref()
const route = useRoute()

interface videoInfo {
  id: number;
  founder: number;
  videoName: string;
  videoType: string;
  videoUrl: string;
  videoTotalTime: string;
}

const allVideoInfo: videoInfo[] = reactive([])

interface learSpace {
  id: number;
  trainId: number;
  userId: number;
  videoId: number;
  videoBreakpoint: number;
  planStatus: string;
}

const allLearnInfo: learSpace[] = reactive([])

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
const j = ref(0)
const videoShow = (index: number, videoInfo: videoInfo) => {  //视频预览按钮
  videoOptions.title = videoInfo.videoName
  videoOptions.src = videoInfo.videoUrl
  nowVideoName.value = videoInfo.videoName
  videoDialog.value = true
  for (j.value = 0; j.value < allLearnInfo.length; j.value++) {
    if (allLearnInfo[j.value].userId == user_id.value) {
      if (allLearnInfo[j.value].videoId == videoInfo.id) {
        videoOptions.currentTime=allLearnInfo[j.value].videoBreakpoint
        break;
      }
    }
  }
  if(j.value==allLearnInfo.length){
    j.value=0
    videoOptions.currentTime=0;
  }
  saveVideoLearn.videoId=videoInfo.id
}

const saveNowVideo = ref(0)
const saveVideoLearn = reactive({
  id: 0,
  trainId: 1,
  userId: 1,
  videoId: 1,
  videoBreakpoint: 1,
  planStatus: "uncompleted",
})

//learn space
const onPause = (ev) => {
  onTimeupdate(ev)
  saveNowVideo.value = ev.target.currentTime
  if(j.value==0){
    saveVideoLearn.trainId=plan_id.value
    saveVideoLearn.userId=user_id.value
    saveVideoLearn.videoBreakpoint=saveNowVideo.value
    axios.post("http://localhost:9090/learn-space-entity/addOneLearnInfo",saveVideoLearn).then(res=>{
    })
  }else{
    allLearnInfo[j.value].videoBreakpoint=saveNowVideo.value
    axios.post("http://localhost:9090/learn-space-entity/addOneLearnInfo",allLearnInfo[j.value]).then(res=>{
    })
  }
};
const onTimeupdate = (ev) => {
};


onMounted(() => {
  user_id.value = route.params.user_id
  plan_id.value = route.params.plan_id
  axios.get("http://localhost:9090/training-resource-entity/getVideoByPlanId/" + plan_id.value).then(res => {
    allVideoInfo.splice(0, allVideoInfo.length)
    allVideoInfo.push(...res.data)
  })
  axios.get("http://localhost:9090/learn-space-entity/getAllLearnInfo").then(res => {
    allLearnInfo.splice(0, allLearnInfo.length)
    allLearnInfo.push(...res.data)
  })
})
</script>

<style scoped>

</style>
