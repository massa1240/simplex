class Cell{
	
	private double top, bottom;

	public Cell(){
		this.top = 0;	
		this.bottom = 0;
	} 

	public Cell(double top){
		this.top = top;	
		this.bottom = 0;
	} 

	public void setTop(double top) {
		this.top = top;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public int getTop() {
		return this.top;
	}

	public int getBottom() {
		return this.bottom;
	}

	public void cloneBottomToTop(){
		this.top = this.bottom;
	}

	public void cloneTopToBottom(){
		this.bottom = this.top;
	}
}