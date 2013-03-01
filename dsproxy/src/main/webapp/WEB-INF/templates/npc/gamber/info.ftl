<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if gambler?exists>
开奖结果:<br/>
${gambler}<br/>
</#if>
<#if info?exists>
${info}<br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>