/*
By: Kevin Tokola
Last Updated: 2/7/2020
Description: This class creates an object that can be used as a grid for a word search. It also has methods that assist
in making a word search such a method that creates/resets a word list, methods that populate/reset the grid, and a
method that displays the word search grid and its associated word list.
*/

import java.util.ArrayList;
import java.util.Scanner;

public class WordSearchGrid{
    private int gridSize;
    private ArrayList<String> wordList; //word list for word search
    private char[][] grid; //2D grid that will be printed for word search

    //////////Constructors//////////

    public WordSearchGrid(){ //Grid is 20x20 2D char array filled with the - char
        this.gridSize = 20;
        this.wordList = new ArrayList<>();
        this.grid = new char[gridSize][gridSize];

        for(int i = 0; i < this.gridSize; i++){
            for(int j = 0; j < this.gridSize; j++){
                this.grid[i][j] = '-';
            }
        }
    }

    //Preconditions: WordSearch object - needed to create a duplicate version of.
    public WordSearchGrid(WordSearchGrid wordSearch){
        this.gridSize = wordSearch.getGridSize();
        this.wordList = new ArrayList<>(wordSearch.getWordList()); //Copies word list

        this.grid = wordSearch.getGrid(); //Copies grid
        char[][] copyGrid = new char [this.gridSize][this.gridSize];
        for(int i = 0; i < this.gridSize; i++){
            for(int j = 0; j < this.gridSize; j++){
                copyGrid[i][j] = this.grid[i][j];
            }
        }
        this.grid = copyGrid;
    }
    //Output: Creates a copy of a WordSearchGrid object.

    //////////Setters and Getters//////////

    public int getGridSize(){
        return this.gridSize;
    }

    public void setGridSize(int size){
        this.gridSize = size;
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    //Preconditions: Word size limit set to 15.
    public void setWordList(){
        Scanner console = new Scanner(System.in);

        while(!this.wordList.isEmpty()) { //Resets word list to empty
            String word = this.wordList.get(0);
            this.wordList.remove(word);
        }

        String newUserWord;
        System.out.println("Enter words for your word search. Type done to stop.");
        System.out.println();
        do{                                                        //Loops to allow user to enter multiple words
            System.out.println("Enter your Word (Type done to stop): ");
            newUserWord = console.next();
            newUserWord = newUserWord.toUpperCase();
            if(!newUserWord.equalsIgnoreCase("Done")) { //Prevents done from being added to list
                if(newUserWord.length() <= 15) {
                    this.wordList.add(newUserWord);
                }else{
                    System.out.println("Word size limit exceeded. Word must be under 16 letters.");
                }
            }
        }while(!newUserWord.equalsIgnoreCase("Done"));
        System.out.println();
    }
    //Output: Clears existing word list and fills it with new user entered words.

    public char[][] getGrid(){
        return this.grid;
    }


    //////////Methods to Fill in the Grid//////////

    public void placeWordsGrid(){
        ArrayList<String> tempWordList = new ArrayList<>(this.wordList);
        int directionVarietyCount = 1;
        while(!tempWordList.isEmpty()){ //Loops until temporary word list has no more words to place
            String word = tempWordList.get(0);
            tempWordList.remove(word);
            int wordLength = word.length();

            int direction;              //Sets word direction
            if(directionVarietyCount <= 8){ //First eight words will be one of each direction
                direction = directionVarietyCount;
                directionVarietyCount++;
            }else {                        //Fully random after first eight words
                direction = (int) (Math.floor(Math.random() * 8) + 1); //Sets word direction
            }

            int[] startingPosition = staggerWords(direction, wordLength); //Starting position for word entry into grid
            int startingRow = startingPosition[0];
            int startingColumn = startingPosition[1];

            switch(direction){ //Word entered into grid
                case 1: //Horizontal
                    for (int i = 0; i < wordLength; i++) {
                        this.grid[startingRow][startingColumn + i] = word.charAt(i);
                    }
                    break;

                case 2: //Vertical
                    for(int i = 0; i< wordLength; i++) {
                        this.grid[startingRow + i][startingColumn] = word.charAt(i);
                    }
                    break;

                case 3: //Diagonal \
                    for(int i = 0; i< wordLength; i++) {
                        this.grid[startingRow + i][startingColumn + i] = word.charAt(i);
                    }
                    break;

                case 4://Diagonal /
                    for(int i = 0; i< wordLength; i++) {
                        this.grid[startingRow + i][startingColumn - i] = word.charAt(i);
                    }
                    break;

                case 5: //Horizontal reverse
                    for(int i = 0; i < wordLength; i++){
                        this.grid[startingRow][startingColumn + i] = word.charAt((wordLength-1) - i);
                    }
                    break;

                case 6: //Vertical reverse
                    for(int i = 0; i< wordLength; i++) {
                        this.grid[startingRow + i][startingColumn] = word.charAt((wordLength-1) - i);
                    }
                    break;

                case 7:// Diagonal \ reverse
                    for(int i = 0; i< wordLength; i++) {
                        this.grid[startingRow + i][startingColumn + i] = word.charAt((wordLength-1) - i);
                    }
                    break;

                case 8://Diagonal / reverse
                    for(int i = 0; i< wordLength; i++) {
                        this.grid[startingRow + i][startingColumn - i] = word.charAt((wordLength-1) - i);
                    }
                    break;
            }
        }
    }
    //Output: Places words in word list on the grid with random directions.

    //Preconditions: int direction:  Used to determine the direction of word being placed.
    //               int wordLength: Used to see how long word being placed is.
    public int[] staggerWords(int direction, int wordLength){
        switch(direction){
            case 1: //Horizontal
            case 5: //Horizontal reverse
                do{
                    boolean blocked = false;
                    int startingRow = (int) Math.floor(Math.random() * this.gridSize); //Random positions in grid boundaries
                    int startingColumn = (int) Math.floor(Math.random() * (this.gridSize - wordLength + 1));
                    int[] startingPositions = {startingRow, startingColumn}; //Returned value
                    for (int i = 0; i < wordLength; i++) {
                        if(this.grid[startingRow][startingColumn + i] != '-'){ //Test for possible word overwrite
                            blocked = true;
                        }
                    }
                    if(!blocked){ //Exit loop if starting position for word valid.
                        return startingPositions;
                    }
                }while(true);

            case 2: //Vertical
            case 6: //Vertical reverse
                do{
                    boolean blocked = false;
                    int startingRow = (int)Math.floor(Math.random() * (this.gridSize-wordLength+1));
                    int startingColumn = (int)Math.floor(Math.random() * this.gridSize);
                    int[] startingPositions = {startingRow, startingColumn};
                    for (int i = 0; i < wordLength; i++) {
                        if(this.grid[startingRow + i][startingColumn] != '-'){
                            blocked = true;
                        }
                    }
                    if(!blocked){
                        return startingPositions;
                    }
                }while(true);

            case 3: //Diagonal \
            case 7: //Diagonal \ reverse
                do{
                    boolean blocked = false;
                    int startingRow = (int)Math.floor(Math.random() * (this.gridSize-wordLength+1));
                    int startingColumn = (int)Math.floor(Math.random() * (this.gridSize-wordLength+1));
                    int[] startingPositions = {startingRow, startingColumn};
                    for (int i = 0; i < wordLength; i++) {
                        if(this.grid[startingRow + i][startingColumn + i] != '-'){
                            blocked = true;
                        }
                    }
                    if(!blocked){
                        return startingPositions;
                    }
                }while(true);

            case 4: //Diagonal /
            case 8: //Diagonal / reverse
                do{
                    boolean blocked = false;
                    int startingRow = (int)Math.floor(Math.random() * (this.gridSize-wordLength+1));
                    int startingColumn = (int)Math.floor(Math.random() * (this.gridSize-wordLength+1) + wordLength-1);
                    int[] startingPositions = {startingRow, startingColumn};
                    for (int i = 0; i < wordLength; i++) {
                        if(this.grid[startingRow + i][startingColumn - i] != '-'){
                            blocked = true;
                        }
                    }
                    if(!blocked){
                        return startingPositions;
                    }
                }while(true);

            default: //Redundant return to satisfy return requirement for compiler.
                int[] anArray = {1, 1};
                return anArray;
        }
    }
    //Output: Method used by placeWordsGrid. Returns int[x,y]: Starting coordinates for entering a word into the grid
    // that won't result in other words being overwritten. May hang program if it can't find space for a word on the
    // grid

    public void fillGrid(){
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                if (this.grid[i][j] == '-') {
                    int randomChar = (int) (Math.floor(Math.random() * 26) + 65); //int value of chars A-Z
                    this.grid[i][j] = (char)randomChar;
                }
            }
        }
    }
    //Output: Replaces '-' chars with random uppercase letters

    public void resetGrid(){
        for(int i = 0; i < this.gridSize; i++){
            for(int j = 0; j < this.gridSize; j++){
                this.grid[i][j] = '-';
            }
        }
    }
    //Output: Returns grid to blank 20x20 array filled with - chars

    //////////Printing the Grid//////////

    public void printGrid(){
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                System.out.print(this.grid[i][j]);
                System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(this.wordList.toString()); //Prints word list for word search
        System.out.println();
    }
    //Output: Prints the grid and prints a word list underneath the grid.
}
