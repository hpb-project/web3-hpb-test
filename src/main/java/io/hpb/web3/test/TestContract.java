package io.hpb.web3.test;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.hpb.web3.protocol.Web3Service;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.protocol.ipc.UnixIpcService;
import io.hpb.web3.protocol.ipc.WindowsIpcService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class TestContract {
	private static Log log = LogFactory.getLog(TestContract.class);
	private static final long WEB3_TIMEOUT = 3;
	public static void main(String[] args) throws Exception {
		Admin admin=admin("http://192.168.0.121:8545/");
		String netVersion = admin.netVersion().send().getNetVersion();
		System.out.println(netVersion);
		/*Credentials credentials=Credentials.create("**************************************************");
		RawTransactionManager transactionManager=new RawTransactionManager(admin, credentials, ChainIdLong.MAINNET);
		BigInteger gasPrice = Convert.toWei("18", Convert.Unit.GWEI).toBigInteger();
		BigInteger gasLimit = new BigInteger("7500000");
		StaticGasProvider contractGasProvider=new StaticGasProvider(gasPrice,gasLimit);
		UFOToken token = UFOToken.load("0xfbbe0ba33812b531aced666d0bb2450216c11d11", admin, transactionManager, contractGasProvider);
		BigInteger bigInteger = token.balanceOf("0xfbbe0ba33812b531aced666d0bb2450216c11d11").send();
		System.out.println(bigInteger);
		String transactionHash="";
		HpbGetTransactionReceipt transactionReceipt =
	             admin.hpbGetTransactionReceipt(transactionHash).send();

		if (transactionReceipt.getTransactionReceipt().isPresent()) {
		   // String contractAddress = transactionReceipt.getResult().getContractAddress();
		} else {
		    // try again
		}*/
	}
	public static Admin admin(String clientAddress) {
		Web3Service web3jService = buildService(clientAddress);
		log.info("Building admin service for endpoint: " + clientAddress);
		return Admin.build(web3jService);
	}
	public static Web3Service buildService(String clientAddress) {
		Web3Service web3jService;

		if (clientAddress == null || clientAddress.equals("")) {
			web3jService = new HttpService(createOkHttpClient());
		} else if (clientAddress.startsWith("http")) {
			web3jService = new HttpService(clientAddress, createOkHttpClient(), false);
		} else if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
			web3jService = new WindowsIpcService(clientAddress);
		} else {
			web3jService = new UnixIpcService(clientAddress);
		}

		return web3jService;
	}

	public static OkHttpClient createOkHttpClient() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		configureLogging(builder);
		configureTimeouts(builder);
		return builder.build();
	}

	public static void configureTimeouts(OkHttpClient.Builder builder) {
		builder.connectTimeout(WEB3_TIMEOUT, TimeUnit.SECONDS);
		builder.readTimeout(WEB3_TIMEOUT, TimeUnit.SECONDS); // Sets the socket timeout too
		builder.writeTimeout(WEB3_TIMEOUT, TimeUnit.SECONDS);
	}

	public static void configureLogging(OkHttpClient.Builder builder) {
		if (log.isDebugEnabled()) {
			HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::debug);
			logging.setLevel(HttpLoggingInterceptor.Level.BODY);
			builder.addInterceptor(logging);
		}
	}
}
