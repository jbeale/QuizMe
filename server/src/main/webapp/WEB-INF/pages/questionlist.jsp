<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    function editQ(questionId) {
        location.href="/questions/edit/"+questionId;
    }
    var qForDeletion = null;
    function deleteQ(questionId, questionName) {
        qForDeletion = questionId;
        $("#deleteModal .modal-body").html("Are you sure you want to delete &quot;"+questionName+"&quot;? This action cannot be undone.");
        $('#deleteModal').modal('show');
    }
    function confirmDeleteModal() {
        location.href="/questions/delete/"+qForDeletion;
    }
</script>
<div class="container">
    <button class="btn btn-info" onclick="location.href='/questions/new'" style="float:right;margin-top:-62px;">Create Question</button>
    <ul class="nav nav-tabs">
        <li role="presentation"><a href="/dashboard">Sessions</a></li>
        <li role="presentation"><a href="/activities/list">Activity Builder</a></li>
        <li role="presentation" class="active"><a href="/questions/list">Question Builder</a></li>
    </ul>
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
                    <button class="btn btn-danger" onclick="deleteQ(${question.getId()}, '${question.getName()}')">Delete</button>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="modalTitle">Confirm Question Delete</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger" onClick="confirmDeleteModal()">Delete Question</button>
            </div>
        </div>
    </div>
</div>