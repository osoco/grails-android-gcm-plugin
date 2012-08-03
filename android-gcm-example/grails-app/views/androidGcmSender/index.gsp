<html>
    <head>
        <meta name="layout" content="main" />
        <title><g:message code="sender.title" /></title>
    </head>
    <h1><g:message code="sender.title" /></h1>
    <p><g:message code="sender.appDescription" /></p>
    <p><g:message code="sender.usage" /></p>
    <g:form name="message" action="sendMessage" id="1">
    
        <div class="formEntryContainer">
            <div class="labelContainer">
                <label for="deviceToken"><g:message code="sender.form.deviceToken"/></label>
            </div>
            <div class="inputContainer">
                <div class="toDupe">
                    <g:select name="deviceToken" from="${tokens}" value="${deviceToken}" noSelection="['':'-']"/></br>
                </div>
            </div>
            <div class="actionsContainer">
                <button type="button" class="add dupeParam deviceToken">
                    <img src="${resource(dir:'images/skin',file:'add.png')}" alt="${message(code:'add')}" border="0" />
                </button> 
            </div>
        </div>
        
        <div class="formEntryContainer">
            <div class="labelContainer">
                <g:message code="sender.form.multicast"/>
            </div>
            <div class="inputContainer">
                <g:checkBox name="multicast" value="${multicast?:true}" />
            </div>
        </div>
        
        <div class="formEntryContainer">
            <div class="labelContainer">
                <label for="collapseKey"><g:message code="sender.form.collapseKey"/></label>
            </div>
            <div class="inputContainer">
                <g:textField name="collapseKey" value="${collapseKey}" />
            </div>
            <div class="actionsContainer">
            </div>
        </div>
        
        <div class="formEntryContainer">
            <div class="labelContainer">
                <g:message code="sender.form.message"/>
            </div>
            <div class="inputContainer">
                <div class="toDupe">
                    <div style="display:table"><div style="display:table-row">
                        <div style="display:table-cell">
                            <label for="messageKey"><g:message code="sender.form.messageKey" /></label>:
                        </div>
                        <div style="display:table-cell">
                            <g:textField name="messageKey" value="${messageKey}" />
                        </div>
                    </div>
                    <div style="display:table-row">
                        <div style="display:table-cell">
                            <label for="messageKey"><g:message code="sender.form.messageValue" /></label>:
                        </div>
                        <div style="display:table-cell">
                            <g:textField name="messageValue" value="${messageValue}" /><br/>
                        </div>
                    </div></div>
                </div>
            </div>
            <div class="actionsContainer">
                <button type="button" class="add dupeParam">
                    <img src="${resource(dir:'images/skin',file:'add.png')}" 
                        alt="${message(code:'add')}" border="0" />
                </button> 
            </div>
        </div>
        
        <div class="formEntryContainer">
            <div class="submitContainer">
                <g:submitButton name="send" value="${message(code:'send')}" />
            </div>
        </div>               
    </g:form>
    
    <script type="text/javascript">
        jQuery("input[name='multicast']").attr("disabled", "disabled");
        jQuery(".actionsContainer button.dupeParam").click(function(e) {
            var prev = jQuery(this).parent().prev('.inputContainer');
            var toClone = prev.children('.toDupe :first');
            toClone.clone().appendTo(prev);
        });
        jQuery(".actionsContainer button.dupeParam.deviceToken").click(function(e) {
            jQuery("input[name='multicast']").removeAttr("disabled");
        });
    </script>
</html>
