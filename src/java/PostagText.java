/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author varun
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PostagText {
	public ArrayList<String> getNouns(String text) throws Exception{
                System.out.println("Postagtext called with "+text);
		MaxentTagger tagger = new MaxentTagger("/home/varun/hacku/stanford-postagger-2013-06-20/models/wsj-0-18-bidirectional-nodistsim.tagger");
		
		FileInputStream fstream = new FileInputStream("/home/varun/hacku/stop-words-collection-2011.11.21/stop-words/stop-words-english1.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		HashSet<String> CommonWords = new HashSet<String>();
		String strLine;
		while ((strLine = br.readLine()) != null){
			CommonWords.add(strLine);
		}
		CommonWords.add("act");
		// The tagged string
		String tagged = tagger.tagString(text);
		 
		// Output the result
		//System.out.println(tagged);
		
		ArrayList<String> NounsAndVerbs = new ArrayList<String>();
		
		String[] TaggedWords = tagged.split(" ");
		
		for(String TaggedWord : TaggedWords){
			String[] PosBreakup = TaggedWord.split("_");
			String Pos = PosBreakup[PosBreakup.length-1];						
			if(Pos.equals("NN") || Pos.equals("NNS"))NounsAndVerbs.add(PosBreakup[0]);			
		}
                
                
		NounsAndVerbs.removeAll(CommonWords);
		System.out.println(NounsAndVerbs);	
		return NounsAndVerbs;		
	}
}
