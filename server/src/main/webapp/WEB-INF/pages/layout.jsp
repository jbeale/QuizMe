<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Whiz - ${titlebar}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/quizwhiz.css" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="header">
    <div class="container">
        <div class="brand"><img src="https://s3.amazonaws.com/quizwhiz/layout/img/quizwhizwhiteh.png" width="187"></div>
        <c:if test="${not empty layout_currentUser}">
        <div id="user-nav" class="btn-group">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <img src="${layout_currentUser.getProfilePictureThumbnail()}">
                ${layout_currentUser.getFullname()}<span class="caret"></span>
            </button>
            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                <li><a href="/dashboard">Dashboard</a></li>
                <li><a href="/account">My Account</a></li>
                <li class="divider"></li>
                <li><a href="/auth/logout">Sign Out</a></li>
            </ul>
        </div>
        </c:if>
    </div>
</div>
<c:if test="${empty disableTitlebar}">
<div class="titlebar">
    <div class="container titlebar-text">${titlebar}</div>
</div>
</c:if>
<div class="content">
    <jsp:include page="/WEB-INF/pages/${view}.jsp"></jsp:include>
</div>
<div class="footer">

</div>
</body>
</html>