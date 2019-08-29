package io.hpb.web3.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import io.hpb.web3.codegen.SolidityFunctionWrapperGenerator;
import io.hpb.web3.contract.UFOToken;
import io.hpb.web3.crypto.Credentials;
import io.hpb.web3.crypto.WalletUtils;
import io.hpb.web3.protocol.Web3Service;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.core.DefaultBlockParameterName;
import io.hpb.web3.protocol.core.methods.response.TransactionReceipt;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.tx.ChainIdLong;
import io.hpb.web3.tx.RawTransactionManager;
import io.hpb.web3.tx.gas.StaticGasProvider;
import io.hpb.web3.utils.Convert;
import okhttp3.OkHttpClient;

public class UFOTokenRun {

	// Keystore Full Path for Publishing Intelligent Contract Accounts
	private static String keyStoreAbsolutePath = "/Users/hpb2017/path/hpbkey/UTC--2018-09-13T10-07-27.074Zd0e114";
	// Publish passwords for smart contracts
	private static String fromPassword = "demo111111";
	// Publish the address of the intelligent contract: This is the UFOToken
	// intelligent contract address which has been published to the main network.
	// Users can query, but can not transfer. Transfer needs HPB balance to
	// transfer.
	private static String address = "0xfbbe0ba33812b531aced666d0bb2450216c11d11";
	// Public Node of HPB Main Network
	private static String blockChainUrl = "http://mainnet.hpb.io/";

	private static StaticGasProvider staticGasProvider=new StaticGasProvider(
			Convert.toWei("18", Convert.Unit.GWEI).toBigInteger(),  
			Convert.toWei("99000000", Convert.Unit.HPB).toBigInteger());
	
	public static void main(String[] args) {

		String packageName = "io.hpb.web3.contract";
		String outDirPath = "//Users//hpb2017//test//erc20//UFO//java";

		String binFilePath = "//Users//hpb2017//test//erc20//UFO//bin//UFOToken.bin";
		String abiFilePath = "//Users//hpb2017//test//erc20//UFO//bin//UFOToken.abi";

		// 1、Generate the mapping class of intelligent contract sol source code by io.
		// hpb. web3; then put the mapping class into the corresponding package
		GenContractJavaCode(packageName, binFilePath, abiFilePath, outDirPath);

		// 2、Publish Smart Contracts and Get Addresses
		depolyUFOTokenTest();

		// 3、Get the balance of the smart contract
		getUFOTokenContractBalance();

		// Query erc20 balance at specified address
		String queryAddress = "0xd8ACcED8A7A92b334007f9C6127A848fE51D3C3b";

		// 4、Check smart contracts and print relevant information
		checkUFOTokenContract(queryAddress);

		// 5、Transfer accounts
		String toAddress = "0x6cb988b9ce48Fd3b5a328B582dd64F5C10d0E114";
		transferUFOTokenContract(toAddress, "13333333000000000000000000");

		// 5.2 Check the balance
		// checkUFOTokenContract(contractAddress,toAddress);

	}

	public static void GenContractJavaCode(String packageName, String binFilePath, String abiFilePath,
			String outDirPath) {
		try {
			SolidityFunctionWrapperGenerator.main(
					Arrays.asList(
							"-b", binFilePath, 
							"-a", abiFilePath, 
							"-p", packageName, 
							"-o", outDirPath
					).toArray(new String[0])
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Compiling Intelligent Contract Source Code to Get the Java Class of Contract
	 * Mapping
	 * 
	 **/
	public static String depolyUFOTokenTest() {

		Credentials credentials = null;
		Admin admin = null;

		String contractAddress = "";
		try {
			Web3Service web3Service = new HttpService(blockChainUrl, new OkHttpClient.Builder().build(), true);
			admin = Admin.build(web3Service);
			credentials = WalletUtils.loadCredentials(fromPassword, keyStoreAbsolutePath);
			RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials,
					ChainIdLong.MAINNET);

			// 1.Release TOKEN
			UFOToken contract = UFOToken.deploy(admin, transactionManager, staticGasProvider).send();
			System.out.println("Contract address：" + contract.getContractAddress());
			contractAddress = contract.getContractAddress();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractAddress;
	}

	/**
	 * Check the balance
	 * 
	 **/
	public static BigDecimal getUFOTokenContractBalance() {

		Admin admin = null;
		BigDecimal balanceWeiAmt = null;

		try {

			Web3Service web3Service = new HttpService(blockChainUrl, new OkHttpClient.Builder().build(), true);
			admin = Admin.build(web3Service);

			BigInteger balance = admin.hpbGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
			balanceWeiAmt = Convert.fromWei(balance.toString(), Convert.Unit.HPB);
			System.out.println(address + "Account balance：" + balanceWeiAmt);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return balanceWeiAmt;
	}

	/**
	 * Check the smart contract and print the address ERC20 balance
	 * 
	 **/
	public static void checkUFOTokenContract(String queryAddress) {

		Credentials credentials = null;
		Admin admin = null;

		try {

			Web3Service web3Service = new HttpService(blockChainUrl, new OkHttpClient.Builder().build(), true);
			admin = Admin.build(web3Service);
			credentials = WalletUtils.loadCredentials(fromPassword, keyStoreAbsolutePath);
			RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials,
					ChainIdLong.MAINNET);

			// Check whether the contract is available

			UFOToken contract = UFOToken.load(address, admin, transactionManager,staticGasProvider);
			System.out.println("Verify the validity of the contract:" + contract.isValid());
			if (contract.isValid()) {
				BigInteger totalSupply = contract.totalSupply().send()
						.divide(new BigInteger("1000000000000000000"));
				System.out.println("UFOtoken Total Supply：" + totalSupply);
				System.out.println(address + " UFOToken balance：" + contract.balanceOf(address).sendAsync()
						.get().divide(new BigInteger("1000000000000000000")));
				System.out.println(queryAddress + " UFOToken balance：" + contract.balanceOf(queryAddress)
						.sendAsync().get().divide(new BigInteger("1000000000000000000")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toDecimal(int decimal, BigInteger integer) {
		StringBuffer sbf = new StringBuffer("1");
		for (int i = 0; i < decimal; i++) {
			sbf.append("0");
		}
		String balance = new BigDecimal(integer).divide(new BigDecimal(sbf.toString()), 18, BigDecimal.ROUND_DOWN)
				.toPlainString();
		return balance;
	}

	/**
	 * Transfer accounts
	 * 
	 **/
	public static void transferUFOTokenContract(String toAddress, String toValue) {

		Credentials credentials = null;
		Admin admin = null;
		try {

			Web3Service web3Service = new HttpService(blockChainUrl, new OkHttpClient.Builder().build(), true);
			admin = Admin.build(web3Service);
			credentials = WalletUtils.loadCredentials(fromPassword, keyStoreAbsolutePath);
			RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials,
					ChainIdLong.MAINNET);

			// Transfer transaction
			UFOToken contract = UFOToken.load(address, admin, transactionManager,staticGasProvider);
			TransactionReceipt receipt = contract.transfer(toAddress, new BigInteger(toValue)).send();
			System.out.println("transaction Hash::::::" + receipt.getTransactionHash());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
