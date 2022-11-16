<template>
  <el-dialog
    :title="$t('nav.add')"
    :visible.sync="showDialog"
    :before-close="closeDialog"
    :close-on-click-modal="false"
    class="dialog">
      <div class="dialog-content">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="ruleForm">
          <el-form-item label="请选择你要添加的导航分类" prop="navType">
            <el-select v-model="ruleForm.navType">
              <el-option v-for="nav in menus" 
              :key="nav.menuId" 
              :label="nav.menuName" 
              :value="nav.menuId"></el-option>
             </el-select>
          </el-form-item>
          <el-form-item label="请填写导航名称" prop="navName">
            <el-input
                    type="text"
                    placeholder="请输入导航名称"
                    v-model="ruleForm.navName"
                    maxlength="24"
                    show-word-limit
                  >
                  </el-input>
          </el-form-item>
          <el-form-item label="请填写导航介绍" prop="navDescription">
            <el-input
                  type="textarea"
                  placeholder="请输入内容"
                  v-model="ruleForm.navDescription"
                  maxlength="100"
                  show-word-limit
                ></el-input>
          </el-form-item>
          <el-form-item label="请填写导航链接" prop="navLink">
             <el-input
                type="text"
                placeholder="请输入导航链接"
                v-model="ruleForm.navLink"
              >
              </el-input>
          </el-form-item>
           <!-- <el-form-item label="请上传导航图片" prop="navPic">
              <el-upload
                action="#"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-change="handleChange"
                :auto-upload="false">
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
           </el-form-item> -->
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="submit" type="primary">确认发布</el-button>
          <el-button @click="closeDialog">取    消</el-button>
        </span>
      </div>
  </el-dialog>
</template>

<script>
import { mapGetters } from "vuex";
import { addSiteInfo } from '@/api/front/frontNav';

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
        dialogVisible: false,
        ruleForm: {
          navType: '',
          navName: '',
          navDescription: '',
          navLink: '',
        },
        rules: {
          navName: [
            { required: true, message: '请输入导航名称', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          navType: [
            { required: true, message: '请选择活动区域', trigger: 'change' }
          ],
          navLink: [
            { required: true, message: '请输入导航链接', trigger: 'blur' },
          ],
          desc: [
            { required: true, message: '请填写活动形式', trigger: 'blur' }
          ]
        },
    }
  },
  created() {
  },
  mounted() {
  },
  computed: {
    ...mapGetters(['menus'])
  },
  methods: { 
    closeDialog() {
      this.$refs['ruleForm'].resetFields();
      this.$emit('close');
    },
    handleRemove(file, fileList) {
        console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleChange(file, fileList) {
      console.log(file, fileList);
    },
    submit(){
       this.$refs['ruleForm'].validate((valid) => {
          if (valid) {
            addSiteInfo(valid).then(res => {
              console.log(res);
              this.closeDialog();
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
  }
}
</script>

<style>
.dialog-content{

}

.dialog-item{
  margin: .5rem;
}

.dialog-item-title{
  margin: .5rem;
}
.el-form-item__label{
  display: block;
  text-align: left;
  float: none;
}
.dialog-item-content{

}
</style>
