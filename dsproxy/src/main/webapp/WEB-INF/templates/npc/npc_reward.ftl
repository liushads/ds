<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村校场<br/>
奖品发放官：<#if rewards?exists>${rewards}</#if><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>