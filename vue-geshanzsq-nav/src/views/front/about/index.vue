<template>
  <div style="padding: 0px">
    <front-home :menu-list="menuList">
      <div slot="mainContainer" style="padding: 20px 5px 0px 5px">
        <div class="personal_contain">
          <div class="reward">
            <p>当前收益: {{dist}} nav</p>
            <el-button :disabled="stackStatus" @click="getReward">领取收益</el-button>
            <el-button :disabled="!stackStatus" @click="staker">质押</el-button>
            <el-button :disabled="stackStatus" @click="cancelStaker">取消质押</el-button>
          </div>
          <p>我的创作</p>
          <el-card class="box-card">
            <div v-for="o in 4" :key="o" class="text item">
              {{'列表内容 ' + o }}
            </div>
          </el-card>
        </div>
      </div>
    </front-home>
  </div>
</template>

<script>
  import Web3 from 'web3';
  import FooterBottom from '@/components/FooterBottom'
  import FrontHome from '@/views/front/FrontHome';
  import Comment from './components/Comment'

  import { getNavAbout, getFrontMenu } from '@/api/front/frontNav'

  import Staker from '@/api/abi/Staker.json';
  import Distribution from '@/api/abi/Distribution.json';

  import { mapState } from 'vuex'
  import ResizeMixin from '@/views/front/mixin/ResizeHandler'

  export default {
    name: "about",
    components: {FooterBottom,FrontHome,Comment},
    data() {
      return {
        menuList: [],
        loading: false,
        // 图片基本地址
        baseUrl: process.env.VUE_APP_BASE_FILE,
        webConfig: {},
        stakerAddress: '0x3DAD3B10a77f4E0bA43c2FA79Cc48D5AB7B16F45',
        stackStatus: false,
        distributionAddress: '0x60695ffCA42Bc16Ce08fC013AfECbe4902033c9a',
        stackStatus: false,
        erc20Arress: '0x21F813B20373D9B3dCf296e2c8d4c44fE507a162',
        dist: 60
      }
    },
    mixins: [ResizeMixin],
    computed: {
      ...mapState({
        device: state => state.app.device,
      }),
      isMobile() {
        return this.device === 'mobile';
      },
    },
    created() {
      this.getFrontMenu();
      // this.getNavAbout();
    },
    methods: {
      //查询菜单
      getFrontMenu() {
        getFrontMenu().then(response => {
          this.menuList = response.menus;
        })
      },
      /** 获取关于本站内容 */
      getNavAbout() {
        this.loading = true;
        getNavAbout().then(response => {
          this.webConfig = response.data;
          this.loading = false;
        }).catch(error => {
          this.loading = false;
        })
      },
      async getReward() {
        console.log(1, 'distributContract', this.address)
        let distributContract =  new this.web3.eth.Contract(Distribution, this.distributionAddress, {
          from: this.address
        })
        let rest = await distributContract.methods.requestVolumeData().send({from: this.address})
        this.$message.success('提取成功')
      },
      async staker() {
        if(window.web3) {
            this.stakerContract = new this.web3.eth.Contract(Staker, this.stakerAddress)
            await this.stakerContract.methods.stake().send({
                from: this.address,
                value: 1000000000
            })
            console.log(this.stakerContract, '_this.stakerOperator_')
            // this.balances = this.stakerOperator.methods.balances(this.$store.user.address)
            // if(this.balances !== 0) {
            //     this.isUserStaked = true;
            // }
        }
      },
      checkAccounts() {
        if (this.web3 === null) return;
        this.web3.eth.getAccounts((err, accounts) => {
            if (err != null)
                return this.Log(this.MetamaskMsg.NETWORK_ERROR, "NETWORK_ERROR");
            if (accounts.length === 0) {
                this.account = "";
                this.Log(this.MetamaskMsg.EMPTY_METAMASK_ACCOUNT, "NO_LOGIN");
                return;
            }
            this.account = accounts[0]; // user Address
            this.$store.commit('SET_ADDRESS', this.account);
            this.isConnect = true;
        });
      },
      async initContract() {
        if(window.web3) {
          this.web3 = new Web3(web3.currentProvider)
          let address = await this.web3.eth.getAccounts()
          this.address = address[0]
          this.stakerContract = new this.web3.eth.Contract(Staker, this.stakerAddress)
          this.stackStatus = await this.stakerContract.methods.getUserStackStatus().call()
        }
      },
      cancelStaker() {

      }
    },
    mounted() {
      this.initContract()
    }
  }
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
