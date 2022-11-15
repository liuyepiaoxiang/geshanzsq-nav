<template>
    <div>
        <el-button @click="init" v-if="!isConnect">{{$t("wallet.connect")}}</el-button>
        <el-dropdown v-else>
            <span class="el-dropdown-link">
                {{addressShift(account)}}
            </span>
            <el-dropdown-menu>{{$t("wallet.disconnect")}}</el-dropdown-menu>
        </el-dropdown>
        <install-metamask :showDialog="showDialog" @close="closeDialog"></install-metamask>
    </div>
</template>
    
<script>
import Web3 from 'web3';
import installMetamask from './installMetamask';
export default {
    name: 'ConnectWallet',
    props: {   },
    components: { installMetamask },
    data() {
        return {
            isConnect: false,
            isInstall: true,
            account: '',
            web3: null,
            MetaMaskId: "1", // main net netID
            netID: "5", // user metamask id
            Web3Interval: null,
            AccountInterval: null,
            NetworkInterval: null,
            stateLog: null,
            isComplete: false,
            type: "INIT",
            showDialog: false,
            MetamaskMsg: {
                LOAD_MATAMASK_WALLET_ERROR: "Loading metamask error, please try later",
                EMPTY_METAMASK_ACCOUNT:
                "Please log in to your metamask to continue with this app.",
                NETWORK_ERROR: "The connection is abnormal, please try again",
                METAMASK_NOT_INSTALL: "Please install metamask for this application",
                METAMASK_TEST_NET: "Currently not in the main network.",
                METAMASK_MAIN_NET: "Currently Main network",
                USER_DENIED_ACCOUNT_AUTHORIZATION: "User denied account authorization",
            },
        }
    },
    created() {
       
    },
     mounted() {
        const isIntalled = this.isMetaMaskInstalled();
        if(!isIntalled) {
            this.showDialog = true;
        } else {
            this.init();
        }
    },
    methods: {
        async init() {
            if (window.ethereum) {
                window.web3 = new Web3(ethereum);
                try {
                    await ethereum.request({ method: 'eth_requestAccounts' });
                    this.web3TimerCheck(window.web3);
                } catch (error) {
                    console.log(error)
                    if(error.code) {
                        this.$message.error('User rejected')
                    }
                }
            } else if (window.web3) {
                window.web3 = new Web3(web3.currentProvider);
                this.web3TimerCheck(window.web3);
            } else {
                this.web3 = null;
                this.showDialog = true;
                this.Log(this.MetamaskMsg.METAMASK_NOT_INSTALL, "NO_INSTALL_METAMASK");
                console.error(
                    "Non-Ethereum browser detected. You should consider trying MetaMask!"
                );
            }
        },
        checkWeb3() {
            let web3 = window.web3;
            if (typeof web3 === "undefined") {
                this.web3 = null;
                this.Log(this.MetamaskMsg.METAMASK_NOT_INSTALL, "NO_INSTALL_METAMASK");
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
        checkNetWork() {
            try {
                // Main Network: 1
                // Ropsten Test Network: 3
                // Kovan Test Network: 42
                // Rinkeby Test Network: 4
                // Goerli Test Network: 5
                this.web3.eth.net.getId().then((netId) => {
                    this.netID = netId;
                    if (this.account !== "" && netId === 1)
                        return this.Log(this.MetamaskMsg.METAMASK_TEST_NET, "MAINNET");
                    if (this.account !== "" && netId === 3)
                        return this.Log(this.MetamaskMsg.METAMASK_TEST_NET, "ROPSTEN");
                    if (this.account !== "" && netId === 42)
                        return this.Log(this.MetamaskMsg.METAMASK_TEST_NET, "KOVAN");
                    if (this.account !== "" && netId === 4)
                        return this.Log(this.MetamaskMsg.METAMASK_TEST_NET, "RINKEBY");
                    if (this.account !== "" && netId === 5)
                        return this.Log(this.MetamaskMsg.METAMASK_TEST_NET, "GOERLI");
                    if (this.account !== "")
                        this.Log(this.MetamaskMsg.METAMASK_MAIN_NET, "MAINNET");
                    });
            } catch (err) {
                this.Log(this.MetamaskMsg.NETWORK_ERROR, "NETWORK_ERROR");
            }
        },
        Log(msg, type = "") {
            const letType = type;
            if (letType === this.type) return;
            const message = this.userMessage === "null" ? msg : this.userMessage;
            this.type = type;
            this.$emit("onComplete", {
                web3: this.web3,
                type,
                account: this.account,
                message,
                netID: this.netID,
            });
        },
        web3TimerCheck(web3) {
            this.web3 = web3;
            this.checkAccounts();
            this.checkNetWork();
            this.Web3Interval = setInterval(() => this.checkWeb3(), 1000);
            this.AccountInterval = setInterval(() => this.checkAccounts(), 1000);
            this.NetworkInterval = setInterval(() => this.checkNetWork(), 1000);
        },
        addressShift(address) {
            let addressStr = ''
            if (address) {
                addressStr =  `${address.slice(0,4)}...${address.slice(-4)}`
            }
            return addressStr;
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
                this.$store.commit('SET_ADDRESS', this.account);
            });
        },
        isMetaMaskInstalled () {
            const { ethereum } = window
            return Boolean(ethereum && ethereum.isMetaMask)
        },
        getConnected() {
            this.account = ethereum.selectedAddress;
        },
        closeDialog() {
            this.showDialog = false;
            console.log('close');
        },
    },
}
</script>

<style>

</style>