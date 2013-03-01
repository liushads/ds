<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if rewards?exists>${rewards}</#if><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>