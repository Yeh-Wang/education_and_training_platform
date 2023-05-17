<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="3">
            <div style="padding-top: 20px">
              <el-link :underline="false" href="/">教育培训平台</el-link>
            </div>
          </el-col>
          <el-col :span="17">
            <div class="grid-content ep-bg-purple"/>
          </el-col>
          <el-col :span="4">
            <div class="grid-content ep-bg-purple-light">
              <el-row>
                <el-col :span="20" >
                  <p>Hello, {{ adm_name }}</p>
                </el-col>
                <el-col :span="4" >
                  <el-tooltip
                      class="box-item"
                      effect="dark"
                      content="退出"
                      placement="bottom"
                  >
                  <el-button circle type="danger" icon="Close" @click="backBack" />
                  </el-tooltip>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu
              :default-active="one_url"
              class="el-menu-vertical-demo"
              router
          >
            <el-menu-item :index="one_url">
              <el-icon>
                <Menu/>
              </el-icon>
              <span>计划信息查看</span>
            </el-menu-item>
            <el-menu-item :index="two_url">
              <el-icon>
                <Document/>
              </el-icon>
              <span>计划资源学习</span>
            </el-menu-item>
            <el-menu-item :index="three_url">
              <el-icon>
                <setting/>
              </el-icon>
              <span>考试界面</span>
            </el-menu-item>
            <el-menu-item :index="four_url">
              <el-icon>
                <Document/>
              </el-icon>
              <span>个人学习信息</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <RouterView/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import router from "@/router";

const backBack = () =>{
  router.push("/")
}

const adm_id = ref()   //登录用户的id
const plan_id = ref() //该用户对应的计划编号
const adm_name = ref("") //登录用户名
const route = useRoute()  //定义路由接收路由传参
const mechanism_id = ref()  //登陆用户所属机构
const one_url = ref("/")
const two_url = ref("/")
const three_url = ref("/")
const four_url = ref("/")

onMounted(() => {
  adm_id.value = route.params.id
  axios.get("http://localhost:9090/users-info-entity/getUserInfoById/" + adm_id.value).then(res => {
    mechanism_id.value = res.data.userMechanism
    adm_name.value = res.data.userName
    axios.get("http://localhost:9090/training-parameter-entity/getOneByMechanismId/"+mechanism_id.value).then(res=>{
      plan_id.value=res.data
      one_url.value="/planInfoView/"+plan_id.value
      two_url.value="/planResourceView/"+adm_id.value+"/"+plan_id.value
      three_url.value="/userExamView/"+adm_id.value+"/"+plan_id.value
      four_url.value="/personalInfoView/"+adm_id.value+"/"+plan_id.value
    })
  })
});
</script>

<style>
.common-layout {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

/*.el-container {*/
/*  height: 100%;*/
/*}*/

.el-aside {
  /*background-color: red;*/
  border-right: 2px solid var(--el-border-color-dark);
}

.el-header {
  /*background-color: aqua;*/
  border-bottom: 3px solid var(--el-border-color);
}

/*.el-main {*/
/*  background-color: blanchedalmond;*/
/*}*/
</style>
