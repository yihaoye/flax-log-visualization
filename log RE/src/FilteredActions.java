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
		c_s_filtered_actions_map.get("&c=collocations").put("&s=RetrieveWordNet", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=RetrieveWordFamily", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=CollocationRetrieveByAID", 1);
		c_s_filtered_actions_map.get("&c=collocations").put("&s=WikipediaArticleSearch", 1);
	
		//c=
		
		
		//c=
		
		
	}
	
	public boolean filter_action(String strLine){
		
		Matcher matcher_actions[] = new Matcher[pattern_actions.length];
		for(int i=0;i<pattern_actions.length;i++){
			matcher_actions[i] = pattern_actions[i].matcher(strLine);
		}
		
		if(!matcher_actions[2].find()){
			System.out.println("nouid");
			return true;
		}
		
		if(matcher_actions[0].find() && matcher_actions[1].find()){
			System.out.println("check");
			if(c_s_filtered_actions_map.containsKey(matcher_actions[0].group()) && c_s_filtered_actions_map.get(matcher_actions[0].group()).containsKey(matcher_actions[1].group())){ //check if contain filtered s_action
				System.out.println(matcher_actions[0].group() + " " + c_s_filtered_actions_map.get(matcher_actions[0].group()).get(matcher_actions[1].group()));
				return true;
			}else{
				System.out.println("not4");
				return false;
			}
		}else{
			System.out.println("notall");
			return true;
		}
	}
	
}
