package com.test;

import java.util.Arrays;

public class KMP {

	public static int[] index = new int[20];

	//
	public static int comp(char[] s, char[] m) {
		int i = 0;
		int j = 0;
		while (j < m.length && i < s.length) {
			if ( s[i] == m[j]) {
				i++;
				j++;
			} else {
				j = index[j];
				if (j == -1) {
					j = 0;
					i++;
				}
			}
		}
		return j == m.length ? i-m.length : -1;
	}

	public static void getIndex(char[] s) {
		int i = 0, j = -1;
		index[0] = -1;
		while (i < s.length) {
			if (j == -1 || s[i] == s[j]) {
				i++;
				j++;
				index[i] = j;
			} else {
				j = index[j];
			}
		}
	}

	public static void main(String args[]) {
		String s = "abcdefgabg";
		String m = "abc";
		char c[] = s.toCharArray();

		getIndex(c);

		System.out.println(Arrays.toString(index));
		System.out.println(comp(s.toCharArray(), m.toCharArray()));

	}

}
