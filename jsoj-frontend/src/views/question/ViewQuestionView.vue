<template>
  <div id="viewQuestionView">
    <a-row :gutter="[24, 24]">
      <!--题目展示区域-->
      <a-col :md="12" :xs="24">
        <a-tabs default-active-key="question">
          <a-tab-pane key="question" title="题目">
            <a-card v-if="question" :title="question.title">
              <MdViewer :value="question.content || ''" />
              <!--判题条件展示区域-->
              <a-descriptions
                :column="{ xs: 1, md: 2, lg: 3 }"
                title="判题条件"
              >
                <a-descriptions-item label="时间限制（ms）：">
                  {{ question.judgeConfig.timeLimit ?? 0 }}
                </a-descriptions-item>
                <a-descriptions-item label="内存限制（KB）：">
                  {{ question.judgeConfig.memoryLimit ?? 0 }}
                </a-descriptions-item>
                <a-descriptions-item label="堆栈限制（KB）：">
                  {{ question.judgeConfig.stackLimit ?? 0 }}
                </a-descriptions-item>
              </a-descriptions>
              <!--标签展示区域-->
              <template #extra>
                <a-space wrap>
                  <a-tag
                    v-for="(tag, index) of question.tags"
                    :key="index"
                    :color="colors[index]"
                    >{{ tag }}
                  </a-tag>
                </a-space>
              </template>
            </a-card>
          </a-tab-pane>
          <a-tab-pane key="comment" disabled title="评论"> 评论区</a-tab-pane>
          <a-tab-pane key="answer" title="答案"> 提交后方可查看答案</a-tab-pane>
        </a-tabs>
      </a-col>
      <!--代码编辑区域-->
      <a-col :md="12" :xs="24">
        <a-form :model="form" layout="inline">
          <a-form-item
            field="language"
            label="编程语言："
            style="min-width: 240px"
          >
            <a-select v-model="form.language" placeholder="选择编程语言">
              <a-option>java</a-option>
              <a-option>cpp</a-option>
              <a-option>c#</a-option>
              <a-option>go</a-option>
              <a-option>python</a-option>
            </a-select>
          </a-form-item>
        </a-form>
        <CodeEditor
          :handle-change="changeCode"
          :language="form.language"
          :value="form.code"
        />
        <a-divider size="0" />
        <a-button
          shape="round"
          size="large"
          style="min-width: 200px; margin-left: 280px"
          type="primary"
          @click="doSubmit"
        >
          提交代码
        </a-button>
      </a-col>
    </a-row>
  </div>
</template>

<!--<script lang="ts" setup>-->
<!--import { defineProps, ref, withDefaults } from "vue";-->
<!--import CodeEditor from "@/components/CodeEditor.vue";-->
<!--import MdViewer from "@/components/MdViewer.vue";-->

<!--interface Props {-->
<!--  id: string;-->
<!--}-->

<!--/**-->
<!-- * 获取到动态路由 id-->
<!-- */-->
<!--const props = withDefaults(defineProps<Props>(), {-->
<!--  id: () => "",-->
<!--});-->

<!--// const question = ref<QuestionVO>();-->
<!--//-->
<!--// const loadData = async () => {-->
<!--//   const res = await QuestionControllerService.getQuestionVoByIdUsingGet(-->
<!--//     props.id as any-->
<!--//   );-->
<!--//   if (res.code === 0) {-->
<!--//     question.value = res.data;-->
<!--//   } else {-->
<!--//     message.error("加载失败，" + res.message);-->
<!--//   }-->
<!--// };-->
<!--// 标签颜色-->
<!--const colors = [-->
<!--  "arcoblue",-->
<!--  "pinkpurple",-->
<!--  "lime",-->
<!--  "purple",-->
<!--  "blue",-->
<!--  "cyan",-->
<!--  "gold",-->
<!--  "orange",-->
<!--  "green",-->
<!--  "magenta",-->
<!--  "gray",-->
<!--];-->

<!--/**-->
<!-- * 代码编辑器中 默认程序-->
<!-- */-->
<!--const codeDefaultValue = ref(-->
<!--  "public class Main {\n" +-->
<!--    "    public static void main(String[] args) {\n" +-->
<!--    '        System.out.println("Hello, World!");\n' +-->
<!--    "    }\n" +-->
<!--    "}\n"-->
<!--);-->

<!--// const form = ref<QuestionSubmitAddRequest>({-->
<!--//   language: "java",-->
<!--//   code: codeDefaultValue as unknown as string,-->
<!--// });-->

<!--/**-->
<!-- * 提交代码-->
<!-- */-->
<!--// const doSubmit = async () => {-->
<!--//   if (!question.value?.id) {-->
<!--//     return;-->
<!--//   }-->
<!--//-->
<!--//   const res = await QuestionControllerService.doSubmitQuestionUsingPost({-->
<!--//     ...form.value,-->
<!--//     questionId: question.value.id,-->
<!--//   });-->
<!--//   if (res.code === 0) {-->
<!--//     message.success("提交成功");-->
<!--//   } else {-->
<!--//     message.error("提交失败," + res.message);-->
<!--//   }-->
<!--// };-->

<!--/**-->
<!-- * 页面加载时，请求数据-->
<!-- */-->
<!--// onMounted(() => {-->
<!--//   loadData();-->
<!--// });-->
<!--//-->
<!--// const changeCode = (value: string) => {-->
<!--//   form.value.code = value;-->
<!--// };-->
<!--</script>-->

<style>
#viewQuestionView {
  max-width: 1600px;
  margin: 0 auto;
}

#viewQuestionView .arco-space-horizontal .arco-space-item {
  margin-bottom: 0 !important;
}
</style>
