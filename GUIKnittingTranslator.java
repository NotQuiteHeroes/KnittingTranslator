/**
 * Paige Eckstein
 * Knitting Translator
 *
 * Requires textfiles containing dictionary for each language
 * These are saved in the resources folder
 *
 */
import java.util.Hashtable;
import java.util.Arrays;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.lang.StringBuilder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GUIKnittingTranslator
{
private static String loadedDictionary;
private static Hashtable terms = new Hashtable();

public GUIKnittingTranslator()
{
        setupGUI();
}

public static void main(String[] args)
{
        GUIKnittingTranslator kt = new GUIKnittingTranslator();
}

private void setupGUI()
{
        JFrame mainFrame = new JFrame("Knitting Translator");
        mainFrame.setSize(385, 550);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel languageLabel = new JLabel("Select language: ");
        String[] languageList = {"German", "Spanish", "French", "Italian", "Swedish"};
        JComboBox<String> languages = new JComboBox<String>(languageList);
        languages.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXX");
        controlPanel.add(languageLabel);
        controlPanel.add(languages);

        JLabel textLabel = new JLabel("Enter text to translate: ");
        JTextArea textField = new JTextArea(10, 30);
        controlPanel.add(textLabel);
        controlPanel.add(textField);

        JLabel noteLabel = new JLabel("Note: If a term is not found in the dictionary, it will print out as is.");
        JLabel noteLabel1 = new JLabel("To add a term to the dictionary, click Update.");
        controlPanel.add(noteLabel);
        controlPanel.add(noteLabel1);

        JButton updateButton = new JButton("Update");
        controlPanel.add(updateButton);
        updateButton.addActionListener((ActionEvent e)->
                {
                        String input = JOptionPane.showInputDialog(null, "Enter term to add followed by English translation, separated by a space:", "Update Dictionary");
                        updateDictionary(input);

                });

        JLabel translationLabel = new JLabel("Translation: ");
        JTextArea translationField = new JTextArea(10, 30);
        controlPanel.add(translationLabel);
        controlPanel.add(translationField);

        JButton saveButton = new JButton("Save");
        JButton clearButton = new JButton("Clear");
        JButton translateButton = new JButton("Translate");
        controlPanel.add(saveButton);
        saveButton.addActionListener((ActionEvent e)->{
                        String input = translationField.getText();
                        saveTranslation(input);
                });
        controlPanel.add(clearButton);
        clearButton.addActionListener((ActionEvent e)->
                {
                        textField.setText("");
                        translationField.setText("");
                });
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
        mainFrame.add(controlPanel);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

}

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
 * populateDictionary reads in translation pairs from text file, splits
 * them into key value pairs
 * @return [Hashtable containing non-English terms as keys, and English pairs]
 */
static void populateDictionary(String dictionary)
{
        if(terms.isEmpty() || !dictionary.equals(loadedDictionary))
        {
                terms.clear();
                try{
                        FileReader fr = new FileReader(dictionary);
                        BufferedReader br = new BufferedReader(fr);
                        String line = br.readLine();
                        //read in line by line from terms file
                        while(line != null)
                        {
                                //split German term from English term
                                String[] splitTerms = line.split(" ");
                                //create map entry with German term as key
                                //and English term as value
                                terms.put(splitTerms[0], splitTerms[1]);
                                line = br.readLine();
                        }
                        br.close();
                }
                catch (IOException e)
                {
                        e.printStackTrace();
                }
        }
}

/**
 * Translates user input
 * @param Hashtable terms [German terms are keys, English terms are values]
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
 * updateDictionary allows user to add their own translation pair to
 * textfile terms.txt
 */
static void updateDictionary(String input)
{
        BufferedWriter bw = null;
        String[] splitInput = input.split("\\s");
        String toTranslate = splitInput[0];
        String translated = splitInput[1];

        try{
                //connect to terms.txt file
                bw = new BufferedWriter(new FileWriter("terms.txt", true));
                //add new translation pair in format:
                //GermanTerm EnglishTerm
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
