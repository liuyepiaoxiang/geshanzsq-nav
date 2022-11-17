// SPDX-License-Identifier: MIT
pragma solidity ^0.8.9;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/AccessControl.sol";

contract Nav is ERC20, AccessControl {
    // bytes32 public constant MINTER_ROLE = keccak256("MINTER_ROLE");

    constructor() ERC20("nav", "NAV") {
        // _grantRole(DEFAULT_ADMIN_ROLE, msg.sender);
        // _grantRole(MINTER_ROLE, msg.sender);
    }

    function decimals() public view virtual override returns (uint8) {
        return 1;
    }

    function mint(address to, uint256 amount) public {
        _mint(to, amount);
    }
}