<template>
  <div id="addQuestionView">
    <div
      style="
        font-size: 32px;
        text-align: center;
        font-weight: bold;
        margin-bottom: 16px;
      "
    >
      创建题目
    </div>
    <a-form
      :model="form"
      label-align="left"
      size="medium"
      style="font-weight: bold; margin: 0 auto"
    >
      <!--填写标题-->
      <a-form-item
        :rules="[{ required: true, message: '题目的标题是必填的' }]"
        field="title"
        label="题目标题："
        required
        tooltip="建议填写题目标题"
      >
        <a-input
          v-model="form.title"
          placeholder="请输入标题"
          style="max-width: 500px"
        />
      </a-form-item>
      <!--填写标签-->
      <a-form-item
        field="tags"
        label="题目标签："
        required
        tooltip="建议填写题目标签"
      >
        <a-input-tag
          v-model="form.tags"
          allow-clear
          placeholder="请选择标签"
          style="max-width: 500px"
        />
      </a-form-item>
      <!--填写内容-->
      <a-form-item
        field="content"
        label="题目内容："
        required
        tooltip="建议填写题目内容"
      >
        <MdEditor :handle-change="onContentChange" :value="form.content" />
      </a-form-item>
      <!--填写答案-->
      <a-form-item
        field="answer"
        label="题目答案："
        required
        tooltip="建议填写题目答案"
      >
        <MdEditor :handle-change="onAnswerChange" :value="form.answer" />
      </a-form-item>
      <a-divider :margin="10" />
      <!--填写判题配置-->
      <a-form-item
        :content-flex="false"
        :merge-props="false"
        label="判题配置："
        required
        tooltip="建议填写判题配置"
      >
        <a-space direction="vertical" style="min-width: 500px">
          <a-form-item
            field="judgeConfig.timeLimit"
            label="时间限制："
            tooltip="单位：ms（毫秒）"
          >
            <a-input-number
              v-model="form.judgeConfig.timeLimit"
              min="0"
              mode="button"
              placeholder="请输入时间限制"
              size="large"
            />
          </a-form-item>
          <a-form-item
            field="judgeConfig.memoryLimit"
            label="内存限制："
            tooltip="单位：kb"
          >
            <a-input-number
              v-model="form.judgeConfig.memoryLimit"
              min="0"
              mode="button"
              placeholder="请输入内存限制"
              size="large"
            />
          </a-form-item>
          <a-form-item
            field="judgeConfig.stackLimit"
            label="堆栈限制："
            tooltip="单位：kb"
          >
            <a-input-number
              v-model="form.judgeConfig.stackLimit"
              min="0"
              mode="button"
              placeholder="请输入堆栈限制"
              size="large"
            />
          </a-form-item>
        </a-space>
      </a-form-item>
      <a-divider :margin="10" />
      <!--填写测试用例-->
      <a-form-item
        :content-flex="false"
        :merge-props="false"
        label="测试用例配置："
        required
        tooltip="建议填写测试用例配置"
      >
        <a-form-item
          v-for="(judgeCaseItem, index) of form.judgeCase"
          :key="index"
          no-style
        >
          <a-space direction="vertical" style="min-width: 500px">
            <a-form-item
              :key="index"
              :field="`form.judgeCase[${index}].input`"
              :label="`输入用例-${index}：`"
            >
              <a-input
                v-model="judgeCaseItem.input"
                placeholder="请输入测试输入用例"
              />
            </a-form-item>
            <a-form-item
              :key="index"
              :field="`form.judgeCase[${index}].output`"
              :label="`输出用例-${index}：`"
            >
              <a-input
                v-model="judgeCaseItem.output"
                placeholder="请输入测试输出用例"
              />
            </a-form-item>
          </a-space>
          <a-space direction="horizontal" size="large">
            <a-button
              shape="round"
              status="danger"
              type="outline"
              @click="handleDelete(index)"
            >
              删除用例
            </a-button>
            <a-button
              shape="round"
              status="success"
              type="outline"
              @click="handleAdd"
              >新增测试用例
            </a-button>
          </a-space>
        </a-form-item>
      </a-form-item>
      <a-divider :margin="10" />
      <!--提交按钮-->
      <a-form-item>
        <a-button
          shape="round"
          style="min-width: 150px; margin: 0 150px"
          type="primary"
          @click="doSubmit"
          >提交
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<!--<script lang="ts" setup>-->
<!--import { ref } from "vue";-->
<!--import MdEditor from "@/components/MdEditor.vue";-->
<!--import { useRoute, useRouter } from "vue-router";-->

<!--const router = useRouter();-->

<!--const route = useRoute();-->
<!--// 如果页面地址包含 update，视为更新页面-->
<!--const updatePage = route.path.includes("update");-->

<!--let form = ref({-->
<!--  title: "",-->
<!--  tags: [],-->
<!--  answer: "",-->
<!--  content: "",-->
<!--  judgeConfig: {-->
<!--    memoryLimit: 1000,-->
<!--    stackLimit: 1000,-->
<!--    timeLimit: 1000,-->
<!--  },-->
<!--  judgeCase: [-->
<!--    {-->
<!--      input: "",-->
<!--      output: "",-->
<!--    },-->
<!--  ],-->
<!--});-->

<!--/**-->
<!-- * 根据题目 id 获取老的数据-->
<!-- */-->
<!--// const loadData = async () => {-->
<!--//   const id = route.query.id;-->
<!--//   if (!id) {-->
<!--//     return;-->
<!--//   }-->
<!--//   const res = await QuestionControllerService.getQuestionByIdUsingGet(-->
<!--//     id as any-->
<!--//   );-->
<!--//   if (res.code === 0) {-->
<!--//     form.value = res.data as any;-->
<!--//     // json 转 js 对象-->
<!--//     if (!form.value.judgeCase) {-->
<!--//       form.value.judgeCase = [-->
<!--//         {-->
<!--//           input: "",-->
<!--//           output: "",-->
<!--//         },-->
<!--//       ];-->
<!--//     } else {-->
<!--//       form.value.judgeCase = JSON.parse(form.value.judgeCase as any);-->
<!--//     }-->
<!--//     if (!form.value.judgeConfig) {-->
<!--//       form.value.judgeConfig = {-->
<!--//         memoryLimit: 1000,-->
<!--//         stackLimit: 1000,-->
<!--//         timeLimit: 1000,-->
<!--//       };-->
<!--//     } else {-->
<!--//       form.value.judgeConfig = JSON.parse(form.value.judgeConfig as any);-->
<!--//     }-->
<!--//     if (!form.value.tags) {-->
<!--//       form.value.tags = [];-->
<!--//     } else {-->
<!--//       form.value.tags = JSON.parse(form.value.tags as any);-->
<!--//     }-->
<!--//   } else {-->
<!--//     message.error("加载失败，" + res.message);-->
<!--//   }-->
<!--// };-->

<!--// onMounted(() => {-->
<!--//   loadData();-->
<!--// });-->

<!--// const doSubmit = async () => {-->
<!--//   console.log(form.value);-->
<!--//   // 区分更新还是创建-->
<!--//   if (updatePage) {-->
<!--//     const res = await QuestionControllerService.updateQuestionUsingPost(-->
<!--//       form.value-->
<!--//     );-->
<!--//     if (res.code === 0) {-->
<!--//       message.success("更新成功");-->
<!--//       await router.push({-->
<!--//         path: "/manage/question",-->
<!--//         replace: true,-->
<!--//       });-->
<!--//     } else {-->
<!--//       message.error("更新失败，" + res.message);-->
<!--//     }-->
<!--//   } else {-->
<!--//     const res = await QuestionControllerService.addQuestionUsingPost(-->
<!--//       form.value-->
<!--//     );-->
<!--//     if (res.code === 0) {-->
<!--//       message.success("创建成功");-->
<!--//       // 添加成功后清空-->
<!--//       form.value.answer = "";-->
<!--//       form.value.tags = [];-->
<!--//       form.value.judgeCase = [];-->
<!--//       form.value.content = "";-->
<!--//       form.value.title = "";-->
<!--//     } else {-->
<!--//       message.error("创建失败，" + res.message);-->
<!--//     }-->
<!--//   }-->
<!--// };-->

<!--/**-->
<!-- * 新增判题用例-->
<!-- */-->
<!--const handleAdd = () => {-->
<!--  form.value.judgeCase.push({-->
<!--    input: "",-->
<!--    output: "",-->
<!--  });-->
<!--};-->

<!--/**-->
<!-- * 删除判题用例-->
<!-- */-->
<!--const handleDelete = (index: number) => {-->
<!--  form.value.judgeCase.splice(index, 1);-->
<!--};-->

<!--const onContentChange = (value: string) => {-->
<!--  form.value.content = value;-->
<!--};-->

<!--const onAnswerChange = (value: string) => {-->
<!--  form.value.answer = value;-->
<!--};-->
<!--</script>-->

<style scoped>
#addQuestionView {
  margin: 0 auto;
  max-width: 900px;
  background: linear-gradient(to right, #efefef, #fff, #efefef);
  border-radius: 10px;
}
</style>
