<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村校场<br/>
扫墓的百姓:<br/>
<#if info?exists>
${info}<br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>