<#macro login path>
    <form action="${path}" method="post">
        <br>
        <div><label> User Name : <input type="text" name="username"/> </label></div>
        <br>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <br>
        <div><input type="submit" value="Log In"/></div>
    </form>
</#macro>

<#macro logout >
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="{{_csrf.token}}" />
        <input type="submit" value="Sign Out"/>
    </form>
</#macro>