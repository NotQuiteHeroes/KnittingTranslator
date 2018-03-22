/**
 * Paige Eckstein
 * Knitting Translator
 *
 * Requires textfiles containing dictionary for each language
 * These are saved in the resources folder
 *
 * Update button allows the user to add a new term to the dictionary
 * Save button saves the translation to a text file in the Saves folder
 *
 * No requirements
 *
 * Compilation:
 * javac GUIKnittingTranslator.java
 *
 * Execution:
 * java GUIKnittingTranslator
 */
import java.util.Hashtable;
import java.util.Arrays;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.lang.StringBuilder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GUIKnittingTranslator
{
//currently loaded dictionary
private static String loadedDictionary;
//actual dictionary of terms
private static Hashtable<String, String> terms = new Hashtable<String, String>();

/**
 * [GUIKnittingTranslator Constructor - calls GUI setup]
 */
public GUIKnittingTranslator()
{
        setupGUI();
}

/**
 * [main Simply creates GUIKnittingTranslator object]
 * @param args [command line arguments - not used with GUI version currently]
 */
public static void main(String[] args)
{
        GUIKnittingTranslator kt = new GUIKnittingTranslator();
}

/**
 * [setupGUI Handles all GUI setup, including button functionality]
 */
private void setupGUI()
{
        //main frame set up
        JFrame mainFrame = new JFrame("Knitting Translator");
        mainFrame.setSize(385, 580);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //main panel set up
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        //language selection drop down box
        JLabel languageLabel = new JLabel("Select language: ");
        String[] languageList = {"German", "Spanish", "French", "Italian", "Swedish"};
        JComboBox<String> languages = new JComboBox<String>(languageList);
        languages.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXX");
        controlPanel.add(languageLabel);
        controlPanel.add(languages);

        //text field to enter text to be translated
        JLabel textLabel = new JLabel("Enter text to translate: ");
        JTextArea textField = new JTextArea(10, 30);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        JScrollPane textFieldPane = new JScrollPane(textField);
        textFieldPane.setPreferredSize(new Dimension(335, 180));
        controlPanel.add(textLabel);
        controlPanel.add(textFieldPane);

        //note about usage
        JLabel noteLabel = new JLabel("Note: If a term is not found in the dictionary, it will print out as is.");
        JLabel noteLabel1 = new JLabel("To add a term to the dictionary, click Update.");
        controlPanel.add(noteLabel);
        controlPanel.add(noteLabel1);

        //update button - opens small joptionpane for user to enter term to save to dictionary
        JButton updateButton = new JButton("Update");
        controlPanel.add(updateButton);
        updateButton.addActionListener((ActionEvent e)->
                {
                        String input = JOptionPane.showInputDialog(null, "Enter term to add followed by English translation, separated by a space:", "Update Dictionary");
                        String language = languages.getSelectedItem().toString();
                        String dictPath = getDictionary(language);
                        updateDictionary(input, dictPath);

                });

        //text area to display translated text
        JLabel translationLabel = new JLabel("Translation: ");
        JTextArea translationField = new JTextArea(10, 30);
        translationField.setLineWrap(true);
        translationField.setWrapStyleWord(true);
        translationField.setEditable(false);
        JScrollPane translationFieldPane = new JScrollPane(translationField);
        translationFieldPane.setPreferredSize(new Dimension(335, 180));
        controlPanel.add(translationLabel);
        controlPanel.add(translationFieldPane);

        //buttons along bottom
        JButton saveButton = new JButton("Save");
        JButton clearButton = new JButton("Clear");
        JButton translateButton = new JButton("Translate");
        //save button - save translation to text file in Saves folder
        controlPanel.add(saveButton);
        saveButton.addActionListener((ActionEvent e)->{
                        String input = translationField.getText();
                        saveTranslation(input);
                });
        //clear button - clear text from all textfields
        controlPanel.add(clearButton);
        clearButton.addActionListener((ActionEvent e)->
                {
                        textField.setText("");
                        translationField.setText("");
                });
        //translate button - determine current dictionary, load it, and translate text provided by user
        controlPanel.add(translateButton);
        translateButton.addActionListener((ActionEvent e)->
                {
                        String language = languages.getSelectedItem().toString();
                        String dictionary = getDictionary(language);
                        populateDictionary(dictionary);

                        String input = textField.getText();
                        String output = translate(input);
                        translationField.setText(output);
                });
        //add everything to mainFrame and make visible
        mainFrame.add(controlPanel);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

}

/**
 * [getDictionary Fetch desired language's dictionary from resources folder]
 * @param  language [desired language for translation - picked by user from drop down menu]
 * @return          [String containing path to where a language's dictionary is saved]
 */
private String getDictionary(String language)
{
        String path = "";
        if(language.equals("German"))
        {
                path = "resources\\German.txt";
        }
        else if(language.equals("Spanish"))
        {
                path = "resources\\Spanish.txt";
        }
        else if(language.equals("French"))
        {
                path = "resources\\French.txt";
        }
        else if(language.equals("Swedish"))
        {
                path = "resources\\Swedish.txt";
        }

        return path;
}

/**
 * [populateDictionary Takes a string containing path to dictionary textfile and reads in the terms. It splits them into key-value pairs
 * and stores them to the hastable terms.]
 * @param dictionary [String containing path to the dictionary text file]
 */
static void populateDictionary(String dictionary)
{
        //if the terms hastable is empty, or if the user has changed languages
        if(terms.isEmpty() || !dictionary.equals(loadedDictionary))
        {
                //empty the hashtable
                terms.clear();
                try{
                        File dict = new File(dictionary);
                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dict), "UTF-8"));
                        String line = br.readLine();
                        //read in line by line from terms file
                        while(line != null)
                        {
                                //split Non-English term from English term
                                String[] splitTerms = line.split(" ");
                                //create map entry with Non-English term as key
                                //and English term as value
                                terms.put(splitTerms[0], splitTerms[1]);
                                line = br.readLine();
                        }
                        br.close();
                }
                catch (UnsupportedEncodingException e)
                {
                        System.out.println(e.getMessage());
                }
                catch (IOException e)
                {
                        e.printStackTrace();
                }
        }
}

/**
 * [translate Takes user input text to translate, performs translate, and returns translation as single String]
 * @param  input [Full String of text entered by user for translation]
 * @return       [String of text entered by user translated]
 */
static String translate(String input)
{
        //tokenize input and store to array
        String[] splitInput = tokenize(input.toLowerCase());

        StringBuilder sb = new StringBuilder();

        //for each individual word, number, or punctuation mark in tokenized array
        for(String term : splitInput)
        {
                //if element is punctuation or a number, simply keep as is
                if(!Character.isLetter(term.charAt(0)))
                {
                        sb.append(term + " ");
                }
                //if element exists as key in hashtable terms, return the value
                else if(terms.containsKey(term))
                {
                        sb.append(terms.get(term) + " ");
                }
                //if element doesn't exist as key in hastable terms, simply keep as is
                else
                {
                        sb.append(term + " ");
                }
        }
        return sb.toString();
}

/**
 * tokenize uses regex to remove whitespace from user input String
 * then splits into elements, making punctuation its own element
 * separate from the words themselves
 * @param  String input         [user input string to translate]
 * @return                      [returns tokenized input as array]
 */
static String[] tokenize(String input)
{
        //regex to split punctuation from words
        String regex = "\\s+|(?=\\p{Punct})|(?<=\\p{Punct})";
        //replace all removes whitespace before splitting using regex defined above
        return input.replaceAll("\\s+(?=\\p{Punct})", "").split(regex);
}

/**
 * [updateDictionary Add a new term to the dictionary]
 * @param input      [Translation to add to dictionary in format Non-EnglishTerm EnglishTerm]
 * @param dictionary [path to dictionary to update]
 */
static void updateDictionary(String input, String dictionary)
{
        BufferedWriter bw = null;
        String[] splitInput = input.split("\\s");
        String toTranslate = splitInput[0];
        String translated = splitInput[1];

        try{
                //connect to dictionary file
                bw = new BufferedWriter(new FileWriter(dictionary, true));
                //add new translation pair in format:
                //Non-EnglishTerm EnglishTerm
                bw.write(toTranslate + " " + translated);
                //append newline after adding text
                bw.newLine();
                bw.flush();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        if(bw != null)
                        {
                                bw.close();
                        }
                } catch (IOException e2) {}
        }
}

/**
 * saveTranslation will take a translated text and save it to a text file
 * called savedTranslation.txt
 * @param String translation [already translated text to be saved]
 */
static void saveTranslation(String translation)
{
        BufferedWriter bw = null;

        try{
                bw = new BufferedWriter(new FileWriter("saves\\savedTranslation.txt", true));
                bw.write(translation);
                //append newline after adding text
                bw.newLine();
                bw.flush();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        if(bw != null)
                        {
                                bw.close();
                        }
                } catch (IOException e2) {}
        }
}

}
