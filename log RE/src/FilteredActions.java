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
		c_s_filtered_actions_map.get("&c=collocations").put("&s=WikipediaArticleDefinitionRetrieve", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationSampleSentenceRetrieve", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationSampleRetrieve", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=AdvanceFieldQuery", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CherryPicking", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationMatching", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationDominoes", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationGuessing", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=RelatedWords", 1);
		
		//c=
		
		
		//c=
		
		
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
