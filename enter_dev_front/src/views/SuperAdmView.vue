<template>
  <div class="common_layout">
    <el-container>
      <el-header style="background-color: #545c64">
        <el-row>
          <el-col :span="3">
            <div style="padding-top: 20px">
              <el-link :underline="false" href="/">教育培训平台</el-link>
            </div>
          </el-col>
          <el-col :span="17">
            <div class="grid-content ep-bg-purple"/>
          </el-col>
          <el-col :span="5">
            <div class="grid-content ep-bg-purple-light">
                  <p>Hello, {{ adm_name }}</p>
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
              background-color="#545c64"
              text-color="white"
              style="height: 680px"
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
              <span>用户信息管理</span>
            </el-menu-item>
            <el-menu-item :index="three_url">
              <el-icon>
                <setting/>
              </el-icon>
              <span>机构培训计划管理</span>
            </el-menu-item>
            <el-menu-item :index="four_url">
              <el-icon>
                <setting/>
              </el-icon>
              <span>资源库管理</span>
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
const adm_name = ref("") //登录用户名
const route = useRoute()  //定义路由接收路由传参
//导航布局
const one_url = ref("")  //路由：机构信息管理
const two_url = ref("")  //路由：学员信息管理
const three_url = ref("")  //路由：机构培训计划管理
const four_url = ref("")

onMounted(() => {
  adm_id.value = route.params.id
  axios.get("http://localhost:9090/users-info-entity/getUserInfoById/" + adm_id.value).then(res => {
    adm_name.value = res.data.userName
  })
  one_url.value='/allOrgInfoManage'
  two_url.value='/allUserInfoManage'
  three_url.value='/allPlanInfoManage'
  four_url.value = '/allResourceManage'
});
</script>

<style>
.common_layout {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.el-container {
  margin: 0;
  padding: 0;
}

.el-aside {
  /*background-color: red;*/
  border-right: 2px solid var(--el-border-color-dark);
  margin: 0;
  padding: 0;
}

.el-header {
  /*background-color: aqua;*/
  border-bottom: 3px solid var(--el-border-color);
  margin: 0;
  padding: 0;
}

.el-main {
  margin: 0;
  padding: 0;
}
</style>
