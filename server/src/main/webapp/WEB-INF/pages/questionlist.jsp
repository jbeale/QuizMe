<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <button class="btn btn-info" onclick="location.href='/questions/new'" style="float:right;margin-top:-62px;">Create Question</button>
    <table class="table table-hover">
        <thead>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Action</th>
        </thead>
        <tbody>
            <c:forEach items="${questions}" var="question">
            <tr>
                <td>${question.getId()}</td>
                <td><a href="/questions/edit/${question.getId()}">${question.getName()}</a></td>
                <td>${question.getType()}</td>
                <td>
                    <button class="btn btn-warning" onclick="editQ(${question.getId()})">Edit</button>
                    <button class="btn btn-danger" onclick="deleteQ(${question.getId()})">Delete</button>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</div>