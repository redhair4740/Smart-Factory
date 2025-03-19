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
import { defineComponent, reactive, ref } from "vue";
import { User } from "@/model/auth.ts";
import { useAuthStore } from "@/store/authStore";
import { encrypt } from "@/utils/encryption";
import RegisterForm from "@/components/RegisterForm.vue";

export default defineComponent({
  setup() {
    const data = reactive<User>({ id: 0, loginName: "", password: "", loginType: "PC" });
    const router = useRouter();
    const authStore = useAuthStore();
    const loading = ref(false);
    const registerVisible = ref(false);
    const registerFormRef = ref();

    const loginCommit = async () => {
      if (!data.loginName || !data.password) {
        ElMessage.warning("请输入用户名和密码");
        return;
      }

      loading.value = true;
      try {
        // 在发送请求前加密密码
        const encryptedPassword = encrypt(data.password);
        if (!encryptedPassword) {
          ElMessage.error("密码加密失败，请重试");
          loading.value = false;
          return;
        }
        
        const loginData = {
          ...data,
          password: encryptedPassword
        };
        
        console.log("正在登录，用户名:", loginData.loginName);
        const success = await authStore.login(loginData);
        
        if (success) {
          ElMessage.success("登录成功");
          router.push("/index");
        } else {
          ElMessage.error("登录失败，请检查用户名和密码");
        }
      } catch (error) {
        console.error("登录失败:", error);
        ElMessage.error(`登录失败: ${(error as Error).message || '未知错误'}`);
      } finally {
        loading.value = false;
      }
    };

    // 打开注册对话框
    const showRegister = () => {
      registerVisible.value = true;
    };

    // 注册成功回调
    const handleRegisterSuccess = (username: string) => {
      registerVisible.value = false;
      data.loginName = username;
      data.password = "";
      ElMessage.success("注册成功，请登录");
    };

    return () => (
      <ElContainer class="login-container">
        <ElMain>
          <ElRow justify="center" align="middle" class="login-row">
            <ElCol xs={24} sm={16} md={12} lg={8} xl={6}>
              <ElCard class="login-card" shadow="always">
                <h2 class="login-title">系统登录</h2>
                <ElForm label-position="top">
                  <ElFormItem label="用户名" required>
                    <ElInput
                      v-model={data.loginName}
                      placeholder="请输入用户名"
                      clearable
                    ></ElInput>
                  </ElFormItem>
                  <ElFormItem label="密码" required>
                    <ElInput
                      v-model={data.password}
                      placeholder="请输入密码"
                      show-password
                      onKeyup={(e: KeyboardEvent) => {
                        if (e.key === 'Enter') loginCommit();
                      }}
                    ></ElInput>
                  </ElFormItem>
                  <ElRow justify="space-between" class="login-buttons">
                    <ElButton onClick={showRegister}>注册账号</ElButton>
                    <ElButton
                      type="primary"
                      onClick={loginCommit}
                      loading={loading.value}
                    >
                      登录
                    </ElButton>
                  </ElRow>
                </ElForm>
              </ElCard>
            </ElCol>
          </ElRow>
        </ElMain>

        {/* 注册表单对话框 */}
        <RegisterForm
          v-model={registerVisible.value}
          ref={registerFormRef}
          onRegister-success={handleRegisterSuccess}
        />
      </ElContainer>
    );
  },
});

// 添加到styles/index.css或直接内联样式
/**
.login-container {
  height: 100vh;
  background-color: #f5f7fa;
}

.login-row {
  height: 100%;
}

.login-card {
  padding: 20px;
  border-radius: 8px;
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
  color: #303133;
}

.login-buttons {
  margin-top: 20px;
}
*/
