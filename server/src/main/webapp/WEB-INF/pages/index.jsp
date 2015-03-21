<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--<p>Current User: ${currentUser.getUsername()}</p>-->

<!DOCTYPE html>
<html>
<head>
    <title>Quiz Whiz</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/quizwhiz.css" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body id="homepage">
<div class="home_header">
    <div class="header_overlay">
        <div class="container navigation">
            <div class="brand"><img src="https://s3.amazonaws.com/quizwhiz/layout/img/quizwhizwhiteh.png" width="187"></div>
            <div class="useractions">
                <button id="login-btn" onclick="location.href='auth/login'" >Login</button><button onclick="location.href='auth/create'" id="register-btn">Sign Up Free</button>
            </div>
        </div>
        <div class="container bannerarea">
            <h1 class="quote">Engage students and track progress in real time during class</h1>
        </div>
    </div>
</div>

<section class="availableon">
    <div class="container">
        <span class="textcenter">Available on</span>
        <span class="textcenter">Android / Chrome</span>
    </div>
</section>
<section class="quote">
    <div class="container">
        <div class="row">
            <div class="col-lg-5 pic">

            </div>
            <div class="col-lg-7 text">
                <div class="maincap">Turn distractions into learning tools</div>
                <div class="subcap">Give them something to pay attention to, in an interactive, fun way that helps them learn the material, and helps you know how well they're learning it.</div>
            </div>
        </div>
    </div>
</section>
<section class="features">

</section>

<div class="footer">

</div>
</body>
</html>