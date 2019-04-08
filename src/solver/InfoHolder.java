package solver;

import java.util.ArrayList;

public class InfoHolder {

	private String numLoops = "";
	private ArrayList<ArrayList<Integer>> nonTouching;

	public InfoHolder(String numLoops, ArrayList<ArrayList<Integer>> nonTouching) {
		this.numLoops = numLoops;
		this.nonTouching = nonTouching;
	}

	public void setNumLoops (String numLoops) {
		this.numLoops = numLoops;
	}

	public String getNumLoops() {
		return this.numLoops;
	}

	public ArrayList<ArrayList<Integer>> getNonTouching(){
		return this.nonTouching;
	}

}
