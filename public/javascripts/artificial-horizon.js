/**
 * Created by benjamin on 8/8/14.
 */

var artificialHorizon = (function() {
    var GROUND_COLOR = "#79582e", LINE_COLOR = "#ffffff", SKY_COLOR = "#205b77", PLANE_COLOR = "#000000";

    var canvas, context;

    var diagonal = 0, halfDiagonal = 0, radius = 0;

    var pitch = 0, roll = 0, roundedPitch = 0;

    var horizon = 0;

    function repaint() {
        context.save();

        context.translate(canvas.width/2, canvas.height/2);
        context.rotate(-radians(roll));

        context.fillStyle = GROUND_COLOR;
        context.strokeStyle = LINE_COLOR;
        context.lineWidth = 2;

        // draw ground
        context.fillRect(-halfDiagonal, -halfDiagonal, diagonal, diagonal);

        // draw sky
        context.fillStyle = SKY_COLOR;
        context.fillRect(-halfDiagonal, -halfDiagonal * 3 + horizon, diagonal, halfDiagonal * 3);
        context.beginPath();
        context.moveTo(halfDiagonal, horizon);
        context.lineTo(-halfDiagonal, horizon);
        context.stroke();

        // top arrow
        context.beginPath();
        context.moveTo(0, -radius * 0.9);
        context.lineTo(radius * 0.05, -radius * 0.85);
        context.lineTo(-radius * 0.05, -radius * 0.85);
        context.closePath();
        context.stroke();

        drawPitchAngleGroup(20, 4);
        drawPitchAngleGroup(10, 4);
        drawPitchAngleGroup(0, 4);
        drawPitchAngleGroup(-10, 4);
        drawPitchAngleGroup(-20, 4);

        context.restore();

        drawForeground();
    }

    function drawPitchAngleGroup(angle) {
        drawPitchAngle(angle, 4);
        drawPitchAngle(angle - 2.5, 1);
        drawPitchAngle(angle - 5, 2);
        drawPitchAngle(angle - 7.5, 1);
    }
    function drawPitchAngle(angle, length) {
        context.beginPath();
        context.moveTo(-canvas.width/20 * length, -(roundedPitch + angle) * canvas.height/50 + horizon);
        context.lineTo(canvas.width/20 * length, -(roundedPitch + angle) * canvas.height/50 + horizon);
        context.stroke();
    }

    function drawForeground() {
        context.save();
        context.translate(canvas.width/2, canvas.height/2);

        context.strokeStyle = LINE_COLOR;
        context.lineWidth = 2;

        drawRollAngle(0);
        drawRollAnglePair(10, true);
        drawRollAnglePair(20, true);
        drawRollAnglePair(30);
        drawRollAnglePair(45, true);
        drawRollAnglePair(60);

        // draw plane
        context.fillStyle = PLANE_COLOR;
        context.strokeStyle = PLANE_COLOR;
        context.lineWidth = 4;

        context.beginPath();
        context.moveTo(canvas.width/4, 0);
        context.lineTo(canvas.width/16, 0);
        context.lineTo(canvas.width/16, canvas.width/20);
        context.stroke();

        context.beginPath();
        context.moveTo(-canvas.width/4, 0);
        context.lineTo(-canvas.width/16, 0);
        context.lineTo(-canvas.width/16, canvas.width/20);
        context.stroke();

        context.beginPath();
        context.arc(0, 0, canvas.width/100, 0, 2* Math.PI);
        context.fill();

        context.restore();
    }

    function drawRollAnglePair(angle, short) {
        drawRollAngle(angle, short);
        drawRollAngle(-angle, short);
    }
    function drawRollAngle(angle, short) {
        var end = short ? radius * 0.95 : radius;
        context.beginPath();
        context.moveTo(radius * 0.9 * Math.sin(radians(angle)), -radius * 0.9 * Math.cos(radians(angle)));
        context.lineTo(end * Math.sin(radians(angle)), -end * Math.cos(radians(angle)));
        context.closePath();
        context.stroke();
    }

    function radians(degrees) {
        return degrees * Math.PI / 180;
    };

    return {
        start: function(canvasId) {
            canvas = document.getElementById(canvasId);

            diagonal = Math.sqrt(Math.pow(canvas.width, 2) + Math.pow(canvas.height, 2));
            halfDiagonal = diagonal/2;
            radius = Math.min(canvas.width, canvas.height) / 2;

            context = canvas.getContext("2d");

            repaint();
        },

        draw: function(pRoll, pPitch) {
            if (context) {
                roll = pRoll;
                pitch = pPitch;
                roundedPitch = Math.round(pitch / 10) * 10;
                horizon = pitch * canvas.height/50;

                repaint();
            }
        },

        running: function() {
            return (typeof context !== "undefined");
        }
    };
})();
