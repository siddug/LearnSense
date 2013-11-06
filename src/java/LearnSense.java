/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author varun
 */
@WebServlet(name = "LearnSense", urlPatterns = {"/LearnSense"})
public class LearnSense extends HttpServlet {
    
    public static Vector<String> doc;
    public static Vector<String> clues;
    public static Vector<HashMap<String, Integer>> topic;
    public static Vector<Integer> score;
    public static HashMap<String, Integer> KeyWords;
    public static String[] final_s;
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LearnSense</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LearnSense at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }    

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        if (request.getParameter("qtype").equals("query")) {
        if (request.getParameter("query") != null) {
            String word = request.getParameter("query").toString();
            ArrayList<ArrayList<String>> FullMeanings = new ArrayList();;

            try{
            FullMeanings = new WordnetMeanings().getFullMeanings(word);
            }catch(Exception e){}

            int cur_count = 0, max_count = 0;
            ArrayList<String> MaxMeaning = null;
            if (!FullMeanings.isEmpty()) {
                MaxMeaning = FullMeanings.get(0);
            }
            for (ArrayList<String> FullMeaning : FullMeanings) {
                for (String meaning : FullMeaning) {
                    if (KeyWords.containsKey(meaning)) {
                        cur_count++;
                    }
                }
                if (cur_count > max_count) {
                    max_count = cur_count;
                    MaxMeaning = FullMeaning;
                }
            }
            
            String result="";
            System.out.println("Most relevant meaning: ");
            for (String meaning : MaxMeaning) {
                //System.out.print(meaning + " ");
                result=result+meaning+" ";
            }
            System.out.println(result);
            HttpSession session = request.getSession();            
            session.setAttribute("Meaning", result);
            session.setAttribute("queryword", word);
            response.sendRedirect("query.jsp");
        }
        }
        
        else{
            if (request.getParameter("query") != null) {
            String text_content = request.getParameter("query").toString();
            System.out.println(text_content);
            ArrayList<String> arr = new ArrayList<String>();
            try{
                arr = new PostagText().getNouns(text_content);
            }catch(Exception e){
                System.out.println(e);
            }
            System.out.println("Nouns: "+arr);
            doc = new Vector<String>(arr);
            clues = new Vector<String>();
            topic = new Vector<HashMap<String, Integer>>();
            score = new Vector<Integer>();
            try{
                find_topics();
            }catch(Exception e){}
            HttpSession session = request.getSession();
            int i;
            for(i=0;i<5 && i<final_s.length;i++){
                System.out.println("Keyword"+i+": "+final_s[i]);
                session.setAttribute("Keyword"+i, final_s[i]);
            }
            session.removeAttribute("queryword");            
            session.removeAttribute("Meaning");
            session.setAttribute("Num_keys", i);
            session.setAttribute("Text_Content",text_content);
            session.setAttribute("Meaning","null");
            response.sendRedirect("query.jsp");
        }	
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        if (request.getParameter("textcontent") != null) {
            String text_content = request.getParameter("textcontent").toString();
            System.out.println(text_content);
            ArrayList<String> arr = new ArrayList<String>();
            try{
                arr = new PostagText().getNouns(text_content);
            }catch(Exception e){
                System.out.println(e);
            }
            System.out.println("Nouns: "+arr);
            doc = new Vector<String>(arr);
            clues = new Vector<String>();
            topic = new Vector<HashMap<String, Integer>>();
            score = new Vector<Integer>();
            try{
                find_topics();
            }catch(Exception e){}
            HttpSession session = request.getSession();
            int i;
            for(i=0;i<5 && i<final_s.length;i++){
                System.out.println("Keyword"+i+": "+final_s[i]);
                session.setAttribute("Keyword"+i, final_s[i]);
            }
            session.setAttribute("Num_keys", i);
            session.setAttribute("Text_Content",text_content);
            response.sendRedirect("query.jsp");
        }	
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public void fill_clues(int set) throws Exception {
        clues.clear();
        score.clear();
        //String[] clu1 = {"act", "firing", "projectile", "missile", "firearm", "sports", "act", "ball", "club", "racket", "bat", "cue", "hand", "chance", "something", "person", "respect", "ability", "round", "object", "games", "projectile", "musket", "object", "shape", "people", "dance", "glands", "androgens", "total", "goods", "services", "money", "time", "labor", "property", "material", "worth", "amount", "money", "something", "value", "something", "republic", "Africa", "Rhodesia", "independence", "United", "Kingdom", "cricket", "equipment", "set", "stumps", "crosspieces", "playing", "cricket", "arch", "croquet", "equipment", "gate", "door", "part", "door", "opening", "window", "door", "business"};
        //String[] clu2 = {"United", "States", "jazz", "musician", "act", "foot", "perennials", "leaves", "flowers", "spikes", "flowers", "plant", "brewing", "taste", "beer", "dance", "music", "boundary", "surface", "line", "limits", "area", "side", "intersection", "surfaces", "object", "attribute", "urgency", "tone", "voice", "advantage", "position", "cricket", "bowler", "side"};
        //String[] clu3 = {"republic", "Africa", "Rhodesia", "independence", "United", "Kingdom", "cricket", "equipment", "set", "stumps", "crosspieces", "playing", "cricket", "arch", "croquet", "equipment", "gate", "door", "part", "door", "opening", "window", "door", "business", "number", "sum", "base", "decimal", "system", "playing", "cards", "deck", "pips", "face", "cricket", "division", "play", "balls", "batsman", "player", "team", "end", "pitch", "instance", "occasion", "event", "period", "time", "resource", "control", "something", "period", "attributes", "activities", "moment", "continuum", "experience", "events", "future", "present", "past", "round", "object", "games", "projectile", "musket", "object", "shape", "people", "dance", "glands", "androgens", "beverage", "act", "beverages", "excess", "liquid", "drinking", "body", "water", "act", "occurrence", "activity", "piece", "luck", "geology", "crack", "earth", "crust", "displacement", "side", "respect", "separation", "factions", "pause", "something", "work", "cricket", "equipment", "set", "stumps", "crosspieces", "playing", "cricket", "arch", "croquet", "equipment", "gate", "door", "part", "door", "opening", "window", "door", "business"};
        ArrayList<String> GetMeanings = new ArrayList<String>();
        for (int i = set; i < set + 11 && i < doc.size(); i++) {
            GetMeanings.add(doc.get(i));
        }
        ArrayList<String> clu = new WordnetMeanings().extractMeanings(GetMeanings);
        /*if(set==1){clu=clu1;}
         if(set==2){clu=clu2;}
         if(set==3){clu=clu3;}*/
        clues = new Vector<String>(clu);

        int i;
        for (i = 0; i < clues.size(); i++) {
            score.add(1);
        }
        for (i = 0; i < clues.size(); i++) {
            for (int j = 0; j < clues.size(); j++) {
                if (j != i) {
                    if (clues.get(i).equals(clues.get(j))) {
                        score.set(i, score.get(i) + 1);
                    }
                }
            }
        }

        for (i = 0; i < clues.size(); i++) {
            for (int j = i; j < clues.size(); j++) {
                if (score.get(i) < score.get(j)) {
                    String temp;
                    int temp_score;
                    temp = clues.get(i);
                    temp_score = score.get(i);
                    clues.set(i, clues.get(j));
                    score.set(i, score.get(j));
                    clues.set(j, temp);
                    score.set(j, temp_score);
                }
            }
        }



        HashMap h = new HashMap();
        int maximum = 20;
        for (i = 0; i < clues.size() && maximum > 0; i++) {
            if (!h.containsKey(clues.get(i))) {
                h.put(clues.get(i), score.get(i));
                maximum--;
            }
        }
        topic.add(h);


    }

    public void find_topics() throws Exception {
        int i = 0;
        for (i = 0; i < doc.size(); i += 11) {
            fill_clues(i);
        }        

        HashMap h2 = new HashMap();
        for (i = 0; i < topic.size(); i++) {
            Set s2 = (topic.get(i)).entrySet();
            Iterator iter2 = s2.iterator();

            while (iter2.hasNext()) {
                Map.Entry mEntry = (Map.Entry) iter2.next();
                //System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
                if (h2.containsKey(mEntry.getKey())) {
                    h2.put(mEntry.getKey(), Integer.parseInt(mEntry.getValue().toString()) + Integer.parseInt(h2.get(mEntry.getKey()).toString()));
                } else {
                    h2.put(mEntry.getKey(), mEntry.getValue());
                }
            }
        }
        int final_size = 0;
        final_s = new String[20];
        int[] final_values = new int[20];
        for (i = 0; i < 20; i++) {
            if (!h2.isEmpty()) {
                Set sett = h2.entrySet();
                Iterator iterr = sett.iterator();
                int max = 0;
                String top = "";
                while (iterr.hasNext()) {
                    Map.Entry mEntry = (Map.Entry) iterr.next();
                    if (Integer.parseInt(mEntry.getValue().toString()) > max) {
                        max = Integer.parseInt(mEntry.getValue().toString());
                        top = mEntry.getKey().toString();
                    }
                }
                h2.remove(top);
                final_s[i] = top;
                final_values[i] = max;
                final_size++;
            } else {
                i = 20;
            }
        }

        //debug code
				/*Set s=h2.entrySet();
         Iterator iter = s.iterator();
				
         while(iter.hasNext()){
         Map.Entry mEntry = (Map.Entry) iter.next();
         System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
         }*/
        KeyWords = new HashMap<String, Integer>();
        System.out.println("Entered find topics "+final_size);
        for (i = 0; i < final_size; i++) {
            if (i < 5) {
                System.out.println("finals"+i+": "+final_s[i]);
                KeyWords.put(final_s[i], final_values[i]);
            }
            System.out.print(final_s[i] + " : " + final_values[i] + "\n");
        }

    }

}

