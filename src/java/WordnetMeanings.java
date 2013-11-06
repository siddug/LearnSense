/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author varun
 */
import java.util.*;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
public class WordnetMeanings {
	public ArrayList<String> extractMeanings(ArrayList<String> Words) throws Exception{
		System.setProperty("wordnet.database.dir", "/home/varun/hacku/WordNet-3.0/dict/");
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		ArrayList<String> result = new ArrayList<String>();
		PostagText ptt = new PostagText();
		for(String word : Words){
			Synset[] synsets = database.getSynsets(word);
			for (int i = 0; i < synsets.length && i < 5; i++)
			{				
				ArrayList<String> NounMeaning = ptt.getNouns(synsets[i].getDefinition());
				/*String[] wordForms = synsets[i].getWordForms();
				for (int j = 0; j < wordForms.length; j++)
				{
					System.out.print((j > 0 ? ", " : "") +
							wordForms[j]);
				}*/
				result.addAll(NounMeaning);
				//System.out.println(": " + synsets[i].getDefinition());
			}
		}
		return result;
	}
	
	public ArrayList<ArrayList<String>> getFullMeanings(String word) throws Exception{
		System.setProperty("wordnet.database.dir", "/home/varun/hacku/WordNet-3.0/dict/");
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();		
		Synset[] synsets = database.getSynsets(word);
		for (int i = 0; i < synsets.length && i < 5; i++)
		{				
			String FM = synsets[i].getDefinition();
			String[] FullMeaning = FM.split(" ");
			ArrayList<String> MeaningWords = new ArrayList<String>(Arrays.asList(FullMeaning));
			/*String[] wordForms = synsets[i].getWordForms();
			for (int j = 0; j < wordForms.length; j++)
			{
				System.out.print((j > 0 ? ", " : "") +
						wordForms[j]);
			}*/
			result.add(MeaningWords);
			//System.out.println(": " + synsets[i].getDefinition());
		}
		return result;
	}
}

