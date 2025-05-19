package view;

final class BoundsDTO {
	private final int xPos, yPos, xSize, ySize;
	
	BoundsDTO(int xPos, int yPos, int xSize, int ySize) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}
}
