package com.wfe.terrain;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

/**
 * Created by Rick on 08.10.2016.
 */
public class Terrain {

    public static final float SIZE = 768;
    public static final int NUM_OF_LODS = 6;
    public static final int TERRAIN_WIDTH = 256;
    public static final int BLOCK_WIDTH = (int)Math.pow(2, NUM_OF_LODS - 1);
    public static final int TERRAIN_WIDTH_BLOCKS = TERRAIN_WIDTH/BLOCK_WIDTH;
    public final static float BLOCK_SIZE = SIZE/TERRAIN_WIDTH_BLOCKS;

    public static final int VERTICES_PER_NODE = (BLOCK_WIDTH + 1) * (BLOCK_WIDTH + 1);

    private TexturePack texturePack;

    private float x, z;
    private int gridX, gridZ;
    private int vaoID;
    private TerrainBlock[] terrainBlocks;

    private int numberOfNodes;
    private int numPerRow;
    private QuadtreeNode headNode;

    public Terrain(int worldX, int worldZ, TexturePack texturePack) {
        this.texturePack = texturePack;
        this.x = worldX * SIZE;
        this.z = worldZ * SIZE;
        this.gridX = worldX;
        this.gridZ = worldZ;
        TerrainVertex[][] heights = null;
        heights = TerrainLoader.load(texturePack.getHeightMap());
        if(heights.length != TERRAIN_WIDTH + 1){
            System.err.println("Terrain width must be " + (TERRAIN_WIDTH + 1) + " vertices");
            System.exit(-1);
        }
        numberOfNodes = (int) Math.round(Math.pow(((heights.length - 1) / BLOCK_WIDTH), 2));
        numPerRow = (int) Math.sqrt(numberOfNodes);
        int verticesCount = VERTICES_PER_NODE * numberOfNodes;
        float[] vertices = new float[verticesCount * 3];
        float[] textureCoords = new float[verticesCount * 2];
        float[] normals = new float[verticesCount * 3];

        headNode = new QuadtreeNode(0, 0, heights, this, vertices, normals, textureCoords);
        terrainBlocks = new TerrainBlock[numberOfNodes];
        headNode.addAllLeavesToList(terrainBlocks);
        vaoID = loadModelToVAO(vertices, normals, textureCoords);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridZ() {
        return gridZ;
    }

    public TexturePack getTexture() {
        return texturePack;
    }

    public int getVaoId() {
        return vaoID;
    }

    public TerrainBlock[] getTerrainBlocks(){
        return terrainBlocks;
    }

    protected void setStitching(TerrainBlock block) {
        int index = block.getIndex();
        int topIndex = getTopNeighbourIndex(index, numPerRow);
        int bottomIndex = getBottomNeighbourIndex(index, numPerRow, numberOfNodes);
        int leftIndex = getLeftNeighbourIndex(index, numPerRow);
        int rightIndex = getRightNeighbourIndex(index, numPerRow);
        setTopStitching(topIndex, block);
        setBottomStitching(bottomIndex, block);
        setLeftStitching(leftIndex, block);
        setRightStitching(rightIndex, block);
    }

    private int getLeftNeighbourIndex(int index, int numPerRow) {
        if (index % numPerRow == 0) {
            return -1;
        } else {
            return index - 1;
        }
    }

    private int getRightNeighbourIndex(int index, int numPerRow) {
        int rightNeighbour = index + 1;
        if (rightNeighbour % numPerRow == 0) {
            return -1;
        } else {
            return rightNeighbour;
        }
    }

    private int getTopNeighbourIndex(int index, int numPerRow) {
        int topNeighbour = index - numPerRow;
        if (topNeighbour < 0) {
            return -1;
        } else {
            return topNeighbour;
        }
    }

    private int getBottomNeighbourIndex(int index, int numPerRow, int totalNumber) {
        int bottomNeighbour = index + numPerRow;
        if (bottomNeighbour >= totalNumber) {
            return -1;
        } else {
            return bottomNeighbour;
        }
    }

    private void setTopStitching(int topIndex, TerrainBlock block) {
        if (topIndex < 0) {
            block.setStitchTop(false);
        } else {
            if (terrainBlocks[topIndex].getLOD() > block.getLOD()) {
                block.setStitchTop(true);
            } else {
                block.setStitchTop(false);
            }
        }
    }

    private void setBottomStitching(int bottomIndex, TerrainBlock block) {
        if (bottomIndex < 0) {
            block.setStitchBottom(false);
        } else {
            if (terrainBlocks[bottomIndex].getLOD() > block.getLOD()) {
                block.setStitchBottom(true);
            } else {
                block.setStitchBottom(false);
            }
        }
    }

    private void setLeftStitching(int leftIndex, TerrainBlock block) {
        if (leftIndex < 0) {
            block.setStitchLeft(false);
        } else {
            if (terrainBlocks[leftIndex].getLOD() > block.getLOD()) {
                block.setStitchLeft(true);
            } else {
                block.setStitchLeft(false);
            }
        }
    }

    private void setRightStitching(int rightIndex, TerrainBlock block) {
        if (rightIndex < 0) {
            block.setStitchRight(false);
        } else {
            if (terrainBlocks[rightIndex].getLOD() > block.getLOD()) {
                block.setStitchRight(true);
            } else {
                block.setStitchRight(false);
            }
        }
    }

    public static int loadModelToVAO(float[] vertices, float[] normals, float[] textureCoords) {
        int vertexArrayID = createVAO();
        storeInterleavedDataInVAO(vertexArrayID, vertices, normals, textureCoords);
        return vertexArrayID;
    }

    private static int createVAO() {
        int vertexArrayID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayID);
        return vertexArrayID;
    }

    private static void storeInterleavedDataInVAO(int vaoID, float[] vertices, float[] normals,
                                                  float[] textureCoords) {
        FloatBuffer interleavedData = interleaveDataInBuffer(vertices, normals, textureCoords);
        int bufferObjectID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferObjectID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, interleavedData, GL15.GL_STATIC_DRAW);

        int vertexByteCount = 4 * (3 + 3 + 2);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, vertexByteCount, 0);
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, vertexByteCount,
                4 * 3);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, vertexByteCount,
                4 * 6);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    private static FloatBuffer interleaveDataInBuffer(float[] vertices, float[] normals,
                                                      float[] textureCoords) {
        FloatBuffer interleavedBuffer = BufferUtils.createFloatBuffer(vertices.length
                + normals.length + textureCoords.length);
        int veticesPointer = 0;
        int normalsPointer = 0;
        int texturePointer = 0;
        for (int i = 0; i < vertices.length / 3; i++) {
            interleavedBuffer.put(new float[] { vertices[veticesPointer++],
                    vertices[veticesPointer++], vertices[veticesPointer++] });
            interleavedBuffer.put(new float[] { normals[normalsPointer++],
                    normals[normalsPointer++], normals[normalsPointer++] });
            interleavedBuffer.put(new float[] { textureCoords[texturePointer++],
                    textureCoords[texturePointer++] });
        }
        interleavedBuffer.flip();
        return interleavedBuffer;
    }

}
