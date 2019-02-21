<%-- 
    Document   : displayroute
    Created on : 12 Nov, 2018, 9:22:01 AM
    Author     : Nishant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head><script>
             function calcRoute(lat1, long1, lat2, long2) {
                var start = new google.maps.LatLng(lat1, long1);
                var end = new google.maps.LatLng(lat2, long2);
                if(lat1 !== lat2 || long1 !== long2){
                var request = {
                  origin: start,
                  destination: end,
                  travelMode: google.maps.TravelMode.DRIVING
                };
                directionsService.route(request, function (response, status) {
                  if (status === google.maps.DirectionsStatus.OK) {
                    directionsDisplay[index] = new google.maps.DirectionsRenderer();
                    directionsDisplay[index].setDirections(response);
                    directionsDisplay[index].setMap(map);
                    directionsDisplay[index].setOptions({ suppressMarkers: true });
                    index++;
                  } else {
                    alert("Directions Request from " + start.toUrlValue(6) + " to " + end.toUrlValue(6) + " failed: " + status);
                  }
                });
              }
          }
    </script>
        <style>
            
        </style>
    </head>
<body onload="initialize();">
<h1>Ganesha Route Map</h1>

<!--<button id ="checkLG" type="button" onclick="selectGaneshaOrLake();">Select Ganesha</button>-->

<div id="allhere">
<h3>Route map</h3>
<div id="googleMap" style="width:100%;height:500px;"></div>
</br>
</br>
<!--<button id ="allutility" type="button" onclick="allUtility();">Select Sure?</button>-->

    
    
<a href="index.jsp"><button id ="allutility"  class="w3-button w3-green w3-hover-purple" type="button">Wanna Start Again?</button></a>

</div>

<script>
    var index = 0;
    var map;
    
    function initialize(){
        directionsService = new google.maps.DirectionsService();
        directionsDisplay = new google.maps.DirectionsRenderer();
        map.setZoom(14);
        <% double resultLatLon[][] = (double[][]) request.getAttribute("resultLanLon");
    
        for(int i = 1;i<(int)resultLatLon[0][0];i++){
            %>
                    
               calcRoute(<%= resultLatLon[i][0]%>,<%= resultLatLon[i][1]%>,<%= resultLatLon[i][2]%>,<%=resultLatLon[i][3] %>);
               var lake = <%=resultLatLon[i][4]%>;

                    var myLatLng = {lat: <%= resultLatLon[i][0]%>, lng: <%= resultLatLon[i][1]%>};
                    if(lake === 1)
                        addMarker(myLatLng, "green","Lake: "+lake);
                    else if(lake === 2)
                        addMarker(myLatLng, "blue","Lake: "+lake);
                    else
                        addMarker(myLatLng, "blue","Lake: "+lake);

//                    var marker = new google.maps.Marker({
//                        position: myLatLng,
//                        map: map,
//                        title: lake
//                });
//                addMarker({lat: -34.297, lng: 150.544}, "yellow");
                
                var myLatLng2 = {lat: <%= resultLatLon[i][2]%>, lng: <%= resultLatLon[i][3]%>};
                if(lake === 1)
                    addMarker(myLatLng2, "green","Lake: "+lake);
                else if(lake === 2)
                    addMarker(myLatLng2, "blue","Lake: "+lake);
                else
                    addMarker(myLatLng2, "blue","Lake: "+lake);

                myLatLng = {lat: <%= resultLatLon[i][2]%>, lng: <%= resultLatLon[i][3]%>};
//                    marker = new google.maps.Marker({
//                        position: myLatLng,
//                        map: map,
//                        title: lake
//                });
            <%
        }
    %>
    }
    
    function addMarker(latLng, color,lake) {
        let url = "http://maps.google.com/mapfiles/ms/icons/";
        url += color + "-dot.png";

        let marker = new google.maps.Marker({
          map: map,
          position: latLng,
          title: lake,
          icon: {
            url: url
          }
        });

        //store the marker object drawn in global array
 //       markersArray.push(marker);
}
    
    function myMap() {
//        intiMap();
    var mapProp= {
        center:new google.maps.LatLng(18.958406,72.813222),
        zoom:16
    };
//    var mapProp2= {
//        center:new google.maps.LatLng(18.955406,72.813222),
//        zoom:16
//    };
    map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
//    mapl=new google.maps.Map(document.getElementById("googleMap2"),mapProp2);

    //marker.setMap(map);

//    google.maps.event.addListener(mapg,'click',function(event) {
//    //  map.setZoom(9);
//    //  map.setCenter(marker.getPosition());
//      GaneshaMarkers.push(new google.maps.Marker({ position: event.latLng, map: mapg }));
//      });
      
//    google.maps.event.addListener(mapl,'click',function(event) {
//    //  map.setZoom(9);
//    //  map.setCenter(marker.getPosition());
//      LakeMarkers.push(new google.maps.Marker({ position: event.latLng, map: mapl }));
//      });
    }
    

    
   
</script>
        
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCiJ0AP772Zc0uy9PAZq0FMOs8f-BM1peQ&callback=myMap"></script>
<!-- JQuery -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <!-- Bootstrap tooltips -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.4/umd/popper.min.js"></script>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <!-- MDB core JavaScript -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.4.1/js/mdb.min.js"></script>
</body>
</html>
