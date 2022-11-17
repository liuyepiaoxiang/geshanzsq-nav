<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div class="personal_contain">
            <div class="reward">
              <p>当前收益: 100000web3n</p>
              <el-button :disabled="stackStatus" @click="getReward">领取收益</el-button>
              <el-button :disabled="!stackStatus" @click="staker">质押</el-button>
            </div>
            <p>我的创作</p>
            <el-card class="box-card">
              <div v-for="o in 4" :key="o" class="text item">
                {{'列表内容 ' + o }}
              </div>
            </el-card>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd :user="user" />
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import { getUserProfile } from "@/api/system/user";

export default {
  name: "Profile",
  components: { userAvatar, userInfo, resetPwd },
  data() {
    return {
      user: {},
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo"
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
      });
    }
  }
};
</script>

<style scoped lang="scss">
.personal_contain {
  width: 100%;
  height: 100%;
  padding-top: 30px;
  .reward {
    width: 800px;
    height: 100px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border: 1px solid #ccc;
    background-color: #ffffff;
    padding: 5px 20px;
    box-sizing: border-box;
  }
}
</style>
