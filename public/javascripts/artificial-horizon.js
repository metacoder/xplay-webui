/**
 * Created by benjamin on 8/8/14.
 */

var artificialHorizon = (function() {
    var GROUND_COLOR = "#79582e", LINE_COLOR = "#ffffff", SKY_COLOR = "#205b77", PLANE_COLOR = "#000000";

    var canvas, context;

    var diagonal = 0, halfDiagonal = 0, radius = 0;

    var pitch = 0, roll = 0;

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

        var roundedPitch = Math.round(pitch / 10) * 10

        drawPitchAngle(roundedPitch + 20, 4);
        drawPitchAngle(roundedPitch + 17.5, 1);
        drawPitchAngle(roundedPitch + 15, 2);
        drawPitchAngle(roundedPitch + 12.5, 1);
        drawPitchAngle(roundedPitch + 10, 4);
        drawPitchAngle(roundedPitch + 7.5, 1);
        drawPitchAngle(roundedPitch + 5, 2);
        drawPitchAngle(roundedPitch + 2.5, 1);
        drawPitchAngle(roundedPitch + 0, 4);
        drawPitchAngle(roundedPitch + -2.5, 1);
        drawPitchAngle(roundedPitch + -5, 2);
        drawPitchAngle(roundedPitch + -7.5, 1);
        drawPitchAngle(roundedPitch + -10, 4);
        drawPitchAngle(roundedPitch + -12.5, 1);
        drawPitchAngle(roundedPitch + -15, 2);
        drawPitchAngle(roundedPitch + -17.5, 1);
        drawPitchAngle(roundedPitch + -20, 4);
        drawPitchAngle(roundedPitch + -22.5, 1);
        drawPitchAngle(roundedPitch + -25, 2);
        drawPitchAngle(roundedPitch + -27.5, 1);
        drawPitchAngle(roundedPitch + -30, 4);

        context.restore();

        drawForeground();
    }

    function drawPitchAngle(angle, length) {
        context.beginPath();
        context.moveTo(-canvas.width/20 * length, -angle * canvas.height/50 + horizon);
        context.lineTo(canvas.width/20 * length, -angle * canvas.height/50 + horizon);
        context.stroke();
    }

    function drawForeground() {
        context.save();
        context.translate(canvas.width/2, canvas.height/2);

        context.strokeStyle = LINE_COLOR;
        context.lineWidth = 2;

        drawRollAngle(-60);
        drawRollAngle(-45, true);
        drawRollAngle(-30);
        drawRollAngle(-20, true);
        drawRollAngle(-10, true);
        drawRollAngle(0);
        drawRollAngle(10, true);
        drawRollAngle(20, true);
        drawRollAngle(30);
        drawRollAngle(45, true);
        drawRollAngle(60);

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
                horizon = pitch * canvas.height/50;

                repaint();
            }
        },

        running: function() {
            return (typeof context !== "undefined");
        }
    };
})();
