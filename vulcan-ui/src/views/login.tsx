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
} from "element-plus";
import { defineComponent, reactive } from "vue";
import axios from "axios";
import bcrypt from "bcryptjs";

export default defineComponent({
  setup() {
    const data = reactive({ username: "", password: "" });
    const router = useRouter();

    const login = async () => {
      const hashedPassword = await bcrypt.hash(data.password, 10); // 使用bcrypt的盐值为10
      try {
        const response = await axios.post("/api/login", {
          username: data.username,
          password: hashedPassword,
        });
        console.log("Login response:", response.data);
        // 在这里处理登录成功的情况，例如存储token
      } catch (error) {
        console.error("Login error:", error);
        // 在这里处理登录失败的情况，例如显示错误消息
      }
      router.push("/index");
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
                      v-model={data.username}
                      placeholder="请输入用户名"
                    ></ElInput>
                  </ElFormItem>
                  <ElFormItem label="密码" prop="password">
                    <ElInput
                      v-model={data.password}
                      placeholder="请输入密码"
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
                          onClick: login,
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
