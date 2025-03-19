<template>
  <el-dialog
    v-model="dialogVisible"
    title="用户注册"
    width="400px"
    :close-on-click-modal="false"
    :before-close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="registerForm"
      :rules="rules"
      label-position="top"
    >
      <el-form-item label="用户名" prop="loginName">
        <el-input 
          v-model="registerForm.loginName" 
          clearable 
          @blur="checkUsernameAvailability"
        />
        <div class="login-tip" v-if="usernameStatus !== null">
          <el-tag v-if="usernameStatus" type="success" size="small">用户名可用</el-tag>
          <el-tag v-else type="danger" size="small">用户名已存在</el-tag>
        </div>
      </el-form-item>
      
      <el-form-item label="姓名" prop="name">
        <el-input v-model="registerForm.name" clearable />
      </el-form-item>
      
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="registerForm.email" type="email" clearable />
      </el-form-item>
      
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="registerForm.phone" clearable />
      </el-form-item>
      
      <el-form-item label="密码" prop="password">
        <el-input v-model="registerForm.password" type="password" show-password />
      </el-form-item>
      
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="registerForm.confirmPassword" type="password" show-password />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button 
        type="primary" 
        @click="submitForm" 
        :loading="loading" 
        :disabled="!isFormValid || !usernameStatus"
      >
        注册
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { ElMessage, ElMessageBox, FormInstance, FormRules } from 'element-plus'
import { register, checkUsername } from '@/api/user'
import { User } from '@/model/auth.ts'
import { useDebounce } from '@/utils/hooks'

// 注册表单显示状态
const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const loading = ref(false)
const usernameStatus = ref<boolean | null>(null)

// 注册表单
const registerForm = reactive({
  loginName: '',
  name: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: ''
})

// 表单验证规则
const rules = reactive<FormRules>({
  loginName: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { 
      validator: (rule, value, callback) => {
        if (value !== registerForm.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      }, 
      trigger: 'blur' 
    }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ]
})

// 是否可以提交表单
const isFormValid = computed(() => {
  return registerForm.loginName && registerForm.password && 
         registerForm.password === registerForm.confirmPassword
})

// 防抖处理的用户名检查
const debouncedCheckUsername = useDebounce(async (username: string) => {
  if (!username || username.length < 3) {
    usernameStatus.value = null
    return
  }

  try {
    const res = await checkUsername(username)
    usernameStatus.value = res.data
  } catch (error) {
    usernameStatus.value = null
  }
}, 500)

// 检查用户名是否可用
const checkUsernameAvailability = () => {
  if (registerForm.loginName) {
    debouncedCheckUsername(registerForm.loginName)
  } else {
    usernameStatus.value = null
  }
}

// 监听用户名变化
watch(() => registerForm.loginName, () => {
  usernameStatus.value = null
})

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const userData: User = {
          id: 0,
          loginName: registerForm.loginName,
          name: registerForm.name,
          password: registerForm.password,
          email: registerForm.email,
          phone: registerForm.phone
        }
        
        const res = await register(userData)
        
        if (res.code === 200) {
          ElMessage.success('注册成功，请登录')
          handleClose()
          emit('register-success', registerForm.loginName)
        } else {
          ElMessage.error(res.message || '注册失败')
        }
      } catch (error) {
        ElMessage.error(`注册失败: ${(error as Error).message || '未知错误'}`)
      } finally {
        loading.value = false
      }
    }
  })
}

// 关闭对话框
const handleClose = () => {
  ElMessageBox.confirm('确认关闭？未保存的数据将丢失', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    resetForm()
    dialogVisible.value = false
    emit('update:modelValue', false)
  }).catch(() => {})
}

// 重置表单
const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
  usernameStatus.value = null
}

// 对外暴露方法和属性
const open = () => {
  dialogVisible.value = true
}

// 事件
const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'register-success', username: string): void
}>()

// 接收的属性
const props = defineProps<{
  modelValue?: boolean
}>()

// 监听modelValue变化
watch(() => props.modelValue, (val) => {
  dialogVisible.value = !!val
})

// 导出组件方法
defineExpose({
  open
})
</script>

<style scoped>
.login-tip {
  margin-top: 5px;
  height: 20px;
}
</style> 