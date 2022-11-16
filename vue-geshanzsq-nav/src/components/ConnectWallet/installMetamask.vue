<template>
   <el-dialog
        :title="$t('wallet.install')"
        :visible.sync="showDialog"
        :before-close="closeDialog"
        :close-on-click-modal="false"
        append-to-body
        class="dialog"
      >
        <div class="dialog-content">
            <div>
                <p>{{$t('wallet.onlySupport')}}</p>
                <p>
                    <span>{{$t('wallet.official')}}</span>
                    <a href="https://metamask.io" target="_blank">https://metamask.io/</a>
                </p>
                <p v-if="browser === 'Chrome'">
                    <span>{{$t('wallet.chrome_plugin')}}</span>
                    <a href="https://chrome.google.com/webstore/detail/metamask/nkbihfbeogaeaoehlefnkodbefgpgknn" target="_blank">https://chrome.google.com/webstore/detail/metamask/nkbihfbeogaeaoehlefnkodbefgpgknn</a>
                </p>
                <p v-else>
                    <span>{{$t('wallet.firefox_plugin')}}</span>
                    <a href="https://addons.mozilla.org/zh-CN/firefox/addon/ether-metamask/" target="_blank">https://addons.mozilla.org/zh-CN/firefox/addon/ether-metamask/</a>
                </p>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeDialog">关 闭</el-button>
        </span>
      </el-dialog>
</template>

<script>


export default {
  name: 'installMetamask',
  components: { },
  props: {
    showDialog:{
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
        browser: '',
    }
  },
  created() {
    this.browser = this.getBrowserType();
  },
  methods: { 
    closeDialog() {
      this.$emit('close');
    },
     getBrowserType(){
        const userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
        let browser='unknown';
        if (userAgent.indexOf("IE")!=-1) {//字符串含有IE字段，表明是IE浏览器
            browser="IE";
        }else if(userAgent.indexOf('Firefox')!=-1){//字符串含有Firefox字段，表明是火狐浏览器
            browser="Firefox";
        }else if(userAgent.indexOf('OPR')!=-1){//Opera
            browser="Opera";
        }else if(userAgent.indexOf('Chrome')!=-1){//Chrome
            browser="Chrome";
        }else if(userAgent.indexOf('Safari')!=-1){//Safari
            browser="Safari";
        }else if(userAgent.indexOf('Trident')!=-1){//IE内核
            browser='IE 11';
        }
        return browser;
    },
  }
}
</script>

<style>

</style>
