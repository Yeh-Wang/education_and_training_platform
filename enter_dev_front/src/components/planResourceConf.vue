<template>
  <div>
    <el-row>
      <el-col :span="2">
        <el-button type="primary" @click="backCode">back</el-button>
      </el-col>
      <el-col :span="3"/>
      <el-col :span="14">
        <el-button size="default" @click="one_url">更新计划和考试基本信息</el-button>
        <el-divider direction="vertical"/>
        <el-button size="default" @click="two_url">该培训计划资源</el-button>
        <el-divider direction="vertical"/>
        <el-button size="default" @click="three_url">配置考试试题资源</el-button>
        <el-divider direction="vertical"/>
        <el-button size="default" @click="four_url">配置计划视频资源</el-button>
      </el-col>
      <el-col :span="2"/>
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
    <!--    更新计划和考试基本信息-->
    <el-row>
      <el-col :span="11">
        <el-card v-show="show_1" style="height: 450px;width: 450px;margin-top: 5%;margin-left: 20%" >
          <el-row>
            <el-col :span="4" />
            <el-col :span="18">计划基本信息</el-col>
            <el-col :span="2">
              <el-button plain circle size="small" type="warning" @click="editInfo" icon="edit" />
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form :disabled="makeEdit" :model="updateForm" label-width="120px">
                <el-form-item label="计划描述">
                  <el-input text v-model="updateForm.trainIntroduce"/>
                </el-form-item>
                <el-form-item label="计划类型">
                  <el-input v-model="updateForm.trainType"/>
                </el-form-item>
                <el-form-item label="计划周期（周）">
                  <el-select-v2
                      v-model="updateForm.trainPeriod"
                      placeholder="计划周期"
                      :options="options"
                  />
                </el-form-item>
                <el-form-item label="开始时间">
                  <el-col :span="15">
                    <el-date-picker
                        v-model="updateForm.startTime"
                        type="datetime"
                        placeholder="Pick a date"
                        style="width: 100%"
                        value-format="YYYY-MM-DD HH:mm:ss"
                    />
                  </el-col>
                </el-form-item>
                <el-form-item label="结束时间">
                  <el-col :span="15">
                    <el-date-picker
                        v-model="updateForm.endTime"
                        type="datetime"
                        placeholder="Pick a date"
                        style="width: 100%"
                        value-format="YYYY-MM-DD HH:mm:ss"
                    />
                  </el-col>
                </el-form-item>
                <el-form-item label="计划状态">
                  <el-select v-model="updateForm.trainStatus" placeholder="please select">
                    <el-option label="进行中" value="进行中" />
                    <el-option label="已结束" value="已结束" />
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmitUpdate">Submit</el-button>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="2" />
      <el-col :span="11">
        <el-card v-show="show_1" style="height: 450px;width: 450px;margin-top: 5%;margin-left: 15%" >
          <el-row>
            <el-col :span="4" />
            <el-col :span="18">考试基本信息</el-col>
            <el-col :span="2">
              <el-button plain circle size="small" type="warning" @click="editExamInfo" icon="edit" />
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form :model="updateExamForm" label-width="120px">
                <el-form-item label="考试科目">
                  <el-input :disabled="makeExamEdit" text v-model="updateExamForm.examSubject"/>
                </el-form-item>
                <el-form-item label="试题数量">
                  <el-input disabled text v-model="updateExamForm.questionNumber"/>
                </el-form-item>
                <el-form-item label="选择题数量">
                  <el-input disabled text v-model="updateExamForm.chooseQuestionNumber"/>
                </el-form-item>
                <el-form-item label="判断题数量">
                  <el-input disabled text v-model="updateExamForm.judgeQuestionNumber"/>
                </el-form-item>
                <el-form-item label="开始时间">
                  <el-col :span="15">
                    <el-date-picker
                        :disabled="makeExamEdit"
                        v-model="updateExamForm.startTime"
                        type="datetime"
                        placeholder="Pick a date"
                        style="width: 100%"
                        value-format="YYYY-MM-DD HH:mm:ss"
                    />
                  </el-col>
                </el-form-item>
                <el-form-item label="结束时间">
                  <el-col :span="15">
                    <el-date-picker
                        :disabled="makeExamEdit"
                        v-model="updateExamForm.endTime"
                        type="datetime"
                        placeholder="Pick a date"
                        style="width: 100%"
                        value-format="YYYY-MM-DD HH:mm:ss"
                    />
                  </el-col>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmitExamUpdate">Submit</el-button>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <!--      该培训计划资源-->
    <el-row>
      <el-col :span="24">
        <el-table v-show="show_2" :data="allVideoInfo" height="295" stripe
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
        <el-table v-show="show_2" :data="filterChooseData" height="310" stripe
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
        <el-table v-show="show_2" :data="filterJudgeData" height="310" stripe
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

    <!--    配置试题资源-->
    <el-row>
      <el-col :span="11">
        <el-table v-show="show_3" :data="filterChooseData" height="300" stripe
                  :cell-style="{border:'1px solid var(--el-border-color)'}"
                  style="width: 100%;border-bottom: 1px solid #b6b0b0;">
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
                  type="success"
                  icon="Plus"
                  @click="chooseAdd(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="2">
        <p style="margin-top: 60%">
          <el-icon v-show="show_3" size="xx-large">
            <Switch/>
          </el-icon>
        </p>
      </el-col>
      <el-col :span="11">
        <el-table v-show="show_3" :data="readyAddChooseData" height="300" stripe
                  :cell-style="{border:'1px solid var(--el-border-color)'}"
                  style="width: 100%;border-bottom: 1px solid #b6b0b0;">
          <el-table-column label="试题编号" prop="id"/>
          <el-table-column label="试题类型" prop="type"/>
          <el-table-column label="试题内容" prop="question"/>
          <el-table-column align="right">
            <template #header>
              <el-button type="success" size="small" icon="Select" @click="submitChooseAdd">Submit</el-button>
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
                  @click="chooseCancel(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="11">
        <el-table v-show="show_3" :data="filterJudgeData" height="300" stripe
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
                  type="success"
                  icon="Plus"
                  @click="judgeAdd(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="2">
        <el-icon v-show="show_3" style="margin-top: 60%" size="xx-large">
          <Switch/>
        </el-icon>
      </el-col>
      <el-col :span="11">
        <el-table v-show="show_3" :data="readyAddJudgeData" height="300" stripe
                  :cell-style="{border:'1px solid var(--el-border-color)'}"
                  style="width: 100%;">
          <el-table-column label="试题编号" prop="id"/>
          <el-table-column label="试题类型" prop="type"/>
          <el-table-column label="试题内容" prop="question"/>
          <el-table-column align="right">
            <template #header>
              <el-button type="success" size="small" icon="Select" @click="submitJudgeAdd">Submit</el-button>
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
                  @click="judgeCancel(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <!--    配置视频资源-->
    <el-row>
      <el-col :span="11">
        <el-table v-show="show_4" :data="allVideoInfo" height="545" stripe
                  :cell-style="{border:'1px solid var(--el-border-color)'}"
                  style="width: 100%;">
          <el-table-column label="资源编号" prop="id"/>
          <el-table-column label="名称" prop="videoName"/>
<!--          <el-table-column label="资源类型" prop="videoType"/>-->
          <el-table-column label="视频资源总时长" prop="videoTotalTime"/>
          <el-table-column align="right">
            <template #default="scope">
              <el-button circle size="small" type="primary" icon="VideoPlay" @click="videoShow(scope.$index, scope.row)" />
              <el-button circle size="small" type="success" icon="Plus" @click="videoAdd(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="2">
        <el-icon v-show="show_4" style="margin-top: 60%" size="xx-large">
          <Switch/>
        </el-icon>
      </el-col>
      <el-col :span="11">
        <el-table v-show="show_4" :data="readyAddVideoData" height="545" stripe
                  :cell-style="{border:'1px solid var(--el-border-color)'}"
                  style="width: 100%;">
          <el-table-column label="资源编号" prop="id"/>
          <el-table-column label="名称" prop="videoName"/>
<!--          <el-table-column label="资源类型" prop="videoType"/>-->
          <el-table-column label="视频资源总时长" prop="videoTotalTime"/>
          <el-table-column align="right">
            <template #header>
              <el-button type="success" size="small" icon="Select" @click="submitVideoAdd">Submit</el-button>
            </template>
            <template #default="scope">
              <el-button circle size="small" type="primary" icon="VideoPlay" @click="videoShow(scope.$index, scope.row)" />
              <el-button circle size="small" type="danger" icon="Close" @click="videoCancel(scope.$index, scope.row)"/>
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
import {computed, onMounted, reactive, ref } from "vue";
import {useRoute} from "vue-router";
import router from "@/router";
import axios from "axios";
import {ElMessage} from "element-plus";
import type { UploadUserFile } from 'element-plus'

const backCode = () => {
  router.go(-1)
}
const plan_id = ref(); //接收路由参数（要管理的计划编号）
const admId = ref()
const route = useRoute()
const show_1 = ref(false)
const show_2 = ref(true)
const show_3 = ref(false)
const show_4 = ref(false)

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
    uploadChoose.founder=admId.value
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
    uploadJudge.founder=admId.value
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
    axios.post("http://localhost:9090/video-resource-bank-entity/addSomeVideoInfo/"+admId.value,videoNames).then(res =>{
      ElMessage({
        message:'upload successful',
        type:'success'
      })
    })
    videoNames.splice(0,videoNames.length)
    uploadVideo.value.splice(0,uploadVideo.value.length)
  }
}


interface trainInfo {
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

interface examInfo{
  id:number,
  planId:number,
  examSubject:string,
  questionNumber:number,
  chooseQuestionNumber:number,
  judgeQuestionNumber:number,
  startTime:string,
  endTime:string
}

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

//------------------------------------------------------------------------------------------------------资源对应的表格的数据
const allVideoInfo: videoInfo[] = reactive([])
const allChooseInfo: chooseInfo[] = reactive([])
const allJudgeInfo: judgeInfo[] = reactive([])

//------------------------------------------------------------------------------------------------------修改培训计划和考试信息信息
const one_url = () => {
  show_1.value = true;
  show_2.value = false;
  show_3.value = false;
  show_4.value = false
  axios.get("http://localhost:9090/training-parameter-entity/getOneByPlanId/"+plan_id.value).then(res=>{
    updateForm.id=res.data.id;updateForm.founder=res.data.founder;updateForm.mechanismId=res.data.mechanismId;updateForm.trainIntroduce=res.data.trainIntroduce;
    updateForm.trainStatus=res.data.trainStatus;updateForm.trainType=res.data.trainType;updateForm.trainPeriod=res.data.trainPeriod;updateForm.startTime=res.data.startTime;
    updateForm.endTime=res.data.endTime
  })
  axios.get("http://localhost:9090/exam-paper-bank-entity/getExamByPlanId/"+plan_id.value).then(res=>{
    updateExamForm.id=res.data.id;updateExamForm.planId=res.data.planId;updateExamForm.examSubject=res.data.examSubject;updateExamForm.questionNumber=res.data.chooseQuestionNumber+res.data.judgeQuestionNumber;
    updateExamForm.chooseQuestionNumber=res.data.chooseQuestionNumber;updateExamForm.judgeQuestionNumber=res.data.judgeQuestionNumber;updateExamForm.startTime=res.data.startTime;
    updateExamForm.endTime=res.data.endTime
  })
}
const updateForm:trainInfo = reactive({
  id: 1,
  founder: 1,
  mechanismId: 1,
  trainIntroduce: "",
  trainType: "",
  trainPeriod: 4,
  startTime: "2000-02-02 00:00:00",
  endTime: "2000-02-02 00:00:00",
  trainStatus: "",
})
const options = Array.from({length: 100}).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
const onSubmitUpdate = () => {
  axios.post("http://localhost:9090/training-parameter-entity/updateTrainPlan",updateForm).then(res=>{
    ElMessage({
      message:'update successful',
      type:'success'
    })
  })
  makeEdit.value=true
}
const makeEdit = ref(true)
const editInfo = () =>{
  makeEdit.value=false
}
const updateExamForm:examInfo = reactive({
  id:1,
  planId:1,
  examSubject:'',
  questionNumber:1,
  chooseQuestionNumber:1,
  judgeQuestionNumber:1,
  startTime:'',
  endTime:''
})
const onSubmitExamUpdate = () =>{
  axios.post("http://localhost:9090/exam-paper-bank-entity/updateExamInfo",updateExamForm).then(res=>{
    ElMessage({
      message:'update successful',
      type:'success'
    })
  })
  makeExamEdit.value=true
}
const makeExamEdit = ref(true)
const editExamInfo = () =>{
  makeExamEdit.value=false
}
//------------------------------------------------------------------------------------------------------该培训计划资源管理
const two_url = () => {
  show_2.value = true;
  show_1.value = false;
  show_3.value = false;
  show_4.value = false
  axios.get("http://localhost:9090/training-resource-entity/getVideoByPlanId/" + plan_id.value).then(res => {
    allVideoInfo.splice(0, allVideoInfo.length)
    allVideoInfo.push(...res.data)
  })
  axios.get("http://localhost:9090/training-resource-entity/getChooseByPlanId/" + plan_id.value).then(res => {
    allChooseInfo.splice(0, allChooseInfo.length)
    allChooseInfo.push(...res.data)
  })
  axios.get("http://localhost:9090/training-resource-entity/getJudgeByPlanId/" + plan_id.value).then(res => {
    allJudgeInfo.splice(0, allJudgeInfo.length)
    allJudgeInfo.push(...res.data)
  })
}
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
  axios.get("http://localhost:9090/training-resource-entity/deleteOneByPlanId/" + plan_id.value + "," + deleteVideoId.value + "," + 3).then(res => {
    // if (res.data === 1) {
    ElMessage({
      message: '删除成功！',
      type: 'success'
    })
    dialogVisible.value = false
    let deleteIndex_1 = allVideoInfo.findIndex((videoInfo) => {
      return videoInfo.id === deleteVideoId.value
    })
    allVideoInfo.splice(deleteIndex_1, 1)
    // } else {
    //   ElMessage.error("删除失败，请重新删除")
    //   dialogVisible.value = false
    // }
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
  axios.get("http://localhost:9090/training-resource-entity/deleteOneByPlanId/" + plan_id.value + "," + deleteChooseId.value + "," + 1).then(res => {
    // if (res.data === 1) {
    ElMessage({
      message: '删除成功！',
      type: 'success'
    })
    chooseDialogVisible.value = false
    let deleteIndex_2 = allChooseInfo.findIndex((chooseInfo) => {
      return chooseInfo.id === deleteChooseId.value
    })
    allChooseInfo.splice(deleteIndex_2, 1)
    // } else {
    //   ElMessage.error("删除失败，请重新删除")
    //   chooseDialogVisible.value = false
    // }
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
  axios.get("http://localhost:9090/training-resource-entity/deleteOneByPlanId/" + plan_id.value + "," + deleteJudgeId.value + "," + 2).then(res => {
    // if (res.data === 1) {
    ElMessage({
      message: '删除成功！',
      type: 'success'
    })
    judgeDialogVisible.value = false
    let deleteIndex_3 = allJudgeInfo.findIndex((judgeInfo) => {
      return judgeInfo.id === deleteJudgeId.value
    })
    allJudgeInfo.splice(deleteIndex_3, 1)
    // } else {
    //   ElMessage.error("删除失败，请重新删除")
    //   judgeDialogVisible.value = false
    // }
  })
}

//------------------------------------------------------------------------------------------------------该培训计划试题资源配置
const three_url = () => {
  readyAddChooseData.splice(0, readyAddChooseData.length)
  readyAddJudgeData.splice(0, readyAddJudgeData.length)
  show_1.value = false;
  show_2.value = false;
  show_3.value = true;
  show_4.value = false;
  axios.get("http://localhost:9090/choose-question-bank-entity/getAllChooseInfo").then(res => {
    allChooseInfo.splice(0, allChooseInfo.length)
    allChooseInfo.push(...res.data)
  })
  axios.get("http://localhost:9090/judge-question-bank-entity/getAllJudgeInfo").then(res => {
    allJudgeInfo.splice(0, allJudgeInfo.length)
    allJudgeInfo.push(...res.data)
  })
}
const readyAddChooseData: chooseInfo[] = reactive([])
const readyAddJudgeData: judgeInfo[] = reactive([])
const chooseAdd = (index: number, chooseEntity: chooseInfo) => {
  readyAddChooseData.push(allChooseInfo[index])
  allChooseInfo.splice(index, 1)
}
const judgeAdd = (index: number, chooseEntity: chooseInfo) => {
  readyAddJudgeData.push(allJudgeInfo[index])
  allJudgeInfo.splice(index, 1)
}
const chooseCancel = (index: number, chooseEntity: chooseInfo) => {
  allChooseInfo.push(readyAddChooseData[index])
  readyAddChooseData.splice(index, 1)
}
const judgeCancel = (index: number, chooseEntity: chooseInfo) => {
  allJudgeInfo.push(readyAddJudgeData[index])
  readyAddJudgeData.splice(index, 1)
}
const submitChooseAdd = () => {
  console.log(readyAddChooseData)
  axios.post("http://localhost:9090/training-resource-entity/saveChooseResourceInfo/" + plan_id.value, readyAddChooseData).then(res => {
    ElMessage({
      message: 'Add Successful',
      type: 'success'
    })
  })
  readyAddChooseData.splice(0, readyAddChooseData.length)
}
const submitJudgeAdd = () => {
  axios.post("http://localhost:9090/training-resource-entity/saveJudgeResourceInfo/" + plan_id.value, readyAddJudgeData).then(res => {
    ElMessage({
      message: 'Add Successful',
      type: 'success'
    })
  })
  readyAddJudgeData.splice(0, readyAddJudgeData.length)
}

//------------------------------------------------------------------------------------------------------该培训计划视频资源配置
const four_url = () => {
  readyAddVideoData.splice(0,readyAddVideoData.length)
  show_1.value = false;
  show_2.value = false;
  show_3.value = false;
  show_4.value = true;
  axios.get("http://localhost:9090/video-resource-bank-entity/getAllVideoInfo").then(res => {
    allVideoInfo.splice(0, allVideoInfo.length)
    allVideoInfo.push(...res.data)
  })
}
const readyAddVideoData :videoInfo[] = reactive([])
const videoAdd = (index:number,videoEntity:videoInfo) =>{
  readyAddVideoData.push(allVideoInfo[index])
  allVideoInfo.splice(index,1)
}
const videoCancel = (index:number,videoEntity:videoInfo) =>{
  allVideoInfo.push(readyAddVideoData[index])
  readyAddVideoData.splice(index,1)
}
const submitVideoAdd = ()=>{
  axios.post("http://localhost:9090/training-resource-entity/saveVideoResourceInfo/" + plan_id.value, readyAddVideoData).then(res => {
    ElMessage({
      message: 'Add Successful',
      type: 'success'
    })
  })
  readyAddVideoData.splice(0,readyAddVideoData.length)
}

onMounted(() => {
  plan_id.value = route.params.id
  admId.value = route.params.adm_id
  axios.get("http://localhost:9090/training-resource-entity/getVideoByPlanId/" + plan_id.value).then(res => {
    allVideoInfo.splice(0, allVideoInfo.length)
    allVideoInfo.push(...res.data)
  })
  axios.get("http://localhost:9090/training-resource-entity/getChooseByPlanId/" + plan_id.value).then(res => {
    allChooseInfo.splice(0, allChooseInfo.length)
    allChooseInfo.push(...res.data)
  })
  axios.get("http://localhost:9090/training-resource-entity/getJudgeByPlanId/" + plan_id.value).then(res => {
    allJudgeInfo.splice(0, allJudgeInfo.length)
    allJudgeInfo.push(...res.data)
  })
})
</script>

<style scoped>
.el-button {
  font-family: 楷体, serif;
  font-size: medium;
}
.upload-demo{
  width: 50%;
  margin-left: 27%;
  margin-top: 50px;
}
</style>