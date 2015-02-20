
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <div class="col-lg-8">
            <form>
                <div class="form-group">
                    <label for="questionName">Question Name</label>
                    <input type="text" class="form-control" id="questionName" placeholder="question-name-here">
                    <p class="help-block">This is a short name for your own sorting purposes only. It will not be displayed to anyone else!</p>
                </div>
                <div class="form-group">
                    <label for="questionType">Type</label>
                    <select class="form-control" id="questionType">
                        <option value="mc">Multiple Choice</option>
                        <option value="fitb">Fill in the blank</option>
                    </select>
                </div>
                <div class="question-data">
                    <div class="form-group">
                        <label for="promptText">Prompt:</label>
                        <textarea class="form-control" id="promptText" rows="3"></textarea>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <input type="radio" name="answerRadios" checked />
                        </span>
                        <input type="text" class="form-control" id="answerA"/>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <input type="radio" name="answerRadios" />
                        </span>
                        <input type="text" class="form-control" id="answerB"/>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <input type="radio" name="answerRadios" />
                        </span>
                        <input type="text" class="form-control" id="answerC"/>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <input type="radio" name="answerRadios" />
                        </span>
                        <input type="text" class="form-control" id="answerD"/>
                    </div>
                </div>
                <button type="submit" class="btn btn-info">Create</button>
            </form>
        </div>
        <div class="col-lg-4"></div>
    </div>
</div>