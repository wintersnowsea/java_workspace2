package io.basic;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class KeyBoardApp2 {
	
	public static void main(String[] args) {
		InputStream is=System.in;
		PrintStream ps=System.out; //문자기반
		
		int data=-1;
		try {
			
			while(true) {
				data=is.read(); //1byte 읽기
				ps.println((char)data); //평상시 사용해오던 System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
