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
              <span>机构信息管理</span>
            </el-menu-item>
            <el-menu-item :index="two_url">
              <el-icon>
                <Document/>
              </el-icon>
              <span>学员信息管理</span>
            </el-menu-item>
            <el-menu-item :index="three_url">
              <el-icon>
                <setting/>
              </el-icon>
              <span>机构培训计划管理</span>
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
import {defineComponent, onBeforeMount, onMounted, ref} from 'vue';
import axios from "axios";
import {useRoute} from "vue-router";
import router from "@/router";

const adm_id = ref()   //登录用户的id
const adm_name = ref("") //登录用户名
const route = useRoute()  //定义路由接收路由传参
const mechanism_id = ref()  //登陆用户所属机构

const backBack = () =>{
  router.push("/")
}
//导航布局
const one_url = ref("")  //路由：机构信息管理
const two_url = ref("")  //路由：学员信息管理
const three_url = ref("")  //路由：机构培训计划管理

onMounted(() => {
  adm_id.value = route.params.id
  axios.get("http://localhost:9090/users-info-entity/getUserInfoById/" + adm_id.value).then(res => {
    mechanism_id.value = res.data.userMechanism
    adm_name.value = res.data.userName
    one_url.value = "/orgInfoManage/" + mechanism_id.value + "/" + adm_id.value
    two_url.value = "/stuInfoManage/" + mechanism_id.value
    three_url.value = "/orgTrainingManage/" + mechanism_id.value+"/"+adm_id.value
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
