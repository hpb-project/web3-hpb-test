package io.hpb.web3.test;

import java.util.Arrays;

import io.hpb.web3.codegen.SolidityFunctionWrapperGenerator;

public class GenContract {

	public static void main(String[] args) {
		String packageName = "io.hpb.web3.contract";
		String outDirPath = "C:\\Users\\ThinkPad\\git\\web3-hpb-test\\src\\main\\java";

		String binFilePath = "C:\\Users\\ThinkPad\\git\\web3-hpb-test\\src\\main\\java\\io\\hpb\\web3\\test\\HpbFaucet.bin";
		String abiFilePath = "C:\\Users\\ThinkPad\\git\\web3-hpb-test\\src\\main\\java\\io\\hpb\\web3\\test\\HpbFaucet.abi";
		GenContractJavaCode(packageName, binFilePath, abiFilePath, outDirPath);
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
}
