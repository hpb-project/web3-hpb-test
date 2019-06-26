package io.hpb.web3.contract;

import io.hpb.web3.abi.EventEncoder;
import io.hpb.web3.abi.TypeReference;
import io.hpb.web3.abi.datatypes.Address;
import io.hpb.web3.abi.datatypes.Event;
import io.hpb.web3.abi.datatypes.Function;
import io.hpb.web3.abi.datatypes.Type;
import io.hpb.web3.abi.datatypes.Utf8String;
import io.hpb.web3.abi.datatypes.generated.Uint256;
import io.hpb.web3.crypto.Credentials;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.core.DefaultBlockParameter;
import io.hpb.web3.protocol.core.RemoteCall;
import io.hpb.web3.protocol.core.methods.request.HpbFilter;
import io.hpb.web3.protocol.core.methods.response.Log;
import io.hpb.web3.protocol.core.methods.response.TransactionReceipt;
import io.hpb.web3.tx.Contract;
import io.hpb.web3.tx.TransactionManager;
import io.hpb.web3.tx.gas.ContractGasProvider;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the the io.hpb.web3.codegen.SolidityFunctionWrapperGenerator to update.
 *
 * <p>Generated with web3 version none.
 */
@SuppressWarnings({
        "rawtypes"
})
public class HpbFaucet extends Contract {
    private static final String BINARY = "60806040526040805190810160405280601181526020017f485042204e6f64657320536572766963650000000000000000000000000000008152506000908051906020019061004f929190610096565b5033600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555061013b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100d757805160ff1916838001178555610105565b82800160010185558215610105579182015b828111156101045782518255916020019190600101906100e9565b5b5090506101129190610116565b5090565b61013891905b8082111561013457600081600090555060010161011c565b5090565b90565b6109528061014a6000396000f300608060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100d35780631ef8d3371461016357806327e1f7df1461019957806370480275146101dc5780638da5cb5b1461021f578063dbbc830b14610276578063f2fde38b146102f9575b3373ffffffffffffffffffffffffffffffffffffffff167f7129701436f0cdc265d1e2cda298e8a1ccd6ed5fce7f69343e16530b07a2e06e346040518082815260200191505060405180910390a2005b3480156100df57600080fd5b506100e861033c565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561012857808201518184015260208101905061010d565b50505050905090810190601f1680156101555780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610197600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506103da565b005b3480156101a557600080fd5b506101da600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061050e565b005b3480156101e857600080fd5b5061021d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610671565b005b34801561022b57600080fd5b506102346107d3565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561028257600080fd5b506102b7600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506107f9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561030557600080fd5b5061033a600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061082c565b005b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103d25780601f106103a7576101008083540402835291602001916103d2565b820191906000526020600020905b8154815290600101906020018083116103b557829003601f168201915b505050505081565b6000600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415151561045f57600080fd5b8073ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f193505050501580156104a5573d6000803e3d6000fd5b508073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167ff0291238ba9315cd60c024111713255791be2e9bf2c56fa98004cf6de980506f346040518082815260200191505060405180910390a350565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561056a57600080fd5b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515156105ef57600080fd5b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156106cd57600080fd5b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415151561075257600080fd5b80600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60026020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561088857600080fd5b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f5c486528ec3e3f0ea91181cff8116f02bfa350e03b8b6f12e00765adbb5af85c60405160405180910390a3505600a165627a7a72305820d5562ba3a3bfdd6ef0c9470f56cd9def9eed7cf875ac2767d7b7ce83a32187360029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_TRANSFERHPB = "transferHpb";

    public static final String FUNC_DELETEADMIN = "deleteAdmin";

    public static final String FUNC_ADDADMIN = "addAdmin";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ADMINMAP = "adminMap";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event TRANSFEROWNERSHIP_EVENT = new Event("TransferOwnership", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event RECEIVEDHPB_EVENT = new Event("ReceivedHpb", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFERHPB_EVENT = new Event("TransferHpb", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected HpbFaucet(String contractAddress, Web3 web3, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3, credentials, gasPrice, gasLimit);
    }

    protected HpbFaucet(String contractAddress, Web3 web3, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3, credentials, contractGasProvider);
    }

    @Deprecated
    protected HpbFaucet(String contractAddress, Web3 web3, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3, transactionManager, gasPrice, gasLimit);
    }

    protected HpbFaucet(String contractAddress, Web3 web3, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferHpb(String to, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_TRANSFERHPB, 
                Arrays.<Type>asList(new io.hpb.web3.abi.datatypes.Address(to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> deleteAdmin(String addr) {
        final Function function = new Function(
                FUNC_DELETEADMIN, 
                Arrays.<Type>asList(new io.hpb.web3.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addAdmin(String addr) {
        final Function function = new Function(
                FUNC_ADDADMIN, 
                Arrays.<Type>asList(new io.hpb.web3.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> adminMap(String param0) {
        final Function function = new Function(FUNC_ADMINMAP, 
                Arrays.<Type>asList(new io.hpb.web3.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new io.hpb.web3.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<TransferOwnershipEventResponse> getTransferOwnershipEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFEROWNERSHIP_EVENT, transactionReceipt);
        ArrayList<TransferOwnershipEventResponse> responses = new ArrayList<TransferOwnershipEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferOwnershipEventResponse typedResponse = new TransferOwnershipEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferOwnershipEventResponse> transferOwnershipEventFlowable(HpbFilter filter) {
        return web3.hpbLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferOwnershipEventResponse>() {
            @Override
            public TransferOwnershipEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFEROWNERSHIP_EVENT, log);
                TransferOwnershipEventResponse typedResponse = new TransferOwnershipEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferOwnershipEventResponse> transferOwnershipEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        HpbFilter filter = new HpbFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFEROWNERSHIP_EVENT));
        return transferOwnershipEventFlowable(filter);
    }

    public List<ReceivedHpbEventResponse> getReceivedHpbEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVEDHPB_EVENT, transactionReceipt);
        ArrayList<ReceivedHpbEventResponse> responses = new ArrayList<ReceivedHpbEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReceivedHpbEventResponse typedResponse = new ReceivedHpbEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ReceivedHpbEventResponse> receivedHpbEventFlowable(HpbFilter filter) {
        return web3.hpbLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ReceivedHpbEventResponse>() {
            @Override
            public ReceivedHpbEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVEDHPB_EVENT, log);
                ReceivedHpbEventResponse typedResponse = new ReceivedHpbEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ReceivedHpbEventResponse> receivedHpbEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        HpbFilter filter = new HpbFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVEDHPB_EVENT));
        return receivedHpbEventFlowable(filter);
    }

    public List<TransferHpbEventResponse> getTransferHpbEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERHPB_EVENT, transactionReceipt);
        ArrayList<TransferHpbEventResponse> responses = new ArrayList<TransferHpbEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferHpbEventResponse typedResponse = new TransferHpbEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferHpbEventResponse> transferHpbEventFlowable(HpbFilter filter) {
        return web3.hpbLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferHpbEventResponse>() {
            @Override
            public TransferHpbEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERHPB_EVENT, log);
                TransferHpbEventResponse typedResponse = new TransferHpbEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferHpbEventResponse> transferHpbEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        HpbFilter filter = new HpbFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERHPB_EVENT));
        return transferHpbEventFlowable(filter);
    }

    @Deprecated
    public static HpbFaucet load(String contractAddress, Web3 web3, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new HpbFaucet(contractAddress, web3, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HpbFaucet load(String contractAddress, Web3 web3,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HpbFaucet(contractAddress, web3, transactionManager, gasPrice, gasLimit);
    }

    public static HpbFaucet load(String contractAddress, Web3 web3, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new HpbFaucet(contractAddress, web3, credentials, contractGasProvider);
    }

    public static HpbFaucet load(String contractAddress, Web3 web3,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HpbFaucet(contractAddress, web3, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HpbFaucet> deploy(Web3 web3, Credentials credentials,
            ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(HpbFaucet.class, web3, credentials, contractGasProvider, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<HpbFaucet> deploy(Web3 web3, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(HpbFaucet.class, web3, transactionManager, contractGasProvider, BINARY, "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<HpbFaucet> deploy(Web3 web3, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(HpbFaucet.class, web3, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<HpbFaucet> deploy(Web3 web3, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(HpbFaucet.class, web3, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static class TransferOwnershipEventResponse {
        public Log log;

        public String from;

        public String to;
    }

    public static class ReceivedHpbEventResponse {
        public Log log;

        public String sender;

        public BigInteger amount;
    }

    public static class TransferHpbEventResponse {
        public Log log;

        public String from;

        public String to;

        public BigInteger value;
    }
}
