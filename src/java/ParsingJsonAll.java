
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nishant
 */
public class ParsingJsonAll {
     public static GaneshaLakeId[] readJsonGaneshaLake(String s, PrintWriter pw) 
     {
         GaneshaLakeId gla[] = null;
         try {
             // typecasting obj to JSONObject

             JSONArray jsonarray = new JSONArray(s);
//             pw.println("HereIn");
             gla = new GaneshaLakeId[jsonarray.length()];
             for (int i = 0; i < jsonarray.length(); i++) {
                 JSONObject jsonobject = jsonarray.getJSONObject(i);
//            String name = jsonobject.getString("name");
//            String url = jsonobject.getString("url");
                GaneshaLakeId gl = new GaneshaLakeId();

                gl.ganesha = Integer.parseInt(jsonobject.getString("ganesha"));
                gl.lakeId = Integer.parseInt(jsonobject.getString("lake"));
//                pw.println(gl.ganesha+" "+gl.lakeId);
                gla[i] = gl;
             }
//         JSONObject obj = new JSONObject("{\"name\": \"John\"}");

//         System.out.println(obj.getString("name"));


         } catch (JSONException ex) {
             pw.println("Exception");
             Logger.getLogger(ParsingJsonAll.class.getName()).log(Level.SEVERE, null, ex);
         }
         return gla;
     }
     
          public static GaneshaLakeDist[] readJsonGaneshaLakeDist(String s, PrintWriter pw) 
     {
         GaneshaLakeDist gla[] = null;
         try {
             // typecasting obj to JSONObject

             JSONArray jsonarray = new JSONArray(s);
//             pw.println("HereIn");
             gla = new GaneshaLakeDist[jsonarray.length()];
             for (int i = 0; i < jsonarray.length(); i++) {
                 JSONObject jsonobject = jsonarray.getJSONObject(i);
//            String name = jsonobject.getString("name");
//            String url = jsonobject.getString("url");
                GaneshaLakeDist gl = new GaneshaLakeDist();

                gl.ganesha = Integer.parseInt(jsonobject.getString("S"));
                gl.lake = Integer.parseInt(jsonobject.getString("D"));
                gl.distance = Integer.parseInt(jsonobject.getString("W"));
                
//                pw.println(gl.ganesha+" "+gl.lake+" "+gl.distance);
                gla[i] = gl;
             }
//         JSONObject obj = new JSONObject("{\"name\": \"John\"}");

//         System.out.println(obj.getString("name"));


         } catch (JSONException ex) {
             pw.println("Exception");
             Logger.getLogger(ParsingJsonAll.class.getName()).log(Level.SEVERE, null, ex);
         }
         return gla;
     }
          
               public static GaneshaGaneshaDist[] readJsonGaneshaGaneshaDist(String s, PrintWriter pw) 
     {
         GaneshaGaneshaDist gla[] = null;
         try {
             // typecasting obj to JSONObject

             JSONArray jsonarray = new JSONArray(s);
//             pw.println("HereIn");
             gla = new GaneshaGaneshaDist[jsonarray.length()];
             for (int i = 0; i < jsonarray.length(); i++) {
                 JSONObject jsonobject = jsonarray.getJSONObject(i);
//            String name = jsonobject.getString("name");
//            String url = jsonobject.getString("url");
                GaneshaGaneshaDist gl = new GaneshaGaneshaDist();

                gl.ganesha2 = Integer.parseInt(jsonobject.getString("S"));
                gl.ganesha1 = Integer.parseInt(jsonobject.getString("D"));
                gl.distance = Integer.parseInt(jsonobject.getString("W"));
                
//                pw.println(gl.ganesha1+" "+gl.ganesha2+" "+gl.distance);
                gla[i] = gl;
             }
//         JSONObject obj = new JSONObject("{\"name\": \"John\"}");

//         System.out.println(obj.getString("name"));


         } catch (JSONException ex) {
             pw.println("Exception");
             Logger.getLogger(ParsingJsonAll.class.getName()).log(Level.SEVERE, null, ex);
         }
         return gla;
     }
               
     public static int[] readJsonNoGaneshaNoLake(String s, PrintWriter pw) 
     {
         int glano[] = new int[2];
         try {
             // typecasting obj to JSONObject

                JSONObject obj = new JSONObject(s);
//                String d1 = obj.getString("noofganesha");
//                String d2 = obj.getString("nooflake");
                
                // typecasting obj to JSONObject 
//                JSONObject jsonobject = (JSONObject) obj; 

                // getting firstName and lastName 
//                String firstName = (String) jo.get("firstName");
             
//             JSONArray jsonarray = new JSONArray(s);
//             JSONObject jsonobject = jsonarray.getJSONObject(0);
////             pw.println("HereIn");
                int a = Integer.parseInt(obj.getString("noofganesha"));
                int b = Integer.parseInt(obj.getString("nooflake"));
                glano [0]=a;
                glano [1]=b;
             
//             for (int i = 0; i < jsonarray.length(); i++) {
//                 JSONObject jsonobject = jsonarray.getJSONObject(i);
////            String name = jsonobject.getString("name");
////            String url = jsonobject.getString("url");
//                GaneshaGaneshaDist gl = new GaneshaGaneshaDist();
//
//                gl.ganesha2 = Integer.parseInt(jsonobject.getString("S"));
//                gl.ganesha1 = Integer.parseInt(jsonobject.getString("D"));
//                gl.distance = Integer.parseInt(jsonobject.getString("W"));
//                
////                pw.println(gl.ganesha1+" "+gl.ganesha2+" "+gl.distance);
//                gla[i] = gl;
//             }
////         JSONObject obj = new JSONObject("{\"name\": \"John\"}");
//
////         System.out.println(obj.getString("name"));


         } catch (JSONException ex) {
             pw.println("Exception");
             Logger.getLogger(ParsingJsonAll.class.getName()).log(Level.SEVERE, null, ex);
         }
         return glano;
     }
     
          
          public static Ganesha[] readJsonGaneshaLanLon(String s, PrintWriter pw) 
     {
         Ganesha gla[] = null;
         try {
             // typecasting obj to JSONObject

             JSONArray jsonarray = new JSONArray(s);
//             pw.println("HereIn");
             gla = new Ganesha[jsonarray.length()];
             for (int i = 0; i < jsonarray.length(); i++) {
                 JSONObject jsonobject = jsonarray.getJSONObject(i);
//            String name = jsonobject.getString("name");
//            String url = jsonobject.getString("url");
                Ganesha gl = new Ganesha();

                gl.ganeshaId = Integer.parseInt(jsonobject.getString("ganesha"));
                gl.lan = Double.parseDouble(jsonobject.getString("lat"));
                gl.lng = Double.parseDouble(jsonobject.getString("lng"));
                
//                pw.println(gl.ganesha+" "+gl.lake+" "+gl.distance);
                gla[i] = gl;
             }
//         JSONObject obj = new JSONObject("{\"name\": \"John\"}");

//         System.out.println(obj.getString("name"));


         } catch (JSONException ex) {
             pw.println("Exception Ganesha");
             Logger.getLogger(ParsingJsonAll.class.getName()).log(Level.SEVERE, null, ex);
         }
         return gla;
     }

          
          public static Lake[] readJsonLankLanLon(String s, PrintWriter pw) 
     {
         Lake gla[] = null;
         try {
             // typecasting obj to JSONObject

             JSONArray jsonarray = new JSONArray(s);

             gla = new Lake[jsonarray.length()];
             for (int i = 0; i < jsonarray.length(); i++) {
//                              pw.println("HereIn");
                 JSONObject jsonobject = jsonarray.getJSONObject(i);
//            String name = jsonobject.getString("name");
//            String url = jsonobject.getString("url");
                Lake gl = new Lake();

                gl.lakeId = Integer.parseInt(jsonobject.getString("lake"));
                gl.lan = Double.parseDouble(jsonobject.getString("lat"));
                gl.lng = Double.parseDouble(jsonobject.getString("lng"));
                
//                pw.println(gl.ganesha+" "+gl.lake+" "+gl.distance);
                gla[i] = gl;
             }
//         JSONObject obj = new JSONObject("{\"name\": \"John\"}");

//         System.out.println(obj.getString("name"));


         } catch (JSONException ex) {
             pw.println("Exception Lake");
             Logger.getLogger(ParsingJsonAll.class.getName()).log(Level.SEVERE, null, ex);
         }
         return gla;
     }

     
//     public static void main(String[] args) throws JSONException {
//         readJsonGaneshaLake("Hey");
//    }
}



