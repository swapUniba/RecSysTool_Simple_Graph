/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.recsystool;

import com.google.common.base.Function;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.graph.Hypergraph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Daniele
 */
public class Main {
    
    public static boolean directed=true;
    public static String delimiter=",";
    public static int user_column=0;
    public static int item_column=1;
    public static int rating_column=2;
    public static int context_column=3;
    public static int threshold=3; 
    public static double pesoContesto=0.4;
    public static double pesoItem=0.5;
    public static double pesoAltri=0.3;
    public static String target="1065";
    static int num_cont=3;
    
    public static void main(String[] args) throws IOException {
     
    /**
     * Inizializzazione del grafo con struttura semplice. 
    
     SimpleGraph graph=new SimpleGraph(user_column,item_column,context_column,rating_column,num_cont,directed,delimiter, target, threshold);
   
     /**
     * Visualizzazione del grafo ed esecuzione del PageRank (PageRank con priorità con le 3 funzioni di pesatura
     */
     /*
      graph.Mostra();
      
      graph.Pagerank(10);      

      graph.PagerankPriors(10, CustomFunction.pesaContesti(graph.contestiUtente, pesoContesto, pesoAltri));

      graph.PagerankPriors(10, CustomFunction.pesaItem(graph.itemUtente, pesoItem, pesoAltri));

      graph.PagerankPriors(10, CustomFunction.pesaItemContesti(graph.itemUtente,graph.contestiUtente, pesoItem, pesoContesto, pesoAltri));
  
    */
    
     /**
      * DECOMMENTARE IL METODO run QUI SOTTO PER USARE INTERFACCIA GRAFICA
      */
             
        run( new GraphicFrame(), 1200, 700);
 

   }
    
    public static void run(JFrame frame, int width, int height) {
    frame.setTitle("RecSys");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(width, height);
    frame.setVisible(true);
  }

}
