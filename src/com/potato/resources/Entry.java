package com.potato.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.potato.dbAccess.EntryJDBCTemplate;

public class Entry {

	private int id;
	private String name;
	private int season;
	private int episode;
	private Date lastViewed;
	private String status;

	/**
	 * This is the method that adds a new 
	 * Record into series table
	 * @param entryJDBCTemplateObject
	 */
	public static void addEntry(EntryJDBCTemplate entryJDBCTemplateObject) {
		Entry myEntry = new Entry();
		myEntry.enterData();

		entryJDBCTemplateObject.create(myEntry.getName(), myEntry.getSeason(), myEntry.getEpisode(), myEntry.getLastViewed());
	}

	/**
	 * Method that gathers data to be saved
	 * into database.
	 */
	private void enterData() {

		Date currentDate = new Date();
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Name: ");
		this.setName(keyboard.nextLine());

		System.out.print("Season: ");
		this.setSeason(keyboard.nextInt());

		System.out.print("Episode: ");
		this.setEpisode(keyboard.nextInt());

		this.setLastViewed(currentDate);
	}

	/**
	 * This is the method that prints all entries
	 * from series table.
	 * @param entryJDBCTemplateObject
	 */
	public static void printEntries(EntryJDBCTemplate entryJDBCTemplateObject) {
		List<Entry> myEntries = entryJDBCTemplateObject.listEntries();

		myEntries = sort(myEntries);		

		System.out.printf("%5s %29s %8s %9s %15s %44s\n", "Id", "Name", "Season", "Episode", "Last Viewed", "Status");
		System.out.println("===================================================================================================================");
		for (int i = 0; i < myEntries.size() - 1; i++) {

			System.out.printf("%5d %29s %8d %9d %15s %44s\n", myEntries.get(i).getId(), myEntries.get(i).getName(), myEntries.get(i).getSeason(),
					myEntries.get(i).getEpisode(), myEntries.get(i).getLastViewed().toString(), myEntries.get(i).getStatus());

			if (i == myEntries.size() - 2) {

				if ( !(myEntries.get(i + 1).getName().charAt(0) == myEntries.get(i).getName().charAt(0)) )
					System.out.println();

				System.out.printf("%5d %29s %8d %9d %15s %44s\n", myEntries.get(i + 1).getId(), myEntries.get(i + 1).getName(), myEntries.get(i + 1).getSeason(),
						myEntries.get(i + 1).getEpisode(), myEntries.get(i + 1).getLastViewed().toString(), myEntries.get(i + 1).getStatus());
				break;
			}

			if ( !(myEntries.get(i).getName().charAt(0) == myEntries.get(i + 1).getName().charAt(0)) ) 
				System.out.println();
		}
		System.out.println("===================================================================================================================");
	}

	/**
	 * This is the method used to list down
	 * entries that have at the beginning, middle or ending
	 * of their name the entered String.
	 * @param entryJDBCTemplateObject
	 */
	public static void searchEntries(EntryJDBCTemplate entryJDBCTemplateObject) {
		Scanner keyboard = new Scanner(System.in);
		String oneLine;

		System.out.print("Find(in name): ");
		oneLine = keyboard.nextLine();

		List<Entry> myEntries = entryJDBCTemplateObject.listSomeEntries(oneLine);

		myEntries = sort(myEntries);

		System.out.printf("%5s %29s %8s %9s %15s %44s\n", "Id", "Name", "Season", "Episode", "Last Viewed", "Status");
		System.out.println("===================================================================================================================");
		for (int i = 0; i < myEntries.size() - 1; i++) {

			System.out.printf("%5d %29s %8d %9d %15s %44s\n", myEntries.get(i).getId(), myEntries.get(i).getName(), myEntries.get(i).getSeason(),
					myEntries.get(i).getEpisode(), myEntries.get(i).getLastViewed().toString(), myEntries.get(i).getStatus());

			if (i == myEntries.size() - 2) {

				if ( !(myEntries.get(i + 1).getName().charAt(0) == myEntries.get(i).getName().charAt(0)) )
					System.out.println();

				System.out.printf("%5d %29s %8d %9d %15s %44s\n", myEntries.get(i + 1).getId(), myEntries.get(i + 1).getName(), myEntries.get(i + 1).getSeason(),
						myEntries.get(i + 1).getEpisode(), myEntries.get(i + 1).getLastViewed().toString(), myEntries.get(i + 1).getStatus());
				break;
			}

			if ( !(myEntries.get(i).getName().charAt(0) == myEntries.get(i + 1).getName().charAt(0)) ) 
				System.out.println();
		}
		if (myEntries.size() == 1)
			System.out.printf("%5d %29s %8d %9d %15s %44s\n", myEntries.get(0).getId(), myEntries.get(0).getName(), myEntries.get(0).getSeason(),
					myEntries.get(0).getEpisode(), myEntries.get(0).getLastViewed().toString(), myEntries.get(0).getStatus());
		System.out.println("===================================================================================================================");
	}
	
	/**
	 * Method that sorts the retrieved list of entries
	 * @param oneList
	 * @return a alphabetically sorted list
	 */
	private static List<Entry> sort(List<Entry> oneList) {
		boolean moved = false;
		Entry aux = new Entry();
		do {
			moved = false;
			for (int i = 0; i < oneList.size() - 1; i++) {

				if (oneList.get(i).getName().charAt(0) > oneList.get(i + 1).getName().charAt(0)) {
					aux = oneList.get(i);
					oneList.set(i, oneList.get(i + 1));
					oneList.set(i + 1, aux);
					moved = true;
				}
			}
		}while (moved);
		return oneList;
	}

	/**
	 * Method that makes you able to increment your position
	 * in the progress of watching a series.
	 * @param entryJDBCTemplateObject
	 */
	public static void watchedOne(EntryJDBCTemplate entryJDBCTemplateObject) {
		Scanner keyboard = new Scanner(System.in);
		char answer;

		System.out.print("Was it the last of it's season? ");
		answer = keyboard.nextLine().charAt(0);
		Date currentViewed = new Date();
		Entry temp = new Entry();

		if (Character.toUpperCase(answer) == 'N') {
			int theId, newEpisode;

			printEntries(entryJDBCTemplateObject);
			Commands.promptEnterKey();

			System.out.print("Id: ");
			theId = keyboard.nextInt();

			temp = entryJDBCTemplateObject.getEntry(theId);

			newEpisode = temp.getEpisode() + 1;

			entryJDBCTemplateObject.update(theId, newEpisode, currentViewed);
		} else if (Character.toUpperCase(answer) == 'Y') {
			int theId, newSeason, newEpisode;

			printEntries(entryJDBCTemplateObject);
			Commands.promptEnterKey();

			System.out.print("Id: ");
			theId = keyboard.nextInt();

			temp = entryJDBCTemplateObject.getEntry(theId);

			newSeason = temp.getSeason() + 1;
			do {				
				System.out.print("Season starts with episode number 0 or 1?\n>> ");
				newEpisode = keyboard.nextInt();
			} while (newEpisode != 0 && newEpisode != 1);

			entryJDBCTemplateObject.update(theId, newSeason, newEpisode, currentViewed);
		}

	}
	/**
	 * Edit entire entry
	 * @param entryJDBCTemplateObject
	 */
	public static void editOne(EntryJDBCTemplate entryJDBCTemplateObject) {
		Scanner keyboard = new Scanner(System.in);
		Entry newEntry = new Entry();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String input;

		printEntries(entryJDBCTemplateObject);
		Commands.promptEnterKey();

		System.out.print("Id: ");
		newEntry.setId(keyboard.nextInt());
		keyboard.nextLine();

		System.out.print("New Name: ");
		newEntry.setName(keyboard.nextLine());

		System.out.print("At Season: ");
		newEntry.setSeason(keyboard.nextInt());

		System.out.print("At Episode: ");
		newEntry.setEpisode(keyboard.nextInt());
		keyboard.nextLine();

		System.out.print("Last Viewed Date(yyyy-mm-dd): ");
		input = keyboard.nextLine();
		try {
			newEntry.setLastViewed(ft.parse(input));
		} catch (ParseException e) {
			System.out.println(e);
		}

		entryJDBCTemplateObject.update(newEntry.getId(), newEntry.getName(), newEntry.getSeason(), newEntry.getEpisode(), newEntry.getLastViewed());
	}

	/**
	 * Delete a id'ed entry
	 * @param entryJDBCTemplateObject
	 */
	public static void deleteEntry(EntryJDBCTemplate entryJDBCTemplateObject) {
		Scanner keyboard = new Scanner(System.in);
		int theId;

		printEntries(entryJDBCTemplateObject);
		Commands.promptEnterKey();

		System.out.print("Id: ");
		theId = keyboard.nextInt();

		entryJDBCTemplateObject.delete(theId);
	}

	/**
	 * Method the gathers new season and episode number and calls
	 * the required method in order for a database update.
	 * @param entryJDBCTemplateObject
	 */
	public static void editSeasonAndEpisode(EntryJDBCTemplate entryJDBCTemplateObject) {
		Scanner keyboard = new Scanner(System.in);
		int theId, newEpisode, newSeason;

		printEntries(entryJDBCTemplateObject);
		Commands.promptEnterKey();

		System.out.print("Id: ");
		theId = keyboard.nextInt();

		System.out.print("At Season: ");
		newSeason = keyboard.nextInt();

		System.out.print("At Episode: ");
		newEpisode = keyboard.nextInt();

		entryJDBCTemplateObject.update(theId, newSeason, newEpisode);
	}

	/**
	 * Method the gathers new date and calls
	 * the required method in order for a database update.
	 * @param entryJDBCTemplateObject
	 */
	public static void editDate(EntryJDBCTemplate entryJDBCTemplateObject) {
		Scanner keyboard = new Scanner(System.in);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = new Date();
		String input;
		int theId;

		printEntries(entryJDBCTemplateObject);
		Commands.promptEnterKey();

		System.out.print("Id: ");
		theId = keyboard.nextInt();
		keyboard.nextLine();

		System.out.print("Last Viewed Date(yyyy-mm-dd): ");
		input = keyboard.nextLine();
		try {
			newDate = ft.parse(input);
		} catch (ParseException e) {
			System.out.println(e);
		}
		entryJDBCTemplateObject.update(theId, newDate);
	}

	/**
	 * Method that is called at the beginning of the program
	 * and updates status column in series table.
	 * @param entryJDBCTemplateObject
	 * @throws ParseException 
	 */
	public static void statusUpdate(EntryJDBCTemplate entryJDBCTemplateObject) {
		List<Entry> myEntries = entryJDBCTemplateObject.listEntries();
		myEntries = sort(myEntries);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date(), resultDate = new Date();
		int currentYear, currentMonth, currentDay;
		int thisYear, thisMonth, thisDay;
		int resultYear, resultMonth, resultDay;
		int difference;
		String dateShort[] = new String[3];
		dateShort = df.format(currentDate).split("-");
		currentYear = Integer.parseInt(dateShort[0]);
		currentMonth = Integer.parseInt(dateShort[1]);
		currentDay = Integer.parseInt(dateShort[2]);

		for (int i = 0; i < myEntries.size(); i++) {
			dateShort = df.format(myEntries.get(i).getLastViewed()).split("-");
			thisYear = Integer.parseInt(dateShort[0]);
			thisMonth = Integer.parseInt(dateShort[1]);
			thisDay = Integer.parseInt(dateShort[2]);
			difference = 0;

			resultYear = 0;
			resultMonth = 0;
			resultDay = 0;
			
			resultYear = currentYear - thisYear;
			
			resultMonth = currentMonth - thisMonth;
			if (resultMonth < 0) {
				resultYear--;
				resultMonth += 12;
			}
			
			resultDay = currentDay - thisDay;
			if (resultDay < 0) {
				resultMonth--;
				resultDay += 30;
			}
			
			try {
				resultDate = df.parse(resultYear + "-" + resultMonth + "-" + resultDay);
			} catch (ParseException e) {
				System.out.println(e);
			}
			
			if (resultYear != 0) {
				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "It's been " + resultYear + " year" + (resultYear > 1?"s, ":", ")
																						+ resultMonth + " month" + (resultMonth > 1?"s, ":", ")
																						+ resultDay + " day" + (resultDay > 1?"s.":"."));
			} else if (resultMonth != 0) {
				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "It's been " + resultMonth + " month" + (resultMonth > 1?"s, ":", ")
																						+ resultDay + " day" + (resultDay > 1?"s.":"."));
			} else if (resultDay != 0) {
				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "It's been " + resultDay + " day" + (resultDay > 1?"s.":"."));				
			} else if (resultDay == 0) {
				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "You've just watched it!");
			}
			
//			if (currentYear - thisYear > 0) {
//				difference += (currentYear - thisYear) * 365;
//			} else if (currentMonth - thisMonth > 0) {
//				difference += (currentMonth - thisMonth) * 30;
//			} else if (currentDay - thisDay > 0) {
//				difference += currentDay - thisDay;
//			}
//			
//			if (difference >= 0 && difference < 14) {
//				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "You've recently watched this");
//			} else if (difference >= 14 && difference < 21) {
//				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "Look for new episode");
//			} else if (difference >= 21 && difference < 90) {
//				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "Delayed");
//			} else if (difference >= 90 && difference < 365) {
//				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "On Break");
//			} else if (difference >= 365) {
//				entryJDBCTemplateObject.update(myEntries.get(i).getId(), "Long Break");
//			}
			
		}

	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public int getEpisode() {
		return episode;
	}
	public void setEpisode(int episode) {
		this.episode = episode;
	}
	public Date getLastViewed() {
		return lastViewed;
	}
	public void setLastViewed(Date lastViewed) {
		this.lastViewed = lastViewed;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
