package io.hpb.web3.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.shared.utils.io.FileUtils;

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
		String filePath="D:\\ethWeb3j\\web3-hpb\\src\\main\\java\\io\\hpb\\web3\\protocol\\core\\methods\\response\\";
		File f=new File(filePath);
		File[] listFiles = f.listFiles();
		for(File ff:listFiles) {
			String name = ff.getName();
			System.out.println(name);
			if(name.indexOf("Eth")>-1) {
				String replace = name.replace("Eth", "Hpb");
				String replace2 = ff.getAbsolutePath().replace(name,replace);
				System.out.println(replace2);
				ff.renameTo(new File(replace2));
			}	
		}
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
