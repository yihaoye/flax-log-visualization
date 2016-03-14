import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;

public class LogRegExp {
    public static void main(String argv[]) {
    	try{
    		   FileInputStream fstream = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log");
    		   BufferedReader in = new BufferedReader(new InputStreamReader(fstream));
    		   String strLine;
    		   String phrasedLine = "";
    		   
    		   //Pattern pattern = Pattern.compile("(\\[\\d{4}-\\d{2}-\\d{2}\\])()()()");
    		   Pattern pattern[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
    				   Pattern.compile("\\d{2}:\\d{2}:\\d{2}"), 
    				   Pattern.compile("uid=537"),
    				   Pattern.compile("s=\\w+"), 
    				   Pattern.compile("c=\\w+"), 
    				   Pattern.compile("s1.query=\\w+"),
    				   Pattern.compile("s1.dbName=\\w+")};
    		   
    		   /*
    		   Pattern pattern[] = new Pattern[6];
    		   pattern[0] = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    		   pattern[1] = Pattern.compile("uid=537");
    		   pattern[2] = Pattern.compile("s=\\w+");
    		   pattern[3] = Pattern.compile("c=\\w+");
    		   pattern[4] = Pattern.compile("s1.query=\\w+");
    		   pattern[5] = Pattern.compile("s1.dbName=\\w+");
    		   */
    		   
    		   
    		   /* read log line by line */
    		   while ((strLine = in.readLine()) != null)   {
    		     /* parse strLine to obtain what you want */
    			   
    			   Matcher matcher[] = new Matcher[pattern.length];
    			   for(int i=0;i<pattern.length;i++){
    				   matcher[i] = pattern[i].matcher(strLine);
    			   }
    			   /*
    			   Matcher matcher1 = pattern1.matcher(strLine);
    			   Matcher matcher2 = pattern2.matcher(strLine);
    			   Matcher matcher3 = pattern3.matcher(strLine);
    			   Matcher matcher4 = pattern4.matcher(strLine);
    			   Matcher matcher5 = pattern5.matcher(strLine);
    			   Matcher matcher6 = pattern6.matcher(strLine);
    			   */
    			   if(matcher[0].find() && matcher[0].group().equals("2016-01-28") && matcher[2].find()){
    				   //System.out.println (strLine);
    				   for(int i=0;i<pattern.length;i++){
    					   if(i==0 || i==2 || matcher[i].find()){
    						   phrasedLine += matcher[i].group() + " ";
    					   }
    				   }
    				   System.out.println (phrasedLine);
    				   phrasedLine = "";
    			   }
    			   
    			   /*if(matcher.find()){
    				   System.out.println (matcher.group());
    			   }*/
    			   
    			   //System.out.println (strLine);
    		   }
    		   in.close();
    		} catch (Exception e) {
    		     System.err.println("Error: " + e.getMessage());
    		}
    }
}

//Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs

