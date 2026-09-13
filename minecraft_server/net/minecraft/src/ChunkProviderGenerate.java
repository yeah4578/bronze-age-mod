// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            IChunkProvider, MapGenCaves, NoiseGeneratorOctaves, Block, 
//            BiomeGenBase, Chunk, World, WorldChunkManager, 
//            MapGenBase, BlockSand, WorldGenLakes, WorldGenDungeons, 
//            WorldGenClay, WorldGenMinable, WorldGenerator, WorldGenFlowers, 
//            BlockFlower, WorldGenTallGrass, BlockTallGrass, WorldGenDeadBush, 
//            BlockDeadBush, WorldGenReed, WorldGenPumpkin, WorldGenCactus, 
//            WorldGenLiquids, Material, IProgressUpdate

public class ChunkProviderGenerate
    implements IChunkProvider
{

    public ChunkProviderGenerate(World world, long l)
    {
        sandNoise = new double[256];
        gravelNoise = new double[256];
        stoneNoise = new double[256];
        field_695_u = new MapGenCaves();
        field_707_i = new int[32][32];
        worldObj = world;
        rand = new Random(l);
        field_705_k = new NoiseGeneratorOctaves(rand, 16);
        field_704_l = new NoiseGeneratorOctaves(rand, 16);
        field_703_m = new NoiseGeneratorOctaves(rand, 8);
        field_702_n = new NoiseGeneratorOctaves(rand, 4);
        field_701_o = new NoiseGeneratorOctaves(rand, 4);
        field_715_a = new NoiseGeneratorOctaves(rand, 10);
        field_714_b = new NoiseGeneratorOctaves(rand, 16);
        mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);
    }

    public void generateTerrain(int i, int j, byte abyte0[], BiomeGenBase abiomegenbase[], double ad[])
    {
        byte byte0 = 4;
        byte byte1 = 64;
        int k = byte0 + 1;
        byte byte2 = 17;
        int l = byte0 + 1;
        field_4224_q = func_4058_a(field_4224_q, i * byte0, 0, j * byte0, k, byte2, l);
        for(int i1 = 0; i1 < byte0; i1++)
        {
            for(int j1 = 0; j1 < byte0; j1++)
            {
                for(int k1 = 0; k1 < 16; k1++)
                {
                    double d = 0.125D;
                    double d1 = field_4224_q[((i1 + 0) * l + (j1 + 0)) * byte2 + (k1 + 0)];
                    double d2 = field_4224_q[((i1 + 0) * l + (j1 + 1)) * byte2 + (k1 + 0)];
                    double d3 = field_4224_q[((i1 + 1) * l + (j1 + 0)) * byte2 + (k1 + 0)];
                    double d4 = field_4224_q[((i1 + 1) * l + (j1 + 1)) * byte2 + (k1 + 0)];
                    double d5 = (field_4224_q[((i1 + 0) * l + (j1 + 0)) * byte2 + (k1 + 1)] - d1) * d;
                    double d6 = (field_4224_q[((i1 + 0) * l + (j1 + 1)) * byte2 + (k1 + 1)] - d2) * d;
                    double d7 = (field_4224_q[((i1 + 1) * l + (j1 + 0)) * byte2 + (k1 + 1)] - d3) * d;
                    double d8 = (field_4224_q[((i1 + 1) * l + (j1 + 1)) * byte2 + (k1 + 1)] - d4) * d;
                    for(int l1 = 0; l1 < 8; l1++)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for(int i2 = 0; i2 < 4; i2++)
                        {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            char c = '\200';
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for(int k2 = 0; k2 < 4; k2++)
                            {
                                double d17 = ad[(i1 * 4 + i2) * 16 + (j1 * 4 + k2)];
                                int l2 = 0;
                                if(k1 * 8 + l1 < byte1)
                                {
                                    if(d17 < 0.5D && k1 * 8 + l1 >= byte1 - 1)
                                    {
                                        l2 = Block.ice.blockID;
                                    } else
                                    {
                                        l2 = Block.waterStill.blockID;
                                    }
                                }
                                if(d15 > 0.0D)
                                {
                                    l2 = Block.stone.blockID;
                                }
                                abyte0[j2] = (byte)l2;
                                j2 += c;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }

                }

            }

        }

    }

    public void replaceBlocksForBiome(int i, int j, byte abyte0[], BiomeGenBase abiomegenbase[])
    {
        byte byte0 = 64;
        double d = 0.03125D;
        sandNoise = field_702_n.generateNoiseOctaves(sandNoise, i * 16, j * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
        gravelNoise = field_702_n.generateNoiseOctaves(gravelNoise, i * 16, 109.0134D, j * 16, 16, 1, 16, d, 1.0D, d);
        stoneNoise = field_701_o.generateNoiseOctaves(stoneNoise, i * 16, j * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for(int k = 0; k < 16; k++)
        {
            for(int l = 0; l < 16; l++)
            {
                BiomeGenBase biomegenbase = abiomegenbase[k + l * 16];
                boolean flag = sandNoise[k + l * 16] + rand.nextDouble() * 0.20000000000000001D > 0.0D;
                boolean flag1 = gravelNoise[k + l * 16] + rand.nextDouble() * 0.20000000000000001D > 3D;
                int i1 = (int)(stoneNoise[k + l * 16] / 3D + 3D + rand.nextDouble() * 0.25D);
                int j1 = -1;
                byte byte1 = biomegenbase.topBlock;
                byte byte2 = biomegenbase.fillerBlock;
                for(int k1 = 127; k1 >= 0; k1--)
                {
                    int l1 = (l * 16 + k) * 128 + k1;
                    if(k1 <= 0 + rand.nextInt(5))
                    {
                        abyte0[l1] = (byte)Block.bedrock.blockID;
                        continue;
                    }
                    byte byte3 = abyte0[l1];
                    if(byte3 == 0)
                    {
                        j1 = -1;
                        continue;
                    }
                    if(byte3 != Block.stone.blockID)
                    {
                        continue;
                    }
                    if(j1 == -1)
                    {
                        if(i1 <= 0)
                        {
                            byte1 = 0;
                            byte2 = (byte)Block.stone.blockID;
                        } else
                        if(k1 >= byte0 - 4 && k1 <= byte0 + 1)
                        {
                            byte1 = biomegenbase.topBlock;
                            byte2 = biomegenbase.fillerBlock;
                            if(flag1)
                            {
                                byte1 = 0;
                            }
                            if(flag1)
                            {
                                byte2 = (byte)Block.gravel.blockID;
                            }
                            if(flag)
                            {
                                byte1 = (byte)Block.sand.blockID;
                            }
                            if(flag)
                            {
                                byte2 = (byte)Block.sand.blockID;
                            }
                        }
                        if(k1 < byte0 && byte1 == 0)
                        {
                            byte1 = (byte)Block.waterStill.blockID;
                        }
                        j1 = i1;
                        if(k1 >= byte0 - 1)
                        {
                            abyte0[l1] = byte1;
                        } else
                        {
                            abyte0[l1] = byte2;
                        }
                        continue;
                    }
                    if(j1 <= 0)
                    {
                        continue;
                    }
                    j1--;
                    abyte0[l1] = byte2;
                    if(j1 == 0 && byte2 == Block.sand.blockID)
                    {
                        j1 = rand.nextInt(4);
                        byte2 = (byte)Block.sandStone.blockID;
                    }
                }

            }

        }

    }

    public Chunk loadChunk(int i, int j)
    {
        return provideChunk(i, j);
    }

    public Chunk provideChunk(int i, int j)
    {
        rand.setSeed((long)i * 0x4f9939f508L + (long)j * 0x1ef1565bd5L);
        byte abyte0[] = new byte[32768];
        Chunk chunk = new Chunk(worldObj, abyte0, i, j);
        biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, i * 16, j * 16, 16, 16);
        double ad[] = worldObj.getWorldChunkManager().temperature;
        generateTerrain(i, j, abyte0, biomesForGeneration, ad);
        replaceBlocksForBiome(i, j, abyte0, biomesForGeneration);
        field_695_u.func_667_a(this, worldObj, i, j, abyte0);
        chunk.func_353_b();
        return chunk;
    }

    private double[] func_4058_a(double ad[], int i, int j, int k, int l, int i1, int j1)
    {
        if(ad == null)
        {
            ad = new double[l * i1 * j1];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        double ad1[] = worldObj.getWorldChunkManager().temperature;
        double ad2[] = worldObj.getWorldChunkManager().humidity;
        field_4226_g = field_715_a.func_4103_a(field_4226_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        field_4225_h = field_714_b.func_4103_a(field_4225_h, i, k, l, j1, 200D, 200D, 0.5D);
        field_4229_d = field_703_m.generateNoiseOctaves(field_4229_d, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
        field_4228_e = field_705_k.generateNoiseOctaves(field_4228_e, i, j, k, l, i1, j1, d, d1, d);
        field_4227_f = field_704_l.generateNoiseOctaves(field_4227_f, i, j, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / l;
        for(int j2 = 0; j2 < l; j2++)
        {
            int k2 = j2 * i2 + i2 / 2;
            for(int l2 = 0; l2 < j1; l2++)
            {
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1.0D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0D - d4;
                double d5 = (field_4226_g[l1] + 256D) / 512D;
                d5 *= d4;
                if(d5 > 1.0D)
                {
                    d5 = 1.0D;
                }
                double d6 = field_4225_h[l1] / 8000D;
                if(d6 < 0.0D)
                {
                    d6 = -d6 * 0.29999999999999999D;
                }
                d6 = d6 * 3D - 2D;
                if(d6 < 0.0D)
                {
                    d6 /= 2D;
                    if(d6 < -1D)
                    {
                        d6 = -1D;
                    }
                    d6 /= 1.3999999999999999D;
                    d6 /= 2D;
                    d5 = 0.0D;
                } else
                {
                    if(d6 > 1.0D)
                    {
                        d6 = 1.0D;
                    }
                    d6 /= 8D;
                }
                if(d5 < 0.0D)
                {
                    d5 = 0.0D;
                }
                d5 += 0.5D;
                d6 = (d6 * (double)i1) / 16D;
                double d7 = (double)i1 / 2D + d6 * 4D;
                l1++;
                for(int j3 = 0; j3 < i1; j3++)
                {
                    double d8 = 0.0D;
                    double d9 = (((double)j3 - d7) * 12D) / d5;
                    if(d9 < 0.0D)
                    {
                        d9 *= 4D;
                    }
                    double d10 = field_4228_e[k1] / 512D;
                    double d11 = field_4227_f[k1] / 512D;
                    double d12 = (field_4229_d[k1] / 10D + 1.0D) / 2D;
                    if(d12 < 0.0D)
                    {
                        d8 = d10;
                    } else
                    if(d12 > 1.0D)
                    {
                        d8 = d11;
                    } else
                    {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    d8 -= d9;
                    if(j3 > i1 - 4)
                    {
                        double d13 = (float)(j3 - (i1 - 4)) / 3F;
                        d8 = d8 * (1.0D - d13) + -10D * d13;
                    }
                    ad[k1] = d8;
                    k1++;
                }

            }

        }

        return ad;
    }

    public boolean chunkExists(int i, int j)
    {
        return true;
    }

	private void generateMinable(int ID, int maxBlocks, int tries, int minY, int maxY, int blockX, int blockZ){
		for(int i = 0; i < tries; ++i) {
			int genX = blockX + this.rand.nextInt(16);
			int genY = this.rand.nextInt(maxY-minY)+minY;
			int genZ = blockZ + this.rand.nextInt(16);
			(new WorldGenMinable(ID, maxBlocks)).generate(this.worldObj, this.rand, genX, genY, genZ);
		}
	}

	public void populate(IChunkProvider var1, int chunkX, int chunkY) {
		BlockSand.fallInstantly = true;
		int blockX = chunkX * 16;
		int blockY = chunkY * 16;
		BiomeGenBase whatBiome = this.worldObj.getWorldChunkManager().getBiomeGenAt(blockX + 16, blockY + 16);
		this.rand.setSeed(this.worldObj.getRandomSeed());
		long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)chunkX * var7 + (long)chunkY * var9 ^ this.worldObj.getRandomSeed());
		double var11 = 0.25D;
		int genX;
		int genY;
		int genZ;
		if(this.rand.nextInt(4) == 0) {
			genX = blockX + this.rand.nextInt(16) + 8;
			genY = this.rand.nextInt(128);
			genZ = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenLakes(Block.waterStill.blockID)).generate(this.worldObj, this.rand, genX, genY, genZ);
		}

		if(this.rand.nextInt(8) == 0) {
			genX = blockX + this.rand.nextInt(16) + 8;
			genY = this.rand.nextInt(this.rand.nextInt(120) + 8);
			genZ = blockY + this.rand.nextInt(16) + 8;
			if(genY < 64 || this.rand.nextInt(10) == 0) {
				(new WorldGenLakes(Block.lavaStill.blockID)).generate(this.worldObj, this.rand, genX, genY, genZ);
			}
		}

		int var16;
		for(var16 = 0; var16 < 8; ++var16) {
			genX = blockX + this.rand.nextInt(16) + 8;
			genY = this.rand.nextInt(128);
			genZ = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenDungeons()).generate(this.worldObj, this.rand, genX, genY, genZ);
		}

		for(var16 = 0; var16 < 10; ++var16) {
			genX = blockX + this.rand.nextInt(16);
			genY = this.rand.nextInt(128);
			genZ = blockY + this.rand.nextInt(16);
			(new WorldGenClay(32)).generate(this.worldObj, this.rand, genX, genY, genZ);
		}

		this.generateMinable(Block.dirt.blockID,       32, 20,  0, 128, blockX, blockY);
		this.generateMinable(Block.gravel.blockID,     32, 20,  0, 128, blockX, blockY);

		this.generateMinable(Block.oreCoal.blockID,    16, 20,  0, 128, blockX, blockY);
		this.generateMinable(Block.oreIron.blockID,    16, 20,  0,  64, blockX, blockY);
		this.generateMinable(Block.oreCopper.blockID,   8, 10,  0, 128, blockX, blockY);
		this.generateMinable(Block.oreTin.blockID,      4,  3,  0,  80, blockX, blockY);
		this.generateMinable(Block.oreGold.blockID,     4,  1,  0,  32, blockX, blockY);
		this.generateMinable(Block.oreSilver.blockID,   8,  3,  0,  32, blockX, blockY);
		this.generateMinable(Block.oreRedstone.blockID, 7,  8,  0,  16, blockX, blockY);
		this.generateMinable(Block.oreDiamond.blockID,  7,  1,  0,  16, blockX, blockY);
		this.generateMinable(Block.oreLapis.blockID,    6,  1,  0,  32, blockX, blockY);


		var11 = 0.5D;
		genX = (int)((this.mobSpawnerNoise.func_806_a((double)blockX * var11, (double)blockY * var11) / 8.0D + this.rand.nextDouble() * 4.0D + 4.0D) / 3.0D);
		genY = 0;
		if(this.rand.nextInt(10) == 0) {
			++genY;
		}

		if(whatBiome == BiomeGenBase.forest) {
			genY += genX + 5;
		}

		if(whatBiome == BiomeGenBase.rainforest) {
			genY += genX + 5;
		}

		if(whatBiome == BiomeGenBase.seasonalForest) {
			genY += genX + 2;
		}

		if(whatBiome == BiomeGenBase.taiga) {
			genY += genX + 5;
		}

		if(whatBiome == BiomeGenBase.desert) {
			genY -= 20;
		}

		if(whatBiome == BiomeGenBase.tundra) {
			genY -= 20;
		}

		if(whatBiome == BiomeGenBase.plains) {
			genY -= 20;
		}

		int var17;
		for(genZ = 0; genZ < genY; ++genZ) {
			var16 = blockX + this.rand.nextInt(16) + 8;
			var17 = blockY + this.rand.nextInt(16) + 8;
			WorldGenerator var18 = whatBiome.getRandomWorldGenForTrees(this.rand);
			var18.func_517_a(1.0D, 1.0D, 1.0D);
			var18.generate(this.worldObj, this.rand, var16, this.worldObj.getHeightValue(var16, var17), var17);
		}

		byte var27 = 0;
		if(whatBiome == BiomeGenBase.forest) {
			var27 = 2;
		}

		if(whatBiome == BiomeGenBase.seasonalForest) {
			var27 = 4;
		}

		if(whatBiome == BiomeGenBase.taiga) {
			var27 = 2;
		}

		if(whatBiome == BiomeGenBase.plains) {
			var27 = 3;
		}

		int var19;
		int var25;
		for(var16 = 0; var16 < var27; ++var16) {
			var17 = blockX + this.rand.nextInt(16) + 8;
			var25 = this.rand.nextInt(128);
			var19 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.plantYellow.blockID)).generate(this.worldObj, this.rand, var17, var25, var19);
		}

		byte var28 = 0;
		if(whatBiome == BiomeGenBase.forest) {
			var28 = 2;
		}

		if(whatBiome == BiomeGenBase.rainforest) {
			var28 = 10;
		}

		if(whatBiome == BiomeGenBase.seasonalForest) {
			var28 = 2;
		}

		if(whatBiome == BiomeGenBase.taiga) {
			var28 = 1;
		}

		if(whatBiome == BiomeGenBase.plains) {
			var28 = 10;
		}

		int var20;
		int var21;
		for(var17 = 0; var17 < var28; ++var17) {
			byte var26 = 1;
			if(whatBiome == BiomeGenBase.rainforest && this.rand.nextInt(3) != 0) {
				var26 = 2;
			}

			var19 = blockX + this.rand.nextInt(16) + 8;
			var20 = this.rand.nextInt(128);
			var21 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenTallGrass(Block.tallGrass.blockID, var26)).generate(this.worldObj, this.rand, var19, var20, var21);
		}

		var28 = 0;
		if(whatBiome == BiomeGenBase.desert) {
			var28 = 2;
		}

		for(var17 = 0; var17 < var28; ++var17) {
			var25 = blockX + this.rand.nextInt(16) + 8;
			var19 = this.rand.nextInt(128);
			var20 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenDeadBush(Block.deadBush.blockID)).generate(this.worldObj, this.rand, var25, var19, var20);
		}

		if(this.rand.nextInt(2) == 0) {
			var17 = blockX + this.rand.nextInt(16) + 8;
			var25 = this.rand.nextInt(128);
			var19 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.plantRed.blockID)).generate(this.worldObj, this.rand, var17, var25, var19);
		}

		if(this.rand.nextInt(4) == 0) {
			var17 = blockX + this.rand.nextInt(16) + 8;
			var25 = this.rand.nextInt(128);
			var19 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.flowerIndigo.blockID)).generate(this.worldObj, this.rand, var17, var25, var19);
		}

		if(this.rand.nextInt(4) == 0) {
			var17 = blockX + this.rand.nextInt(16) + 8;
			var25 = this.rand.nextInt(128);
			var19 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.flowerBluebell.blockID)).generate(this.worldObj, this.rand, var17, var25, var19);
		}

		if(this.rand.nextInt(4) == 0) {
			var17 = blockX + this.rand.nextInt(16) + 8;
			var25 = this.rand.nextInt(128);
			var19 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.rand, var17, var25, var19);
		}

		if(this.rand.nextInt(8) == 0) {
			var17 = blockX + this.rand.nextInt(16) + 8;
			var25 = this.rand.nextInt(128);
			var19 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.worldObj, this.rand, var17, var25, var19);
		}

		for(var17 = 0; var17 < 10; ++var17) {
			var25 = blockX + this.rand.nextInt(16) + 8;
			var19 = this.rand.nextInt(128);
			var20 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenReed()).generate(this.worldObj, this.rand, var25, var19, var20);
		}

		if(this.rand.nextInt(32) == 0) {
			var17 = blockX + this.rand.nextInt(16) + 8;
			var25 = this.rand.nextInt(128);
			var19 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenPumpkin()).generate(this.worldObj, this.rand, var17, var25, var19);
		}

		var17 = 0;
		if(whatBiome == BiomeGenBase.desert) {
			var17 += 10;
		}

		for(var25 = 0; var25 < var17; ++var25) {
			var19 = blockX + this.rand.nextInt(16) + 8;
			var20 = this.rand.nextInt(128);
			var21 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenCactus()).generate(this.worldObj, this.rand, var19, var20, var21);
		}

		for(var25 = 0; var25 < 50; ++var25) {
			var19 = blockX + this.rand.nextInt(16) + 8;
			var20 = this.rand.nextInt(this.rand.nextInt(120) + 8);
			var21 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenLiquids(Block.waterMoving.blockID)).generate(this.worldObj, this.rand, var19, var20, var21);
		}

		for(var25 = 0; var25 < 20; ++var25) {
			var19 = blockX + this.rand.nextInt(16) + 8;
			var20 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
			var21 = blockY + this.rand.nextInt(16) + 8;
			(new WorldGenLiquids(Block.lavaMoving.blockID)).generate(this.worldObj, this.rand, var19, var20, var21);
		}

		this.generatedTemperatures = this.worldObj.getWorldChunkManager().getTemperatures(this.generatedTemperatures, blockX + 8, blockY + 8, 16, 16);

		for(var25 = blockX + 8; var25 < blockX + 8 + 16; ++var25) {
			for(var19 = blockY + 8; var19 < blockY + 8 + 16; ++var19) {
				var20 = var25 - (blockX + 8);
				var21 = var19 - (blockY + 8);
				int var22 = this.worldObj.findTopSolidBlock(var25, var19);
				double var23 = this.generatedTemperatures[var20 * 16 + var21] - (double)(var22 - 64) / 64.0D * 0.3D;
				if(var23 < 0.5D && var22 > 0 && var22 < 128 && this.worldObj.isAirBlock(var25, var22, var19) && this.worldObj.getBlockMaterial(var25, var22 - 1, var19).getIsSolid() && this.worldObj.getBlockMaterial(var25, var22 - 1, var19) != Material.ice) {
					this.worldObj.setBlockWithNotify(var25, var22, var19, Block.snow.blockID);
				}
			}
		}

		BlockSand.fallInstantly = false;
	}

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate)
    {
        return true;
    }

    public boolean func_361_a()
    {
        return false;
    }

    public boolean func_364_b()
    {
        return true;
    }

    private Random rand;
    private NoiseGeneratorOctaves field_705_k;
    private NoiseGeneratorOctaves field_704_l;
    private NoiseGeneratorOctaves field_703_m;
    private NoiseGeneratorOctaves field_702_n;
    private NoiseGeneratorOctaves field_701_o;
    public NoiseGeneratorOctaves field_715_a;
    public NoiseGeneratorOctaves field_714_b;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private World worldObj;
    private double field_4224_q[];
    private double sandNoise[];
    private double gravelNoise[];
    private double stoneNoise[];
    private MapGenBase field_695_u;
    private BiomeGenBase biomesForGeneration[];
    double field_4229_d[];
    double field_4228_e[];
    double field_4227_f[];
    double field_4226_g[];
    double field_4225_h[];
    int field_707_i[][];
    private double generatedTemperatures[];
}
