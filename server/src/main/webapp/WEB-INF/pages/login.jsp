<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <div class="col-lg-6">
            <c:if test="${login_error == true}">
            <div class="alert alert-danger">Invalid username or password. Please try again.</div>
            </c:if>
            <form class="login-form" method="POST" name="login-form">
                <div class="form-group">
                    <label for="usernameInput">Username</label>
                    <input type="text" class="form-control" id="usernameInput" name="username" placeholder="Enter username"/>
                </div>
                <div class="form-group">
                    <label for="passwordInput">Password</label>
                    <input type="password" class="form-control" id="passwordInput" name="password" placeholder="Password"/>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> Keep me signed in
                    </label>
                </div>
                <button type="submit" class="btn btn-default">Sign In</button>
            </form>
        </div>
        <div class="col-lg-6">

        </div>
    </div>
</div>
