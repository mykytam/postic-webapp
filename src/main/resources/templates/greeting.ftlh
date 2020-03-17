<#include "parts/security.ftlh">
<#import "parts/common.ftlh" as c>

<@c.page>

    <#if user??>
    <h4>Hello, ${name}!</h4>
    <#else>
    <h4>Hello, guest!</h4>
    </#if>
    <br>
    <div>
        <h5>This is a Postic!</h5>
    </div>
    <br>
    <div>
        Fully functional web application in which you can create, edit, like posts and follow people!
    </div>
    <div>
       Created by Mykyta Morar with letsCode's Youtube tutorial on Spring Framework.
    </div>
</@c.page>