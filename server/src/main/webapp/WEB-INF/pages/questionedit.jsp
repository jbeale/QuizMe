<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container" id="question-editor">
    <div class="row">
        <div class="col-lg-8">
            <form method="POST">
                <div class="form-group">
                    <label for="questionName">Question Name</label>
                    <input type="text" name="questionName" class="form-control" id="questionName" placeholder="question-name-here" <c:if test="${not empty question}">value="${question.getName()}"</c:if>>
                    <p class="help-block">This is a short name for your own sorting purposes only. It will not be displayed to anyone else!</p>
                </div>
                <div class="form-group">
                    <label for="questionType">Type</label>
                    <select class="form-control" id="questionType" name="questionType">
                        <option value="mc">Multiple Choice</option>
                        <!--<option value="fitb">Fill in the blank</option>-->
                    </select>
                </div>
                <div class="question-data">
                    <div class="form-group">
                        <label for="promptText">Prompt:</label>
                        <textarea class="form-control" id="promptText" name="promptText" rows="3"><c:if test="${not empty question}">${question.getData().getPrompt()}</c:if></textarea>
                    </div>
                    <c:choose>
                        <c:when test="${empty question}">
                            <div class="input-group">
                        <span class="input-group-addon">
                            <input type="radio" name="correctOption" value="0" checked />
                        </span>
                                <input type="text" class="form-control" name="optionText[]"/>
                            </div>
                            <div class="input-group">
                        <span class="input-group-addon">
                            <input type="radio" name="correctOption" value="1"  />
                        </span>
                                <input type="text" class="form-control" name="optionText[]"/>
                            </div>
                            <div class="input-group">
                        <span class="input-group-addon">
                            <input type="radio" name="correctOption" value="2" />
                        </span>
                                <input type="text" class="form-control"  name="optionText[]"/>
                            </div>
                            <div class="input-group">
                        <span class="input-group-addon">
                            <input type="radio" name="correctOption" value="3"  />
                        </span>
                                <input type="text" class="form-control" name="optionText[]"/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${question.getData().getChoices()}" var="choice" varStatus="loop">
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <input type="radio" name="correctOption" value="${loop.index}" <c:if test="${choice.getCorrect()==true}">checked="checked"</c:if> />
                                    </span>
                                    <input type="text" class="form-control" name="optionText[]" value="${choice.getText()}"/>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                </div>
                <c:choose>
                    <c:when test="${empty question}">
                        <button type="submit" class="btn btn-info">Create</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn btn-info">Save Changes</button>
                    </c:otherwise>
                </c:choose>
                <button type="button" class="btn btn-default" onclick="javascript:href='/questions/list'">Cancel</button>
            </form>
        </div>
        <div class="col-lg-4"></div>
    </div>
</div>