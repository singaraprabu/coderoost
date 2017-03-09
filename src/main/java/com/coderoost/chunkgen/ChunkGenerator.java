package com.coderoost.chunkgen;

import java.util.HashMap;

public interface ChunkGenerator {
	void init();
	void init(int min_blk_size, int avg_blk_size, int max_blk_size);
	HashMap<Integer,Integer> generate(byte[] data);
}