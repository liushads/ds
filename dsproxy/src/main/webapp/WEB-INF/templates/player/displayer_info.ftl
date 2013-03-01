<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
申请展示：<br/>
<#if object?exists>
申请展示成功<br/>
</#if>
<@goback />
<@gogame />
</p></card>
</wml>
 