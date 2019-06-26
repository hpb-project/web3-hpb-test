package io.hpb.web3.test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import io.hpb.web3.contract.RedPacketToken;
import io.hpb.web3.crypto.Credentials;
import io.hpb.web3.crypto.WalletUtils;
import io.hpb.web3.protocol.Web3Service;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.tx.ChainIdLong;
import io.hpb.web3.tx.RawTransactionManager;
import io.hpb.web3.tx.gas.StaticGasProvider;
import io.hpb.web3.utils.Convert;
import okhttp3.OkHttpClient;

public class TestWeb3 {
	private static StaticGasProvider staticGasProvider = new StaticGasProvider(Convert.toWei("18", Convert.Unit.GWEI),
			Convert.toWei("99000000", Convert.Unit.HPB));
	// Keystore Full Path for Publishing Intelligent Contract Accounts
	private static String keyStoreAbsolutePath = "/Users/hpb2017/path/hpbkey/UTC--2018-09-13T10-07-27.074Zd0e114";
	// Publish passwords for smart contracts
	private static String fromPassword = "demo111111";

	public static void main(String[] args) throws Exception {
		/*final WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://192.168.0.121:8546/"));
		final boolean includeRawResponses = false;
		final WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses);
		webSocketService.connect();
		final Web3 web3 = Web3.build(webSocketService);
		final Flowable<NewHeadsNotification> notifications = web3.newHeadsNotifications();
		notifications.subscribe(b ->{
			System.out.println(b);
		});*/
		String y="000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000000000e0000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000001200000000000000000000000000000000000000000000000000000000000000002000000000000000000000000c2a25725e92d817e3aeed4dcc5be2b03d883bf26000000000000000000000000c2a25725e92d817e3aeed4dcc5be2b03d883bf26000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000002386f26fc10000000000000000000000000000000000000000000000000000002386f26fc10000";
		String m="000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000000000e0000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000001200000000000000000000000000000000000000000000000000000000000000002000000000000000000000000c2a25725e92d817e3aeed4dcc5be2b03d883bf26000000000000000000000000c2a25725e92d817e3aeed4dcc5be2b03d883bf26000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002";
		List<String> _tos=Arrays.asList(new String[] {"0xc2a25725e92d817e3aeed4dcc5be2b03d883bf26","0xc2a25725e92d817e3aeed4dcc5be2b03d883bf26"});
		String _name="";
        String _desc="";
        
        List<BigInteger> _values=Arrays.asList(new BigInteger[] {
        		Convert.toWei("0.01", Convert.Unit.HPB).toBigInteger(),
        		Convert.toWei("0.01", Convert.Unit.HPB).toBigInteger()
        });
        Web3Service web3Service = new HttpService("", new OkHttpClient.Builder().build(), true);
        Admin admin = Admin.build(web3Service);
		Credentials credentials = WalletUtils.loadCredentials(fromPassword, keyStoreAbsolutePath);
		RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials,
				ChainIdLong.MAINNET);
        RedPacketToken packetToken = RedPacketToken.load("", admin, transactionManager, staticGasProvider);
        packetToken.mintDedaultBatch(_tos, _name, _desc, _values, BigInteger.ZERO).send();
	}

}
