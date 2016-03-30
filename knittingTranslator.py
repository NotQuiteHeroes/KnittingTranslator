# -*- coding: UTF-8 -*-

from nltk import word_tokenize
from string import punctuation 

#Dictionary holding german words for keys and their translated english 
#counterpart as values
oneWordDict = {'ab':'from','abheben' : 'to slip', 'abketten':'to bind off', 'abnahmegang':'decrease row', 'abnehmen':'to decrease', 'abnehme':'decrease', 
'abschrÃ¤gen':'shape', 'alle':'all', 'anderen':'another', 'anfang':'beginning', 'anfangen':'begin', 'anschlag':'casting on', 'anschlagen':'cast on', 
'armausschnitt':'armhole', 'Ã¤rmel':'sleeve', 'Ã¤rmelschrÃ¤gung':'sleeve shaping', 'auf':'on', 'auffassen':'to pick up sts', 'aufnahme':'increase', 
'aufnehmen':'to increase', 'bei':'at', 'beide':'both', 'beidseitig':'both sides', 'bis':'until', 'bleiben':'remain', 'breite':'wide', 
'dabei':'at same time', 'dann':'then', 'darÃ¼berstricken':'knit across', 'dauern':'last', 'der':'the', 'die': 'the' , 'das':'the', 'durch':'through [by]',
'einmal':'once', 'einsetzen':'insert', 'ersten':'first', 'faden':'yarn', 'folgen':'follow', 'folgt':'follows', 'gang':'round', 'ganz':'whole',
'gerade':'straight', 'gleichzeitig':'at the same time', 'hilfsnadel':'stitch holder', 'hinter':'behind', 'hinÃ¼ber':'over', 'immer':'always', 
'jede':'each', 'jeder':'every', 'jetzt':'now', 'kante':'edge', 'masche':'stitch', 'maschen':'stitches', 'muster':'pattern [stitch pattern]', 
'nÃ¤hen':'sew', 'naht':'seam', 'nÃ¤hte':'seams', 'noch':'in addition [next]', 'nun':'now', 'perlmuster':'seed stitch', 'rand':'side [edge]', 
'rechts':'right [right side]', 'reihe':'row', 'restliche':'remaining', 'sind':'are [there are]', 'spielstricknadeln':'dpns', 'Ã¼ber':'over', 
'umgekehrt':'reverse', 'und':'and', 'unter':'under', 'wechseln':'turn [change][switch]', 'weite':'width', 'weiterarb':'continue to work', 
'weiterhin':'from now on [continue]', 'wenn':'when', 'wie':'as', 'wiederholen':'repeat', 'widerholungszeichen':'repeat from','zunehmen':'to increase [increase]', 'zusammen':'together', 'zusammenstricken':'knit together [k2tog]', 'zweimal':'twice','zweite':'second','abkÃ¼rzungen':'abbreviations', 'maschenmarkierer':'stitch marker', 'stricken':'knit [knitting]', 'reihen':'rows', 'drehen':'turn', 'linken':'left','drei':'three', 'wieder':'again', 'aufstricken': 'of knitting [on knitting]', 'weitere':'more', 'aus':'from [of]', 'ruckseite':'wrong side [WS]','bestent':'consists', 'bis':'to', 'letzten':'last', 'zu': 'to', 'den':'the', 'dem':'the', 'kraus':'garter', 'lochmuster':'eyelet pattern', 'rÃ¼ckreihe':'wrong side [WS]', 'vorderseite':'right side [RS]','hinreihe':'right side [RS]', 'endmuster':'final pattern', 'zum':'to', 'folgendermassen':'as follows', 'aufbaureihe':'set up','mal':'times','arbeiten':'work', 'man':'one', 'kann':'can', 'vor':'before', 'locker':'loosely', 'fÃ¤denvernÃ¤hen':'weave in ends', 'spannen':'tension [block]',
'nach':'as', 'wunsch':'desired', 'mm':'stitch marker', 'mms':'place marker', 'U':'yarn over [yo]', 're':'knit [k]', 'li':'purl [p]', 'm':'stitch [st]', 'grda':'degrees',
'2rezus':'k2tog', 'um':'about', 'gerade':'straight', 'grÃ¶ÃŸe':'size', 'maschenprobe':'swatch', 'gestrickt':'knitted', 'mit':'with', 'mre':'knit', 'mli':'purl', 'sie':'they', 'erscheinen':'appear', "1mli":"purl one", "1mre":"knit one", "2mli":"purl two", "2mre":"knit two", "3mli":"purl three", "3mre":"knit three", "4mli":"purl four", "4mre":"knit four", "5mli":"purl five", "5mre":"knit five", "nadelspiel":"needle size", "werden":"will", "eine":"a", "runde":"round", "chart":"chart", "gewÃ¼nschte":"desired", "erreicht":"reached", "ist":"is", "ferse":"heel", "mÃ¶glich":"possible", "wenden":"turn", "von":"from", "glattrechtsmuster":"stockinette stitch pattern"}

#Create empty list to be populated with translated terms
translated = []

#This is the main module
def main():
	print("Welcome to the German to English knitting translator.")
	user_input = raw_input("Please enter the term or terms to be translated: ")
	term_list = word_tokenize(user_input.lower())
	print("Your term will be translated and returned in English. Any alternatives will be displayed in brackets")
	search_dict(term_list)
	print(' '.join(translated))		#Concatenate list translated
	again = raw_input("Would you like to translate another? ")
	if again.lower() == "yes":		
		translated[:] = []			#Clears list translated for next iteration 
		main()
	elif again.lower() == "no":
		print("Thank you for using the knitting translator!")

#This function searches the provided dictionary and returns the proper translation
def search_dict(term_list):
	for term in term_list:
		if term.isdigit() or term in punctuation:
			translated.append(term)
		elif term == 'x':
                        translated.append(term)
		elif term in oneWordDict.keys():
			translated.append(oneWordDict[term])
		else:
			print(term)
	return translated


main()
