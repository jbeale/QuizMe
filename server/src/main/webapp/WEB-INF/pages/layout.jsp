<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Whiz - ${titlebar}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/quizme/resources/css/quizwhiz.css" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="header">
    <div class="container">
        <div class="brand"><img src="/quizme/resources/quizwhizwhiteh.png" width="187"></div>
    </div>
</div>
<div class="titlebar">
    <div class="container titlebar-text">${titlebar}</div>
</div>
<div class="content">
    <jsp:include page="/WEB-INF/pages/${view}.jsp"></jsp:include>
</div>
<div class="footer">

</div>
</body>
</html>