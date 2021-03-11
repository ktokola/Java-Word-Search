/*
By: Kevin Tokola
Word Search
Last Update: 2/7/2020
Description: The Purpose of this assignment is to create a program that creates a word search that uses
the words a user enters.
*/

import java.util.*;

public class WordSearchMain {
    public static void main(String[] args){
        menu();
    }

    public static void menu(){
        Scanner console = new Scanner(System.in);
        WordSearchGrid answerGrid = new WordSearchGrid();
        boolean restart = true; //Defaults to true to allow menu to loop if q not selected.

        printIntro();
        char select = console.next().toLowerCase().charAt(0); //Selector for the menu switch.
        console.nextLine();
        do{
            switch(select) {
                case 'g': //generate new word search
                    answerGrid.resetGrid(); //Resets grid to blank 20x20 2D char array filled with the - char
                    answerGrid.setWordList(); //Creates new word list for WordSearchGrid object
                    answerGrid.placeWordsGrid();//Enters words from word list into WordSearchGrid grid.

                    printIntro();
                    select = console.next().toLowerCase().charAt(0);
                    console.nextLine();
                    break;
                case 'p': //print out word search
                    //Starts by duplicating answerGrid and filling blank spaces ('-' chars) with random A-Z.
                    WordSearchGrid filledGrid = new WordSearchGrid(answerGrid);
                    filledGrid.fillGrid();
                    filledGrid.printGrid(); //prints filledGrid object's grid.

                    printIntro();
                    select = console.next().toLowerCase().charAt(0);
                    console.nextLine();
                    break;
                case 's': //Show the solution to the word search
                    answerGrid.printGrid();

                    printIntro();
                    select = console.next().toLowerCase().charAt(0);
                    console.nextLine();
                    break;
                case 'q': //quit
                    restart = false;
                    break;
                default:
                    System.out.println("Invalid input.");

                    printIntro();
                    select = console.next().toLowerCase().charAt(0);
                    console.nextLine();
                    break;
            }
        }while(restart);
    }
    //Output: Menu for executing the program.

    public static void printIntro(){
        System.out.println("This program is creates a word search from the words you enter.");
        System.out.println("Generate a new word search (g)");
        System.out.println("Print out your word search (p)");
        System.out.println("Show the solution to your word search (s)");
        System.out.println("Quit the program (q)");
        System.out.println();
        System.out.println("Enter selection:");
    }
    //Output: A description of the program and the menu options. Also a prompt to use the menu.
}
