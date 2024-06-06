import { useRouter } from "vue-router";
import {
  ElCard,
  ElForm,
  ElFormItem,
  ElInput,
  ElButton,
  ElContainer,
  ElMain,
  ElRow,
  ElCol,
  ElMessage,
} from "element-plus";
import { defineComponent, reactive, watch } from "vue";
import bcrypt from "bcryptjs";
import { login } from "@/api/auth.ts";
import { User } from "@/model/auth.ts";

export default defineComponent({
  setup() {
    const data = reactive<User>({ id: 0, loginName: "", password: "" });
    const router = useRouter();

    watch(
      () => data.loginName,
      (newValue, oldValue) => {
        console.info(newValue);
        console.info(oldValue);
      }
    );

    const loginCommit = async () => {
      const hashedPassword = await bcrypt.hash(data.password, 10); // 使用bcrypt的盐值为10
      data.password = hashedPassword;
      try {
        const responseData = await login({ ...data, password: hashedPassword });
        if (responseData.code === 200 && responseData.msg) {
          router.push("/index");
        } else {
          ElMessage.error("登录失败:" + responseData.msg);
        }
        // 在这里处理登录成功的情况，例如存储token
      } catch (error) {
        console.error("登录失败:", error);
        ElMessage.error("登录失败:" + error.message);
      }
    };
    const register = () => {};

    return () => (
      <ElContainer>
        <ElMain>
          <ElRow justify="center">
            <ElCol span="12">
              <ElCard
                style={{
                  maxWidth: "480px",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <ElForm label-position="right" label-width="auto">
                  <ElFormItem label="用户名" prop="username">
                    <ElInput
                      v-model={data.loginName}
                      placeholder="请输入用户名"
                    ></ElInput>
                  </ElFormItem>
                  <ElFormItem label="密码" prop="password">
                    <ElInput
                      v-model={data.password}
                      placeholder="请输入密码"
                      show-password
                    ></ElInput>
                  </ElFormItem>
                  {/* 使用ElRow和ElCol来实现按钮靠右 */}
                  <ElRow justify="end" gutter="10">
                    <ElCol span="12">
                      <ElButton
                        type="primary"
                        {...{
                          onClick: register,
                        }}
                      >
                        注册
                      </ElButton>
                    </ElCol>
                    <ElCol span="12">
                      <ElButton
                        type="primary"
                        {...{
                          onClick: loginCommit,
                        }}
                      >
                        登录
                      </ElButton>
                    </ElCol>
                  </ElRow>
                </ElForm>
              </ElCard>
            </ElCol>
          </ElRow>
        </ElMain>
      </ElContainer>
    );
  },
});
