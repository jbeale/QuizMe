<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <div class="col-lg-8">
            <p>Welcome, ${currentUser.getFullname()}</p>

            <ul>
                <li><a href="/questions/list">My Questions</a></li>
                <li><a href="/quiz/list">My Quizzes</a></li>
                <li><a href="/sessions/new">Start New Session</a></li>
            </ul>
        </div>
        <div class="col-lg-4">

        </div>
    </div>
</div>
