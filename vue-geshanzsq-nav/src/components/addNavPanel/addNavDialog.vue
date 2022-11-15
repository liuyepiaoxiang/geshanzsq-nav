<template>
   <el-dialog
        :title="$t('nav.add')"
        :visible.sync="showDialog"
        :before-close="closeDialog"
        :close-on-click-modal="false"
        class="dialog"
      >
        <div class="dialog-content">
         <div>请选择你要添加的导航分类</div>
         <div>
          <el-select>
            <el-option></el-option>
          </el-select>
         </div>
         <div>请填写导航名称</div>
         <div>
          <el-input
              type="text"
              placeholder="请输入导航名称"
              v-model="text"
              maxlength="24"
              show-word-limit
            >
            </el-input>
         </div>
         <div>请填写导航介绍</div>
         <div>
          <el-input
              type="textarea"
              placeholder="请输入内容"
              v-model="textarea"
              maxlength="100"
              show-word-limit
            ></el-input>
         </div>
         <div>请填写导航链接</div>
         <div>
          <el-input
              type="text"
              placeholder="请输入导航链接"
              v-model="text"
            >
            </el-input>
         </div>
         <div>请上传导航图片</div>
         <div>
          <el-upload
            action="#"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
             :auto-upload="false">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
         </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeDialog">关 闭</el-button>
        </span>
      </el-dialog>
</template>

<script>


export default {
  name: 'addNavDialog',
  components: { },
  props: {
    showDialog:{
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
        dialogImageUrl: '',
        dialogVisible: false
    }
  },
  created() {
  },
  methods: { 
    closeDialog() {
      this.$emit('close');
    },
    handleRemove(file, fileList) {
        console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }
  }
}
</script>

<style>

</style>
