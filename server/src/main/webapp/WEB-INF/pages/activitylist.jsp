<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    function editActivity(activityId) {
        location.href="/activities/"+activityId;
    }
    var activityForDeletion = null;
    function deleteQ(activityId, activityName) {
        activityForDeletion = activityId;
        $("#deleteModal .modal-body").html("Are you sure you want to delete &quot;"+activityName+"&quot;? This action cannot be undone.");
        $('#deleteModal').modal('show');
    }
    function confirmDeleteModal() {
        location.href="/activities/delete/"+activityForDeletion;
    }
</script>
<div class="container">
    <button class="btn btn-info" onclick="location.href='/activities/new'" style="float:right;margin-top:-62px;">New Activity</button>
    <ul class="nav nav-tabs">
        <li role="presentation"><a href="/dashboard">Sessions</a></li>
        <li role="presentation" class="active"><a href="/activities/list">Activity Builder</a></li>
        <li role="presentation"><a href="/questions/list">Question Builder</a></li>
    </ul>
    <table class="table table-hover">
        <thead>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Action</th>
        </thead>
        <tbody>
            <c:forEach items="${activities}" var="activity">
            <tr>
                <td>${activity.getId()}</td>
                <td><a href="/activities/${activity.getId()}">${activity.getName()}</a></td>
                <td>
                    <button class="btn btn-warning" onclick="editActivity(${activity.getId()})">Edit</button>
                    <button class="btn btn-danger" onclick="editActivity(${activity.getId()}, '${activity.getName()}')">Delete</button>
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
                <h4 class="modal-title" id="modalTitle">Confirm Activity Deletion</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger" onClick="confirmDeleteModal()">Delete Activity</button>
            </div>
        </div>
    </div>
</div>