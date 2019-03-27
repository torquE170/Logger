package com.potato;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.potato.dbAccess.EntryJDBCTemplate;
import com.potato.resources.Commands;
import com.potato.resources.Entry;

public class MainApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		EntryJDBCTemplate entryJDBCTemplateObject = (EntryJDBCTemplate) context.getBean("entryJDBCTemplate");
		Scanner keyboard = new Scanner(System.in);
		Commands.clearScreen();
		Entry.statusUpdate(entryJDBCTemplateObject);
		
		int option = -1;
		System.out.println("___________________________________");
		do {
			System.out.println("|   Main Menu                     |");
			System.out.println("|                                 |");
			System.out.println("| 1 - Add a entry                 |");
			System.out.println("| 2 - Take a look at all entries  |");
			System.out.println("| 3 - Search the entries          |");
			System.out.println("| 4 - Watched a episode           |");
			System.out.println("| 5 - Edit a entry                |");
			System.out.println("| 6 - Delete a entry              |");
			System.out.println("|                                 |");
			System.out.println("| 0 - Exit                        |");
			System.out.print(">> ");
			option = keyboard.nextInt();
			switch(option) {
			case 1:
				{
					Entry.addEntry(entryJDBCTemplateObject);
					break;
				}
			case 2:
				{
					Entry.printEntries(entryJDBCTemplateObject);
					Commands.promptEnterKey();
					break;
				}
			case 3:
				{
					Entry.searchEntries(entryJDBCTemplateObject);
					Commands.promptEnterKey();
					break;
				}
			case 4:
				{
					Entry.watchedOne(entryJDBCTemplateObject);
					break;
				}
			case 5:
				{
					do {
						System.out.println("|*********************************|");
						System.out.println("|  Edit Menu                      |");
						System.out.println("|  1 - All                        |");
						System.out.println("|  2 - Season and Episode         |");
						System.out.println("|  3 - Date                       |");
						System.out.println("|                                 |");
						System.out.println("|  0 - Back                       |");
						System.out.print(">> ");
						option = keyboard.nextInt();
						switch(option) {
						case 1:
							{
								Entry.editOne(entryJDBCTemplateObject);
								break;
							}
						case 2:
							{
								Entry.editSeasonAndEpisode(entryJDBCTemplateObject);
								break;
							}
						case 3:
							{
								Entry.editDate(entryJDBCTemplateObject);
								break;
							}
						case 0:
							{
								option = -2;
								break;
							}
						default:
							{
								option = 0;
								System.out.println("| Choose a valid option!          |");
								break;
							}
						}
					} while (option != -2);
					break;
				}
			case 6:
				{
					Entry.deleteEntry(entryJDBCTemplateObject);
					break;
				}
			case 0:
			{
				option = -1;
				System.out.println("|  Bye!                           |");
				System.out.println("|_________________________________|");
				break;
			}
			default:
				{
					option = 0;
					System.out.println("| Choose a valid option!          |");
					break;
				}
			}
		} while (option != -1);
		
		((ConfigurableApplicationContext)context).close();
	}
	
}
