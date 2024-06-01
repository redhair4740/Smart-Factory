import { useRouter } from "vue-router";
import { ElCard, ElForm, ElFormItem, ElInput, ElButton } from "element-plus";
import { defineComponent, reactive } from "vue";

export default defineComponent({
  setup() {
    const data = reactive({ username: "", password: "" });
    const router = useRouter();

    function login() {
      alert(data.username);
      alert(data.password);
      router.push("/index");
    }

    return () => (
      <ElCard>
        <ElForm>
          <ElFormItem label="用户名" prop="username">
            <ElInput
              v-model={data.username}
              placeholder="请输入用户名"
            ></ElInput>
          </ElFormItem>
          <ElFormItem label="密码" prop="password">
            <ElInput v-model={data.password} placeholder="请输入密码"></ElInput>
          </ElFormItem>
          <ElFormItem>
            <ElButton
              type="primary"
              {...{
                onClick: login,
              }}
            >
              登录
            </ElButton>
          </ElFormItem>
        </ElForm>
      </ElCard>
    );
  },
});
