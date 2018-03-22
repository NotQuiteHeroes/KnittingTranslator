# KnittingTranslator

Current version only works with German terms.

## GUI Version
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/GUIKnittingTranslator.JPG "GUI Version")
### Compilation:
javac GUIKnittingTranslator.java
### Usage:
java GUIKnittingTranslator


The GUI Version of the Knitting Translator has a user interface, with two text boxes, the one on the top being for user input of German term(s) to translate, and the bottom for displaying the translated English term(s).
The dropdown at the top will allow the user to pick a different language to input.
The Update button is to add a term to the dictionary. The Save button will save the translated text to a .txt file in the Saves folder.
Clear will clear the text from both textboxes.
The Translate button will submit the German term(s) to be translated, and display the subsequent translation.
***
## Command Line Version
### Full Command Line Version:
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/knittingTranslator1.JPG "GUI Version")
### Compilation:
javac knittingTranslator.java
### Usage:
java knittingTranslator


The full command line version of the Knitting Translator offers a three item menu. 1. Offers the option to translate German term(s) into English. 2. Offers the ability to add a term to the dictionary. 3. Offers the option to exit the program.
Upon translating a text through option 1, the program will ask if the user wants to save the translation. If they enter "yes", the translation will be saved to a .txt file. 
### Command Line Quick Access Version:
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/knittingTranslator2.JPG "GUI Version")
### Compilation:
javac knittingTranslator.java
### Usage:
java knittingTranslator \<term to translate\>
  
The shortened version can take the German term(s) to translate from the command line and return the translation before exiting. 
***
#### To Do:
+ Update dictionaries and add languages.
+ GUI Version doesn't wrap text in textboxes.
+ Unicode characters not being recognized.
