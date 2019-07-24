/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.recsystool;

/**
 *
 * @author Daniele
 */



import com.google.common.base.Function;
import com.google.common.base.Functions;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.scoring.PageRankWithPriors;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.VisualizationViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class SimpleGraph {

    private Graph graph;
    private HashMap<String, String[]> luoghi;
    private ArrayList<String> contesti;
    private List<String> contesto;
    private HashMap<String, ArrayList<String>> preferenze;
    private HashMap<String, Point2D> layoutvertici;
    private int width=1000;
    private int height=800;
    private int nodi_P = 0, nodi_C = 0, nodi_L = 0, nodi_D = 0, archi_PC = 0, archi_CL = 0, archi_LD = 0;
    private int P_map=0, C_map=0, L_map=0, D_map=0;
   
    static int column_userId=0;
    static int column_item=1;
    static int column_context=3;
    static int column_voto=2;
    static String percorso="data\\movie.txt";
    static int userid=0;
    ArrayList<String> contestiUtente=new ArrayList<String>();
    ArrayList<String> itemUtente=new ArrayList<String>();
    
    public SimpleGraph( int user, int item, int context, int rating, int numero_context,boolean direct, String del, String targetUser, int soglia_rating) throws IOException {
        
        
        
    column_userId=user;
    column_item=item;
    column_context=context;
    column_voto=rating;
        //Save context user locally
   
 
    layoutvertici = new HashMap<>();
        //Instantiante Graph Oject
    graph = new OrderedSparseMultigraph<>();
   // int num_events = number_events;
      

        Random r=new Random();
        File file= new File(percorso);
        BufferedReader br=new BufferedReader(new FileReader (file));
        String st;
        String[] all;
        int numCont;
      
            int i=0;
           
               while((st=br.readLine())!=null){
                   i++;
                
                        all=st.split(del);
                        if(!graph.containsVertex("P_"+all[column_userId])){
                        
                           graph.addVertex("P_"+all[column_userId]);
                           nodi_P++;
                        }
                 String contexts="C_";   
                 
                  contexts+=all[column_context];
                 // System.out.println(all[column_context]);
             
                  if(numero_context>1){
                 
              
                  for(int j=1;j<=numero_context-1;j++){
                  contexts+=all[column_context+j];
              }

                  }
                  //System.out.println(contexts);

                   if(Integer.parseInt(all[2])>=soglia_rating){      
                   if(!graph.containsVertex(contexts)){
                       graph.addVertex(contexts);
                       nodi_C++;
                       //System.out.println("aggiunto il contesto " + contexts + " per il rating " + all[2]);
                   }
                    
                    if(!graph.containsVertex("L_"+all[column_item])){
                     graph.addVertex("L_"+all[column_item]);
                        nodi_L++;
                    }
                    
                    EdgeType orient;
                    if(direct==false){
                         orient = EdgeType.UNDIRECTED;
                    }
                    else{
                    orient = EdgeType.DIRECTED;
                    }
                    
                   
                    graph.addEdge("PC:" + (++archi_PC), new Pair<>("P_" + all[column_userId], contexts), orient);
  
                    
                    graph.addEdge("CL:" + (++archi_CL), new Pair<>(contexts, "L_" + all[column_item]), orient);

                    if(targetUser.equals(all[column_userId])){
                 
                     
                        contestiUtente.add(contexts);
        
                        itemUtente.add("L_"+all[column_item]);
                    }
                 
                   }
                  //  System.out.println("utente "+a + " per il locale "+ all[column_item]+" nel contesto "+ contesti.get(numCont-1)+" ha votato "+all[column_voto]);
                    //}
                   
                
            }//}
        
        
        


        
    }

    
 

 

  
    public HashMap<String, Double> Pagerank(int top_results) {

        HashMap<String, Double> result_pr = new HashMap<String, Double>();

        PageRank ranker = new PageRank(graph, 0.3);
  
        ranker.evaluate();
        System.out.println("--Numero Utenti     "+nodi_P);
        System.out.println("--Numero Item    "+nodi_L);
        System.out.println("--Numero Contesti    "+nodi_C);
        System.out.println("\n---PageRank - Tolerance = " + ranker.getTolerance()+" - Dump factor = " + (1.00d - ranker.getAlpha()+ " - Max iterations = " + ranker.getMaxIterations()));
        HashMap<String, Double> map = new HashMap();
        for (Object v : graph.getVertices()) {
            if (v.toString().contains("L_") && !ranker.getVertexScore(v).toString().equals("0.0")) {
                map.put(v.toString(), (Double) ranker.getVertexScore(v));
            }
        }

        Object[] obj = map.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).toArray();

        for (int i = 0; i < top_results; i++) {
            String nome = obj[i].toString();
            //System.out.println(nome);
            String score = nome.substring(nome.indexOf("=") + 1);
            nome = nome.substring(nome.indexOf("_") + 1, nome.indexOf("="));
            result_pr.put(nome, Double.valueOf(score));
            String stamp = i + 1 + " - " + nome + " - Score: " + score;
            
            
                
                System.out.println(stamp);
            
          }

        return result_pr;
    }

    public HashMap<String, Double> PagerankPriors(int top_results, Function f) throws FileNotFoundException, IOException {
        
           File file= new File(percorso);
           BufferedReader br= new BufferedReader(new FileReader(file));
           
          String st;
          String[] all;
          
        HashMap<String, Double> result_prp = new HashMap<String, Double>();


        PageRankWithPriors ranker = new PageRankWithPriors(graph, f, 0.7);
        
        ranker.evaluate();

        System.out.println("\n---PageRankWithPriors - Tolerance = " + ranker.getTolerance()+" - Dump factor = " + (1.00d - ranker.getAlpha()+ " - Max iterations = " + ranker.getMaxIterations()));
        //Magari dopo i risultati rifiltrare per categoria per ottenere risultati completamente coerenti
        //System.out.println("Contesto preso in considerazione: " + contesto);
        HashMap<String, Double> map = new HashMap();
        for (Object v : graph.getVertices()) {
            if (v.toString().contains("L_") && !ranker.getVertexScore(v).toString().equals("0.0")) {
                map.put(v.toString(), (Double) ranker.getVertexScore(v));
            }
        }

        Object[] obj = map.entrySet().stream()
               .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).toArray();
    


        for (int i = 0; i < top_results; i++) {
            
    
            String nome = obj[i].toString();
   
                 String score = nome.substring(nome.indexOf("=") + 1);
            nome = nome.substring(nome.indexOf("_") + 1, nome.indexOf("="));
           
            String stamp = i + 1 + " - " + nome + " - Score: " + score;
 
            System.out.println(stamp);//}
            

     
       
    } return result_prp;
    }

    public void AddToMap(Object j){
        if(j.toString().contains("P_")){
      
            float offset= (float)(P_map+1)/(nodi_P+1);
            layoutvertici.put(j.toString(),new Point2D.Float(offset*width, height/11));
            P_map++;
        }
        else if (j.toString().contains("C_")){
            float offset= (float)(C_map+1)/(nodi_C+1);
                
           layoutvertici.put(j.toString(),new Point2D.Float(offset*width, 4*(height/11)));
            C_map++;
        }
        else if (j.toString().contains("L_")){
            float offset= (float)(L_map+1)/(nodi_L+1);
            layoutvertici.put(j.toString(),new Point2D.Float(offset*width, 7*(height/11)));
            L_map++;
        }
    
        else{
            layoutvertici.put(j.toString(),new Point2D.Float(0, 0));
            System.out.println("GRAVE ERRORE");}
    }


    public void Mostra() {
        graph.getVertices().stream().forEach((Object j) -> AddToMap(j));
        Function<String, Point2D> vertexLocations = Functions.forMap(layoutvertici);

        StaticLayout layout = new StaticLayout(graph, vertexLocations);
        VisualizationViewer<String, String> vs = new VisualizationViewer<String, String>(layout, new Dimension(width, height));
        vs.getRenderer().setVertexRenderer(new CustomRenderer());
        JFrame frame = new JFrame();

        vs.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()== KeyEvent.VK_P) {
                    Container content = frame.getContentPane();
                    BufferedImage img = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = img.createGraphics();
                    content.printAll(g2d);
                    g2d.dispose();

                    try {
                        ImageIO.write(img, "png", new File("GraphImage.png"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        frame.getContentPane().add(vs);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle("Recommendation Graph");
        frame.setIconImage(new ImageIcon("data/icon.png").getImage());
        frame.setLocationRelativeTo(null);

    }

    public void Esporta(String type, String filename) throws IOException {

        //fix graphml export
        if (type.equals("GraphML")) ExportGraph.exportAsGraphML(graph, filename);
        else if (type.equals("Net")) ExportGraph.exportAsNet(graph, filename);
        else System.out.println("Export Type Error");
    }
    
    
    
    
 
   
 
   
    
   
   
   
    
      
   }
    
    


