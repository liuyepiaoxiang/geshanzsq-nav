require("@nomicfoundation/hardhat-toolbox");

// const { API_URL, PRIVATE_KEY } = process.env;

let PRIVATE_KEY = '4e21c98be4c8dc5b9294276af2f340a17b5567e1b9e7d7dc03572bdaa7f546aa'

/** @type import('hardhat/config').HardhatUserConfig */
module.exports = {
  solidity: "0.8.17",
  defaultNetwork: "goerli",
  networks: {
     hardhat: {},
     goerli: {
        url: 'https://ethereum-goerli-rpc.allthatnode.com/',
        // url: 'https://goerli.infura.io/v3/',
        accounts: [`${PRIVATE_KEY}`]
     }
  }
};

/**
* @type import('hardhat/config').HardhatUserConfig
*/

// require('dotenv').config();
// require("@nomiclabs/hardhat-ethers");

