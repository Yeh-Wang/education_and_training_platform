<template>
  <div class="login-box">
  <el-form :model="form" label-width="120px" :size="'default'" class="loginForm">
    <h1>Login System</h1>
    <el-form-item label="User Type">
      <el-select v-model="form.grade" placeholder="please select your identity">
        <el-option label="super administrator" value="0" />
        <el-option label="organization administrator" value="1" />
        <el-option label="regular user" value="2" />
      </el-select>
    </el-form-item>
    <el-form-item label="user name">
      <el-input
          :disabled="!form.grade"
          placeholder="Please input your username"
          v-model="form.username"
      />
    </el-form-item>
    <el-form-item label="password">
      <el-input
          :disabled="!form.grade"
          v-model="form.password"
          type="password"
          placeholder="Please input password"
          show-password
      />
    </el-form-item>
    <el-button id="okButton" type="primary" @click="onSubmit">Login</el-button>
  </el-form>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";

export default defineComponent({
  name: 'loginView',

  setup(){
    const form = ref({
      username:'',
      password:'',
      grade:null,
    })

    const onSubmit = () =>{
      axios.get("http://localhost:9090/users-info-entity/judgeLogin/"+form.value.username+','+form.value.password+','+form.value.grade).then(res=>{
        if(!isNaN(res.data)){
          ElMessage({
            message:'Login Successful!',
            type:'success'
          })
          if(form.value.grade==0){
            router.push({path:'/superAdm/'+res.data})
          }else if(form.value.grade==1){
            router.push({path:'/orgAdm/'+res.data})
          }else if(form.value.grade==2){
            router.push({path:'/regularUser/'+res.data})
          }
        }else{
          ElMessage.error(res.data)
        }
      })
    }
    return{
      form,
      onSubmit,
    }
  },
  mounted(){
    // this.$router.push({path:'/orgAdm/'+2})
  }
});
</script>

<style>
.login-box {
  height: 100%;
  width: 100%;
  margin: 0;
  /*background-image: url("../assets/Login.jpg");*/
  background-color: aqua;
  background-repeat: no-repeat;
  background-size: 100%;
  box-sizing: border-box;
  padding-top: 220px;
}
.loginForm {
  width: 380px;
  padding: 30px;
  background-color: #fff;
  opacity: 0.9;
  border-radius: 20px;
  margin: 0 auto;
  text-align: center;
}
#okButton{
  width: 360px;
  margin-left: 20px;
}
</style>