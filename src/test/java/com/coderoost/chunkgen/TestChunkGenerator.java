package com.coderoost.chunkgen;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.coderoost.rabinchunk.VariableChunkGenerator;

import java.nio.file.Path;

public class TestChunkGenerator {

	public void testVariableChunking() throws IOException {
		ChunkGenerator varChunkGenerator = new VariableChunkGenerator();
		varChunkGenerator.init();
		Path path = Paths.get("src/main/resources/Test1.txt");
		byte[] data = Files.readAllBytes(path);
		HashMap<Integer,Integer> resultMap = varChunkGenerator.generate(data);
	}
	
	public static void main(String[] args) throws IOException {
		TestChunkGenerator testChunkGenerator = new TestChunkGenerator();
		testChunkGenerator.testVariableChunking();
	}
}
