<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    function validate() {
        if ($('#sessionName').val() == "") {
            setError("Please name the session.")
            return false;
        } else if ($('#activitySelect').val() == "null") {
            setError("Please select an activity.")
            return false;
        }
        return true;
    }
    function setError(text) {
        if (text == null) {
            $('#validation-errors').hide();
        } else {
            $('#validation-errors').html(text);
            $('#validation-errors').show();
        }
    }

</script>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <div class="alert alert-danger" id="validation-errors"></div>
            <form class="session-open-form" method="POST" name="session-open-form">
                <div class="form-group">
                    <label for="sessionName">Session Name</label>
                    <input type="text" class="form-control" id="sessionName" name="sessionName" placeholder="Enter a name"/>
                </div>
                <div class="form-group">
                    <label for="activitySelect">Activity</label>
                    <select class="form-control" id="activitySelect" name="activitySelect">
                        <option value="null" selected>Select an Activity</option>
                        <c:forEach items="${activities}" var="activity">
                            <option value="${activity.getId()}">${activity.getName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="button" id="submit-btn" class="btn btn-default" onclick="if (validate()) $('.session-open-form').submit();">Start</button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    setError(null);
</script>