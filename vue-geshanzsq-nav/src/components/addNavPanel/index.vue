<template>
    <el-row :gutter="24" type="flex" justify="center">
        <el-col :sm="16">
          <p>成为社区贡献者</p>
        </el-col>
        <el-col :sm="8">
            <el-button type="primary" @click="addNav">+添加导航</el-button>
        </el-col>
        <add-nav-dialog :showDialog="showAddNavDialog"  @close="closeDialog"></add-nav-dialog>
    </el-row>
</template>

<script>
import addNavDialog from './addNavDialog.vue';
  export default {
  components: { addNavDialog },
  name: "BenefitPanel",
  data() {
    return {
      showAddNavDialog: false,
    }
  },
  methods: {
    addNav() {
        this.showAddNavDialog = true;
    },
    closeDialog() {
        this.showAddNavDialog = false;
    },
  },
}
</script>

<style scoped>
</style>
