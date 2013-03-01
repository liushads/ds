<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if location?exists>
${location}<br/>
</#if>
<#if info?exists>
${info}<br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p>
</card>
</wml>