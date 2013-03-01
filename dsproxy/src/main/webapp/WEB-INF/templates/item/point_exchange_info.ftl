<#include "/include/header.ftl">
<card title="${game_title}"><p>
积分兑换<br/>
<#if info?exists>
${info}<br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>