package branch_and_boud;

public class NodeTree {
	
	private double z;
	private double [] constraints;
	private int status;
	
	
	 public NodeTree(double z, double [] constraints) {
		 this.setZ(z);
		 this.setConstraints(constraints);
	 }	

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double [] getConstraints() {
		return constraints;
	}

	public void setConstraints(double [] constraints) {
		this.constraints = constraints;
	}
	
	

}
