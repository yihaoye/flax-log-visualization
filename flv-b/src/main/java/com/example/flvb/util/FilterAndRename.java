package com.example.flvb.util;

import java.util.HashMap;

public class FilterAndRename {

	//the hashmap (c_s_filtered_actions_map) mainly used to store white list for filter, inner hashmap is also for changing s_action name, more straightforward for user
	HashMap<String, HashMap<String, String>> c_s_filtered_actions_map = new HashMap<String, HashMap<String, String>>();
	
	//may be used in future, c_action_map can be used for rename c_action name as above one
	HashMap<String, String> c_action_map = new HashMap<String, String>();
	
	
	public FilterAndRename() {
		//c=collocations and its relevant s_action
		this.c_s_filtered_actions_map.put("&c=collocations", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationQuery", "Query");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=ClassifierBrowse", "Classifier Browse");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationMapRetrieve", "Related Collocations");
		//this.c_s_filtered_actions_map.get("&c=collocations").put("&s=WikipediaArticleDefinitionRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationSampleSentenceRetrieve", "Sample Sentence");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationSampleRetrieve", "More Collocations");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=AdvanceFieldQuery", "Advance Field Query");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=CherryPicking", "Collecting Collocations");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationMatching", "Matching activity");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationDominoes", "Domino activity");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationGuessing", "Guessing activity");
		this.c_s_filtered_actions_map.get("&c=collocations").put("&s=RelatedWords", "Related Words activity");
		
		/*
		//c=BAWESS
		this.c_s_filtered_actions_map.put("&c=BAWESS", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=ClassifierBrowser", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxWordQuery", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=AdvancedFieldQuery", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxCollocationBrowse", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxLexicalBundle", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxWordList", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=CherryPicking", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxCollocationRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxSampleRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxTextRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=WikipediaRelatedArticle", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=WikipediaArticleDefinitionRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxCollocationContextRetrieve", null);
		
		//c=phrases
		this.c_s_filtered_actions_map.put("&c=phrases", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=phrases").put("&s=PhraseSearch", null);
		
		//c=password
		this.c_s_filtered_actions_map.put("&c=password", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=password").put("&s=RelatedWords", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=ClassifierBrowse", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=Hangman", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=ContentWordGuessing", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=CollocationMatching", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=ScrambleSentence", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=CollocationFillinBlanks", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=MissingPunctuation", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=ScrambleParagraph", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=FlaxCollocationBrowse", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=AdvancedFieldQuery", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=CollocationGuessing", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=SplitSentences", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=PredictingWords", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=FlaxWordList", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=CherryPicking", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=FlaxWordQuery", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=CollocationDominoes", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=FlaxCollocationRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=classifierbrowse", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=FlaxDocumentRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=password").put("&s=FlaxTextRetrieve", null);
		
		//c=englishcommonlaw
		this.c_s_filtered_actions_map.put("&c=englishcommonlaw", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=RelatedWords", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=ClassifierBrowse", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=ContentWordGuessing", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=ScrambleSentence", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=CollocationFillinBlanks", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=ScrambleParagraph", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxCollocationBrowse", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=AdvancedFieldQuery", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=CollocationGuessing", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=SplitSentences", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxWordList", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=CherryPicking", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxWordQuery", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=CollocationDominoes", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxCollocationRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxSampleRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxLexicalBundle", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxTextRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxCollocationContextRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=WikipediaArticleDefinitionRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=WikipediaRelatedArticle", null);

		//c=flaxc
		this.c_s_filtered_actions_map.put("&c=flaxc", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=ClassifierBrowse", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=ContentWordGuessing", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=ScrambleSentence", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=ScrambleParagraph", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxCollocationBrowse", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=AdvancedFieldQuery", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=SplitSentences", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxWordList", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=CherryPicking", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxWordQuery", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxCollocationRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxTextRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=Hangman", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=MissingPunctuation", null);
		this.c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxCollocationRetrieve", null);

		//c=bookphrases
		this.c_s_filtered_actions_map.put("&c=bookphrases", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=bookphrases").put("&s=BookPhraseSearch", null);
		this.c_s_filtered_actions_map.get("&c=bookphrases").put("&s=PhraseSearch", null);
		
		//c=PAAH
		this.c_s_filtered_actions_map.put("&c=PAAH", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=ClassifierBrowse", null);
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxCollocationBrowse", null);
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=AdvancedFieldQuery", null);
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxWordList", null);
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=CherryPicking", null);
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxWordQuery", null);
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxCollocationRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxCollocationContextRetrieve", null);
		this.c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxLexicalBundle", null);

		//c=ejfield0c1
		this.c_s_filtered_actions_map.put("&c=ejfield0c1", new HashMap<String, String>());
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=ClassifierBrowse", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=ContentWordGuessing", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=ScrambleSentence", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=CollocationFillinBlanks", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=MissingPunctuation", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=ScrambleParagraph", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=FlaxCollocationBrowse", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=AdvancedFieldQuery", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=SplitSentences", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=FlaxWordList", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=CherryPicking", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=FlaxWordQuery", null);
		this.c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=FlaxCollocationRetrieve", null);
		*/
		
	}
	
	
	public boolean process(String[] str_line_abstract_info) {
		
		if (check(str_line_abstract_info[2]) && check(str_line_abstract_info[3]) && check(str_line_abstract_info[4])) { // if all of uid, c_action, s_action are not empty, carry out following statements, or go to else{}
			if (this.c_s_filtered_actions_map.containsKey(str_line_abstract_info[3]) && this.c_s_filtered_actions_map.get(str_line_abstract_info[3]).containsKey(str_line_abstract_info[4])) { // check if contain filtered s_action
				// only rename the action when not skip the str_line (应该没问题，过后有空再检查一下)
				str_line_abstract_info[4] = this.c_s_filtered_actions_map.get(str_line_abstract_info[3]).get(str_line_abstract_info[4]); // rename the s_action
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	public boolean check(String str) {
		if (str != null && !str.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
}
