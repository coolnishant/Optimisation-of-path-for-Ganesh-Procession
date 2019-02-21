/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nishant
 */
public class UtilityServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UtilityServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UtilityServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw= response.getWriter();
//        pw.println("<h1>" + mess+"</h1>");
        String text = "some text";
        text = request.getParameter("alldata");
//        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
//        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
//        response.getWriter().write(text); 
        pw.println(text);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         PrintWriter pw= response.getWriter();
        String text = request.getParameter("alldata");
        response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?        
        String [] JSONdataALL = text.split("#");

        int dataLakeGaneshaNo[] = ParsingJsonAll.readJsonNoGaneshaNoLake(JSONdataALL[0], pw);
 

        Ganesha[] gan = ParsingJsonAll.readJsonGaneshaLanLon(JSONdataALL[3],pw);
        
        Lake[] lak = ParsingJsonAll.readJsonLankLanLon(JSONdataALL[4],pw);
        
        int noofganesha = dataLakeGaneshaNo[0];
        int nooflakes = dataLakeGaneshaNo[1];
        
        //With or without algorithm?
        //a. With using optimisation algorithm
        //b. Without using optimisation algorithm 
        char ch = 'b';
        
        //What Logic for Balancing you want to apply?
        //0. No Balancing
        //1. Min Distance Difference
        //2. Min Distance form minlakeclustercount
        int logic = 0;
        
        
        pw.write("Count of: Ganeshas: "+noofganesha+"\tLakes: "+nooflakes+"\n");
        
        GaneshaLakeId[] gla = new GaneshaLakeId[noofganesha];
        
        GaneshaLakeDist[] gla1=ParsingJsonAll.readJsonGaneshaLakeDist(JSONdataALL[1],pw);
        pw.println("Lake Ganesha Dist");
        for(GaneshaLakeDist gl:gla1){
            response.getWriter().write(gl.lake+"\t"+gl.ganesha+'\t'+gl.distance+"\n");
        }
        for (int i = 0; i < noofganesha; i++){
            int min = Integer.MAX_VALUE;
            int j = i;
            int lakeId = 0;
            while(j<gla1.length){
                if(min > gla1[j].distance){
                    min = gla1[j].distance;
                    lakeId = gla1[j].lake;
                }
                j = j + noofganesha;
            }
            GaneshaLakeId glag = new GaneshaLakeId();
            glag.lakeId = lakeId;
            glag.ganesha = i;
            gla[i] = glag;
        }
        
         pw.println("Ganesha Before Lake ID");
        for(GaneshaLakeId gl1:gla){
            response.getWriter().write(gl1.ganesha+"\t"+gl1.lakeId+"\n");
        }
        
        //Balancing Logic Call
        switch(logic){
            case 1:
            //Balancing Logic1
            balancing1(gla,gla1,nooflakes,noofganesha,pw);
            break;
            case 2:
            //Balancing Logic2
            balancing2(gla,gla1,nooflakes,noofganesha,pw);
            break;
        }
        
        GaneshaGaneshaDist[] gla2=ParsingJsonAll.readJsonGaneshaGaneshaDist(JSONdataALL[2],pw);
        pw.println("Ganesha Ganesha Dist");
        for(GaneshaGaneshaDist gl12:gla2)
            response.getWriter().write(gl12.ganesha1+"\t"+gl12.ganesha2+"\t"+gl12.distance+"\n");
        pw.println("\nThe Route is given as: \n");
        double[][] resultLatLon=null;
        switch(ch){
            case 'a':
              resultLatLon = makeMSTDouble(gla,gla1,gla2,noofganesha,nooflakes,gan, lak,pw);
            break;
            case 'b':
              resultLatLon = makeWithoutAny(gla,gla1,gla2,noofganesha,nooflakes,gan, lak,pw);
            break;
        }
        for(int i=0;i<(int)resultLatLon[0][0];i++){
            pw.println(resultLatLon[i][0]+", "+resultLatLon[i][1]+", "+resultLatLon[i][2]+", "+resultLatLon[i][3]+", "+resultLatLon[i][4]);
        }
        request.setAttribute("resultLanLon", resultLatLon);
        request.getRequestDispatcher("displayroute.jsp").forward(request, response);
        
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
    
    private  String mess;
    
    public void init() throws ServletException{
        mess="Ganesha";
    }

private void makeMST(GaneshaLakeId[] gla, GaneshaLakeDist[] gla1, GaneshaGaneshaDist[] gla2, int noofganesha, int nooflakes, Ganesha[] ganesha, Lake[] lake, PrintWriter pw) {
    char at = 'k';
    int mintill = 0;
    int minisat = 0;
    int minis=0;
    boolean visited[] = new boolean[noofganesha];
    
//    int lid = 1;
//    
//    int minvaldist=Integer.MAX_VALUE;
//    int misvalganeshaat = -1;
//    for(int i=0;i<noofganesha;i++){
////        pw.println(gla[i].lakeId +"--"+ lid );
//        if(gla[i].lakeId == lid && minvaldist>gla1[lid*noofganesha+i].distance){
//            minvaldist = gla1[lid*noofganesha+i].distance;
//            misvalganeshaat = i;
//        }
//    }
    
//    for(int lid =0;lid<nooflakes;lid++){
//        int countCO1Lake = countClusterOfOneLake(gla,lid);
//        for(int i=0;i<noofganesha;i++){
//            at = 'k';
//            mintill = Integer.MAX_VALUE;
//            minisat = 0;
//            int start = lid*noofganesha;
//            for(int j=start;j<start+noofganesha;j++){
//                if(mintill > gla1[j].distance && !visited[gla[j].ganesha] && gla[gla[j].ganesha].lakeId == lid){
//                    minisat=lid;
//                    mintill=gla1[j].distance;
//                    at='k';
//                    minis=j;
//                }
//            }
//        }

      for(int i=0;i<nooflakes;i++)
      {
          
        int minvaldist=Integer.MAX_VALUE;
        int misvalganeshaat = -1;
          int noofginclust = 0;
          for(int ij=0;ij<noofganesha;ij++){
//        pw.println(gla[i].lakeId +"--"+ lid );
        if(gla[ij].lakeId == i && minvaldist>gla1[i*noofganesha+ij].distance){
            minvaldist = gla1[i*noofganesha+ij].distance;
            misvalganeshaat = ij;
            noofginclust++;
        }
    }
        newClass nc[]=new newClass[noofganesha+1];
        int p=0;
        nc[0]=new newClass();
        nc[0].isLake=true;
        nc[0].lakeId=i;
        Ganesha g[]=new Ganesha[noofganesha];
        int k1=0;
        for(int j=0;j<noofganesha;j++)
        {
            if(gla[j].lakeId==i)
            {
                g[k1]=new Ganesha();
                g[k1].ganeshaId=j;
                k1++;
            }
        }
//        pw.println(g.length+"+++");
        for(int k=0;k<k1;k++)
        {
            int min=Integer.MAX_VALUE;
            int minIs=0;
//            pw.println(gla2.length);
            for(int l=0;l<gla2.length;l++)
            {
                if( g[k]!=null &&gla2[l].ganesha1==g[k].ganeshaId )
                {
//                    pw.println(l+"--");
                    if(min>gla2[l].distance && visited[gla2[l].ganesha2]==false && gla2[l].distance !=0)
                    {
                        minIs=l;
                        min=gla2[l].distance;
                        visited[gla2[l].ganesha2]=true;
                    }
                }
            }
            newClass nc1=new newClass();
            nc1.lakeId=i;
            nc1.ganeshaId=gla2[minIs].ganesha2;
            nc1.src=g[k].ganeshaId;
            nc1.isLake=false;
            p++;
            nc[p]=nc1;
        }
        if(misvalganeshaat != -1){
          pw.println("Lake: "+i+" (lan, log): ("+lake[i].lan+", "+lake[i].lng+"  to   ganesha: "+misvalganeshaat+" (lan, log): ("+ganesha[misvalganeshaat].lan+", "+ganesha[misvalganeshaat].lng+")");
          for(int k=1;k<p;k++)
            {
    //            pw.println("ganesha\t"+nc[k].ganeshaId+"\t from\t ganesha: "+nc[k].src);
                pw.println("ganesha: "+nc[k].src+"(lan, lng): ("+ganesha[nc[k].src].lan+", "+ganesha[nc[k].src].lng+") to ganesha: "+nc[k].ganeshaId+"(lan, lng): ("+ganesha[nc[k].ganeshaId].lan+", "+ganesha[nc[k].ganeshaId].lng+")");
            }
        }
        else{
            pw.println("Lake "+i+" (lan, log): ("+lake[i].lan+", "+lake[i].lng+") is Too Far.");
        }
        pw.println();
      }
     
        
//        for(int j=0;j<noofganesha;j++){
//            int k1 = j*noofganesha;
//            for(int k=j*noofganesha;k<(j+1)*noofganesha;k++){
//                if((mintill>gla2[k].distance)&& !visited[j] && gla[j].lakeId==lid){
//                    minisat = j;
//                    mintill = gla2[j].distance;
//                    minis = k1-j;
//                    at = 'g';
//                }
//            }
//        }
//        for(int i=0;i<noofganesha;i++){
//            int j = i;
//            pw.println(gla[i].lakeId+" - "+lid);
//            if(gla[i].lakeId == lid)
//            while(j<gla2.length){
//                pw.println(mintill+" - "+gla2[j].distance+" - "+visited[gla2[j].ganesha1]+" + "+gla[gla2[j].ganesha1].lakeId);
//                if((mintill>gla2[j].distance)&& gla2[j].distance != 0 &&
//                        visited[gla2[j].ganesha1] && lid==gla[gla2[j].ganesha1].lakeId && lid==gla[gla2[j].ganesha2].lakeId){
//                    minisat = gla2[j].ganesha2;;
//                    mintill = gla2[j].distance;
//                    minis = gla2[j].ganesha1;
//                    at = 'g';
//                }
//                j = j + noofganesha;
//            }
//        pw.println("+"+minis);
//        visited[minis]=true;
//        pw.println(minisat +"-"+at+"\t---------------->"+minis);
//    
//        }
//        pw.println("#");
//        
        
        }
    


    private int countClusterOfOneLake(GaneshaLakeId[] gla, int lid) {
        int c=0;
        for(int i=0;i<gla.length;i++){
            if(gla[i].lakeId == lid){
                c++;
            }
        }
        return c;
    }

    
private double[][] makeMSTDouble(GaneshaLakeId[] gla, GaneshaLakeDist[] gla1, GaneshaGaneshaDist[] gla2, int noofganesha, int nooflakes, Ganesha[] ganesha, Lake[] lake, PrintWriter pw) {
//    char at = 'k';
//    int mintill = 0;
//    int minisat = 0;
//    int minis=0;
    double[][] resultPlot = new double[noofganesha+nooflakes+1][5];
    int re = 1;
    
    boolean visited[] = new boolean[noofganesha];
    
//    int lid = 1;
//    
//    int minvaldist=Integer.MAX_VALUE;
//    int misvalganeshaat = -1;
//    for(int i=0;i<noofganesha;i++){
////        pw.println(gla[i].lakeId +"--"+ lid );
//        if(gla[i].lakeId == lid && minvaldist>gla1[lid*noofganesha+i].distance){
//            minvaldist = gla1[lid*noofganesha+i].distance;
//            misvalganeshaat = i;
//        }
//    }
    
//    for(int lid =0;lid<nooflakes;lid++){
//        int countCO1Lake = countClusterOfOneLake(gla,lid);
//        for(int i=0;i<noofganesha;i++){
//            at = 'k';
//            mintill = Integer.MAX_VALUE;
//            minisat = 0;
//            int start = lid*noofganesha;
//            for(int j=start;j<start+noofganesha;j++){
//                if(mintill > gla1[j].distance && !visited[gla[j].ganesha] && gla[gla[j].ganesha].lakeId == lid){
//                    minisat=lid;
//                    mintill=gla1[j].distance;
//                    at='k';
//                    minis=j;
//                }
//            }
//        }

      for(int i=0;i<nooflakes;i++)
      {
          
        int minvaldist=Integer.MAX_VALUE;
        int misvalganeshaat = -1;
          int noofginclust = 0;
          for(int ij=0;ij<noofganesha;ij++){
//        pw.println(gla[i].lakeId +"--"+ lid );
        if(gla[ij].lakeId == i && minvaldist>gla1[i*noofganesha+ij].distance){
            minvaldist = gla1[i*noofganesha+ij].distance;
            misvalganeshaat = ij;
            noofginclust++;
        }
    }
        newClass nc[]=new newClass[noofganesha+1];
        int p=0;
        nc[0]=new newClass();
        nc[0].isLake=true;
        nc[0].lakeId=i;
        Ganesha g[]=new Ganesha[noofganesha];
        int k1=0;
        for(int j=0;j<noofganesha;j++)
        {
            if(gla[j].lakeId == i)
            {
                g[k1]=new Ganesha();
                g[k1].ganeshaId=j;
                
//                pw.println(j+" j+++");
                k1++;
                
            }
        }
        pw.println(k1+" k1+++");
        for(int k=0;k<k1;k++)
        {
            int min=Integer.MAX_VALUE;
            int minIs=0;
//            pw.println(gla2.length);
            for(int l=0;l<gla2.length;l++)
            {
                if( g[k]!=null && gla2[l].ganesha1==g[k].ganeshaId && gla[gla2[l].ganesha2].lakeId == i)
                {
//                pw.println( gla[g[k].ganeshaId].lakeId+" lakeid");
                    if(min>gla2[l].distance && visited[gla2[l].ganesha2]==false && gla2[l].distance !=0)
                    {
//                        pw.println(gla2[l].ganesha2+"--"+i);
                        minIs=l;
                        min=gla2[l].distance;
                        visited[gla2[l].ganesha2]=true;
                    }
                }
            }
            newClass nc1=new newClass();
            nc1.lakeId=i;
            nc1.ganeshaId=gla2[minIs].ganesha2;
            nc1.src=g[k].ganeshaId;
            nc1.isLake=false;
            p++;
            nc[p]=nc1;
        }
        if(misvalganeshaat != -1){
          resultPlot[re][0] = lake[i].lan;
          resultPlot[re][1] = lake[i].lng;
          resultPlot[re][2] = ganesha[misvalganeshaat].lan;
          resultPlot[re][3] = ganesha[misvalganeshaat].lng;
          
          resultPlot[re][4] = lake[i].lakeId;
          re++;
          pw.println("Lake: "+i+" (lan, log): ("+lake[i].lan+", "+lake[i].lng+"  to   ganesha: "+misvalganeshaat+" (lan, log): ("+ganesha[misvalganeshaat].lan+", "+ganesha[misvalganeshaat].lng+")");
          for(int k=1;k<p;k++)
            {
                
                resultPlot[re][0] = ganesha[nc[k].src].lan;
                resultPlot[re][1] = ganesha[nc[k].src].lng;
                resultPlot[re][2] = ganesha[nc[k].ganeshaId].lan;
                resultPlot[re][3] = ganesha[nc[k].ganeshaId].lng;
                //change 1
                resultPlot[re][4] = lake[i].lakeId;
                re++;
    //            pw.println("ganesha\t"+nc[k].ganeshaId+"\t from\t ganesha: "+nc[k].src);
                pw.println("ganesha: "+nc[k].src+"(lan, lng): ("+ganesha[nc[k].src].lan+", "+ganesha[nc[k].src].lng+") to ganesha: "+nc[k].ganeshaId+"(lan, lng): ("+ganesha[nc[k].ganeshaId].lan+", "+ganesha[nc[k].ganeshaId].lng+")");
            }
        }
        else{
            
            resultPlot[re][0] = lake[i].lan;
            resultPlot[re][1] = lake[i].lng;
            resultPlot[re][2] = lake[i].lan;
            resultPlot[re][3] = lake[i].lng;
            resultPlot[re][4] = lake[i].lakeId;
            re++;
            pw.println("Lake "+i+" (lan, log): ("+lake[i].lan+", "+lake[i].lng+") is Too Far.");
        }
        pw.println();
      }
     resultPlot[0][0] = re;
        
//        for(int j=0;j<noofganesha;j++){
//            int k1 = j*noofganesha;
//            for(int k=j*noofganesha;k<(j+1)*noofganesha;k++){
//                if((mintill>gla2[k].distance)&& !visited[j] && gla[j].lakeId==lid){
//                    minisat = j;
//                    mintill = gla2[j].distance;
//                    minis = k1-j;
//                    at = 'g';
//                }
//            }
//        }
//        for(int i=0;i<noofganesha;i++){
//            int j = i;
//            pw.println(gla[i].lakeId+" - "+lid);
//            if(gla[i].lakeId == lid)
//            while(j<gla2.length){
//                pw.println(mintill+" - "+gla2[j].distance+" - "+visited[gla2[j].ganesha1]+" + "+gla[gla2[j].ganesha1].lakeId);
//                if((mintill>gla2[j].distance)&& gla2[j].distance != 0 &&
//                        visited[gla2[j].ganesha1] && lid==gla[gla2[j].ganesha1].lakeId && lid==gla[gla2[j].ganesha2].lakeId){
//                    minisat = gla2[j].ganesha2;;
//                    mintill = gla2[j].distance;
//                    minis = gla2[j].ganesha1;
//                    at = 'g';
//                }
//                j = j + noofganesha;
//            }
//        pw.println("+"+minis);
//        visited[minis]=true;
//        pw.println(minisat +"-"+at+"\t---------------->"+minis);
//    
//        }
//        pw.println("#");
//        
        return resultPlot;
        }

    
private double[][] makeWithoutAny(GaneshaLakeId[] gla, GaneshaLakeDist[] gla1, GaneshaGaneshaDist[] gla2, int noofganesha, int nooflakes, Ganesha[] ganesha, Lake[] lake, PrintWriter pw) {
//    char at = 'k';
//    int mintill = 0;
//    int minisat = 0;
//    int minis=0;
    double[][] resultPlot = new double[noofganesha+nooflakes+1][5];
    int re = 1;
    
    boolean visited[] = new boolean[noofganesha];

      for(int i=0;i<nooflakes;i++)
      {
          
        int minvaldist=Integer.MAX_VALUE;
        int misvalganeshaat = -1;
          int noofginclust = 0;
          for(int ij=0;ij<noofganesha;ij++){
//        pw.println(gla[i].lakeId +"--"+ lid );
        if(gla[ij].lakeId == i && minvaldist>gla1[i*noofganesha+ij].distance){
            minvaldist = gla1[i*noofganesha+ij].distance;
            misvalganeshaat = ij;
            noofginclust++;
        }
    }
        newClass nc[]=new newClass[noofganesha+1];
        int p=0;
        nc[0]=new newClass();
        nc[0].isLake=true;
        nc[0].lakeId=i;
        Ganesha g[]=new Ganesha[noofganesha];
        int k1=0;
        for(int j=0;j<noofganesha;j++)
        {
            if(gla[j].lakeId == i) 
            {
                g[k1]=new Ganesha();
                g[k1].ganeshaId=j;
                
                resultPlot[re][0] = lake[i].lan;
                resultPlot[re][1] = lake[i].lng;
                resultPlot[re][2] = ganesha[j].lan;
                resultPlot[re][3] = ganesha[j].lng;
                resultPlot[re][4] = lake[i].lakeId;
                re++;
                pw.println("Lake: "+i+" (lan, log): ("+lake[i].lan+", "+lake[i].lng+"  to   ganesha: "+j+" (lan, log): ("+ganesha[j].lan+", "+ganesha[j].lng+")");
                k1++;
                
            }
        }
        
        if(k1 == 0)
        {    
            resultPlot[re][0] = lake[i].lan;
            resultPlot[re][1] = lake[i].lng;
            resultPlot[re][2] = lake[i].lan;
            resultPlot[re][3] = lake[i].lng;
            resultPlot[re][4] = lake[i].lakeId;
            re++;
            pw.println("Lake "+i+" (lan, log): ("+lake[i].lan+", "+lake[i].lng+") is Too Far.");
        }
        pw.println();
      }
     resultPlot[0][0] = re;
        
//        for(int j=0;j<noofganesha;j++){
//            int k1 = j*noofganesha;
//            for(int k=j*noofganesha;k<(j+1)*noofganesha;k++){
//                if((mintill>gla2[k].distance)&& !visited[j] && gla[j].lakeId==lid){
//                    minisat = j;
//                    mintill = gla2[j].distance;
//                    minis = k1-j;
//                    at = 'g';
//                }
//            }
//        }
//        for(int i=0;i<noofganesha;i++){
//            int j = i;
//            pw.println(gla[i].lakeId+" - "+lid);
//            if(gla[i].lakeId == lid)
//            while(j<gla2.length){
//                pw.println(mintill+" - "+gla2[j].distance+" - "+visited[gla2[j].ganesha1]+" + "+gla[gla2[j].ganesha1].lakeId);
//                if((mintill>gla2[j].distance)&& gla2[j].distance != 0 &&
//                        visited[gla2[j].ganesha1] && lid==gla[gla2[j].ganesha1].lakeId && lid==gla[gla2[j].ganesha2].lakeId){
//                    minisat = gla2[j].ganesha2;;
//                    mintill = gla2[j].distance;
//                    minis = gla2[j].ganesha1;
//                    at = 'g';
//                }
//                j = j + noofganesha;
//            }
//        pw.println("+"+minis);
//        visited[minis]=true;
//        pw.println(minisat +"-"+at+"\t---------------->"+minis);
//    
//        }
//        pw.println("#");
//        
        return resultPlot;
        }


void balancing1(GaneshaLakeId[] gla, GaneshaLakeDist[] gla1, int nooflakes, int noofganesha, PrintWriter pw){
            int lakeclust[] = new int[nooflakes];
        
        int minlakecount = Integer.MAX_VALUE;
        int minlakecountat = -1;
        int maxlakecount = Integer.MIN_VALUE;
        int maxlakecountat = -1;
        
        for(int i =0;i<lakeclust.length;i++){
            lakeclust[i] = countClusterOfOneLake(gla,i);
            if(minlakecount>lakeclust[i]){
                minlakecount = lakeclust[i];
                minlakecountat = i;
            }
            if(maxlakecount<lakeclust[i]){
                maxlakecount = lakeclust[i];
                maxlakecountat = i;
            }
        }
//        int a = 0;
        pw.println("max lake count "+maxlakecountat+" - "+maxlakecount);
        GaneshaLakeDist gl;
        GaneshaLakeDist gl2;
        boolean[] visitedgan = new boolean[noofganesha];
        while(maxlakecount>2*minlakecount){
            int mindist = Integer.MAX_VALUE;
            int minat = -1;
        for(int i=0;i<noofganesha;i++){
//        for(GaneshaLakeDist gl:gla1){
             
             gl = gla1[i+minlakecountat*noofganesha];
             gl2 = gla1[i+maxlakecountat*noofganesha];
             pw.println(gl.distance+" - "+gl2.distance);

            //Logic 1 for Balancing
             if(mindist > gl.distance-gl2.distance && gl2.distance<gl.distance && !visitedgan[i] && gla[gl.ganesha].lakeId == maxlakecountat){
                mindist = gl.distance-gl2.distance;
                minat = i;
            }
            //Logic 2 for Balancing
//            if(mindist > gl.distance  && !visitedgan[i] && gla[gl.ganesha].lakeId == maxlakecountat){
//                mindist = gl.distance;
//                minat = i;
//            }
             
//            if(maxlakecount>2*minlakecount){
//                break;
//            }
//            a++;
//            if(i == noofganesha)
//                break;
        }
            if(minat < noofganesha){
                gla[minat].lakeId = minlakecountat;
                visitedgan[minat] = true;
                maxlakecount--;
                minlakecount++;
            }
        }
        pw.println("Ganesha Lake ID");
        for(GaneshaLakeId gl1:gla){
            pw.write(gl1.ganesha+"\t"+gl1.lakeId+"\n");
        }
        
        pw.println(minlakecount+" -  "+maxlakecount);
        
        }

        void balancing2(GaneshaLakeId[] gla, GaneshaLakeDist[] gla1, int nooflakes, int noofganesha, PrintWriter pw){
            int lakeclust[] = new int[nooflakes];
        
        int minlakecount = Integer.MAX_VALUE;
        int minlakecountat = -1;
        int maxlakecount = Integer.MIN_VALUE;
        int maxlakecountat = -1;
        
        for(int i =0;i<lakeclust.length;i++){
            lakeclust[i] = countClusterOfOneLake(gla,i);
            if(minlakecount>lakeclust[i]){
                minlakecount = lakeclust[i];
                minlakecountat = i;
            }
            if(maxlakecount<lakeclust[i]){
                maxlakecount = lakeclust[i];
                maxlakecountat = i;
            }
        }
//        int a = 0;
        pw.println("max lake count "+maxlakecountat+" - "+maxlakecount);
        GaneshaLakeDist gl;
        GaneshaLakeDist gl2;
        boolean[] visitedgan = new boolean[noofganesha];
        while(maxlakecount>2*minlakecount){
            int mindist = Integer.MAX_VALUE;
            int minat = -1;
        for(int i=0;i<noofganesha;i++){
//        for(GaneshaLakeDist gl:gla1){
             
             gl = gla1[i+minlakecountat*noofganesha];
             gl2 = gla1[i+maxlakecountat*noofganesha];
             pw.println(gl.distance+" - "+gl2.distance);

//            Logic 1 for Balancing
//             if(mindist > gl.distance-gl2.distance && gl2.distance<gl.distance && !visitedgan[i] && gla[gl.ganesha].lakeId == maxlakecountat){
//                mindist = gl.distance-gl2.distance;
//                minat = i;
//            }
            //Logic 2 for Balancing
            if(mindist > gl.distance  && !visitedgan[i] && gla[gl.ganesha].lakeId == maxlakecountat){
                mindist = gl.distance;
                minat = i;
            }
             
//            if(maxlakecount>2*minlakecount){
//                break;
//            }
//            a++;
//            if(i == noofganesha)
//                break;
        }
            if(minat < noofganesha){
                gla[minat].lakeId = minlakecountat;
                visitedgan[minat] = true;
                maxlakecount--;
                minlakecount++;
            }
        }
        pw.println("Ganesha Lake ID");
        for(GaneshaLakeId gl1:gla){
            pw.write(gl1.ganesha+"\t"+gl1.lakeId+"\n");
        }
        
        pw.println(minlakecount+" -  "+maxlakecount);
        
        }

}
