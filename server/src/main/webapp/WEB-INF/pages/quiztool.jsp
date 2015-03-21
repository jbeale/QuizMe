
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.socket.io/socket.io-1.3.4.js"></script>

<script type="text/javascript">
    var socket = io('http://localhost:3001');
    var user;
    var session;
    var token = "afca3484a7d7ae99d8c";
    socket.connect('http://localhost:3001');
    function joinSession(sessionKey) {
        socket.emit('join session', sessionKey, token);
    };

    socket.on('join result', function(data) {
        alert(data);
    });

</script>