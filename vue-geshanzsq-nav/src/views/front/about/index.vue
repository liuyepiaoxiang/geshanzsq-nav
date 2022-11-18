<template>
  <div style="padding: 0px">
    <front-home :menu-list="menuList">
      <div slot="mainContainer" style="padding: 20px;height: calc(100vh - 137px);">
        <div class="personal_contain">
          <div class="reward">
            <p style="margin-bottom: 12px;">作品累计点击量: {{ profit }}</p>
            <div>
              <p>可换取收益: {{ profit / 5 }}</p>
              <el-button :disabled="!stackStatus" @click="getReward">领取收益</el-button>
            </div>
            <div style="text-align:center">
              <p style="margin-bottom: 12px;">质押状态：{{stackStatus ? '已完成质押' : '未参与质押'}}</p>
              <div style="">
                <el-button :disabled="stackStatus" @click="staker">参与质押</el-button>
                <el-button :disabled="!stackStatus" @click="cancelStaker">取消质押</el-button>
              </div>
            </div>
          </div>
          <!-- <p>我的创作</p> -->
          <el-row :gutter="24" >
            <h4 style="margin-left: 12px">
              <svg-icon icon-class="tag" style="margin-right: 7px;font-size: 18px"/>
              <span style="color: #555;font-size: 17px;font-weight:normal">我的创作</span>
            </h4>
            <el-col :sm="6" :sx="24" style="margin-bottom: 15px" :key="'sitecard' + index" v-for="(site, index) in siteList">
              <site-card :site="site" style="height: 90px"></site-card>
            </el-col>
          </el-row>
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
  import Nav from '@/api/abi/Nav.json';
  import NavReward from '@/api/abi/NavReward.json';

  import SiteCard from "@/components/SiteCard/SiteCard";

  import { mapState } from 'vuex'
  import ResizeMixin from '@/views/front/mixin/ResizeHandler'

  import { getUserFrontMenu, getUserClickCount } from '@/api/front/frontNav'

  export default {
    name: "about",
    components: {FooterBottom,FrontHome,Comment,SiteCard},
    data() {
      return {
        menuList: [],
        loading: false,
        // 图片基本地址
        baseUrl: process.env.VUE_APP_BASE_FILE,
        webConfig: {},
        stakerAddress: '0x3DAD3B10a77f4E0bA43c2FA79Cc48D5AB7B16F45',
        stackStatus: false,
        rewardAddress: '0x5Be0C36794b9f4Dcc5ec7553b50f84F223853Cd5',
        erc20Arress: '0x6Ac120B418223F1c059D61f751D568544Bf4104e',
        siteList: [],
        profit: 0
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
        let distributContract =  new this.web3.eth.Contract(NavReward.abi, this.rewardAddress, {
          from: this.address
        })
        let response = await distributContract.methods.mintNav(this.address).send({from: this.address})
        console.log(response, "_distributContract_")
        this.$message.success('提现成功')
        // let rest = await distributContract.methods.requestVolumeData().send({from: this.address})
      },
      async staker() {
        if(window.ethereum) {
            this.stakerContract = new this.web3.eth.Contract(Staker, this.stakerAddress)
            await this.stakerContract.methods.stake().send({
                from: this.address,
                value: 1000000000
            })
        } else {
          this.$message.warning("请安装metamask钱包")
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
        if(window.ethereum) {
          this.web3 = new Web3(web3.currentProvider)
          let address = await this.web3.eth.getAccounts()
          this.address = address[0]

          this.stakerContract = new this.web3.eth.Contract(Staker, this.stakerAddress)
          let status = await this.stakerContract.methods.getUserStackStatus().call({
            from: this.address
          })
          this.stackStatus = status
        }
      },
      async cancelStaker() {
        if(window.ethereum) {
            let response = await this.stakerContract.methods.withdraw().send({from: this.address})
        } else {
          this.$message.warning("请安装metamask钱包")
        }
      },
      getUserNavList() {
        let params = {
          userWalletAddress: '0x0914f881EC583fCD460031A93B135B9a706ADeBE'
        }
        getUserFrontMenu(params).then(response => {
          if (response.code == 200) {
            this.siteList = response.rows
          }
        }).catch(error => {
        })
      },
      getUserProfit() {
        let params = {
          userWalletAddress: this.address
        }
        getUserClickCount(params).then(response => {
          if (response.code == 200) {
            this.profit = response.data
          }
        }).catch(error => {
        })
      },
      async getUserBalance() {
        if(window.ethereum) {
          this.web3 = new Web3(web3.currentProvider)
          let address = await this.web3.eth.getAccounts()
          this.address = address[0]
          this.ERCContract = new this.web3.eth.Contract(Nav.abi, this.erc20Arress)
          let result = await this.ERCContract.methods.balanceOf(this.address).call()
        }

      }
    },
    mounted() {
      this.getUserBalance()
    },
    created() {
      this.initContract()
      this.getUserNavList()
      this.getUserProfit()
    }
  }
</script>

<style scoped lang="scss">
.personal_contain {
  width: 100%;
  height: 100%;
  padding-top: 30px;
  .reward {
    width: 100%;
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
