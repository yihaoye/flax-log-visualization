import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilteredActions {

	HashMap<String, HashMap<String, Integer>> c_s_filtered_actions_map = new HashMap<String, HashMap<String, Integer>>();
	
	/* get all unique action_c and action_s */
	Pattern pattern_actions[] = {Pattern.compile("\\&c=\\w+"), 
								Pattern.compile("\\&s=\\w+"),
								Pattern.compile("uid=\\w+")};
	
	public FilteredActions(){
		
		//c=collocations and its relevant s_action
		c_s_filtered_actions_map.put("&c=collocations", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationQuery", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=ClassifierBrowse", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationMapRetrieve", 1);
		//c_s_filtered_actions_map.get("&c=collocations").put("&s=WikipediaArticleDefinitionRetrieve", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationSampleSentenceRetrieve", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationSampleRetrieve", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=AdvanceFieldQuery", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CherryPicking", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationMatching", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationDominoes", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationGuessing", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=RelatedWords", 1);
		
		/*
		//c=BAWESS
		c_s_filtered_actions_map.put("&c=BAWESS", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=ClassifierBrowser", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxWordQuery", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=AdvancedFieldQuery", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxCollocationBrowse", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxLexicalBundle", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxWordList", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=CherryPicking", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxCollocationRetrieve", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxSampleRetrieve", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxTextRetrieve", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=WikipediaRelatedArticle", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=WikipediaArticleDefinitionRetrieve", 1);
		c_s_filtered_actions_map.get("&c=BAWESS").put("&s=FlaxCollocationContextRetrieve", 1);
		
		//c=phrases
		c_s_filtered_actions_map.put("&c=phrases", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=phrases").put("&s=PhraseSearch", 1);
		
		//c=password
		c_s_filtered_actions_map.put("&c=password", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=password").put("&s=RelatedWords", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=ClassifierBrowse", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=Hangman", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=ContentWordGuessing", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=CollocationMatching", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=ScrambleSentence", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=CollocationFillinBlanks", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=MissingPunctuation", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=ScrambleParagraph", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=FlaxCollocationBrowse", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=AdvancedFieldQuery", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=CollocationGuessing", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=SplitSentences", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=PredictingWords", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=FlaxWordList", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=CherryPicking", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=FlaxWordQuery", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=CollocationDominoes", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=FlaxCollocationRetrieve", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=classifierbrowse", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=FlaxDocumentRetrieve", 1);
		c_s_filtered_actions_map.get("&c=password").put("&s=FlaxTextRetrieve", 1);
		
		//c=englishcommonlaw
		c_s_filtered_actions_map.put("&c=englishcommonlaw", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=RelatedWords", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=ClassifierBrowse", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=ContentWordGuessing", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=ScrambleSentence", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=CollocationFillinBlanks", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=ScrambleParagraph", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxCollocationBrowse", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=AdvancedFieldQuery", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=CollocationGuessing", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=SplitSentences", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxWordList", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=CherryPicking", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxWordQuery", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=CollocationDominoes", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxCollocationRetrieve", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxSampleRetrieve", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxLexicalBundle", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxTextRetrieve", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=FlaxCollocationContextRetrieve", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=WikipediaArticleDefinitionRetrieve", 1);
		c_s_filtered_actions_map.get("&c=englishcommonlaw").put("&s=WikipediaRelatedArticle", 1);

		//c=flaxc
		c_s_filtered_actions_map.put("&c=flaxc", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=ClassifierBrowse", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=ContentWordGuessing", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=ScrambleSentence", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=ScrambleParagraph", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxCollocationBrowse", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=AdvancedFieldQuery", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=SplitSentences", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxWordList", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=CherryPicking", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxWordQuery", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxCollocationRetrieve", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxTextRetrieve", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=Hangman", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=MissingPunctuation", 1);
		c_s_filtered_actions_map.get("&c=flaxc").put("&s=FlaxCollocationRetrieve", 1);

		//c=bookphrases
		c_s_filtered_actions_map.put("&c=bookphrases", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=bookphrases").put("&s=BookPhraseSearch", 1);
		c_s_filtered_actions_map.get("&c=bookphrases").put("&s=PhraseSearch", 1);
		
		//c=PAAH
		c_s_filtered_actions_map.put("&c=PAAH", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=ClassifierBrowse", 1);
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxCollocationBrowse", 1);
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=AdvancedFieldQuery", 1);
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxWordList", 1);
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=CherryPicking", 1);
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxWordQuery", 1);
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxCollocationRetrieve", 1);
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxCollocationContextRetrieve", 1);
		c_s_filtered_actions_map.get("&c=PAAH").put("&s=FlaxLexicalBundle", 1);

		//c=ejfield0c1
		c_s_filtered_actions_map.put("&c=ejfield0c1", new HashMap<String, Integer>());
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=ClassifierBrowse", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=ContentWordGuessing", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=ScrambleSentence", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=CollocationFillinBlanks", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=MissingPunctuation", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=ScrambleParagraph", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=FlaxCollocationBrowse", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=AdvancedFieldQuery", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=SplitSentences", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=FlaxWordList", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=CherryPicking", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=FlaxWordQuery", 1);
		c_s_filtered_actions_map.get("&c=ejfield0c1").put("&s=FlaxCollocationRetrieve", 1);
		*/
		
	}
	
	
	public boolean filter_action(String strLine){
		
		Matcher matcher_actions[] = new Matcher[pattern_actions.length];
		for(int i=0;i<pattern_actions.length;i++){
			matcher_actions[i] = pattern_actions[i].matcher(strLine);
		}
		
		if(matcher_actions[0].find() && matcher_actions[1].find() && matcher_actions[2].find()){
			if(c_s_filtered_actions_map.containsKey(matcher_actions[0].group()) && c_s_filtered_actions_map.get(matcher_actions[0].group()).containsKey(matcher_actions[1].group())){ //check if contain filtered s_action
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
}
