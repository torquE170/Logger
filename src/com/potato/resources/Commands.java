package com.potato.resources;

import java.io.IOException;
import java.util.Scanner;

public class Commands {

	public static void promptEnterKey() {
		Scanner keyboard = new Scanner(System.in);
		try {
			System.out.println("| --- Press Enter to continue --- |");
			System.out.print("|                                 |");
			keyboard.nextLine();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void clearScreen() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch(IOException e) {
			System.out.println(e);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}
