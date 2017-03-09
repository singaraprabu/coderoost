package com.coderoost.rabinchunk;

import java.math.BigInteger;
import java.util.HashMap;

public class RabinSplitterContext {
	private int windowsSize;
	private int minBlockSize;
	private int avgBlockSize;
	private int maxBlockSize;
	private int prime;
	private BigInteger fingerPrint;
	private BigInteger polynomialArray[];
	private RabinNode rabinRing[];
	private RabinNode currRabinNode;
	private int currentOffset;
	private int currentLength;
	
	public int getWindowsSize() {
		return windowsSize;
	}
	public void setWindowsSize(int windows_size) {
		this.windowsSize = windows_size;
	}
	public int getMinBlockSize() {
		return minBlockSize;
	}
	public void setMinBlockSize(int minBlockSize) {
		this.minBlockSize = minBlockSize;
	}
	public int getAvgBlockSize() {
		return avgBlockSize;
	}
	public void setAvgBlockSize(int avgblocksize) {
		this.avgBlockSize = avgblocksize;
	}
	public int getMaxBlockSize() {
		return maxBlockSize;
	}
	public void setMaxBlockSize(int maxBlockSize) {
		this.maxBlockSize = maxBlockSize;
	}
	public int getPrime() {
		return prime;
	}
	public void setPrime(int prime) {
		this.prime = prime;
	}
	public BigInteger getFingerPrint() {
		return fingerPrint;
	}
	public void setFingerPrint(BigInteger findgerPrint) {
		this.fingerPrint = findgerPrint;
	}
	public BigInteger[] getPolynomialArray() {
		return polynomialArray;
	}
	public void setPolynomialArray(BigInteger[] polynomialArray) {
		this.polynomialArray = polynomialArray;
	}
	public RabinNode[] getRabinRing() {
		return rabinRing;
	}
	public void setRabinRing(RabinNode[] rabinRing) {
		this.rabinRing = rabinRing;
	}
	public RabinNode getCurrRabinNode() {
		return currRabinNode;
	}
	public void setCurrRabinNode(RabinNode currRabinNode) {
		this.currRabinNode = currRabinNode;
	}
	public int getCurrentOffset() {
		return currentOffset;
	}
	public void setCurrent_offset(int currentOffset) {
		this.currentOffset = currentOffset;
	}
	public int getCurrentLength() {
		return currentLength;
	}
	public void setCurrent_length(int currentLength) {
		this.currentLength = currentLength;
	}
}
