
public enum Direction {

	
	UP(0, -10), LEFT(-10, 0), DOWN(0, 10), RIGHT(10, 0), NONE(0, 0);
	
	private int x, y;
	
	
	private Direction(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	public int getXDirection(Direction d){
		return d.x;
	}
	
	public int getYDirection(Direction d){
		return d.y;
	}
	
	
}
