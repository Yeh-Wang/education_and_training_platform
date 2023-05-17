<template>

  <el-row>
    <el-col :span="12">
      <el-table :data="allLearnInfo"
                stripe
                :cell-style="{border:'1px solid var(--el-border-color)'}"
                border
                style="width: 100%">
        <el-table-column label="编号" prop="id" width="180" />
        <el-table-column label="视频编号" prop="videoId" width="180" />
        <el-table-column label="视频进度" prop="videoBreakpoint" />
      </el-table>
    </el-col>
    <el-col :span="1"></el-col>
    <el-col :span="11">
      <el-card v-model="userGrade"
               style="height: 400px;width: 400px;">
        <el-row>
          <el-col :span="24">
            <span>编号:{{ userGrade.id }}</span>
          </el-col>
        </el-row>
        <el-divider/>
        <span>分数:{{ userGrade.score }}</span>
        <el-divider/>
        <span>是否完成:{{ userGrade.isComplete }}</span>
      </el-card>
    </el-col>
  </el-row>

</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";

const plan_id = ref()
const user_id = ref()
const route = useRoute()

interface learSpace {
  id: number;
  trainId: number;
  userId: number;
  videoId: number;
  videoBreakpoint: number;
  planStatus: string;
}
interface userGradeList{
  id:number;
  userId:number;
  examPaperId:number;
  score:number;
  isComplete:string;
}

const allLearnInfo: learSpace[] = reactive([])
const userGrade:userGradeList = reactive({
  id:1,
  userId:1,
  examPaperId:1,
  score:0,
  isComplete:"uncompleted"
})

onMounted(()=>{
  user_id.value = route.params.user_id
  plan_id.value = route.params.plan_id

  axios.get("http://localhost:9090/exam-info-entity/getExamInfoByUser/"+user_id.value).then(res=>{
    userGrade.id=res.data.id;userGrade.userId=res.data.userId;userGrade.examPaperId=res.data.examPaperId;
    userGrade.score=res.data.score;userGrade.isComplete=res.data.isComplete
    console.log(userGrade)
  })
  axios.get("http://localhost:9090/learn-space-entity/getLearnByUserVideo/"+user_id.value+","+plan_id.value).then(res=>{
    allLearnInfo.splice(0,allLearnInfo.length)
    allLearnInfo.push(...res.data)
    console.log(allLearnInfo)
  })
})
</script>

<style scoped>

</style>
