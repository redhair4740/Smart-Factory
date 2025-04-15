<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <!-- 关键指标卡片 -->
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="data-card">
          <template #header>
            <div class="card-header">
              <span>今日产量</span>
              <el-tag type="success">实时</el-tag>
            </div>
          </template>
          <div class="card-content">
            <div class="value">2,345</div>
            <div class="trend">
              <span class="label">较昨日</span>
              <span class="percentage increase">+15%</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="data-card">
          <template #header>
            <div class="card-header">
              <span>设备运行率</span>
              <el-tag type="warning">实时</el-tag>
            </div>
          </template>
          <div class="card-content">
            <div class="value">85.7%</div>
            <div class="trend">
              <span class="label">较昨日</span>
              <span class="percentage decrease">-2.3%</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="data-card">
          <template #header>
            <div class="card-header">
              <span>良品率</span>
              <el-tag>今日</el-tag>
            </div>
          </template>
          <div class="card-content">
            <div class="value">98.5%</div>
            <div class="trend">
              <span class="label">较昨日</span>
              <span class="percentage increase">+0.5%</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="data-card">
          <template #header>
            <div class="card-header">
              <span>待处理报警</span>
              <el-tag type="danger">实时</el-tag>
            </div>
          </template>
          <div class="card-content">
            <div class="value">3</div>
            <div class="trend">
              <span class="label">较昨日</span>
              <span class="percentage">持平</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 生产状态概览 -->
    <el-row :gutter="20" class="mt-4">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>生产状态概览</span>
              <el-radio-group v-model="timeRange" size="small">
                <el-radio-button label="day">今日</el-radio-button>
                <el-radio-button label="week">本周</el-radio-button>
                <el-radio-button label="month">本月</el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <div class="chart-container">
            <!-- 这里可以集成图表组件 -->
            <div class="chart-placeholder">生产趋势图表</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>设备状态分布</span>
            </div>
          </template>
          <div class="chart-container">
            <!-- 这里可以集成图表组件 -->
            <div class="chart-placeholder">设备状态饼图</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 报警信息列表 -->
    <el-row class="mt-4">
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最近报警信息</span>
              <el-button type="primary" link>查看全部</el-button>
            </div>
          </template>
          <el-table :data="alarmList" style="width: 100%">
            <el-table-column prop="time" label="时间" width="180" />
            <el-table-column prop="device" label="设备" width="180" />
            <el-table-column prop="type" label="类型">
              <template #default="{ row }">
                <el-tag :type="row.typeTag">{{ row.type }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="description" label="描述" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="row.statusTag">{{ row.status }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const timeRange = ref('day')

const alarmList = ref([
  {
    time: '2024-03-21 10:30:45',
    device: '注塑机A-01',
    type: '温度异常',
    typeTag: 'warning',
    description: '设备温度超过预警阈值',
    status: '待处理',
    statusTag: 'danger'
  },
  {
    time: '2024-03-21 09:15:22',
    device: '包装线B-02',
    type: '停机',
    typeTag: 'danger',
    description: '急停按钮被触发',
    status: '已处理',
    statusTag: 'success'
  },
  {
    time: '2024-03-21 08:45:10',
    device: '检测站C-03',
    type: '质量预警',
    typeTag: 'warning',
    description: '连续3件产品不合格',
    status: '处理中',
    statusTag: 'warning'
  }
])
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.data-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  text-align: center;
  padding: 10px 0;
}

.value {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
}

.trend {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  font-size: 14px;
}

.label {
  color: #909399;
}

.percentage {
  font-weight: 500;
}

.increase {
  color: #67c23a;
}

.decrease {
  color: #f56c6c;
}

.mt-4 {
  margin-top: 20px;
}

.chart-container {
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.chart-placeholder {
  color: #909399;
  font-size: 14px;
}
</style>