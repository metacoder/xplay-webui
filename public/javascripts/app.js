function MainCtrl($scope){

    $scope.centerMapOnAircraft = true;
    $scope.udpStatus = 'unknown';
    $scope.udpStatusIcon = 'glyphicon-question-sign';
    $scope.websocketStatus = 'connecting';
    $scope.websocketStatusIcon = 'glyphicon-question-sign';


    /* ================================================================ */
    /* map code                                                         */
    /* ================================================================ */

    var plane = {
        path : 'm -1.4064217,-215.63686 c -12.9560103,0 -19.1740303,42.80954 -21.3773503,62.88076 -0.45814,6.08867 -0.97291,11.61986 -0.97291,17.30085 l 0,60.502566 c -0.13431,3.44701 -0.73266,4.40381 -2.7242,6.13875 l -46.71457,40.69575 c -3.18764,2.20418 -7.33515,2.12237 -7.01798,-5.45346 l 0,-5.40876 c 0,-2.98059 -2.38348,-5.40877 -5.36407,-5.40877 l -6.70508,0 c -2.98058,0 -5.40876,2.42818 -5.40876,5.40877 l 0,31.7203197 c 0.0157,0.9701 -0.15769,1.05125 -0.84818,1.66519 L -142.83886,33.792036 c -4.53651,3.15795 -6.19859,0.0596 -6.43688,-5.49816 l 0,-9.87881 c 0,-2.98059 -2.38347,-5.40877 -5.36406,-5.40877 l -6.70508,0 c -2.98059,0 -5.40876,2.42818 -5.40876,5.40877 l 0,32.85504 c -0.003,0.41191 -0.17177,0.45043 -0.35881,0.66367 l -47.51545,40.99372 c -3.02542,3.87152 -4.81843,5.63897 -4.69355,12.203244 l -0.16908,18.42478 c -0.38261,2.50774 1.95765,3.50595 3.60106,2.71745 0,0 81.13485,-50.093264 112.25296,-63.827894 31.118118,-13.73463 72.593648,-23.15487 72.593648,-23.15487 3.19835,-0.77396 6.05794,-0.86459 7.28618,2.54793 l 0,133.073454 0,5.54286 c 0.0822,16.54466 6.33244,26.55533 0,32.49728 l -51.72296,50.26863 c -3.14093,3.34871 -4.95346,6.21196 -5.32091,11.62338 l 0.0775,5.72594 c 0.17422,2.67887 1.18044,4.05562 3.63446,3.73986 l 66.1568,-16.26607 c 1.7351203,-0.54805 3.1135603,-0.36124 4.3312403,1.32162 l 6.75661996,14.88397 8.30254004,-15.26901 c 1.147,-1.63534 3.3026397,-2.41373 4.9264697,-1.83124 20.40655,4.83258 40.8131,9.66517 61.21964,14.49775 2.62547,0.31568 4.75398,0.26075 5.09024,-3.26192 l 0,-8.74268 c -1.3742,-6.03068 -4.84032,-8.84032 -8.47592,-12.46919 l -51.92915,-44.22104 c -4.85769,-4.92351 -0.0429,-15.32292 1.60922,-32.00557 l 0,-5.49817 0,-131.508934 0.0446,-0.0446 0.0446,0 0,-0.0446 0.0446,0 0,-0.0446 0.0446,0 0,-0.0446 0.0446,0 c 0.0402,-0.19235 0.0865,-0.39686 0.13403,-0.58111 0.0473,-0.18425 0.1233,-0.36115 0.1788,-0.53641 0.0555,-0.17527 0.11458,-0.32632 0.1788,-0.4917 0.0642,-0.16539 0.10499,-0.33709 0.1788,-0.49171 0.0738,-0.15461 0.13918,-0.30406 0.22351,-0.447 0.0842,-0.1429 0.17275,-0.27191 0.2682,-0.40231 0.0954,-0.13045 0.20549,-0.24064 0.3129,-0.3576 0.10743,-0.11701 0.23745,-0.25499 0.35761,-0.35761 0.12015,-0.1027 0.22391,-0.18079 0.3576,-0.2682 0.13374,-0.0874 0.29902,-0.19691 0.44701,-0.2682 0.148,-0.0712 0.28391,-0.12459 0.447,-0.1788 0.16308,-0.0544 0.35743,-0.0978 0.53641,-0.13403 0.17897,-0.0363 0.34078,-0.0718 0.5364,-0.0894 0.19564,-0.0176 0.41274,-0.003 0.62581,0 0.21308,10e-4 0.43921,0.022 0.67051,0.0446 0.2313,0.0226 0.4649,0.0453 0.71521,0.0894 0,0 37.6559,8.16667 69.42033,21.59007 31.764442,13.4234 116.465792,64.122024 116.465792,64.122024 1.94021,0.39708 3.57657,0.10198 3.82331,-1.80048 l 0,-17.12931 c 0.11215,-4.595 -2.60719,-8.678734 -4.60415,-10.504634 l -46.49641,-41.13968 c -1.60175,-1.94784 -1.95895,-2.32195 -2.00365,-3.47335 l 0,-32.54006 c 0,-2.98059 -2.42818,-5.40877 -5.40877,-5.40877 l -6.70507,0 c -2.98059,0 -5.36407,2.42818 -5.36407,5.40877 l 0,9.87881 c 0.52057,5.87725 -1.04367,8.05372 -4.69355,5.27466 L 96.124198,-5.5919043 c -1.02016,-0.87224 -1.25859,-0.91239 -1.24588,-1.76292 l 0,-31.6254897 c 0,-2.98059 -2.42817,-5.40877 -5.40876,-5.40877 l -6.70508,0 c -2.98059,0 -5.36406,2.42818 -5.36406,5.40877 l 0,5.40876 c 0.75793,7.53296 -4.18952,9.04252 -7.50969,5.40876 l -46.40138,-40.74939 c -2.16309,-1.95235 -2.36689,-3.14928 -2.59039,-5.87517 l 0,-60.667896 c -0.1463,-5.99708 -0.46962,-12.72277 -1.12829,-18.20054 -2.70364,-21.2488 -10.9380297,-67.1006 -21.1772697,-61.98098 z',
        fillColor : 'red',
        fillOpacity : 1,
        scale : 0.1,
        strokeColor : 'black',
        strokeWeight : 1
    };

    var position = new google.maps.LatLng( 0, 0 )
    var myOptions = {
        zoom : 12,
        center : position,
        mapTypeId : google.maps.MapTypeId.ROADMAP
    };

    var map = new google.maps.Map( document.getElementById( "map" ), myOptions );

    var marker = new google.maps.Marker({
        position : position,
        map : map,
        title : "plane",
        icon : plane
    });


    /* ================================================================ */
    /* websocket code                                                   */
    /* ================================================================ */

    var ws = new ReconnectingWebSocket( 'ws://' + document.location.host + '/websocket' ) ;
    ws.onopen = function ( ) {
        console.log( 'ws connected' );
        $scope.$apply(function() {
            $scope.websocketStatus = 'connected';
            $scope.websocketStatusIcon = STATUS.receiving;
        });
    };
    ws.onconnecting = function ( ) {
        console.log( 'ws connecting' );
        $scope.$apply(function() {
            $scope.websocketStatus = 'connecting';
            $scope.websocketStatusIcon = STATUS.error;
        });
    };
    ws.onerror = function ( ) {
        console.log( 'ws error' );
        $scope.$apply(function() {
            $scope.websocketStatus = 'error';
            $scope.websocketStatusIcon = STATUS.error;
        });
    };
    ws.onclose = function ( ) {
        console.log( 'ws closed' );
        $scope.$apply(function() {
            $scope.websocketStatus = 'disconnected';
            $scope.websocketStatusIcon = STATUS.error;
        });
    };
    ws.onmessage = function ( msgevent ) {

        $scope.$apply(function() {

            var msg = JSON.parse(msgevent.data);
            if (msg.type == "position") {
                position = new google.maps.LatLng(msg.lat, msg.lon)
                marker.setPosition(position);

                if ($scope.centerMapOnAircraft) {
                    map.panTo(position);
                }

                $scope.latitude = msg.lat.toFixed(3);
                $scope.longitude = msg.lon.toFixed(3);
                $scope.altitude = Math.round(msg.ftmsl) + ' ft';
                $scope.overGround = Math.round(msg.ftagl) + ' ft';
            } else if (msg.type == "pitchRollHeading") {
                plane.rotation = msg.trueHeading;
                marker.setIcon(plane);
            } else if (msg.type == "speed") {
                $scope.indKias = Math.round(msg.indKias) + ' knots';
                $scope.trueKtgs = Math.round(msg.trueKtgs) + ' knots';
            } else if (msg.type == "udpConnectionStatus"){
                $scope.udpStatus = msg.status;
                $scope.udpStatusIcon = STATUS[msg.status];
            } else {
                console.log('in :', msg);
            }

        });

    };


    /* ================================================================ */
    /* status icon                                                      */
    /* ================================================================ */

    var STATUS = {
        initializing : "glyphicon-question-sign",
        waiting : "glyphicon-ok-sign icon-warning",
        receiving : "glyphicon-ok-sign icon-success",
        error : "glyphicon-remove-sign icon-danger"
    };
}