<template>
  <div>
    <div class="stake-container" v-if="isUserStake">
        <el-button @click="installMetamask" v-if="!isInstall">{{$t("wallet.install")}}</el-button>
        <el-button @click="init" v-if="!isConnect">{{$t("wallet.connect")}}</el-button>
    </div>
    <div class="stake-info-container" v-else>
        <el-button @click="installMetamask" v-if="!isInstall">{{$t("wallet.install")}}</el-button>
        <el-button @click="init" v-if="!isConnect">{{$t("wallet.connect")}}</el-button>
    </div>
  </div>
</template>

<script>
import Staker from './abi/Staker.json';
export default {
    name: 'ConnectWallet',
    props: {   },
    components: {},
    data() {
        return {
            web3: null,
            stakerAddress: '0xe061ac13e8c0fc99336d90cf13e64104b6ac2549',
            stakerOperator: {},
            balances: 0,
            isUserStaked: false,
        }
    },
    created() {
        
    },
    mounted() {
        this.InitContract();
    },
    methods: {
        InitContract() {
            if(window.web3 && this.$store.getters.address) {
                this.stakerOperator = new web3.eth.Contract(Staker, this.stakerAddress)
                this.balances = this.stakerOperator.methods.balances(this.$store.getters.address)
                if(this.balances !== 0) {
                    this.isUserStaked = true;
                }
            }
        },
    },
}
</script>

<style>

</style>