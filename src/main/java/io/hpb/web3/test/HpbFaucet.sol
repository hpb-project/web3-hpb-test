pragma solidity ^0.4.25;

/***
 * HPB水龙头 
 *  */
contract HpbFaucet {

    string public name = "HPB Nodes Service";

    address public owner;

    /**
     * Only the HPB foundation account (administrator) can call.
     */
    modifier onlyOwner {
        require(msg.sender == owner);
        // Do not forget the "_;"! It will be replaced by the actual function
        // body when the modifier is used.
        _;
    }

    event TransferOwnership(address indexed from,address indexed to);

    function transferOwnership(address newOwner) onlyOwner public {
        owner = newOwner;
        emit TransferOwnership(msg.sender, newOwner);
    }

    event ReceivedHpb(address indexed sender, uint amount);

    function () payable external {
        emit ReceivedHpb(msg.sender, msg.value);
    }

    constructor () payable public {
        owner = msg.sender;
    }
    // 合约管理员，可以添加和删除候选人
    mapping (address => address) public adminMap;

    modifier onlyAdmin {
        require(adminMap[msg.sender] != 0);
        _;
    }

    function addAdmin(address addr) onlyOwner public {
        require(adminMap[addr] != 0);
        adminMap[addr] = addr;
    }

    function deleteAdmin(address addr) onlyOwner public {
        require(adminMap[addr] != 0);
        adminMap[addr] = 0;
    }
    event TransferHpb(address indexed from,address indexed to,uint value);

    function transferHpb(address to) onlyAdmin payable public {
        to.transfer(msg.value);
        emit TransferHpb(msg.sender, to,msg.value);
    }


}
