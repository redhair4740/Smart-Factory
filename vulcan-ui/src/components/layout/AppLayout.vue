<template>
  <el-container class="app-wrapper">
    <el-aside :width="isCollapse ? '64px' : '200px'" class="sidebar-container">
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical"
        :collapse="isCollapse"
        @select="handleSelect"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/dashboard">
          <el-icon><Monitor /></el-icon>
          <template #title>仪表盘</template>
        </el-menu-item>
        <el-sub-menu index="/production">
          <template #title>
            <el-icon><Tools /></el-icon>
            <span>生产管理</span>
          </template>
          <el-menu-item index="/production/plan">生产计划</el-menu-item>
          <el-menu-item index="/production/orders">工单管理</el-menu-item>
          <el-menu-item index="/production/monitor">生产监控</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/equipment">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>设备管理</span>
          </template>
          <el-menu-item index="/equipment/status">设备状态</el-menu-item>
          <el-menu-item index="/equipment/maintenance">维护记录</el-menu-item>
          <el-menu-item index="/equipment/alarms">报警信息</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/quality">
          <template #title>
            <el-icon><CircleCheck /></el-icon>
            <span>质量控制</span>
          </template>
          <el-menu-item index="/quality/inspection">质量检测</el-menu-item>
          <el-menu-item index="/quality/defects">不良品管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/inventory">
          <template #title>
            <el-icon><Box /></el-icon>
            <span>库存管理</span>
          </template>
          <el-menu-item index="/inventory/materials">原材料</el-menu-item>
          <el-menu-item index="/inventory/products">成品库存</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/reports">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>报表统计</span>
          </template>
          <el-menu-item index="/reports/production">生产报表</el-menu-item>
          <el-menu-item index="/reports/quality">质量报表</el-menu-item>
          <el-menu-item index="/reports/equipment">设备报表</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header height="60px" class="app-header">
        <div class="header-left">
          <el-button @click="toggleSidebar">
            <el-icon><Fold v-if="!isCollapse" /><Expand v-else /></el-icon>
          </el-button>
          <el-breadcrumb separator="/" class="breadcrumb">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-for="(item, index) in breadcrumbList" :key="index">
              {{ item }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-badge :value="3" class="notification-badge">
            <el-icon class="notification-icon"><Bell /></el-icon>
          </el-badge>
          <el-dropdown>
            <span class="el-dropdown-link">
              {{ username }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>系统设置</el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Monitor, Fold, Expand, ArrowDown, Tools, Setting, CircleCheck, Box, Document, Bell } from '@element-plus/icons-vue'
import { useAuthStore } from '@/store/authStore'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const isCollapse = ref(false)
const username = computed(() => authStore.user?.loginName || '未知用户')

const activeMenu = computed(() => route.path)

const breadcrumbList = computed(() => {
  const path = route.path.split('/').filter(Boolean)
  return path.map(item => {
    return item.charAt(0).toUpperCase() + item.slice(1)
  })
})

const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value
}

const handleSelect = (key: string) => {
  router.push(key)
}

const handleLogout = async () => {
  await authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.app-wrapper {
  height: 100vh;
}

.sidebar-container {
  background-color: #304156;
  transition: width 0.3s;
}

.el-menu-vertical {
  border-right: none;
}

.app-header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
  cursor: pointer;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  color: #606266;
  gap: 4px;
}

.notification-badge {
  margin-right: 10px;
}

.notification-icon {
  font-size: 20px;
  color: #606266;
}

.breadcrumb {
  margin-left: 10px;
}
</style>