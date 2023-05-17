<template>
  <el-card v-model="now_plan"
           style="height: 400px;width: 500px;margin-left: 25%;margin-top: 10%">
    <el-row>
      <el-col :span="24">
        <span>计划编号:{{ now_plan.id }}</span>
      </el-col>
    </el-row>
    <el-divider/>
    <span>计划介绍:{{ now_plan.trainIntroduce }}</span>
    <el-divider/>
    <span>计划类型:{{ now_plan.trainType }}</span>
    <el-divider/>
    <span>计划周期:{{ now_plan.trainPeriod }} 周</span>
    <el-divider/>
    <el-row>
      <el-col :span="24">
        开始时间:{{ now_plan.startTime }}
      </el-col>
    </el-row>
    <el-divider/>
    <el-row>
      <el-col :span="24">
        <span>结束时间:{{now_plan.endTime}}</span>
      </el-col>
    </el-row>
  </el-card>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";

const plan_id = ref()
const route = useRoute()

interface planInfo {
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
const now_plan:planInfo = reactive({
  id: 1,
  founder: 1,
  mechanismId: 1,
  trainIntroduce: "",
  trainType: "",
  trainPeriod: 0,
  startTime: "",
  endTime: "",
  trainStatus: ""
})

onMounted(()=>{
  plan_id.value=route.params.plan_id
  axios.get("http://localhost:9090/training-parameter-entity/getOneByPlanId/"+plan_id.value).then(res=>{
    now_plan.id=res.data.id;now_plan.trainIntroduce=res.data.trainIntroduce;now_plan.trainType=res.data.trainType;now_plan.trainPeriod=res.data.trainPeriod;
    now_plan.startTime=res.data.startTime;now_plan.endTime=res.data.endTime;
  })
})

</script>

<style scoped>

</style>
