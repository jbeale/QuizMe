<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <div class="col-lg-6">
            <c:if test="${register_error == true}">
            <div class="alert alert-danger">An error occurred during registration. Ensure you have filled out all fields and try again.</div>
            </c:if>
            <c:if test="${username_error == true}">
                <div class="alert alert-danger">Username was already taken. Please try again</div>
            </c:if>
            <form class="register-form" method="POST" name="register-form">
                <div class="form-group">
                    <label for="usernameInput">Pick a Username</label>
                    <input type="text" class="form-control" id="usernameInput" name="username" placeholder="Enter username"/>
                </div>
                <div class="form-group">
                    <label for="passwordInput">Password</label>
                    <input type="password" class="form-control" id="passwordInput" name="password" placeholder="Password"/>
                </div>
                <div class="form-group">
                    <label for="firstnameInput">First Name</label>
                    <input type="text" class="form-control" id="firstnameInput" name="firstname" placeholder=""/>
                </div>
                <div class="form-group">
                    <label for="lastnameInput">Last Name</label>
                    <input type="text" class="form-control" id="lastnameInput" name="lastname" placeholder=""/>
                </div>
                <div class="form-group">
                    <label for="emailInput">Email Address</label>
                    <input type="email" class="form-control" id="emailInput" name="email" placeholder=""/>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> Keep me signed in
                    </label>
                </div>
                <button type="submit" class="btn btn-default">Create</button>
            </form>
        </div>
        <div class="col-lg-6">

        </div>
    </div>
</div>
