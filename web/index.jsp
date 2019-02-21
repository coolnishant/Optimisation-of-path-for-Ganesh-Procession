<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <style>
            
        </style>
    </head>
<body>

<h1>Ganesha Immersion Map</h1>

<!--<button id ="checkLG" type="button" onclick="selectGaneshaOrLake();">Select Ganesha</button>-->

<div id="allhere">
<h3>Select Ganesha</h3>
<div id="googleMap" style="width:100%;height:500px;"></div>
</br>
<h3>Select Lake</h3>
<div id="googleMap2" style="width:100%;height:500px;"></div>

</br>
<!--<button id ="allutility" type="button" onclick="allUtility();">Select Sure?</button>-->
<h1></h1>
<form id="loginForm" name="loginForm" method="post" action="UtilityServlet">
    <input type="hidden" id="alldata" name="alldata"/>
    <button class="w3-button w3-green w3-hover-red" id ="allutility" type="button" onclick="allUtility();">Select Sure?</button>
</form>
</br>
<button id ="allutility" type="button" class="w3-button w3-red w3-hover-green"  onclick="allReset();">Wanna Reset?</button>

</div>

<script>
    var GaneshaMarkers = [];
    var LakeMarkers = [];
    var GaneshaDistanceLake = [];
    var GaneshaGaneshaDist = [];
    var GaneshaLakeCluster = [];
    var GaneshaLocation =[];
    var LakeLocation = [];
    var directionsDisplay=[];
    function intiMap(){
        GaneshaMarkers = [];
        LakeMarkers = [];
        GaneshaDistanceLake = [];
        GaneshaGaneshaDist = [];
        GaneshaLakeCluster = [];
        GaneshaLocation =[];
        LakeLocation = [];
    }
    
    function allReset(){
       location.reload();
    }
    
    function allUtility(){
//        var res = document.getElementById("googleMap");
//    //    res.innerHTML = 'Hey';
//            //        res.innerHTML = 'Select Lakes';
//       res.disabled=true;
        console.log(GaneshaMarkers[0]);
        document.getElementById('googleMap2').style.visiblity = 'none';
        //all lake ganesha distnace
        console.log('lake: '+LakeMarkers.length+' gana: '+GaneshaMarkers.length);
        var totallake = LakeMarkers.length;
       
        for (var i = 0; i < totallake; i++) {
          var lat1 = LakeMarkers[i].getPosition().lat();
          var lng1 = LakeMarkers[i].getPosition().lng();
          
            LakeLocation.push({ lake:""+i,lat: ""+lat1,lng: ""+lng1});
          setDistanceLakeOneToGanesha(i);
          
//          assignClusterToEachGanesha(i);
      }
      
       var totalganesha = GaneshaMarkers.length; 
      for (var i = 0; i < totalganesha; i++) {
//          setDistanceLakeOneToGanesha(i);
//          assignClusterToEachGanesha(i);
          
          var lat1 = GaneshaMarkers[i].getPosition().lat();
          var lng1 = GaneshaMarkers[i].getPosition().lng();
          GaneshaLocation.push({ ganesha:""+i,lat: ""+lat1,lng: ""+lng1});
          setDistanceGaneshaOneToGanesha(i);
      }
//      { ganesha:""+id,lake: ""+minDistLakeId}
      var noGaneshanoLake = {noofganesha:""+GaneshaMarkers.length,nooflake:""+LakeMarkers.length};
//      $.get("UtilityServlet",  GaneshaLakeCluster);  
        console.log(GaneshaDistanceLake);
        var alldata = document.getElementById("alldata");
//        alldata.value = JSON.stringify(GaneshaLakeCluster)+'#'+ JSON.stringify(GaneshaDistanceLake)+'#'+ JSON.stringify(GaneshaGaneshaDist);
        alldata.value = JSON.stringify(noGaneshanoLake)+'#'+JSON.stringify(GaneshaDistanceLake)+'#'+ JSON.stringify(GaneshaGaneshaDist)+'#'+JSON.stringify(GaneshaLocation)+'#'+JSON.stringify(LakeLocation);
        console.log(alldata.value);
        var myform = document.getElementById("loginForm");
        myform.submit();
    }
    
    
    function selectGaneshaOrLake(){
        var res = document.getElementById("checkLG");
    //    res.innerHTML = 'Hey';
        if(res.innerHTML === 'Select Lake'){
            res.innerHTML='Select Ganesha';
    //        res.innerHTML=marker.getPosition();
        }
        else if(res.innerHTML === 'Select Ganesha'){
            res.innerHTML='Select Lake';
        }
    }
    //var marker = new google.maps.Marker({position: myCenter});

    function myMap() {
        intiMap();
    var mapProp= {
        center:new google.maps.LatLng(18.958406,72.813222),
        zoom:16
    };
    var mapProp2= {
        center:new google.maps.LatLng(18.955406,72.813222),
        zoom:16
    };
    mapg=new google.maps.Map(document.getElementById("googleMap"),mapProp);
    mapl=new google.maps.Map(document.getElementById("googleMap2"),mapProp2);

    //marker.setMap(map);

    google.maps.event.addListener(mapg,'click',function(event) {
    //  map.setZoom(9);
    //  map.setCenter(marker.getPosition());
      GaneshaMarkers.push(new google.maps.Marker({ position: event.latLng, map: mapg }));
      });
      
    google.maps.event.addListener(mapl,'click',function(event) {
    //  map.setZoom(9);
    //  map.setCenter(marker.getPosition());
      LakeMarkers.push(new google.maps.Marker({ position: event.latLng, map: mapl }));
      });
    }

 function setDistanceLakeOneToGanesha(id) {
     console.log(id);
     
      var OneGaneshaDistanceLake = [];
      currentLake = LakeMarkers[id];
      var lat = currentLake.getPosition().lat();
      var lng = currentLake.getPosition().lng();
      var totalganesha = GaneshaMarkers.length;
      for (var i = 0; i < totalganesha; i++) {
//        if (activeMark.includes(i)) {
          console.log('Ganesha id: ' + i);
          var lat1 = GaneshaMarkers[i].getPosition().lat();
          var lng1 = GaneshaMarkers[i].getPosition().lng();
          var q = i;
          $.ajax({
            url: 'https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=' + lat + ',' + lng + '&destinations=' + lat1 + ',' + lng1 + '&key=AIzaSyCq_NfdZscVJ8ZPtanPN0LOzJhlNDw6xkg',
            ajaxI: i,
            async: false,
            success: function (data) {
              var w1 = data.rows[0].elements[0].distance.value;
//              w1 = w1 / 1000;
//              var w2 = data.rows[0].elements[0].duration.value;
//              w2 = w2 / 60;
              var d1 = {
                "S": ""+this.ajaxI,
                "D": ""+id,
                "W": ""+w1
              };
              var d2 = {
                "S": id,
                "D": this.ajaxI,
                "W": w1
              };
              
//              var d3 = {
//                "S": this.ajaxI,
//                "D": id,
//                "W": w2
//              };
//              var d4 = {
//                "S": id,
//                "D": this.ajaxI,
//                "W": w2
//              };

              GaneshaDistanceLake.push(d1);
              
//              dataToBackendDist.push(d2);
//              dataToBackendTime.push(d3);
//              dataToBackendTime.push(d4);

              return data;
            }
          });
//        }
        
//      console.log(OneGaneshaDistanceLake);
      }
//      GaneshaDistanceLake.push(OneGaneshaDistanceLake);
      console.log(GaneshaDistanceLake);
//      for (var k = 0; k < directionsDisplay.length; k++)
//            if (directionsDisplay[k] != null) {
//              directionsDisplay[k].setMap(null);
//              directionsDisplay[k] = null;
//            }
//       directionsDisplay = [];
//       $("#seqPlaces").html("");
    }
   
 function assignClusterToEachGanesha(id) {
     console.log(id);
     var minDistLakeId = -1;
     var minDistG2L = Number.MAX_SAFE_INTEGER;
      var OneGaneshaDistanceLake = [];
      currentGanesha = GaneshaMarkers[id];
      var lat = currentGanesha.getPosition().lat();
      var lng = currentGanesha.getPosition().lng();
      var totallake = LakeMarkers.length;
      for (var i = 0; i < totallake; i++) {
//        if (activeMark.includes(i)) {
          console.log('Ganesha id: ' + id);
          var lat1 = LakeMarkers[i].getPosition().lat();
          var lng1 = LakeMarkers[i].getPosition().lng();
          var q = i;
          $.ajax({
            url: 'https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=' + lat + ',' + lng + '&destinations=' + lat1 + ',' + lng1 + '&key=AIzaSyCq_NfdZscVJ8ZPtanPN0LOzJhlNDw6xkg',
            ajaxI: i,
            async: false,
            success: function (data) {
              var w1 = data.rows[0].elements[0].distance.value;
//              w1 = w1 / 1000;
//              var w2 = data.rows[0].elements[0].duration.value;
//              w2 = w2 / 60;
              var d1 = {
                "S": this.ajaxI,
                "D": id,
                "W": w1
              };
              var d2 = {
                "S": id,
                "D": this.ajaxI,
                "W": w1
              };
              
//              var d3 = {
//                "S": this.ajaxI,
//                "D": id,
//                "W": w2
//              };
//              var d4 = {
//                "S": id,
//                "D": this.ajaxI,
//                "W": w2
//              };
//                console.log('w1: '+w1+' minDisTill: '+minDistG2L);
                if(w1<minDistG2L){
                    minDistLakeId = i;
                    minDistG2L = w1;
                }
                
//              OneGaneshaDistanceLake.push(d1);
              
//              dataToBackendDist.push(d2);
//              dataToBackendTime.push(d3);
//              dataToBackendTime.push(d4);

              return data;
            }
          });
//        }
        
//      console.log(OneGaneshaDistanceLake);
      }
      
      GaneshaLakeCluster.push({ ganesha:""+id,lake: ""+minDistLakeId});
//      GaneshaLakeCluster.push({ ganesha:"3",lake: "12"});
      console.log(GaneshaLakeCluster);
//      for (var k = 0; k < directionsDisplay.length; k++)
//            if (directionsDisplay[k] != null) {
//              directionsDisplay[k].setMap(null);
//              directionsDisplay[k] = null;
//            }
//       directionsDisplay = [];
//       $("#seqPlaces").html("");
    }
    
    
 function setDistanceGaneshaOneToGanesha(id) {
     console.log(id);
     
      var OneGaneshaDistanceGanesha = [];
      currentLake = GaneshaMarkers[id];
      var lat = currentLake.getPosition().lat();
      var lng = currentLake.getPosition().lng();
      var totalganesha = GaneshaMarkers.length;
      for (var i = 0; i < totalganesha; i++) {
//        if (activeMark.includes(i)) {
          console.log('Ganesha id: ' + i);
          var lat1 = GaneshaMarkers[i].getPosition().lat();
          var lng1 = GaneshaMarkers[i].getPosition().lng();
          var q = i;
          $.ajax({
            url: 'https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=' + lat + ',' + lng + '&destinations=' + lat1 + ',' + lng1 + '&key=AIzaSyCq_NfdZscVJ8ZPtanPN0LOzJhlNDw6xkg',
            ajaxI: i,
            async: false,
            success: function (data) {
              var w1 = data.rows[0].elements[0].distance.value;
//              w1 = w1 / 1000;
//              var w2 = data.rows[0].elements[0].duration.value;
//              w2 = w2 / 60;
              var d1 = {
                S: ""+this.ajaxI,
                D: ""+id,
                W: ""+w1
              };
              var d2 = {
                "S": ""+id,
                "D": ""+this.ajaxI,
                "W": ""+w1
              };
              
//              var d3 = {
//                "S": this.ajaxI,
//                "D": id,
//                "W": w2
//              };
//              var d4 = {
//                "S": id,
//                "D": this.ajaxI,
//                "W": w2
//              };
//                console.log("Here")
              GaneshaGaneshaDist.push({
                S: ""+this.ajaxI,
                D: ""+id,
                W: ""+w1
              });
              
//              dataToBackendDist.push(d2);
//              dataToBackendTime.push(d3);
//              dataToBackendTime.push(d4);

              return data;
            }
          });
//        }
        
//      console.log(OneGaneshaDistanceGanesha);
      }
//      GaneshaGaneshaDist.push({OneGaneshaDistanceGanesha});
      console.log(GaneshaGaneshaDist);
//      for (var k = 0; k < directionsDisplay.length; k++)
//            if (directionsDisplay[k] != null) {
//              directionsDisplay[k].setMap(null);
//              directionsDisplay[k] = null;
//            }
//       directionsDisplay = [];
//       $("#seqPlaces").html("");
    }
    function calcRoute(lat1, long1, lat2, long2) {
      var start = new google.maps.LatLng(lat1, long1);
      var end = new google.maps.LatLng(lat2, long2);
      var request = {
        origin: start,
        destination: end,
        travelMode: google.maps.TravelMode.DRIVING
      };
      directionsService.route(request, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
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
