<#include "/include/header.ftl">
<card title="${game_title}"><p>
乐羊羊：<br/>
<#if info?exists>
${info}<br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>