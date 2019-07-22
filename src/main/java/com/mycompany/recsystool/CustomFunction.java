/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.recsystool;

import com.google.common.base.Function;
import java.util.List;

/**
 *
 * @author Daniele
 */
public class CustomFunction {
    
    
    public static Function test(){
            Function f1 = ((Object i) -> {
                if(i.toString().toString().startsWith("L_")){
              //  System.out.println(i.toString());
                return 1.0;}
               
             //       System.out.println("No");
             
                return 0.1;
                
    });
    return f1;
        
    }
    
    
    
    public static Function pesa(List lista, float peso, float pesoAltri){
    Function f1 = ((Object i) -> {
        if (lista.contains(i)) return peso;
        else return pesoAltri;
    });
    return f1;
}
    
    public static Function pesaItemContesti(List item, List contesti, double pesoItem, double pesoContesto, double pesoAltri ){
             System.out.println("\n\n PesaItemContesti------------------------");
    Function f1 = ((Object i) -> {
        if (item.contains(i)) return pesoItem;
        if(contesti.contains(i)) return pesoContesto;
        else return pesoAltri;
    });
    return f1;
}
    
   public static Function pesaItem(List listaItem, double pesoItem, double pesoAltri){
    System.out.println("\n\n PesaItem------------------------");
    Function f1 = ((Object i) -> {
        if (i.toString().startsWith("L_") && listaItem.contains(i)){
           // System.out.println("ciao");
            return pesoItem;
        }
        else return pesoAltri;
    });
    return f1;
}
    
    public static Function pesaItem2(List listaItem, double pesoItem, double pesoAltriItem, double pesoAltri){
    Function f1 = ((Object i) -> {
        if (listaItem.contains(i)) return pesoItem;
        if(i.toString().startsWith("L_")) return pesoAltriItem;
        else return pesoAltri;
    });
    return f1;
}
    
    public static Function pesaContesti(List listaContesti, double pesoContesto, double pesoAltri){
     System.out.println("\n\n PesaContesti------------------------");
    Function f1 = ((Object i) -> {
        if (i.toString().startsWith("C_") && listaContesti.contains(i)) {
            //System.out.println(" C_"+i.toString());
            return pesoContesto;
        }
        else {
            //System.out.println("----------------------------------");
            return pesoAltri;
        }
    });
    return f1;
}
    
    public static Function pesaContesti2(List listaContesti, float pesoContesto, float pesoAltriContesti, float pesoAltri){
    Function f1 = ((Object i) -> {
        if (listaContesti.contains(i)) return pesoContesto;
        if(i.toString().startsWith("C_")) return pesoAltriContesti;
        else return pesoAltri;
    });
    return f1;
}
    
    public static Function pesaContestiItem(List listaContesti, List listaItem, double pesoNodo, double pesoAltri){

    Function f1 = ((Object i) -> {
        if (listaContesti.contains(i) || listaItem.contains(i)) return pesoNodo;
        else return pesoAltri;
    });
    return f1;
}
    
    public static Function pesaContestiItem2(List listaContesti, List listaItem, double pesoItem, double pesoContesto, double pesoAltri){
    Function f1 = ((Object i) -> {
        if (listaContesti.contains(i)) 
        {
            //System.out.println(" contesto");
            return pesoContesto;
        }
        if (listaItem.contains(i)) {
           // System.out.println(" item");
            return pesoContesto;
        }
        else return pesoAltri;
    });
    return f1;
}
    
    
}
