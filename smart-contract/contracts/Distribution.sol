//SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

import '@chainlink/contracts/src/v0.8/ChainlinkClient.sol';
import '@chainlink/contracts/src/v0.8/ConfirmedOwner.sol';

interface ICounter {
    function mint(address, uint256) external;
}

contract GenericLargeResponse is ChainlinkClient, ConfirmedOwner {
    using Chainlink for Chainlink.Request;

    bytes32 private jobId;
    uint256 private fee;
    uint256 public volume;
    address public contractAdd;
    bytes32 public _requestIds;

    mapping(bytes32 => address) dister;

    constructor(address _coin) ConfirmedOwner(msg.sender) {
        setChainlinkToken(0x326C977E6efc84E512bB9C30f76E30c160eD06FB);
        setChainlinkOracle(0x40193c8518BB267228Fc409a613bDbD8eC5a97b3);
        jobId = 'ca98366cc7314957b8c012c72f05aeeb';
        fee = (1 * LINK_DIVISIBILITY) / 10;
        contractAdd = _coin;
    }

    event RequestVolume(bytes32 indexed requestId, uint256 volume);
    function requestVolumeData() public returns (bytes32 requestId) {
        Chainlink.Request memory req = buildChainlinkRequest(jobId, address(this), this.fulfill.selector);

        req.add('get', 'https://www.fastmock.site/mock/d1c049b90a94d6a89541715d9bf2dd9c/getBalance/api/coin');

        req.add('path', 'amount');

        int256 timesAmount = 1;
        req.addInt('times', timesAmount);

        _requestIds = sendChainlinkRequest(req, fee);
        dister[_requestIds] = msg.sender;
        return _requestIds;
    }

    function fulfill(bytes32 _requestId, uint256 _volume) public recordChainlinkFulfillment(_requestId) {
        emit RequestVolume(_requestId, _volume);
        volume = _volume;
        ICounter(contractAdd).mint(dister[_requestId], _volume);
    }
}