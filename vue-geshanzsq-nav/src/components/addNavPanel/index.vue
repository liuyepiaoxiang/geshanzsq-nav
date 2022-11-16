<template>
    <div class="benefit-panel">
        <div class="benefit-description">
          <p class="benefit-content">成为社区贡献者</p>
        </div>
        <div class="benefit-add">
            <el-button type="primary" @click="addNav">+添加导航</el-button>
        </div>
        <add-nav-dialog :showDialog="showAddNavDialog"  @close="closeDialog"></add-nav-dialog>
    </div>
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
.benefit-panel{
  height: 4rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  background-color: white;
  border-radius: 4px;
  border: 1px solid #e6ebf5;
  color: #303133;
}
.benefit-content{
  margin: 0 2rem;
}
.benefit-description{
  flex-grow: 1;
}

.benefit-add{
  margin: 0 4rem;
}
</style>
