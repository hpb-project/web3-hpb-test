package io.hpb.web3.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hpb.web3.abi.FunctionEncoder;
import io.hpb.web3.abi.FunctionReturnDecoder;
import io.hpb.web3.abi.TypeReference;
import io.hpb.web3.abi.datatypes.Address;
import io.hpb.web3.abi.datatypes.DynamicArray;
import io.hpb.web3.abi.datatypes.Function;
import io.hpb.web3.abi.datatypes.Type;
import io.hpb.web3.abi.datatypes.generated.Bytes32;
import io.hpb.web3.crypto.Credentials;
import io.hpb.web3.crypto.RawTransaction;
import io.hpb.web3.crypto.WalletUtils;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.core.DefaultBlockParameterName;
import io.hpb.web3.protocol.core.DefaultBlockParameterNumber;
import io.hpb.web3.protocol.core.methods.request.Transaction;
import io.hpb.web3.protocol.core.methods.response.HpbBlockNumber;
import io.hpb.web3.protocol.core.methods.response.HpbGetBalance;
import io.hpb.web3.protocol.core.methods.response.HpbGetTransactionCount;
import io.hpb.web3.protocol.core.methods.response.HpbGetTransactionReceipt;
import io.hpb.web3.protocol.core.methods.response.HpbSendTransaction;
import io.hpb.web3.protocol.core.methods.response.TransactionReceipt;
import io.hpb.web3.tuples.generated.Tuple4;
import io.hpb.web3.tx.ChainIdLong;
import io.hpb.web3.tx.RawTransactionManager;
import io.hpb.web3.utils.Convert;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class HpbWeb3Controller{
	private static Log log = LogFactory.getLog(HpbWeb3Controller.class);
	private final long WEB3J_TIMEOUT = 3;
	private final String contractAddr = "0x2251a2533556e7c6243a73f015eb96aa155c5791";
	private final BigInteger gasPrice = Convert.toWei("18", Convert.Unit.GWEI).toBigInteger();
	private final BigInteger gasLimit = new BigInteger("95000000");
	@Autowired
	private Admin admin;
	@ApiOperation(value="Search the transaction receipt according to the transaction hash",notes = ""
			+ " reqStrList [  Parameter1：transaction hash]")
	@PostMapping("/QueryByHash")
	public List<Object> QueryByHash(@RequestBody List<String> reqStrList)throws Exception{
		List<Object> list=new ArrayList<Object>();
		if(reqStrList!=null&&reqStrList.size()>0) {
			String transactionHash = reqStrList.get(0);
			HpbGetTransactionReceipt receipt = admin.hpbGetTransactionReceipt(transactionHash).send();
			if(!receipt.hasError()) {
				TransactionReceipt transactionReceipt = receipt.getResult();
				if(transactionReceipt.isStatusOK()) {
					list.add(transactionReceipt);
				}
			}
		}
		return list;
	}
	@ApiOperation(value="parameter",notes = "")
	@PostMapping("/getCurrentBlock")
	public List<Object> getCurrentBlock()throws Exception{
		List<Object> list=new ArrayList<Object>();
		HpbBlockNumber blockNumber = admin.hpbBlockNumber().sendAsync().get(WEB3J_TIMEOUT, TimeUnit.MINUTES);
		list.add(blockNumber);
		return list;
	}
	@ApiOperation(value="Obtaining the current account Nonce",notes = " "
			+ " reqStrList [ Parameter1：Account address;]")
	@PostMapping("/getCurrentNonce")
	public List<Object> getCurrentNonce(@RequestBody List<String> reqStrList)throws Exception{
		List<Object> list=new ArrayList<Object>();
		if(reqStrList!=null&&reqStrList.size()>0) {
			String address =reqStrList.get(0);
			HpbGetTransactionCount transactionCount = admin.hpbGetTransactionCount(address, 
					DefaultBlockParameterName.PENDING).sendAsync().get(WEB3J_TIMEOUT, TimeUnit.MINUTES);
			BigInteger nonce = transactionCount.getTransactionCount();
			log.info(nonce);
			list.add(nonce);
		}
		return list;
	}
	@ApiOperation(value="Obtain the balance of the current account",notes = ""
			+ " reqStrList [ Parameter1：Account address; ]")
	@PostMapping("/getBalance")
	public List<Object> getBalance(@RequestBody List<String> reqStrList)throws Exception{
		List<Object> list=new ArrayList<Object>();
		if(reqStrList!=null&&reqStrList.size()>0) {
			String address =reqStrList.get(0);
			HpbGetBalance balance = admin.hpbGetBalance(address, DefaultBlockParameterName.LATEST).send();
			log.info(balance);
			list.add(balance);
		}
		return list;
	}
	@ApiOperation(value="Sending transaction",notes = ""
			+ " reqStrList [ Parameter1：Account keystore address; Parameter2：Password; Parameter3：Receiving Account Address;Parameter4：Transfer amount;]")
	@PostMapping("/sendTransaction")
	public List<Object> sendTransaction(@RequestBody List<String> reqStrList)throws Exception{
		List<Object> list=new ArrayList<Object>();
		if(reqStrList!=null&&reqStrList.size()>3) {
			String keystore =reqStrList.get(0);
			String password =reqStrList.get(1);
			Credentials credentials = WalletUtils.loadCredentials(password, keystore);
			RawTransactionManager transactionManager=new RawTransactionManager(admin, credentials, ChainIdLong.MAINNET);
			HpbGetTransactionCount transactionCount = admin.hpbGetTransactionCount(credentials.getAddress(), 
					DefaultBlockParameterName.PENDING).sendAsync().get(WEB3J_TIMEOUT, TimeUnit.MINUTES);
			BigInteger nonce = transactionCount.getTransactionCount();
			String to =reqStrList.get(2);
			String value =reqStrList.get(3);
			RawTransaction rawTransaction = RawTransaction.createTransaction(
					nonce,
					gasPrice,
					gasLimit,
					to,
					Convert.toWei(value, Convert.Unit.HPB).toBigInteger(),
					"");
			HpbSendTransaction transaction = transactionManager.signAndSend(rawTransaction);
			log.info(transaction.getTransactionHash());
			list.add(transaction);
		}
		return list;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value="Call HpbNodes Smart Contract",notes = ""+ " reqStrList [ Parameter1：BlockParameter;]")
	@PostMapping("/invokeHpbNodes")
	public List<Object> invokeHpbNodes(@RequestBody List<String> reqStrList)throws Exception{
		List<Object> list=new ArrayList<Object>();
		final Function function = new Function("getAllHpbNodes", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}, 
                		new TypeReference<DynamicArray<Bytes32>>() {}, 
                		new TypeReference<DynamicArray<Bytes32>>() {}, 
                		new TypeReference<DynamicArray<Bytes32>>() {}));
		String encodedFunction = FunctionEncoder.encode(function);
		String value = admin.hpbCall(
                Transaction.createHpbCallTransaction(contractAddr, contractAddr, encodedFunction),
                new DefaultBlockParameterNumber(new BigInteger(reqStrList.get(0))))
        .send()
        .getValue();
		List<Type> results = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        Tuple4<List<String>, List<byte[]>, List<byte[]>, List<byte[]>> tuple4 = new Tuple4<List<String>, List<byte[]>, List<byte[]>, List<byte[]>>(
                convertToNative((List<Address>) results.get(0).getValue()), 
                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                convertToNative((List<Bytes32>) results.get(2).getValue()), 
                convertToNative((List<Bytes32>) results.get(3).getValue()));
		list.add(tuple4);
		return list;
	}
	@SuppressWarnings("unchecked")
	protected static <S extends Type<?>, T> List<T> convertToNative(List<S> arr) {
        List<T> out = new ArrayList<>();
        for (final S s : arr) {
            out.add((T) s.getValue());
        }
        return out;
    }
}
