<template>
    <div>
        <el-button @click="connect" v-if="isConnect">{{$t("wallet.connect")}}</el-button>
        <el-dropdown v-else>
            <span class="el-dropdown-link">
                {{addressShift(account)}}
            </span>
            <el-dropdown-menu>{{$t("wallet.disconnect")}}</el-dropdown-menu>
        </el-dropdown>
    </div>
</template>
    
<script>
export default {
    name: 'ConnectWallet',
    props: {   },
    components: {},
    data() {
        return {
            isConnect: false,
            account: '',
        }
    },
    created() {
        this.getConnected();
    },
    mounted() {
        this.changeAccount();
    },
    methods: {
        addressShift(address) {
            return `${address.slice(0,4)}...${address.slice(-4)}`
        },
        connect() {
            if (window.ethereum) {
                window.ethereum.enable().then((res) => {
                    this.isConnect = true;
                    this.account = res[0];
                });
            } else {
                message.error($t("wallet.isNotInstall"));
            }
        },
        changeAccount() {
            ethereum.on("accountsChanged", function(accounts) {
                this.account = addressShiftaccounts[0];
            });
        },
        getConnected() {
            this.account = ethereum.selectedAddress;
        }
    },
}
</script>

<style>

</style>