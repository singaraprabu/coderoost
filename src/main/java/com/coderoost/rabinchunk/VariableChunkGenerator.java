package com.coderoost.rabinchunk;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.stream.IntStream;

import com.coderoost.chunkgen.ChunkGenerator;

/**
 * @author singaraprabu
 *	Implement Rabin karp method to generate variable chunks
 */
public class VariableChunkGenerator implements ChunkGenerator {
	
	final int MAX_PN_SIZE = 256;
	final int MIN_BLOCK_SIZE = 32768; // 32 KB
	final int AVG_BLOCK_SIZE = 65536; //64 KB
	final int MAX_BLOCK_SIZE = 131072; //128 KB
	final int PRIME = 153191; 
	final int WINDOW_SIZE = 48;
	RabinSplitterContext rabinSplitterCtx;
	boolean isInitInvoked = false;
	
	@Override
	public void init() {
		rabinSplitterCtx = new RabinSplitterContext(); 
		set_default_variables();
		initPrerequisite();
		isInitInvoked = true;
	}
	
	@Override
	public void init(int minBlkSize, int avgBlkSize, int maxBlkSize) {
		init();
		rabinSplitterCtx.setMinBlockSize(minBlkSize);
		rabinSplitterCtx.setAvgBlockSize(avgBlkSize);
		rabinSplitterCtx.setMaxBlockSize(maxBlkSize);
		initPrerequisite();
		isInitInvoked = true;
	}
	
	public void init(int minBlkSize, int avgBlkSize, int maxBlkSize, int prime, int windowSize) {
		rabinSplitterCtx = new RabinSplitterContext(); 
		rabinSplitterCtx.setMinBlockSize(minBlkSize);
		rabinSplitterCtx.setAvgBlockSize(avgBlkSize);
		rabinSplitterCtx.setMaxBlockSize(maxBlkSize);
		rabinSplitterCtx.setPrime(prime);
		rabinSplitterCtx.setWindowsSize(windowSize);
		initPrerequisite();
		isInitInvoked = true;
	}
	
	public void set_default_variables() {
		rabinSplitterCtx.setMinBlockSize(MIN_BLOCK_SIZE);
		rabinSplitterCtx.setAvgBlockSize(AVG_BLOCK_SIZE);
		rabinSplitterCtx.setMaxBlockSize(MAX_BLOCK_SIZE);
		rabinSplitterCtx.setPrime(PRIME);
		rabinSplitterCtx.setWindowsSize(WINDOW_SIZE);
	}
	
	public void initPrerequisite() {
		rabinSplitterCtx.setFingerPrint(BigInteger.ZERO);
		pregeneratePolynomials();
		createRabinRing();
		rabinSplitterCtx.setCurrRabinNode(rabinSplitterCtx.getRabinRing()[0]);
	}
	
	void pregeneratePolynomials() {
		BigInteger result = BigInteger.valueOf(rabinSplitterCtx.getPrime()).pow(rabinSplitterCtx.getWindowsSize());
		
		BigInteger polynomial_array[] = new BigInteger[MAX_PN_SIZE];
		
		IntStream.range(0, MAX_PN_SIZE).forEach(i -> {
			polynomial_array[i] = result.multiply(BigInteger.valueOf(i));
		});
		
		rabinSplitterCtx.setPolynomialArray(polynomial_array);
	}
	
	void createRabinRing() {
		RabinNode rabin_ring[] = new RabinNode[rabinSplitterCtx.getWindowsSize()];
		
		IntStream.range(0, rabinSplitterCtx.getWindowsSize()).forEach(i -> {
			rabin_ring[i]= new RabinNode();
			rabin_ring[i].setValue(0);
		});
		
		IntStream.range(0, rabinSplitterCtx.getWindowsSize()).forEachOrdered(i -> {
			if(i == rabinSplitterCtx.getWindowsSize()-1) {
				rabin_ring[i].setNext(rabin_ring[0]);
			}
			else {
				rabin_ring[i].setNext(rabin_ring[i+1]);
			}
		});
		
		rabinSplitterCtx.setRabinRing(rabin_ring);
	}
	
	@Override
	public HashMap<Integer,Integer> generate(byte[] data) {
		if(!isInitInvoked) {
			System.out.println("Error: init method not invoked");
		}
		System.out.println("Generate method starts");
		HashMap<Integer,Integer> variableChunksMetadata= new HashMap<>();
		
		rabinSplitterCtx.setCurrent_offset(0);
		rabinSplitterCtx.setCurrent_length(0);
		
		IntStream.range(0, data.length).forEachOrdered(i -> {
			int ch = data[i];
			BigInteger finger_print = rabinSplitterCtx.getFingerPrint();
			finger_print = finger_print.multiply(BigInteger.valueOf(rabinSplitterCtx.getPrime()));
			finger_print = finger_print.add(BigInteger.valueOf(ch));
			finger_print = finger_print.subtract(rabinSplitterCtx.getPolynomialArray()[rabinSplitterCtx.getCurrRabinNode().getValue()]);
			rabinSplitterCtx.setFingerPrint(finger_print);
			
			rabinSplitterCtx.getCurrRabinNode().setValue(ch);
			rabinSplitterCtx.setCurrRabinNode(rabinSplitterCtx.getCurrRabinNode().getNext());
			
			if(rabinSplitterCtx.getCurrentLength() > rabinSplitterCtx.getMinBlockSize()) {
				if(rabinSplitterCtx.getFingerPrint().and(BigInteger.valueOf(rabinSplitterCtx.getAvgBlockSize())) == BigInteger.ONE
					|| rabinSplitterCtx.getCurrentLength() >= rabinSplitterCtx.getMaxBlockSize())
				{
					variableChunksMetadata.put(Integer.valueOf(rabinSplitterCtx.getCurrentOffset()), Integer.valueOf(rabinSplitterCtx.getCurrentLength()));
					rabinSplitterCtx.setCurrent_length(0);
					rabinSplitterCtx.setCurrent_offset(rabinSplitterCtx.getCurrentOffset()+i);
				}
			}
			rabinSplitterCtx.setCurrent_length(rabinSplitterCtx.getCurrentLength()+1);
			
		});
		System.out.println("Generate method finish");
		return variableChunksMetadata;
	}
}
