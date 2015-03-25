<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.rawgit.com/jbeale/html5sortable/master/jquery.sortable.js"></script>

<div class="container" id="activity-editor">
    <div class="row">
        <div class="col-lg-8">
            <form method="POST">
                <input type="hidden" id="questionIds" name="questionIds" value="<c:if test="${not empty activity}">${activity.getQuestionIds()}</c:if>">
                <div class="form-group">
                    <label for="activityName">Activity Name</label>
                    <input type="text" name="activityName" class="form-control" id="activityName" placeholder="activity-name-here" <c:if test="${not empty activity}">value="${activity.getName()}"</c:if>>
                    <p class="help-block">This is a name for the activity. This is for your own sorting purposes only, and will not be displayed to other users.</p>
                </div>
                <div class="form-group">
                    <label>Questions:</label>
                </div>
                <div class="question-selector">
                    <button type="button" id="add-question" class="btn btn-primary" data-toggle="modal" onclick="resetQuestionModal()" data-target="#addQuestionModal">Add Question</button>
                    <ul class="sortable">
                    <c:choose>
                        <c:when test="${empty activity}">

                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${activity.getQuestions()}" var="question" varStatus="loop">
                                <li class="question-list-item" data-questionId="${question.getId()}">
                                    <span class="qi-name">${question.getName()}</span>
                                    <span class="qi-prompt">&ldquo; ${question.getData().getPromptTextExcerpt()} &rdquo;</span>
                                    <span class="qi-options">
                                        <button class="btn-sm btn-danger" style="width:20px; height:20px; line-height:1;padding:5px 5px;" type="button" onclick="$(this).parents('.question-list-item').remove(); applyQuestionListChanges()">X</button>
                                    </span>
                                </li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </ul>
                </div>
                <c:choose>
                    <c:when test="${empty activity}">
                        <button type="submit" class="btn btn-info">Create</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn btn-info">Save Changes</button>
                    </c:otherwise>
                </c:choose>
                <button type="button" class="btn btn-default" onclick="location.href='/activities/list'">Cancel</button>
            </form>
        </div>
        <div class="col-lg-4"></div>
    </div>
</div>
<div class="modal fade" id="addQuestionModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add Question</h4>
            </div>
            <div class="modal-body">
                <p>Choose a question to add:</p>
                <div class="add-question-list list-group">
                    <c:forEach items="${userQuestionbank}" var="question">
                    <a href="javascript:void(0)" class="list-group-item ns-question-item" data-id="${question.getId()}" onclick="selectNewQuestion(this)"><span class="question-name">${question.getName()}</span><span class="prompt-sample">${question.getData().getPromptTextExcerpt()}</span></a>
                    </c:forEach>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="addNewQ" disabled="disabled" onclick="doQAdd(this)">Add</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function applyQuestionListChanges() {
        var string = '';
        var questions = $('.question-list-item');
        for (var i = 0; i < questions.length; i++) {
            string += $(questions[i]).attr('data-questionId');
            if (i < questions.length-1) string += ',';
        }
        $('#questionIds').val(string);
    }
    var nsQuestionId = null;
    var nsQuestionName = null;
    var nsQuestionPrompt = null;
    function selectNewQuestion(item) {
        if (item != null) {
            nsQuestionId = $(item).attr('data-id');
            nsQuestionName = $(item).children('.question-name').html();
            nsQuestionPrompt = $(item).children('.prompt-sample').html();
            $('.ns-question-item').removeClass('active');
            $(item).addClass('active');
        }

        $('#addNewQ').removeAttr("disabled");
    }
    function addQuestionToList(questionId, questionName, promptText) {
        var html = '<li class="question-list-item" data-questionId="'+questionId+'">'+
                '<span class="qi-name">'+questionName+'</span>'+
        '<span class="qi-prompt">&ldquo; '+promptText+' &rdquo;</span>'+
        '<span class="qi-options">'+
                '<button class="btn-sm btn-danger" style="width:20px; height:20px; line-height:1;padding:5px 5px;" type="button" onclick="$(this).parents(\'.question-list-item\').remove(); applyQuestionListChanges();">X</button>'+
        '</span>'+
        '</li>';
        $('.sortable').sortable('destroy');
        $('.sortable').append(html);
        makeSortable();
        applyQuestionListChanges();
    }
    function resetQuestionModal() {
        $('.ns-question-item').removeClass('active');
        $('.ns-question-item').first().addClass('active');
        $('#addNewQ').attr("disabled", "disabled");
    }
    function makeSortable() {
        $('.sortable').sortable({}).bind('sortupdate', function() {applyQuestionListChanges()});
    }
    function doQAdd(btn) {
        if (!btn.hasAttribute("disabled")) {
            addQuestionToList(nsQuestionId, nsQuestionName, nsQuestionPrompt);
            $('#addQuestionModal').modal('hide');
        }
    }
    makeSortable();
</script>