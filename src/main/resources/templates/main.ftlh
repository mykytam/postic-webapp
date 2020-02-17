<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="Input message" />
            <input type="file" name="file"   />
            <input type="text" name="tag" placeholder="Tag">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Add</button>
        </form>
    </div>
    <div>Messages List</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value = ${filter?ifExists}>
        <button type="submit">Find</button>
    </form>

    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <div>
                <#if message.filename??>
                    <img src = "/img/${message.filename}"
                </#if>
            </div>
        </div>
    <#else>
        No Messages
    </#list>
    <br>
    <div>
        <@l.logout />
        <span>
             <br>
            <a href="/user">User list</a>
        </span>
    </div>
</@c.page>