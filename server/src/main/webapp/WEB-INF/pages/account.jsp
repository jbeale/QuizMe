<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <form class="form-horizontal">
        <div class="form-group">
            <div class="col-sm-2 control-label">Profile Picture</div>
            <div class="col-sm-10">
                <img class="img-thumbnail" src="${user.getProfilePicture()}">
                <p>Your profile picture is linked to your email address on <a href="http://gravatar.com">Gravatar.</a> You can change your picture there.</p>
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputEmail" name="email" value="${user.getEmail()}">
            </div>
        </div>
        <div class="form-group">
            <label for="inputFirstName" class="col-sm-2 control-label">First Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputFirstName" name="firstname" value="${user.getFirstname()}">
            </div>
        </div>
        <div class="form-group">
            <label for="inputLastName" class="col-sm-2 control-label">Last Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputLastName" name="lastname" value="${user.getLastname()}">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2">
                Change Password:
            </div>
        </div>
        <div class="form-group">
            <label for="inputPass" class="col-sm-2 control-label">New Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="inputPass" name="password">
            </div>
        </div>
        <div class="form-group">
            <label for="inputConfPass" class="col-sm-2 control-label">Confirm New Password:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="inputConfPass" name="confirmpassword">
            </div>
        </div>
    </form>
</div>