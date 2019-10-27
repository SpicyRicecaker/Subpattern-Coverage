import java.util.ArrayList;
import java.util.Scanner;

public class Subpattern {
	int x;
	int y;

	public Subpattern() {
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();

		String inputArray[] = input.split(" ");

		int x = Integer.parseInt(inputArray[0]);
		int y = Integer.parseInt(inputArray[1]);

		int oriArray[][] = new int[y][x];

		for (int a = 2; a < inputArray.length; a++) {
			//Convert hex to binary
			//Then convert that into string
			//Add to binaries
			String tempCol = hexToBinaryString(inputArray[a]);
			//Then loop through the columns of y
			//Then add the corresponding character 
			for (int k = 0; k < y; k++) {
				oriArray[k][a - 2] = (int) (tempCol.charAt(k) - 48);
			}

		}

		ArrayList<int[]> possibles = new ArrayList<int[]>();
		//Find size of Subarray
		for (int a = 1; a <= x; a++) {
			if (x % a != 0) {
				continue;
			}
			for (int b = 1; b <= y; b++) {
				if (y % b != 0) {
					continue;
				}
				boolean isPoss = true;
				//Top left square of each partition of subarray
				for (int j = 0; j < x; j += a) {
					for (int k = 0; k < y; k += b) {
						//Loop through current partition and subtracted for equality
						for (int l = j; l < j + a; l++) {
							for (int m = k; m < k + b; m++) {
								if (oriArray[m][l] != oriArray[m - k][l - j]) {
									isPoss = false;
								}
							}
						}
					}
				}
				if (isPoss) {
					possibles.add(new int[] { a, b });
				}
			}
		}
		int smallestX = x;
		int smallestY = y;

		int lowestSize = y * x;
		for (int a = 0; a < possibles.size(); a++) {
			if (possibles.get(a)[0] * possibles.get(a)[1] < lowestSize) {
				lowestSize = possibles.get(a)[0] * possibles.get(a)[1];
				smallestX = possibles.get(a)[0];
				smallestY = possibles.get(a)[1];
			}
		}

		System.out.println(smallestX + " " + smallestY);

		//Debug
		//		for (int a = 0; a < y; a++) {
		//			for (int b = 0; b < x; b++) {
		//				System.out.print(oriArray[a][b] + " ");
		//			}
		//			System.out.println();
		//		}

	}

	String hexToBinaryString(String hex) {
		String binary = "";
		for (int a = 0; a < hex.length(); a++) {
			int temp = (int) (hex.charAt(a) - 48);
			if (temp > 9) {
				temp -= 7;
			}

			char[] toAdd = new char[4];
			while (temp != 0) {
				if (temp - 8 >= 0) {
					toAdd[0] = '1';
					temp -= 8;
				} else if (temp - 4 >= 0) {
					toAdd[1] = '1';
					temp -= 4;
				} else if (temp - 2 >= 0) {
					toAdd[2] = '1';
					temp -= 2;
				} else if (temp - 1 >= 0) {
					toAdd[3] = '1';
					temp -= 1;
				}
			}
			for (int b = 0; b < 4; b++) {
				if (toAdd[b] != '1') {
					toAdd[b] = '0';
				}
			}
			binary += String.valueOf(toAdd);
		}

		if (binary.length() < y) {
			for (int a = 0; a < y - binary.length(); a++) {
				binary += "0";
			}
		} else if (binary.length() > y) {
			binary = binary.substring(y);
		}
		return binary;

	}

	public static void main(String[] args) {
		new Subpattern();
	}

}
