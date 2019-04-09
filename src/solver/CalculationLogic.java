package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import solver.InfoHolder;

public class CalculationLogic {
	private String paths = "";
	private int arr[][];
	private String arr1[][];
	private ArrayList<ArrayList<Integer>> forward;
	private ArrayList<ArrayList<Integer>> loop = new ArrayList<>();
	private ArrayList<InfoHolder> single;

	public CalculationLogic(int arr[][], String arr2[][]) {
		this.arr = arr;
		this.arr1 = arr2;
	}

	private String forwardSeq(int startPoint) { // Detecting forward paths in one string
		for (int i = startPoint + 1; i < arr[0].length; i++) {
			if (arr[startPoint][i] == 1) {
				if (!paths.endsWith(Integer.toString(startPoint))) {
					paths += ",";
					paths += startPoint;
				}
				paths += ",";
				paths += i;
				forwardSeq(i);
			}
		}
		return paths;
	}

	private void detectLoops() { // Detect single loops
		loop = new ArrayList<>();
		ArrayList<Integer> singleLoop = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if (i > j && arr[i][j] == 1) {
					for (int x = 0; x < forward.size(); x++) {
						if (forward.get(x).indexOf(i) >= 0 && forward.get(x).indexOf(j) >= 0) {
							for (int y = forward.get(x).indexOf(j); y <= forward.get(x).indexOf(i); y++) {
								singleLoop.add(forward.get(x).get(y));
							}
							singleLoop.add(j);
							loop.add((ArrayList<Integer>) singleLoop.clone());
							singleLoop.clear();
						}
					}
				} else if (i == j && arr[i][j] == 1) {
					singleLoop.add(i);
					singleLoop.add(j);
					System.out.println(i);
					loop.add((ArrayList<Integer>) singleLoop.clone());
					singleLoop.clear();
				}
			}
		}
	}

	private void forwardBaths(String paths) { // Switch forward paths' string into array of integers

		String[] s = paths.split(",");
		ArrayList<Integer> forwards = new ArrayList<>();
		forward = new ArrayList<>();

		for (int i = 1; i < s.length; i++) {
			if (s[i].equals(String.valueOf(arr.length - 1))) {
				forwards.add(Integer.valueOf(s[i]));
				forward.add((ArrayList<Integer>) forwards.clone());
				for (int j = 0; j < forwards.size(); j++) {
					if (j == forwards.size() - 1) {
					}
				}
				forwards.clear();
				if ((i + 1) < s.length && Integer.valueOf(s[i + 1]) != 0) {
					for (int x = 0; x < Integer.valueOf(s[i + 1]); x++) {
						forwards.add(x);
					}
				}
			} else {
				forwards.add(Integer.valueOf(s[i]));
			}
		}

	}

	private void detectTwoNonTouching() { // Detecting two non touching loops
		ArrayList<ArrayList<Integer>> twoNonTouching = new ArrayList<>();
		String numLoops = "";
		single = new ArrayList<>();

		for (int i = 0; i < loop.size(); i++) {
			for (int j = i + 1; j < loop.size(); j++) {
				if (checker(loop.get(i), loop.get(j))) {
					twoNonTouching.add(loop.get(i));
					twoNonTouching.add(loop.get(j));
					numLoops = "";
					numLoops += i;
					numLoops += j;
					single.add(new InfoHolder(numLoops, (ArrayList<ArrayList<Integer>>) twoNonTouching.clone()));
					twoNonTouching.clear();
				}
			}
		}

	}

	// Checking two non touching loops
	private boolean checker(ArrayList<Integer> loop1, ArrayList<Integer> loop2) {
		Set<Integer> set = new HashSet<>();

		for (int i = 0; i < loop1.size(); i++) {
			set.add(loop1.get(i));
		}

		for (int i = 0; i < loop2.size(); i++) {
			set.add(loop2.get(i));
		}

		int x = loop1.size();
		int y = loop2.size();
		int z = set.size();

		if (set.size() == loop1.size() + loop2.size() - 2) {
			return true;
		} else {
			return false;
		}
	}

	private int[] contain(InfoHolder arr1, InfoHolder arr2) {
		int counter = 0;
		int[] positions = new int[2];

		for (int i = 0; i < arr1.getNonTouching().size(); i++) {
			if (arr2.getNonTouching().contains(arr1.getNonTouching().get(i))) {
				counter++;
			} else {
				positions[0] = i;
				continue;
			}
		}

		for (int i = 0; i < arr2.getNonTouching().size(); i++) {
			if (arr1.getNonTouching().contains(arr2.getNonTouching().get(i))) {
				// counter ++;
			} else {
				positions[1] = i;
				continue;
			}
		}

		if (counter == arr1.getNonTouching().size() - 1) {
			return positions;
		} else {
			return null;
		}
	}

	private void getNonTouching(ArrayList<InfoHolder> arr) {
		int k = 0;
		Set<ArrayList<Integer>> set = new HashSet<>();
		Set<String> set1 = new HashSet<>();
		char[] chars;
		int[] positions;
		StringBuilder sb;
		ArrayList<ArrayList<Integer>> afterSet = new ArrayList<>();

		for (int i = 0; i < arr.size() - k; i++) {
			for (int j = i + 1; j < arr.size() - k; j++) {
				positions = contain(arr.get(i), arr.get(j));
				if (positions != null) {
					if (checker(arr.get(i).getNonTouching().get(positions[0]),
							arr.get(j).getNonTouching().get(positions[1]))) {
						k++;

						for (int x = 0; x < arr.get(i).getNonTouching().size(); x++) {
							set.add(arr.get(i).getNonTouching().get(x));
						}

						for (int x = 0; x < arr.get(j).getNonTouching().size(); x++) {
							set.add(arr.get(j).getNonTouching().get(x));
						}

						afterSet.clear();
						afterSet.addAll(set);
						String s = "";
						s += arr.get(i).getNumLoops();
						s += arr.get(j).getNumLoops();
						chars = s.toCharArray();
						Set<Character> charSet = new LinkedHashSet<Character>();
						for (char c : chars) {
							charSet.add(c);
						}

						sb = new StringBuilder();
						for (Character character : charSet) {
							sb.append(character);
						}
						arr.add(new InfoHolder(sb.toString(), (ArrayList<ArrayList<Integer>>) afterSet.clone()));
						afterSet.clear();
						set.clear();
					} else {
						afterSet.clear();
					}
				}
			}
			if (i == arr.size() - k - 1) {
				k = 0;
			}
		}
	}

	private void removingDuplicate() {
		Set<Character> set = new LinkedHashSet<Character>();
		StringBuilder sb;
		char[] chars;
		String s = "";
		for (int i = 0; i < single.size(); i++) {
			for (int j = i + 1; j < single.size(); j++) {
				set.clear();
				s = "";
				s += single.get(i).getNumLoops();
				s += single.get(j).getNumLoops();
				chars = s.toCharArray();
				for (char c : chars) {
					set.add(c);
				}

				sb = new StringBuilder();

				for (Character character : set) {
					sb.append(character);
				}

				if (sb.toString()
						.length() == (single.get(i).getNumLoops().length() + single.get(j).getNumLoops().length())
								/ 2) {
					single.remove(j);
					j--;
					set.clear();
				}
			}
		}
	}

	private String getDeltaNonTouching(ArrayList<InfoHolder> arr, ArrayList<ArrayList<Integer>> arr2) {
		int counter = 2;
		String s = "";
		for (int i = 0; i < arr.size(); i++) {
			if (i == 0)
				s += "+ ( ";
			if (arr.get(i).getNumLoops().length() == counter) {
				for (int x = 0; x < arr.get(i).getNumLoops().length(); x++) {
					s += loopGain(arr.get(i).getNonTouching().get(x));
					if (x != arr.get(i).getNumLoops().length() - 1)
						s += " * ";
				}
				if (i != arr.size() - 1) {
					s += " + ";
				} else {

				}
			} else {
				counter++;
				i--;
				if (counter % 2 != 0) {
					s += " ) - ( ";
				} else {
					s += " ) + ( ";
				}
			}
		}
		if (!s.isEmpty()) {
			s += " )";
		}
		String s1 = "";
		s1 += "1";
		for (int z = 0; z < arr2.size(); z++) {
			if (z == 0)
				s1 += " - ( ";
			s1 += loopGain(arr2.get(z));
			if (z != arr2.size() - 1)
				s1 += " + ";
			if (z == arr2.size() - 1)
				s1 += " ) ";
		}
		s1 += s;
		return s1;
	}

	private ArrayList<String> deltaForward() {
		Set<Integer> set = new HashSet<>();
		int length = 0;
		ArrayList<String> deltaForwardPaths = new ArrayList<>();
		ArrayList<InfoHolder> arr;
		for (int i = 0; i < forward.size(); i++) {
			arr = new ArrayList<>(single);
			set.clear();
			length = 0;
			for (int j = 0; j < arr.size(); j++) {
				set.clear();
				length = 0;
				for (int x = 0; x < forward.get(i).size(); x++) {
					set.add(forward.get(i).get(x));
				}
				length += forward.get(i).size();

				for (int x = 0; x < arr.get(j).getNonTouching().size(); x++) {
					length += arr.get(j).getNonTouching().get(x).size();
					for (int y = 0; y < arr.get(j).getNonTouching().get(x).size(); y++) {
						set.add(arr.get(j).getNonTouching().get(x).get(y));
					}
				}

				if (set.size() != length - 2) {
					arr.remove(j);
					j--;
				}
				if (j == arr.size() - 1)
					break;
			}
			ArrayList<ArrayList<Integer>> loops = new ArrayList<>(loop);
			for (int x = 0; x < loops.size(); x++) {
				for (int y = 0; y < loops.get(x).size(); y++) {
					if (forward.get(i).contains(loops.get(x).get(y))) {
						loops.remove(x);
						y--;
					}
					if (loops.isEmpty() || y == loops.size() - 1) {
						break;
					}
				}
			}

			deltaForwardPaths.add(getDeltaNonTouching(arr, loops));
		}
		return deltaForwardPaths;
	}

	private ArrayList<String> forwardPathsGain() {
		ArrayList<String> gain = new ArrayList<>();
		String s = "";
		s += "( ";
		for (int i = 0; i < forward.size(); i++) {
			for (int j = 0; j < forward.get(i).size() - 1; j++) {
				s += arr1[forward.get(i).get(j)][forward.get(i).get(j + 1)];
				if (j != forward.get(i).size() - 2)
					s += " * ";
				if (j == forward.get(i).size() - 2) {
					s += " )";
					gain.add(s);
					s = "";
					s += "( ";
				}
			}
		}
		return gain;
	}

	private String ovarallTransferFunction(ArrayList<String> forwards, ArrayList<String> deltaForwards) {
		String s = "";
		s += "R(S) / C(S) = ";

		for (int i = 0; i < forwards.size(); i++) {
			s += forwards.get(i);
			s += " * ";
			s += "( ";
			s += deltaForwards.get(i);
			s += " )";
			if (i != forwards.size() - 1)
				s += " + ";
		}
		s += " / ";
		s += getDeltaNonTouching(single, loop);
		return s;
	}

	private String loopGain(ArrayList<Integer> loop) {
		String s = "";
		s += "( ";

		for (int i = 0; i < loop.size() - 1; i++) {
			s += arr1[loop.get(i)][loop.get(i + 1)];

			if (i != loop.size() - 2)
				s += " * ";
		}

		s += " )";
		return s;
	}

	public String getOverall() {
		forwardSeq(0);
		forwardBaths(paths);
		detectLoops();

		Set<ArrayList<Integer>> set = new HashSet<>(loop);
		loop.clear();
		loop.addAll(set);

		detectLoops();

		detectTwoNonTouching();

		getNonTouching(single);

		removingDuplicate();

		getDeltaNonTouching(single, loop);

		deltaForward();

		forwardPathsGain();

		return ovarallTransferFunction(forwardPathsGain(), deltaForward());
	}

	public String forwardPathString() {
		String s = "";
		ArrayList<String> ss = new ArrayList<>();
		ss = forwardPathsGain();
		s += "Forward Paths: \n";
		for (int i = 0; i < ss.size(); i++) {
			s += ss.get(i);
			s += "\n";
		}
		return s;
	}

	public String getDeltaForward() {
		String s = "";
		ArrayList<String> ss = new ArrayList<>();
		ss = deltaForward();
		s += "Delta Forward Paths: \n";
		for (int i = 0; i < ss.size(); i++) {
			s += ss.get(i);
			s += "\n";
		}
		return s;
	}

	public String getLoopsGain() {
		String s = "";
		s += "Loops Gain: \n";
		for (int i = 0; i < loop.size(); i++) {
			s += loopGain(loop.get(i));
			s += "\n";
		}
		return s;
	}

	public String nonTouchingLoops() {
		String s = "";
		s += "Non Touching Loops: \n";
		for (int i = 0; i < single.size(); i++) {
			for (int j = 0; j < single.get(i).getNonTouching().size(); j++) {
				s += loopGain(single.get(i).getNonTouching().get(j));
				if (j != single.get(i).getNonTouching().size() - 1)
					s += ",";
			}
			s += "\n";
		}
		return s;
	}

}
