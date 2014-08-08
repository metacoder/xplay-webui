/**
 * Created by benjamin on 8/8/14.
 */

var artificialHorizon = (function() {
    var GROUND_COLOR = "#79582e", LINE_COLOR = "#ffffff", SKY_COLOR = "#205b77";

    var canvas, context;

    var diagonal = 0, halfDiagonal = 0;

    var pitch = 0, roll = 0;

    function repaint() {
        context.save();

        context.translate(canvas.width/2, canvas.height/2);
        context.rotate(-roll);

        context.fillStyle = GROUND_COLOR;
        context.strokeStyle = LINE_COLOR;
        context.lineWidth = 2;

        // draw ground
        context.fillRect(-halfDiagonal, -halfDiagonal, diagonal, diagonal);

        // draw sky
        context.fillStyle = SKY_COLOR;
        context.fillRect(-halfDiagonal, -halfDiagonal, diagonal, halfDiagonal);
        context.beginPath();
        context.moveTo(halfDiagonal, 0);
        context.lineTo(-halfDiagonal, 0);
        context.stroke();

        context.restore();
    }

    function radians(degrees) {
        return degrees * Math.PI / 180;
    };

    return {
        start: function(canvasId) {
            canvas = document.getElementById(canvasId);

            diagonal = Math.sqrt(Math.pow(canvas.width, 2) + Math.pow(canvas.height, 2));
            halfDiagonal = diagonal/2;

            context = canvas.getContext("2d");

            repaint();
        },

        draw: function(degRoll, degPitch) {
            if (context) {
                roll = radians(degRoll);
                pitch = radians(degPitch);

                repaint();
            }
        },

        running: function() {
            return (typeof context !== "undefined");
        }
    };
})();
