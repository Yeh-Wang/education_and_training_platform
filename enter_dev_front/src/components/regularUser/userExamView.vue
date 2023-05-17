<template>
  <div>
    {{ nowTime }}
  </div>
  <el-card
      v-show="theExamInfo"
      v-model="updateExamForm"
      style="height: 400px;width: 500px;margin-left: 25%;margin-top: 10%">
    <el-row>
      <el-col :span="24">
        <span>考试编号:{{ updateExamForm.id }}  考试科目:{{ updateExamForm.examSubject }}</span>
      </el-col>
    </el-row>
    <el-divider/>
    <span>题目总数:{{ updateExamForm.questionNumber }} </span>
    <el-divider/>
    <span>选择题数:{{ updateExamForm.chooseQuestionNumber }} 判断题数:{{ updateExamForm.judgeQuestionNumber }}</span>
    <el-divider/>
    <el-row>
      <el-col :span="24">
        开始时间:{{ updateExamForm.startTime }}
      </el-col>
    </el-row>
    <el-divider/>
    <el-row>
      <el-col :span="24">
        <span>结束时间:{{ updateExamForm.endTime }}</span>
      </el-col>
    </el-row>
    <el-tooltip
        class="box-item"
        effect="light"
        content="点击进行考试"
        placement="top"
    >
      <el-button style="margin-top: 20px" type="primary" size="default" :disabled="isExam" @click="toExam">开始考试
      </el-button>
    </el-tooltip>
  </el-card>

  <el-card v-show="intoExam">
      <el-form v-for="chooseInfos in allChooseInfo">
        <span>{{ chooseInfos.question }}({{ chooseInfos.score }})</span>
        <el-form-item label="选项：">
          <el-radio-group v-model="chooseInfos.analysis" class="ml-4">
            <el-radio label="A">{{ chooseInfos.answerA }}</el-radio>
            <el-radio label="B">{{ chooseInfos.answerB }}</el-radio>
            <el-radio label="C">{{ chooseInfos.answerC }}</el-radio>
            <el-radio label="D">{{ chooseInfos.answerD }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-divider/>
      </el-form>
      <el-form v-for="judgeInfos in allJudgeInfo">
        <span>{{ judgeInfos.question }}({{ judgeInfos.score }})</span>
        <el-form-item label="选项：">
          <el-radio-group v-model="judgeInfos.analysis" class="ml-4">
            <el-radio label="对">对</el-radio>
            <el-radio label="错">错</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-divider/>
      </el-form>
    <div>
      <el-button type="success" @click="submitExam">提交考试</el-button>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import axios from "axios";
import {useRoute} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import moment from "moment";
import {ElMessage} from "element-plus";

const plan_id = ref()
const user_id = ref()
const route = useRoute()

interface userGradeList{
  id:number;
  userId:number;
  examPaperId:number;
  score:number;
  isComplete:string;
}

interface examInfo {
  id: number,
  planId: number,
  examSubject: string,
  questionNumber: number,
  chooseQuestionNumber: number,
  judgeQuestionNumber: number,
  startTime: string,
  endTime: string
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

const allChooseInfo: chooseInfo[] = reactive([]) //考题
const allJudgeInfo: judgeInfo[] = reactive([])

const isExam = ref(true)  //考试按钮是否可以点击
const theExamInfo = ref(true) //显示考试信息
const intoExam = ref(false)  //考试界面控制

const toExam = () => {
  theExamInfo.value = false
  intoExam.value = true
}

const stuScore = ref(0);
const workOutScore = () =>{
  for(let i=0 ;i<allChooseInfo.length;i++){
    if(allChooseInfo[i].analysis===allChooseInfo[i].answerCorrect){
      stuScore.value += allChooseInfo[i].score
    }
  }
  for(let i=0 ;i<allJudgeInfo.length;i++){
    if(allJudgeInfo[i].analysis===allJudgeInfo[i].answerCorrect){
      stuScore.value += allJudgeInfo[i].score
    }
  }
}
const userGrade:userGradeList = reactive({
  id:1,
  userId:1,
  examPaperId:1,
  score:0,
  isComplete:"uncompleted"
})
const submitExam = () =>{
  intoExam.value=false
  theExamInfo.value=false
  workOutScore()
  userGrade.userId=user_id.value
  userGrade.examPaperId=updateExamForm.id
  userGrade.score=stuScore.value
  userGrade.isComplete="completed"
  axios.post("http://localhost:9090/exam-info-entity/addAInfo",userGrade).then(res=>{
  })
}

const updateExamForm: examInfo = reactive({
  id: 1,
  planId: 1,
  examSubject: '',
  questionNumber: 1,
  chooseQuestionNumber: 1,
  judgeQuestionNumber: 1,
  startTime: '',
  endTime: ''
})
const flag = ref(true)
const nowTime = ref(moment().format("YYYY-MM-DD HH:mm:ss"))
setInterval(() => {
  nowTime.value = moment().format("YYYY-MM-DD HH:mm:ss")
  if (nowTime.value < updateExamForm.endTime) {
    isExam.value = false
  }
  if (nowTime.value < updateExamForm.startTime) {
    isExam.value = true
  }
  if(nowTime.value > updateExamForm.endTime){
    if(intoExam.value==flag.value){
      submitExam()
      ElMessage({
        message:"考试时间已过",
        type:"error"
      })
    }
    isExam.value = true
  }
})
onMounted(() => {
  user_id.value = route.params.user_id
  plan_id.value = route.params.plan_id
  axios.get("http://localhost:9090/training-resource-entity/getChooseByPlanId/" + plan_id.value).then(res => {
    allChooseInfo.splice(0, allChooseInfo.length)
    allChooseInfo.push(...res.data)
    for(let i=0;i<allChooseInfo.length;i++){
      allChooseInfo[i].analysis=""
    }
  })
  axios.get("http://localhost:9090/training-resource-entity/getJudgeByPlanId/" + plan_id.value).then(res => {
    allJudgeInfo.splice(0, allJudgeInfo.length)
    allJudgeInfo.push(...res.data)
    for(let i=0;i<allJudgeInfo.length;i++){
      allJudgeInfo[i].analysis=""
    }
  })
  axios.get("http://localhost:9090/exam-paper-bank-entity/getExamByPlanId/" + plan_id.value).then(res => {
    updateExamForm.id = res.data.id;
    updateExamForm.planId = res.data.planId;
    updateExamForm.examSubject = res.data.examSubject;
    updateExamForm.questionNumber = res.data.chooseQuestionNumber + res.data.judgeQuestionNumber;
    updateExamForm.chooseQuestionNumber = res.data.chooseQuestionNumber;
    updateExamForm.judgeQuestionNumber = res.data.judgeQuestionNumber;
    updateExamForm.startTime = res.data.startTime;
    updateExamForm.endTime = res.data.endTime
    if(moment().format("YYYY-MM-DD HH:mm:ss")>updateExamForm.endTime){
      userGrade.userId=user_id.value
      userGrade.examPaperId=updateExamForm.id
      axios.post("http://localhost:9090/exam-info-entity/addAInfo",userGrade).then(res=>{
      })
      ElMessage({
        message:"考试时间已过",
        type:"error"
      })
      intoExam.value=false
    }
  })
})
</script>

<style scoped>

</style>
